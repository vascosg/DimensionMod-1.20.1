package net.agentefreitas.dimensionmod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraftforge.registries.ForgeRegistries;

public class BlackWordlessBook extends Item{

    private static int maxListSize = 5;

    public BlackWordlessBook(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!world.isClientSide) {
            double reachDistance = 20.0D;

            // 1. Shift + Clique: Restaurar o último
            if (player.isCrouching()) {
                removeLast(world, player, stack);
                return InteractionResultHolder.success(stack);
            }

            // 2. Tentar capturar Entidade (Raycast)
            Entity entity = getEntityLookingAt(player, reachDistance);
            if (entity != null) {
                capturarEntidade(player, stack, entity);
                return InteractionResultHolder.success(stack);
            }

            // 3. Tentar capturar Bloco (Raycast)
            HitResult hitResult = player.pick(reachDistance, 1.0F, false);
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                capturarBloco(world, player, stack, (BlockHitResult) hitResult);
                return InteractionResultHolder.success(stack);
            }

            player.displayClientMessage(Component.literal("Nada capturado!"), true);
        }

        return InteractionResultHolder.pass(stack);
    }


    public static Entity getEntityLookingAt(Player player, double distance) {
        Vec3 eyePosition = player.getEyePosition(1.0F);
        Vec3 lookVector = player.getViewVector(1.0F);
        Vec3 reachVector = eyePosition.add(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance);

        AABB aabb = player.getBoundingBox()
                .expandTowards(lookVector.scale(distance))
                .inflate(1.0D, 1.0D, 1.0D);

        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(player, eyePosition, reachVector, aabb,
                (entity) -> !entity.isSpectator() && entity.isPickable(), distance);

        if (entityHitResult != null) {
            return entityHitResult.getEntity();
        }
        return null;
    }

    public static void printContent (Player player, ItemStack stack) {

        CompoundTag tag = stack.getTag();
        if (tag == null) {
            player.displayClientMessage(Component.literal("Nenhum dado capturado ainda!"), false);
            return;
        }

        // 1️⃣ Exibe as entidades guardadas
        if (tag.contains("CapturedEntities")) {
            ListTag entities = tag.getList("CapturedEntities", Tag.TAG_COMPOUND);
            if (!entities.isEmpty()) {
                player.displayClientMessage(Component.literal("§e=== Entidades Capturadas ==="), false);
                for (Tag entry : entities) {
                    CompoundTag entityData = (CompoundTag) entry;
                    String id = entityData.getString("Entity");
                    String name = entityData.getString("Name");
                    player.displayClientMessage(Component.literal("• " + name + " (" + id + ")"), false);
                }
            }
        } else {
            player.displayClientMessage(Component.literal("Nenhuma entidade capturada!"), false);
        }

        // 2️⃣ Exibe os blocos guardados
        if (tag.contains("CapturedBlocks")) {
            ListTag blocks = tag.getList("CapturedBlocks", Tag.TAG_COMPOUND);
            if (!blocks.isEmpty()) {
                player.displayClientMessage(Component.literal("§a=== Blocos Capturados ==="), false);
                for (Tag entry : blocks) {
                    CompoundTag blockData = (CompoundTag) entry;
                    String id = blockData.getString("Block");
                    player.displayClientMessage(Component.literal("• " + id ), false);
                }
            }
        } else {
            player.displayClientMessage(Component.literal("Nenhum bloco capturado!"), false);
        }
    }

    public static void placeLastBlock(Level world, Player player, ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("CapturedBlocks")) {
            ListTag blockList = tag.getList("CapturedBlocks", Tag.TAG_COMPOUND);

            if (!blockList.isEmpty()) {
                // Pega o último bloco salvo
                CompoundTag lastBlockData = (CompoundTag) blockList.get(blockList.size() - 1);
                String blockId = lastBlockData.getString("Block");

                Block block = BuiltInRegistries.BLOCK.get(new ResourceLocation(blockId));
                if (block != null) {
                    // Pega a posição em que o jogador está a apontar (como “colocar bloco” normalmente)
                    HitResult hitResult = player.pick(5.0D, 1.0F, false);
                    if (hitResult.getType() == HitResult.Type.BLOCK) {
                        BlockHitResult blockHit = (BlockHitResult) hitResult;
                        BlockPos pos = blockHit.getBlockPos().relative(blockHit.getDirection());

                        // Coloca o bloco
                        world.setBlock(pos, block.defaultBlockState(), 3);
                        player.displayClientMessage(Component.literal("Bloco colocado: " + blockId + " em " + pos), false);

                        // Remove o último da lista
                        blockList.remove(blockList.size() - 1);
                        tag.put("CapturedBlocks", blockList);
                    } else {
                        player.displayClientMessage(Component.literal("Aponta para um bloco para colocar!"), false);
                    }
                } else {
                    player.displayClientMessage(Component.literal("Bloco não encontrado: " + blockId), false);
                }
            } else {
                player.displayClientMessage(Component.literal("Nenhum bloco capturado para restaurar!"), false);
            }
        }
    }

    public static void spawnLastEntity(Level world, Player player, ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("CapturedEntities")) {
            ListTag entityList = tag.getList("CapturedEntities", Tag.TAG_COMPOUND);
            if (entityList.isEmpty()) return;

            CompoundTag lastEntry = (CompoundTag) entityList.get(entityList.size() - 1);

            // 1. Recuperar o ID e o NBT guardado
            String entityId = lastEntry.getString("EntityID");
            CompoundTag entityNBT = lastEntry.getCompound("EntityData");

            EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(entityId));
            if (type != null) {
                Entity entity = type.create(world);
                if (entity != null) {
                    // 2. Aplicar o NBT de volta à entidade (isso restaura a textura, sela, etc.)
                    entity.load(entityNBT);

                    // 3. Definir posição (para não spawnar nas coordenadas antigas onde foi capturado!)
                    Vec3 look = player.getLookAngle().normalize();
                    entity.setPos(player.getX() + look.x * 2, player.getY() + 1, player.getZ() + look.z * 2);

                    world.addFreshEntity(entity);

                    // Remover da lista
                    entityList.remove(entityList.size() - 1);
                    tag.put("CapturedEntities", entityList);
                    player.displayClientMessage(Component.literal("Entidade restaurada com sucesso!"), false);
                }
            }
        }
    }

    public static void setMaxListSize(int newSize) {
        maxListSize = newSize;
    }

    public static void removeLast (Level world,Player player , ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null) {
            ListTag entityList = tag.getList("CapturedEntities", Tag.TAG_COMPOUND);
            ListTag blockList = tag.getList("CapturedBlocks", Tag.TAG_COMPOUND);

            int lastEntityIndex = -1;
            int lastBlockIndex = -1;

            if (!entityList.isEmpty()) {
                CompoundTag lastEntity = (CompoundTag) entityList.get(entityList.size() - 1);
                lastEntityIndex = lastEntity.getInt("Index");
            }

            if (!blockList.isEmpty()) {
                CompoundTag lastBlock = (CompoundTag) blockList.get(blockList.size() - 1);
                lastBlockIndex = lastBlock.getInt("Index");
            }

            // Decide qual restaurar: menor índice => mais antigo
            if (lastBlockIndex != -1 && (lastBlockIndex >= lastEntityIndex || lastEntityIndex == -1)) {
                placeLastBlock(world, player, stack);
            } else if (lastEntityIndex != -1) {
                spawnLastEntity(world, player, stack);
            } else {
                player.displayClientMessage(Component.literal("Nada para restaurar!"), false);
            }
        } else {
            player.displayClientMessage(Component.literal("Nada para restaurar!"), false);
        }
    }

    public static void capturarEntidade(Player player, ItemStack stack, Entity entity) {
        CompoundTag tag = stack.getOrCreateTag();
        ListTag entityList = tag.getList("CapturedEntities", Tag.TAG_COMPOUND);
        ListTag blockList = tag.getList("CapturedBlocks", Tag.TAG_COMPOUND);

        if (entityList.size() >= maxListSize) {
            player.displayClientMessage(Component.literal("O item já está cheio!"), true);
            return;
        }

        int index = entityList.size() + blockList.size();
        CompoundTag entityData = new CompoundTag();

        // --- A MUDANÇA ESTÁ AQUI ---
        // Criamos um novo CompoundTag para guardar TODOS os dados do mob (vida, cor, inventário, etc.)
        CompoundTag entityNBT = new CompoundTag();
        entity.saveWithoutId(entityNBT); // Guarda os dados específicos

        // Guardamos o ID da entidade para sabermos o que spawnar depois
        entityData.putString("EntityID", EntityType.getKey(entity.getType()).toString());
        entityData.put("EntityData", entityNBT); // Colocamos o NBT completo lá dentro
        entityData.putString("Name", entity.getName().getString());
        entityData.putInt("Index", index);
        // ---------------------------

        entityList.add(entityData);
        tag.put("CapturedEntities", entityList);

        entity.remove(Entity.RemovalReason.DISCARDED);

        player.displayClientMessage(Component.literal("Entidade capturada com dados: " + entity.getName().getString()), true);
        printContent(player, stack);
    }

    public static void capturarBloco(Level world, Player player, ItemStack stack, BlockHitResult blockHit) {
        BlockPos pos = blockHit.getBlockPos();
        BlockState state = world.getBlockState(pos);

        CompoundTag tag = stack.getOrCreateTag();
        ListTag entityList = tag.getList("CapturedEntities", Tag.TAG_COMPOUND);
        ListTag blockList = tag.getList("CapturedBlocks", Tag.TAG_COMPOUND);

        if (blockList.size() >= maxListSize) {
            player.displayClientMessage(Component.literal("O item já contém o número máximo de blocos!"), true);
            return;
        }

        int index = entityList.size() + blockList.size();
        CompoundTag blockData = new CompoundTag();
        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        blockData.putString("Block", id.toString());
        blockData.putInt("Index", index);

        blockList.add(blockData);
        tag.put("CapturedBlocks", blockList);

        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

        player.displayClientMessage(Component.literal("Índice do bloco capturado: " + index), true);
        printContent(player, stack);
    }

}
