package com.creamsicle.annesenchanting.blockentities;

import com.creamsicle.annesenchanting.AnnesEnchanting;
import com.creamsicle.annesenchanting.block.ModBlocks;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, AnnesEnchanting.MOD_ID);


    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
