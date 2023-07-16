package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockType;
import de.eldoria.schematicsanitizer.sanitizer.report.BlockNbtReport;
import de.eldoria.schematicsanitizer.sanitizer.report.cause.NbtRemovalCause;
import de.eldoria.schematicsanitizer.sanitizer.report.entities.RemovedBlockNbt;

/**
 * Builds a BlockNbtReport containing information about removed block NBT data.
 */

public class BlockNbtReportBuilder extends BaseReportBuilder<RemovedBlockNbt> {
    /**
     * Logs removal of a block with the specified details.
     *
     * @param vector3   The coordinates of the block to be removed.
     * @param blockType The type of the block to be removed.
     * @param cause     The cause of the removal.
     * @param key       The key associated with the removal.
     * @param text      The additional text related to the removal.
     */
    public void removed(BlockVector3 vector3, BlockType blockType, NbtRemovalCause cause, String key, String text) {
        removed(new RemovedBlockNbt(vector3, blockType, cause, key, text));
    }

    /**
     * Logs removal of a block with the specified parameters.
     *
     * @param vector3   the position of the block in BlockVector3 format
     * @param blockType the type of the block to be removed
     * @param cause     the cause of the block removal
     * @param key       the key associated with the block (optional)
     */
    public void removed(BlockVector3 vector3, BlockType blockType, NbtRemovalCause cause, String key) {
        removed(vector3, blockType, cause, key, null);
    }

    /**
     * Build the BlockNbtReport object.
     *
     * @return The built BlockNbtReport object.
     */
    public BlockNbtReport build() {
        return new BlockNbtReport(removed());
    }
}
