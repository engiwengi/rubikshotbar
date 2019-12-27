package net.rubikshotbar.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import net.rubikshotbar.HotbarScroll;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
    @Shadow
    public int selectedSlot;
    @Final
    @Shadow
    public DefaultedList<ItemStack> main;
    @Shadow
    @Final
    public PlayerEntity player;

    private long rubikshotbar$lastScroll;

    @Environment(EnvType.CLIENT)
    @Inject(method = "scrollInHotbar", at = @At("HEAD"), cancellable = true)
    private void rubikshotbar$onScrollInHotbar(double scrollAmount, CallbackInfo ci) {
        HotbarScroll scroll = HotbarScroll.getInstance(main, scrollAmount, MinecraftClient.getInstance().interactionManager, player, selectedSlot);
        if (scroll != null) {
            if (rubikshotbar$hasSufficientTimePassed()) {
                scroll.doScroll();
            }
            ci.cancel();
        }
    }

    @Environment(EnvType.CLIENT)
    private boolean rubikshotbar$hasSufficientTimePassed() {
        boolean enoughTimePassed = System.nanoTime() - rubikshotbar$lastScroll > 50000000;
        if (enoughTimePassed) rubikshotbar$lastScroll = System.nanoTime();
        return enoughTimePassed;
    }
}
