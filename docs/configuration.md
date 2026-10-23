---
sidebar_position: 4
---

# Configuration

The plugin keeps its files in `plugins/SchematicSanitizer`. They are created on the first start.

| File | What it holds |
|---|---|
| `filter_settings.yml` | The rules: what gets removed and the limits. |
| `config.yml` | General settings. |
| `base_configuration.yml` | The log level. |
| `entity_types.txt` | A list of every entity ID your server knows. Written fresh on each start. |

There is no reload command. Restart the server after you change a file.

:::warning Keep the files valid
If a file cannot be read, for example because of a typo in a block name, the plugin renames it to
`backup_<date>_<file name>` and writes a new file with the defaults. Check the console after a restart.
:::

## filter_settings.yml

This file decides what a check or a fix removes. With the default settings it looks like this. Your file may put
quotes around the values and list the entries in another order.

```yaml
filter:
  blockFilter:
    materialBlacklist:
    - COMMAND_BLOCK
    - REPEATING_COMMAND_BLOCK
    - CHAIN_COMMAND_BLOCK
    - STRUCTURE_BLOCK
  entityFilter:
    removeCreature: true
    removeNonCreatures: false
    entityBlacklist:
    - minecraft:command_block_minecart
    - minecraft:falling_block
  textBlacklist:
  - clickEvent
  - run_command
  nbtBlacklist:
  - LootTable
  - ArmorItem
  - ArmorItems
  - HandItem
  - HandItems
  - FireworksItem
  - Item
  - Items
  - DecorItem
  - Inventory
  - buy
  - buyB
  - sell
  - SaddleItem
limit:
  size: 600
  contentLimit:
    blocks: 50000
    nonAirBlocks: 40000
    creatures: 50
    nonCreatures: 600
```

### filter

| Key | Default | What it does |
|---|---|---|
| `blockFilter.materialBlacklist` | command block, repeating command block, chain command block, structure block | Blocks of these types are removed. Use Bukkit material names like `COMMAND_BLOCK`. |
| `entityFilter.removeCreature` | `true` | Remove every creature, like animals, monsters and villagers. |
| `entityFilter.removeNonCreatures` | `false` | Remove every non-creature, like armor stands, item frames, paintings, minecarts and boats. |
| `entityFilter.entityBlacklist` | `minecraft:command_block_minecart`, `minecraft:falling_block` | Entities of these types are removed. Use entity IDs as listed in `entity_types.txt`. |
| `textBlacklist` | `clickEvent`, `run_command` | A text data tag is removed when its value contains one of these texts. |
| `nbtBlacklist` | see the list above | Data tags with one of these names are removed, together with everything inside them. |

See [What gets removed](./checking-and-fixing.md#what-gets-removed) for how creatures and non-creatures are told
apart.

:::warning Turning on non-creature removal
When `removeNonCreatures` is `true`, creatures are removed as well, even if `removeCreature` is `false`. The report
lists them with the reason "Non Creature".
:::

#### What the defaults remove

- **Command blocks and structure blocks**, and minecarts with a command block.
- **Falling blocks.**
- **Text with `clickEvent` or `run_command`**, the markers of clickable commands, for example on signs.
- **Items stored in blocks.** `Items` empties chests, barrels, droppers, hoppers and other containers.
- **Items stored on entities**, through `Item`, `HandItems`, `ArmorItems`, `Inventory`, `DecorItem`, `SaddleItem` and
  `FireworksItem`. `Item` is, for example, the item in an item frame.
- **Loot tables** (`LootTable`), so a chest does not fill itself with loot when it is opened.
- **Villager trades** (`buy`, `buyB`, `sell`).
- **All creatures.**

If you want to keep container contents, take `Items` out of `nbtBlacklist`.

The default list also contains `minecraft:potion`. FastAsyncWorldEdit marks that ID as old, and a server that does not
know it leaves it out of the file. Newer servers use `minecraft:splash_potion` and `minecraft:lingering_potion`. Add
them to `entityBlacklist` if you want thrown potions removed.

### limit

| Key | Default | What it does |
|---|---|---|
| `size` | `600` | Largest allowed length of the longest side, in blocks. Only shown in the report. |
| `contentLimit.blocks` | `50000` | Largest allowed number of blocks, air included (length x width x height). Only shown in the report. |
| `contentLimit.nonAirBlocks` | `40000` | Largest allowed number of blocks that are not air. Only shown in the report. |
| `contentLimit.creatures` | `50` | Largest allowed number of creatures. Creatures above this number are removed. |
| `contentLimit.nonCreatures` | `600` | Largest allowed number of non-creatures. Non-creatures above this number are removed. |

## config.yml

```yaml
updateCheck: true
```

| Key | Default | What it does |
|---|---|---|
| `updateCheck` | `true` | Check for new versions. See [Update check](./updates-and-support.md#update-check). |

## base_configuration.yml

| Key | Default | What it does |
|---|---|---|
| `logLevel` | `INFO` | How much the plugin writes to the console. Other values: `OFF`, `SEVERE`, `WARNING`, `DEBUG`, `FINE`, `FINER`, `FINEST`, `ALL`. |

The file also holds `version` and `lastInstalledVersion`. The plugin manages these. Leave them as they are.
