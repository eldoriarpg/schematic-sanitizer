/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) EldoriaRPG Team and Contributor
 */
package de.eldoria.schematicsanitizer.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TextTest {
    private static final String EVIL = "a'<click:run_command:'/op me'>b</click>\\'<red>c";

    @Test
    void escapedTextIsShownAsItIs() {
        Component component = MiniMessage.miniMessage().deserialize("<gray>" + Text.escape(EVIL));
        assertEquals(EVIL, plain(component));
        assertNoClick(component);
    }

    @Test
    void escapedTextStaysInsideAHover() {
        Component component = MiniMessage.miniMessage()
                .deserialize("<hover:show_text:'<gray>%s'>File</hover>".formatted(Text.quoted(Text.escape(EVIL))));
        assertEquals("File", plain(component));
        assertNoClick(component);
        HoverEvent<?> hover = component.children().isEmpty()
                ? component.hoverEvent()
                : component.children().getFirst().hoverEvent();
        assertEquals(EVIL, plain((Component) hover.value()));
    }

    @Test
    void quotedTextStaysInsideAClickCommand() {
        Component component = MiniMessage.miniMessage()
                .deserialize("<click:run_command:'/report load %s'>[Full report]</click>".formatted(Text.quoted(EVIL)));
        assertEquals("[Full report]", plain(component));
        Component clicked = component.children().isEmpty()
                ? component
                : component.children().getFirst();
        assertEquals("/report load " + EVIL, clicked.clickEvent().value());
    }

    private static String plain(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    private static void assertNoClick(Component component) {
        assertNull(component.clickEvent());
        component.children().forEach(TextTest::assertNoClick);
        if (component.hoverEvent() != null && component.hoverEvent().value() instanceof Component hover) {
            assertNoClick(hover);
        }
    }
}
