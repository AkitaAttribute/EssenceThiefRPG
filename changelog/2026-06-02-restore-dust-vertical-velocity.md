# 2026-06-02 Restore Dust Particles With Vertical-Only Velocity

## Prompt Provided

```text
So no more crashing, but for some reason you decided to do a potion effect particle rather than the dust particle in the original zip, and also the particles are not going exclusively up, like I defined.
```

## Problem

The 1.21.x build fix switched the trail from the requested dust particle style to `ColorParticleOption` / `ParticleTypes.ENTITY_EFFECT`. That avoided the custom particle compile failure, but it changed the visual particle type away from the original dust trail.

The velocity-based implementation also included horizontal velocity components to simulate a cone. That caused particles to move outward instead of moving exclusively upward.

## Proposed Solution Implemented

- Restored dust particles as the trail particle type.
- Root 1.20.x uses `DustParticleOptions(new Vector3f(red, green, blue), 1.0F)`, matching the original 1.20.x dust option style.
- 1.21.x uses `DustParticleOptions(color.rgb(), 1.0F)`, matching the original 1.21.x dust option style.
- Removed horizontal velocity from trail emission.
- `ServerLevel#sendParticles` now sends `xVelocity = 0.0D`, `zVelocity = 0.0D`, and only uses the upward velocity from `TrailSchedule.upwardVelocityPerTick()`.
- Removed `horizontalVelocityPerTick(...)` because the trail should not drift sideways.
- Removed custom particle registration from the 1.20.x mod constructor because the trail no longer uses the custom `essencethief:essence_rising` particle.
- Kept 1.21.x stale-file placeholders from the previous build fix so older copied-over files do not reintroduce the custom-particle compile failure.
- Bumped `mod_version` to `0.2.7` in both Gradle property files.

## Files Changed

```text
EssenceThiefRPG-project-index.md
gradle.properties
src/main/java/com/akitaattribute/essencethief/EssenceThiefMod.java
src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
src/main/java/com/akitaattribute/essencethief/trail/TrailSchedule.java
src/test/java/com/akitaattribute/essencethief/trail/TrailScheduleTest.java
variants/forge-1.21.11/gradle.properties
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/TrailSchedule.java
variants/forge-1.21.11/src/test/java/com/akitaattribute/essencethief/trail/TrailScheduleTest.java
```

## Files Added

```text
changelog/2026-06-02-restore-dust-vertical-velocity.md
```

## Updated Structure Index

```text
EssenceThiefRPG-codex-create-minecraft-mod-for-custom-essence-xp-orb/
├── changelog/
│   └── 2026-06-02-restore-dust-vertical-velocity.md
├── src/main/java/com/akitaattribute/essencethief/EssenceThiefMod.java
│   └── {EssenceThiefMod()}
├── src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
│   ├── {track(entity+Entity, color+EssenceColor)}
│   ├── {updateColorIfTracked(entity+Entity, color+EssenceColor)}
│   ├── {untrack(entity+Entity)}
│   ├── {onEntityJoin(event+EntityJoinLevelEvent)}
│   ├── {onEntityLeave(event+EntityLeaveLevelEvent)}
│   ├── {onLevelTick(event+TickEvent.LevelTickEvent)}
│   ├── {discoverLoadedEntities(level+ServerLevel)}
│   ├── {autoTrack(entity+Entity)}
│   └── TrackedTrail
│       └── {tick(tickingLevel+ServerLevel, gameTick+int)}
├── src/main/java/com/akitaattribute/essencethief/trail/TrailSchedule.java
│   ├── {shouldSpawn(gameTick+int, stream+int)}
│   ├── {isRising(gameTick+int, stream+int)}
│   ├── {progress(gameTick+int, stream+int)}
│   ├── {upwardVelocityPerTick()}
│   └── {validateStream(stream+int)}
├── variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
│   ├── {track(entity+Entity, color+EssenceColor)}
│   ├── {updateColorIfTracked(entity+Entity, color+EssenceColor)}
│   ├── {untrack(entity+Entity)}
│   ├── {onEntityJoin(event+EntityJoinLevelEvent)}
│   ├── {onEntityLeave(event+EntityLeaveLevelEvent)}
│   ├── {onLevelTick(event+TickEvent.LevelTickEvent.Post)}
│   ├── {discoverLoadedEntities(level+ServerLevel)}
│   ├── {autoTrack(entity+Entity)}
│   └── TrackedTrail
│       └── {tick(tickingLevel+ServerLevel, gameTick+int)}
└── variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/TrailSchedule.java
    ├── {shouldSpawn(gameTick+int, stream+int)}
    ├── {isRising(gameTick+int, stream+int)}
    ├── {progress(gameTick+int, stream+int)}
    ├── {upwardVelocityPerTick()}
    └── {validateStream(stream+int)}
```

## Notes

This patch intentionally prioritizes the prompt requirements over the earlier cone-spread interpretation:

- Dust particle appearance is restored.
- Velocity-based spawning is preserved.
- Motion is vertical only.
- There is no horizontal cone drift.
