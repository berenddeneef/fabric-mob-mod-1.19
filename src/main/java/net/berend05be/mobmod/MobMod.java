package net.berend05be.mobmod;



import net.berend05be.mobmod.entity.ModEntities;
import net.berend05be.mobmod.entity.custom.SculkbugEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class MobMod implements ModInitializer {
	public static final String MOD_ID = "mobmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		GeckoLib.initialize();
		FabricDefaultAttributeRegistry.register(ModEntities.SCULKBUG, SculkbugEntity.setAttributes());
	}
}
