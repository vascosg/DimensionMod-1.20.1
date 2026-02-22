package net.agentefreitas.dimensionmod.entity.custom;

import net.agentefreitas.dimensionmod.entity.ModEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class KunziteProjectileEntity extends Projectile {

    // Construtor para o Registro (ModEntities)
    public KunziteProjectileEntity(EntityType<? extends KunziteProjectileEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = false;
        this.setNoGravity(true);
    }

    // Construtor para o Mob usar (O 'super' do Projectile não aceita o shooter direto no construtor)
    public KunziteProjectileEntity(Level level, LivingEntity shooter) {
        this(ModEntities.KUNZITE_PROJECTILE.get(), level);
        this.setOwner(shooter); // Define quem atirou
        // Define a posição inicial no corpo do mob
        this.setPos(shooter.getX(), shooter.getEyeY() - 0.1D, shooter.getZ());
    }

    @Override
    public void tick() {
        // O super.tick() lida com a lógica base de projéteis
        super.tick();

        // Detetar colisão
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS) {
            this.onHit(hitresult);
        }

        // O Projectile base não aplica movimento sozinho de forma eficiente,
        // mas vamos garantir que ele não use gravidade e use o vetor correto.
        Vec3 vec3 = this.getDeltaMovement();
        this.setPos(this.getX() + vec3.x, this.getY() + vec3.y, this.getZ() + vec3.z);

        // IMPORTANTE: Atualiza a rotação para o modelo não ficar parado
        this.updateRotation();

        // Partículas
        if (this.level().isClientSide && this.random.nextFloat() < 0.7F) {
            this.level().addParticle(ParticleTypes.PORTAL, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
    }

    protected boolean canHitEntity(Entity entity) {
        // 1. Não acerta a si mesmo
        if (entity == this) return false;

        // 2. Não acerta o dono (owner)
        if (entity == this.getOwner()) return false;

        // 3. Deixa o super decidir o resto (se o alvo é invulnerável, etc.)
        return super.canHitEntity(entity);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!this.level().isClientSide) {
            Entity target = result.getEntity();
            Entity owner = this.getOwner();


            // Evita que o projétil acerte o próprio mob que o atirou
            if (target != owner) {
                target.hurt(this.damageSources().mobProjectile(this, (LivingEntity) owner), 7.0F);
                this.discard(); // Remove o projétil após o acerto
            }

            if (target instanceof LivingEntity livingTarget) {
                // Parâmetros: (Efeito, Duração em ticks, Nível do efeito)
                // 100 ticks = 5 segundos
                // Nível 1 (index 0) = Slowness I | Nível 2 (index 1) = Slowness II
                livingTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1), owner);
            }
        }
        super.onHitEntity(result);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        // Se bater num bloco e você quiser que suma:
        if (result.getType() == HitResult.Type.BLOCK && !this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
