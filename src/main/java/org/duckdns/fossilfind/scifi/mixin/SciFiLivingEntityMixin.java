package org.duckdns.fossilfind.scifi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;

@Mixin(LivingEntity.class)
public abstract class SciFiLivingEntityMixin
{
	private static final AttributeModifier EARTH_GRAVITY = new AttributeModifier("Earth_gravity", 1d, Operation.MULTIPLY_TOTAL);
	private static final AttributeModifier MOON_GRAVITY = new AttributeModifier("Moon_gravity", 1.62d / 9.8d, Operation.MULTIPLY_TOTAL);
	
	@Inject(method = "travel", at = @At("HEAD"))
	private void injectTravelHead(Vec3 travelVector, CallbackInfo info)
	{
		LivingEntity entity = (LivingEntity) (Object) this;
		
		AttributeModifier gravityModifier = getGravity(entity.level.dimension());
		
		//entity.fallDistance *= getGravity(entity.level.dimension()).getAmount();
		
		if (entity.isEffectiveAi() || entity.isControlledByLocalInstance())
		{
			AttributeInstance gravity = entity.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
			
			System.out.println(gravity.getValue() + " *");
			System.out.println(getGravity(entity.level.dimension()).getAmount() + " =");
			
			if (!gravity.hasModifier(gravityModifier))
				gravity.addTransientModifier(gravityModifier);
			
			System.out.println(gravity.getValue());
			
			System.out.println("-----------------");
			System.out.println();
		}
	}
	
	@Inject(method = "travel", at = @At("TAIL"))
	private void injectTravelTail(Vec3 travelVector, CallbackInfo info)
	{
		LivingEntity entity = (LivingEntity) (Object) this;
		
		AttributeModifier gravityModifier = getGravity(entity.level.dimension());
		
		AttributeInstance gravity = entity.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
		if(gravity.hasModifier(gravityModifier))
			gravity.removeModifier(gravityModifier);
	}
	
	private static AttributeModifier getGravity(ResourceKey<Level> dimension)
	{
		if(dimension.location().equals(BuiltinDimensionTypes.NETHER.location()))
			return MOON_GRAVITY;
		
		return EARTH_GRAVITY;
	}
}