package net.agentefreitas.dimensionmod.block.custom;

import net.agentefreitas.dimensionmod.block.ModBlockEntities;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static net.agentefreitas.dimensionmod.block.ModBlockEntities.DIMENSION_BOOK_LECTERN_BLOCK_ENTITY;

public class DimensionBookLectern extends BaseEntityBlock {

    public enum BookState implements StringRepresentable {
        EMPTY("empty"),
        BLACK("black"),
        BLUE("blue"),
        GREEN("green"),
        ORANGE("orange"),
        PURPLE("purple"),
        RED("red"),
        YELLOW("yellow");

        private final String name;

        BookState(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    private static DimensionBookLecternBlockEntity DimensionBlockEntity;
    public static final EnumProperty<BookState> BOOK_STATE = EnumProperty.create("book_state", BookState.class);

    public DimensionBookLectern(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BOOK_STATE, BookState.EMPTY));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BOOK_STATE);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        DimensionBlockEntity = new DimensionBookLecternBlockEntity(pos, state);
        return DimensionBlockEntity; // Cria uma nova instância de DimensionBookLecternBlockEntity
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        ItemStack itemInHand = player.getItemInHand(hand);
        BookState hasBook = state.getValue(BOOK_STATE);

        // Ação do jogador com o livro
        if (!level.isClientSide) {

            if(hasBook == BookState.EMPTY) {

                // Coloca o livro no bloco // switch case x.x
                if (itemInHand.is(ModItems.DIARY_OF_A_CREATOR.get())) {

                    placeBook(BookState.BLACK,itemInHand,level,pos,player,state);

                } else if (itemInHand.is(ModItems.BLUE_BOOK.get())) {

                    placeBook(BookState.BLUE,itemInHand,level,pos,player,state);

                } else if (itemInHand.is(ModItems.GREEN_BOOK.get())) {

                    placeBook(BookState.GREEN,itemInHand,level,pos,player,state);

                } else if (itemInHand.is(ModItems.ORANGE_BOOK.get())){

                    placeBook(BookState.ORANGE,itemInHand,level,pos,player,state);

                } else if (itemInHand.is(ModItems.PURPLE_BOOK.get())) {

                    placeBook(BookState.PURPLE,itemInHand,level,pos,player,state);

                } else if (itemInHand.is(ModItems.RED_BOOK.get())) {

                    placeBook(BookState.RED,itemInHand,level,pos,player,state);

                } else if (itemInHand.is(ModItems.YELLOW_BOOK.get())) {

                    placeBook(BookState.YELLOW,itemInHand,level,pos,player,state);

                }
            }

            if (hasBook != BookState.EMPTY) {

                // Retira o livro do bloco
                if (itemInHand.isEmpty()) {

                    if (hasBook == BookState.BLACK) {

                        ItemStack blackStack = new ItemStack(ModItems.DIARY_OF_A_CREATOR.get());
                        removeBook(blackStack,itemInHand,level,pos,player,state);

                    } else if (hasBook == BookState.BLUE) {

                        ItemStack blueStack = new ItemStack(ModItems.BLUE_BOOK.get());
                        removeBook(blueStack,itemInHand,level,pos,player,state);

                    } else if (hasBook == BookState.GREEN) {

                        ItemStack greenStack = new ItemStack(ModItems.GREEN_BOOK.get());
                        removeBook(greenStack,itemInHand,level,pos,player,state);

                    } else if (hasBook == BookState.ORANGE) {

                        ItemStack orangeStack = new ItemStack(ModItems.ORANGE_BOOK.get());
                        removeBook(orangeStack,itemInHand,level,pos,player,state);

                    } else if (hasBook == BookState.PURPLE) {

                        ItemStack purpleStack = new ItemStack(ModItems.PURPLE_BOOK.get());
                        removeBook(purpleStack,itemInHand,level,pos,player,state);

                    } else if (hasBook == BookState.RED) {

                        ItemStack redStack = new ItemStack(ModItems.RED_BOOK.get());
                        removeBook(redStack,itemInHand,level,pos,player,state);

                    } else if (hasBook == BookState.YELLOW) {

                        ItemStack yellowStack = new ItemStack(ModItems.YELLOW_BOOK.get());
                        removeBook(yellowStack,itemInHand,level,pos,player,state);

                    }
                }
            }

        }
        return InteractionResult.PASS;
    }

    private InteractionResult placeBook(BookState bs, ItemStack itemInHand, Level level, BlockPos pos , Player player, BlockState state) {

        // Salva o conteúdo do livro no bloco (NBT)
        CompoundTag bookNBT = itemInHand.getOrCreateTag();

        // Verifique o conteúdo do livro
        //System.out.println("Conteúdo do livro: " + bookNBT.toString());

        // Atualiza o estado do bloco para ter o livro
        level.setBlock(pos, state.setValue(BOOK_STATE, bs), 3);

        // Armazena os dados do livro no BlockEntity
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof DimensionBookLecternBlockEntity) {
            ((DimensionBookLecternBlockEntity) blockEntity).setBookNBT(bookNBT);
            //player.sendSystemMessage(Component.literal("NBT guardado!"));
        }

        // Remove o livro do inventário
        itemInHand.shrink(1);
        //player.sendSystemMessage(Component.literal("Livro colocado no bloco!"));
        return InteractionResult.SUCCESS;
    }

    private InteractionResult removeBook (ItemStack bookStack, ItemStack itemInHand, Level level, BlockPos pos , Player player, BlockState state) {

        // Recupera o livro armazenado
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (blockEntity == null) {
            //player.sendSystemMessage(Component.literal("BlockEntity não encontrado!"));
            return InteractionResult.FAIL; // Não continua a execução se o BlockEntity for null
        }

        if (blockEntity instanceof DimensionBookLecternBlockEntity) {
            CompoundTag savedNBT = ((DimensionBookLecternBlockEntity) blockEntity).getBookNBT();
            if(savedNBT != null) {
                //ItemStack bookStack = new ItemStack(ModItems.DIARY_OF_A_CREATOR.get());
                bookStack.setTag(savedNBT); // Restaura o NBT do livro
                //System.out.println("Conteúdo do livro devolvido: " + savedNBT.toString());
                if (!player.getInventory().add(bookStack)) {
                    player.drop(bookStack, false); // Se não houver espaço no inventário, derruba o livro no chão
                }
            } else {
                //player.sendSystemMessage(Component.literal("NBT null!"));
            }
        }else {
            //player.sendSystemMessage(Component.literal("Not Instance!"));
        }

        // Atualiza o estado do bloco para não ter livro
        level.setBlock(pos, state.setValue(BOOK_STATE, BookState.EMPTY), 3);
        //player.sendSystemMessage(Component.literal("Livro devolvido ao inventário!"));
        return InteractionResult.SUCCESS;
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return level.isClientSide() ? null :  (level0, pos0, state0, blockEntity) -> ((DimensionBookLecternBlockEntity)blockEntity).tick();
    }

    public BlockEntity getDimensionBlockEntity() {
        return DimensionBlockEntity;
    }
}
