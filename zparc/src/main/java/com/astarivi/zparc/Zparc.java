package com.astarivi.zparc;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.appcompat.content.res.AppCompatResources;


/**
 * Zparc class for gradient drawable animations. Use the builder to create an instance of this class.
 *
 * @see         Builder
 */
public class Zparc {
    // Static defaults
    public static final @DrawableRes int ANIM_GREEN_PURPLE = R.drawable.anim_green_purple;
    public static final @DrawableRes int ANIM_BLUE_PURPLE = R.drawable.anim_blue_purple;
    public static final @DrawableRes int ANIM_RED_PURPLE = R.drawable.anim_red_purple;
    // Actual stuff
    private final View view;
    private final int duration;
    private final Drawable drawable;
    private AnimationDrawable animationDrawable;

    private Zparc(Builder builder) {
        view = builder.view;
        duration = builder.duration;
        drawable = builder.drawable;
    }

    public void startAnimation() {
        view.setBackground(drawable);

        final Drawable viewBackground = view.getBackground();

        if (viewBackground == null) return;

        if (viewBackground instanceof AnimationDrawable) {
            animationDrawable = (AnimationDrawable) viewBackground;
        } else {
            throw new IllegalStateException("View's background is not AnimationDrawable");
        }

        animationDrawable.setEnterFadeDuration(duration);
        animationDrawable.setExitFadeDuration(duration);

        view.setBackground(animationDrawable);
        view.post(() -> animationDrawable.start());
    }

    public void stopAnimation() {
        if (animationDrawable == null || !animationDrawable.isRunning()) return;
        animationDrawable.stop();
    }

    /**
     * To build an instance of Zparc, three things are needed, though some can be provided through
     * multiple means. You must provide: <br/>
     * 1. The target view to animate to, set through the {@link Builder#setView(View)} method. <br/>
     * 2. The "duration" (better known as speed) of the animation, set through the
     * {@link Builder#setDuration(int)} method. <br/>
     * 3. The animation itself. It can be provided through any of the following methods:
     * {@link Builder#setAnimList(int)}, {@link Builder#setAnimDrawable(AnimationDrawable)},
     * {@link Builder#setAnimColors(int[])}, {@link Builder#setAnimColors(int, int)}} or
     * {@link Builder#setAnimColors(int[], int)}. Providing it more than once, will replace the last.
     */
    public static class Builder {
        private View view;
        private int duration = -1;
        private Drawable drawable;
        private final Context context;

        /**
         * Start playing the gradient animation.
         */
        public Builder(Context context) {
            this.context = context;
        }

        public Builder setView(View view) {
            this.view = view;
            return this;
        }

        public Builder setDuration(int duration) {
            if (duration < 1) throw new IllegalArgumentException("Negative animation duration not allowed");

            this.duration = duration;
            return this;
        }

        /**
         * Set the animation to use through the ID of an Animation List drawable resource.
         *
         * @param drawableRes the animation list
         * @return the builder instance
         * @see Builder#setAnimDrawable(AnimationDrawable)
         */
        public Builder setAnimList(@DrawableRes int drawableRes) {
            drawable = AppCompatResources.getDrawable(context, drawableRes);
            return this;
        }

        /**
         * Set the animation to use directly by giving an already inflated AnimationDrawable object.
         * Note that this drawable is expected to contain a valid gradient animation.
         *
         * @param animDrawable the inflated AnimationDrawable object
         * @return the builder instance
         * @see Builder#setAnimList(int) 
         */
        public Builder setAnimDrawable(AnimationDrawable animDrawable) {
            drawable = animDrawable;
            return this;
        }

        /**
         * Set the animation to use directly by giving two colors to produce a gradient with.
         * The animations produced with this method aren't as defined, or colorful, as those
         * made with a proper animation list. An animation time of 4 seconds will be used.
         *
         * @param startColor the color to start the gradient with.
         * @param endColor the color to end the gradient with.                
         * @return the builder instance
         */
        public Builder setAnimColors(@ColorInt int startColor, @ColorInt int endColor) {
            GradientDrawable shape1 = new GradientDrawable();
            shape1.setShape(GradientDrawable.RECTANGLE);
            shape1.setCornerRadius(0);
            shape1.setColor(startColor);

            GradientDrawable shape2 = new GradientDrawable();
            shape2.setShape(GradientDrawable.RECTANGLE);
            shape2.setCornerRadius(0);
            shape2.setColor(endColor);

            AnimationDrawable animationDrawable = new AnimationDrawable();
            animationDrawable.addFrame(shape1, 4000);
            animationDrawable.addFrame(shape2, 4000);

            drawable = animationDrawable;

            return this;
        }

        /**
         * Set the animation to use by giving an array of colors. An animation time of 4 seconds
         * will be used.
         *
         * @param colors the color int array
         * @return the builder instance
         */
        public Builder setAnimColors(@ColorInt int[] colors) {
            setAnimColors(colors, 4000);
            return this;
        }

        /**
         * Set the animation to use by giving an array of colors, and the animation time in
         * milliseconds.
         *
         * @param colors the color int array
         * @param animTimeMs the animation time in milliseconds
         * @return the builder instance
         */
        public Builder setAnimColors(@ColorInt int[] colors, int animTimeMs) {
            AnimationDrawable animationDrawable = new AnimationDrawable();

            for (int color : colors) {
                GradientDrawable shape = new GradientDrawable();
                shape.setShape(GradientDrawable.RECTANGLE);
                shape.setCornerRadius(0);
                shape.setColor(color);
                animationDrawable.addFrame(shape, animTimeMs);
            }

            drawable = animationDrawable;
            return this;
        }

        public Zparc build() {
            if (drawable == null) throw new IllegalArgumentException("No drawable has been provided.");
            if (duration == -1) throw new IllegalArgumentException("No anim duration has been set.");
            if (view == null) throw new IllegalArgumentException("No view to draw to set.");

            return new Zparc(this);
        }
    }
}
