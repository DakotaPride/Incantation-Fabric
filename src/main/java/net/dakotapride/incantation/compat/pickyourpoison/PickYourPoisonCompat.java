package net.dakotapride.incantation.compat.pickyourpoison;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.effect.EmptyStatusEffect;
import net.dakotapride.incantation.compat.pickyourpoison.item.ForeignPoisonResistanceScrollItem;
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

    public static StatusEffect FOREIGN_POISON_RESISTANCE = new EmptyStatusEffect(StatusEffectCategory.BENEFICIAL, 0xD85252);
    public static ForeignPoisonResistanceScrollItem FOREIGN_POISON_RESISTANCE_SCROLL;

    public static Potion FOREIGN_POISON_RESISTANCE_POTION;

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
        FOREIGN_POISON_RESISTANCE_POTION = registerPotion("foreign_poison_resistance",
                new Potion(new StatusEffectInstance(FOREIGN_POISON_RESISTANCE, 220, 0)));
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.MUNDANE,
                Items.FERMENTED_SPIDER_EYE, FOREIGN_POISON_RESISTANCE_POTION);

        Registry.register(Registry.STATUS_EFFECT, new Identifier(PICK_YOUR_POISON_ID, "foreign_poison_resistance"), FOREIGN_POISON_RESISTANCE);
        FOREIGN_POISON_RESISTANCE_SCROLL = registerItem("foreign_poison_resistance_scroll",
                new ForeignPoisonResistanceScrollItem(new FabricItemSettings().maxCount(16).group(IncantationMod.INCANTATION_GROUP)));

    }

}
