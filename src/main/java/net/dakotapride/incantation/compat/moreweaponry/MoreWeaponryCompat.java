package net.dakotapride.incantation.compat.moreweaponry;


import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.effect.EmptyStatusEffect;
import net.dakotapride.incantation.common.item.EffectScrollItem;
import net.dakotapride.incantation.compat.moreweaponry.item.HarmingResistanceScrollItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MoreWeaponryCompat {
    public static final String MORE_WEAPONRY_ID = ("moreweaponry");

    // Items

    public static StatusEffect HARMING_RESISTANCE = new EmptyStatusEffect(StatusEffectCategory.BENEFICIAL, 0xCEFFF2);
    public static HarmingResistanceScrollItem HARMING_RESISTANCE_SCROLL;
    public static Item COLD_BLOODED_IMBUED_ITEM;
    public static Item COLD_BLOODED_TWIST_ITEM;

    // Registration

    public static <T extends Item> T registerItem(String name, T item) {
        Registry.register(Registry.ITEM, new Identifier(MORE_WEAPONRY_ID, name), item);
        return item;
    }


    public static void moreWeaponryCompatRegistry() {

        // Items
        HARMING_RESISTANCE_SCROLL = registerItem("harming_resistance_scroll",
                new HarmingResistanceScrollItem(new FabricItemSettings().group(IncantationMod.INCANTATION_GROUP)));

    }

}
