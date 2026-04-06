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

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private final ResourceLocation targetDimension;

    public CustomPortalBlock(Properties pProperties, ResourceLocation targetDimension) {

        super(pProperties);
        this.targetDimension = targetDimension;
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

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        // 1. Verificamos se é um jogador e se estamos no servidor
        if (pEntity instanceof ServerPlayer serverPlayer && !pLevel.isClientSide) {


            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof CustomPortalBlockEntity portalBe) {

                // 1. Obtemos a dimensão guardada na BlockEntity
                ResourceKey<Level> destination = ResourceKey.create(Registries.DIMENSION, portalBe.getDestinationDim());
                ServerLevel targetWorld = serverPlayer.server.getLevel(destination);

                if (targetWorld != null) {
                    double x = pEntity.getX();
                    double z = pEntity.getZ();
                    BlockPos safePos = findSafeSpawn(targetWorld, (int) x, (int) z);

                    serverPlayer.teleportTo(targetWorld, x, safePos.getY(), z, serverPlayer.getYRot(), serverPlayer.getXRot());
                }
            }

        }
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof CustomPortalBlockEntity portalBe) {
            // Passa os dados que o Bloco recebeu no construtor para a Entity
            portalBe.setPortalData(this.targetDimension);
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
