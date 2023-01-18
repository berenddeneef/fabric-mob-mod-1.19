package net.berend05be.mobmod.entity.client;

import net.berend05be.mobmod.MobMod;
import net.berend05be.mobmod.entity.custom.SculkbugEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SculkbugModel extends GeoModel<SculkbugEntity> {
    @Override
    public Identifier getModelResource(SculkbugEntity object) {
        return new Identifier(MobMod.MOD_ID, "geo/sculkbug.geo.json");
    }

    @Override
    public Identifier getTextureResource(SculkbugEntity object) {
        return new Identifier(MobMod.MOD_ID, "textures/entity/sculkbug/sculkbug.png");
    }

    @Override
    public Identifier getAnimationResource(SculkbugEntity animatable) {
        return new Identifier(MobMod.MOD_ID, "animations/sculkbug.animation.json");
    }

    @Override
    public RenderLayer getRenderType(SculkbugEntity animatable, Identifier texture) {
        return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
    }
}
