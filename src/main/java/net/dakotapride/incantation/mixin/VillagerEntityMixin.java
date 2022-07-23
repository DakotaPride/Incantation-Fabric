package net.dakotapride.incantation.mixin;

import net.dakotapride.incantation.common.soulsComeAlive.SoulsComeAlive;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.TradeOffer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {
    private final VillagerEntity villagerEntity = (VillagerEntity) (Object) this;

    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "prepareOffersFor", at = @At("TAIL"))
    private void prepareOffersFor(PlayerEntity player, CallbackInfo ci) {
        int i = villagerEntity.getReputation(player) - 10;
        if (i != 0) {
            Iterator var3 = this.getOffers().iterator();

            if (player.hasStatusEffect(SoulsComeAlive.MIDAS_FAVOUR)) {
                while(var3.hasNext()) {
                    TradeOffer tradeOffer = (TradeOffer)var3.next();
                    tradeOffer.increaseSpecialPrice(-MathHelper.floor((float)i * tradeOffer.getPriceMultiplier()));
                }
            }
        }
        if (player.hasStatusEffect(SoulsComeAlive.DEVILISH_BARGAIN)) {
            StatusEffectInstance statusEffectInstance = player.getStatusEffect(SoulsComeAlive.DEVILISH_BARGAIN);
            int j = statusEffectInstance.getAmplifier();
            Iterator var5 = villagerEntity.getOffers().iterator();

            while(var5.hasNext()) {
                TradeOffer tradeOffer2 = (TradeOffer)var5.next();
                double d = 0.5D + 0.0937D * (double)j;
                int k = (int)Math.floor(d * (double)tradeOffer2.getOriginalFirstBuyItem().getCount());
                tradeOffer2.increaseSpecialPrice(-Math.max(k, 1));
            }
        }
    }

}
