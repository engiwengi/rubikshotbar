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

    private long rubikshotbar$lastScroll;

    @Environment(EnvType.CLIENT)
    @Inject(method = "scrollInHotbar", at = @At("HEAD"), cancellable = true)
    private void rubikshotbar$onScrollInHotbar(double scrollAmount, CallbackInfo ci) {
        if (MinecraftClient.getInstance().options.keySneak.isPressed()) {
//          Stops players from using a freewheel mouse and causing a huge amount of packets to be sent
            if (rubikshotbar$hasSufficientTimePassed()) {
                int scrollDirection = (int) (scrollAmount / abs(scrollAmount));
                ClientPlayerInteractionManager interactionManager = MinecraftClient.getInstance().interactionManager;

                if (interactionManager != null) {
                    if (MinecraftClient.getInstance().options.keySprint.isPressed()) {
                        rubikshotbar$doHorizontalScroll(interactionManager, scrollDirection);
                    } else {
                        rubikshotbar$doVerticalScroll(interactionManager, scrollDirection);
                    }
                } else {
                    System.out.print("Scroll Hotbar failed. No Interaction Manager found!");
                }
            }
            ci.cancel();
        }
    }

    @Environment(EnvType.CLIENT)
    private void rubikshotbar$swapTo(ClientPlayerInteractionManager interactionManager, int slot, int toSlot) {
        interactionManager.clickSlot(0, slot, toSlot, SlotActionType.SWAP, player);
    }

    @Environment(EnvType.CLIENT)
    private boolean rubikshotbar$hasSufficientTimePassed() {
        boolean enoughTimePassed = System.nanoTime() - rubikshotbar$lastScroll > 50000000;
        if (enoughTimePassed) rubikshotbar$lastScroll = System.nanoTime();
        return enoughTimePassed;
    }

    @Environment(EnvType.CLIENT)
    private void rubikshotbar$doVerticalScroll(ClientPlayerInteractionManager interactionManager, int scrollDirection) {
        ArrayList<Integer> slots = new ArrayList<>();
        slots.add(selectedSlot + 18 + 9 * scrollDirection);
        slots.add(selectedSlot + 18);
        slots.add(selectedSlot + 18 - 9 * scrollDirection);

        for (Integer slot : slots) {
            if (!main.get(slot).isEmpty()) {
                rubikshotbar$swapTo(interactionManager, slot, selectedSlot);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    private void rubikshotbar$doHorizontalScroll(ClientPlayerInteractionManager interactionManager, int scrollDirection) {
        for (int i = scrollDirection > 0 ? 0 : 9; i <= 9 && i >= 0; i += scrollDirection) {
            rubikshotbar$swapTo(interactionManager, 28, i);
        }
    }
}
