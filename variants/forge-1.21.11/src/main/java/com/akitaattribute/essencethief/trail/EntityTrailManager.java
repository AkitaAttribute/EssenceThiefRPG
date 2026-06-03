package com.akitaattribute.essencethief.trail;

import com.akitaattribute.essencethief.api.EssenceColor;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Server-side registry which can decorate mobs, item drops, vanilla entities, and Essence wrappers alike. */
public final class EntityTrailManager {
    private static final int DISCOVERY_INTERVAL_TICKS = 20;
    private static final Map<UUID, TrackedTrail> TRAILS = new ConcurrentHashMap<>();

    private EntityTrailManager() {
    }

    public static void track(Entity entity, EssenceColor color) {
        track(entity, color, null);
    }

    public static void track(Entity entity, EssenceColor color, Iterable<ServerPlayer> targetPlayers) {
        if (entity.level().isClientSide()) {
            throw new IllegalStateException("Rising trails must be registered from the logical server");
        }
        TRAILS.put(entity.getUUID(), new TrackedTrail(entity, color, toVisiblePlayerIds(targetPlayers)));
    }

    public static void updateColorIfTracked(Entity entity, EssenceColor color) {
        TRAILS.computeIfPresent(entity.getUUID(), (uuid, tracked) -> new TrackedTrail(entity, color, tracked.visiblePlayerIds()));
    }

    public static void untrack(Entity entity) {
        TRAILS.remove(entity.getUUID());
    }

    public static void onEntityJoin(EntityJoinLevelEvent event) {
        autoTrack(event.getEntity());
    }

    public static void onEntityLeave(EntityLeaveLevelEvent event) {
        untrack(event.getEntity());
    }

    public static void onLevelTick(TickEvent.LevelTickEvent.Post event) {
        if (!(event.level() instanceof ServerLevel level)) {
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
            TRAILS.putIfAbsent(entity.getUUID(), new TrackedTrail(entity, EssenceColor.DEFAULT, null));
        }
    }

    private static Set<UUID> toVisiblePlayerIds(Iterable<ServerPlayer> targetPlayers) {
        if (targetPlayers == null) {
            return null;
        }
        Set<UUID> visiblePlayerIds = new HashSet<>();
        for (ServerPlayer player : targetPlayers) {
            if (player != null) {
                visiblePlayerIds.add(player.getUUID());
            }
        }
        return visiblePlayerIds;
    }

    private static Iterable<ServerPlayer> visiblePlayers(ServerLevel level, Set<UUID> visiblePlayerIds) {
        if (visiblePlayerIds == null) {
            return null;
        }
        ArrayList<ServerPlayer> visiblePlayers = new ArrayList<>();
        for (ServerPlayer player : level.players()) {
            if (visiblePlayerIds.contains(player.getUUID())) {
                visiblePlayers.add(player);
            }
        }
        return visiblePlayers;
    }

    public static <T extends ParticleOptions> void sendForcedParticle(
        ServerLevel level,
        T particle,
        double x,
        double y,
        double z,
        int count,
        double xOffset,
        double yOffset,
        double zOffset,
        double speed
    ) {
        sendForcedParticle(level, null, particle, x, y, z, count, xOffset, yOffset, zOffset, speed);
    }

    public static <T extends ParticleOptions> void sendForcedParticle(
        ServerLevel level,
        Iterable<ServerPlayer> targetPlayers,
        T particle,
        double x,
        double y,
        double z,
        int count,
        double xOffset,
        double yOffset,
        double zOffset,
        double speed
    ) {
        Iterable<ServerPlayer> resolvedPlayers = targetPlayers == null ? level.players() : targetPlayers;
        for (ServerPlayer player : resolvedPlayers) {
            if (player != null) {
                level.sendParticles(player, particle, true, x, y, z, count, xOffset, yOffset, zOffset, speed);
            }
        }
    }

    private record TrackedTrail(Entity entity, EssenceColor color, Set<UUID> visiblePlayerIds) {
        private boolean tick(ServerLevel tickingLevel, int gameTick) {
            if (entity.isRemoved()) {
                return true;
            }
            if (entity.level() != tickingLevel) {
                return false;
            }

            DustParticleOptions particle = new DustParticleOptions(color.rgb(), 1.0F);
            for (int stream = 0; stream < TrailSchedule.STREAM_COUNT; stream++) {
                double progress = TrailSchedule.progress(gameTick, stream);
                if (progress < 0.0D) {
                    continue;
                }
                sendForcedParticle(
                    tickingLevel,
                    visiblePlayers(tickingLevel, visiblePlayerIds),
                    particle,
                    entity.getX(),
                    entity.getBoundingBox().maxY + TrailSchedule.RISE_BLOCKS * progress,
                    entity.getZ(),
                    1, 0.0D, 0.0D, 0.0D, 0.0D
                );
            }
            return false;
        }
    }
}
