package net.berend05be.mobmod.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class SculkbugEntity extends FlyingEntity implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean upIsPressed, downIsPressed, forward, backward, left, right;
    protected Item sourceItem;


    public SculkbugEntity(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (getWorld().isClient()) {
            upIsPressed = MinecraftClient.getInstance().options.jumpKey.isPressed();
            downIsPressed = MinecraftClient.getInstance().options.sprintKey.isPressed();
            forward = MinecraftClient.getInstance().options.forwardKey.isPressed();
            backward = MinecraftClient.getInstance().options.backKey.isPressed();
            left = MinecraftClient.getInstance().options.leftKey.isPressed();
            right = MinecraftClient.getInstance().options.rightKey.isPressed();
        }
    }
    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.getWorld().isClient() || !this.isAlive()) {
            return;
        }
    }
    @Override
    public void move(MovementType movementType, Vec3d movement) {
        super.move(movementType, movement);
    }


    public SculkbugEntity(EntityType<? extends FlyingEntity > entityType, World level, Item sourceItem) {
        super(entityType, level);
        setNoGravity(true);
        this.sourceItem = sourceItem;
    }
    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        return getFirstPassenger() instanceof LivingEntity entity ? entity : null;
    }

    @Override
    public void travel(Vec3d pos) {
        if (this.isAlive()) {
            if (this.hasPassengers()) {
                LivingEntity passenger = (LivingEntity)getControllingPassenger();
                this.prevYaw = getYaw();
                this.prevPitch = getPitch();

                setYaw(passenger.getYaw());
                setPitch(passenger.getPitch() * 0.5f);
                setRotation(getYaw(), getPitch());

                this.bodyYaw = this.getYaw();
                this.headYaw = this.bodyYaw;
                float x = 0;
                float z = 0;
                float y = 0;
                if (upIsPressed)
                   y = 1;
                else y=0;
                if (downIsPressed)
                    y=-1;
                if (forward)
                    z = 1;
                else z=0;
                if (backward)
                    z=-1;
                if (left)
                    x = 1;
                else x=0;
                if (right)
                    x=-1;
                super.travel(new Vec3d(x, y, z));
                Vec3d vec3d2 = this.getVelocity();
                this.setVelocity(vec3d2.x,vec3d2.y, vec3d2.z);
            }
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.hasPassengers()) {
            player.startRiding(this);

            return super.interactMob(player, hand);
        }

        return super.interactMob(player, hand);
    }


    @Override
    public boolean isLogicalSideForUpdatingMovement() {
        return true;
    }




    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller",
                1, this::predicate));
    }
    private PlayState predicate(AnimationState animationState) {
        if(animationState.isMoving()&(isOnGround())) {
            animationState.getController().setAnimation(RawAnimation.begin().then("animation.sculkbug.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if(animationState.isMoving()) {
            animationState.getController().setAnimation(RawAnimation.begin().then("animation.sculkbug.flying", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        animationState.getController().setAnimation(RawAnimation.begin().then("animation.sculkbug.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1f)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 1f)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 2f);
    }

    protected void initGoals() {

        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WARDEN_LISTENING;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WARDEN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15f, 1.0f);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 1.6F;
    }
    public boolean isPushable() {
        return !this.hasPassengers();
    }
    public double getMountedHeightOffset() {
        return (double)1.75;
    }
}
