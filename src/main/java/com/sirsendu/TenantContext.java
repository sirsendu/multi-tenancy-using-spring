package com.sirsendu;

/**
 *
 * @author Sirsendu Konar
 *
 */
public class TenantContext {
    private static ThreadLocal<Object> currentTenant = new ThreadLocal<>();

    public static void setCurrentTenant(final Object tenant) {
        currentTenant.set(tenant);
    }

    public static Object getCurrentTenant() {
        return currentTenant.get();
    }
}
