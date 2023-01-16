package net.berend05be.mobmod;


import net.berend05be.mobmod.entity.ModEntities;
import net.berend05be.mobmod.entity.client.SculkbugRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;


public class MobModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.SCULKBUG, SculkbugRenderer::new);

    }
}
