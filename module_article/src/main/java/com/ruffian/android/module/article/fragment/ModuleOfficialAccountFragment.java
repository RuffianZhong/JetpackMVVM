package com.ruffian.android.module.article.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ruffian.android.library.common.base.BaseFragment;
import com.ruffian.android.library.common.config.RouterConfig;
import com.ruffian.android.module.article.R;

/**
 * ModuleOfficialAccountFragment
 *
 * @author ZhongDaFeng
 */
@Route(path = RouterConfig.path_fmt_official_account)
public class ModuleOfficialAccountFragment extends BaseFragment {

    @Override
    protected int layoutId() {
        return R.layout.fmt_module_official_account;
    }

}