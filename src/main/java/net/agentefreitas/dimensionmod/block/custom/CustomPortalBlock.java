package net.agentefreitas.dimensionmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;



import javax.swing.text.html.BlockView;

public class CustomPortalBlock extends BaseEntityBlock {

    /*
    public enum PortalState implements StringRepresentable {
        EMPTY("empty"), //WHITE DEFAULT
        BLACK("black"),
        BLUE("blue"),
        GREEN("green"),
        ORANGE("orange"), //to orange dimension
        PURPLE("purple"),
        RED("red"),
        YELLOW("yellow");

        private final String name;

        PortalState(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }*/

    //private static CustomPortalBlockEntity CustomPortalBlockEntity;
    //public static final EnumProperty<CustomPortalBlock.PortalState> PORTAL_STATE = EnumProperty.create("portal_state", CustomPortalBlock.PortalState.class);

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public CustomPortalBlock(Properties pProperties) {
        super(pProperties);
        //this.registerDefaultState(this.stateDefinition.any().setValue(PORTAL_STATE, CustomPortalBlock.PortalState.ORANGE));
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        if (facing.getAxis() == Direction.Axis.X) {
            // Portal vertical na direção EAST-WEST
            return Block.box(7.5, 0, 0, 8.5, 16, 16);
        } else {
            // Portal vertical na direção NORTH-SOUTH
            return Block.box(0, 0, 7.5, 16, 16, 8.5);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CustomPortalBlockEntity(pos, state);
    }

    /*
    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (world.isClientSide) return;

        boolean foundOrange = false;
        for (Direction dir : Direction.values()) {
            BlockState neighbor = world.getBlockState(pos.relative(dir));
            if (neighbor.is(Blocks.ORANGE_TERRACOTTA)) {
                foundOrange = true;
                break;
            }
        }

        if (foundOrange && state.getValue(PORTAL_STATE) != PortalState.ORANGE) {
            // Aqui o bloco já existe, não dá mais erro
            world.setBlock(pos, state.setValue(PORTAL_STATE, PortalState.ORANGE), 3);
        }
    }
    */

}
