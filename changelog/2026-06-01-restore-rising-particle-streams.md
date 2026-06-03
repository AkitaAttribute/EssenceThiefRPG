# 2026-06-01 Restore Rising Particle Streams

## Prompt Provided

```text
That fixed the build, but you have broken the "rising" effect that was previously happening with the particles. The particles are still there. They are still at the top of the entities, but they are not rising.
```

## Problem

The previous patch changed the trail behavior to spawn each stream particle only at the entity top and relied on particle velocity to create the rising motion.

That kept particles visible, but the chosen particle path did not reliably render the visible rising motion. The particles appeared at the top of entities but did not climb upward.

## Proposed Solution Implemented

- Restored position-based rising behavior instead of relying on particle velocity.
- Each of the three streams now remains active during its rise window.
- Each active stream emits a particle at the interpolated position for that tick.
- The particle starts at the top of the entity and moves upward by position over the configured two-block rise distance.
- The cone shape is preserved by widening each stream's horizontal offset as the progress value increases.
- The half-second stagger and half-second gap behavior remain controlled by `TrailSchedule`.
- Applied the same logic to both the 1.20.x root source and the 1.21.x variant source.
- Bumped `mod_version` from `0.2.2` to `0.2.3` in both Gradle property files.

## Files Changed

```text
EssenceThiefRPG-project-index.md
gradle.properties
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
changelog/2026-06-01-restore-rising-particle-streams.md
```

## Updated Structure Index

```text
EssenceThiefRPG-codex-create-minecraft-mod-for-custom-essence-xp-orb/
├── changelog/
│   └── 2026-06-01-restore-rising-particle-streams.md
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

This intentionally brings back the previous visual model: the server emits particles at progressively higher positions each tick. This is more reliable for the Dust particle behavior than spawning one particle at the entity top and expecting velocity to visibly carry it upward.
