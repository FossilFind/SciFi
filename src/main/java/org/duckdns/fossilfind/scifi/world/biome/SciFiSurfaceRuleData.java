package org.duckdns.fossilfind.scifi.world.biome;

import org.duckdns.fossilfind.scifi.block.SciFiBlocks;

import net.minecraft.world.level.levelgen.SurfaceRules;

public class SciFiSurfaceRuleData
{
	public static SurfaceRules.RuleSource moon()
	{
		return SurfaceRules.state(SciFiBlocks.MOON_REGOLITH.get().defaultBlockState());
	}
}