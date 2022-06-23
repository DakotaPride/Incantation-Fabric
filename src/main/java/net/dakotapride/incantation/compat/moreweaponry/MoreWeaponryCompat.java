package net.dakotapride.incantation.compat.moreweaponry;


import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.effect.EmptyStatusEffect;
import net.dakotapride.incantation.compat.moreweaponry.item.HarmingResistanceScrollItem;
import net.dakotapride.incantation.mixin.BrewingRecipeRegistryMixin;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MoreWeaponryCompat {
    public static final String MORE_WEAPONRY_ID = ("moreweaponry");

    // Items

    public static StatusEffect HARMING_RESISTANCE = new EmptyStatusEffect(StatusEffectCategory.BENEFICIAL, 0xCEFFF2);
    public static HarmingResistanceScrollItem HARMING_RESISTANCE_SCROLL;

    public static Potion HARMING_RESISTANCE_POTION;

    // Registration

    public static <T extends Item> T registerItem(String name, T item) {
        Registry.register(Registry.ITEM, new Identifier(MORE_WEAPONRY_ID, name), item);
        return item;
    }

    public static <T extends Potion> T registerPotion(String name, T potion) {
        Registry.register(Registry.POTION, new Identifier(MORE_WEAPONRY_ID, name), potion);
        return potion;
    }


    public static void moreWeaponryCompatRegistry() {

        HARMING_RESISTANCE_POTION = registerPotion("harming_resistance",
                new Potion(new StatusEffectInstance(HARMING_RESISTANCE, 220, 0)));
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.MUNDANE,
                Items.SPIDER_EYE, HARMING_RESISTANCE_POTION);

        Registry.register(Registry.STATUS_EFFECT, new Identifier(MORE_WEAPONRY_ID, "harming_resistance"), HARMING_RESISTANCE);
        HARMING_RESISTANCE_SCROLL = registerItem("harming_resistance_scroll",
                new HarmingResistanceScrollItem(new FabricItemSettings().maxCount(16).group(IncantationMod.INCANTATION_GROUP)));

    }

}
