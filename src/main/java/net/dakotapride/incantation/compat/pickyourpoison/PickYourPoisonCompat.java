package net.dakotapride.incantation.compat.pickyourpoison;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.effect.EmptyStatusEffect;
import net.dakotapride.incantation.compat.pickyourpoison.item.ReflectionResistanceScrollItem;
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

public class PickYourPoisonCompat {
    public static final String PICK_YOUR_POISON_ID = ("pickyourpoison");

    // Items

    public static StatusEffect REFLECTION_RESISTANCE = new EmptyStatusEffect(StatusEffectCategory.BENEFICIAL, 0xD85252);
    public static ReflectionResistanceScrollItem REFLECTION_RESISTANCE_SCROLL;

    public static Potion REFLECTION_RESISTANCE_POTION;

    // Registration

    public static <T extends Item> T registerItem(String name, T item) {
        Registry.register(Registry.ITEM, new Identifier(PICK_YOUR_POISON_ID, name), item);
        return item;
    }

    public static <T extends Potion> T registerPotion(String name, T potion) {
        Registry.register(Registry.POTION, new Identifier(PICK_YOUR_POISON_ID, name), potion);
        return potion;
    }

    public static void pickYourPoisonCompatRegistry() {
        REFLECTION_RESISTANCE_POTION = registerPotion("reflection_resistance",
                new Potion(new StatusEffectInstance(REFLECTION_RESISTANCE, 220, 0)));
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.MUNDANE,
                Items.FERMENTED_SPIDER_EYE, REFLECTION_RESISTANCE_POTION);

        Registry.register(Registry.STATUS_EFFECT, new Identifier(PICK_YOUR_POISON_ID, "reflection_resistance"), REFLECTION_RESISTANCE);
        REFLECTION_RESISTANCE_SCROLL = registerItem("reflection_resistance_scroll",
                new ReflectionResistanceScrollItem(new FabricItemSettings().maxCount(16).group(IncantationMod.INCANTATION_GROUP)));

    }

}
