package com.akitaattribute.essencethief.trail;

import com.akitaattribute.essencethief.EssenceThiefMod;
import com.akitaattribute.essencethief.api.EssenceColor;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Server-side registry which can decorate mobs, item drops, vanilla entities, and Essence wrappers alike. */
@Mod.EventBusSubscriber(modid = EssenceThiefMod.MOD_ID)
public final class EntityTrailManager {
    private static final double CONE_RADIUS = 0.45D;
    private static final int DISCOVERY_INTERVAL_TICKS = 20;
    private static final Map<UUID, TrackedTrail> TRAILS = new ConcurrentHashMap<>();

    private EntityTrailManager() {
    }

    public static void track(Entity entity, EssenceColor color) {
        if (entity.level().isClientSide()) {
            throw new IllegalStateException("Rising trails must be registered from the logical server");
        }
        TRAILS.put(entity.getUUID(), new TrackedTrail(entity, color));
    }

    public static void updateColorIfTracked(Entity entity, EssenceColor color) {
        TRAILS.computeIfPresent(entity.getUUID(), (uuid, tracked) -> new TrackedTrail(entity, color));
    }

    public static void untrack(Entity entity) {
        TRAILS.remove(entity.getUUID());
    }

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        autoTrack(event.getEntity());
    }

    @SubscribeEvent
    public static void onEntityLeave(EntityLeaveLevelEvent event) {
        untrack(event.getEntity());
    }

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.level instanceof ServerLevel level)) {
            return;
        }
        int gameTick = (int) level.getGameTime();
        if (Math.floorMod(gameTick, DISCOVERY_INTERVAL_TICKS) == 0) {
            discoverLoadedEntities(level);
        }
        TRAILS.values().removeIf(trail -> trail.tick(level, gameTick));
    }

    private static void discoverLoadedEntities(ServerLevel level) {
        for (Entity entity : level.getAllEntities()) {
            autoTrack(entity);
        }
    }

    private static void autoTrack(Entity entity) {
        if (!entity.level().isClientSide()) {
            TRAILS.putIfAbsent(entity.getUUID(), new TrackedTrail(entity, EssenceColor.DEFAULT));
        }
    }

    private record TrackedTrail(Entity entity, EssenceColor color) {
        private boolean tick(ServerLevel tickingLevel, int gameTick) {
            if (entity.isRemoved()) {
                return true;
            }
            if (entity.level() != tickingLevel) {
                return false;
            }

            DustParticleOptions particle = new DustParticleOptions(
                new Vector3f(color.redFloat(), color.greenFloat(), color.blueFloat()), 1.0F
            );
            for (int stream = 0; stream < TrailSchedule.STREAM_COUNT; stream++) {
                if (!TrailSchedule.shouldSpawn(gameTick, stream)) {
                    continue;
                }
                double angle = stream * (Math.PI * 2.0D / TrailSchedule.STREAM_COUNT);
                double xVelocity = Math.cos(angle) * CONE_RADIUS / TrailSchedule.RISE_TICKS;
                double zVelocity = Math.sin(angle) * CONE_RADIUS / TrailSchedule.RISE_TICKS;
                tickingLevel.sendParticles(
                    particle,
                    entity.getX(),
                    entity.getBoundingBox().maxY,
                    entity.getZ(),
                    0, xVelocity, TrailSchedule.upwardVelocityPerTick(), zVelocity, 1.0D
                );
            }
            return false;
        }
    }
}
