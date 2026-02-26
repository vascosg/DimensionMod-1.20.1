package net.agentefreitas.dimensionmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
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

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        // 1. Verificamos se é um jogador e se estamos no servidor
        if (pEntity instanceof ServerPlayer serverPlayer && !pLevel.isClientSide) {


            // 3. Obtemos a nossa dimensão customizada
            ResourceKey<Level> destination = ResourceKey.create(Registries.DIMENSION,
                    new ResourceLocation("dimensionmod", "passion_fruit_dimension"));

            ServerLevel targetWorld = serverPlayer.server.getLevel(destination);
            assert targetWorld != null;

            // Escolhe X e Z fixos ou aleatórios
            double x = pEntity.getX();  // Podes mudar para qualquer coordenada
            double z = pEntity.getZ();

            BlockPos safePos = findSafeSpawn(targetWorld, (int) x, (int) z);

            serverPlayer.teleportTo(targetWorld, x, safePos.getY(), z, serverPlayer.getYRot(), serverPlayer.getXRot());

        }
    }

    private static BlockPos findSafeSpawn(ServerLevel level, int x, int z) {
        int blockX = Mth.floor(x);
        int blockZ = Mth.floor(z);
        int maxY = level.getMaxBuildHeight() - 1;
        int minY = level.getMinBuildHeight();

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(blockX, maxY, blockZ);

        // Desce até encontrar bloco sólido com dois de ar acima
        while (pos.getY() > minY) {
            BlockState below = level.getBlockState(pos);
            BlockState above1 = level.getBlockState(pos.above());
            BlockState above2 = level.getBlockState(pos.above(2));

            if (!below.isAir() && above1.isAir() && above2.isAir()) {
                return pos.above(); // ponto de spawn é acima do bloco sólido
            }
            pos.move(Direction.DOWN);
        }

        // Se não encontrou, fallback para Y=100 com posição forçada
        return new BlockPos(blockX, 100, blockZ);
    }
}
