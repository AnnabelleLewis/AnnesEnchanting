package com.creamsicle.annesenchanting.block.custom;

import com.creamsicle.annesenchanting.block.ModBlocks;
import com.creamsicle.annesenchanting.blockentities.BookshelfBlockEntity;
import com.creamsicle.annesenchanting.container.InscribingTableMenu;
import com.creamsicle.annesenchanting.data.dataclasses.EnchKVPair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnnesEnchantmentTableBlock extends Block {
    public AnnesEnchantmentTableBlock() {
        super(BlockBehaviour.Properties.of(Material.WOOD)
                .strength(2f)
                .sound(SoundType.WOOD));
    }



    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        if (!level.isClientSide) {
            getEnchantments(level,pos);

            /*MenuProvider containerProvider = new MenuProvider() {


                @Override
                public Component getDisplayName() {
                    return new TranslatableComponent("screen.annesenchanting.inscribing_table");
                }

                @Override
                public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                    return new InscribingTableMenu(windowId, pos, playerInventory, playerEntity);
                }
            };
            NetworkHooks.openGui((ServerPlayer) player, containerProvider, pos);*/
        }

        return InteractionResult.SUCCESS;
    }

    ArrayList<EnchKVPair> getEnchantments(Level pLevel, BlockPos pPos){
        ArrayList<EnchKVPair> returnList = new ArrayList<EnchKVPair>();

        for(int i = -2; i <= 2; ++i) {
            for(int j = -2; j <= 2; ++j) {
                if (i > -2 && i < 2 && j == -1) {
                    j = 2;
                }


                for(int k = 0; k <= 1; ++k) {
                    BlockPos blockpos = pPos.offset(i, k, j);
                    if (pLevel.getBlockEntity(blockpos) instanceof BookshelfBlockEntity bookshelfBlockEntity) {
                        if (!pLevel.isEmptyBlock(pPos.offset(i / 2, 0, j / 2))) {
                            break;
                        }

                        List<EnchKVPair> bookshelfEnchants = bookshelfBlockEntity.getEnchantments();
                        for(EnchKVPair enchantment: bookshelfEnchants){
                            if(!enchantment.listContainsKVPair(returnList)){
                                returnList.add(enchantment);
                            }
                        }


                    }
                }

            }
        }
        for(EnchKVPair enchant: returnList){

            System.out.println(enchant.toString());
        }
        return returnList;
    }

}
