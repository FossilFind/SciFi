package org.duckdns.fossilfind.scifi.inventory;

import org.duckdns.fossilfind.scifi.block.SciFiBlocks;
import org.duckdns.fossilfind.scifi.block.entity.RefineryBlockEntity;
import org.duckdns.fossilfind.scifi.inventory.slot.ConditionalSlot;
import org.duckdns.fossilfind.scifi.inventory.slot.FuelSlot;
import org.duckdns.fossilfind.scifi.inventory.slot.ResultSlot;
import org.duckdns.fossilfind.scifi.item.recipes.RefineryRecipe;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class RefineryMenu extends AbstractContainerMenu
{
	private final RefineryBlockEntity entity;
	private final ContainerData data;
	private final Level level;
	
	public RefineryMenu(int containerId, Inventory inventory, BlockEntity entity, ContainerData data)
	{
		super(SciFiMenus.REFINERY.get(), containerId);
		
		this.entity = (RefineryBlockEntity) entity;
		this.data = data;
		this.level = inventory.player.level;
		
		entity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler ->
		{
			addSlot(new ConditionalSlot(handler, 0, 56, 17, (stack) -> RefineryRecipe.hasRecipe(stack)));
			addSlot(new FuelSlot(handler, 1, 56, 53));
			addSlot(new ResultSlot(handler, 2, 116, 24));
			addSlot(new ResultSlot(handler, 3, 116, 46));
		});
		
		for(int row = 0; row < 3; row++)
			for(int col = 0; col < 9; col++)
				addSlot(new Slot(inventory, 9 + row * 9 + col, 8 + col * 18, 84 + row * 18));
		
		for(int col = 0; col < 9; col++)
			addSlot(new Slot(inventory, col, 8 + col * 18, 142));
		
		addDataSlots(data);
	}
	
	public RefineryMenu(int id, Inventory inventory, FriendlyByteBuf buffer)
	{
		this(id, inventory, inventory.player.level.getBlockEntity(buffer.readBlockPos()), new SimpleContainerData(4));
	}
	
	@Override
	public boolean stillValid(Player player)
	{
		return stillValid(ContainerLevelAccess.create(level, entity.getBlockPos()), player, SciFiBlocks.REFINERY.get());
	}
	
	@Override
	public ItemStack quickMoveStack(Player player, int index)
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = slots.get(index);
		
		if(slot != null && slot.hasItem())
		{
			ItemStack slotStack = slot.getItem();
			stack = slotStack.copy();
			
			if(index == 2 || index == 3)
				if(!moveItemStackTo(slotStack, 3, 39, true))
					return ItemStack.EMPTY;
			else if(index != 1 && index != 0)
			{
				if(canSmelt(slotStack))
					if(!moveItemStackTo(slotStack, 0, 1, false))
						return ItemStack.EMPTY;
				else if(ForgeHooks.getBurnTime(slotStack, RecipeType.SMELTING) > 0)
					if(!moveItemStackTo(slotStack, 1, 2, false))
						return ItemStack.EMPTY;
				else if(index >= 4 && index < 31)
					if(!moveItemStackTo(slotStack, 31, 40, false))
						return ItemStack.EMPTY;
				else if(index >= 31 && index < 40 && !moveItemStackTo(slotStack, 4, 31, false))
					return ItemStack.EMPTY;
			}
			else if(!moveItemStackTo(slotStack, 4, 40, false))
				return ItemStack.EMPTY;
			
			if(slotStack.isEmpty())
				slot.set(ItemStack.EMPTY);
			else
				slot.setChanged();
			
			if(slotStack.getCount() == stack.getCount())
				return ItemStack.EMPTY;
			
			slot.onTake(player, slotStack);
		}
		
		return stack;
	}
	
	private boolean canSmelt(ItemStack stack)
	{
		return RefineryRecipe.hasRecipe(stack);
	}
	
	public int getBurnProgress()
	{
		return data.get(3) != 0 && data.get(2) != 0 ? data.get(2) * 24 / data.get(3) : 0;
	}
	
	public int getLitProgress()
	{
		return data.get(1) == 0 ? data.get(0) * 13 / 200 : data.get(0) * 13 / data.get(1);
	}
	
	public boolean isLit()
	{
		return data.get(0) > 0;
	}
}