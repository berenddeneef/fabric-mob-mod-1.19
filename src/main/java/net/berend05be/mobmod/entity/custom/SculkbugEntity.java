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
    private boolean upIsPressed, downIsPressed;
    @Override
    public void tick() {
        super.tick();
        if (world.isClient) {
            upIsPressed = MinecraftClient.getInstance().options.jumpKey.isPressed();
            downIsPressed = MinecraftClient.getInstance().options.sprintKey.isPressed();
        }
    }


    public SculkbugEntity(EntityType<? extends FlyingEntity> entityType, World level) {
        super(entityType, level);
    }
    @Override
    public void travel(Vec3d pos) {
            if (this.hasPassengers()) {
                LivingEntity passenger = (LivingEntity)getPrimaryPassenger();
                this.prevYaw = getYaw();
                this.prevPitch = getPitch();

                setYaw(passenger.getYaw());
                setPitch(passenger.getPitch() * 0.5f);
                setRotation(getYaw(), getPitch());

                this.bodyYaw = this.getYaw();
                this.headYaw = this.bodyYaw;
                float x = passenger.sidewaysSpeed * 0.5F;
                float z = passenger.forwardSpeed;
                float y = 0;
                if (forwardSpeed <= 0.0f) {
                    forwardSpeed *= 0.25f;
                }

                if (upIsPressed) {
                    y = 500f;
                }

                if (downIsPressed) {
                    y = -500f;
                }


                if (z <= 0)
                    z *= 0.25f;

                this.setMovementSpeed(0.3f);
                super.travel(new Vec3d(x, y, z));
            }
        }

    // Let the player ride the entity
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.hasPassengers()) {
            player.startRiding(this);

            return super.interactMob(player, hand);
        }

        return super.interactMob(player, hand);
    }

    // Get the controlling passenger
    @Nullable
    public Entity getPrimaryPassenger() {
        return getFirstPassenger();
    }

    @Override
    public boolean isLogicalSideForUpdatingMovement() {
        return true;
    }

    // Adjust the rider's position while riding
    @Override
    public void updatePassengerPosition(Entity entity) {
        super.updatePassengerPosition(entity);

        if (entity instanceof LivingEntity passenger) {
            entity.setPosition(getX(), getY() - -1.4f, getZ());

            this.prevPitch = passenger.prevPitch;
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController(this, "controller",
                1, this::predicate));
    }
    private PlayState predicate(AnimationState animationState) {
        if(animationState.isMoving()) {
            animationState.getController().setAnimation(RawAnimation.begin().then("animation.sculkbug.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        animationState.getController().setAnimation(RawAnimation.begin().then("animation.sculkbug.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6f)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.6f)
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

}
