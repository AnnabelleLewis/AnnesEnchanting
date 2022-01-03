package com.creamsicle.annesenchanting.data.recipes;

import com.creamsicle.annesenchanting.AnnesEnchanting;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, AnnesEnchanting.MOD_ID);

    public static final RegistryObject<RecipeSerializer<InscribingTableRecipe>> COBALT_BLASTER_SERIALIZER =
            SERIALIZERS.register("inscribing", () -> InscribingTableRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        Registry.register(Registry.RECIPE_TYPE, InscribingTableRecipe.Type.ID, InscribingTableRecipe.Type.INSTANCE);
    }
}
