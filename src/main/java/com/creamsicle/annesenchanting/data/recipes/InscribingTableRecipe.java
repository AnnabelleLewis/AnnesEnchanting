package com.creamsicle.annesenchanting.data.recipes;

import com.creamsicle.annesenchanting.AnnesEnchanting;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;
import java.rmi.registry.Registry;
import java.util.*;


public class InscribingTableRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final EnchKVPair output;
    private final EnchKVPair input1;
    private final EnchKVPair input2;

    private final Ingredient catalyst;

    public InscribingTableRecipe(ResourceLocation id, EnchKVPair output, EnchKVPair input1, EnchKVPair input2, Ingredient catalyst) {
        this.id = id;
        this.output = output;
        this.input1 = input1;
        this.input2 = input2;
        this.catalyst = catalyst;

    }


    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {

        //if(!pContainer.getItem(0).isEnchanted()){System.out.println("item in slot 1 not enchanted");return false;}
        //if(!pContainer.getItem(1).isEnchanted()){System.out.println("item in slot 2 not enchanted");return false;}
        if(!(pContainer.getItem(0).getItem() == Items.ENCHANTED_BOOK)){System.out.println("item in slot 1 not enchanted");return false;}
        if(!(pContainer.getItem(1).getItem() == Items.ENCHANTED_BOOK)){System.out.println("item in slot 2 not enchanted");return false;}
        if(!catalyst.test(pContainer.getItem(2))){System.out.println("incorrect catalyst");return false;}



        Map<Enchantment, Integer> item1enchants = EnchantmentHelper.deserializeEnchantments(EnchantedBookItem.getEnchantments(pContainer.getItem(0)));
        Map<Enchantment, Integer> item2enchants = EnchantmentHelper.deserializeEnchantments(EnchantedBookItem.getEnchantments(pContainer.getItem(1)));

        if(EnchInMap(item1enchants,input1) && EnchInMap(item2enchants,input2)){return true;}
        if(EnchInMap(item2enchants,input1) && EnchInMap(item1enchants,input2)){return true;}
        System.out.println("correct enchants not present");
        return false;
    }

    public boolean EnchInMap(Map<Enchantment, Integer> map, EnchKVPair kvPair){

        Enchantment kEnch = ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.tryParse(kvPair.GetID()));

        if(!map.containsKey(kEnch)){return false;}
        if(!(map.get(kEnch) == kvPair.GetLevel())){return false;}

        return true;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return EnchantedBookItem.createForEnchantment(output.toEnchantmentInstance());
    }


    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {


        return EnchantedBookItem.createForEnchantment(output.toEnchantmentInstance());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {

        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<InscribingTableRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "inscribing";
    }

    public static class Serializer implements RecipeSerializer<InscribingTableRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(AnnesEnchanting.MOD_ID,"inscribing");

        @Override
        public InscribingTableRecipe fromJson(ResourceLocation id, JsonObject json) {
            EnchKVPair output;
            EnchKVPair input1;
            EnchKVPair input2;
            Ingredient catalyst;

            //Get input enchantment types and level
            JsonArray inputEnchants = json.getAsJsonArray("enchantments");
            input1 = KVPairFromJson(inputEnchants.get(0).getAsJsonObject());
            input2 = KVPairFromJson(inputEnchants.get(1).getAsJsonObject());

            //Get output enchantment type and level
            output = KVPairFromJson(json.getAsJsonObject("output"));

            //Get catalyst
            catalyst = Ingredient.fromJson(json.getAsJsonObject("catalyst"));

            return new InscribingTableRecipe(id, output, input1, input2, catalyst);
        }

        //Returns a map containing the id and level of the enchantment
        private EnchKVPair KVPairFromJson(JsonObject json){
            String enchID;
            int enchLevel;

            enchID = json.get("enchantment").getAsString();
            enchLevel = json.get("level").getAsInt();


            return new EnchKVPair(enchID,enchLevel);
        }

        @Override
        public InscribingTableRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            EnchKVPair input1 = new EnchKVPair(buf);
            EnchKVPair input2 = new EnchKVPair(buf);
            EnchKVPair output = new EnchKVPair(buf);
            Ingredient catalyst = Ingredient.fromNetwork(buf);
            return new InscribingTableRecipe(id, output, input1, input2, catalyst);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, InscribingTableRecipe recipe) {
            recipe.input1.WriteToFriendlyByteBuff(buf);
            recipe.input2.WriteToFriendlyByteBuff(buf);
            recipe.output.WriteToFriendlyByteBuff(buf);
            recipe.catalyst.toNetwork(buf);
        }

        @Override
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Nullable
        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }


    }

    //Handler for associating the encantmentID IE"minecraft:sharpness" and level of a given enchantment
    private static class EnchKVPair{
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
    }
}
