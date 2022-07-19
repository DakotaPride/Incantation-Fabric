package net.dakotapride.incantation.compat.pickyourpoison;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.effect.EmptyStatusEffect;
import net.dakotapride.incantation.compat.pickyourpoison.item.EchoingScrollItem;
import net.dakotapride.incantation.compat.pickyourpoison.item.unconcealed_scrolls.base.UnconcealedEchoingScroll;
import net.dakotapride.incantation.compat.pickyourpoison.item.unconcealed_scrolls.extended.UnconcealedExtendedEchoingScroll;
import net.dakotapride.incantation.compat.pickyourpoison.item.unconcealed_scrolls.frosted.UnconcealedFrostedEchoingScroll;
import net.dakotapride.incantation.compat.pickyourpoison.item.unconcealed_scrolls.stronger.UnconcealedStrongerEchoingScroll;
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

    public static StatusEffect ECHOING = new EmptyStatusEffect(StatusEffectCategory.BENEFICIAL, 0xD85252);
    public static EchoingScrollItem ECHOING_SCROLL;

    public static UnconcealedEchoingScroll UNCONCEALED_ECHOING_SCROLL;
    public static UnconcealedExtendedEchoingScroll UNCONCEALED_LONG_ECHOING_SCROLL;
    public static UnconcealedStrongerEchoingScroll UNCONCEALED_STRONG_ECHOING_SCROLL;
    public static UnconcealedFrostedEchoingScroll UNCONCEALED_FROSTED_ECHOING_SCROLL;

    public static Potion ECHOING_POTION;

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

        UNCONCEALED_ECHOING_SCROLL = registerItem("unconcealed_echoing_scroll",
                new UnconcealedEchoingScroll(IncantationMod.IncantationMaterials.PACKED_FABRIC_PATCHING,
                        new FabricItemSettings().maxCount(1).maxDamage(80).group(IncantationMod.INCANTATION_GROUP)));
        UNCONCEALED_FROSTED_ECHOING_SCROLL = registerItem("frosted_unconcealed_echoing_scroll",
                new UnconcealedFrostedEchoingScroll(IncantationMod.IncantationMaterials.PACKED_FABRIC_PATCHING,
                        new FabricItemSettings().maxCount(1).maxDamage(80).group(IncantationMod.INCANTATION_GROUP)));
        UNCONCEALED_LONG_ECHOING_SCROLL = registerItem("long_unconcealed_echoing_scroll",
                new UnconcealedExtendedEchoingScroll(IncantationMod.IncantationMaterials.PACKED_FABRIC_PATCHING,
                        new FabricItemSettings().maxCount(1).maxDamage(80).group(IncantationMod.INCANTATION_GROUP)));
        UNCONCEALED_STRONG_ECHOING_SCROLL = registerItem("strong_unconcealed_echoing_scroll",
                new UnconcealedStrongerEchoingScroll(IncantationMod.IncantationMaterials.PACKED_FABRIC_PATCHING,
                        new FabricItemSettings().maxCount(1).maxDamage(80).group(IncantationMod.INCANTATION_GROUP)));

        ECHOING_POTION = registerPotion("echoing",
                new Potion(new StatusEffectInstance(ECHOING, 220, 0)));
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.MUNDANE,
                Items.FERMENTED_SPIDER_EYE, ECHOING_POTION);

        Registry.register(Registry.STATUS_EFFECT, new Identifier(PICK_YOUR_POISON_ID, "echoing"), ECHOING);
        ECHOING_SCROLL = registerItem("echoing_scroll",
                new EchoingScrollItem(IncantationMod.IncantationMaterials.FABRIC_PATCHING,
                        new FabricItemSettings().maxCount(1).group(IncantationMod.INCANTATION_GROUP)));

    }

}
