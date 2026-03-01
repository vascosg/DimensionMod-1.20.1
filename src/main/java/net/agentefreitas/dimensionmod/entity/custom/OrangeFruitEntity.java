package net.agentefreitas.dimensionmod.entity.custom;

import net.agentefreitas.dimensionmod.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

public class OrangeFruitEntity extends LivingEntity {

    private int lifeTime = 0;
    private static final int MAX_LIFE = 5 * 20; // 5 segundos * 20 ticks

    public OrangeFruitEntity(EntityType<? extends OrangeFruitEntity> type, Level world) {
        super(type, world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public void tick() {
        super.tick();

        this.lifeTime++;

        if (!this.level().isClientSide && this.lifeTime >= MAX_LIFE) {
            this.discard();
        }

        if (!this.level().isClientSide) {

            // Gravidade
            this.setDeltaMovement(this.getDeltaMovement().add(0, -0.04, 0));

            // Movimento
            this.move(MoverType.SELF, this.getDeltaMovement());

            // Colisão com chão: não descarta imediatamente, só ajusta Y
            if (this.onGround()) {
                // diminui velocidade vertical
                this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, -0.5, 1.0));
            }

            // Colisão com jogador
            List<Player> players = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(0.2));
            ResourceKey<Level> destination = ResourceKey.create(Registries.DIMENSION,
                    new ResourceLocation("dimensionmod", "orange_dimension"));
            for (Player player : players) {
                if (player instanceof ServerPlayer sp) {
                    // Teleporte ou efeito
                    ServerLevel targetWorld = sp.server.getLevel(destination);
                    assert targetWorld != null;

                    // Escolhe X e Z fixos ou aleatórios
                    double x = player.getX();  // Podes mudar para qualquer coordenada
                    double z = player.getZ();

                    BlockPos safePos = findSafeSpawn(targetWorld, (int) x, (int) z);

                    sp.teleportTo(targetWorld, x, safePos.getY(), z, sp.getYRot(), sp.getXRot());
                    this.discard(); // só descarta depois de tocar no jogador
                }
            }

            ServerLevel currentLevel = (ServerLevel) this.level();
            ServerLevel targetWorld = currentLevel.getServer().getLevel(destination);
            if (targetWorld == null) {
                return;
            }

            List<Pig> pigs = this.level().getEntitiesOfClass(Pig.class, this.getBoundingBox().inflate(0.5));
            for (Pig pig : pigs) {
                // Remove o porco do mundo atual

                double x = pig.getX();  // Podes mudar para qualquer coordenada
                double z = pig.getZ();
                pig.discard();

                // Cria e posiciona a OrangePigEntity no mundo destino
                OrangePigEntity orangePig = new OrangePigEntity(ModEntities.ORANGE_PIG.get(), targetWorld);

                BlockPos safePos = findSafeSpawn(targetWorld, (int) x, (int) z);

                orangePig.moveTo(x, safePos.getY(), z, pig.getYRot(), pig.getXRot());

                // Spawna no servidor
                targetWorld.addFreshEntity(orangePig);
            }
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

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 1.0D)       // Só 1 de vida (meio coração)
                .add(Attributes.MOVEMENT_SPEED, 0.0D); // Ela não anda sozinha
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() { return java.util.Collections.emptyList(); }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) { return ItemStack.EMPTY; }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {}

    @Override
    public HumanoidArm getMainArm() { return HumanoidArm.RIGHT; }


}