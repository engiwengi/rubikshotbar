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
        long handle = MinecraftClient.getInstance().getWindow().getHandle();
        int singleColumn = RubiksHotbarConfigManager.getConfig().getSingleColumn().getKeyCode();
        int allColumn = RubiksHotbarConfigManager.getConfig().getAllColumn().getKeyCode();
        int row = RubiksHotbarConfigManager.getConfig().getRow().getKeyCode();

        if (interactionManager == null) {
            System.out.print("Scroll Hotbar failed. No Interaction Manager found!");
            return null;
        } else if (singleColumn != -1 && InputUtil.isKeyPressed(handle, singleColumn)) {
            return new SingleColumnHotbarScroll(main, scrollAmount, interactionManager, player, selectedSlot);
        } else if (row != -1 && InputUtil.isKeyPressed(handle, row)) {
            return new RowHotbarScroll(scrollAmount, interactionManager, player);
        } else if (allColumn != -1 && InputUtil.isKeyPressed(handle, allColumn)) {
            return new AllColumnHotbarScroll(main, scrollAmount, interactionManager, player);
        } else {
            return null;
        }
    }

    ScrollDirection getScrollDirection();

    int getTargetSlot();

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
