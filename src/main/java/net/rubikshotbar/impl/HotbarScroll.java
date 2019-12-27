package net.rubikshotbar.impl;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import net.rubikshotbar.config.RubiksHotbarConfigManager;

public interface HotbarScroll {
    void doScroll();

    static HotbarScroll getInstance(DefaultedList<ItemStack> main, double scrollAmount, ClientPlayerInteractionManager interactionManager, PlayerEntity player, int selectedSlot) {
        if (interactionManager == null) {
            System.out.print("Scroll Hotbar failed. No Interaction Manager found!");
            return null;
        } else if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), RubiksHotbarConfigManager.getConfig().getSingleColumn().getKeyCode())) {
            return new SingleColumnHotbarScroll(main, scrollAmount, interactionManager, player, selectedSlot);
        } else if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), RubiksHotbarConfigManager.getConfig().getRow().getKeyCode())) {
            return new RowHotbarScroll(main, scrollAmount, interactionManager, player);
        } else if (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), RubiksHotbarConfigManager.getConfig().getAllColumn().getKeyCode())) {
            return new AllColumnHotbarScroll(main, scrollAmount, interactionManager, player);
        } else {
            return null;
        }
    }

    enum ScrollDirection {
        UP(1),
        DOWN(-1);

        final int value;

        ScrollDirection(int i) {
            this.value = i;
        }

        static ScrollDirection from(double scrollAmount) {
            return scrollAmount > 0 ? ScrollDirection.UP : ScrollDirection.DOWN;
        }

        public int getValue() {
            return this.value;
        }
    }
}
