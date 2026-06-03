# 2026-06-01 Prompt Fixes

## Prompt Provided

```text
Fix the following issues in the zip, add the index markdown to the zip, add a changelog folder, in the changelog folder include a new file with the prompt provided and your proposed solution including structure similar to the index (adding new files to the index if you create them in addition to this changelog file). Provide in your response the updated zip, the contents of the change file and any other comments you'd like to relay to me.

- Easy method to color the orb
- Three particles / streams
- Support 1.20.x and 1.21.x Forge
- Do not block based on Forge version, feature availability throws errors
- Fix missing particles above entities
```

## Proposed Solution Implemented

### Easy method to color the orb

- Replaced the previous custom-name marker approach with `EssenceColorData`.
- `Essence.color(int rgb)` and `Essence.color(EssenceColor color)` remain the simple caller-facing API.
- Color is now stored with an Essence-specific scoreboard-style entity tag and persistent entity data instead of overwriting the orb display name.
- Existing trail color is updated when an already-tracked Essence orb is recolored on the server.

### Three particles / streams

- Changed the trail schedule from continuous per-tick stream emission to three staggered particle spawns.
- Each stream now spawns one particle at the top of the entity at its scheduled point.
- The particles are staggered by 10 ticks, matching the requested half-second spacing at 20 TPS.
- The particle is emitted with upward velocity intended to travel roughly two blocks over the configured rise duration.

### Support 1.20.x and 1.21.x Forge

- Kept the two-build layout: root project for 1.20.x and `variants/forge-1.21.11` for 1.21.x.
- Bumped the mod version to `0.2.2` in both Gradle property files.
- Kept separate GitHub Actions matrix builds so each line can compile against its own Forge/Minecraft toolchain.

### Do not block based on Forge version, feature availability throws errors

- Relaxed the root Forge dependency range from `[47,48)` to `[47,)`.
- Left the 1.21.x Forge dependency range as `[61,)`.
- Minecraft version ranges still intentionally constrain the builds to their relevant Minecraft line: `[1.20,1.21)` and `[1.21,1.22)`.

### Fix missing particles above entities

- Kept entity join tracking.
- Added periodic loaded-entity discovery every 20 ticks to catch entities that were already loaded or missed the join event path.
- Kept leave-event cleanup and removed stale tracked entities when they are removed.

## Files Added

```text
EssenceThiefRPG-project-index.md
changelog/
└── 2026-06-01-prompt-fixes.md
src/main/java/com/akitaattribute/essencethief/api/EssenceColorData.java
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/api/EssenceColorData.java
```

## Files Changed

```text
gradle.properties
src/main/java/com/akitaattribute/essencethief/api/Essence.java
src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
src/main/java/com/akitaattribute/essencethief/trail/TrailSchedule.java
src/main/resources/META-INF/mods.toml
src/test/java/com/akitaattribute/essencethief/trail/TrailScheduleTest.java
variants/forge-1.21.11/gradle.properties
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/api/Essence.java
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/client/ColorableExperienceOrbRenderer.java
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/TrailSchedule.java
variants/forge-1.21.11/src/main/resources/META-INF/mods.toml
variants/forge-1.21.11/src/test/java/com/akitaattribute/essencethief/trail/TrailScheduleTest.java
```

## Updated Structure Index

```text
EssenceThiefRPG-codex-create-minecraft-mod-for-custom-essence-xp-orb/
├── EssenceThiefRPG-project-index.md
├── changelog/
│   └── 2026-06-01-prompt-fixes.md
├── src/main/java/com/akitaattribute/essencethief/api/EssenceColorData.java
│   ├── {apply(orb+ExperienceOrb, color+EssenceColor)}
│   ├── {read(orb+ExperienceOrb)}
│   ├── {read(entity+Entity)}
│   ├── {tagFor(color+EssenceColor)}
│   ├── {readFromTags(entity+Entity)}
│   └── {stripExistingEssenceTags(entity+Entity)}
└── variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/api/EssenceColorData.java
    ├── {apply(orb+ExperienceOrb, color+EssenceColor)}
    ├── {read(orb+ExperienceOrb)}
    ├── {read(entity+Entity)}
    ├── {tagFor(color+EssenceColor)}
    ├── {readFromTags(entity+Entity)}
    └── {stripExistingEssenceTags(entity+Entity)}
```

## Notes

- I could not run a local Gradle compile in this environment because `gradle` is not installed and this repository does not include a Gradle wrapper.
- The GitHub Actions workflow remains the expected compile validation path.
- The particle behavior is now closer to the literal prompt, but Minecraft particle lifetime and velocity behavior can still vary visually by client settings and particle implementation.
