package net.dakotapride.incantation.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantedBerryItem extends Item {
    public EnchantedBerryItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
