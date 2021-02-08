package com.ruffian.android.mvvm.common;

import com.ruffian.android.framework.mvvm.component.MVVMFragment;
import com.ruffian.android.framework.mvvm.presenter.IPresenter;
import com.ruffian.android.framework.mvvm.view.IMVVMView;

/**
 * BaseFragment
 *
 * @author ZhongDaFeng
 */
public class BaseFragment<V extends IMVVMView, P extends IPresenter<V>> extends MVVMFragment<V, P> {

}
