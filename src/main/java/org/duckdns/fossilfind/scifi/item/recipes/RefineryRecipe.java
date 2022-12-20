package org.duckdns.fossilfind.scifi.item.recipes;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.duckdns.fossilfind.scifi.SciFi;
import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

public class RefineryRecipe implements Recipe<SimpleContainer>
{
	private final ResourceLocation id;
	private final Ingredient input;
	private final ItemStack output;
	private final int cookingTime;
	
	public RefineryRecipe(ResourceLocation id, Ingredient input, ItemStack output, int cookingTime)
	{
		this.id = id;
		this.input = input;
		this.output = output;
		this.cookingTime = cookingTime;
	}
	
	@Override
	public boolean matches(SimpleContainer container, Level level)
	{
		if(level.isClientSide)
			return false;
		
		return input.test(container.getItem(0));
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients()
	{
		return NonNullList.of(null, input);
	}
	
	@Override
	public ItemStack assemble(SimpleContainer container)
	{
		return output;
	}
	
	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		return true;
	}
	
	@Override
	public ItemStack getResultItem()
	{
		return output.copy();
	}
	
	@Override
	public ResourceLocation getId()
	{
		return id;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return Serializer.INSTANCE;
	}
	
	@Override
	public RecipeType<?> getType()
	{
		return Type.INSTANCE;
	}
	
	public int getCookingTime()
	{
		return cookingTime;
	}
	
	public static @Nullable RefineryRecipe getRecipe(ItemStack stack)
	{
		@SuppressWarnings("resource")
		ClientLevel level = Minecraft.getInstance().level;
		Set<Recipe<?>> recipes = level != null ? level.getRecipeManager().getRecipes().stream().filter(recipe -> recipe.getType() == Type.INSTANCE).collect(Collectors.toSet()) : Collections.emptySet();
		
		for(Recipe<?> recipe : recipes)
		{
			RefineryRecipe refineryRecipe = (RefineryRecipe) recipe;
			if(refineryRecipe.input != null && refineryRecipe.input.getItems() != null && refineryRecipe.input.getItems().length != 0)
				if(refineryRecipe.input.getItems()[0].getItem() == stack.getItem())
					return refineryRecipe;
		}
		
		return null;
	}
	
	public static boolean hasRecipe(ItemStack stack)
	{
		return getRecipe(stack) != null;
	}
	
	public static class Type implements RecipeType<RefineryRecipe>
	{
		private Type() { }
		
		public static final Type INSTANCE = new Type();
		public static final String ID = "refining";
	}
	
	public static class Serializer implements RecipeSerializer<RefineryRecipe>
	{
		public static final Serializer INSTANCE = new Serializer();
		public static final ResourceLocation ID = new ResourceLocation(SciFi.MODID, Type.ID);
		
		@SuppressWarnings("deprecation")
		@Override
		public RefineryRecipe fromJson(ResourceLocation recipeId, JsonObject serializedRecipe)
		{
			Ingredient input;
			
			if(!serializedRecipe.has("ingredient"))
				throw new JsonSyntaxException("Missing ingredient, expected to find a string or object");
			
			if(serializedRecipe.get("ingredient").isJsonObject())
				input = Ingredient.fromJson(serializedRecipe.getAsJsonObject("ingredient"));
			else
				input = Ingredient.of(new ItemStack(Registry.ITEM.get(new ResourceLocation(serializedRecipe.get("ingredient").getAsString()))));
			
			ItemStack output;
			
			if(!serializedRecipe.has("result"))
				throw new JsonSyntaxException("Missing result. expected to find a string or object");
			
			if(serializedRecipe.get("result").isJsonObject())
				output = ShapedRecipe.itemStackFromJson(serializedRecipe.getAsJsonObject("result"));
			else
				output = new ItemStack(Registry.ITEM.get(new ResourceLocation(serializedRecipe.get("result").getAsString())));
			
			int cookingTime;
			
			if(serializedRecipe.has("cookingTime"))
				cookingTime = serializedRecipe.get("cookingTime").getAsInt();
			else
				cookingTime = 200;
			
			return new RefineryRecipe(recipeId, input, output, cookingTime);
		}
		
		@Override
		public @Nullable RefineryRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
		{
			Ingredient input = Ingredient.fromNetwork(buffer);
			ItemStack output = buffer.readItem();
			int cookingTime = buffer.readInt();
			
			return new RefineryRecipe(recipeId, input, output, cookingTime);
		}
		
		@Override
		public void toNetwork(FriendlyByteBuf buffer, RefineryRecipe recipe)
		{
			buffer.writeItemStack(recipe.input.getItems()[0], false);
			buffer.writeItemStack(recipe.output, false);
			buffer.writeInt(recipe.cookingTime);
		}
	}
}