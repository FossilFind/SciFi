package org.duckdns.fossilfind.scifi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

@Mixin(LivingEntity.class)
public abstract class SciFiLivingEntityMixin
{
	private static final double EARTH_GRAVITY = 0.08d;
	private static final double MOON_GRAVITY = 1.62d / 9.8d * EARTH_GRAVITY;
	
	@Inject(method = "travel", at = @At("HEAD"))
	private void injectTravelHead(Vec3 travelVector, CallbackInfo info)
	{
		LivingEntity entity = (LivingEntity) (Object) this;
		
		entity.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(getGravity(entity.level.dimension()));
		
		entity.fallDistance *= getGravity(entity.level.dimension()) / 0.08d;
	}
	
	private static double getGravity(ResourceKey<Level> dimension)
	{
		if(dimension.location().equals(BuiltinDimensionTypes.NETHER.location()))
			return MOON_GRAVITY;
		
		return EARTH_GRAVITY;
	}
}