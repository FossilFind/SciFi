package org.duckdns.fossilfind.scifi.integration;

//import java.util.List;
//import java.util.Objects;
//
//import org.duckdns.fossilfind.scifi.SciFi;
//import org.duckdns.fossilfind.scifi.item.recipes.RefineryRecipe;
//
//import mezz.jei.api.IModPlugin;
//import mezz.jei.api.JeiPlugin;
//import mezz.jei.api.recipe.RecipeType;
//import mezz.jei.api.registration.IRecipeCategoryRegistration;
//import mezz.jei.api.registration.IRecipeRegistration;
//import net.minecraft.client.Minecraft;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.item.crafting.RecipeManager;

//@JeiPlugin
public class JEISciFiPlugin// implements IModPlugin
{
//	public static RecipeType<RefineryRecipe> REFINING_TYPE = new RecipeType<>(RefineryRecipeCategory.UID, RefineryRecipe.class);
//	
//	@Override
//	public ResourceLocation getPluginUid()
//	{
//		return new ResourceLocation(SciFi.MODID, "jei_plugin");
//	}
//	
//	@Override
//	public void registerCategories(IRecipeCategoryRegistration registration)
//	{
//		registration.addRecipeCategories(new RefineryRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
//	}
//	
//	@Override
//	public void registerRecipes(IRecipeRegistration registration)
//	{
//		@SuppressWarnings("resource")
//		RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
//		List<RefineryRecipe> refineryRecipes = rm.getAllRecipesFor(RefineryRecipe.Type.INSTANCE);
//		registration.addRecipes(REFINING_TYPE, refineryRecipes);
//	}
}