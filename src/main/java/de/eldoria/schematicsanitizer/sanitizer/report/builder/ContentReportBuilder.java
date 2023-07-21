/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.sanitizer.report.builder;

import de.eldoria.schematicsanitizer.sanitizer.CreatureType;
import de.eldoria.schematicsanitizer.sanitizer.report.ContentReport;


/**
 * The ContentReportBuilder class is responsible for constructing ContentReport objects and keeping track of various statistics related to the content.
 */

public class ContentReportBuilder {
    private int blocks;
    private int nonAirBlocks;
    private int totalCreatures;
    private int totalNonCreatures;
    private int creatures;
    private int nonCreatures;

    /**
     * Increments the value of the "blocks" variable by 1.
     */
    public void countBlock() {
        blocks++;
    }

    /**
     * Gets the value of blocks.
     *
     * @return The value of blocks.
     */
    public int blocks() {
        return blocks;
    }

    /**
     * Retrieves the total number of creatures.
     *
     * @return The total number of creatures.
     */
    public int creatures() {
        return totalCreatures;
    }

    /**
     * Returns the total count of non-creature objects.
     *
     * @return The total count of non-creature objects.
     */
    public int nonCreatures() {
        return totalNonCreatures;
    }

    /**
     * Returns the number of non-air blocks.
     *
     * @return The number of non-air blocks.
     */
    public int nonAirBlocks() {
        return nonAirBlocks;
    }

    /**
     * Increases the count of non-air blocks.
     * This method increments the nonAirBlocks variable by 1.
     */
    public void countNonAirBlock() {
        nonAirBlocks++;
    }

    /**
     * Increments the count for the total number of creatures or non-creatures based on
     * the given creature type.
     *
     * @param type The type of the creature.
     */
    public void countTotalEntity(CreatureType type) {
        switch (type) {
            case CREATURE -> totalCreatures++;
            case NON_CREATURE -> totalNonCreatures++;
            case UNKNOWN -> {
            }
        }
    }

    /**
     * Increases the count of a specific entity type.
     *
     * @param type The type of creature to count
     */
    public void countEntity(CreatureType type) {
        switch (type) {
            case CREATURE -> creatures++;
            case NON_CREATURE -> nonCreatures++;
            case UNKNOWN -> {
            }
        }
    }

    /**
     * Builds a ContentReport object with the given information.
     *
     * @return The built ContentReport object.
     */
    public ContentReport build() {
        return new ContentReport(blocks, nonAirBlocks, totalCreatures, totalNonCreatures, creatures, nonCreatures);
    }
}
