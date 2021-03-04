package com.ruffian.android.module.knowledge.fragment;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.ruffian.android.library.common.base.BaseFragment;
import com.ruffian.android.library.common.config.RouterConfig;
import com.ruffian.android.module.knowledge.R;

/**
 * ModuleKnowledgeFragment
 *
 * @author ZhongDaFeng
 */
@Route(path = RouterConfig.path_fmt_knowledge)
public class ModuleKnowledgeFragment extends BaseFragment {

    @Override
    protected int layoutId() {
        return R.layout.fmt_module_knowledge;
    }


}