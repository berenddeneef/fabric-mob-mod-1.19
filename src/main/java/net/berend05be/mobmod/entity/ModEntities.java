package net.berend05be.mobmod.entity;

import net.berend05be.mobmod.MobMod;
import net.berend05be.mobmod.entity.custom.SculkbugEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<SculkbugEntity> SCULKBUG = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(MobMod.MOD_ID, "sculkbug"),
            FabricEntityTypeBuilder.<SculkbugEntity>create(SpawnGroup.CREATURE, SculkbugEntity::new)
                    .dimensions(EntityDimensions.fixed(1.5f, 2f)).build());

    public static void registerModEntities() {
        MobMod.LOGGER.info("Registering Mod Entities for " + MobMod.MOD_ID);
    }
}
