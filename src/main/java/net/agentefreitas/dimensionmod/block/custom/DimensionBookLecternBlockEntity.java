package net.agentefreitas.dimensionmod.block.custom;

import net.agentefreitas.dimensionmod.block.ModBlockEntities;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.phys.AABB;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class DimensionBookLecternBlockEntity extends BlockEntity {

    private BlockPos pos;
    private String lastMessage = "";
    private CompoundTag bookNBT = new CompoundTag();
    private static final ResourceKey<Level> EMPTY_DIMENSION = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("dimensionmod", "empty_dimension"));
    private static boolean accelerateTicks = false;
    private static boolean slowTicks = false;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();


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
        compound.put("BookNBT", bookNBT);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (compound.contains("BookNBT", 10)) {
            this.bookNBT = compound.getCompound("BookNBT");
        }
    }


    public void tick() {

    }

    public void messageReceiver(String message, ServerPlayer player) {

        if (level.dimension() == level.OVERWORLD) {
            List<Player> players = level.getEntitiesOfClass(Player.class, new AABB(pos).inflate(3)); // Todos os players perto

            BlockState state = this.getBlockState();
            DimensionBookLectern.BookState bookState = state.getValue(DimensionBookLectern.BOOK_STATE);
            if (bookState == DimensionBookLectern.BookState.YELLOW) {
                yellowState(player,message,bookState);
            } else if (bookState == DimensionBookLectern.BookState.RED) {
                redState(player,message,bookState);
            } else if (bookState == DimensionBookLectern.BookState.BLUE) {
                blueState(message,player);
            } else if (bookState == DimensionBookLectern.BookState.GREEN) {
                greenState(message,player);
            } else if (bookState == DimensionBookLectern.BookState.BLACK) {
                blackState(message,player);
            } else if (bookState == DimensionBookLectern.BookState.ORANGE) {
                orangeState(message,player);
            }
        }
    }

    private void yellowState(ServerPlayer player,String message ,DimensionBookLectern.BookState bookState){

        if (!message.equalsIgnoreCase("levitate")) return;

        if (!player.getAbilities().mayfly) {
            player.getAbilities().mayfly = true;
            player.onUpdateAbilities();
            player.sendSystemMessage(Component.literal("**LEVITATE! Agora podes voar! 🔥**"));
            System.out.println(player.getName().getString() + " agora pode voar!");
            while(bookState == DimensionBookLectern.BookState.YELLOW) {
                BlockState state = this.getBlockState();
                bookState = state.getValue(DimensionBookLectern.BOOK_STATE);
            }
            player.getAbilities().mayfly = false;
            player.onUpdateAbilities();
        }

    }

    private void redState (ServerPlayer player,String message ,DimensionBookLectern.BookState bookState) {

        if (message.equalsIgnoreCase("golden blood")) {

            player.setAbsorptionAmount(20.0F);

            for (int i = 0; i < 50; i++) {
                double radius = 3.0;
                double angle = level.random.nextDouble() * 360;
                double distance = level.random.nextDouble() * radius;
                double x = pos.getX() + 0.5 + Math.cos(Math.toRadians(angle)) * distance;
                double z = pos.getZ() + 0.5 + Math.sin(Math.toRadians(angle)) * distance;
                double y = pos.getY() + 1.2 + level.random.nextDouble() * 2;

                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x, y, z, 0, 0.02, 0);
                level.addParticle(ParticleTypes.ENCHANT, x, y, z, 0, 0.1, 0);
            }

            while (bookState == DimensionBookLectern.BookState.RED) {
                BlockState state = this.getBlockState();
                bookState = state.getValue(DimensionBookLectern.BOOK_STATE);

                player.getFoodData().setSaturation(20.0F);
                player.getFoodData().setFoodLevel(20);

            }
        }

            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
            player.setHealth(20.0F);
            player.setAbsorptionAmount(0.0F);

    }

    private void blueState (String message,Player player) {
        System.out.println("entrou blue");//TODO this is too simple

        String command = "";
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        CommandSourceStack source = server.createCommandSourceStack();

        command = switch (message) {
            case "rain" -> "weather rain";
            case "tempest" -> "weather thunder";
            case "sunny" -> "weather clear";
            default -> command;
        };
        server.getCommands().performPrefixedCommand(source, command);
    }

    private void greenState (String message, Player player) {
        System.out.println("entrou green");

        String command = "";

        if (message.equalsIgnoreCase("speed up")) {
            accelerateTicks = true;

            scheduler.schedule(() -> {
                accelerateTicks = false;
                System.out.println("accelerateTicks set to false after 1 minute");
            }, 1, TimeUnit.MINUTES);
        } else if (message.equalsIgnoreCase("slow down")) { //TODO not being used
            slowTicks = true;

            scheduler.schedule(() -> {
                accelerateTicks = false;
                System.out.println("SlowTicks set to false after 1 minute");
            }, 1, TimeUnit.MINUTES);
        }
    }

    private void blackState (String message, Player player) {

        //TODO does nothing as the black state shouldnt need the lectern
    }

    private void orangeState (String message, Player player) {

        if (!message.equalsIgnoreCase("5 element edge")) return;

        // Itens que você quer checar
        ItemStack fire = new ItemStack(ModItems.FIRE_TREASURE.get());
        ItemStack water = new ItemStack(ModItems.WATER_TREASURE.get());
        ItemStack wood = new ItemStack(ModItems.WOOD_TREASURE.get());
        ItemStack earth = new ItemStack(ModItems.EARTH_TREASURE.get());
        ItemStack gold = new ItemStack(ModItems.GOLD_TREASURE.get());

        // Verifica se tem pelo menos 1 de cada
        if (hasItem(player, fire) &&
                hasItem(player, water) &&
                hasItem(player, wood) &&
                hasItem(player, earth) &&
                hasItem(player, gold)) {

            // Remove 1 de cada item
            removeOneItem(player, fire.getItem());
            removeOneItem(player, water.getItem());
            removeOneItem(player, wood.getItem());
            removeOneItem(player, earth.getItem());
            removeOneItem(player, gold.getItem());

            // Adiciona a espada no inventário
            ItemStack sword = new ItemStack(ModItems.FIVE_ELEMENT_INDESTRUCTIBLE_SWORD.get());
            if (!player.getInventory().add(sword)) {
                player.drop(sword, false); // Se o inventário estiver cheio, joga no chão
            }
        }
        
    }

    private boolean hasItem(Player player, ItemStack item) {
        return player.getInventory().contains(item);
    }

    private void removeOneItem(Player player, Item item) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (!stack.isEmpty() && stack.getItem().equals(item)) {
                stack.shrink(1);
                if (stack.isEmpty()) {
                    player.getInventory().setItem(i, ItemStack.EMPTY);
                }
                return;
            }
        }
    }

    public static boolean getAccelerateTicks() {
        return accelerateTicks;
    }

    public static boolean getSlowTicks() {
        return slowTicks;
    }


}
