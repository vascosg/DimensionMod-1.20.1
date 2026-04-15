package net.agentefreitas.dimensionmod.entity.custom;


import net.agentefreitas.dimensionmod.entity.ModEntities;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class FireEggProjectileEntity extends ThrowableItemProjectile {
    public FireEggProjectileEntity(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    public FireEggProjectileEntity(Level level, LivingEntity shooter) {
        super(ModEntities.FIRE_EGG.get(), shooter, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.FIRE_EGG.get(); // O item que o projétil "parece"
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity target = result.getEntity();
        // Dá dano e coloca fogo
        target.hurt(this.damageSources().thrown(this, this.getOwner()), 5.0F);
        target.setSecondsOnFire(5);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            // Cria uma pequena explosão ou partículas ao bater no chão
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard(); // Remove o projétil
        }
    }
}