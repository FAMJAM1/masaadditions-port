package com.red.masaadditions.tweakeroo_additions.mixin;

import com.red.masaadditions.tweakeroo_additions.config.ConfigsExtended;
import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import com.red.masaadditions.tweakeroo_additions.util.MiscUtils;
import fi.dy.masa.tweakeroo.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelEventHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Breaking a block is a level event now instead of a call into the particle
// engine, so the tweak intercepts the event before vanilla spawns anything
@Mixin(LevelEventHandler.class)
public class MixinParticleManager {
    @Unique
    private static final int PARTICLES_DESTROY_BLOCK = 2001;

    @Shadow
    @Final
    private Minecraft minecraft;

    // Declared as the general level here, so the shadow has to match that and
    // the cast happens at the one place a client level is actually wanted
    @Shadow
    @Final
    private Level level;

    // From 1.12 Tweakeroo by Masa
    @Inject(method = "levelEvent", at = @At("HEAD"), cancellable = true)
    private void onAddBlockDestroyEffects1(int type, BlockPos pos, int data, CallbackInfo ci) {
        if (type != PARTICLES_DESTROY_BLOCK) {
            return;
        }

        if (!Configs.Disable.DISABLE_BLOCK_BREAK_PARTICLES.getBooleanValue() && FeatureToggleExtended.TWEAK_BLOCK_BREAKING_PARTICLES.getBooleanValue()) {
            BlockState state = Block.stateById(data);

            if (!state.isAir()) {
                MiscUtils.addCustomBlockBreakingParticles(this.minecraft.particleEngine, (ClientLevel) this.level, this.level.getRandom(), pos, state);
                ci.cancel();
            }
        }
    }
}
