package net.agentefreitas.dimensionmod.entity.custom;

import net.agentefreitas.dimensionmod.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class FireHorseEntity extends TamableAnimal {
    public FireHorseEntity(EntityType<? extends TamableAnimal> p_21683_, Level p_21684_) {

        super(p_21683_, p_21684_);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 0.0F);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return true;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        // Se já for domesticado
        if (this.isTame()) {
            if (this.isVehicle()) {
                return super.mobInteract(player, hand);
            }
            // Se o player clicar com Shift ou se não tiver nada na mão, ele monta
            if (!this.level().isClientSide) {
                player.startRiding(this);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        // Lógica para domesticar (ex: usando Maçã Dourada)
        else if (itemstack.is(ModItems.FIRE_CORE.get())) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            if (!this.level().isClientSide) {
                if (this.random.nextInt(15) == 0) { // 1/15 de chance de sucesso
                    this.tame(player);
                    this.level().broadcastEntityEvent(this, (byte)7); // Partículas de coração
                } else {
                    this.level().broadcastEntityEvent(this, (byte)6); // Partículas de fumo (falha)
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if(this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    public boolean canStandOnFluid(FluidState fluidState) {
        // Diz ao motor de física que o cavalo pode "pisar" em lava
        return fluidState.is(FluidTags.LAVA);
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isAlive()) {
            // Verifica se há um passageiro e se esse passageiro é um Player
            if (this.isVehicle() && this.getControllingPassenger() instanceof Player player && this.isTame()) {

                // 1. Sincroniza a rotação do cavalo com a do player
                this.setYRot(player.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(player.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;

                // 2. Captura os inputs do player (WASD)
                float strafe = player.xxa * 0.5F; // Movimento lateral
                float vertical = player.zza;      // Movimento para frente/trás

                if (vertical <= 0.0F) {
                    vertical *= 0.25F; // Abranda a marcha atrás
                }

                // 3. Configura o salto (opcional)
                // Se quiseres que ele pule como um cavalo normal, podes usar a lógica de jumpStrength

                // 4. Executa o movimento
                this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                super.travel(new Vec3(strafe, travelVector.y, vertical));

            } else {
                // Se não houver player montado, ele comporta-se como um mob normal
                super.travel(travelVector);
            }
        }
    }

    @Override
    public LivingEntity getControllingPassenger() {
        return (LivingEntity) this.getFirstPassenger();
    }


    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(ModItems.FIRE_CORE.get()), false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        addBehaviourGoals();

    }

    protected void addBehaviourGoals() {

        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }
}
