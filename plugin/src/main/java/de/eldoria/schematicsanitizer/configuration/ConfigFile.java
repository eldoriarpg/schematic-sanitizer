/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) EldoriaRPG Team and Contributor
 */
package de.eldoria.schematicsanitizer.configuration;

import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * The content of {@code config.yml}. The switch {@code updateCheck} of 1.x is read once into {@code updates.notice}
 * and not written back.
 */
@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class ConfigFile {
    private UpdateSettings updates = new UpdateSettings();

    public UpdateSettings updates() {
        return updates;
    }

    @JsonSetter("updateCheck")
    private void legacyUpdateCheck(boolean updateCheck) {
        updates.notice(updateCheck);
    }
}
