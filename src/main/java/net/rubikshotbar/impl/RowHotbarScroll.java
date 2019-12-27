package net.rubikshotbar.impl;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.container.SlotActionType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;

public class RowHotbarScroll implements HotbarScroll {
    private final DefaultedList<ItemStack> main;
    private final ScrollDirection scrollDirection;
    private final ClientPlayerInteractionManager interactionManager;
    private PlayerEntity player;

    RowHotbarScroll(DefaultedList<ItemStack> main, double scrollAmount, ClientPlayerInteractionManager interactionManager, PlayerEntity player) {
        this.scrollDirection = ScrollDirection.from(scrollAmount);
        this.interactionManager = interactionManager;
        this.player = player;
        this.main = main;
    }

    public void doScroll() {
        for (int i = scrollDirection.getValue() > 0 ? 0 : 9; i <= 9 && i >= 0; i += scrollDirection.getValue()) {
            interactionManager.clickSlot(0, 28, i, SlotActionType.SWAP, player);
        }
    }
}
