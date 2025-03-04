package net.agentefreitas.dimensionmod.block.custom;

import net.agentefreitas.dimensionmod.block.ModBlockEntities;
import net.agentefreitas.dimensionmod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLOutput;
import java.util.List;
import java.util.UUID;


public class DimensionBookLecternBlockEntity extends BlockEntity {

    private BlockPos pos;
    private String lastMessage = "";
    private CompoundTag bookNBT = new CompoundTag(); // Armazena o NBT do livro
    private static final ResourceKey<Level> EMPTY_DIMENSION = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("dimensionmod", "empty_dimension"));

    public DimensionBookLecternBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DIMENSION_BOOK_LECTERN_BLOCK_ENTITY.get(), pos, state);
        this.pos = pos;
    }

    public void setLastMessage(String message) {
        if(message.isEmpty() || message.isBlank()) {
            return;
        }
        System.out.println("ultima menssagem defenida : " + message);
        this.lastMessage = message;
    }


    // Métodos para salvar e carregar o conteúdo do livro
    public void setBookNBT(CompoundTag nbt) {
        this.bookNBT = nbt;
    }

    public CompoundTag getBookNBT() {
        return this.bookNBT;
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("BookNBT", bookNBT); // Salva o NBT do livro no BlockEntity
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (compound.contains("BookNBT", 10)) {
            this.bookNBT = compound.getCompound("BookNBT"); // Carrega o NBT do livro
        }
    }


    public void tick() {
        /**
        if (level == null || level.isClientSide) return;

        //(level.dimension().equals(EMPTY_DIMENSION))
        if (level.dimension() == level.OVERWORLD) {
            List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(pos).inflate(3)); // Todos os players perto
            if (players.isEmpty()) {
                System.out.println("nenhum player");
            }
            //System.out.printf("posicao da entity :" + pos.getX() + " " + pos.getY() + " " + pos.getZ());
            for (Player player : players) {
                if (lastMessage.equalsIgnoreCase("levitate")) {
                    System.out.println("a menssagem foi levitate");
                    if (!player.getAbilities().mayfly) {
                        player.getAbilities().mayfly = true;
                        player.onUpdateAbilities();
                        player.sendSystemMessage(Component.literal("**LEVITATE! Agora podes voar! 🔥**"));
                        System.out.println(player.getName().getString() + " agora pode voar!");
                    }
                } else {
                    System.out.println("a menssagem nao foi levitate mas sim : " + lastMessage );
                }
            }
            //lastMessage = ""; // Reset para não repetir
        }**/
    }

    public void noTick(String message) {
         System.out.println("entrou no noTick");
        //if (level == null || level.isClientSide) return;

        //(level.dimension().equals(EMPTY_DIMENSION))
        if (level.dimension() == level.OVERWORLD) {
            List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(pos).inflate(3)); // Todos os players perto
            if (players.isEmpty()) {
                System.out.println("nenhum player");
            }
            //System.out.printf("posicao da entity :" + pos.getX() + " " + pos.getY() + " " + pos.getZ());
            for (Player player : players) {
                if (message.equalsIgnoreCase("levitate")) {
                    System.out.println("a menssagem foi levitate");
                    if (!player.getAbilities().mayfly) {
                        player.getAbilities().mayfly = true;
                        player.onUpdateAbilities();
                        player.sendSystemMessage(Component.literal("**LEVITATE! Agora podes voar! 🔥**"));
                        System.out.println(player.getName().getString() + " agora pode voar!");
                    }
                } else {
                    System.out.println("a menssagem nao foi levitate mas sim : " + lastMessage );
                }
            }
            //lastMessage = ""; // Reset para não repetir
        }
    }
}
