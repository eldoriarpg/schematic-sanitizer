package de.eldoria.schematicsanitizer.sanitizer;

import com.fastasyncworldedit.core.extent.clipboard.MemoryOptimizedClipboard;
import com.sk89q.jnbt.CompoundTag;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.entity.Entity;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.world.block.BlockStateHolder;
import de.eldoria.schematicsanitizer.sanitizer.filter.BlockFilter;
import de.eldoria.schematicsanitizer.sanitizer.filter.EntityFilter;
import de.eldoria.schematicsanitizer.sanitizer.report.Report;
import de.eldoria.schematicsanitizer.sanitizer.report.builder.BlockRemovalCause;
import de.eldoria.schematicsanitizer.sanitizer.report.builder.EntityRemovalCause;
import de.eldoria.schematicsanitizer.sanitizer.report.builder.RemovedBlock;
import de.eldoria.schematicsanitizer.sanitizer.report.builder.RemovedEntity;
import de.eldoria.schematicsanitizer.sanitizer.report.builder.ReportBuilder;
import de.eldoria.schematicsanitizer.sanitizer.settings.Settings;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public class SanitizerExtent extends MemoryOptimizedClipboard {

    private final Settings settings;
    private final ReportBuilder report = new ReportBuilder();

    public SanitizerExtent(Clipboard oldClipboard, Settings settings) {
        super(oldClipboard.getRegion());
        this.settings = settings;
    }

    @Override
    public boolean setTile(int x, int y, int z, CompoundTag tag) {
        return super.setTile(x, y, z, tag);
    }

    @Override
    public <B extends BlockStateHolder<B>> boolean setBlock(int x, int y, int z, B block) {
        return allowedBlock(x, y, z, block) && setBlock(getIndex(x, y, z), cleanBlockData(block));
    }

    private <B extends BlockStateHolder<B>> boolean allowedBlock(int x, int y, int z, B block) {
        BlockFilter blockFilter = settings.filter().blockFilter();
        if (blockFilter.isBlacklisted(block.getBlockType())) {
            report.block().removed(new RemovedBlock(BlockVector3.at(x, y, z), block.getBlockType(), BlockRemovalCause.BLACKLIST));
            return false;
        }
        return true;
    }

    private <B extends BlockStateHolder<B>> B cleanBlockData(B block) {
        // todo: remove illegal items and tags from block data
        return block;
    }

    @Nullable
    @Override
    public Entity createEntity(Location location, BaseEntity entity) {
        return allowedEntity(location, entity) ? super.createEntity(location, cleanEntity(entity)) : null;
    }

    @Nullable
    @Override
    public Entity createEntity(Location location, BaseEntity entity, UUID uuid) {
        return allowedEntity(location, entity) ? super.createEntity(location, cleanEntity(entity), uuid) : null;
    }

    private boolean allowedEntity(Location location, BaseEntity entity) {
        EntityFilter filter = settings.filter().entityFilter();
        Class<? extends org.bukkit.entity.Entity> entityClass = BukkitAdapter.adapt(entity.getType()).getEntityClass();

        if (entityClass == null) {
            report.entity().removed(new RemovedEntity(location, entity, EntityRemovalCause.UNKNOWN_TYPE));
            return false;
        }

        if (Creature.class.isAssignableFrom(entityClass) && filter.removeCreature()) {
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

    private BaseEntity cleanEntity(BaseEntity entity) {
        // todo: remove illegal items from entity
        return entity;
    }

    @Override
    public <B extends BlockStateHolder<B>> int setBlocks(Region region, B block) throws MaxChangedBlocksException {
        return super.setBlocks(region, block);
    }

    @Override
    public int setBlocks(Region region, Pattern pattern) throws MaxChangedBlocksException {
        // Should not happen inside a schematic
        return super.setBlocks(region, pattern);
    }

    @Override
    public int setBlocks(Set<BlockVector3> vset, Pattern pattern) {
        // Should not happen inside a schematic
        return super.setBlocks(vset, pattern);
    }

    @Override
    public <T extends BlockStateHolder<T>> boolean setBlock(BlockVector3 position, T block) throws WorldEditException {
        return super.setBlock(position, block);
    }

    public Report report() {
        return report.build();
    }
}
