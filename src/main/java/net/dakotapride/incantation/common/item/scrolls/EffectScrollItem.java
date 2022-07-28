package net.dakotapride.incantation.common.item.scrolls;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class EffectScrollItem extends Item {
    private final ToolMaterial material;
    public EffectScrollItem(ToolMaterial material, Settings settings) {
        super(settings);
        this.material = material;
    }

    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return (this.material.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient));
    }
}
