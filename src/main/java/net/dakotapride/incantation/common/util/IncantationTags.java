package net.dakotapride.incantation.common.util;

import net.dakotapride.incantation.common.IncantationMod;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class IncantationTags {

    public static class EffectTags {
        public static final TagKey<StatusEffect> RESISTED_EFFECTS = createTag("resisted_effects");

        private static TagKey<StatusEffect> createTag(String name) {
            return TagKey.of(Registry.MOB_EFFECT_KEY, new Identifier(IncantationMod.INCANTATION_ID, name));
        }
    }
}
