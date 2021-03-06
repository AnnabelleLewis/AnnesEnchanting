package com.creamsicle.annesenchanting.blockentities;

import com.creamsicle.annesenchanting.AnnesEnchanting;
import com.creamsicle.annesenchanting.block.ModBlocks;

import net.minecraft.world.level.block.entity.BlockEntityType;

import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, AnnesEnchanting.MOD_ID);


    public static final RegistryObject<BlockEntityType<BookshelfBlockEntity>> BOOKCASE =
            BLOCK_ENTITIES.register("bookshelf", () ->
                    BlockEntityType.Builder.of(BookshelfBlockEntity::new,
                            ModBlocks.BOOKSHELF.get()).build(null));



    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
