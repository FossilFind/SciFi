package org.duckdns.fossilfind.scifi.world.biome;

import org.duckdns.fossilfind.scifi.SciFi;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.biome.BiomeSpecialEffects;

public class SciFiBiomes
{
	public static final ResourceKey<Biome> MOON = ResourceKey.create(Registries.BIOME, new ResourceLocation(SciFi.MODID, "moon"));
	
	public static void bootStrap(BootstapContext<Biome> context)
	{
		context.register(MOON, moon());
	}
	
	public static Biome moon()
	{
		return new Biome.BiomeBuilder()
				.temperature(0.8f)
				.downfall(0)
				.precipitation(Precipitation.NONE)
				.specialEffects(new BiomeSpecialEffects.Builder()
						.skyColor(0)
						.fogColor(0)
						.waterColor(4159204)
						.waterFogColor(329011)
					.build())
			.build();
	}
}