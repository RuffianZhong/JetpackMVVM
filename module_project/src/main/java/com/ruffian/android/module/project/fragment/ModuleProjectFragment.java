package com.ruffian.android.module.project.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ruffian.android.library.common.base.BaseFragment;
import com.ruffian.android.library.common.config.RouterConfig;
import com.ruffian.android.module.project.R;

/**
 * ModuleProjectFragment
 *
 * @author ZhongDaFeng
 */
@Route(path = RouterConfig.path_fmt_project)
public class ModuleProjectFragment extends BaseFragment {

    @Override
    protected int layoutId() {
        return R.layout.fmt_module_project;
    }

}