package com.fronttcapital.behaviorsdemo.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;


abstract class HeaderBehavior<V extends View> extends ViewOffsetBehavior<V> {
    private static final int INVALID_POINTER = -1;
    private Runnable flingRunnable;
    OverScroller scroller;
    private boolean isBeingDragged;
    private int activePointerId = -1;
    private int lastMotionY;
    private int touchSlop = -1;
    private VelocityTracker velocityTracker;

    public HeaderBehavior() {
    }

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent ev) {
        if (this.touchSlop < 0) {
            this.touchSlop = ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
        }

        int action = ev.getAction();
        if (action == 2 && this.isBeingDragged) {
            return true;
        } else {
            int activePointerId;
            int pointerIndex;
            switch (ev.getActionMasked()) {
                case 0:
                    this.isBeingDragged = false;
                    activePointerId = (int) ev.getX();
                    pointerIndex = (int) ev.getY();
                    if (this.canDragView(child) && parent.isPointInChildBounds(child, activePointerId, pointerIndex)) {
                        this.lastMotionY = pointerIndex;
                        this.activePointerId = ev.getPointerId(0);
                        this.ensureVelocityTracker();
                    }
                    break;
                case 1:
                case 3:
                    this.isBeingDragged = false;
                    this.activePointerId = -1;
                    if (this.velocityTracker != null) {
                        this.velocityTracker.recycle();
                        this.velocityTracker = null;
                    }
                    break;
                case 2:
                    activePointerId = this.activePointerId;
                    if (activePointerId != -1) {
                        pointerIndex = ev.findPointerIndex(activePointerId);
                        if (pointerIndex != -1) {
                            int y = (int) ev.getY(pointerIndex);
                            int yDiff = Math.abs(y - this.lastMotionY);
                            if (yDiff > this.touchSlop) {
                                this.isBeingDragged = true;
                                this.lastMotionY = y;
                            }
                        }
                    }
            }

            if (this.velocityTracker != null) {
                this.velocityTracker.addMovement(ev);
            }

            return this.isBeingDragged;
        }
    }

    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent ev) {
        if (this.touchSlop < 0) {
            this.touchSlop = ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
        }

        int activePointerIndex;
        int y;
        switch (ev.getActionMasked()) {
            case 0:
                activePointerIndex = (int) ev.getX();
                y = (int) ev.getY();
                if (!parent.isPointInChildBounds(child, activePointerIndex, y) || !this.canDragView(child)) {
                    return false;
                }

                this.lastMotionY = y;
                this.activePointerId = ev.getPointerId(0);
                this.ensureVelocityTracker();
                break;
            case 1:
                if (this.velocityTracker != null) {
                    this.velocityTracker.addMovement(ev);
                    this.velocityTracker.computeCurrentVelocity(1000);
                    float yvel = this.velocityTracker.getYVelocity(this.activePointerId);
                    this.fling(parent, child, -this.getScrollRangeForDragFling(child), 0, yvel);
                }
            case 3:
                this.isBeingDragged = false;
                this.activePointerId = -1;
                if (this.velocityTracker != null) {
                    this.velocityTracker.recycle();
                    this.velocityTracker = null;
                }
                break;
            case 2:
                activePointerIndex = ev.findPointerIndex(this.activePointerId);
                if (activePointerIndex == -1) {
                    return false;
                }

                y = (int) ev.getY(activePointerIndex);
                int dy = this.lastMotionY - y;
                if (!this.isBeingDragged && Math.abs(dy) > this.touchSlop) {
                    this.isBeingDragged = true;
                    if (dy > 0) {
                        dy -= this.touchSlop;
                    } else {
                        dy += this.touchSlop;
                    }
                }

                if (this.isBeingDragged) {
                    this.lastMotionY = y;
                    this.scroll(parent, child, dy, this.getMaxDragOffset(child), 0);
                }
        }

        if (this.velocityTracker != null) {
            this.velocityTracker.addMovement(ev);
        }

        return true;
    }

    int setHeaderTopBottomOffset(CoordinatorLayout parent, V header, int newOffset) {
        return this.setHeaderTopBottomOffset(parent, header, newOffset, -2147483648, 2147483647);
    }

    int setHeaderTopBottomOffset(CoordinatorLayout parent, V header, int newOffset, int minOffset, int maxOffset) {
        int curOffset = this.getTopAndBottomOffset();
        int consumed = 0;
        if (minOffset != 0 && curOffset >= minOffset && curOffset <= maxOffset) {
            newOffset = MathUtils.clamp(newOffset, minOffset, maxOffset);
            if (curOffset != newOffset) {
                this.setTopAndBottomOffset(newOffset);
                consumed = curOffset - newOffset;
            }
        }

        return consumed;
    }

    int getTopBottomOffsetForScrollingSibling() {
        return this.getTopAndBottomOffset();
    }

    final int scroll(CoordinatorLayout coordinatorLayout, V header, int dy, int minOffset, int maxOffset) {
        return this.setHeaderTopBottomOffset(coordinatorLayout, header, this.getTopBottomOffsetForScrollingSibling() - dy, minOffset, maxOffset);
    }

    final boolean fling(CoordinatorLayout coordinatorLayout, V layout, int minOffset, int maxOffset, float velocityY) {
        if (this.flingRunnable != null) {
            layout.removeCallbacks(this.flingRunnable);
            this.flingRunnable = null;
        }

        if (this.scroller == null) {
            this.scroller = new OverScroller(layout.getContext());
        }

        this.scroller.fling(0, this.getTopAndBottomOffset(), 0, Math.round(velocityY), 0, 0, minOffset, maxOffset);
        if (this.scroller.computeScrollOffset()) {
            this.flingRunnable = new HeaderBehavior.FlingRunnable(coordinatorLayout, layout);
            ViewCompat.postOnAnimation(layout, this.flingRunnable);
            return true;
        } else {
            this.onFlingFinished(coordinatorLayout, layout);
            return false;
        }
    }

    void onFlingFinished(CoordinatorLayout parent, V layout) {
    }

    boolean canDragView(V view) {
        return false;
    }

    int getMaxDragOffset(V view) {
        return -view.getHeight();
    }

    int getScrollRangeForDragFling(V view) {
        return view.getHeight();
    }

    private void ensureVelocityTracker() {
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }

    }

    private class FlingRunnable implements Runnable {
        private final CoordinatorLayout parent;
        private final V layout;

        FlingRunnable(CoordinatorLayout parent, V layout) {
            this.parent = parent;
            this.layout = layout;
        }

        public void run() {
            if (this.layout != null && HeaderBehavior.this.scroller != null) {
                if (HeaderBehavior.this.scroller.computeScrollOffset()) {
                    HeaderBehavior.this.setHeaderTopBottomOffset(this.parent, this.layout, HeaderBehavior.this.scroller.getCurrY());
                    ViewCompat.postOnAnimation(this.layout, this);
                } else {
                    HeaderBehavior.this.onFlingFinished(this.parent, this.layout);
                }
            }

        }
    }
}
