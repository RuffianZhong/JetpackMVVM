package com.ruffian.android.mvvm.customview.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.ruffian.android.mvvm.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * RecyclerView-线性分割线
 *
 * @author ZhongDaFeng
 */
public class LinearDividerDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;

    private int mOrientation;
    /**
     * 最后一行是否显示分割线,默认不显示
     */
    private boolean mShowLastLine;

    private Context context;

    public LinearDividerDecoration(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        this.context = context;
        setOrientation(orientation);
    }

    /**
     * 此构造方法默认使用自定义分割线、垂直方向、最后一行不显示分割线
     *
     * @param context
     */
    public LinearDividerDecoration(Context context) {
        mDivider = context.getResources().getDrawable(R.drawable.ic_launcher_background);
        this.context = context;
        mOrientation = LinearLayoutManager.VERTICAL;
        mShowLastLine = false;
    }


    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    public void setDivider(int res) {
        mDivider = context.getResources().getDrawable(res);
    }

    /**
     * 设置是否显示最后一行分割线
     *
     * @return
     */
    public boolean isShowLastLine() {
        return mShowLastLine;
    }

    public void setShowLastLine(boolean showLastLine) {
        this.mShowLastLine = showLastLine;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        //Log.v("recyclerview - itemdecoration", "onDraw()");

        if (mOrientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }

    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = 0;
        if (mShowLastLine) {
            childCount = parent.getChildCount();
        } else {
            childCount = parent.getChildCount() - 1;
        }
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            // android.support.v7.widget.RecyclerView v = new android.support.v7.widget.RecyclerView(parent.getContext());
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = 0;
        if (mShowLastLine) {
            childCount = parent.getChildCount();
        } else {
            childCount = parent.getChildCount() - 1;
        }
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }

    /**
     * 设置自定义分割线
     * * @param drawable
     */
    public void setDrawable(@NonNull Drawable drawable) {
        if (drawable == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        mDivider = drawable;
    }
}
