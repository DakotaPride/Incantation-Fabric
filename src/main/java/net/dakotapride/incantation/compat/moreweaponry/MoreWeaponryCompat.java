package net.dakotapride.incantation.compat.moreweaponry;


import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.effect.EmptyStatusEffect;
import net.dakotapride.incantation.compat.moreweaponry.item.UnwoundedScrollItem;
import net.dakotapride.incantation.compat.moreweaponry.item.unconcealed_scrolls.base.UnconcealedUnwoundedScroll;
import net.dakotapride.incantation.compat.moreweaponry.item.unconcealed_scrolls.extended.UnconcealedExtendedUnwoundedScroll;
import net.dakotapride.incantation.compat.moreweaponry.item.unconcealed_scrolls.frosted.UnconcealedFrostedUnwoundedScroll;
import net.dakotapride.incantation.compat.moreweaponry.item.unconcealed_scrolls.stronger.UnconcealedStrongerUnwoundedScroll;
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

    public static StatusEffect UNWOUNDED = new EmptyStatusEffect(StatusEffectCategory.BENEFICIAL, 0xCEFFF2);
    public static UnwoundedScrollItem UNWOUNDED_SCROLL;

    public static UnconcealedUnwoundedScroll UNCONCEALED_UNWOUNDED_SCROLL;
    public static UnconcealedExtendedUnwoundedScroll UNCONCEALED_LONG_UNWOUNDED_SCROLL;
    public static UnconcealedStrongerUnwoundedScroll UNCONCEALED_STRONG_UNWOUNDED_SCROLL;
    public static UnconcealedFrostedUnwoundedScroll UNCONCEALED_FROSTED_UNWOUNDED_SCROLL;

    public static Potion UNWOUNDED_POTION;

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

        UNCONCEALED_UNWOUNDED_SCROLL = registerItem("unconcealed_unwounded_scroll",
                new UnconcealedUnwoundedScroll(IncantationMod.IncantationMaterials.PACKED_FABRIC_PATCHING,
                        new FabricItemSettings().maxCount(1).maxDamage(80).group(IncantationMod.INCANTATION_GROUP)));
        UNCONCEALED_FROSTED_UNWOUNDED_SCROLL = registerItem("frosted_unconcealed_unwounded_scroll",
                new UnconcealedFrostedUnwoundedScroll(IncantationMod.IncantationMaterials.PACKED_FABRIC_PATCHING,
                        new FabricItemSettings().maxCount(1).maxDamage(80).group(IncantationMod.INCANTATION_GROUP)));
        UNCONCEALED_LONG_UNWOUNDED_SCROLL = registerItem("long_unconcealed_unwounded_scroll",
                new UnconcealedExtendedUnwoundedScroll(IncantationMod.IncantationMaterials.PACKED_FABRIC_PATCHING,
                        new FabricItemSettings().maxCount(1).maxDamage(80).group(IncantationMod.INCANTATION_GROUP)));
        UNCONCEALED_STRONG_UNWOUNDED_SCROLL = registerItem("strong_unconcealed_unwounded_scroll",
                new UnconcealedStrongerUnwoundedScroll(IncantationMod.IncantationMaterials.PACKED_FABRIC_PATCHING,
                        new FabricItemSettings().maxCount(1).maxDamage(80).group(IncantationMod.INCANTATION_GROUP)));

        UNWOUNDED_POTION = registerPotion("unwounded",
                new Potion(new StatusEffectInstance(UNWOUNDED, 220, 0)));
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.MUNDANE,
                Items.SPIDER_EYE, UNWOUNDED_POTION);

        Registry.register(Registry.STATUS_EFFECT, new Identifier(MORE_WEAPONRY_ID, "unwounded"), UNWOUNDED);
        UNWOUNDED_SCROLL = registerItem("unwounded_scroll",
                new UnwoundedScrollItem(IncantationMod.IncantationMaterials.FABRIC_PATCHING,
                        new FabricItemSettings().maxCount(1).maxDamage(60).group(IncantationMod.INCANTATION_GROUP)));

    }

}
