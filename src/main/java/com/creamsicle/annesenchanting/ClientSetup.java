package com.creamsicle.annesenchanting;

import com.creamsicle.annesenchanting.container.ModContainers;
import com.creamsicle.annesenchanting.screen.InscribingTableScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(modid = AnnesEnchanting.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    @SubscribeEvent
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModContainers.INSCRIBING_TABLE_CONTAINER.get(), InscribingTableScreen::new);           // Attach our container to the screen
            //ItemBlockRenderTypes.setRenderLayer(ModBlocks.INSCRIBING_TABLE, RenderType.translucent()); // Set the render type for our power generator to translucent
        });
    }
}