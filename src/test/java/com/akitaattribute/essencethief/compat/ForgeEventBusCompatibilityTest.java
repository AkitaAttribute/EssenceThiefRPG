package com.akitaattribute.essencethief.compat;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class ForgeEventBusCompatibilityTest {
    @Test
    void supportsSingleConsumerListenerOverload() throws Exception {
        Method method = ForgeEventBusCompatibility.findAddListener(SingleConsumerBus.class);
        Consumer<Object> listener = ignored -> { };

        assertEquals(1, method.getParameterCount());
        assertArrayEquals(new Object[] {listener}, ForgeEventBusCompatibility.argumentsFor(method, Object.class, listener));
    }

    @Test
    void supportsPriorityCanceledEventTypeAndConsumerOverload() throws Exception {
        Method method = ForgeEventBusCompatibility.findAddListener(ExpandedBus.class);
        Consumer<String> listener = ignored -> { };
        Object[] arguments = ForgeEventBusCompatibility.argumentsFor(method, String.class, listener);

        assertEquals(4, method.getParameterCount());
        assertArrayEquals(new Object[] {Priority.NORMAL, false, String.class, listener}, arguments);
    }

    @Test
    void findsPublicInterfaceMethodBehindPackagePrivateImplementation() throws Exception {
        Method method = ForgeEventBusCompatibility.findAddListener(HiddenImplementation.class);

        assertSame(SingleConsumerBus.class, method.getDeclaringClass());
    }

    public interface SingleConsumerBus {
        void addListener(Consumer<Object> listener);
    }

    public interface ExpandedBus {
        void addListener(Priority priority, boolean receiveCanceled, Class<?> eventType, Consumer<?> listener);
    }

    private static final class HiddenImplementation implements SingleConsumerBus {
        @Override
        public void addListener(Consumer<Object> listener) {
        }
    }

    private enum Priority {
        LOW,
        NORMAL,
        HIGH
    }
}
