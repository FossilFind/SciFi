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
	
	public static final RegistryObject<Item> ALUMINUM_INGOT = ITEMS.register("aluminum_ingot", () -> new Item(new Item.Properties()));
	
	/*
	 * Block Items
	 */
	public static final RegistryObject<Item> REFINERY = ITEMS.register("refinery", () -> new BlockItem(SciFiBlocks.REFINERY.get(), new Item.Properties()));
	
	public static final RegistryObject<Item> MOON_REGOLITH = ITEMS.register("moon_regolith", () -> new BlockItem(SciFiBlocks.MOON_REGOLITH.get(), new Item.Properties()));
}