package net.dakotapride.incantation.compat.enhancedcelestials;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.effect.EmptyStatusEffect;
import net.dakotapride.incantation.compat.enhancedcelestials.item.RemedyScrollItem;
import net.dakotapride.incantation.compat.enhancedcelestials.item.unconcealed_scrolls.base.UnconcealedRemedyScroll;
import net.dakotapride.incantation.compat.enhancedcelestials.item.unconcealed_scrolls.extended.UnconcealedExtendedRemedyScroll;
import net.dakotapride.incantation.compat.enhancedcelestials.item.unconcealed_scrolls.frosted.UnconcealedFrostedRemedyScroll;
import net.dakotapride.incantation.compat.enhancedcelestials.item.unconcealed_scrolls.stronger.UnconcealedStrongerRemedyScroll;
import net.dakotapride.incantation.mixin.BrewingRecipeRegistryMixin;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EnhancedCelestialsCompat {
    public static final String ENHANCED_CELESTIALS_ID = ("enhancedcelestials");

    // Items

    public static StatusEffect REMEDY = new EmptyStatusEffect(StatusEffectCategory.BENEFICIAL, 0xF4FFDB);
    public static RemedyScrollItem REMEDY_SCROLL;
    public static Item MENDING_MOON_CREST_FRUIT;
    public static Item MOON_CREST_FRUIT;

    public static UnconcealedRemedyScroll UNCONCEALED_REMEDY_SCROLL;
    public static UnconcealedExtendedRemedyScroll UNCONCEALED_LONG_REMEDY_SCROLL;
    public static UnconcealedStrongerRemedyScroll UNCONCEALED_STRONG_REMEDY_SCROLL;
    public static UnconcealedFrostedRemedyScroll UNCONCEALED_FROSTED_REMEDY_SCROLL;

    public static Potion REMEDY_POTION;

    private static final Identifier GRASS_BLOCK_ID
            = new Identifier("minecraft", "blocks/grass");

    // Registration

    public static <T extends Item> T registerItem(String name, T item) {
        Registry.register(Registry.ITEM, new Identifier(ENHANCED_CELESTIALS_ID, name), item);
        return item;
    }

    public static <T extends Potion> T registerPotion(String name, T potion) {
        Registry.register(Registry.POTION, new Identifier(ENHANCED_CELESTIALS_ID, name), potion);
        return potion;
    }

    public static void enhancedCelestialsCompatRegistry() {

        UNCONCEALED_REMEDY_SCROLL = registerItem("unconcealed_remedy_scroll",
                new UnconcealedRemedyScroll(IncantationMod.IncantationMaterials.PACKED_FABRIC_PATCHING,
                        new FabricItemSettings().maxCount(1).maxDamage(80).group(IncantationMod.INCANTATION_GROUP)));
        UNCONCEALED_FROSTED_REMEDY_SCROLL = registerItem("frosted_unconcealed_remedy_scroll",
                new UnconcealedFrostedRemedyScroll(IncantationMod.IncantationMaterials.PACKED_FABRIC_PATCHING,
                        new FabricItemSettings().maxCount(1).maxDamage(80).group(IncantationMod.INCANTATION_GROUP)));
        UNCONCEALED_LONG_REMEDY_SCROLL = registerItem("long_unconcealed_remedy_scroll",
                new UnconcealedExtendedRemedyScroll(IncantationMod.IncantationMaterials.PACKED_FABRIC_PATCHING,
                        new FabricItemSettings().maxCount(1).maxDamage(80).group(IncantationMod.INCANTATION_GROUP)));
        UNCONCEALED_STRONG_REMEDY_SCROLL = registerItem("strong_unconcealed_remedy_scroll",
                new UnconcealedStrongerRemedyScroll(IncantationMod.IncantationMaterials.PACKED_FABRIC_PATCHING,
                        new FabricItemSettings().maxCount(1).maxDamage(80).group(IncantationMod.INCANTATION_GROUP)));

        REMEDY_POTION = registerPotion("remedy",
                new Potion(new StatusEffectInstance(REMEDY, 220, 0)));
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.MUNDANE,
                Items.WHEAT, REMEDY_POTION);

        Registry.register(Registry.STATUS_EFFECT, new Identifier(ENHANCED_CELESTIALS_ID, "remedy"), REMEDY);
        REMEDY_SCROLL = registerItem("remedy_scroll",
                new RemedyScrollItem(IncantationMod.IncantationMaterials.FABRIC_PATCHING,
                        new FabricItemSettings().maxCount(1).maxDamage(60).group(IncantationMod.INCANTATION_GROUP)));

        MOON_CREST_FRUIT = registerItem("moon_crest_fruit",
                new Item(new FabricItemSettings().group(IncantationMod.INCANTATION_GROUP).food(
                        new FoodComponent.Builder().hunger((int) 3.0f)
                                .saturationModifier(5.0f)
                                .build())));
        MENDING_MOON_CREST_FRUIT = registerItem("mending_moon_crest_fruit",
                new Item(new FabricItemSettings().group(IncantationMod.INCANTATION_GROUP).food(
                        new FoodComponent.Builder().hunger((int) 5.0f)
                                .saturationModifier(7.0f)
                                .statusEffect(new StatusEffectInstance(REMEDY, 400), 1.0f)
                                .build())));

        LootTableEvents.MODIFY.register(((resourceManager, manager, id, supplier, setter) -> {
            if (GRASS_BLOCK_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.015f))
                        .with(ItemEntry.builder(MOON_CREST_FRUIT))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                supplier.pool(poolBuilder.build());
            }

            if (GRASS_BLOCK_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.006f))
                        .with(ItemEntry.builder(MENDING_MOON_CREST_FRUIT))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                supplier.pool(poolBuilder.build());
            }

        }));

    }
}
