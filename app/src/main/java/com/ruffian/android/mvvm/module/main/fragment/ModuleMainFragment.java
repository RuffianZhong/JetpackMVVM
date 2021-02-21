package com.ruffian.android.mvvm.module.main.fragment;

import android.os.Bundle;
import android.view.View;

import com.ruffian.android.framework.http.utils.LogUtils;
import com.ruffian.android.mvvm.R;
import com.ruffian.android.mvvm.common.BaseFragment;
import com.ruffian.android.mvvm.customview.recyclerview.LinearSpaceDecoration;
import com.ruffian.android.mvvm.databinding.FmtMainDataBinding;
import com.ruffian.android.mvvm.module.main.adapter.ArticleAdapter;
import com.ruffian.android.mvvm.module.main.entity.ArticleBean;
import com.ruffian.android.mvvm.module.main.presenter.ArticlePresenter;
import com.ruffian.android.mvvm.module.main.view.IArticleView;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * MainFragment
 *
 * @author ZhongDaFeng
 */
public class ModuleMainFragment extends BaseFragment<IArticleView, ArticlePresenter> implements IArticleView {


    private int mPageIndex = 0;//分页下标
    private ArrayList<ArticleBean> mList = new ArrayList<>();
    private ArticleAdapter articleAdapter;


    @Override
    protected int layoutId() {
        return R.layout.fmt_module_main;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        getMVVMPresenter().getArticleList(mPageIndex);
    }

    private void init() {
        articleAdapter = new ArticleAdapter(mList);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        getViewDataBinding().recyclerView.setLayoutManager(manager);
        LinearSpaceDecoration decoration = new LinearSpaceDecoration(10);
        getViewDataBinding().recyclerView.addItemDecoration(decoration);
        getViewDataBinding().recyclerView.setAdapter(articleAdapter);


        getViewDataBinding().refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPageIndex = 0;//下拉刷新从第一个开始
                getMVVMPresenter().getArticleList(mPageIndex);
            }
        });
        getViewDataBinding().refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mPageIndex++;
                getMVVMPresenter().getArticleList(mPageIndex);
            }
        });
    }


    @Override
    public FmtMainDataBinding getViewDataBinding() {
        return (FmtMainDataBinding) super.getViewDataBinding();
    }

    @Override
    public void onArticleListSuccess(int pageIndex, ArrayList<ArticleBean> list) {
        if (pageIndex == 0) {
            getViewDataBinding().refreshLayout.finishRefresh();
            mList.clear();
        } else {
            getViewDataBinding().refreshLayout.finishLoadMore();
        }

        mList.addAll(list);
        articleAdapter.notifyDataSetChanged();
    }

    @Override
    public void onArticleListError(int pageIndex, int code, String msg) {
        if (pageIndex == 0)
            getViewDataBinding().refreshLayout.finishRefresh();
        else
            getViewDataBinding().refreshLayout.finishLoadMore();

        showToast(msg);
    }

    @Override
    public void showToast(@NonNull String msg) {
        LogUtils.e("=====" + msg);
    }

    @Override
    public ArticlePresenter makePresenter() {
        return new ArticlePresenter();
    }
}