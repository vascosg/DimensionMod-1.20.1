package net.agentefreitas.dimensionmod.entity.animations;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class CitrineSwordAnimationDefenitions {

    public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(2.5f).looping()
            .addAnimation("citrine_sword",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -22.5f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, -22.5f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
    public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(0.5f).looping()
            .addAnimation("citrine_sword",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
    public static final AnimationDefinition ATTACK = AnimationDefinition.Builder.withLength(2.25f)
            .addAnimation("citrine_sword",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.posVec(30f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5f, KeyframeAnimations.posVec(15f, 0f, -37f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5834333f, KeyframeAnimations.posVec(34f, 0f, -17f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.625f, KeyframeAnimations.posVec(34f, 0f, 7f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.6766667f, KeyframeAnimations.posVec(20f, 0f, 26f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.7083433f, KeyframeAnimations.posVec(0f, 0f, 36f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.75f, KeyframeAnimations.posVec(-19f, 0f, 32f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.7916767f, KeyframeAnimations.posVec(-33f, 0f, 16f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.8343333f, KeyframeAnimations.posVec(-36f, 0f, -4f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.875f, KeyframeAnimations.posVec(-28f, 0f, -22f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.9167667f, KeyframeAnimations.posVec(-13f, 0f, -33f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.9583433f, KeyframeAnimations.posVec(7f, 0f, -35f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2f, KeyframeAnimations.posVec(25f, 0f, -25f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.25f, KeyframeAnimations.posVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.CATMULLROM)))
            .addAnimation("citrine_sword",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, -22.5f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, -80f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(1.5f, KeyframeAnimations.degreeVec(-75f, 0f, -80f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2f, KeyframeAnimations.degreeVec(315f, 0f, -80f),
                                    AnimationChannel.Interpolations.CATMULLROM),
                            new Keyframe(2.25f, KeyframeAnimations.degreeVec(360f, 0f, -22.5f),
                                    AnimationChannel.Interpolations.CATMULLROM))).build();
}
