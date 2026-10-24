---
sidebar_position: 3
---

# Commands and permissions

## Commands

The base command is `/schematicsanitizer`. You can also use `/schemsan` or `/sanitizer`.
All commands work for players and from the console.

| Command | Permission | What it does |
|---|---|---|
| `/schematicsanitizer check <schematic>` | `schematicsanitizer.check.use` | Check a schematic and show a report. |
| `/schematicsanitizer fix <schematic> [new name] [-o]` | `schematicsanitizer.fix.use` | Check a schematic and save a cleaned copy. |
| `/schematicsanitizer fixbatch <directory> [-o]` | `schematicsanitizer.fix.batch` | Fix every schematic in a folder. |
| `/schematicsanitizer report` | one of the three above | Show your latest report again. |
| `/schematicsanitizer report page <list> [page]` | one of the three above | Page through a list in your latest report. |
| `/schematicsanitizer report load <path>` | one of the three above | Open the full report of a file from a batch. |
| `/schematicsanitizer debug` | `schematicsanitizer.admin` | Upload a debug report for support. |
| `/schematicsanitizer about` | none | Show the plugin version and links. |
| `/schematicsanitizer:eldo-update <action>` | `schematicsanitizer.updates` | Download a new version or decide about auto update. |

`/schematicsanitizer` on its own lists the sub-commands.

### check

```text
/schematicsanitizer check <schematic>
```

| Argument | Required | What it means |
|---|---|---|
| `schematic` | yes | Path of the file inside the schematic folder, with its extension. Write spaces as `\_`. |

The file is not changed. See [Check](./checking-and-fixing.md#check).

### fix

```text
/schematicsanitizer fix <schematic> [new name] [-o]
```

| Argument | Required | What it means |
|---|---|---|
| `schematic` | yes | Path of the file inside the schematic folder, with its extension. Write spaces as `\_`. |
| `new name` | no | Name of the clean copy, saved in the same folder. It must be a plain file name, without folders. Without it the copy is called `<name>_new.schem`. |
| `-o` | no | Overwrite the original file. Put it at the end. |

See [Fix](./checking-and-fixing.md#fix).

### fixbatch

```text
/schematicsanitizer fixbatch <directory> [-o]
```

| Argument | Required | What it means |
|---|---|---|
| `directory` | yes | Path of a folder inside the schematic folder. Write spaces as `\_`. |
| `-o` | no | Overwrite the original files. Put it at the end. |

Without `-o` the clean files go into `<directory>_new`. See
[Fix a whole folder](./checking-and-fixing.md#fix-a-whole-folder).

### report

```text
/schematicsanitizer report
/schematicsanitizer report page <list> [page]
/schematicsanitizer report load <path>
```

| Argument | Required | What it means |
|---|---|---|
| `list` | yes | Which list to show: `blocks`, `blocks_nbt`, `entities` or `entities_nbt`. |
| `page` | no | Page number, counted from `0`. Each page shows 10 entries. Default is `0`. |
| `path` | yes | Path of a file from a batch, as offered by tab completion. |

You rarely need to type these. The **[Show]**, arrow and **[Full report]** buttons in chat run them for you.
See [The report](./checking-and-fixing.md#the-report).

### debug

Uploads a debug report and shows you a link to it. See [Debug reports](./updates-and-support.md#debug-reports).

### eldo-update

```text
/schematicsanitizer:eldo-update <download|enable-auto|later|never>
```

This command belongs to the update offer in chat. You normally use it by clicking an option in that offer. Only
the console, operators and players with `schematicsanitizer.updates` can use it. It exists once a new version was
found.

| Action | What it does |
|---|---|
| `download` | Downloads the new version into the server's update folder. It is installed on the next restart. A new major version asks you to confirm with `download confirm`. |
| `enable-auto` | Turns on `updates.autoUpdate`. |
| `later` | Asks again after the next manual update. |
| `never` | Stops asking. This sets `updates.askForAutoUpdate` to `false`. |

See [Update check](./updates-and-support.md#update-check).

## Permissions

| Permission | What it allows |
|---|---|
| `schematicsanitizer.check.use` | Use `check` and `report`. |
| `schematicsanitizer.fix.use` | Use `fix` and `report`. |
| `schematicsanitizer.fix.batch` | Use `fixbatch` and `report`. |
| `schematicsanitizer.admin` | Use `debug`. |
| `schematicsanitizer.updates` | Get the update notice on join and use `eldo-update`. |
| `schematicsanitizer.*` | All of the above. |
| `eldoutilities.debug` | Use `debug`. This is a shared permission of Eldoria plugins. |

All permissions are given to operators by default. Everyone else needs them from your permission plugin.

:::warning Files on your server
`fix` and `fixbatch` write files on your server. Give these permissions only to staff you trust.
:::
