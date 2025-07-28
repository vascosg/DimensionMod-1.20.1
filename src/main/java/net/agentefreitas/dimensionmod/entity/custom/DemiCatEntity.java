package net.agentefreitas.dimensionmod.entity.custom;

import net.agentefreitas.dimensionmod.entity.DemiCatVariant;
import net.agentefreitas.dimensionmod.entity.DiscipleVariant;
import net.agentefreitas.dimensionmod.entity.goal.BaiYuAttackGoal;
import net.agentefreitas.dimensionmod.entity.goal.GoToBedAtNightGoal;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class DemiCatEntity extends PathfinderMob implements Npc, Merchant {

    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(DemiCatEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> TAMED =
            SynchedEntityData.defineId(DemiCatEntity.class, EntityDataSerializers.INT); //0 tamed, <0 tamed

    public DemiCatEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        //DemiCatVariant variant = Util.getRandom(DemiCatVariant.values(), this.random);
        //this.setVariant(variant);
        //Random random = new Random();
        //int nFish = random.nextInt(100) + 1;
        //this.setTamed(nFish);
        this.setPersistenceRequired();
        this.setCustomNameVisible(false);
    }

    private Player tradingPlayer;
    private int xp;

    private static final EntityDataAccessor<Integer> XP =
            SynchedEntityData.defineId(BaiYuEntity.class, EntityDataSerializers.INT);


    private MerchantOffers offers = new MerchantOffers();

    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(BaiYuEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }

        if(this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = 15; // Length in ticks of your animation
            attackAnimationState.start(this.tickCount);
        } else {
            --this.attackAnimationTimeout;
        }

        if(!this.isAttacking()) {
            attackAnimationState.stop();
        }
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
        this.entityData.define(XP, 0);
        this.entityData.define(VARIANT, 0);
        this.entityData.define(TAMED, 0);
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
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(1, new GoToBedAtNightGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(Items.SALMON), false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(Items.COD), false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(Items.COOKED_COD), false));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.2D, Ingredient.of(Items.COOKED_SALMON), false));
        //this.goalSelector.addGoal(1, new TemptGoal(this, 1.2D, Ingredient.of(ModItems.CAT_NEEP), false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        addBehaviourGoals();

    }

    protected void addBehaviourGoals() {

        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        //this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        //this.targetSelector.addGoal(4 , new BaiYuAttackGoal(this,1.0D, true));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 80D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.ATTACK_DAMAGE, 15.0D);
    }

    @Override
    public MerchantOffers getOffers() {
        if (this.offers.isEmpty()) {
            setupOffers();
        }
        return this.offers;
    }

    @Override
    public void overrideOffers(MerchantOffers pOffers) {

    }

    public void setupOffers() {
        if (this.offers == null) {
            this.offers = new MerchantOffers();
        }
        if (this.offers.isEmpty()) {

            ItemStack enchantedBoots = new ItemStack(Items.IRON_BOOTS);
            EnchantmentHelper.enchantItem(RandomSource.create(), enchantedBoots, 20, true);

            Random random = new Random();
            int number = random.nextInt(101);

            if ( this.getTypeVariant() == 0) { //Male1
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 2),
                        new ItemStack(Items.SANDSTONE, 1),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COD, 5),
                        new ItemStack(Items.CACTUS, 15),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 2),
                        new ItemStack(Items.FEATHER, 10),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COD, 25),
                        new ItemStack(Items.BLAZE_ROD, 3),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COOKED_SALMON, 5),
                        new ItemStack(Items.EMERALD, 2),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COOKED_COD, 2),
                        new ItemStack(Items.MAP, 2),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COOKED_COD, 64),
                        new ItemStack(Items.TNT, 1),
                        3, 1, 0.05F
                ));
                if (number > 95) {
                    this.offers.add(new MerchantOffer(
                            new ItemStack(Items.COOKED_SALMON, 64),
                            new ItemStack(ModItems.SUN_SWORD.get(), 1),
                            3, 10, 0.05F
                    ));
                }

            }

            if ( this.getTypeVariant() == 1) { //Male2
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 2),
                        new ItemStack(Items.SANDSTONE, 1),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COD, 5),
                        new ItemStack(Items.CACTUS, 15),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 2),
                        new ItemStack(Items.FEATHER, 10),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COD, 25),
                        new ItemStack(Items.BLAZE_ROD, 3),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COOKED_SALMON, 5),
                        new ItemStack(Items.REDSTONE, 25),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COOKED_COD, 2),
                        new ItemStack(Items.LEATHER, 15),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COOKED_COD, 10),
                        new ItemStack(Items.AMETHYST_SHARD, 15),
                        3, 1, 0.05F
                ));
                if (number > 95) {
                    this.offers.add(new MerchantOffer(
                            new ItemStack(Items.COOKED_SALMON, 64),
                            new ItemStack(ModItems.SUN_SWORD.get(), 1),
                            3, 10, 0.05F
                    ));
                }

            }

            if ( this.getTypeVariant() == 2) { //Female1
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 2),
                        new ItemStack(Items.ACACIA_LOG, 5),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COD, 5),
                        new ItemStack(Items.STRING, 15),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 2),
                        new ItemStack(Items.GLASS_BOTTLE, 10),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COD, 10),
                        new ItemStack(Items.MUD, 64),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COOKED_SALMON, 5),
                        new ItemStack(Items.LAVA_BUCKET, 1),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COOKED_COD, 2),
                        new ItemStack(Items.ARROW, 30),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COOKED_COD, 10),
                        new ItemStack(Items.GHAST_TEAR, 3),
                        3, 1, 0.05F
                ));
                if (number > 95) {
                    this.offers.add(new MerchantOffer(
                            new ItemStack(Items.COOKED_SALMON, 64),
                            new ItemStack(ModItems.MOON_BOW.get(), 1),
                            3, 10, 0.05F
                    ));
                }

            }

            if ( this.getTypeVariant() == 3) { //Female1
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 2),
                        new ItemStack(Items.ACACIA_LOG, 5),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COD, 5),
                        new ItemStack(Items.STRING, 15),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 2),
                        new ItemStack(Items.GLASS_BOTTLE, 10),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COD, 10),
                        new ItemStack(Items.MUD, 64),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COOKED_SALMON, 45),
                        new ItemStack(Items.ENCHANTING_TABLE, 1),
                        3, 1, 0.05F
                ));
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COOKED_COD, 45),
                        enchantedBoots,
                        3, 1, 0.05F
                ));

                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.COOKED_COD, 64),
                        new ItemStack(Items.CLOCK, 1),
                        3, 1, 0.05F
                ));
                if (number > 95) {
                    this.offers.add(new MerchantOffer(
                            new ItemStack(Items.COOKED_SALMON, 64),
                            new ItemStack(ModItems.MOON_BOW.get(), 1),
                            3, 10, 0.05F
                    ));
                }

            }

            if (number <= 9) {
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 5),
                        new ItemStack(Items.ORANGE_TERRACOTTA, 30),
                        3, 10, 0.05F
                ));
            } else if (number <= 18) {
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 5),
                        new ItemStack(Items.LIGHT_BLUE_TERRACOTTA, 30),
                        3, 10, 0.05F
                ));
            } else if (number <= 27) {
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 5),
                        new ItemStack(Items.LIME_TERRACOTTA, 30),
                        3, 10, 0.05F
                ));
            } else if (number <= 36) {
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 5),
                        new ItemStack(Items.MAGENTA_TERRACOTTA, 30),
                        3, 10, 0.05F
                ));
            } else if (number <= 45) {
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 5),
                        new ItemStack(Items.RED_TERRACOTTA, 30),
                        3, 10, 0.05F
                ));
            } else if (number <= 54) {
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 5),
                        new ItemStack(Items.BLUE_TERRACOTTA, 30),
                        3, 10, 0.05F
                ));
            } else if (number <= 63) {
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 5),
                        new ItemStack(Items.YELLOW_TERRACOTTA, 30),
                        3, 10, 0.05F
                ));
            } else if (number <= 72) {
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 5),
                        new ItemStack(Items.GREEN_TERRACOTTA, 30),
                        3, 10, 0.05F
                ));
            } else if (number <= 81) {
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 5),
                        new ItemStack(Items.PINK_TERRACOTTA, 30),
                        3, 10, 0.05F
                ));
            } else if (number <= 90) {
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 5),
                        new ItemStack(Items.PURPLE_TERRACOTTA, 30),
                        3, 10, 0.05F
                ));
            } else if (number <= 99) {
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 5),
                        new ItemStack(Items.BLACK_TERRACOTTA, 30),
                        3, 10, 0.05F
                ));
            } else {
                this.offers.add(new MerchantOffer(
                        new ItemStack(Items.SALMON, 30),
                        new ItemStack(Items.BLACK_TERRACOTTA, 5),
                        3, 10, 0.05F
                ));
            }
        }
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        if (!this.level().isClientSide) {
            if (isTamed()) {
                this.setTradingPlayer(player);
                this.setupOffers(); // garante ofertas antes de abrir o menu

                if (this.offers != null && !this.offers.isEmpty()) {
                    /**
                     player.openMenu(new SimpleMenuProvider(
                     (id, inv, p) -> new MerchantMenu(id, inv, this),
                     this.getDisplayName()
                     ));
                     return InteractionResult.SUCCESS;**/
                    this.openTradingScreen(player, this.getDisplayName(), 1);

                } else {
                    // Se não tem ofertas, pode mostrar mensagem ou cancelar abrir GUI
                    return InteractionResult.PASS;
                }
            } else {
                ItemStack itemInHand = player.getItemInHand(hand);
                if (itemInHand.getItem() == Items.COD || itemInHand.getItem() == Items.SALMON) {

                    if (!player.isCreative()) {
                        itemInHand.shrink(1);
                    }

                    int tamedValue = this.entityData.get(TAMED);
                    this.entityData.set(TAMED, tamedValue - 1);

                    ((ServerLevel) this.level()).sendParticles(
                            ParticleTypes.HEART,
                            this.getX(), this.getY() + 0.5, this.getZ(),
                            5,
                            0.3, 0.3, 0.3,
                            0.02
                    );

                    return InteractionResult.SUCCESS;
                }
            }

        }
        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }

    @Override
    public void setTradingPlayer(@Nullable Player player) {
        this.tradingPlayer = player;
    }

    @Override
    public @Nullable Player getTradingPlayer() {
        return this.tradingPlayer;
    }

    @Override
    public void notifyTrade(MerchantOffer offer) {
        //this.setXP(this.getXP() + offer.getXp());
    }

    @Override
    public void notifyTradeUpdated(ItemStack stack) {}

    @Override
    public int getVillagerXp() {
        return this.getXP();
    }

    @Override
    public void overrideXp(int pXp) {
        this.setXP(pXp);
    }

    @Override
    public boolean showProgressBar() {
        return true;
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return SoundEvents.VILLAGER_YES;
    }

    @Override
    public boolean isClientSide() {
        return this.level().isClientSide;
    }

    // Getter e setter para facilitar
    public int getXP() {
        return this.entityData.get(XP);
    }

    public void setXP(int xp) {
        this.entityData.set(XP, xp);
    }


    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", this.getTypeVariant());
        pCompound.putInt("Tamed", this.getTamed());
        pCompound.putInt("XP", this.getXP());
    }

    // Carregando os dados do NBT
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(VARIANT, pCompound.getInt("Variant"));
        this.entityData.set(TAMED, pCompound.getInt("Tamed"));
        this.setXP(pCompound.getInt("XP"));
    }

    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    public DemiCatVariant getVariant() {
        return DemiCatVariant.byId(this.getTypeVariant() & 255);
    }

    private void setVariant(DemiCatVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    private int getTamed() {
        return this.entityData.get(TAMED);
    }

    private void setTamed(Integer n) {
        this.entityData.set(TAMED, n);
    }

    private boolean isTamed() {
        return this.entityData.get(TAMED) == 0;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {

        this.setVariant(Util.getRandom(DemiCatVariant.values(), this.random));
        Random random = new Random();
        int nFish = random.nextInt(100) + 1;
        this.setTamed((nFish));


        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

}
