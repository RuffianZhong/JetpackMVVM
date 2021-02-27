package com.ruffian.android.library.common.widget.recyclerview;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView-线性间距
 *
 * @author ZhongDaFeng
 */
public class LinearSpaceDecoration extends RecyclerView.ItemDecoration {


    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    private int mSpace, mOrientation;
    private boolean mHasHeader, mHasFooter;

    public LinearSpaceDecoration(int space) {
        this(VERTICAL, space);
    }

    public LinearSpaceDecoration(int orientation, int space) {
        this(orientation, space, false, false);
    }

    public LinearSpaceDecoration(int orientation, int space, boolean hasHeader, boolean hasFooter) {
        this.mSpace = space;
        this.mOrientation = orientation;
        this.mHasHeader = hasHeader;
        this.mHasFooter = hasFooter;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildLayoutPosition(view);
        int childCount = parent.getAdapter().getItemCount();

        if (mOrientation == HORIZONTAL) {

            /**
             * 第一个左边目标值,其他右边目标值
             */
            if (position == 0) {
                outRect.left = mSpace;
            }
            outRect.right = mSpace;

        } else if (mOrientation == VERTICAL) {

            /**
             * 存在header,第一个不绘制间距,如果存在footer最后一个不绘制间距
             */
            if (position == 0 && mHasHeader) {
                outRect.bottom = 0;
            } else if (position == childCount - 1 && mHasFooter) {
                outRect.bottom = 0;
            } else {
                outRect.bottom = mSpace;
            }

        }
    }

}
