package org.duckdns.fossilfind.scifi.inventory;

import org.duckdns.fossilfind.scifi.SciFi;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SciFiMenus
{
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, SciFi.MODID);
	
	public static final RegistryObject<MenuType<RefineryMenu>> REFINERY = MENUS.register("refinery", () -> IForgeMenuType.create(RefineryMenu::new));
}