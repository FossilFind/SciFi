package org.duckdns.fossilfind.scifi.inventory.slot;

import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ConditionalSlot extends SlotItemHandler
{
	private final Predicate<ItemStack> condition;
	
	public ConditionalSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Predicate<ItemStack> condition)
	{
		super(itemHandler, index, xPosition, yPosition);
		
		this.condition = condition;
	}
	
	@Override
	public boolean mayPlace(@NotNull ItemStack stack)
	{
		return condition.test(stack);
	}
}