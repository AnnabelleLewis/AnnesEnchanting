package com.creamsicle.annesenchanting.container;


import com.creamsicle.annesenchanting.block.ModBlocks;
import com.creamsicle.annesenchanting.data.recipes.InscribingTableRecipe;
import com.creamsicle.annesenchanting.data.recipes.ModRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.Optional;


public class InscribingTableContainer extends AbstractContainerMenu {
    //private BlockEntity blockEntity;
    private Player playerEntity;
    private IItemHandler playerInventory;
    private final SimpleContainer craftSlots = new SimpleContainer(3);
    private final ResultContainer resultSlots = new ResultContainer();
    private final ContainerLevelAccess access;

    public InscribingTableContainer(int windowID, BlockPos pos, Inventory playerInventory, Player player) {

        super(ModContainers.INSCRIBING_TABLE_CONTAINER.get(), windowID);
        //blockEntity = player.getCommandSenderWorld().getBlockEntity(pos);
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
        this.access = ContainerLevelAccess.create(player.level, pos);
        //if (blockEntity != null) {
        //    blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
        this.addSlot(new Slot(craftSlots, 0, 34, 28));
        this.addSlot(new Slot(craftSlots, 1, 52, 28));
        this.addSlot(new Slot(craftSlots, 2, 43, 46));
        this.addSlot(new Slot(resultSlots, 3, 124, 35));
        //    });
        //}
        layoutPlayerInventorySlots(10, 80);

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
    //ContainerLevelAccess.create(pLevel, pPos)

    public boolean stillValid(Player pPlayer) {

        return stillValid(access, pPlayer, ModBlocks.INSCRIBING_TABLE.get());
    }


    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    protected static void slotChangedCraftingGrid(AbstractContainerMenu self, Level level, Player player, SimpleContainer inputContainer, ResultContainer output) {
        if (!level.isClientSide) {
            ServerPlayer serverplayer = (ServerPlayer)player;
            ItemStack itemstack = ItemStack.EMPTY;
            Optional<InscribingTableRecipe> optional = level.getServer().getRecipeManager().getRecipeFor(ModRecipeTypes.INSCRIBING_RECIPE, inputContainer, level);
            if (optional.isPresent()) {
                InscribingTableRecipe craftingrecipe = optional.get();
                if (output.setRecipeUsed(level, serverplayer, craftingrecipe)) {
                    itemstack = craftingrecipe.assemble(inputContainer);
                }
            }

            output.setItem(0, itemstack);
            self.setRemoteSlot(0, itemstack);
            serverplayer.connection.send(new ClientboundContainerSetSlotPacket(self.containerId, self.incrementStateId(), 0, itemstack));
        }
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void slotsChanged(Container pInventory) {
        this.access.execute((p_39386_, p_39387_) -> {
            slotChangedCraftingGrid(this, p_39386_, this.playerEntity, this.craftSlots, this.resultSlots);
        });
    }

    public void fillCraftSlotsStackedContents(StackedContents pItemHelper) {
        this.craftSlots.fillStackedContents(pItemHelper);
    }

    public void clearCraftingContent() {
        this.craftSlots.clearContent();
        this.resultSlots.clearContent();
    }

    public boolean recipeMatches(Recipe<? super CraftingContainer> pRecipe) {
        return pRecipe.matches(this.craftSlots, this.playerEntity.level);
    }

}
