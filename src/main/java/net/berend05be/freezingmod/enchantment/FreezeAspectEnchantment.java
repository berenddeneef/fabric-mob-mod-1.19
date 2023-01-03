package net.berend05be.freezingmod.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

public class FreezeAspectEnchantment extends Enchantment {
    public FreezeAspectEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot... slotTypes) {
        super(weight, type, slotTypes);
    }
    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (!target.world.isClient) {
            target.setFrozenTicks(300);

        }
        super.onTargetDamaged(user, target, level);
    }
    @Override
    public int getMaxLevel() {
        return  1;
    }
}
