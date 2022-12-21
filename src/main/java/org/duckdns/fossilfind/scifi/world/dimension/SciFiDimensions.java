package org.duckdns.fossilfind.scifi.world.dimension;

import org.duckdns.fossilfind.scifi.SciFi;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class SciFiDimensions
{
	public static final ResourceKey<Level> MOON_KEY = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(SciFi.MODID, "moon"));
	public static final ResourceKey<DimensionType> MOON_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, MOON_KEY.location());
}