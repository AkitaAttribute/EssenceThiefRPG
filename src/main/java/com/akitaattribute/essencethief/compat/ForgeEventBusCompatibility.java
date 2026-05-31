package com.akitaattribute.essencethief.compat;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/** Reflective bridge for Forge event-bus API shapes used by supported runtime variants. */
public final class ForgeEventBusCompatibility {
    private static final String MINECRAFT_FORGE_CLASS = "net.minecraftforge.common.MinecraftForge";
    private static final String EVENT_BUS_FIELD = "EVENT_BUS";

    private ForgeEventBusCompatibility() {
    }

    public static <T> void addListener(Class<T> eventType, Consumer<T> listener) {
        try {
            Object eventBus = findEventBus();
            Method method = findAddListener(eventBus.getClass());
            method.invoke(eventBus, argumentsFor(method, eventType, listener));
        } catch (ReflectiveOperationException | IllegalArgumentException exception) {
            Throwable cause = exception instanceof InvocationTargetException && exception.getCause() != null
                ? exception.getCause()
                : exception;
            throw new IllegalStateException("This Forge runtime cannot register essence entity trails", cause);
        }
    }

    private static Object findEventBus() throws ReflectiveOperationException {
        Class<?> minecraftForge = Class.forName(MINECRAFT_FORGE_CLASS);
        Field field = minecraftForge.getField(EVENT_BUS_FIELD);
        return field.get(null);
    }

    static Method findAddListener(Class<?> eventBusType) throws NoSuchMethodException {
        return publicApiTypes(eventBusType).stream()
            .flatMap(type -> List.of(type.getMethods()).stream())
            .filter(method -> method.getName().equals("addListener"))
            .filter(method -> Modifier.isPublic(method.getModifiers()))
            .filter(method -> Modifier.isPublic(method.getDeclaringClass().getModifiers()))
            .filter(ForgeEventBusCompatibility::isSupportedAddListener)
            .min(Comparator.comparingInt(Method::getParameterCount))
            .orElseThrow(() -> new NoSuchMethodException("Forge EVENT_BUS does not expose a supported addListener overload"));
    }

    private static boolean isSupportedAddListener(Method method) {
        boolean hasConsumer = false;
        for (Class<?> parameterType : method.getParameterTypes()) {
            if (Consumer.class.isAssignableFrom(parameterType)) {
                hasConsumer = true;
            } else if (parameterType != boolean.class && parameterType != Class.class && !parameterType.isEnum()) {
                return false;
            }
        }
        return hasConsumer;
    }

    static Object[] argumentsFor(Method method, Class<?> eventType, Consumer<?> listener) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] arguments = new Object[parameterTypes.length];
        for (int index = 0; index < parameterTypes.length; index++) {
            Class<?> parameterType = parameterTypes[index];
            if (Consumer.class.isAssignableFrom(parameterType)) {
                arguments[index] = listener;
            } else if (parameterType == boolean.class) {
                arguments[index] = false;
            } else if (parameterType == Class.class) {
                arguments[index] = eventType;
            } else if (parameterType.isEnum()) {
                arguments[index] = enumConstant(parameterType, "NORMAL");
            } else {
                throw new IllegalArgumentException("Unsupported Forge addListener parameter: " + parameterType.getName());
            }
        }
        return arguments;
    }

    private static Object enumConstant(Class<?> enumType, String preferredName) {
        Object[] constants = enumType.getEnumConstants();
        for (Object constant : constants) {
            if (((Enum<?>) constant).name().equals(preferredName)) {
                return constant;
            }
        }
        if (constants.length == 0) {
            throw new IllegalArgumentException("Forge listener priority enum has no values: " + enumType.getName());
        }
        return constants[0];
    }

    private static List<Class<?>> publicApiTypes(Class<?> implementationType) {
        List<Class<?>> types = new ArrayList<>();
        collectPublicTypes(implementationType, types, new HashSet<>());
        return types;
    }

    private static void collectPublicTypes(Class<?> type, List<Class<?>> types, Set<Class<?>> visited) {
        if (type == null || !visited.add(type)) {
            return;
        }
        if (Modifier.isPublic(type.getModifiers())) {
            types.add(type);
        }
        for (Class<?> interfaceType : type.getInterfaces()) {
            collectPublicTypes(interfaceType, types, visited);
        }
        collectPublicTypes(type.getSuperclass(), types, visited);
    }
}
