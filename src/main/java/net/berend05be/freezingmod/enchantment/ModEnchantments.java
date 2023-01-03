package net.berend05be.freezingmod.enchantment;

import net.berend05be.freezingmod.FreezingMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantments {
    public static Enchantment FREEZING_ASPECT = register("freezing_aspect",
            new FreezeAspectEnchantment(Enchantment.Rarity.RARE,
                    EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(FreezingMod.MOD_ID, name), enchantment);
    }
    public static void RegisterModEnchantments() {
        System.out.println("Registering Enchantments for " + FreezingMod.MOD_ID);

    }
}
