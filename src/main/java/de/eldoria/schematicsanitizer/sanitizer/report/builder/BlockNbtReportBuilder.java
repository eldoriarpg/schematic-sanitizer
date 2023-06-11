package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;

import java.util.ArrayList;
import java.util.List;

public class BlockNbtReportBuilder {
    private final List<RemovedBlockNbt> removed = new ArrayList<>();


    public void removed(RemovedBlockNbt removed) {
        this.removed.add(removed);
    }

    public void removed(BlockVector3 vector3, BlockType blockType, NbtRemovalCause cause, String key, String text) {
        removed(new RemovedBlockNbt(vector3, blockType, cause, key, text));
    }

    public void removed(BlockVector3 vector3, BlockType blockType, NbtRemovalCause cause, String key) {
        removed(vector3, blockType, cause, key, null);
    }
}
