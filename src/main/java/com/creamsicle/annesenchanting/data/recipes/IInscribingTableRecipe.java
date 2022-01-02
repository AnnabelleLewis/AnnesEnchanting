package com.creamsicle.annesenchanting.data.recipes;

import com.creamsicle.annesenchanting.AnnesEnchanting;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public interface IInscribingTableRecipe extends Recipe<Container> {
    ResourceLocation TYPE_ID = new ResourceLocation(AnnesEnchanting.MOD_ID, "inscribing");

    @Override
    default RecipeType<?> getType(){
        return Registry.RECIPE_TYPE.getOptional(TYPE_ID).get();
    }


    ItemStack assemble(Inventory pContainer);

    ItemStack assemble(CraftingContainer pContainer);
}
