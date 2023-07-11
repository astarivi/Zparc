package com.astarivi.zparc;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.appcompat.content.res.AppCompatResources;


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

    public static class Builder {
        private View view;
        private int duration = -1;
        private Drawable drawable;
        private final Context context;

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

        public Builder setAnimList(@DrawableRes int drawableRes) {
            drawable = AppCompatResources.getDrawable(context, drawableRes);
            return this;
        }

        public Builder setAnimDrawable(AnimationDrawable animDrawable) {
            drawable = animDrawable;
            return this;
        }

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

        public Builder setAnimColors(@ColorInt int[] colors) {
            setAnimColors(colors, 4000);
            return this;
        }

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
            if (view == null) throw new IllegalArgumentException("No view set.");

            return new Zparc(this);
        }
    }
}
