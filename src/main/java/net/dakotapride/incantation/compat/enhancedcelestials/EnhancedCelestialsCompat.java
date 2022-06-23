package net.dakotapride.incantation.compat.enhancedcelestials;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.effect.EmptyStatusEffect;
import net.dakotapride.incantation.compat.enhancedcelestials.item.HarvestHealingScrollItem;
import net.dakotapride.incantation.compat.moreweaponry.item.HarmingResistanceScrollItem;
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

    public static StatusEffect HARVEST_HEALING = new EmptyStatusEffect(StatusEffectCategory.BENEFICIAL, 0xF4FFDB);
    public static HarvestHealingScrollItem HARVEST_HEALING_SCROLL;
    public static Item MENDING_MOON_CREST_FRUIT;
    public static Item MOON_CREST_FRUIT;

    public static Potion HARVEST_HEALING_POTION;

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

        HARVEST_HEALING_POTION = registerPotion("harvest_healing",
                new Potion(new StatusEffectInstance(HARVEST_HEALING, 220, 0)));
        BrewingRecipeRegistryMixin.invokeRegisterPotionRecipe(Potions.MUNDANE,
                Items.WHEAT, HARVEST_HEALING_POTION);

        Registry.register(Registry.STATUS_EFFECT, new Identifier(ENHANCED_CELESTIALS_ID, "harvest_healing"), HARVEST_HEALING);
        HARVEST_HEALING_SCROLL = registerItem("harvest_healing_scroll",
                new HarvestHealingScrollItem(new FabricItemSettings().maxCount(16).group(IncantationMod.INCANTATION_GROUP)));

        MOON_CREST_FRUIT = registerItem("moon_crest_fruit",
                new Item(new FabricItemSettings().group(IncantationMod.INCANTATION_GROUP).food(
                        new FoodComponent.Builder().hunger((int) 3.0f)
                                .saturationModifier(5.0f)
                                .build())));
        MENDING_MOON_CREST_FRUIT = registerItem("mending_moon_crest_fruit",
                new Item(new FabricItemSettings().group(IncantationMod.INCANTATION_GROUP).food(
                        new FoodComponent.Builder().hunger((int) 5.0f)
                                .saturationModifier(7.0f)
                                .statusEffect(new StatusEffectInstance(HARVEST_HEALING, 400), 1.0f)
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
