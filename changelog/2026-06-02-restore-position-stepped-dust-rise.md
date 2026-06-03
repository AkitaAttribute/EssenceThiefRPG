# 2026-06-02 Restore Position-Stepped Dust Rise

## Prompt Provided

```text
Why didn't you provide an updated zip
```

## Context

The prior response correctly diagnosed why vanilla dust particles were not reproducing the original upward movement through velocity alone, but it failed to provide an updated project zip.

Dust particles keep the desired visual style, but they do not reliably honor server-provided directional velocity. The original working behavior was a position-stepped dust trail: the server emits dust particles at progressively higher Y positions while the stream is active.

## Proposed Solution Implemented

- Restored position-stepped dust particle movement for both the 1.20.x root build and the 1.21.x variant.
- Kept dust particles as the visual particle type.
- Kept the three staggered streams.
- Removed velocity reliance from the active trail emission path.
- Particles now spawn at the entity top plus `TrailSchedule.RISE_BLOCKS * progress`.
- X and Z remain fixed at the entity center so the motion is exclusively upward.
- Bumped `mod_version` to `0.2.8` in both Gradle property files.

## Files Changed

```text
EssenceThiefRPG-project-index.md
gradle.properties
src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
variants/forge-1.21.11/gradle.properties
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
```

## Files Added

```text
changelog/2026-06-02-restore-position-stepped-dust-rise.md
```

## Updated Structure Index

```text
EssenceThiefRPG-codex-create-minecraft-mod-for-custom-essence-xp-orb/
├── changelog/
│   └── 2026-06-02-restore-position-stepped-dust-rise.md
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
└── variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
    ├── {track(entity+Entity, color+EssenceColor)}
    ├── {updateColorIfTracked(entity+Entity, color+EssenceColor)}
    ├── {untrack(entity+Entity)}
    ├── {onEntityJoin(event+EntityJoinLevelEvent)}
    ├── {onEntityLeave(event+EntityLeaveLevelEvent)}
    ├── {onLevelTick(event+TickEvent.LevelTickEvent.Post)}
    ├── {discoverLoadedEntities(level+ServerLevel)}
    ├── {autoTrack(entity+Entity)}
    └── TrackedTrail
        └── {tick(tickingLevel+ServerLevel, gameTick+int)}
```

## Notes

This intentionally chooses visual correctness with dust particles over true velocity-based movement. Vanilla dust particles are not a reliable particle type for velocity-driven motion, so the stable implementation is to move the spawn position upward over time.
