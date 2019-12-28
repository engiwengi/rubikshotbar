package net.rubikshotbar.impl;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;

public class AllColumnHotbarScroll extends SingleColumnHotbarScroll implements HotbarScroll {
    private int targetSlot;

    AllColumnHotbarScroll(DefaultedList<ItemStack> main, double scrollAmount, ClientPlayerInteractionManager interactionManager, PlayerEntity player) {
        super(main, scrollAmount, interactionManager, player, 0);
        targetSlot = 0;
    }

    public void doScroll() {
        while (getTargetSlot() < 9) {
            super.doScroll();
            incrementTargetSlot();
        }
    }

    @Override
    public int getTargetSlot() {
        return targetSlot;
    }

    private void incrementTargetSlot() {
        targetSlot++;
    }
}
