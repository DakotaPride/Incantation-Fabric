package net.dakotapride.incantation.compat.croptopia;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.effect.EmptyStatusEffect;
import net.dakotapride.incantation.compat.croptopia.item.SugaryHealingScrollItem;
import net.dakotapride.incantation.compat.moreweaponry.item.HarmingResistanceScrollItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CroptopiaCompat {
    public static final String CROPTOPIA_ID = ("croptopia");

    // Items

    public static StatusEffect SUGARY_HEALING = new EmptyStatusEffect(StatusEffectCategory.BENEFICIAL, 0xFFC1FA);
    public static SugaryHealingScrollItem SUGARY_HEALING_SCROLL;

    // Registration

    public static <T extends Item> T registerItem(String name, T item) {
        Registry.register(Registry.ITEM, new Identifier(CROPTOPIA_ID, name), item);
        return item;
    }


    public static void croptopiaCompatRegistry() {

        Registry.register(Registry.STATUS_EFFECT, new Identifier(CROPTOPIA_ID, "sugary_healing"), SUGARY_HEALING);
        SUGARY_HEALING_SCROLL = registerItem("sugary_healing_scroll",
                new SugaryHealingScrollItem(new FabricItemSettings().maxCount(16).group(IncantationMod.INCANTATION_GROUP)));

    }


}
