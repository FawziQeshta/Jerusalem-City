package com.iug.jerusalem_city.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
  private int space;

  public SpacesItemDecoration(int space) {
    this.space = space;
  }

  @Override
  public void getItemOffsets(Rect outRect, View view,
                             RecyclerView parent, RecyclerView.State state) {
    outRect.left = space;
    outRect.right = space;

    // Add top margin only for the first item to avoid double space between items
    if (parent.getChildLayoutPosition(view) == 0) {
        outRect.top = 0;
    } else {
        outRect.top = 0;
    }
  }
}