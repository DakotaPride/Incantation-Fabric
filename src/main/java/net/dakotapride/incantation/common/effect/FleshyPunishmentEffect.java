package net.dakotapride.incantation.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.BlockPos;

public class FleshyPunishmentEffect extends EmptyStatusEffect{
    public FleshyPunishmentEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        BlockPos pos = new BlockPos(entity.getPos());
         if (entity.world.isSkyVisible(pos) && entity.world.isDay() && !entity.isOnFire()) {
            entity.setFireTicks(20000000);
         }
    }

}
