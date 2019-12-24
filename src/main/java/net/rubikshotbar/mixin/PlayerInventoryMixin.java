package net.rubikshotbar.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.container.SlotActionType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

import static java.lang.Math.abs;


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

    @Environment(EnvType.CLIENT)
    @Inject(method = "scrollInHotbar", at = @At("HEAD"), cancellable = true)
    private void onScrollInHotbar(double scrollAmount, CallbackInfo ci) {
        if (MinecraftClient.getInstance().options.keySneak.isPressed()) {
            ArrayList<Integer> slots = new ArrayList<>();
            int scrollDirection = (int) (scrollAmount / abs(scrollAmount));
            slots.add(selectedSlot + 18 + 9 * scrollDirection);
            slots.add(selectedSlot + 18);
            slots.add(selectedSlot + 18 - 9 * scrollDirection);

            ClientPlayerInteractionManager interactionManager = MinecraftClient.getInstance().interactionManager;
            if (interactionManager != null) {
                for (Integer slot : slots) {
                    rubikshotbar$swapHotbar(interactionManager, slot);
                }
            } else {
//              Can this even happen?
                System.out.print("Scroll Hotbar failed. No Interaction Manager found!");
            }
            ci.cancel();
        }
    }

    @Environment(EnvType.CLIENT)
    private void rubikshotbar$swapHotbar(ClientPlayerInteractionManager interactionManager, int slot) {
        if (!main.get(slot).isEmpty()) {
            interactionManager.clickSlot(0, slot, selectedSlot, SlotActionType.SWAP, player);
        }
    }
}
