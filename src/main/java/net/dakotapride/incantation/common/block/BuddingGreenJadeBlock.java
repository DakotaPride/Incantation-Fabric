package net.dakotapride.incantation.common.block;

import net.dakotapride.incantation.common.IncantationMod;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

public class BuddingGreenJadeBlock extends GreenJadeBlock {
    public BuddingGreenJadeBlock(Settings settings) {
        super(settings);
    }

    private static final Direction[] DIRECTIONS = Direction.values();

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(5) != 0) {
            return;
        }

        Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = null;
        if (canGrowIn(blockState)) {
            block = IncantationMod.SMALL_GREEN_JADE_BUD;
        } else if (blockState.isOf(IncantationMod.SMALL_GREEN_JADE_BUD) && blockState.get(GreenJadeClusterBlock.FACING) == direction) {
            block = IncantationMod.MEDIUM_GREEN_JADE_BUD;
        } else if (blockState.isOf(IncantationMod.MEDIUM_GREEN_JADE_BUD) && blockState.get(GreenJadeClusterBlock.FACING) == direction) {
            block = IncantationMod.LARGE_GREEN_JADE_BUD;
        } else if (blockState.isOf(IncantationMod.LARGE_GREEN_JADE_BUD) && blockState.get(GreenJadeClusterBlock.FACING) == direction) {
            block = IncantationMod.GREEN_JADE_CLUSTER;
        }

        if (block != null) {


            BlockState blockState2 = block.getDefaultState().with(AmethystClusterBlock.FACING, direction).with(AmethystClusterBlock.WATERLOGGED, Boolean.valueOf((blockState.getFluidState().getFluid() == Fluids.WATER)));
            world.setBlockState(blockPos, blockState2);
        }
    }

    public static boolean canGrowIn(BlockState state) {
        return (state.isAir() || (state.isOf(Blocks.WATER) && state.getFluidState().getLevel() == 8));
    }

}
