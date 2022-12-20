package org.duckdns.fossilfind.scifi.block.entity;

import java.util.Map;

import org.duckdns.fossilfind.scifi.SciFi;
import org.duckdns.fossilfind.scifi.block.RefineryBlock;
import org.duckdns.fossilfind.scifi.inventory.RefineryMenu;
import org.duckdns.fossilfind.scifi.item.recipes.RefineryRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class RefineryBlockEntity extends BlockEntity implements MenuProvider
{
	private final ItemStackHandler itemHandler;
	private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
	private final Map<Direction, LazyOptional<WrappedHandler>> directionHandler;
	
	private int cookingProgress = 0, cookingTimeTotal, litTime = 0, litDuration;
	protected final ContainerData data;
	
	public RefineryBlockEntity(BlockPos pos, BlockState state)
	{
		super(SciFiBlockEntities.REFINERY.get(), pos, state);
		
		itemHandler = new ItemStackHandler(4)
		{
			@Override
			protected void onContentsChanged(int slot)
			{
				setChanged();
			}
			
			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack)
			{
				return switch(slot)
				{
				case 0 -> RefineryRecipe.hasRecipe(stack);
				case 1 -> ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
				case 2 -> false;
				case 3 -> false;
				default -> super.isItemValid(slot, stack);
				};
			}
		};
		
		directionHandler = Map.of
		(
			Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> itemHandler.isItemValid(i, s) && i == 1)),
			Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> itemHandler.isItemValid(i, s) && i == 1)),
			Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> itemHandler.isItemValid(i, s) && i == 1)),
			Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> itemHandler.isItemValid(i, s) && i == 1)),
			Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> false, (i, s) -> itemHandler.isItemValid(i, s) && i == 0)),
			Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 2 || i == 3, (i, s) -> false))
		);
		
		data = new ContainerData()
		{
			@Override
			public int get(int index)
			{
				return switch(index)
				{
				case 0 -> RefineryBlockEntity.this.litTime;
				case 1 -> RefineryBlockEntity.this.litDuration;
				case 2 -> RefineryBlockEntity.this.cookingProgress;
				case 3 -> RefineryBlockEntity.this.cookingTimeTotal;
				default -> 0;
				};
			}
			
			@Override
			public void set(int index, int value)
			{
				switch(index)
				{
				case 0 -> RefineryBlockEntity.this.litTime = value;
				case 1 -> RefineryBlockEntity.this.litDuration = value;
				case 2 -> RefineryBlockEntity.this.cookingProgress = value;
				case 3 -> RefineryBlockEntity.this.cookingTimeTotal = value;
				}
			}
			
			@Override
			public int getCount()
			{
				return 4;
			}
		};
	}
	
	@Override
	public Component getDisplayName()
	{
		return Component.translatable("container." + SciFi.MODID + ".refinery");
	}
	
	@Override
	public AbstractContainerMenu createMenu(int windowID, Inventory inventory, Player player)
	{
		return new RefineryMenu(windowID, inventory, this, data);
	}
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		lazyItemHandler = LazyOptional.of(() -> itemHandler);
	}
	
	@Override
	public void invalidateCaps()
	{
		super.invalidateCaps();
		lazyItemHandler.invalidate();
	}
	
	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
	{
		if(cap == ForgeCapabilities.ITEM_HANDLER)
		{
			if(side == null)
				return lazyItemHandler.cast();
			
			if(directionHandler.containsKey(side))
			{
				Direction localDir = getBlockState().getValue(RefineryBlock.FACING);
				
				if(side == Direction.UP || side == Direction.DOWN)
					return directionHandler.get(side).cast();
				
				return switch(localDir)
				{
					default -> directionHandler.get(side.getOpposite()).cast();
					case EAST -> directionHandler.get(side.getClockWise()).cast();
					case SOUTH -> directionHandler.get(side).cast();
					case WEST -> directionHandler.get(side.getCounterClockWise()).cast();
				};
			}
		}
		
		return super.getCapability(cap, side);
	}
	
	@Override
	public void load(CompoundTag tag)
	{
		super.load(tag);
		
		itemHandler.deserializeNBT(tag.getCompound("Items"));
		
		cookingProgress = tag.getInt("CookingProgress");
		cookingTimeTotal = tag.getInt("CookingTimeTotal");
		litTime = tag.getInt("LitTime");
		litDuration = ForgeHooks.getBurnTime(itemHandler.getStackInSlot(1), RecipeType.SMELTING);
	}
	
	@Override
	protected void saveAdditional(CompoundTag tag)
	{
		tag.put("Items", itemHandler.serializeNBT());
		
		tag.putInt("CookingProgress", cookingProgress);
		tag.putInt("CookingTimeTotal", cookingTimeTotal);
		tag.putInt("LitTime", litTime);
		
		super.saveAdditional(tag);
	}
	
	public void drops()
	{
		SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
		for(int i = 0; i < itemHandler.getSlots(); i++)
			inventory.setItem(i, itemHandler.getStackInSlot(i));
		
		Containers.dropContents(level, worldPosition, inventory);
	}
	
	public static void serverTick(Level level, BlockPos pos, BlockState state, RefineryBlockEntity entity)
	{
		boolean lit = entity.isLit();
		boolean dirty = false;
		
		if(entity.isLit())
			--entity.litTime;
		
		ItemStack fuel = entity.itemHandler.getStackInSlot(1);
		
		boolean hasIngredient = !entity.itemHandler.getStackInSlot(0).isEmpty();
		boolean hasFuel = !fuel.isEmpty();
		
		if(entity.isLit() || hasFuel && hasIngredient)
		{
			RefineryRecipe recipe = RefineryRecipe.getRecipe(entity.itemHandler.getStackInSlot(0));
			
			entity.cookingTimeTotal = recipe != null ? recipe.getCookingTime() : 0;
			
			if(!entity.isLit() && entity.canBurn(recipe, entity.itemHandler, entity.itemHandler.getSlotLimit(0)))
			{
				entity.litTime = ForgeHooks.getBurnTime(fuel, RecipeType.SMELTING);
				entity.litDuration = entity.litTime;
				
				if(entity.isLit())
				{
					dirty = true;
					
					if(fuel.hasCraftingRemainingItem())
						entity.itemHandler.setStackInSlot(1, fuel.getCraftingRemainingItem());
					else if(hasFuel)
					{
						fuel.shrink(1);
						if(fuel.isEmpty())
							entity.itemHandler.setStackInSlot(1, fuel.getCraftingRemainingItem());
					}
				}
			}
			
			if(entity.isLit() && entity.canBurn(recipe, entity.itemHandler, entity.itemHandler.getSlotLimit(0)))
			{
				entity.cookingProgress++;
				
				if(entity.cookingProgress == entity.cookingTimeTotal)
				{
					entity.cookingProgress = 0;
					
					entity.burn(recipe, entity.itemHandler, entity.itemHandler.getSlotLimit(0));
					
					dirty = true;
				}
			}
			else
				entity.cookingProgress = 0;
		}
		else if(!entity.isLit() && entity.cookingProgress > 0)
			entity.cookingProgress = Mth.clamp(entity.cookingProgress - 2, 0, entity.cookingTimeTotal);
		
		if(lit != entity.isLit())
		{
			dirty = true;
			state = state.setValue(RefineryBlock.LIT, Boolean.valueOf(entity.isLit()));
			level.setBlock(pos, state, 3);
		}
		
		if(dirty)
		{
			setChanged(level, pos, state);
		}
	}
	
	private boolean isLit()
	{
		return litTime > 0;
	}
	
	public boolean canBurn(@Nullable RefineryRecipe recipe, ItemStackHandler items, int stackSize)
	{
		if(!itemHandler.getStackInSlot(0).isEmpty() && recipe != null)
		{
			ItemStack output = recipe.getResultItem();
			
			if(output.isEmpty())
				return false;
			
			ItemStack result1 = itemHandler.getStackInSlot(2);
			ItemStack result2 = itemHandler.getStackInSlot(3);
			
			if(result1.isEmpty() && result2.isEmpty())
				return true;
			
			if(!result1.sameItem(output) || !result2.sameItem(output))
				return false;
			
			if(result1.getCount() + output.getCount() <= stackSize && result1.getCount() + output.getCount() <= result1.getMaxStackSize() && result2.getCount() + output.getCount() <= stackSize && result2.getCount() + output.getCount() <= result2.getMaxStackSize())
				return true;
			
			return result1.getCount() + output.getCount() <= output.getMaxStackSize() && result2.getCount() + output.getCount() <= output.getMaxStackSize();
		}
		
		return false;
	}
	
	public boolean burn(@Nullable RefineryRecipe recipe, ItemStackHandler items, int stackSize)
	{
		if(recipe != null && canBurn(recipe, items, stackSize))
		{
			ItemStack input = itemHandler.getStackInSlot(0);
			ItemStack output = recipe.getResultItem();
			ItemStack result1 = itemHandler.getStackInSlot(2);
			ItemStack result2 = itemHandler.getStackInSlot(3);
			
			if(result1.isEmpty())
				itemHandler.setStackInSlot(2, output.copy());
			else if(result1.is(output.getItem()))
				result1.grow(output.getCount());
			
			if(result2.isEmpty())
				itemHandler.setStackInSlot(3, output.copy());
			else if(result2.is(output.getItem()))
				result2.grow(output.getCount());
			
			input.shrink(1);
			
			return true;
		}
		
		return false;
	}
}