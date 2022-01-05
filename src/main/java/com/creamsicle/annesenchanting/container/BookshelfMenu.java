package com.creamsicle.annesenchanting.container;

import com.creamsicle.annesenchanting.block.ModBlocks;
import com.creamsicle.annesenchanting.blockentities.BookshelfBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BookshelfMenu extends AbstractContainerMenu {

    private final BookshelfBlockEntity blockEntity;
    private final Level level;
    //private final ContainerData data;

    public BookshelfMenu(int windowId, Inventory inv, FriendlyByteBuf extraData) {
        this(windowId, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public BookshelfMenu(int windowId, Inventory inv, BlockEntity entity) {
        super(ModMenus.BOOKSHELF_CONTAINER.get(), windowId);
        checkContainerSize(inv, 4);
        blockEntity = ((BookshelfBlockEntity) entity);
        this.level = inv.player.level;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 55, 23));
            this.addSlot(new SlotItemHandler(handler, 1, 73, 23));
            this.addSlot(new SlotItemHandler(handler, 2, 91, 23));
            this.addSlot(new SlotItemHandler(handler, 3, 109, 23));
        });


    }

    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.BOOKSHELF.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 10 + l * 18, 46 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 10 + i * 18, 104));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }
}
