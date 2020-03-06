package com.fronttcapital.behaviorsdemo;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class Divider15ItemDecoration extends RecyclerView.ItemDecoration {
    private static final int RECYCLERVIEW_ITEM_SPACE_15 = 15;

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = RECYCLERVIEW_ITEM_SPACE_15;
        outRect.right = RECYCLERVIEW_ITEM_SPACE_15;
        outRect.bottom = RECYCLERVIEW_ITEM_SPACE_15;
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = RECYCLERVIEW_ITEM_SPACE_15;
        }
    }
}
