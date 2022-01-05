package com.creamsicle.annesenchanting.container;

/*
*
*       this.addSlot(new Slot(craftSlots, 0, 34, 28));
        this.addSlot(new Slot(craftSlots, 1, 52, 28));
        this.addSlot(new Slot(craftSlots, 2, 43, 46));
        this.addSlot(new Slot(resultSlots, 3, 124, 35));
*
* */

import com.creamsicle.annesenchanting.data.recipes.InscribingTableRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.Optional;


public class InscribingTableMenu extends AbstractContainerMenu  {

    private BlockPos blockPos;
    private Player playerEntity;
    private IItemHandler playerInventory;
    private final ResultContainer resultSlots = new ResultContainer();
    private final SimpleContainer inputSlots = new SimpleContainer(3) {
        /**
         * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think
         * it hasn't changed and skip it.
         */
        public void setChanged() {
            super.setChanged();
            InscribingTableMenu.this.slotsChanged(this);
        }

    };

    public InscribingTableMenu(int windowId, BlockPos pos, Inventory playerInventory, Player player) {
        super(ModMenus.INSCRIBING_TABLE_CONTAINER.get(), windowId);
        this.blockPos = pos;
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.addSlot(new Slot(inputSlots, 0, 34, 28) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.getItem() == Items.ENCHANTED_BOOK;
                }
            }
        );
        this.addSlot(new Slot(inputSlots, 1, 52, 28){
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.getItem() == Items.ENCHANTED_BOOK;
            }
        });
        this.addSlot(new Slot(inputSlots, 2, 43, 46));
        this.addSlot(new Slot(resultSlots, 0, 124, 35){
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return false;
            }

            @Override
            public void onTake(Player pPlayer, ItemStack pStack) {
                InscribingTableMenu.this.outputTaken();
            }
        });
        layoutPlayerInventorySlots(10, 80);
    }

    public void outputTaken(){
        this.inputSlots.removeItem(0,1);
        this.inputSlots.removeItem(1,1);
        this.inputSlots.removeItem(2,1);
    }

    public void slotsChanged(SimpleContainer container){
        System.out.println("Slot interracted with");
        Optional<InscribingTableRecipe> match = playerEntity.level.getRecipeManager().
                getRecipeFor(InscribingTableRecipe.Type.INSTANCE, inputSlots, playerEntity.level);
        if(match.isPresent()) {
            System.out.println("Valid recipe found");
            System.out.println(match.get().getResultItem().getItem().toString());
            resultSlots.setItem(0,match.get().getResultItem());
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }
}
