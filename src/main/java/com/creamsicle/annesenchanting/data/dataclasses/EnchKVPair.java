package com.creamsicle.annesenchanting.data.dataclasses;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

//Handler for associating the encantmentID IE"minecraft:sharpness" and level of a given enchantment
public class EnchKVPair{
    private String enchantmentID;
    private int enchantmnetLevel;

    public EnchKVPair(String id, int level){
        this.enchantmentID = id;
        this.enchantmnetLevel = level;
    }

    public EnchKVPair(FriendlyByteBuf buff){
        this.enchantmentID = buff.readUtf();
        this.enchantmnetLevel = buff.readInt();
    }

    public EnchKVPair(Enchantment enchantment, int level){
        this.enchantmentID = enchantment.getRegistryName().toString();
        this.enchantmnetLevel = level;
    }

    public boolean equals(EnchKVPair other){
        if(other.GetLevel() != this.enchantmnetLevel){return false;}
        if(!other.GetID().equals(this.enchantmentID)){return false;}
        return true;
    }

    public String GetID(){return enchantmentID;}
    public int GetLevel(){return enchantmnetLevel;}

    public void WriteToFriendlyByteBuff(FriendlyByteBuf buff){
        buff.writeUtf(enchantmentID);
        buff.writeInt(enchantmnetLevel);
    }

    public EnchantmentInstance toEnchantmentInstance(){
        return new EnchantmentInstance(
                ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(enchantmentID)),
                enchantmnetLevel
        );
    }

    public String toString(){
        return GetID() + " level " + GetLevel();
    }

    public boolean listContainsKVPair(ArrayList<EnchKVPair> list){
        for(EnchKVPair ench: list){
            if(ench.equals(this)){return true;}
        }
        return false;
    }
}
