package net.rubikshotbar;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.container.SlotActionType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import net.rubikshotbar.config.RubiksHotbarConfigManager;

import java.util.ArrayList;

public class SingleColumnHotbarScroll implements HotbarScroll {
    private final DefaultedList<ItemStack> main;
    private final HotbarScroll.ScrollDirection scrollDirection;
    private final ClientPlayerInteractionManager interactionManager;
    private final int selectedSlot;
    private PlayerEntity player;

    SingleColumnHotbarScroll(DefaultedList<ItemStack> main, double scrollAmount, ClientPlayerInteractionManager interactionManager, PlayerEntity player, int selectedSlot) {
        this.scrollDirection = HotbarScroll.ScrollDirection.from(scrollAmount);
        this.interactionManager = interactionManager;
        this.player = player;
        this.selectedSlot = selectedSlot;
        this.main = main;
    }

    public void doScroll() {
        ArrayList<Integer> slots = new ArrayList<>();
        slots.add(selectedSlot + 18 + 9 * scrollDirection.getValue());
        slots.add(selectedSlot + 18);
        slots.add(selectedSlot + 18 - 9 * scrollDirection.getValue());

        for (Integer slot : slots) {
            if (!main.get(slot).isEmpty() || !RubiksHotbarConfigManager.getConfig().getIgnoreEmpty()) {
                interactionManager.clickSlot(0, slot, selectedSlot, SlotActionType.SWAP, player);
            }
        }
    }
}
