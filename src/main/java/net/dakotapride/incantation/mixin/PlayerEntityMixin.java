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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    private final PlayerEntity playerEntity = (PlayerEntity) (Object) this;

    public PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
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
