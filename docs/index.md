---
title: Schematic Sanitizer
sidebar_position: 1
---

# Schematic Sanitizer

Schematic Sanitizer checks schematic files for things you do not want on your server and removes them. Think of
command blocks, structure blocks, chests full of items, signs with hidden click commands, or a few hundred mobs packed
into one build.

You point it at a schematic in your WorldEdit schematic folder. It gives you a report of everything it found. If you
like, it writes a clean copy of the file right away, or cleans a whole folder in one go.

## Why you want it

Schematics come from many places: players, build teams, downloads. A schematic can carry much more than blocks. It
can hold block data like chest contents, entities like armor stands and mobs, and text with click events. Pasting such
a file can hand out items, run commands or lag your server.

Schematic Sanitizer lets you look inside a file before you use it, and strip out what you do not trust.

## What it can do

- **Check a schematic.** Get a report of every block, entity and data tag that breaks your rules, and how the
  schematic compares to your size and content limits. See [Checking and fixing](./checking-and-fixing.md).
- **Fix a schematic.** Write a cleaned copy next to the original, or overwrite the original.
- **Fix a whole folder.** Clean every schematic in a folder and get a short report for each file.
- **Set your own rules.** Choose which blocks, entities, data tags and texts are removed. See
  [Configuration](./configuration.md).
- **Use it from your own plugin.** The same checks are available as an API. See
  [Developer API](./developer-api.md).

:::note It runs when you ask
Schematic Sanitizer works on files when you run one of its commands. It does not watch uploads, pastes or the
schematic folder on its own.
:::

## Requirements

| What | Version |
|---|---|
| Server | Paper 1.21.11 or newer |
| Java | 25 |
| FastAsyncWorldEdit | required |
| Internet | On the first start, to download the libraries |

Plain WorldEdit is not enough. The plugin needs FastAsyncWorldEdit.

## Installation

1. Download the plugin from [Lyna](https://lyna.eldoria.de/products/32),
   [Modrinth](https://modrinth.com/plugin/fPX2XM5q) or [Hangar](https://hangar.papermc.io/Eldoria/SchematicSanitizer).
2. Make sure FastAsyncWorldEdit is installed.
3. Put the Schematic Sanitizer jar into your `plugins` folder.
4. Start the server. The plugin creates its folder `plugins/SchematicSanitizer` with its configuration files.
5. Check the default rules in `filter_settings.yml` and change them if you like. Restart the server after a change.

## Where to go next

- Learn how a check and a fix work in [Checking and fixing](./checking-and-fixing.md).
- Look up a command or permission in [Commands and permissions](./commands.md).
- Change what gets removed in [Configuration](./configuration.md).
- Read about update notes and debug reports in [Updates and support](./updates-and-support.md).
