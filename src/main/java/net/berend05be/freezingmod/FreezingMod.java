package net.berend05be.freezingmod;

import net.berend05be.freezingmod.effect.ModEffects;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//Very important comment
public class FreezingMod implements ModInitializer {
	public static final String MOD_ID = "freezingmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModEffects.registerEffects();
	}
}
