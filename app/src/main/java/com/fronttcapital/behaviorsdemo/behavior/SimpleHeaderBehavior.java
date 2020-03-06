package com.fronttcapital.behaviorsdemo.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Jay
 * 本behavior定义的是头部textView预先与recyclerview而滑动的行为规则
 */
public class SimpleHeaderBehavior extends CoordinatorLayout.Behavior {
    private static final String TAG = "SimpleHeaderBehavior";

    public SimpleHeaderBehavior() {
    }

    public SimpleHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);

        /**
         *
         * 头部textView有一个可滑动区间,即2个临界点
         * 1.recyclerview向上滑动,第一个完全可见的条目index==0,textView退出可滑动区间;
         * 2.recyclerview向下滑动,第一个完全可见的条目index==0,textView开始进入可滑动区间;
         */

        RecyclerView recyclerView = (RecyclerView) target;
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        if (firstCompletelyVisibleItemPosition != 0) {
            Log.d(TAG, "onNestedPreScroll  firstCompletelyVisibleItemPosition != 0 return");
            return;
        }

        int childHeight = child.getHeight();
        float targetY = target.getY();

        if (dy > 0) {

            if (targetY <= 0) {
                child.setTranslationY(-childHeight);
                Log.d(TAG, "onNestedPreScroll  target.getY() <= 0  return");
                return;
            }

            /**
             * 1.recyclerview向上滑动,第一个完全可见的条目index==0,textView退出可滑动区间;
             * textView按增量dy移动
             */

            child.setTranslationY(-dy + child.getTranslationY());

        } else {

            if (targetY >= childHeight) {
                child.setTranslationY(0);
                Log.d(TAG, "onNestedPreScroll   targetY >= childHeight return");
                return;
            }

            /**
             * 2.recyclerview向下滑动,第一个完全可见的条目index==0,textView开始进入可滑动区间;
             * textView按增量dy移动
             */
            child.setTranslationY(-dy + child.getTranslationY());

        }

        consumed[1] = dy;

    }
}
