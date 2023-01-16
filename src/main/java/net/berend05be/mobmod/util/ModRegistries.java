package net.berend05be.mobmod.util;

import net.berend05be.mobmod.entity.ModEntities;
import net.berend05be.mobmod.entity.custom.SculkbugEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class ModRegistries {
    public static void registerModStuffs() {
        registerAttributes();
    }
    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.SCULKBUG, SculkbugEntity.createMobAttributes());

    }
}
