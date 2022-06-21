package net.dakotapride.incantation.common.util;

import net.dakotapride.incantation.compat.moreweaponry.MoreWeaponryCompat;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class IncantationTags {

        public static final TagKey<Item> MOREWEAPONRY_HARVESTABLE_FOODS = createMoreWeaponryItemTag("harvestable_healing_foods");
        public static final TagKey<Item> CROPTOPIA_HARVESTABLE_FOODS = createCroptopiaItemTag("harvestable_healing_foods");
        public static final TagKey<Item> HARVESTABLE_FOODS = createCommonItemTag("harvestable_healing_foods");

        private static TagKey<Item> createMoreWeaponryItemTag(String name) {
            return TagKey.of(Registry.ITEM_KEY, new Identifier(MoreWeaponryCompat.MORE_WEAPONRY_ID, name));
        }

        private static TagKey<Item> createCroptopiaItemTag(String name) {
            return TagKey.of(Registry.ITEM_KEY, new Identifier("croptopia", name));
        }

        private static TagKey<Item> createCommonItemTag(String name) {
            return TagKey.of(Registry.ITEM_KEY, new Identifier("c", name));
        }


}
