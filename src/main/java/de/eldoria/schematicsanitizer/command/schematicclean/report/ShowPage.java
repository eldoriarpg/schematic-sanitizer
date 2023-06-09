package de.eldoria.schematicsanitizer.command.schematicclean.report;

import de.eldoria.eldoutilities.commands.command.AdvancedCommand;
import de.eldoria.eldoutilities.commands.command.CommandMeta;
import de.eldoria.eldoutilities.commands.command.util.Arguments;
import de.eldoria.eldoutilities.commands.exceptions.CommandException;
import de.eldoria.eldoutilities.commands.executor.ITabExecutor;
import de.eldoria.schematicsanitizer.command.schematicclean.Report;
import de.eldoria.schematicsanitizer.sanitizer.report.SanitizerReport;
import de.eldoria.schematicsanitizer.sanitizer.report.SizedReport;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ShowPage extends AdvancedCommand implements ITabExecutor {
    private final Report report;
    private final Function<SanitizerReport, SizedReport<?>> map;
    private static final int SIZE = 10;

    public ShowPage(Plugin plugin, String name, Report report, Function<SanitizerReport, SizedReport<?>> map) {
        super(plugin, CommandMeta.builder(name)
                .addUnlocalizedArgument("page", false)
                .build());
        this.report = report;
        this.map = map;
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull String alias, @NotNull Arguments args) throws CommandException {
        int page = args.asInt(0, 0);
        SanitizerReport sanitizerReport = report.get(sender);
        String component = sanitizerReport.pageComponent(map, page, SIZE);
        component = """
                <bold>Schematic Report</bold>
                %s
                %s
                """.stripIndent()
                .formatted(component.indent(2), getPageFooter(page, map.apply(sanitizerReport)));
        messageSender().sendMessage(sender, component);
    }

    private String getPageFooter(int page, SizedReport<?> report) {
        return (page == 0 ? "❮❮❮" : pageCommand(page - 1, "❮❮❮"))
                + " %s/%s ".formatted(page + 1, report.pages(SIZE))
                + (page + 1 == report.pages(SIZE) ? "❯❯❯" : pageCommand(page + 1, "❯❯❯"));
    }

    private String pageCommand(int page, String arrow) {
        return "<click:run_command:/schematicclean report showpage %s %d>%s</click>".formatted(meta().name(), page, arrow);
    }
}
