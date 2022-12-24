package org.duckdns.fossilfind.scifi.block;

import org.duckdns.fossilfind.scifi.SciFi;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SciFiBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SciFi.MODID);
	
	public static final RegistryObject<Block> REFINERY = BLOCKS.register("refinery", () -> new RefineryBlock(Block.Properties.of(Material.METAL)));
	
	public static final RegistryObject<Block> MOON_REGOLITH = BLOCKS.register("moon_regolith", () -> new Block(Block.Properties.of(Material.SAND)));
	
	public static final RegistryObject<Block> BAUXITE_ORE = BLOCKS.register("bauxite_ore", () -> new Block(Block.Properties.copy(Blocks.IRON_ORE)));
	public static final RegistryObject<Block> RUTILE_ORE = BLOCKS.register("rutile_ore", () -> new Block(Block.Properties.copy(Blocks.IRON_ORE)));
	public static final RegistryObject<Block> TANTILITE_ORE = BLOCKS.register("tantilite_ore", () -> new Block(Block.Properties.copy(Blocks.IRON_ORE)));
	
	public static final RegistryObject<Block> BAUXITE_BLOCK = BLOCKS.register("bauxite_block", () -> new Block(Block.Properties.copy(Blocks.RAW_IRON_BLOCK)));
	public static final RegistryObject<Block> RUTILE_BLOCK = BLOCKS.register("rutile_block", () -> new Block(Block.Properties.copy(Blocks.RAW_IRON_BLOCK)));
	public static final RegistryObject<Block> TANTILITE_BLOCK = BLOCKS.register("tantilite_block", () -> new Block(Block.Properties.copy(Blocks.RAW_IRON_BLOCK)));
	
	public static final RegistryObject<Block> ALUMINUM_BLOCK = BLOCKS.register("aluminum_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK)));
	public static final RegistryObject<Block> TITANIUM_BLOCK = BLOCKS.register("titanium_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK)));
	public static final RegistryObject<Block> TANTALUM_BLOCK = BLOCKS.register("tantalum_block", () -> new Block(Block.Properties.copy(Blocks.IRON_BLOCK)));
}