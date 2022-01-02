package com.creamsicle.annesenchanting.data.recipes;

import com.creamsicle.annesenchanting.AnnesEnchanting;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, AnnesEnchanting.MOD_ID);

    public static final RegistryObject<InscribingTableRecipe.Serializer> INSCRIBING_SERIALIZER =
            RECIPE_SERIALIZER.register("inscribing", InscribingTableRecipe.Serializer::new);

    public static RecipeType<IInscribingTableRecipe> INSCRIBING_RECIPE =
            new InscribingTableRecipe.InscribingRecipeType();

    public static void register(IEventBus eventBus){
        RECIPE_SERIALIZER.register(eventBus);

        Registry.register(Registry.RECIPE_TYPE, InscribingTableRecipe.TYPE_ID, INSCRIBING_RECIPE);
    }
}
