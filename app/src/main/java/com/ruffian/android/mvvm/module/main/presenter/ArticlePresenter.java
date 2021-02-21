package com.ruffian.android.mvvm.module.main.presenter;

import android.util.Log;

import com.ruffian.android.framework.http.utils.LogUtils;
import com.ruffian.android.framework.mvvm.model.IModelCallback;
import com.ruffian.android.framework.mvvm.model.ModelFactory;
import com.ruffian.android.framework.mvvm.presenter.MVVMPresenter;
import com.ruffian.android.mvvm.module.main.entity.ArticleListBean;
import com.ruffian.android.mvvm.module.main.model.ArticleModel;
import com.ruffian.android.mvvm.module.main.view.IArticleView;

/**
 * ArticlePresenter
 */
public class ArticlePresenter extends MVVMPresenter<IArticleView> {


    public void getArticleList(final int pageIndex) {
        ModelFactory.getModel(ArticleModel.class).getArticleList(pageIndex, getView().getLifecycleOwner(), new IModelCallback.Http<ArticleListBean>() {

            @Override
            public void onSuccess(ArticleListBean object) {
                if (isAttached())
                    getView().onArticleListSuccess(pageIndex, object.getList());
            }

            @Override
            public void onError(int code, String msg) {
                if (isAttached())
                    getView().onArticleListError(pageIndex, code, msg);
            }

            @Override
            public void onCancel() {

            }
        });
    }

}
