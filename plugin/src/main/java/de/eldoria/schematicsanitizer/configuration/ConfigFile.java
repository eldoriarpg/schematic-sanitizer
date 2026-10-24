/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) EldoriaRPG Team and Contributor
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
