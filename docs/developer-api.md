---
sidebar_position: 6
---

# Developer API

The checks of Schematic Sanitizer are available to your own plugin through the `api` module. You can check a
schematic file, read the report and write a cleaned copy, with your own rules.

The full Javadocs are at [eldoriarpg.github.io/schematic-sanitizer](https://eldoriarpg.github.io/schematic-sanitizer/).

## Add the dependency

The API is published on EldoNexus.

| | |
|---|---|
| Repository | `https://eldonexus.de/repository/maven-public/` |
| Group | `de.eldoria.schematic-sanitizer` |
| Artifact | `api` |
| Version | `1.0.9` |

**Gradle (Kotlin)**

```kotlin
repositories {
    maven("https://eldonexus.de/repository/maven-public/")
}

dependencies {
    compileOnly("de.eldoria.schematic-sanitizer:api:1.0.9")
}
```

**Maven**

```xml
<repository>
    <id>EldoNexus</id>
    <url>https://eldonexus.de/repository/maven-public/</url>
</repository>

<dependency>
    <groupId>de.eldoria.schematic-sanitizer</groupId>
    <artifactId>api</artifactId>
    <version>1.0.9</version>
    <scope>provided</scope>
</dependency>
```

The Schematic Sanitizer plugin jar contains the API. With `compileOnly`, install the plugin on the server and add it
as a dependency in your `plugin.yml`:

```yaml
depend:
  - SchematicSanitizer
```

The API needs Paper and FastAsyncWorldEdit, like the plugin.

## Build your rules

`Settings` holds the rules. `Settings.DEFAULT` gives you the same defaults the plugin writes into
`filter_settings.yml` (see [Configuration](./configuration.md)). To build your own, use `Settings.builder()`:

```java
Settings settings = Settings.builder()
        .filter(filter -> filter
                .blockFilter(blocks -> blocks
                        .withMaterialBlacklist(Material.COMMAND_BLOCK, Material.STRUCTURE_BLOCK))
                .entityFilter(entities -> entities
                        .removeCreature(true)
                        .removeNonCreatures(false)
                        .withEntityBlacklist(EntityTypes.COMMAND_BLOCK_MINECART))
                .withTextBlacklist("clickEvent", "run_command")
                .withNbtBlacklist("Items", "LootTable"))
        .limit(limit -> limit
                .size(600)
                .contentLimit(content -> content
                        .blocks(50000)
                        .nonAirBlocks(40000)
                        .creatures(50)
                        .nonCreatures(600)))
        .build();
```

`Material` is Bukkit's `org.bukkit.Material`. `EntityTypes` is `com.sk89q.worldedit.world.entity.EntityTypes`.

A new builder starts with empty blacklists and with both entity removal switches off. Its limits start at size `600`,
`50000` blocks, `50000` non-air blocks, `50` creatures and `600` non-creatures.

## Check or fix a schematic

```java
Path schematic = getDataFolder().toPath().resolve("castle.schem");
Sanitizer sanitizer = Sanitizer.create(schematic, settings);

SanitizerReport report = sanitizer.check();
```

`Sanitizer.create` throws a `FileNotFoundException` when the file does not exist. `check` and the `fix` methods throw
an `IOException` when the file cannot be read, for example because it is not a schematic.

To save a cleaned copy, use one of the `fix` methods. Each returns the same kind of report as `check`.

| Method | Where the clean schematic goes |
|---|---|
| `fix()` | Overwrites the original file. |
| `fix(String newName)` | A file with that name in the same folder. `.schem` is added unless the name ends in `.schem` or `.schematic`. |
| `fix(Path newPath)` | Exactly that path. |

The clean schematic is always written in FastAsyncWorldEdit's own `.schem` format.

Reading and cleaning a large schematic takes time. The plugin runs these calls off the main thread, and you should
do the same.

## Read the report

`SanitizerReport` is a record:

| Part | Type | What it holds |
|---|---|---|
| `path()` | `Path` | The file that was checked. |
| `newPath()` | `Path` or `null` | The file that was written by a fix. |
| `blocks()` | `BlockReport` | Removed blocks as `RemovedBlock`. |
| `blocksNbt()` | `BlockNbtReport` | Removed block data tags as `RemovedBlockNbt`. |
| `entities()` | `EntityReport` | Removed entities as `RemovedEntity`. |
| `entitiesNbt()` | `EntityNbtReport` | Removed entity data tags as `RemovedEntityNbt`. |
| `limits()` | `LimitReport` | The longest side (`maxSize()`) and the counted content (`content()`). |

Each list report has `size()`, `isEmpty()` and `entities()` for the entries. Each entry has a location, a type and a
`removalCause()`. The data tag entries also have the `tag()` and, for text matches, its `value()`.

```java
getLogger().info("Removed %d blocks".formatted(report.blocks().size()));

for (RemovedBlock block : report.blocks().entities()) {
    getLogger().info("Removed %s at %s: %s".formatted(block.type(), block.location(), block.removalCause()));
}

for (RemovedEntity entity : report.entities().entities()) {
    getLogger().info("Removed %s at %s: %s".formatted(entity.type().getType(), entity.location(), entity.removalCause()));
}
```

`limits().content()` gives the counted `blocks()`, `nonAirBlocks()`, `creatures()`, `nonCreatures()`,
`totalCreatures()` and `totalNonCreatures()`. Compare them with your `Settings` to see which limits a schematic goes
over. Only the creature and non-creature limits remove anything.
