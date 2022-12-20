package org.duckdns.fossilfind.scifi;

import org.duckdns.fossilfind.scifi.block.SciFiBlocks;
import org.duckdns.fossilfind.scifi.block.entity.SciFiBlockEntities;
import org.duckdns.fossilfind.scifi.inventory.SciFiMenus;
import org.duckdns.fossilfind.scifi.item.SciFiItems;
import org.duckdns.fossilfind.scifi.item.recipes.SciFiRecipes;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SciFi.MODID)
public class SciFi
{
	public static final String MODID = "scifi";
	
	public SciFi()
	{
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		SciFiBlocks.BLOCKS.register(bus);
		SciFiBlockEntities.BLOCK_ENTITIES.register(bus);
		SciFiMenus.MENUS.register(bus);
		SciFiItems.ITEMS.register(bus);
		SciFiRecipes.SERIALIZERS.register(bus);
	}
}