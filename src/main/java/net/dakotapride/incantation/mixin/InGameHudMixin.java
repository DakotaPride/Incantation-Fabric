package net.dakotapride.incantation.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dakotapride.incantation.common.IncantationMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    @Unique
    private static final Identifier RADIANCE_HEARTS = new Identifier(IncantationMod.INCANTATION_ID, "textures/gui/radiance_hearts.png");
    @Unique
    private static final Identifier DUSK_HEARTS = new Identifier(IncantationMod.INCANTATION_ID, "textures/gui/dusk_hearts.png");

    @Inject(method = "drawHeart", at = @At("HEAD"), cancellable = true)
    private void drawHeart(MatrixStack matrices, InGameHud.HeartType type, int x, int y, int v, boolean blinking, boolean halfHeart, CallbackInfo ci) {
        if (!blinking && type == InGameHud.HeartType.NORMAL
                && MinecraftClient.getInstance().cameraEntity instanceof PlayerEntity player
                && (player.hasStatusEffect(IncantationMod.RADIANCE) || (player.hasStatusEffect(IncantationMod.DUSK)))) {
            if ((player.hasStatusEffect(IncantationMod.RADIANCE))) {
                RenderSystem.setShaderTexture(0, RADIANCE_HEARTS);
            } else if ((player.hasStatusEffect(IncantationMod.DUSK))) {
                RenderSystem.setShaderTexture(0, DUSK_HEARTS);
            }
            drawTexture(matrices, x, y, halfHeart ? 9 : 0, v, 9, 9);
            RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE);
            ci.cancel();
        }
    }
}
