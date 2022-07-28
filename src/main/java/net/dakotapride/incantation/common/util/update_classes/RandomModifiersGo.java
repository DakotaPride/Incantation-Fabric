package net.dakotapride.incantation.common.util.update_classes;

import net.dakotapride.incantation.common.item.EnchantedItem;
import net.dakotapride.incantation.common.item.artefacts.SweetenedModifierItemPack;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import static net.dakotapride.incantation.common.IncantationMod.*;

public class RandomModifiersGo {

    public static Item CINNAMON_BUN;
    public static EnchantedItem ENCHANTED_CINNAMON_BUN;
    public static Item GOLDEN_CINNAMON_BUN;
    public static EnchantedItem ENCHANTED_GOLDEN_CINNAMON_BUN;
    public static SweetenedModifierItemPack SWEETENED_MODIFIER_ITEM_PACK;
    public static Item t;

    public static <T extends Item> T registerItem(String name, T item) {
        Registry.register(Registry.ITEM, new Identifier(INCANTATION_ID, name), item);
        return item;
    }

    public static void registerRandomModifiersGoFeatures() {
        CINNAMON_BUN = registerItem("cinnamon_bun",
                new Item(new FabricItemSettings().food(new FoodComponent.Builder().saturationModifier(1.0f).hunger((int) 1.0f)
                        .build()).group(INCANTATION_GROUP)));
        ENCHANTED_CINNAMON_BUN = registerItem("enchanted_cinnamon_bun",
                new EnchantedItem(new FabricItemSettings().food(new FoodComponent.Builder().saturationModifier(3.0f).hunger((int) 4.0f)
                        .build()).group(INCANTATION_GROUP)));
        GOLDEN_CINNAMON_BUN = registerItem("golden_cinnamon_bun",
                new Item(new FabricItemSettings().food(new FoodComponent.Builder().saturationModifier(1.0f).hunger((int) 1.0f)
                        .build()).group(INCANTATION_GROUP)));
        ENCHANTED_GOLDEN_CINNAMON_BUN = registerItem("enchanted_golden_cinnamon_bun",
                new EnchantedItem(new FabricItemSettings().food(new FoodComponent.Builder().saturationModifier(3.0f).hunger((int) 4.0f)
                        .build()).group(INCANTATION_GROUP)));
        SWEETENED_MODIFIER_ITEM_PACK = registerItem("sweetened_modifier_pack",
                new SweetenedModifierItemPack(new FabricItemSettings().rarity(Rarity.UNCOMMON).group(INCANTATION_GROUP)));
    }

}
