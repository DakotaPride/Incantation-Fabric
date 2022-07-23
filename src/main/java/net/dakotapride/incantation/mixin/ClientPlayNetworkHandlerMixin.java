package net.dakotapride.incantation.mixin;

import net.dakotapride.incantation.common.soulsComeAlive.SoulsComeAlive;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    private static ClientPlayNetworkHandler clientPlayNetworkHandler;
    private final ClientWorld world;
    private final MinecraftClient client;

    public ClientPlayNetworkHandlerMixin(ClientWorld world, MinecraftClient client) {
        this.world = world;
        this.client = client;
    }

    private static ItemStack getActiveTotemOfSalvation(PlayerEntity player) {
        for (Hand hand : Hand.values()) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (itemStack.isOf(SoulsComeAlive.TOTEM_OF_SALVATION)) {
                return itemStack;
            }
        }
        return new ItemStack(SoulsComeAlive.TOTEM_OF_SALVATION);
    }



    @Inject(method = "onEntityStatus", at = @At("TAIL"))
    private void onEntityStatus(EntityStatusS2CPacket packet, CallbackInfo ci) {
        NetworkThreadUtils.forceMainThread(packet, clientPlayNetworkHandler, this.client);
        Entity entity = packet.getEntity(this.world);
        if (entity != null) {
            if (packet.getStatus() == 40) {
                this.world.playSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PARTICLE_SOUL_ESCAPE, entity.getSoundCategory(), 3.0F, 1.5F, false);
                if (entity == this.client.player) {
                    this.client.gameRenderer.showFloatingItem(getActiveTotemOfSalvation(this.client.player));
                }
            }
        }
    }

}
