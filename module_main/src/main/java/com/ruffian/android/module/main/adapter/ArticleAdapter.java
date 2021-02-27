package com.ruffian.android.module.main.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.ruffian.android.module.main.R;
import com.ruffian.android.module.main.databinding.ItemArticleDataBinding;
import com.ruffian.android.module.main.entity.ArticleBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import androidx.databinding.DataBindingUtil;

public class ArticleAdapter extends BaseQuickAdapter<ArticleBean, BaseDataBindingHolder<ItemArticleDataBinding>> {

    public ArticleAdapter(@Nullable List<ArticleBean> data) {
        super(R.layout.item_article, data);
    }


    /**
     * 当 ViewHolder 创建完毕以后，会执行此回掉
     * 可以在这里做任何你想做的事情
     */
    @Override
    protected void onItemViewHolderCreated(@NotNull BaseDataBindingHolder<ItemArticleDataBinding> viewHolder, int viewType) {
        super.onItemViewHolderCreated(viewHolder, viewType);
        // 绑定 view
        DataBindingUtil.bind(viewHolder.itemView);
    }


    @Override
    protected void convert(@NotNull BaseDataBindingHolder<ItemArticleDataBinding> holder, ArticleBean item) {
        if (item == null) {
            return;
        }

        // 获取 Binding
        ItemArticleDataBinding binding = holder.getDataBinding();
        if (binding != null) {
            // 设置数据
            binding.setArticleBean(item);
            binding.executePendingBindings();
        }
    }
}
