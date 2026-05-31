package com.akitaattribute.essencethief;

import com.akitaattribute.essencethief.trail.EntityTrailManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Consumer;

@Mod(EssenceThiefMod.MOD_ID)
public final class EssenceThiefMod {
    public static final String MOD_ID = "essencethief";

    public EssenceThiefMod() {
        registerTrailListener();
    }

    /**
     * Registers without linking against EVENT_BUS's declared field type. Forge changed that type
     * between supported Minecraft generations, even though the public field and listener method remain.
     */
    private static void registerTrailListener() {
        try {
            Object eventBus = MinecraftForge.class.getField("EVENT_BUS").get(null);
            Method addListener = Arrays.stream(eventBus.getClass().getMethods())
                .filter(method -> method.getName().equals("addListener"))
                .filter(method -> method.getParameterCount() == 1)
                .filter(method -> Consumer.class.isAssignableFrom(method.getParameterTypes()[0]))
                .findFirst()
                .orElseThrow(() -> new NoSuchMethodException("Forge EVENT_BUS does not expose addListener(Consumer)"));
            Consumer<Object> listener = event -> {
                if (event instanceof TickEvent.LevelTickEvent levelTickEvent) {
                    EntityTrailManager.onLevelTick(levelTickEvent);
                }
            };
            addListener.invoke(eventBus, listener);
        } catch (ReflectiveOperationException exception) {
            Throwable cause = exception instanceof InvocationTargetException && exception.getCause() != null
                ? exception.getCause()
                : exception;
            throw new IllegalStateException("This Forge runtime cannot register essence entity trails", cause);
        }
    }
}
