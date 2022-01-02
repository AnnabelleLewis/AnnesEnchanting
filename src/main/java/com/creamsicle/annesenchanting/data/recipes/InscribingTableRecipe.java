package com.creamsicle.annesenchanting.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;


public class InscribingTableRecipe implements IInscribingTableRecipe{

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public InscribingTableRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    /*@Override
    public boolean matches(Inventory pContainer, Level pLevel) {
        if(recipeItems.get(0).test(pContainer.getItem(0)) && recipeItems.get(1).test(pContainer.getItem(1)) ||
                recipeItems.get(0).test(pContainer.getItem(1)) && recipeItems.get(1).test(pContainer.getItem(0))){
            if(recipeItems.get(2).test(pContainer.getItem(2))){
                return true;
            }
        }
        return false;
    }*/

    @Override
    public ItemStack assemble(Inventory pContainer) {
        return output.copy();
    }

    @Override
    public ItemStack assemble(CraftingContainer pContainer) {
        return null;
    }


    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        if(recipeItems.get(0).test(pContainer.getItem(0)) && recipeItems.get(1).test(pContainer.getItem(1)) ||
                recipeItems.get(0).test(pContainer.getItem(1)) && recipeItems.get(1).test(pContainer.getItem(0))){
            if(recipeItems.get(2).test(pContainer.getItem(2))){
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack assemble(Container pContainer) {
        return null;
    }


    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    public static class InscribingRecipeType implements RecipeType<IInscribingTableRecipe>{
        @Override
        public String toString() {
            return InscribingTableRecipe.TYPE_ID.toString();
        }
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>>
        implements RecipeSerializer<InscribingTableRecipe>{

        @Override
        public InscribingTableRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson( json.get("output").getAsJsonObject());


            JsonArray ingredients = json.get("ingredients").getAsJsonArray();
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new InscribingTableRecipe(recipeId, output, inputs);
        }

        @Nullable
        @Override
        public InscribingTableRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buffer));
            }

            ItemStack output = buffer.readItem();
            return new InscribingTableRecipe(recipeId, output,inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, InscribingTableRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buffer);
            }
            buffer.writeItemStack(recipe.getResultItem(), false);
        }
    }
}
