package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;
import de.eldoria.schematicsanitizer.sanitizer.report.BlockNbtReport;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.NbtRemovalCause;
import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedBlockNbt;

public class BlockNbtReportBuilder extends BaseReportBuilder<RemovedBlockNbt> {
    public void removed(BlockVector3 vector3, BlockType blockType, NbtRemovalCause cause, String key, String text) {
        removed(new RemovedBlockNbt(vector3, blockType, cause, key, text));
    }

    public void removed(BlockVector3 vector3, BlockType blockType, NbtRemovalCause cause, String key) {
        removed(vector3, blockType, cause, key, null);
    }

    public BlockNbtReport build() {
        return new BlockNbtReport(removed());
    }
}
