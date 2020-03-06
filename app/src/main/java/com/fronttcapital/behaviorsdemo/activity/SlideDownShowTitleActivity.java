package com.fronttcapital.behaviorsdemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fronttcapital.behaviorsdemo.Divider15ItemDecoration;
import com.fronttcapital.behaviorsdemo.R;

/**
 * 本界面CoordinatorLayout内子view只有textView和recyclerview
 * 其中recyclerview作为scrollingView角色,负责事件分发,其behavior是我们自定义的,用于跟随textView的移动
 * textView的behavior是我们自定义的,根据我们自定义行为规则先于recyclerview滑动
 */
public class SlideDownShowTitleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_down_show_title);

        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        final int DATA_SIZE = 30;
        final int[] adapterData = new int[DATA_SIZE];
        for (int i = 0; i < DATA_SIZE; i++) {
            adapterData[i] = i;
        }
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                TextView textView = new TextView(parent.getContext());
                textView.setBackgroundColor(Color.GRAY);
                textView.setLayoutParams(new ViewGroup.LayoutParams(-1, 150));
                textView.setGravity(Gravity.CENTER);
                return new RecyclerView.ViewHolder(textView) {
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                ((TextView) holder.itemView).setText("I love jay ! item " + adapterData[position]);
            }

            @Override
            public int getItemCount() {
                return adapterData.length;
            }
        });
        recyclerView.addItemDecoration(new Divider15ItemDecoration());
    }
}
