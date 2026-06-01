# Essence Thief RPG

A Forge mod API for creating colored **essence** XP orbs and attaching colored, rising particle trails to arbitrary entities.

## Compatibility strategy

Minecraft `1.20.x` and `1.21.x` use different Minecraft, Forge, EventBus, Java, and ForgeGradle APIs. The repository therefore produces two standalone JARs instead of attempting to load one cross-generation binary:

| Artifact line | Compile target | Forge target | Java compile target | Gradle | Minecraft range | Required Forge range |
| --- | --- | --- | --- | --- | --- | --- |
| `essencethief-1.20.1-*.jar` | Minecraft `1.20.1` | Forge `47.4.20` | 17 | 8.8 | `[1.20,1.21)` | `[47,48)` |
| `essencethief-1.21.11-*.jar` | Minecraft `1.21.11` | Forge `61.1.7` | 21 | 9.3.1 | `[1.21,1.22)` | `[61,)` |

Use the JAR matching the installed Minecraft generation and Forge major line. The Forge dependency range prevents either artifact from loading on the incompatible event-bus generation. GitHub Actions compiles and uploads both standalone artifacts independently. No JAR is checked into source control.

The ForgeGradle 7 setup used by the `1.21.11` workspace runs Gradle under Java 25 while preserving the official MDK Java 21 compilation target. GitHub Actions installs both JDKs for that job.

Every server-side entity is enrolled automatically with `EssenceColor.DEFAULT`, including players, mobs, item drops, and vanilla XP orbs. The wrapper API remains available when a caller needs to override that color or remove an entity trail.

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

Each implementation emits three colored streams from the entity top, staggers them by ten ticks (0.5 seconds), expands them into a cone while rising two blocks, and inserts a ten-tick gap before each stream repeats.

## Build

Build the Minecraft `1.20.1` variant with Java 17 and Gradle 8.8:

```bash
gradle clean build
```

Build the Minecraft `1.21.11` variant with Java 21 and Gradle 9.3.1:

```bash
gradle -p variants/forge-1.21.11 clean build
```
