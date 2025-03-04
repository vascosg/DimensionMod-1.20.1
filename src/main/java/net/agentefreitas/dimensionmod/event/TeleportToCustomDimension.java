package net.agentefreitas.dimensionmod.event;

import net.agentefreitas.dimensionmod.block.ModBlocks;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;

import static java.lang.Thread.sleep;

@Mod.EventBusSubscriber
public class TeleportToCustomDimension {
    private static final ResourceKey<Level> CUSTOM_DIMENSION = ResourceKey.create(
            Registries.DIMENSION,
            new ResourceLocation("dimensionmod", "empty_dimension")
    );

    private static  BlockPos playerPos ;


    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.@NotNull RightClickBlock event) throws InterruptedException {
        if (!event.getLevel().isClientSide) { // Apenas no servidor
            ServerPlayer player = (ServerPlayer) event.getEntity();

            boolean hitDoor = event.getLevel().getBlockState(event.getPos()).getBlock() == ModBlocks.DIMENSION_DOOR_BLOCK.get();
            boolean keyHand = player.getMainHandItem().is(ModItems.DIMENSION_DOOR_KEY.get());
            boolean isOverworld = event.getLevel().dimension().location().equals(Level.OVERWORLD.location());

            // Verifica se o bloco que o player está a clicar é o Dimension Door Block
            if (hitDoor) {

                // Verifica se está com a chave na mão
                if (keyHand) {

                    if(isOverworld) {

                        //player.sendSystemMessage(Component.literal("Going to dimension!"));

                        savePlayerPosition(player);

                        ServerLevel targetDimension = player.getServer().getLevel(CUSTOM_DIMENSION);

                        if (targetDimension != null) {

                            // Efeito de som e partículas
                            makeParticles(event);
                            // Espera 1 segundo para dar efeito
                            sleep(1000);

                            CommandSourceStack source = player.createCommandSourceStack().withPermission(4);
                            player.getServer().getCommands().performPrefixedCommand(source, "reload");
                            sleep(1000);

                            player.teleportTo(targetDimension, 0.5, 55, 0.5, player.getYRot(), player.getXRot());
                        }


                   } else {

                        //player.sendSystemMessage(Component.literal("Going to OVERWORLD!"));

                        ServerLevel overworld = player.getServer().getLevel(Level.OVERWORLD);
                        if (overworld != null) {

                            CompoundTag playerData = player.getPersistentData();
                            if (playerData.contains("savedPosition")) {
                                CompoundTag positionTag = playerData.getCompound("savedPosition");

                                double posX = positionTag.getDouble("posX");
                                double posY = positionTag.getDouble("posY");
                                double posZ = positionTag.getDouble("posZ");

                                makeParticles(event);
                                sleep(1000);
                                //player.teleportTo(overworld, playerPos.getX(), playerPos.getY(), playerPos.getZ(), player.getYRot(), player.getXRot());
                                player.changeDimension(overworld);
                                player.teleportTo(overworld, posX, posY, posZ, player.getYRot(), player.getXRot());
                            }
                        }
                    }
                }
            }
        }
    }

    private static void makeParticles(PlayerInteractEvent.@NotNull RightClickBlock event){

        ServerPlayer player = (ServerPlayer) event.getEntity();
        ((ServerLevel) player.level()).sendParticles(ParticleTypes.PORTAL, player.getX(), player.getY() + 1, player.getZ(), 50, 0.5, 1, 0.5, 0.1);
        player.level().playSound(null, player.blockPosition(), SoundEvents.PORTAL_TRIGGER, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    public static void savePlayerPosition(ServerPlayer player) {

        CompoundTag playerData = player.getPersistentData();
        CompoundTag positionTag = new CompoundTag();

        positionTag.putDouble("posX", player.getX());
        positionTag.putDouble("posY", player.getY());
        positionTag.putDouble("posZ", player.getZ());


        playerData.put("savedPosition", positionTag);

    }
}