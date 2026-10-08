package com.red.masaadditions.tweakeroo_additions.tweakeroo_mixin;

import com.google.common.collect.ImmutableList;
import com.red.masaadditions.tweakeroo_additions.config.FeatureToggleExtended;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.config.options.BooleanHotkeyGuiWrapper;
import fi.dy.masa.tweakeroo.config.FeatureToggle;
import fi.dy.masa.tweakeroo.gui.GuiConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

@Pseudo
@Mixin(value = GuiConfigs.class, remap = false)
public class MixinGuiConfigs {
    private static final ImmutableList<IHotkeyTogglable> EXTRA_TOGGLES = ImmutableList.copyOf(Arrays.asList(FeatureToggleExtended.values()));

    private BooleanHotkeyGuiWrapper wrapExtraConfig(IHotkeyTogglable config) {
        return new BooleanHotkeyGuiWrapper(config.getName(), config, config.getKeybind());
    }

    // The Tweaks tab and the combined "All" tab both wrap the base toggle list through
    // the same stream, so the extra toggles are appended there rather than at each of
    // the call sites that consume the result
    @Redirect(method = "getConfigs", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;map(Ljava/util/function/Function;)Ljava/util/stream/Stream;"))
    private Stream<BooleanHotkeyGuiWrapper> appendExtraTweaks(Stream<FeatureToggle> toggles, Function<FeatureToggle, BooleanHotkeyGuiWrapper> wrapper) {
        return Stream.concat(toggles.map(wrapper), EXTRA_TOGGLES.stream().map(this::wrapExtraConfig));
    }
}
