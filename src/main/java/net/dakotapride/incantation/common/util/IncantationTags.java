package net.dakotapride.incantation.common.util;

import net.dakotapride.incantation.common.IncantationMod;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class IncantationTags {

        public static final TagKey<Item> HARVESTABLE_FOODS = createCommonItemTag("harvestable_healing_foods");
        public static final TagKey<StatusEffect> PICK_YOUR_POISON_EFFECTS = createStatusEffectTag("pick_your_poison_effects");

        private static TagKey<Item> createCommonItemTag(String name) {
            return TagKey.of(Registry.ITEM_KEY, new Identifier("c", name));
        }

        private static TagKey<StatusEffect> createStatusEffectTag(String name) {
            return TagKey.of(Registry.MOB_EFFECT_KEY, new Identifier(IncantationMod.INCANTATION_ID, name));
        }


}
