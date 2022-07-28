package net.dakotapride.incantation.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    private final PlayerEntity playerEntity = (PlayerEntity) (Object) this;

    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    private static int getLevelExperience(int experienceLevel) {
        if (experienceLevel >= 30) {
            return 112 + (experienceLevel - 30) * 9;
        } else {
            return experienceLevel >= 15 ? 37 + (experienceLevel - 15) * 5 : 7 + experienceLevel * 2;
        }
    }

    private static int getExperienceToLevel(int level) {
        int experience = 0;
        for (int i = 0; i < level; i++) {
            experience += getLevelExperience(i);
        }
        return experience;
    }

    @Inject(method = "getNextLevelExperience", at = @At("TAIL"), cancellable = true)
    private void getNextLevelExperience(CallbackInfoReturnable<Integer> cir) {
        int playerExperienceLevel = playerEntity.experienceLevel;

        if (playerExperienceLevel == 10) {
            playerEntity.giveItemStack(new ItemStack(Items.OBSIDIAN, 16));
            cir.cancel();
        } else if (playerExperienceLevel == 50) {
            playerEntity.giveItemStack(new ItemStack(Items.NETHER_WART, 48));
            cir.cancel();
        } else if (playerExperienceLevel == 100) {
            playerEntity.giveItemStack(new ItemStack(Items.GLASS_BOTTLE, 32));
            cir.cancel();
        } else if (playerExperienceLevel == 150) {
            playerEntity.giveItemStack(new ItemStack(Items.SOUL_SOIL, 24));
            cir.cancel();
        } else if (playerExperienceLevel == 200) {
            playerEntity.giveItemStack(new ItemStack(Items.BOOK, 56));
            cir.cancel();
        }
    }

}
