package net.berend05be.mobmod.entity.client;

import net.berend05be.mobmod.MobMod;
import net.berend05be.mobmod.entity.custom.SculkbugEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;


public class SculkbugRenderer extends GeoEntityRenderer<SculkbugEntity> {
    public SculkbugRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SculkbugModel());
    }

    @Override
    public Identifier getTextureLocation(SculkbugEntity instance) {
        return new Identifier(MobMod.MOD_ID, "textures/entity/chomper_texture.png");
    }


    @Override
    public RenderLayer getRenderType(SculkbugEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
