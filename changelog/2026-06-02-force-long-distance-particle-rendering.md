# 2026-06-02 Force Long-Distance Particle Rendering

## Prompt Provided

```text
That's a lot better. Is it possible to make the particles render further away? They seem to be only appearing at a fraction of my render distance
```

## Context

The trail particles were using the normal `ServerLevel#sendParticles(...)` overload. Minecraft's normal particle mode only sends particles to nearby players, which can make particles disappear well before the player's configured render distance.

Minecraft's particle command has a `force` display mode that expands the send distance from normal nearby particle range to a much larger forced particle range. The Forge/Minecraft server API exposes this through the per-player `ServerLevel#sendParticles(ServerPlayer, particle, force, ...)` overload.

## Proposed Solution Implemented

- Added a `sendForcedParticle(...)` helper to both the 1.20.x root build and the 1.21.x variant.
- The helper loops through `ServerLevel#players()` and calls the per-player particle overload with `force = true`.
- Kept the current dust particle appearance.
- Kept the position-stepped upward movement.
- Kept X/Z fixed at the entity center so the particles rise vertically.
- Bumped `mod_version` to `0.2.9` in both Gradle property files.

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
changelog/2026-06-02-force-long-distance-particle-rendering.md
```

## Updated Structure Index

```text
EssenceThiefRPG-codex-create-minecraft-mod-for-custom-essence-xp-orb/
├── changelog/
│   └── 2026-06-02-force-long-distance-particle-rendering.md
├── src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
│   ├── {track(entity+Entity, color+EssenceColor)}
│   ├── {updateColorIfTracked(entity+Entity, color+EssenceColor)}
│   ├── {untrack(entity+Entity)}
│   ├── {onEntityJoin(event+EntityJoinLevelEvent)}
│   ├── {onEntityLeave(event+EntityLeaveLevelEvent)}
│   ├── {onLevelTick(event+TickEvent.LevelTickEvent)}
│   ├── {discoverLoadedEntities(level+ServerLevel)}
│   ├── {autoTrack(entity+Entity)}
│   ├── {sendForcedParticle(level+ServerLevel, particle+T, x+double, y+double, z+double, count+int, xOffset+double, yOffset+double, zOffset+double, speed+double)}
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
    ├── {sendForcedParticle(level+ServerLevel, particle+T, x+double, y+double, z+double, count+int, xOffset+double, yOffset+double, zOffset+double, speed+double)}
    └── TrackedTrail
        └── {tick(tickingLevel+ServerLevel, gameTick+int)}
```

## Notes

This increases the particle send range, but it does not make unloaded entities/chunks exist on the client. The server must still have the entity loaded and ticking for the trail to be emitted.

Because this mod currently tracks many entities, forced particles can become expensive on busy servers. A later cleanup should add distance filtering, opt-in tracking, or a config toggle before this becomes a production-style feature.
