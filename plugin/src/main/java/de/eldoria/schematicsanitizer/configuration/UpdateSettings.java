/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) EldoriaRPG Team and Contributor
 */
package de.eldoria.schematicsanitizer.configuration;

/**
 * How the plugin looks for new versions: {@code notice} tells the console and players with the update permission,
 * {@code autoUpdate} downloads new versions on its own, and {@code askForAutoUpdate} offers admins to switch auto
 * update on from chat.
 */
@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class UpdateSettings {
    private boolean notice = true;
    private boolean autoUpdate = false;
    private boolean askForAutoUpdate = true;

    public boolean notice() {
        return notice;
    }

    public void notice(boolean notice) {
        this.notice = notice;
    }

    public boolean autoUpdate() {
        return autoUpdate;
    }

    public void autoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }

    public boolean askForAutoUpdate() {
        return askForAutoUpdate;
    }

    public void askForAutoUpdate(boolean askForAutoUpdate) {
        this.askForAutoUpdate = askForAutoUpdate;
    }
}
