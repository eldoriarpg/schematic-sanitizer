[![Release](https://img.shields.io/nexus/maven-releases/de.eldoria.schematic-sanitizer/api?label=Release&logo=Release&server=https%3A%2F%2Feldonexus.de&style=for-the-badge)][release]
[![Snapshot](https://img.shields.io/nexus/s/de.eldoria.schematic-sanitizer/api?color=orange&label=Snapshot&server=https%3A%2F%2Feldonexus.de&style=for-the-badge)][snapshot]

[release]: https://eldonexus.de/#browse/browse:maven-releases:de%2Feldoria%2Fschematic-sanitizer
[snapshot]: https://eldonexus.de/#browse/browse:maven-snapshots:de%2Feldoria%2Fschematic-sanitizer

# Schematic Sanitizer

Check schematic files for things you do not want on your server and remove them.

Point it at a schematic in your WorldEdit schematic folder and get a report of every block, entity and data tag that
breaks your rules. Write a clean copy right away, or clean a whole folder in one go.

- Remove command blocks, structure blocks and other blocks you name.
- Remove entities by type, all creatures or all non-creatures, or only those above a limit.
- Remove data tags by name, like chest contents and loot tables, and text with click commands.
- See the size and content of a schematic next to your limits.
- Use the same checks from your own plugin through the API.

Everything else is in the [documentation](https://docs.eldoria.de/schematic-sanitizer).

## Requirements

| What | Version |
|---|---|
| Server | Paper 1.21.11 or newer |
| Java | 25 |
| Other plugins | FastAsyncWorldEdit |
| Internet | On the first start, to download the libraries |

## Download

- [Modrinth](https://modrinth.com/plugin/fPX2XM5q)
- [Hangar](https://hangar.papermc.io/Eldoria/SchematicSanitizer)
- [Lyna](https://lyna.eldoria.de/products/32)

## Updating from 1.x

The update check setting is carried over to the new `updates` section of `config.yml`. The debug permission of
Eldoria plugins is now spelled `eldoutilities.debug`.

[![Cooperation-Label: Recommended and supported by RedstoneWorld](https://redstoneworld.de/bilder/kooperation/Cooperation-Label.png)](https://redstoneworld.de)

## License

The plugin is licensed under the [GNU Affero General Public License v3.0](LICENSE.md). The API module (`api/`) is
licensed under the [GNU Lesser General Public License v3.0 or later](api/LICENSE.md).
