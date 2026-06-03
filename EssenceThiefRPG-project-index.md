# EssenceThiefRPG Project Index

Generated for future project navigation. Java code files list method-like entries beneath each file using `{method(param name+type)}` notation.

## File Structure

```text
├── .github
│   └── workflows
│       └── build.yml
├── changelog
│   ├── 2026-06-01-prompt-fixes.md
│   └── 2026-06-01-fix-1.21-stale-custom-particle-files.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── akitaattribute
│   │   │           └── essencethief
│   │   │               ├── api
│   │   │               │   ├── Essence.java
│   │   │               │   ├── EssenceColor.java
│   │   │               │   ├── EssenceColorData.java
│   │   │               │   └── WrappedEntity.java
│   │   │               ├── client
│   │   │               │   └── ColorableExperienceOrbRenderer.java
│   │   │               ├── trail
│   │   │               │   ├── EntityTrailManager.java
│   │   │               │   └── TrailSchedule.java
│   │   │               └── EssenceThiefMod.java
│   │   └── resources
│   │       ├── META-INF
│   │       │   └── mods.toml
│   │       └── pack.mcmeta
│   └── test
│       └── java
│           └── com
│               └── akitaattribute
│                   └── essencethief
│                       ├── api
│                       │   └── EssenceColorTest.java
│                       └── trail
│                           └── TrailScheduleTest.java
├── variants
│   └── forge-1.21.11
│       ├── src
│       │   ├── main
│       │   │   ├── java
│       │   │   │   └── com
│       │   │   │       └── akitaattribute
│       │   │   │           └── essencethief
│       │   │   │               ├── api
│       │   │   │               │   ├── Essence.java
│       │   │   │               │   ├── EssenceColor.java
│       │   │   │               │   ├── EssenceColorData.java
│       │   │   │               │   └── WrappedEntity.java
│       │   │   │               ├── client
│       │   │   │               │   └── ColorableExperienceOrbRenderer.java
│       │   │   │               ├── trail
│       │   │   │               │   ├── EntityTrailManager.java
│       │   │   │               │   └── TrailSchedule.java
│       │   │   │               └── EssenceThiefMod.java
│       │   │   └── resources
│       │   │       ├── META-INF
│       │   │       │   └── mods.toml
│       │   │       └── pack.mcmeta
│       │   └── test
│       │       └── java
│       │           └── com
│       │               └── akitaattribute
│       │                   └── essencethief
│       │                       ├── api
│       │                       │   └── EssenceColorTest.java
│       │                       └── trail
│       │                           └── TrailScheduleTest.java
│       ├── build.gradle
│       ├── gradle.properties
│       └── settings.gradle
├── .gitignore
├── build.gradle
├── EssenceThiefRPG-project-index.md
├── gradle.properties
├── LICENSE
├── README.md
└── settings.gradle
```

## Code Method Index

### `src/main/java/com/akitaattribute/essencethief/EssenceThiefMod.java`
- {EssenceThiefMod()}

### `src/main/java/com/akitaattribute/essencethief/api/Essence.java`
- {Essence(orb+ExperienceOrb)}
- {create(level+Level, x+double, y+double, z+double, value+int)}
- {color(rgb+int)}
- {color(color+EssenceColor)}
- {color()}
- {createRisingTrail(color+EssenceColor)}
- {createRisingTrail(rgb+int)}
- {removeRisingTrail()}
- {readColor(orb+ExperienceOrb)}
- {syncTrailColor(orb+ExperienceOrb, color+EssenceColor)}

### `src/main/java/com/akitaattribute/essencethief/api/EssenceColor.java`
- {EssenceColor(red+int, green+int, blue+int)}
- {fromRgb(rgb+int)}
- {rgb()}
- {redFloat()}
- {greenFloat()}
- {blueFloat()}
- {checkChannel(name+String, value+int)}

### `src/main/java/com/akitaattribute/essencethief/api/EssenceColorData.java`
- {apply(orb+ExperienceOrb, color+EssenceColor)}
- {read(orb+ExperienceOrb)}
- {read(entity+Entity)}
- {tagFor(color+EssenceColor)}
- {readFromTags(entity+Entity)}
- {stripExistingEssenceTags(entity+Entity)}

### `src/main/java/com/akitaattribute/essencethief/api/WrappedEntity.java`
- {WrappedEntity(entity+E)}
- {entity()}
- {createRisingTrail(color+EssenceColor)}
- {createRisingTrail(rgb+int)}
- {removeRisingTrail()}

### `src/main/java/com/akitaattribute/essencethief/client/ColorableExperienceOrbRenderer.java`
- {ColorableExperienceOrbRenderer(context+EntityRendererProvider.Context)}
- {render(orb+ExperienceOrb, yaw+float, partialTick+float, poseStack+PoseStack, buffers+MultiBufferSource, packedLight+int)}
- {getTextureLocation(orb+ExperienceOrb)}
- {vanillaColor(orb+ExperienceOrb, partialTick+float)}
- {vertex(consumer+VertexConsumer, pose+PoseStack.Pose, x+float, y+float, color+EssenceColor, u+float, v+float)}
- {registerRenderers(event+EntityRenderersEvent.RegisterRenderers)}

### `src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java`
- {track(entity+Entity, color+EssenceColor)}
- {track(entity+Entity, color+EssenceColor, targetPlayers+Iterable<ServerPlayer>)}
- {updateColorIfTracked(entity+Entity, color+EssenceColor)}
- {untrack(entity+Entity)}
- {onEntityJoin(event+EntityJoinLevelEvent)}
- {onEntityLeave(event+EntityLeaveLevelEvent)}
- {onLevelTick(event+TickEvent.LevelTickEvent)}
- {discoverLoadedEntities(level+ServerLevel)}
- {autoTrack(entity+Entity)}
- {toVisiblePlayerIds(targetPlayers+Iterable<ServerPlayer>)}
- {visiblePlayers(level+ServerLevel, visiblePlayerIds+Set<UUID>)}
- {sendForcedParticle(level+ServerLevel, particle+T, x+double, y+double, z+double, count+int, xOffset+double, yOffset+double, zOffset+double, speed+double)}
- {sendForcedParticle(level+ServerLevel, targetPlayers+Iterable<ServerPlayer>, particle+T, x+double, y+double, z+double, count+int, xOffset+double, yOffset+double, zOffset+double, speed+double)}
- {TrackedTrail(entity+Entity, color+EssenceColor, visiblePlayerIds+Set<UUID>)}
- {tick(tickingLevel+ServerLevel, gameTick+int)}

### `src/main/java/com/akitaattribute/essencethief/trail/TrailSchedule.java`
- {shouldSpawn(gameTick+int, stream+int)}
- {isRising(gameTick+int, stream+int)}
- {progress(gameTick+int, stream+int)}
- {upwardVelocityPerTick()}
- {validateStream(stream+int)}

### `src/test/java/com/akitaattribute/essencethief/api/EssenceColorTest.java`
- No method declarations found.

### `src/test/java/com/akitaattribute/essencethief/trail/TrailScheduleTest.java`
- No method declarations found.

### `variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/EssenceThiefMod.java`
- {EssenceThiefMod()}

### `variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/api/Essence.java`
- {Essence(orb+ExperienceOrb)}
- {create(level+Level, x+double, y+double, z+double, value+int)}
- {color(rgb+int)}
- {color(color+EssenceColor)}
- {color()}
- {createRisingTrail(color+EssenceColor)}
- {createRisingTrail(rgb+int)}
- {removeRisingTrail()}
- {readColor(orb+ExperienceOrb)}
- {syncTrailColor(orb+ExperienceOrb, color+EssenceColor)}

### `variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/api/EssenceColor.java`
- {EssenceColor(red+int, green+int, blue+int)}
- {fromRgb(rgb+int)}
- {rgb()}
- {redFloat()}
- {greenFloat()}
- {blueFloat()}
- {checkChannel(name+String, value+int)}

### `variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/api/EssenceColorData.java`
- {apply(orb+ExperienceOrb, color+EssenceColor)}
- {read(orb+ExperienceOrb)}
- {read(entity+Entity)}
- {tagFor(color+EssenceColor)}
- {readFromTags(entity+Entity)}
- {stripExistingEssenceTags(entity+Entity)}

### `variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/api/WrappedEntity.java`
- {WrappedEntity(entity+E)}
- {entity()}
- {createRisingTrail(color+EssenceColor)}
- {createRisingTrail(rgb+int)}
- {removeRisingTrail()}

### `variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/client/ColorableExperienceOrbRenderer.java`
- {ColorableExperienceOrbRenderer(context+EntityRendererProvider.Context)}
- {register()}
- {submit(state+State, poseStack+PoseStack, collector+SubmitNodeCollector, camera+CameraRenderState)}
- {createRenderState()}
- {extractRenderState(orb+ExperienceOrb, state+State, partialTick+float)}
- {vanillaColor(ageInTicks+float)}
- {vertex(consumer+VertexConsumer, pose+PoseStack.Pose, x+float, y+float, color+EssenceColor, u+float, v+float, light+int)}

### `variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java`
- {track(entity+Entity, color+EssenceColor)}
- {track(entity+Entity, color+EssenceColor, targetPlayers+Iterable<ServerPlayer>)}
- {updateColorIfTracked(entity+Entity, color+EssenceColor)}
- {untrack(entity+Entity)}
- {onEntityJoin(event+EntityJoinLevelEvent)}
- {onEntityLeave(event+EntityLeaveLevelEvent)}
- {onLevelTick(event+TickEvent.LevelTickEvent.Post)}
- {discoverLoadedEntities(level+ServerLevel)}
- {autoTrack(entity+Entity)}
- {toVisiblePlayerIds(targetPlayers+Iterable<ServerPlayer>)}
- {visiblePlayers(level+ServerLevel, visiblePlayerIds+Set<UUID>)}
- {sendForcedParticle(level+ServerLevel, particle+T, x+double, y+double, z+double, count+int, xOffset+double, yOffset+double, zOffset+double, speed+double)}
- {sendForcedParticle(level+ServerLevel, targetPlayers+Iterable<ServerPlayer>, particle+T, x+double, y+double, z+double, count+int, xOffset+double, yOffset+double, zOffset+double, speed+double)}
- {TrackedTrail(entity+Entity, color+EssenceColor, visiblePlayerIds+Set<UUID>)}
- {tick(tickingLevel+ServerLevel, gameTick+int)}

### `variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/TrailSchedule.java`
- {shouldSpawn(gameTick+int, stream+int)}
- {isRising(gameTick+int, stream+int)}
- {progress(gameTick+int, stream+int)}
- {upwardVelocityPerTick()}
- {validateStream(stream+int)}

### `variants/forge-1.21.11/src/test/java/com/akitaattribute/essencethief/api/EssenceColorTest.java`
- No method declarations found.

### `variants/forge-1.21.11/src/test/java/com/akitaattribute/essencethief/trail/TrailScheduleTest.java`
- No method declarations found.

## 2026-06-01 Velocity Particle Additions

```text
EssenceThiefRPG-codex-create-minecraft-mod-for-custom-essence-xp-orb/
├── changelog/
│   └── 2026-06-01-velocity-based-rising-particles.md
├── src/main/java/com/akitaattribute/essencethief/registry/ModParticles.java
│   └── {register(modEventBus+IEventBus)}
├── src/main/java/com/akitaattribute/essencethief/particle/EssenceRisingParticleOptions.java
│   ├── {fromColor(color+EssenceColor)}
│   ├── {getType()}
│   ├── {writeToNetwork(buffer+FriendlyByteBuf)}
│   ├── {writeToString()}
│   └── {clamp01(value+float)}
├── src/main/java/com/akitaattribute/essencethief/particle/EssenceRisingParticleType.java
│   ├── {EssenceRisingParticleType(overrideLimiter+boolean)}
│   └── {codec()}
├── src/main/java/com/akitaattribute/essencethief/client/particle/EssenceRisingParticle.java
│   ├── {EssenceRisingParticle(level+ClientLevel, x+double, y+double, z+double, xVelocity+double, yVelocity+double, zVelocity+double, options+EssenceRisingParticleOptions, sprites+SpriteSet)}
│   ├── {getRenderType()}
│   ├── {tick()}
│   └── Provider
│       ├── {Provider(sprites+SpriteSet)}
│       └── {createParticle(options+EssenceRisingParticleOptions, level+ClientLevel, x+double, y+double, z+double, xVelocity+double, yVelocity+double, zVelocity+double)}
├── src/main/java/com/akitaattribute/essencethief/client/particle/EssenceParticleProviders.java
│   └── {register(event+RegisterParticleProvidersEvent)}
├── variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/registry/ModParticles.java
│   └── {register(modBusGroup+BusGroup)}
├── variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/particle/EssenceRisingParticleOptions.java
│   ├── {fromColor(color+EssenceColor)}
│   ├── {getType()}
│   ├── {writeToNetwork(buffer+FriendlyByteBuf)}
│   ├── {writeToString()}
│   └── {clamp01(value+float)}
├── variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/particle/EssenceRisingParticleType.java
│   ├── {EssenceRisingParticleType(overrideLimiter+boolean)}
│   └── {codec()}
├── variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/client/particle/EssenceRisingParticle.java
│   ├── {EssenceRisingParticle(level+ClientLevel, x+double, y+double, z+double, xVelocity+double, yVelocity+double, zVelocity+double, options+EssenceRisingParticleOptions, sprites+SpriteSet)}
│   ├── {getRenderType()}
│   ├── {tick()}
│   └── Provider
│       ├── {Provider(sprites+SpriteSet)}
│       └── {createParticle(options+EssenceRisingParticleOptions, level+ClientLevel, x+double, y+double, z+double, xVelocity+double, yVelocity+double, zVelocity+double)}
└── variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/client/particle/EssenceParticleProviders.java
    ├── {register()}
    └── {register(event+RegisterParticleProvidersEvent)}
```


## 2026-06-01 1.21 Velocity Build Fix Update

```text
changelog/
└── 2026-06-01-fix-1.21-velocity-build.md

variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/EssenceThiefMod.java
└── {EssenceThiefMod()}

variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
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


### `variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/client/particle/EssenceRisingParticle.java`
- {EssenceRisingParticle()}

### `variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/client/particle/EssenceParticleProviders.java`
- {register()}
- {register(event+RegisterParticleProvidersEvent)}

### `variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/particle/EssenceRisingParticleOptions.java`
- {EssenceRisingParticleOptions(red+float, green+float, blue+float, scale+float)}
- {fromColor(color+EssenceColor)}

### `variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/particle/EssenceRisingParticleType.java`
- {EssenceRisingParticleType()}

### `variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/registry/ModParticles.java`
- {ModParticles()}
- {register(modBusGroup+BusGroup)}

## 2026-06-02 Dust Vertical Velocity Update

```text
changelog/
└── 2026-06-02-restore-dust-vertical-velocity.md

src/main/java/com/akitaattribute/essencethief/EssenceThiefMod.java
└── {EssenceThiefMod()}

src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
├── {track(entity+Entity, color+EssenceColor)}
├── {updateColorIfTracked(entity+Entity, color+EssenceColor)}
├── {untrack(entity+Entity)}
├── {onEntityJoin(event+EntityJoinLevelEvent)}
├── {onEntityLeave(event+EntityLeaveLevelEvent)}
├── {onLevelTick(event+TickEvent.LevelTickEvent)}
├── {discoverLoadedEntities(level+ServerLevel)}
├── {autoTrack(entity+Entity)}
└── TrackedTrail
    └── {tick(tickingLevel+ServerLevel, gameTick+int)}

src/main/java/com/akitaattribute/essencethief/trail/TrailSchedule.java
├── {shouldSpawn(gameTick+int, stream+int)}
├── {isRising(gameTick+int, stream+int)}
├── {progress(gameTick+int, stream+int)}
├── {upwardVelocityPerTick()}
└── {validateStream(stream+int)}

variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
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

variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/TrailSchedule.java
├── {shouldSpawn(gameTick+int, stream+int)}
├── {isRising(gameTick+int, stream+int)}
├── {progress(gameTick+int, stream+int)}
├── {upwardVelocityPerTick()}
└── {validateStream(stream+int)}
```

## Patch Addition - 2026-06-02 Restore Position-Stepped Dust Rise

```text
changelog/
└── 2026-06-02-restore-position-stepped-dust-rise.md
src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
└── TrackedTrail
    └── {tick(tickingLevel+ServerLevel, gameTick+int)}
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
└── TrackedTrail
    └── {tick(tickingLevel+ServerLevel, gameTick+int)}
```

## Patch Addition - 2026-06-02 Force Long-Distance Particle Rendering

```text
changelog/
└── 2026-06-02-force-long-distance-particle-rendering.md
src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
├── {sendForcedParticle(level+ServerLevel, particle+T, x+double, y+double, z+double, count+int, xOffset+double, yOffset+double, zOffset+double, speed+double)}
└── TrackedTrail
    └── {tick(tickingLevel+ServerLevel, gameTick+int)}
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
├── {sendForcedParticle(level+ServerLevel, particle+T, x+double, y+double, z+double, count+int, xOffset+double, yOffset+double, zOffset+double, speed+double)}
└── TrackedTrail
    └── {tick(tickingLevel+ServerLevel, gameTick+int)}
```
