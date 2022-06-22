package net.dakotapride.incantation.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class FleshyPunishmentEffect extends EmptyStatusEffect{
    public FleshyPunishmentEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.world.isDay() && !entity.isFireImmune() && !entity.isWet()) {
            BlockPos pos = new BlockPos(entity.getPos());
            if (entity.getVehicle() instanceof BoatEntity) {
                pos = pos.up();
            }
            if (entity.world.isSkyVisible(pos)) {
                entity.damage(DamageSource.ON_FIRE, 0.5f);
            }
        }
    }

}
