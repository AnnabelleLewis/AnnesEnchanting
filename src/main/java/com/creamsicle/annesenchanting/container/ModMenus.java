package com.creamsicle.annesenchanting.container;

import com.creamsicle.annesenchanting.AnnesEnchanting;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, AnnesEnchanting.MOD_ID);

    public static final RegistryObject<MenuType<InscribingTableMenu>> INSCRIBING_TABLE_CONTAINER =
            MENUS.register("inscribing_table", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new InscribingTableMenu(windowId,pos,inv,inv.player);
            }));

    public static final RegistryObject<MenuType<BookshelfMenu>> BOOKSHELF_CONTAINER =
            registerMenuType(BookshelfMenu::new, "bookshelf_container");


    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }

}
