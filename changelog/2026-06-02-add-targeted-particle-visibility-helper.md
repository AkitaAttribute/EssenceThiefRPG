# 2026-06-02 Add Targeted Particle Visibility Helper

## Prompt Provided

```text
Interesting. That appears to mean we can make it so that only certain players see this particle trail. Why don't you define that for our helper method, and if no player list is passed in, do all
```

## Proposed Solution Implemented

- Added an overloaded `sendForcedParticle(...)` helper that accepts `Iterable<ServerPlayer> targetPlayers`.
- Kept the existing no-player-list helper signature.
- The no-player-list overload delegates to the targeted helper with `targetPlayers = null`.
- The targeted helper sends forced particles only to the supplied players.
- If `targetPlayers` is `null`, the helper sends to all players in the level.
- Added an overloaded `track(Entity, EssenceColor, Iterable<ServerPlayer>)` method so trails can be registered for a specific visible-player set.
- `TrackedTrail` stores visible player UUIDs rather than direct `ServerPlayer` references, avoiding stale player object references.
- Existing `track(Entity, EssenceColor)` behavior remains unchanged and still sends particles to all players.
- `updateColorIfTracked(...)` now preserves the existing visible-player target set.
- Bumped `mod_version` to `0.2.10` in both Gradle property files.

## Files Changed

```text
EssenceThiefRPG-project-index.md
gradle.properties
src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
variants/forge-1.21.11/gradle.properties
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
```

## Updated Structure Index

```text
EssenceThiefRPG-codex-create-minecraft-mod-for-custom-essence-xp-orb/
├── changelog/
│   └── 2026-06-02-add-targeted-particle-visibility-helper.md
├── src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
│   ├── {track(entity+Entity, color+EssenceColor)}
│   ├── {track(entity+Entity, color+EssenceColor, targetPlayers+Iterable<ServerPlayer>)}
│   ├── {updateColorIfTracked(entity+Entity, color+EssenceColor)}
│   ├── {toVisiblePlayerIds(targetPlayers+Iterable<ServerPlayer>)}
│   ├── {visiblePlayers(level+ServerLevel, visiblePlayerIds+Set<UUID>)}
│   ├── {sendForcedParticle(level+ServerLevel, particle+T, x+double, y+double, z+double, count+int, xOffset+double, yOffset+double, zOffset+double, speed+double)}
│   └── {sendForcedParticle(level+ServerLevel, targetPlayers+Iterable<ServerPlayer>, particle+T, x+double, y+double, z+double, count+int, xOffset+double, yOffset+double, zOffset+double, speed+double)}
└── variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
    ├── {track(entity+Entity, color+EssenceColor)}
    ├── {track(entity+Entity, color+EssenceColor, targetPlayers+Iterable<ServerPlayer>)}
    ├── {updateColorIfTracked(entity+Entity, color+EssenceColor)}
    ├── {toVisiblePlayerIds(targetPlayers+Iterable<ServerPlayer>)}
    ├── {visiblePlayers(level+ServerLevel, visiblePlayerIds+Set<UUID>)}
    ├── {sendForcedParticle(level+ServerLevel, particle+T, x+double, y+double, z+double, count+int, xOffset+double, yOffset+double, zOffset+double, speed+double)}
    └── {sendForcedParticle(level+ServerLevel, targetPlayers+Iterable<ServerPlayer>, particle+T, x+double, y+double, z+double, count+int, xOffset+double, yOffset+double, zOffset+double, speed+double)}
```

## Notes

This exposes the visibility concept at both levels:

- Low-level helper: send one forced particle event to either all players or a supplied player list.
- Trail registration: register a trail that is visible only to specific players.

The current automatic tracking path still uses all players because it calls `track`/`autoTrack` without a target-player list.
