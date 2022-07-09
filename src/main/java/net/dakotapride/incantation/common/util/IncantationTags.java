package net.dakotapride.incantation.common.util;

import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class IncantationTags {

        public static final TagKey<Item> HARVESTABLE_FOODS = createCommonItemTag("harvestable_healing_foods");

        private static TagKey<Item> createCommonItemTag(String name) {
            return TagKey.of(Registry.ITEM_KEY, new Identifier("c", name));
        }


}
