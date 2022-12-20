package org.duckdns.fossilfind.scifi.item.recipes;

import org.duckdns.fossilfind.scifi.SciFi;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SciFiRecipes
{
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, SciFi.MODID);
	
	public static final RegistryObject<RecipeSerializer<RefineryRecipe>> REFINERY_RECIPE_SERIALIZER = SERIALIZERS.register("refining", () -> RefineryRecipe.Serializer.INSTANCE);
}