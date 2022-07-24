package net.dakotapride.incantation.mixin;

import net.dakotapride.incantation.common.IncantationMod;
import net.dakotapride.incantation.common.soulsComeAlive.SoulsComeAlive;
import net.dakotapride.incantation.common.soulsComeAlive.entity.source.IncantationDamageSource;
import net.dakotapride.incantation.compat.moreweaponry.MoreWeaponryCompat;
import net.dakotapride.incantation.compat.pickyourpoison.PickYourPoisonCompat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow protected abstract int getNextAirOnLand(int air);

    @Shadow protected abstract int getNextAirUnderwater(int air);

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect, @Nullable Entity source);

    private final LivingEntity livingEntity = (LivingEntity) (Object) this;
    private final LivingEntity attacker = livingEntity.getAttacker();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    private void dropSoulFragments$Type() {
        if (attacker instanceof PlayerEntity) {
            if (livingEntity instanceof PlayerEntity) {
                dropStack(new ItemStack(SoulsComeAlive.PLAYER_SOUL_FRAGMENT));
            }
            if (livingEntity instanceof HostileEntity) {
                dropStack(new ItemStack(SoulsComeAlive.HOSTILE_SOUL_FRAGMENT));
            }
            if (livingEntity instanceof PassiveEntity) {
                dropStack(new ItemStack(SoulsComeAlive.PASSIVE_SOUL_FRAGMENT));
            }
        }
    }

    @Inject(method = "travel", at = @At("TAIL"))
    private void incantation$hasAngelsStiffnessEffect(Vec3d movementInput, CallbackInfo ci) {
        if (livingEntity.canMoveVoluntarily() || this.isLogicalSideForUpdatingMovement()) {
            double d = 0.08D;
            boolean bl = this.getVelocity().y <= 0.0D;
            if (bl && livingEntity.hasStatusEffect(SoulsComeAlive.ANGEL_STIFFNESS)) {
                d = 0.1D;
                this.onLanding();
            }
        }
    }

    @Inject(method = "canHaveStatusEffect", at = @At("TAIL"), cancellable = true)
    private void canHaveStatusEffect(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        if (livingEntity.hasStatusEffect(SoulsComeAlive.SIREN_WARNING)) {
            StatusEffect statusEffect = effect.getEffectType();
            if (statusEffect == StatusEffects.REGENERATION || statusEffect == StatusEffects.POISON) {
                cir.setReturnValue(false);
            }
        }

    }

    private boolean isBeingRainedOn() {
        BlockPos blockPos = this.getBlockPos();
        return this.world.hasRain(blockPos) || this.world.hasRain(new BlockPos((double)blockPos.getX(), this.getBoundingBox().maxY, (double)blockPos.getZ()));
    }

    /* Code belongs to Apace100
    Curseforge: https://www.curseforge.com/minecraft/mc-mods/origins
    Modrinth: https://modrinth.com/mod/origins
    */
    private void hasSirenWarningEffect() {
        if(this.hasStatusEffect(SoulsComeAlive.SIREN_WARNING)) {
            if(!this.isSubmergedInWater()) {
                if(!this.isBeingRainedOn()) {
                    int landGain = this.getNextAirOnLand(0);
                    this.setAir(this.getNextAirUnderwater(this.getAir()) - landGain);
                    if (this.getAir() == -20) {
                        this.setAir(0);

                        for(int i = 0; i < 8; ++i) {
                            double f = this.random.nextDouble() - this.random.nextDouble();
                            double g = this.random.nextDouble() - this.random.nextDouble();
                            double h = this.random.nextDouble() - this.random.nextDouble();
                            this.world.addParticle(ParticleTypes.BUBBLE, this.getParticleX(0.5), this.getEyeY() + this.random.nextGaussian() * 0.08D, this.getParticleZ(0.5), f * 0.5F, g * 0.5F + 0.25F, h * 0.5F);
                        }

                        this.damage(IncantationDamageSource.SIREN_BREATHING, 1.0F);
                    }
                } else {
                    int landGain = this.getNextAirOnLand(0);
                    this.setAir(this.getAir() - landGain);
                }
            } else if(this.getAir() < this.getMaxAir()){
                this.setAir(this.getNextAirOnLand(this.getAir()));
            }

            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 40, 0));
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        hasSirenWarningEffect();

        if (livingEntity.hasStatusEffect(SoulsComeAlive.SOUL_BLESSING)) {
            livingEntity.removeStatusEffect(SoulsComeAlive.DEVILISH_BARGAIN);
        } else if (livingEntity.hasStatusEffect(SoulsComeAlive.DEVILISH_BARGAIN)) {
            float v = 10.0f;
            if (livingEntity.getHealth() > v) {
                livingEntity.setHealth(v);
            }
        }

        if (livingEntity.hasStatusEffect(IncantationMod.TOLERANCE)) {
            livingEntity.clearStatusEffects();
        }

        if (livingEntity.hasStatusEffect(PickYourPoisonCompat.ECHOING)) {
            if (!(livingEntity.getAttacker() == null)) {
                livingEntity.getActiveStatusEffects().forEach((statusEffect, statusEffectInstance) -> {
                    if (!attacker.hasStatusEffect(statusEffect) || (attacker.getStatusEffect(statusEffect) != null
                            && Objects.requireNonNull(attacker.getStatusEffect(statusEffect)).getAmplifier() <= statusEffectInstance.getAmplifier())) {
                        attacker.addStatusEffect(new StatusEffectInstance(statusEffect, 60, statusEffectInstance.getAmplifier()));
                    }
                });
            }
        }

        if (livingEntity.hasStatusEffect(MoreWeaponryCompat.UNWOUNDED)) {
            livingEntity.removeStatusEffect(StatusEffects.POISON);
            livingEntity.removeStatusEffect(StatusEffects.WITHER);
            livingEntity.removeStatusEffect(StatusEffects.INSTANT_DAMAGE);
        }

        if (livingEntity.hasStatusEffect(IncantationMod.RA_WRATH)) {
            livingEntity.setFireTicks(40);
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 300, 2));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 200, 1));
            livingEntity.removeStatusEffect(StatusEffects.HUNGER);
        }

        if (livingEntity.hasStatusEffect(IncantationMod.BLEAK)) {
            livingEntity.setFrozenTicks(0);
        }
    }

    @Inject(method = "dropLoot", at = @At("TAIL"))
    private void incantation$dropSoulFragment(DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
        dropSoulFragments$Type();
    }

}
