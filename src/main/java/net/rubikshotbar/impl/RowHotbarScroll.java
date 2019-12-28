package net.rubikshotbar.impl;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.container.SlotActionType;
import net.minecraft.entity.player.PlayerEntity;

public class RowHotbarScroll implements HotbarScroll {
    private final ScrollDirection scrollDirection;
    private final ClientPlayerInteractionManager interactionManager;
    private PlayerEntity player;

    RowHotbarScroll(double scrollAmount, ClientPlayerInteractionManager interactionManager, PlayerEntity player) {
        this.scrollDirection = ScrollDirection.from(scrollAmount);
        this.interactionManager = interactionManager;
        this.player = player;
    }

    public void doScroll() {
        for (int i = getScrollDirection().getValue() > 0 ? 0 : 7; i <= 7 && i >= 0; i += getScrollDirection().getValue()) {
            interactionManager.clickSlot(0, 44, i, SlotActionType.SWAP, player);
        }
    }

    @Override
    public ScrollDirection getScrollDirection() {
        return scrollDirection;
    }

    @Override
    public int getTargetSlot() {
        return 0;
    }
}
