package org.duckdns.fossilfind.scifi.world.dimension;

import org.duckdns.fossilfind.scifi.SciFi;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class SciFiDimensions
{
	public static final ResourceKey<Level> MOON_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(SciFi.MODID, "moon"));
	public static final ResourceKey<DimensionType> MOON_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, MOON_KEY.location());
	
}