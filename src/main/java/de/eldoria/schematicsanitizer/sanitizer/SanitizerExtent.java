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
import java.util.function.Supplier;

public class SanitizerExtent extends BlockArrayClipboard {
    private final Settings settings;
    private final ReportBuilder report = new ReportBuilder();

    public SanitizerExtent(Clipboard base, Settings settings) {
        super(base.getRegion());
        this.settings = settings;
        report.limit().maxSize(Math.max(Math.max(base.getHeight(), base.getWidth()), base.getLength()));
    }

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

    public SanitizerReport report(Path newPath) {
        report.newPath(newPath);
        return report.build();
    }

    public SanitizerReport report() {
        return report.build();
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
                case CREATURE -> content.creatures() <= contentLimit.creatures() ? ifValid.get() : null;
                case NON_CREATURE -> content.nonCreatures() <= contentLimit.nonCreatures() ? ifValid.get() : null;
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
        for (String key : nbt.keySet()) {
            if (settings.filter().nbtBlacklist().contains(key)) {
                nbt = nbt.remove(key);
                report.blockNbt().removed(vector3, block.getBlockType(), NbtRemovalCause.ILLEGAL_TAG, key);
                continue;
            }
            BinaryTag nextTag = nbt.get(key);
            if (nextTag == null) continue;
            BinaryTagType<? extends BinaryTag> nextType = nextTag.type();
            if (BinaryTagTypes.COMPOUND.test(nextType)) {
                nbt = cleanBlockNBT(vector3, block, nbt.getCompound(key));
                continue;
            }

            if (BinaryTagTypes.STRING.test(nextType)) {
                String text = nbt.getString(key);
                for (String e : settings.filter().textBlacklist()) {
                    if (text.contains(e)) {
                        nbt = nbt.remove(key);
                        report.blockNbt().removed(vector3, block.getBlockType(), NbtRemovalCause.TEXT_BLACKLIST, key, text);
                    }
                }
            }
        }
        return nbt;
    }

    private CompoundBinaryTag cleanEntityNBT(Location location, BaseEntity entity, CompoundBinaryTag nbt) {
        for (String key : nbt.keySet()) {
            if (settings.filter().nbtBlacklist().contains(key)) {
                nbt = nbt.remove(key);
                report.entityNbt().removed(location, entity, NbtRemovalCause.ILLEGAL_TAG, key);
                continue;
            }
            BinaryTag nextTag = nbt.get(key);
            if (nextTag == null) continue;
            BinaryTagType<? extends BinaryTag> nextType = nextTag.type();
            if (nextType.test(BinaryTagTypes.COMPOUND)) {
                nbt = cleanEntityNBT(location, entity, nbt.getCompound(key));
                continue;
            }

            if (nextType.test(BinaryTagTypes.STRING)) {
                String text = nbt.getString(key);
                for (String e : settings.filter().textBlacklist()) {
                    if (text.contains(e)) {
                        nbt = nbt.remove(key);
                        report.entityNbt().removed(location, entity, NbtRemovalCause.TEXT_BLACKLIST, key, text);
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
