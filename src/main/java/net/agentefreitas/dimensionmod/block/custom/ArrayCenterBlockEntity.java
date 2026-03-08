package net.agentefreitas.dimensionmod.block.custom;

import net.agentefreitas.dimensionmod.block.ModBlockEntities;
import net.agentefreitas.dimensionmod.entity.ModEntities;
import net.agentefreitas.dimensionmod.entity.custom.GlowBoxEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArrayCenterBlockEntity extends BlockEntity {

    private int radius = 5;
    public static final List<ArrayCenterBlockEntity> ACTIVE_BARRIERS = new ArrayList<>();
    private final List<UUID> activeGlows = new ArrayList<>();
    private boolean spawnedGlows = false;
    private int startupDelay = 200;

    public ArrayCenterBlockEntity(BlockPos pPos, BlockState pBlockState) {
        // Usa o teu RegistryObject aqui para o super
        super(ModBlockEntities.ARRAY_CENTER_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        this.spawnedGlows = false;
        clearGlows();
        if (this.level != null && !this.level.isClientSide) {
            if (!ACTIVE_BARRIERS.contains(this)) {
                ACTIVE_BARRIERS.add(this);
            }
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        ACTIVE_BARRIERS.remove(this);
        clearGlows();
    }

    public int getRadius() {
        return radius;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ArrayCenterBlockEntity be) {
        if (level.isClientSide) return;

        // --- 1. LÓGICA DE NASCIMENTO (AGUARDA ESTRUTURA) ---
        if (!be.spawnedGlows || be.activeGlows.isEmpty()) {

            // Se ainda estamos no tempo de espera inicial (delay)
            if (be.startupDelay > 0) {
                be.startupDelay--;
                return; // Sai do tick e espera o próximo
            }

            // Tenta dar spawn. Se o método retornar TRUE (conseguiu 10), avançamos.
            // Se retornar FALSE (não achou blocos sólidos), ele tentará novamente no próximo tick.
            if (be.choseRandomGlowBlockPos()) {
                be.spawnedGlows = true;
                be.setChanged();

            }
            return; // Não executa a barreira nem a autodestruição enquanto não spawnar
        }

        // --- 2. LÓGICA DA BARREIRA (PLAYERS E PROJÉTEIS) ---
        // Usamos be.getRadius() para garantir que pegamos o valor da instância
        int currentRadius = be.getRadius();
        double minWorldY = level.getMinBuildHeight();
        double maxWorldY = level.getMaxBuildHeight();

        AABB areaInteresse = new AABB(
                pos.getX() - currentRadius - 0.5, minWorldY, pos.getZ() - currentRadius - 0.5,
                pos.getX() + currentRadius + 1.5, maxWorldY, pos.getZ() + currentRadius + 1.5
        );

        List<Player> players = level.getEntitiesOfClass(Player.class, areaInteresse);
        for (Player player : players) {
            double dx = Math.abs(player.getX() - (pos.getX() + 0.5));
            double dz = Math.abs(player.getZ() - (pos.getZ() + 0.5));

            if (dx > currentRadius || dz > currentRadius) {
                Vec3 centerPos = new Vec3(pos.getX() + 0.5, player.getY(), pos.getZ() + 0.5);
                Vec3 pushDir = player.position().subtract(centerPos).normalize();
                player.setDeltaMovement(pushDir.scale(0.6));
                player.hurtMarked = true;
            }
        }

        // Bloqueio de Projéteis
        AABB areaBuscaProjeteis = new AABB(pos).inflate(currentRadius + 1.5);
        List<Projectile> projeteis = level.getEntitiesOfClass(Projectile.class, areaBuscaProjeteis);
        for (Projectile p : projeteis) {
            double dx = Math.abs(p.getX() - (pos.getX() + 0.5));
            double dz = Math.abs(p.getZ() - (pos.getZ() + 0.5));
            if (Math.abs(dx - currentRadius) < 0.5 || Math.abs(dz - currentRadius) < 0.5) {
                p.discard();
            }
        }

        // --- 3. VERIFICAÇÃO DE SAÚDE (A CADA 1 SEGUNDO) ---
        if (level.getGameTime() % 200 == 0) {
            // Limpa UUIDs mortos
            be.activeGlows.removeIf(uuid -> {
                Entity e = ((ServerLevel)level).getEntity(uuid);
                return e == null || !e.isAlive();
            });

            // Agora é seguro verificar autodestruição, pois já spawnou!
            be.verifySelDestruction();
        }
    }

    @Override
    public AABB getRenderBoundingBox() {
        // Retorna uma área gigante para o Minecraft saber que deve desenhar o shader
        // mesmo que o bloco central não esteja no meio da tela.
        return new AABB(worldPosition).inflate(radius + 1);
    }

    public boolean choseRandomGlowBlockPos() {
        if (this.level == null || this.level.isClientSide) return false;

        if (level instanceof ServerLevel serverLevel) {
            if (!serverLevel.isPositionEntityTicking(this.worldPosition)) {
                return false;
            }
        }

        RandomSource random = this.level.random;
        BlockPos centro = this.worldPosition;
        int tentativas = 0;
        int blocosEncontrados = activeGlows.size(); // Começa com o que já temos

        // Tentamos preencher até chegar a 10
        while (blocosEncontrados < 10 && tentativas < 50) {
            tentativas++;

            int rangeExtra = 20;
            int maxRange = radius + rangeExtra;

            int rx = random.nextInt(maxRange * 2 + 1) - maxRange;
            int rz = random.nextInt(maxRange * 2 + 1) - maxRange;
            int ry = random.nextInt(7) - 3; // Aumentei um pouco o alcance vertical (-3 a +3)

            if (Math.abs(rx) > radius || Math.abs(rz) > radius) {
                BlockPos posAlvo = centro.offset(rx, ry, rz);

                // Se encontrar um bloco que NÃO é ar, spawna
                if (!level.getBlockState(posAlvo).isAir()) {
                    spawnGlowCube(level, posAlvo);
                    blocosEncontrados++;
                }
            }
        }

        // Retorna true se conseguimos os 10, false se ainda faltam
        return blocosEncontrados >= 10;
    }

    // Versão atualizada do teu spawnGlowCube para suportar múltiplos UUIDs
    private void spawnGlowCube(Level level, BlockPos targetPos) {
        // Verifica se estamos no lado do servidor
        if (!level.isClientSide) {

            GlowBoxEntity glowBox = ModEntities.GLOW_BOX.get().create(level);
            if (glowBox != null) {
                glowBox.setPos(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);
                glowBox.setGlowingTag(true);
                glowBox.setInvulnerable(true);

                configurarEquipaAzul(glowBox);
                UUID newUUID = glowBox.getUUID();
                this.activeGlows.add(glowBox.getUUID());
                this.setChanged();
                level.addFreshEntity(glowBox);
            }
        }
    }

    public void clearGlows() {
        if (this.level instanceof ServerLevel sLevel) {
            for (UUID uuid : activeGlows) {
                Entity entity = sLevel.getEntity(uuid);
                if (entity != null) {
                    entity.discard();
                }
            }
            activeGlows.clear();
        }
    }

    private void configurarEquipaAzul(Entity entity) {
        Scoreboard scoreboard = entity.level().getScoreboard();
        String teamName = "BarreiraAzul";

        PlayerTeam team = scoreboard.getPlayerTeam(teamName);
        if (team == null) {
            team = scoreboard.addPlayerTeam(teamName);
            team.setColor(ChatFormatting.BLUE);
        }

        scoreboard.addPlayerToTeam(entity.getScoreboardName(), team);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        ListTag list = new ListTag();
        for (UUID uuid : activeGlows) {
            // Guardamos cada UUID como um elemento NBT
            list.add(NbtUtils.createUUID(uuid));
        }
        nbt.put("GlowUUIDs", list);
        nbt.putInt("BarrierRadius", this.radius);
        nbt.putBoolean("SpawnedGlows", this.spawnedGlows);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if (nbt.contains("GlowUUIDs", 9)) { // 9 é o ID para ListTag
            ListTag list = nbt.getList("GlowUUIDs", 11); // 11 é o ID para IntArray (formato do UUID)
            activeGlows.clear();
            for (int i = 0; i < list.size(); i++) {
                activeGlows.add(NbtUtils.loadUUID(list.get(i)));
            }
        }
        if (nbt.contains("BarrierRadius")) {
            this.radius = nbt.getInt("BarrierRadius"); // Carrega o raio
        }
        if (nbt.contains("SpawnedGlows")) {
            this.spawnedGlows = nbt.getBoolean("SpawnedGlows");
        }

    }

    private void verifySelDestruction() {
        if (this.activeGlows.size() <= 3) {
            if (this.level != null && !this.level.isClientSide) {
                System.out.println("Autodestruição: Apenas " + activeGlows.size() + " barreiras restantes.");

                // 1. Limpa as entidades que sobraram (opcional, para não deixar lixo)
                this.clearGlows();

                // 2. Transforma o bloco em AR (destrói o bloco físico)
                this.level.setBlockAndUpdate(this.worldPosition, Blocks.AIR.defaultBlockState());
            }
        }
    }

    public void increaseRadius() {
        if(this.radius >= 50) {
            this.radius = 5;
        } else {
            this.radius += 5;
        }
        this.setChanged();
        if (this.level != null && !this.level.isClientSide) {

            this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    // 1. Envia os dados para o cliente quando o bloco carrega
    @Override
    public @NotNull CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    // 2. Cria o pacote de rede para sincronizar
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    // 3. Opcional: Garante que o raio é lido corretamente ao receber o pacote
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            this.radius = tag.getInt("BarrierRadius");
        }
    }


}
