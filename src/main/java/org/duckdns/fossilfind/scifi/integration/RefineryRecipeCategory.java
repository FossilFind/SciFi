package org.duckdns.fossilfind.scifi.integration;

import org.duckdns.fossilfind.scifi.SciFi;
import org.duckdns.fossilfind.scifi.block.SciFiBlocks;
import org.duckdns.fossilfind.scifi.item.recipes.RefineryRecipe;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class RefineryRecipeCategory implements IRecipeCategory<RefineryRecipe>
{
	public final static ResourceLocation UID = new ResourceLocation(SciFi.MODID, "refining");
	public final static ResourceLocation TEXTURE = new ResourceLocation(SciFi.MODID, "textures/gui/refinery.png");
	
	private final IDrawable background;
	private final IDrawable icon;
	private final IDrawableStatic staticFlame;
	private final IDrawableAnimated animatedFlame;
	private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;
	
	public RefineryRecipeCategory(IGuiHelper helper)
	{
		background = helper.createDrawable(TEXTURE, 53, 14, 82, 58);
		icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(SciFiBlocks.REFINERY.get()));
		staticFlame = helper.createDrawable(TEXTURE, 176, 0, 14, 14);
		animatedFlame = helper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
		cachedArrows = CacheBuilder.newBuilder().maximumSize(25).build(new CacheLoader<>()
		{
			@Override
			public IDrawableAnimated load(Integer cookTime)
			{
				return helper.drawableBuilder(TEXTURE, 176, 14, 24, 17).buildAnimated(cookTime, IDrawableAnimated.StartDirection.LEFT, false);
			}
		});
	}
	
	@Override
	public RecipeType<RefineryRecipe> getRecipeType()
	{
		return JEISciFiPlugin.REFINING_TYPE;
	}
	
	@Override
	public Component getTitle()
	{
		return Component.translatable("container." + SciFi.MODID + ".refinery");
	}
	
	public IDrawableAnimated getArrow(RefineryRecipe recipe)
	{
		int cookTime = recipe.getCookingTime();
		
		if(cookTime <= 0)
			cookTime = 200;
		
		return cachedArrows.getUnchecked(cookTime);
	}
	
	@Override
	public IDrawable getBackground()
	{
		return background;
	}
	
	@Override
	public IDrawable getIcon()
	{
		return icon;
	}
	
	@Override
	public void draw(RefineryRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY)
	{
		animatedFlame.draw(poseStack, 4, 23);
		
		IDrawableAnimated arrow = getArrow(recipe);
		arrow.draw(poseStack, 26, 20);
		
		drawCookTime(recipe, poseStack, 51);
	}
	
	private void drawCookTime(RefineryRecipe recipe, PoseStack poseStack, int y) {
		int cookTime = recipe.getCookingTime();
		if (cookTime > 0)
		{
			int cookTimeSeconds = cookTime / 20;
			Component timeString = Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds);
			Minecraft minecraft = Minecraft.getInstance();
			Font fontRenderer = minecraft.font;
			int stringWidth = fontRenderer.width(timeString);
			fontRenderer.draw(poseStack, timeString, background.getWidth() - stringWidth, y, 0xFF808080);
		}
	}
	
	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, RefineryRecipe recipe, IFocusGroup focuses)
	{
		builder.addSlot(RecipeIngredientRole.INPUT, 3, 3).addIngredients(recipe.getIngredients().get(0));
		builder.addSlot(RecipeIngredientRole.OUTPUT, 63, 10).addItemStack(recipe.getResultItem());
		builder.addSlot(RecipeIngredientRole.OUTPUT, 63, 32).addItemStack(recipe.getResultItem());
	}
}