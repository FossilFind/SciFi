package org.duckdns.fossilfind.scifi.world.biome;

import org.duckdns.fossilfind.scifi.SciFi;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class SciFiBiomes
{
	public static final ResourceKey<Biome> MOON = ResourceKey.create(Registries.BIOME, new ResourceLocation(SciFi.MODID, "moon"));
}