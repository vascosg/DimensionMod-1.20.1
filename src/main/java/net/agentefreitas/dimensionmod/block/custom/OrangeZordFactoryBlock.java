package net.agentefreitas.dimensionmod.block.custom;

import net.agentefreitas.dimensionmod.block.ModBlockEntities;
import net.agentefreitas.dimensionmod.entity.ModEntities;
import net.agentefreitas.dimensionmod.entity.custom.OrangeZordFactoryEntity;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.property.Properties;
import org.jetbrains.annotations.Nullable;

public class OrangeZordFactoryBlock extends Block implements EntityBlock {
    public OrangeZordFactoryBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new OrangeZordFactoryBlockEntity(pPos, pState);
    }

    // Faz com que a animação comece quando o bloco aparece no mundo
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if (!pLevel.isClientSide && pPlacer instanceof Player player) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof OrangeZordFactoryBlockEntity factoryBe) {
                factoryBe.setOwner(player.getUUID());
                System.out.println("1. UUID enviado para a BlockEntity: " + player.getUUID());
            }
        }

        if (!pLevel.isClientSide()) {
            // Criamos a nossa entidade visual
            OrangeZordFactoryEntity visual = ModEntities.ORANGE_ZORD_FACTORY.get().create(pLevel);

            if (visual != null) {
                // Posicionamos no centro do bloco.
                // Nota: Se o modelo ainda aparecer um pouco fora, ajusta o Y (pPos.getY())
                visual.moveTo(pPos.getX() + 0.5D, pPos.getY(), pPos.getZ() + 0.5D, 0, 0);

                // Adicionamos ao mundo
                pLevel.addFreshEntity(visual);

                // Opcional: Se quiseres guardar a entidade na BlockEntity para removê-la depois
                BlockEntity be = pLevel.getBlockEntity(pPos);
                if (be instanceof OrangeZordFactoryBlockEntity factoryBe) {
                    factoryBe.setVisualEntity(visual);
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);

        if (itemInHand.is(ModItems.CONDENSED_ZORD.get())) {

            if (!pLevel.isClientSide()) {
                BlockEntity be = pLevel.getBlockEntity(pPos);
                if (be instanceof OrangeZordFactoryBlockEntity factoryBe) {
                    factoryBe.triggerAnimation();
                }
            }

             itemInHand.shrink(1);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    // Garante que o renderer da Block Entity é chamado (se usares um custom renderer)
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.ORANGE_ZORDE_FACTORY_BLOCK_ENTITY.get(), OrangeZordFactoryBlockEntity::tick);
    }

    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> pServerType, BlockEntityType<E> pClientType, BlockEntityTicker<? super E> pTicker) {
        return pClientType == pServerType ? (BlockEntityTicker<A>) pTicker : null;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof OrangeZordFactoryBlockEntity factoryBe) {
                factoryBe.removeVisualEntity();
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state) { return RenderShape.INVISIBLE; }

    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

}