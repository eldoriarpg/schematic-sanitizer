/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer;

import com.sk89q.jnbt.CompoundTag;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.entity.Entity;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.util.nbt.BinaryTag;
import com.sk89q.worldedit.util.nbt.BinaryTagType;
import com.sk89q.worldedit.util.nbt.BinaryTagTypes;
import com.sk89q.worldedit.util.nbt.CompoundBinaryTag;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import com.sk89q.worldedit.world.block.BlockTypes;
import de.eldoria.schematicsanitizer.sanitizer.filter.BlockFilter;
import de.eldoria.schematicsanitizer.sanitizer.filter.EntityFilter;
import de.eldoria.schematicsanitizer.sanitizer.filter.Filter;
import de.eldoria.schematicsanitizer.sanitizer.limit.ContentLimit;
import de.eldoria.schematicsanitizer.sanitizer.report.SanitizerReport;
import de.eldoria.schematicsanitizer.sanitizer.report.builder.ContentReportBuilder;
import de.eldoria.schematicsanitizer.sanitizer.report.builder.ReportBuilder;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.BlockRemovalCause;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.EntityRemovalCause;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.NbtRemovalCause;
import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedBlock;
import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedEntity;
import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * An extent which filters the set blocks based on the provided {@link Settings}.
 * <p>
 * It is recommended to use a {@link ForwardExtentCopy} to write into it.
 *
 * <pre>
 * SanitizerExtent sanitizerExtent = new SanitizerExtent(path, clipboard, settings);
 * ForwardExtentCopy copy = new ForwardExtentCopy(clipboard, clipboard.getRegion(), sanitizerExtent, clipboard.getMinimumPoint());
 * Operations.completeBlindly(copy);
 * </pre>
 * <p>
 * The extent will then remove any data.
 * <p>
 * A report about the removed data can be retrieved with {@link #report()}
 * <br>
 * <br>
 * <b>What the extent will do:</b>
 * <ul>
 * <li> remove creature entities once the {@link ContentLimit#creatures()} limit is reached
 * <li> remove non creature entities once the {@link ContentLimit#nonCreatures()} limit is reached
 * <li> remove invalid nbt tags from blocks as defined in {@link Filter#nbtBlacklist()}
 * <li> remove invalid nbt tags with content from blocks as defined in {@link Filter#textBlacklist()}
 * <li> remove invalid nbt tags from entities as defined in {@link Filter#nbtBlacklist()}
 * <li> remove invalid nbt tags with content from entities as defined in {@link Filter#textBlacklist()}
 * <li> remove blocks when they are contained in {@link BlockFilter#materialBlacklist()}
 * <li> remove entities when they are contained in {@link EntityFilter#entityBlacklist()}
 * <li> remove entities if they are a creature and {@link EntityFilter#removeCreature()} is true
 * <li> remove entities if they are not a creature and {@link EntityFilter#removeNonCreatures()} is true
 * </ul>
 * <p>
 * <b>What the extent won't do:</b>
 * <ul>
 * <li> remove blocks once the {@link ContentLimit#blocks()} limit is reached
 * <li> remove blocks once the {@link ContentLimit#nonAirBlocks()} limit is reached
 * </ul>
 */
public class SanitizerExtent extends BlockArrayClipboard {
    private final Settings settings;
    private final ReportBuilder report;

    /**
     * Creates a new extent
     *
     * @param path     path of the file used in the report
     * @param base     the clipboard which will be copied
     * @param settings settings for filtering
     */
    public SanitizerExtent(Path path, Clipboard base, Settings settings) {
        super(base.getRegion());
        this.settings = settings;
        this.report = new ReportBuilder(path);
        this.report.limit().maxSize(Math.max(Math.max(base.getHeight(), base.getWidth()), base.getLength()));
        setOrigin(base.getOrigin());
    }

    public SanitizerReport report(Path newPath) {
        report.newPath(newPath);
        return report.build();
    }

    public SanitizerReport report() {
        return report.build();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean setTile(int x, int y, int z, CompoundTag tag) {
        //TODO not sure what to do here
        return super.setTile(x, y, z, tag);
    }

    @Override
    public <B extends BlockStateHolder<B>> boolean setBlock(int x, int y, int z, B block) {
        report.limit().content().countBlock();
        if (block.getBlockType() != BlockTypes.AIR) report.limit().content().countNonAirBlock();
        return allowedBlock(x, y, z, block) && super.setBlock(x, y, z, cleanBlockData(BlockVector3.at(x, y, z), block));
    }

    @Nullable
    @Override
    public Entity createEntity(Location location, BaseEntity entity) {
        return createEntity(location, entity, () -> super.createEntity(location, cleanEntity(location, entity)));
    }

    @Nullable
    @Override
    public Entity createEntity(Location location, BaseEntity entity, UUID uuid) {
        return createEntity(location, entity, () -> super.createEntity(location, cleanEntity(location, entity), uuid));
    }

    @Override
    public <B extends BlockStateHolder<B>> int setBlocks(Region region, B block) throws MaxChangedBlocksException {
        return super.setBlocks(region, block);
    }

    @Override
    public int setBlocks(Region region, Pattern pattern) throws MaxChangedBlocksException {
        // Should not happen inside a schematic copy
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public int setBlocks(Set<BlockVector3> vset, Pattern pattern) {
        // Should not happen inside a schematic copy
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    public <T extends BlockStateHolder<T>> boolean setBlock(BlockVector3 position, T block) throws WorldEditException {
        return setBlock(position.getBlockX(), position.getBlockY(), position.getBlockZ(), block);
    }

    /**
     * Checks if a block is allowed based on the given {@link Settings}.
     *
     * @param x     the x-coordinate of the block
     * @param y     the y-coordinate of the block
     * @param z     the z-coordinate of the block
     * @param block the block to be checked
     * @return true if the block is allowed, false otherwise
     */
    private <B extends BlockStateHolder<B>> boolean allowedBlock(int x, int y, int z, B block) {
        BlockFilter blockFilter = settings.filter().blockFilter();
        if (blockFilter.isBlacklisted(block.getBlockType())) {
            report.block().removed(new RemovedBlock(BlockVector3.at(x, y, z), block.getBlockType(), BlockRemovalCause.BLACKLIST));
            return false;
        }
        return true;
    }

    /**
     * Cleans the block data by removing unwanted data from the given BlockStateHolder.
     * If the block is not an instance of BaseBlock, the method returns the block as is.
     *
     * @param vector3 The BlockVector3 representing the position of the block.
     * @param block   The BlockStateHolder to clean the data for.
     * @param <B>     The type of the BlockStateHolder.
     * @return The cleaned BlockStateHolder.
     */
    @SuppressWarnings("unchecked")
    private <B extends BlockStateHolder<B>> B cleanBlockData(BlockVector3 vector3, B block) {
        if (!(block instanceof BaseBlock)) return block;
        CompoundBinaryTag nbt = block.getNbt();
        if (nbt != null) nbt = cleanBlockNBT(vector3, block, nbt);
        return (B) block.toBaseBlock(nbt);
    }

    private Entity createEntity(Location location, BaseEntity entity, Supplier<Entity> ifValid) {
        CreatureType type = CreatureType.getType(entity);
        ContentReportBuilder content = report.limit().content();
        ContentLimit contentLimit = settings.limit().contentLimit();
        content.countTotalEntity(type);
        if (allowedEntity(location, entity)) {
            content.countEntity(type);
            return switch (type) {
                case CREATURE -> {
                    if (content.creatures() <= contentLimit.creatures()) {
                        yield ifValid.get();
                    }
                    report.entity().removed(new RemovedEntity(location, entity, EntityRemovalCause.CREATURE_LIMIT));
                    yield null;
                }
                case NON_CREATURE -> {
                    if (content.nonCreatures() <= contentLimit.nonCreatures()) {
                        yield ifValid.get();
                    }
                    report.entity().removed(new RemovedEntity(location, entity, EntityRemovalCause.NON_CREATURE_LIMIT));
                    yield null;
                }
                case UNKNOWN -> null;
            };
        }
        return null;
    }

    private boolean allowedEntity(Location location, BaseEntity entity) {
        EntityFilter filter = settings.filter().entityFilter();
        CreatureType type = CreatureType.getType(entity);

        if (type == CreatureType.UNKNOWN) {
            report.entity().removed(new RemovedEntity(location, entity, EntityRemovalCause.UNKNOWN_TYPE));
            return false;
        }

        if (type == CreatureType.CREATURE && filter.removeCreature()) {
            report.entity().removed(new RemovedEntity(location, entity, EntityRemovalCause.CREATURE));
            return false;
        } else if (filter.removeNonCreatures()) {
            report.entity().removed(new RemovedEntity(location, entity, EntityRemovalCause.NON_CREATURE));
            return false;
        }

        if (filter.isBlacklisted(entity.getType())) {
            report.entity().removed(new RemovedEntity(location, entity, EntityRemovalCause.BLACKLIST));
            return false;
        }
        return true;
    }


    private <B extends BlockStateHolder<B>> CompoundBinaryTag cleanBlockNBT(BlockVector3 vector3, B block, CompoundBinaryTag nbt) {
        return cleanNBT(tag -> report.blockNbt().removed(vector3, block.getBlockType(), NbtRemovalCause.ILLEGAL_TAG, tag),
                (key, value) -> report.blockNbt().removed(vector3, block.getBlockType(), NbtRemovalCause.TEXT_BLACKLIST, key, value),
                nbt);
    }

    private CompoundBinaryTag cleanEntityNBT(Location location, BaseEntity entity, CompoundBinaryTag nbt) {
        return cleanNBT(t -> report.entityNbt().removed(location, entity, NbtRemovalCause.ILLEGAL_TAG, t),
                (tag, text) -> report.entityNbt().removed(location, entity, NbtRemovalCause.TEXT_BLACKLIST, tag, text),
                nbt);
    }

    private CompoundBinaryTag cleanNBT(Consumer<String> onIllegalTag, BiConsumer<String, String> onIllegalText, CompoundBinaryTag nbt) {
        // iterate over keys
        for (String key : nbt.keySet()) {
            // remove key if it is blacklisted
            if (settings.filter().nbtBlacklist().contains(key)) {
                nbt = nbt.remove(key);
                onIllegalTag.accept(key);
                continue;
            }
            // get the entry
            BinaryTag entry = nbt.get(key);
            if (entry == null) continue;
            BinaryTagType<? extends BinaryTag> type = entry.type();
            // check if the tag is an object/compound
            if (type.test(BinaryTagTypes.COMPOUND)) {
                // check object keys via recursion
                nbt = cleanNBT(onIllegalTag, onIllegalText, nbt.getCompound(key));
                continue;
            }

            // check if the key has a string value
            if (type.test(BinaryTagTypes.STRING)) {
                String text = nbt.getString(key);
                // check if any blacklisted value is contained inside the text
                for (String e : settings.filter().textBlacklist()) {
                    if (text.contains(e)) {
                        nbt = nbt.remove(key);
                        onIllegalText.accept(key, text);
                    }
                }
            }
        }
        return nbt;
    }

    private BaseEntity cleanEntity(Location location, BaseEntity entity) {
        CompoundBinaryTag nbt = entity.getNbt();
        if (nbt != null) entity.setNbt(cleanEntityNBT(location, entity, nbt));
        return entity;
    }
}
