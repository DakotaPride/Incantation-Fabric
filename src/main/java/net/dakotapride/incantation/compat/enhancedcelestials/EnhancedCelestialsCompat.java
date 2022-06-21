package net.dakotapride.incantation.compat.enhancedcelestials;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.effect.EmptyStatusEffect;
import net.dakotapride.incantation.compat.enhancedcelestials.item.HarvestHealingScrollItem;
import net.dakotapride.incantation.compat.moreweaponry.item.HarmingResistanceScrollItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EnhancedCelestialsCompat {
    public static final String ENHANCED_CELESTIALS_ID = ("enhancedcelestials");

    // Items

    public static StatusEffect HARVEST_HEALING = new EmptyStatusEffect(StatusEffectCategory.BENEFICIAL, 0xF4FFDB);
    public static HarvestHealingScrollItem HARVEST_HEALING_SCROLL;
    public static Item MENDING_MOON_CREST_FRUIT;
    public static Item MOON_CREST_FRUIT;

    // Registration

    public static <T extends Item> T registerItem(String name, T item) {
        Registry.register(Registry.ITEM, new Identifier(ENHANCED_CELESTIALS_ID, name), item);
        return item;
    }

    public static void enhancedCelestialsCompatRegistry() {

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

    }
}
