package net.agentefreitas.dimensionmod.entity.custom;

import net.agentefreitas.dimensionmod.entity.goal.BaiYuAttackGoal;
import net.agentefreitas.dimensionmod.entity.goal.DiscipleAttackGoal;
import net.agentefreitas.dimensionmod.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class BaiYuEntity extends PathfinderMob implements Npc, Merchant {

    public BaiYuEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        this.setPersistenceRequired();
        this.setCustomName(Component.literal("Bai Yu").withStyle(style -> style.withColor(0x82b776)));
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

        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(Items.POPPY), false)); //TODO chose better item
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        addBehaviourGoals();

    }

    protected void addBehaviourGoals() {

        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        //this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(4 , new BaiYuAttackGoal(this,1.0D, true));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 100D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.ATTACK_DAMAGE, 25.0D);
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
            //Saplings
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 5),
                    new ItemStack(Items.OAK_SAPLING, 1),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 5),
                    new ItemStack(Items.SPRUCE_SAPLING, 1),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 5),
                    new ItemStack(Items.ACACIA_SAPLING, 1),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 5),
                    new ItemStack(Items.BIRCH_SAPLING, 1),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 5),
                    new ItemStack(Items.CHERRY_SAPLING, 1),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 5),
                    new ItemStack(Items.DARK_OAK_SAPLING, 1),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 5),
                    new ItemStack(Items.JUNGLE_SAPLING, 1),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 5),
                    new ItemStack(Items.MANGROVE_PROPAGULE, 1),
                    10, 1, 0.05F
            ));

            //LOGS
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 25),
                    new ItemStack(Items.OAK_LOG, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 25),
                    new ItemStack(Items.SPRUCE_LOG, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 25),
                    new ItemStack(Items.ACACIA_LOG, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 25),
                    new ItemStack(Items.BIRCH_LOG, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 25),
                    new ItemStack(Items.CHERRY_LOG, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 25),
                    new ItemStack(Items.DARK_OAK_LOG, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 25),
                    new ItemStack(Items.JUNGLE_LOG, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 25),
                    new ItemStack(Items.MANGROVE_LOG, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 25),
                    new ItemStack(Items.BAMBOO_BLOCK, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 25),
                    new ItemStack(Items.CRIMSON_STEM, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 25),
                    new ItemStack(Items.WARPED_STEM, 64),
                    10, 1, 0.05F
            ));

            //STONES
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 15),
                    new ItemStack(Items.STONE, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 15),
                    new ItemStack(Items.COBBLESTONE, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 15),
                    new ItemStack(Items.DEEPSLATE, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 15),
                    new ItemStack(Items.COBBLED_DEEPSLATE, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 15),
                    new ItemStack(Items.GRANITE, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 15),
                    new ItemStack(Items.DIORITE, 64),
                    10, 1, 0.05F
            ));

            //SOIL
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 10),
                    new ItemStack(Items.DIRT, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 10),
                    new ItemStack(Items.SAND, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 10),
                    new ItemStack(Items.RED_SAND, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 10),
                    new ItemStack(Items.GRAVEL, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 10),
                    new ItemStack(Items.PACKED_MUD, 64),
                    10, 1, 0.05F
            ));

            //FINDS
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.PRISMARINE, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.NETHER_BRICKS, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.BASALT, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.RED_NETHER_BRICKS, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.BLACKSTONE, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.END_STONE, 64),
                    10, 1, 0.05F
            ));

            //TERRACOTTA
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.TERRACOTTA, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.WHITE_TERRACOTTA, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.LIGHT_GRAY_TERRACOTTA, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.GRAY_TERRACOTTA, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.BLACK_TERRACOTTA, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.BROWN_TERRACOTTA, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.RED_TERRACOTTA, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.ORANGE_TERRACOTTA, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.YELLOW_TERRACOTTA, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.LIME_TERRACOTTA, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.GREEN_TERRACOTTA, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.CYAN_TERRACOTTA, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.LIGHT_BLUE_TERRACOTTA, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.BLUE_TERRACOTTA, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.PURPLE_TERRACOTTA, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.MAGENTA_TERRACOTTA, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 40),
                    new ItemStack(Items.PINK_TERRACOTTA, 64),
                    10, 1, 0.05F
            ));

            //HONORABLE MENTIONS
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 64),
                    new ItemStack(Items.QUARTZ_BLOCK, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 64),
                    new ItemStack(Items.SOUL_SOIL, 64),
                    10, 1, 0.05F
            ));
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 64),
                    new ItemStack(Items.CALCITE, 64),
                    10, 1, 0.05F
            ));


        }

        boolean offerExists = false; //TODO so ugly
        for (MerchantOffer offer : this.offers) {
            ItemStack cost = offer.getCostA();
            ItemStack result = offer.getResult();
            if (cost.getItem() == Items.POPPY && result.getItem() == ModItems.GREEN_BOOK.get()) {
                offerExists = true;
                break;
            }
        }

        if (this.getXP() > 200 && !offerExists) {
            this.offers.add(new MerchantOffer(
                    new ItemStack(Items.POPPY, 1),
                    new ItemStack(ModItems.GREEN_BOOK.get(), 1),
                    10, 5, 0.05F
            ));
        }
    }

    @Override
    public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
        if (!this.level().isClientSide) {
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
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("XP", this.getXP());
    }

    // Carregando os dados do NBT
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setXP(compound.getInt("XP"));
    }

}
