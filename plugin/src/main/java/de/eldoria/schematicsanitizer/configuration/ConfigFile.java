/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.configuration;

public class ConfigFile {
    private boolean updateCheck = true;

    /**
     * @return true when the plugin should check Lyna for new versions and notify admins
     */
    public boolean updateCheck() {
        return updateCheck;
    }
}
