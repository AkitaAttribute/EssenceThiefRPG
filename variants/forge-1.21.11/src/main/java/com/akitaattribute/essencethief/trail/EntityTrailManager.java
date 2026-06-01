package com.akitaattribute.essencethief.trail;

import com.akitaattribute.essencethief.api.EssenceColor;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class EntityTrailManager {
    private static final double CONE_RADIUS = 0.45D;
    private static final Map<UUID, TrackedTrail> TRAILS = new ConcurrentHashMap<>();

    private EntityTrailManager() {
    }

    public static void track(Entity entity, EssenceColor color) {
        if (entity.level().isClientSide()) {
            throw new IllegalStateException("Rising trails must be registered from the logical server");
        }
        TRAILS.put(entity.getUUID(), new TrackedTrail(entity, color));
    }

    public static void untrack(Entity entity) {
        TRAILS.remove(entity.getUUID());
    }

    public static void onEntityJoin(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (!entity.level().isClientSide()) {
            TRAILS.putIfAbsent(entity.getUUID(), new TrackedTrail(entity, EssenceColor.DEFAULT));
        }
    }

    public static void onEntityLeave(EntityLeaveLevelEvent event) {
        untrack(event.getEntity());
    }

    public static void onLevelTick(TickEvent.LevelTickEvent.Post event) {
        if (!(event.level() instanceof ServerLevel level)) {
            return;
        }
        TRAILS.values().removeIf(trail -> trail.tick(level));
    }

    private record TrackedTrail(Entity entity, EssenceColor color) {
        private boolean tick(ServerLevel tickingLevel) {
            if (entity.isRemoved()) return true;
            if (entity.level() != tickingLevel) return false;
            DustParticleOptions particle = new DustParticleOptions(color.rgb(), 1.0F);
            int gameTick = (int) tickingLevel.getGameTime();
            for (int stream = 0; stream < TrailSchedule.STREAM_COUNT; stream++) {
                double progress = TrailSchedule.progress(gameTick, stream);
                if (progress < 0.0D) continue;
                double angle = stream * (Math.PI * 2.0D / TrailSchedule.STREAM_COUNT) + progress * Math.PI;
                double radius = CONE_RADIUS * progress;
                tickingLevel.sendParticles(particle,
                    entity.getX() + Math.cos(angle) * radius,
                    entity.getBoundingBox().maxY + progress * TrailSchedule.RISE_BLOCKS,
                    entity.getZ() + Math.sin(angle) * radius,
                    1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
            return false;
        }
    }
}
