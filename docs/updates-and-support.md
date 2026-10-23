---
sidebar_position: 5
---

# Updates and support

## Update check

When `updateCheck` in `config.yml` is `true`, the plugin asks [Lyna](https://lyna.eldoria.de/products/32) whether a
newer version exists. It checks right after the server starts and then every 6 hours.

When a new version is out, the console shows a short note with the new version, your version and when each was
published. Set `updateCheck: false` to turn the check off.

## Debug reports

If something goes wrong and you ask for help, a debug report shows the helper what your server looks like.
Run:

```text
/schematicsanitizer debug
```

You need `schematicsanitizer.admin` or `eldoutilitites.debug`.

The first time, the plugin explains what will be uploaded and asks you to agree. Run the command again to agree and
upload. You are asked only once. Your answer is saved in `plugins/EldoUtilities/config.yml`.

The report contains:

- your installed plugins and their versions,
- the latest server log,
- server details such as world names and the player count,
- the configuration files of Schematic Sanitizer,
- the installed FastAsyncWorldEdit version and the path of the schematic folder,
- the health checks below.

IP addresses, passwords, tokens and similar values are hidden before the upload. The plugin cannot promise to catch
every value you consider private, so look at the report before you share it widely.

After the upload you get two links:

- **Read.** Give this link to whoever helps you. Everyone with the link can read the report.
- **Delete.** Removes the report right away.

Reports are deleted after two weeks on their own.

To take back your agreement, set `debugConsent: false` in that file.

## Health checks

The debug report runs these checks:

| Check | OK | Problem |
|---|---|---|
| FastAsyncWorldEdit | Installed and enabled. Shows its version. | Error: not installed or not enabled. |
| Schematic folder | The folder exists and can be read. Shows its path. | Warning: the folder does not exist. Error: the folder cannot be read. |

## Getting help

Use `/schematicsanitizer about` to see your plugin version and the links to the project and the support Discord.
