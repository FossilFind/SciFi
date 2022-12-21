package org.duckdns.fossilfind.scifi.world.biome;

import java.util.List;

import org.duckdns.fossilfind.scifi.SciFi;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraftforge.registries.ForgeRegistries;

public class SciFiNoiseGeneratorSettings
{
	public static final ResourceKey<NoiseGeneratorSettings> MOON = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(SciFi.MODID, "moon"));
	
	public static NoiseGeneratorSettings moon(BootstapContext<NoiseGeneratorSettings> context)
	{
		return new NoiseGeneratorSettings(
				NoiseSettings.create(-64, 384, 1, 2),
				Blocks.STONE.defaultBlockState(),
				Blocks.AIR.defaultBlockState(),
				NoiseRouterData.overworld(context.lookup(Registries.DENSITY_FUNCTION), context.lookup(Registries.NOISE), false, false),
				SciFiSurfaceRuleData.moon(),
				List.of(),
				63,
				false,
				true,
				true,
				false);
	}
}