package com.fronttcapital.behaviorsdemo.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * Jay
 * recyclerview只需要跟随textView移动即可
 */
public class SlideDownShowTitleRVBehavior extends CoordinatorLayout.Behavior {
    public SlideDownShowTitleRVBehavior() {
    }

    public SlideDownShowTitleRVBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof TextView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {

        float dependencyY = dependency.getY();
        if (dependencyY > 0) {
            dependency.setY(0);
            return true;
        }

        float y = dependency.getHeight() + dependencyY;
        if (y < 0) {
            y = 0;
        }
        child.setY(y);

        return true;
    }
}
