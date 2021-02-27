package com.ruffian.android.library.common.widget.recyclerview;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * RecyclerView-九宫格瀑布流间隔
 * r
 *
 * @author ZhongDaFeng
 */
public class StaggeredGridSpaceDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;
    private boolean mHasHeader, mHasFooter;


    public StaggeredGridSpaceDecoration(int space, boolean hasHeader, boolean hasFooter) {
        this.mSpace = space;
        this.mHasHeader = hasHeader;
        this.mHasFooter = hasFooter;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildLayoutPosition(view);
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();

        if (isFirstColumn(parent, position, spanCount, childCount))// 第一列情况
        {
            if (ignoreHeaderOrFooter(outRect, position, childCount)) {
                if (isFirstRaw(position, spanCount))// 如果是第一行,左=mSpace,右=mSpace/2,底部=mSpace,上=mSpace
                {
                    outRect.set(mSpace, mSpace, mSpace / 2, mSpace);
                } else//其他,左=mSpace,右=mSpace/2,底部=mSpace
                {
                    outRect.set(mSpace, 0, mSpace / 2, mSpace);
                }
            }
        } else if (isLastColumn(parent, position, spanCount, childCount))// 最后一列情况
        {
            if (ignoreHeaderOrFooter(outRect, position, childCount)) {
                if (isFirstRaw(position, spanCount)) // 如果是第一行,左=mSpace/2,右=mSpace,底部=mSpace,上=mSpace
                {
                    outRect.set(mSpace / 2, mSpace, mSpace, mSpace);
                } else //其他,左=mSpace2/,右=mSpace,底部=mSpace
                {
                    outRect.set(mSpace / 2, 0, mSpace, mSpace);
                }
            }
        } else if (isFirstRaw(position, spanCount)) //除去第一列,最后一列,第一行的情况,左=mSpace/2,右=mSpace/2,底部=mSpace,上=mSpace
        {
            outRect.set(mSpace / 2, mSpace, mSpace / 2, mSpace);
        } else // 其他,左=mSpace/2,右=mSpace/2,底部=mSpace
        {
            if (ignoreHeaderOrFooter(outRect, position, childCount)) {
                outRect.set(mSpace / 2, 0, mSpace / 2, mSpace);
            }
        }

    }

    /**
     * 如果存在header则忽略position=0,存在footer则忽略position=childCount - 1
     *
     * @param outRect
     * @param position
     * @param childCount
     * @return
     */
    public boolean ignoreHeaderOrFooter(Rect outRect, int position, int childCount) {

        boolean flag = true;
        if (position == 0 && mHasHeader) {
            flag = false;
            outRect.set(0, 0, 0, 0);
        } else if (position == childCount - 1 && mHasFooter) {
            flag = false;
            outRect.set(0, 0, 0, 0);
        }

        return flag;
    }


    /**
     * 是否第一列
     *
     * @param parent
     * @param pos
     * @param spanCount
     * @param childCount
     * @return
     */
    private boolean isFirstColumn(RecyclerView parent, int pos, int spanCount,
                                  int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            //如果存在header,position-1
            if (mHasHeader) {
                pos = pos - 1;
            }
            if (pos % spanCount == 0)// 如果是第一列，则不需要绘制右边
            {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            //如果存在header,position-1
            if (mHasHeader) {
                pos = pos - 1;
            }

            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if (pos % spanCount == 0)// 如果是第一列，则不需要绘制右边
                {
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是第一列，则不需要绘制右边
                    return true;
            }
        }

        return false;
    }


    /**
     * 是否最后一列
     *
     * @param parent
     * @param pos
     * @param spanCount
     * @param childCount
     * @return
     */
    private boolean isLastColumn(RecyclerView parent, int pos, int spanCount,
                                 int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            //如果存在header,position-1
            if (mHasHeader) {
                pos = pos - 1;
            }
            if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
            {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            //如果存在header,position-1
            if (mHasHeader) {
                pos = pos - 1;
            }
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0)// 如果是最后一列，则不需要绘制右边
                {
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (pos >= childCount)// 如果是最后一列，则不需要绘制右边
                    return true;
            }
        }
        return false;
    }


    /**
     * 是否第一行
     *
     * @param pos
     * @param spanCount
     * @return
     */
    private boolean isFirstRaw(int pos, int spanCount) {


        if (mHasHeader) {
            pos = pos - 1;
        }

        if (pos < spanCount) {
            return true;
        }

        return false;
    }


    /**
     * 是否最后一行
     *
     * @param parent
     * @param pos
     * @param spanCount
     * @param childCount
     * @return
     */
    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            //childCount = childCount - (childCount % spanCount) + 1;
            //如果存在header,position-1
            if (mHasHeader) {
                pos = pos - 1;
            }
            int target = (((childCount / spanCount) - 1) * spanCount) + (pos % spanCount);


            if (pos >= target)// 如果是最后一行，则不需要绘制底部
                return true;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();

            //如果存在header,position-1
            if (mHasHeader) {
                pos = pos - 1;
            }
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) >= childCount)
                    return true;
            } else
            // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 获取列数
     *
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

}
