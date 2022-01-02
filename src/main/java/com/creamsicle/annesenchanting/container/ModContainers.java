package com.creamsicle.annesenchanting.container;

import com.creamsicle.annesenchanting.AnnesEnchanting;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers {
    public static final DeferredRegister<MenuType<?>> CONTAINERS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, AnnesEnchanting.MOD_ID);

    public static final RegistryObject<MenuType<InscribingTableContainer>> INSCRIBING_TABLE_CONTAINER =
            CONTAINERS.register("inscribing_table", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new InscribingTableContainer(windowId,pos,inv,inv.player);
            }));

    public static void register(IEventBus eventBus){
        CONTAINERS.register(eventBus);
    }

}
