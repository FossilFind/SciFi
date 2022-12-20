package org.duckdns.fossilfind.scifi.block;

import org.duckdns.fossilfind.scifi.SciFi;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SciFiBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SciFi.MODID);
	
	public static final RegistryObject<Block> REFINERY = BLOCKS.register("refinery", () -> new RefineryBlock(Block.Properties.of(Material.METAL)));
	
	public static final RegistryObject<Block> MOON_REGOLITH = BLOCKS.register("moon_regolith", () -> new Block(Block.Properties.of(Material.SAND)));
}