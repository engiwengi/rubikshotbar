package net.rubikshotbar;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.container.SlotActionType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import net.rubikshotbar.config.RubiksHotbarConfigManager;

import java.util.ArrayList;

public class AllColumnHotbarScroll implements HotbarScroll {
    private final DefaultedList<ItemStack> main;
    private final ScrollDirection scrollDirection;
    private final ClientPlayerInteractionManager interactionManager;
    private PlayerEntity player;

    AllColumnHotbarScroll(DefaultedList<ItemStack> main, double scrollAmount, ClientPlayerInteractionManager interactionManager, PlayerEntity player) {
        this.scrollDirection = ScrollDirection.from(scrollAmount);
        this.interactionManager = interactionManager;
        this.player = player;
        this.main = main;
    }

    public void doScroll() {
        for (int i = 0; i < 9; i++) {
            ArrayList<Integer> slots = new ArrayList<>();
            slots.add(i + 18 + 9 * scrollDirection.getValue());
            slots.add(i + 18);
            slots.add(i + 18 - 9 * scrollDirection.getValue());

            for (Integer slot : slots) {
                if (!main.get(slot).isEmpty() || !RubiksHotbarConfigManager.getConfig().getIgnoreEmpty()) {
                    interactionManager.clickSlot(0, slot, i, SlotActionType.SWAP, player);
                }
            }
        }
    }
}
