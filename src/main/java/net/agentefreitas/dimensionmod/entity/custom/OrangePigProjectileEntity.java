package net.agentefreitas.dimensionmod.entity.custom;

import net.agentefreitas.dimensionmod.entity.ModEntities;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class OrangePigProjectileEntity extends ThrowableItemProjectile {


    // Construtor para o Registro (ModEntities)
    public OrangePigProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    // Construtor para disparar (no Mob)
    public OrangePigProjectileEntity(Level pLevel, LivingEntity pShooter) {
        super(ModEntities.ORANGE_PIG_PROJECTILE.get(), pShooter, pLevel);
    }

    // Define qual item o projétil representa (importante para partículas)
    @Override
    protected Item getDefaultItem() {
        return Items.ORANGE_DYE; // Ou qualquer item do teu mod
    }

    @Override
    public void tick() {
        super.tick();
        // O ThrowableItemProjectile já faz o movimento e colisões no super.tick()!
        // Não precisas de setPos manual aqui.
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity victim = result.getEntity();
        Entity owner = this.getOwner();

        if (victim != owner && !this.level().isClientSide) {
            victim.hurt(this.damageSources().thrown(this, owner), 4.0f);
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
