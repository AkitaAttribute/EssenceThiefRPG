package com.akitaattributegaming.essencethief.trail;

import com.akitaattributegaming.essencethief.EssenceThiefMod;
import com.akitaattributegaming.essencethief.api.EssenceColor;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.TickEvent;
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

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.level instanceof ServerLevel level)) {
            return;
        }
        TRAILS.values().removeIf(trail -> trail.tick(level));
    }

    private record TrackedTrail(Entity entity, EssenceColor color) {
        private boolean tick(ServerLevel tickingLevel) {
            if (entity.isRemoved()) {
                return true;
            }
            if (entity.level() != tickingLevel) {
                return false;
            }

            DustParticleOptions particle = new DustParticleOptions(
                new Vector3f(color.redFloat(), color.greenFloat(), color.blueFloat()), 1.0F
            );
            int gameTick = (int) tickingLevel.getGameTime();
            for (int stream = 0; stream < TrailSchedule.STREAM_COUNT; stream++) {
                double progress = TrailSchedule.progress(gameTick, stream);
                if (progress < 0.0D) {
                    continue;
                }
                double angle = stream * (Math.PI * 2.0D / TrailSchedule.STREAM_COUNT) + progress * Math.PI;
                double radius = CONE_RADIUS * progress;
                tickingLevel.sendParticles(
                    particle,
                    entity.getX() + Math.cos(angle) * radius,
                    entity.getBoundingBox().maxY + progress * TrailSchedule.RISE_BLOCKS,
                    entity.getZ() + Math.sin(angle) * radius,
                    1, 0.0D, 0.0D, 0.0D, 0.0D
                );
            }
            return false;
        }
    }
}
