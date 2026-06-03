# 2026-06-01 Velocity-Based Rising Particles

## Prompt Provided

```text
I'd like the better implementation of the velocity based rather than position tracking if you can get it to work. Please look online for the correct way to do this.
```

## Online Findings

- Forge's particle documentation says server-side particle spawning should use `ServerLevel#sendParticles`; client-only particle methods called from the server do nothing.
- Minecraft's particle command behavior treats `count=0` as the directional-particle mode where the delta values become the motion vector instead of random spread.
- Dust/redstone particles are the wrong tool for this because they do not reliably inherit custom motion. Mojang tracks this as a dust-particle motion issue, and community documentation reports the same limitation.
- The better implementation is therefore a custom particle type that carries the RGB color as particle options and uses the packet velocity values as the particle's real client-side motion.

## Proposed Solution Implemented

- Replaced `DustParticleOptions` trail emission with a custom `essencethief:essence_rising` particle.
- Added `EssenceRisingParticleOptions` so the server can send RGB color and scale as particle data rather than overloading velocity/delta values.
- Added `EssenceRisingParticleType` and `ModParticles` registration.
- Added a client-side `EssenceRisingParticle` that explicitly sets `xd`, `yd`, and `zd` from the server-supplied velocity values.
- Added client particle provider registration.
- Switched `EntityTrailManager` back to `count=0` directional particle spawning.
- Kept three staggered streams; each stream spawns once every cycle, then the client particle rises using its own velocity.
- Kept the cone behavior by adding a small horizontal velocity component per stream.
- Bumped `mod_version` to `0.2.4`.

## Files Added

```text
src/main/java/com/akitaattribute/essencethief/registry/ModParticles.java
src/main/java/com/akitaattribute/essencethief/particle/EssenceRisingParticleOptions.java
src/main/java/com/akitaattribute/essencethief/particle/EssenceRisingParticleType.java
src/main/java/com/akitaattribute/essencethief/client/particle/EssenceRisingParticle.java
src/main/java/com/akitaattribute/essencethief/client/particle/EssenceParticleProviders.java
src/main/resources/assets/essencethief/particles/essence_rising.json
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/registry/ModParticles.java
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/particle/EssenceRisingParticleOptions.java
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/particle/EssenceRisingParticleType.java
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/client/particle/EssenceRisingParticle.java
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/client/particle/EssenceParticleProviders.java
variants/forge-1.21.11/src/main/resources/assets/essencethief/particles/essence_rising.json
changelog/2026-06-01-velocity-based-rising-particles.md
```

## Files Changed

```text
gradle.properties
src/main/java/com/akitaattribute/essencethief/EssenceThiefMod.java
src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
src/main/java/com/akitaattribute/essencethief/trail/TrailSchedule.java
src/test/java/com/akitaattribute/essencethief/trail/TrailScheduleTest.java
variants/forge-1.21.11/gradle.properties
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/EssenceThiefMod.java
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
variants/forge-1.21.11/src/main/java/com/akitaattribute/essencethief/trail/TrailSchedule.java
variants/forge-1.21.11/src/test/java/com/akitaattribute/essencethief/trail/TrailScheduleTest.java
EssenceThiefRPG-project-index.md
```

## Updated Structure Index

```text
EssenceThiefRPG-codex-create-minecraft-mod-for-custom-essence-xp-orb/
├── changelog/
│   └── 2026-06-01-velocity-based-rising-particles.md
├── src/main/java/com/akitaattribute/essencethief/EssenceThiefMod.java
│   └── {EssenceThiefMod()}
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
├── src/main/java/com/akitaattribute/essencethief/trail/EntityTrailManager.java
│   └── TrackedTrail
│       └── {tick(tickingLevel+ServerLevel, gameTick+int)}
├── src/main/java/com/akitaattribute/essencethief/trail/TrailSchedule.java
│   ├── {upwardVelocityPerTick()}
│   └── {horizontalVelocityPerTick(finalRadius+double)}
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

## Notes

- This is the clean direction for a true velocity-based, color-controllable particle trail.
- The prior `DustParticleOptions` implementation could be position-stepped, but it should not be expected to support true custom velocity.
- I could not run the Gradle build locally because this repository still does not include a Gradle wrapper and the environment does not have Gradle installed.
