package com.ruffian.android.module.main.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ruffian.android.framework.http.utils.LogUtils;
import com.ruffian.android.library.common.base.BaseFragment;
import com.ruffian.android.module.main.R;
import com.ruffian.android.library.common.widget.recyclerview.LinearSpaceDecoration;
import com.ruffian.android.module.main.adapter.ArticleAdapter;
import com.ruffian.android.module.main.databinding.FmtMainDataBinding;
import com.ruffian.android.module.main.entity.ArticleBean;
import com.ruffian.android.module.main.presenter.ArticlePresenter;
import com.ruffian.android.module.main.view.IArticleView;
import com.ruffian.android.module.main.viewmodel.ArticleViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * MainFragment
 *
 * @author ZhongDaFeng
 */
public class ModuleMainFragment extends BaseFragment<IArticleView, ArticlePresenter, FmtMainDataBinding> implements IArticleView {

    private int mPageIndex = 0;//分页下标
    private ArrayList<ArticleBean> mList = new ArrayList<>();
    private ArticleAdapter articleAdapter;
    private ArticleViewModel articleViewModel;

    @Override
    protected int layoutId() {
        return R.layout.fmt_module_main;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        init();
        initArticleList();
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

    private void initArticleList() {
        if (articleViewModel.getArticleList().getValue() == null) {
            getMVVMPresenter().getArticleList(mPageIndex);
        } else {
            mList.addAll(articleViewModel.getArticleList().getValue());
        }
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        articleViewModel.getArticleList().setValue(mList);
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