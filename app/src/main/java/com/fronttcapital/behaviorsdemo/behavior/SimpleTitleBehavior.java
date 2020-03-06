package com.fronttcapital.behaviorsdemo.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Jay
 * 1.用于标题view
 * 2.依赖于scrollingView角色(此处指的是RecyclerView)的移动
 * 3.RecyclerView向上滑动并且顶部y值==titleView.getHeight(),此临界点取名叫upReach,过了此临界点向上滑动将无需嵌套滑动;
 * 4.RecyclerView向下滑动并且顶部y值==titleView.getHeight(),此临界点取名叫downReach,达到此临界点之前的向下滑动无需嵌套滑动;
 * RecyclerView的顶部y值在 大于titleView.getHeight() /小于appBarLayout.getHeight()  之间时,titleView需要移动
 */
public class SimpleTitleBehavior extends CoordinatorLayout.Behavior {
    public SimpleTitleBehavior() {
    }

    public SimpleTitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {

        float childHeight = child.getHeight();
        float appBarHeight = parent.getChildAt(0).getHeight();
        float dependencyY = dependency.getY();
        if (dependencyY >= childHeight && dependencyY <= appBarHeight) {

            /**
             * 一元一次方程 y = kx + m;
             * 经过upReach点(-childHeight,appBarHeight)和downReach点(0,childHeight)点
             */
            float value = (childHeight * appBarHeight - childHeight * dependencyY) / (appBarHeight - childHeight);
            child.setTranslationY(value);
        }
        return true;
    }
}
