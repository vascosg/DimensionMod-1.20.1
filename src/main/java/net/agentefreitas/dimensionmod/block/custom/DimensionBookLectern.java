package net.agentefreitas.dimensionmod.block.custom;

import net.agentefreitas.dimensionmod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.WritableBookItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.item.ItemStack;

public class DimensionBookLectern extends BaseEntityBlock {

    public static final BooleanProperty HAS_BOOK = BooleanProperty.create("has_book");

    public DimensionBookLectern(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HAS_BOOK, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HAS_BOOK);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DimensionBookLecternBlockEntity(pos, state); // Cria uma nova instância de DimensionBookLecternBlockEntity
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemInHand = player.getItemInHand(hand);
        boolean hasBook = state.getValue(HAS_BOOK);

        // Ação do jogador com o livro
        if (!level.isClientSide) {
            // Coloca o livro no bloco
            if (!hasBook && itemInHand.is(ModItems.DIARY_OF_A_CREATOR.get())) {

                // Salva o conteúdo do livro no bloco (NBT)
                CompoundTag bookNBT = itemInHand.getOrCreateTag();

                // Verifique o conteúdo do livro
                System.out.println("Conteúdo do livro: " + bookNBT.toString());

                // Atualiza o estado do bloco para ter o livro
                level.setBlock(pos, state.setValue(HAS_BOOK, true), 3);

                // Armazena os dados do livro no BlockEntity
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof DimensionBookLecternBlockEntity) {
                    ((DimensionBookLecternBlockEntity) blockEntity).setBookNBT(bookNBT);
                    player.sendSystemMessage(Component.literal("NBT guardado!"));
                }

                // Remove o livro do inventário
                itemInHand.shrink(1);
                player.sendSystemMessage(Component.literal("Livro colocado no bloco!"));
                return InteractionResult.SUCCESS;
            }

            // Retira o livro do bloco
            if (hasBook && itemInHand.isEmpty()) {
                // Recupera o livro armazenado
                BlockEntity blockEntity = level.getBlockEntity(pos);

                if (blockEntity == null) {
                    player.sendSystemMessage(Component.literal("BlockEntity não encontrado!"));
                    return InteractionResult.FAIL; // Não continua a execução se o BlockEntity for null
                }

                if (blockEntity instanceof DimensionBookLecternBlockEntity) {
                    CompoundTag savedNBT = ((DimensionBookLecternBlockEntity) blockEntity).getBookNBT();
                    if(savedNBT != null) {
                        ItemStack bookStack = new ItemStack(ModItems.DIARY_OF_A_CREATOR.get());
                        bookStack.setTag(savedNBT); // Restaura o NBT do livro
                        System.out.println("Conteúdo do livro devolvido: " + savedNBT.toString());
                        if (!player.getInventory().add(bookStack)) {
                            player.drop(bookStack, false); // Se não houver espaço no inventário, derruba o livro no chão
                        }
                    } else {
                        player.sendSystemMessage(Component.literal("NBT null!"));
                    }
                }else {
                    player.sendSystemMessage(Component.literal("Not Instance!"));
                }

                // Atualiza o estado do bloco para não ter livro
                level.setBlock(pos, state.setValue(HAS_BOOK, false), 3);
                player.sendSystemMessage(Component.literal("Livro devolvido ao inventário!"));
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
