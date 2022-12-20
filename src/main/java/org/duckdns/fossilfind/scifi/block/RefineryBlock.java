package org.duckdns.fossilfind.scifi.block;

import java.util.List;

import org.duckdns.fossilfind.scifi.block.entity.RefineryBlockEntity;
import org.duckdns.fossilfind.scifi.block.entity.SciFiBlockEntities;
import org.duckdns.fossilfind.scifi.util.KeyboardHelpers;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class RefineryBlock extends BaseEntityBlock
{
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty LIT = BooleanProperty.create("lit");
	
	public RefineryBlock(Properties properties)
	{
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
	{
		return new RefineryBlockEntity(pos, state);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
	{
		return level.isClientSide ? null : createTickerHelper(type, SciFiBlockEntities.REFINERY.get(), RefineryBlockEntity::serverTick);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
	{
		if(level.isClientSide)
			return InteractionResult.SUCCESS;
		
		BlockEntity be = level.getBlockEntity(pos);
		if(be instanceof RefineryBlockEntity)
			NetworkHooks.openScreen((ServerPlayer) player, (RefineryBlockEntity) be, pos);
		
		return InteractionResult.CONSUME;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context)
	{
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if(!state.is(newState.getBlock()))
		{
			BlockEntity be = level.getBlockEntity(pos);
			if(be instanceof RefineryBlockEntity)
				((RefineryBlockEntity) be).drops();
		}
		
		super.onRemove(state, level, pos, newState, isMoving);
	}
	
	@Override
	public boolean hasAnalogOutputSignal(BlockState state)
	{
		return true;
	}
	
	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos)
	{
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
	}
	
	@Override
	public RenderShape getRenderShape(BlockState state)
	{
		return RenderShape.MODEL;
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation direction)
	{
		return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
	}
	

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirror)
	{
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(FACING, LIT);
	}
	
	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand)
	{
		if(state.getValue(LIT))
		{
			double x = (double) pos.getX() + 0.5d;
			double y = (double) pos.getY();
			double z = (double) pos.getZ() + 0.5d;
			
			if(rand.nextDouble() < 0.1d)
				level.playLocalSound(x, y, z, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1f, 1f, false);
			
			Direction direction = state.getValue(FACING);
			Direction.Axis axis = direction.getAxis();
			
			double xzModifier = rand.nextDouble() * 0.6d - 0.3d;
			double xOffset = axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52d : xzModifier;
			double yOffset = rand.nextDouble() * 6d / 16d;
			double zOffset = axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52d : xzModifier;
			
			level.addParticle(ParticleTypes.SMOKE, x + xOffset, y + yOffset, z + zOffset, 0, 0, 0);
			level.addParticle(ParticleTypes.FLAME, x + xOffset, y + yOffset, z + zOffset, 0, 0, 0);
		}
	}
	
	@Override
	public void appendHoverText(ItemStack stack, BlockGetter getter, List<Component> tooltip, TooltipFlag flag)
	{
		if(KeyboardHelpers.shift())
		{
			tooltip.add(Component.literal("Smelts ores in a more pure way").withStyle(ChatFormatting.GREEN));
			tooltip.add(Component.literal("than the furnace to double the output").withStyle(ChatFormatting.GREEN));
		}
		else
			tooltip.add(Component.literal("Hold SHIFT").withStyle(ChatFormatting.DARK_PURPLE));
	}
}