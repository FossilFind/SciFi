package org.duckdns.fossilfind.scifi.item;

import org.duckdns.fossilfind.scifi.SciFi;
import org.duckdns.fossilfind.scifi.block.SciFiBlocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SciFiItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SciFi.MODID);
	
	public static final RegistryObject<Item> BAUXITE = ITEMS.register("bauxite", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RUTILE = ITEMS.register("rutile", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TANTILITE = ITEMS.register("tantilite", () -> new Item(new Item.Properties()));
	
	public static final RegistryObject<Item> ALUMINUM_INGOT = ITEMS.register("aluminum_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> TANTALUM_INGOT = ITEMS.register("tantalum_ingot", () -> new Item(new Item.Properties()));
	
	/*
	 * Block Items
	 */
	public static final RegistryObject<Item> REFINERY = ITEMS.register("refinery", () -> new BlockItem(SciFiBlocks.REFINERY.get(), new Item.Properties()));
	
	public static final RegistryObject<Item> MOON_REGOLITH = ITEMS.register("moon_regolith", () -> new BlockItem(SciFiBlocks.MOON_REGOLITH.get(), new Item.Properties()));
	
	public static final RegistryObject<Item> BAUXITE_ORE = ITEMS.register("bauxite_ore", () -> new BlockItem(SciFiBlocks.BAUXITE_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> RUTILE_ORE = ITEMS.register("rutile_ore", () -> new BlockItem(SciFiBlocks.RUTILE_ORE.get(), new Item.Properties()));
	public static final RegistryObject<Item> TANTILITE_ORE = ITEMS.register("tantilite_ore", () -> new BlockItem(SciFiBlocks.TANTILITE_ORE.get(), new Item.Properties()));
	
	public static final RegistryObject<Item> BAUXITE_BLOCK = ITEMS.register("bauxite_block", () -> new BlockItem(SciFiBlocks.BAUXITE_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> RUTILE_BLOCK = ITEMS.register("rutile_block", () -> new BlockItem(SciFiBlocks.RUTILE_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> TANTILITE_BLOCK = ITEMS.register("tantilite_block", () -> new BlockItem(SciFiBlocks.TANTILITE_BLOCK.get(), new Item.Properties()));
	
	public static final RegistryObject<Item> ALUMINUM_BLOCK = ITEMS.register("aluminum_block", () -> new BlockItem(SciFiBlocks.ALUMINUM_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> TITANIUM_BLOCK = ITEMS.register("titanium_block", () -> new BlockItem(SciFiBlocks.TITANIUM_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Item> TANTALUM_BLOCK = ITEMS.register("tantalum_block", () -> new BlockItem(SciFiBlocks.TANTALUM_BLOCK.get(), new Item.Properties()));
}