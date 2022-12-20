package org.duckdns.fossilfind.scifi.inventory.slot;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ResultSlot extends SlotItemHandler
{
	public ResultSlot(IItemHandler handler, int index, int x, int y)
	{
		super(handler, index, x, y);
	}
	
	@Override
	public boolean mayPlace(ItemStack stack)
	{
		return false;
	}
}