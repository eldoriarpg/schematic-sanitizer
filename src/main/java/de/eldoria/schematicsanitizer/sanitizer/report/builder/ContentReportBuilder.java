package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import de.eldoria.schematicsanitizer.sanitizer.CreatureType;
import de.eldoria.schematicsanitizer.sanitizer.report.ContentReport;
import org.bukkit.entity.Creature;

public class ContentReportBuilder {
    private int blocks;
    private int nonAirBlocks;
    private int totalCreatures;
    private int totalNonCreatures;
    private int creatures;
    private int nonCreatures;

    public void countBlock() {
        blocks++;
    }
    public int blocks() {
        return blocks;
    }

    public int creatures() {
        return totalCreatures;
    }

    public int nonCreatures() {
        return totalNonCreatures;
    }

    public int nonAirBlocks() {
        return nonAirBlocks;
    }

    public void countNonAirBlock() {
        nonAirBlocks++;
    }

    public void countTotalEntity(CreatureType type) {
        switch (type) {
            case CREATURE -> totalCreatures++;
            case NON_CREATURE -> totalNonCreatures++;
            case UNKNOWN -> {
            }
        }
    }
    public void countEntity(CreatureType type) {
        switch (type) {
            case CREATURE -> creatures++;
            case NON_CREATURE -> nonCreatures++;
            case UNKNOWN -> {
            }
        }
    }

    public ContentReport build() {
        return new ContentReport(blocks, nonAirBlocks, totalCreatures, totalNonCreatures, creatures, nonCreatures);
    }
}
