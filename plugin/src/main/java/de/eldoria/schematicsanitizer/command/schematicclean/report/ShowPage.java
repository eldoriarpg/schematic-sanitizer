/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.eldoria.schematicsanitizer.command.schematicclean.report;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import de.eldoria.eldoutilities.commands.command.util.Arguments;
import de.eldoria.eldoutilities.commands.exceptions.CommandException;
import de.eldoria.eldoutilities.commands.executor.ITabExecutor;
import de.eldoria.schematicsanitizer.command.schematicclean.Report;
import de.eldoria.schematicsanitizer.rendering.ReportRenderer;
import de.eldoria.schematicsanitizer.rendering.subreports.sized.SizedReportRenderer;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ShowPage extends AdvancedCommand implements ITabExecutor {
    private static final int SIZE = 10;
    private final Report report;
    private final Function<ReportRenderer, SizedReportRenderer<?>> map;

    public ShowPage(Plugin plugin, String name, Report report, Function<ReportRenderer, SizedReportRenderer<?>> map) {
        super(plugin, CommandMeta.builder(name)
                .addUnlocalizedArgument("page", false)
                .build());
        this.report = report;
        this.map = map;
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull String alias, @NotNull Arguments args) throws CommandException {
        int page = args.asInt(0, 0);
        ReportRenderer sanitizerReport = report.get(sender);
        if (sanitizerReport == null) {
            throw CommandException.message("No report available");
        }
        SizedReportRenderer<?> sized = map.apply(sanitizerReport);
        if (sized.isEmpty()) {
            throw CommandException.message("No page available. 0 results.");
        }
        if (page < 0 || page > sized.pages(SIZE)) {
            throw CommandException.message("Invalid page.");
        }
        String component = sanitizerReport.pageComponent(map, page, SIZE);
        component = """
                <header>Schematic Report</header>
                %s
                %s
                """.stripIndent()
                .formatted(component.indent(2), getPageFooter(page, sized));
        messageSender().sendMessage(sender, component);
    }

    private String getPageFooter(int page, SizedReportRenderer<?> report) {
        String leftButton = page == 0 ? "<inactive>❮❮❮" : pageCommand(page - 1, "<interact>❮❮❮");
        String currentPage = " <default>%s/%s ".formatted(page + 1, report.pages(SIZE));
        String rightButton = page + 1 == report.pages(SIZE) ? "<inactive>❯❯❯" : pageCommand(page + 1, "<interact>❯❯❯");
        String backButton = "<click:run_command:/schematicsanitizer report><interact>[Back]</click>";
        return "%s %s %s\n%s".formatted(leftButton, currentPage, rightButton, backButton);
    }

    private String pageCommand(int page, String arrow) {
        return "<click:run_command:/schematicsanitizer report page %s %d>%s</click>".formatted(meta().name(), page, arrow);
    }
}
