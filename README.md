# Essence Thief RPG

A Forge mod API for creating colored **essence** XP orbs and attaching colored, rising particle trails to arbitrary entities.

## Compatibility strategy

The distributable is compiled against Minecraft **1.21.1** and Forge **52.1.0**, while `mods.toml` accepts the requested Minecraft **1.20.x** and **1.21.x** families through the Maven version range `[1.20,1.22)`. The Forge loader range also remains broad: the mod uses a reflective event-bus bridge for Forge API-shape differences and reports a descriptive feature-availability error if a Forge runtime cannot provide a required capability. Cross-version loading remains best-effort because Minecraft and Forge APIs can change between releases; the metadata intentionally does not reject a compatible-looking runtime solely because it is a later patch release.

## API examples

Create a vanilla XP orb wrapped as colored essence and give it a matching trail:

```java
Essence essence = Essence.create(level, x, y, z, 5)
    .color(0x9B5CFF)
    .createRisingTrail(0x9B5CFF);
```

Decorate any existing entity, including mobs and item drops:

```java
WrappedEntity<?> decorated = new WrappedEntity<>(entity)
    .createRisingTrail(new EssenceColor(255, 80, 20));
```

Remove a trail when it is no longer needed:

```java
decorated.removeRisingTrail();
```

Trail registration must happen on the logical server. The manager emits three colored streams from the top of the entity, staggers them by ten ticks (0.5 seconds), expands them into a cone while rising two blocks, and inserts a ten-tick gap before each stream repeats. Orb colors are encoded through vanilla-synchronized entity data so clients can render them without custom networking.

## Build

```bash
gradle build
```

The distributable JAR is written to `build/libs/` as a local build output and is intentionally not checked into source control. GitHub Actions installs Gradle, builds the mod, and uploads the standalone JAR directly as a workflow artifact (not inside an additional ZIP archive).
