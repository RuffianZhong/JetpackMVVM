### 1.MVVM

在 Android 项目中理解

- MVC 是一种项目架构模式，分为 Model，View（xml），Controller（Activity/Fragment）。在 Android 中使用时突出缺点表现为 Controller 太臃肿，承担角色过大（既是Controller又是View）
- MVP 基于 MVC 模式，将 Activity/Fragment 视为 View 层范畴，引入 Presenter 作为 View 和 Model 的桥梁，解耦 View 和 Model
- MVVM 更确切的说是 MVPVM，它的出现并不是为了解决 MVP 的某个痛点，而是在 MVP 模式下添加 ViewModel (绑定View的数据模型)，将现在'前端'流行的数据绑定的思想融合进来

### 1.1 Presenter
Presenter 作为桥梁连接 View 和 Model，解耦模块。 主要职责绑定View/解绑View，同时内部持有 Model 从而链接 V / M

- 作者认为 P 层不适合只绑定单个 Model 因此在 P 层中并没有对 Model 进行绑定，而是在具体 Presenter 中创建所需要的 Model

`IPresenter.java`

```
public interface IPresenter<V extends IView> {

    /**
     * 将 View 添加到当前 Presenter
     */
    @UiThread
    void attachView(@NonNull V view);

    /**
     * 将 View 从 Presenter 移除
     */
    @UiThread
    void detachView();

}
```

`MVVMPresenter<V extends IView>`

```
//Presenter
public class MVVMPresenter<V extends IView> implements IPresenter<V> {

    //View(传递时对象为Activity/Fragment必须要销毁)
    protected V mView;

    @Override
    public void attachView(@NonNull V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    /**
     * 获取View
     * 备注： getView() 在组件销毁后可能为 null 使用前通过 isAttached() 判断
     *
     * @return
     */
    public V getView() {
        return mView;
    }

    /**
     * View是否已经绑定
     *
     * @return
     */
    public boolean isAttached() {
        return mView != null;
    }
}
```

`具体业务Presenter`

```
//具体业务Presenter
public class LoginPresenter extends MVVMPresenter<ILoginView> {

    /**
     * 登录
     */
    public void login(String account, String password) {
        ModelFactory.getModel(AccountModel.class).login(account, password, getView().getLifecycleOwner(), new IModelCallback.Http<UserBean>() {
            @Override
            public void onSuccess(UserBean object) {
                if (!isAttached()) return;
                getView().loginSuccess(object);
            }

            @Override
            public void onError(int code, String msg) {
                if (!isAttached()) return;
                if (isAttached()) getView().onError(code, msg);
            }

            @Override
            public void onCancel() {
               //TODO
            }
        });
    }
}
```

上述代码可以看出
- 1.Presenter 中创建了 Model，而 View 则是通过 MVVMPresenter<ILoginView> 中泛型传入绑定
- 2.在 Presenter 中调用 Model 的方法获取网络/本地数据，处理完成之后 调用 View 接口更新UI
- 通过 1,2 两步，Presenter 起到了桥梁作用，解耦 V / M ，同时充当了 控制器 处理业务逻辑

ILoginView 是什么？

### 1.2 View
定义一系列界面相关的View接口，然后在 Activity/Fragment 中实现（这里将 Activity/Fragment 角色定为 View，只处理UI展示），该 View 接口与 Presenter 相绑定，在 Presenter 中处理 控制器 相关的逻辑，最终在需要操作UI时调用对应 View 的函数定义

- Activity/Fragment 被定位成 View，实现View接口，而 Presenter 则充当了之前的 控制器 角色，处理各种逻辑，然后调用 View 接口定义的函数 （此时只是调用接口定义，具体的实现在 Activity/Fragment ）

`IView.java`

```
public interface IView {

}
```

`IMVVMView.java`

```
    //基础View接口，此处添加了两个常用的函数定义
    public interface IMVVMView extends IView {
        /**
         * LifecycleOwner
         */
        LifecycleOwner getLifecycleOwner();
    
        /**
         * Activity
         */
        Activity getActivity();
    }
```

`具体业务View接口定义，按需实现`

```
 //具体业务View接口定义，按需实现
    public interface ILoginView extends IMVVMView {
        //登录成功
        public void loginSuccess(UserBean userBean);
        //登录失败
        public void onError(int code, String desc);
    }
```

`Activity/Fragment 中实现View接口`

```
    //Activity/Fragment 中实现View接口
    public class LoginActivity extends BaseActivity implements ILoginView {

        @Override
        public void onError(int code, String desc) {
            //update ui
        }

        @Override
        public void loginSuccess(UserBean userBean) {
            //update ui
        }
    }
```

> 上述代码可以看出，View 只负责处理 UI ，何时处理？数据怎么来？都交由 Presenter 完成。职责很单一

### 1.3 Model
数据模型的构建或者获取，提供数据实体。此处并没有太多的限制，只要能构建出数据模型就可以

> 此处作者为了项目的可维护性，定义了一个数据模型回调接口，目的是不管后续 Model 具体实现怎么变化，对接 P 层实现方式不变

`IModelCallback`

```
public interface IModelCallback {

    /**
     * 网络数据回调，泛指http
     *
     * @param <T>
     */
    public interface Http<T> {

        public void onSuccess(T object);

        public void onError(int code, String msg);

        public void onCancel();
    }

    /**
     * 其他数据回调<本地数据，数据库等>
     *
     * @param <T>
     */
    public interface Data<T> {

        public void onSuccess(T object);
    }
}
```

`具体业务Model(实现方式没有规定，合理构造数据即可)`

```
//具体业务Model(实现方式没有规定，合理构造数据即可)
public class AccountModel {
    /**
     * 登录
     */
    public void login(String account, String password, LifecycleOwner lifecycle, final IModelCallback.Http<UserBean> modelCallback) {
        BizFactory.getBiz(AccountBiz.class).login(account, password, lifecycle, new HttpCallback<Response<UserBean>>() {

            @Override
            public void onSuccess(Response<UserBean> value) {
                //通过 IModelCallback 回调给调用者（Presenter）
                modelCallback.onSuccess(value.getData());
            }

            @Override
            public void onError(int code, String desc) {
                //通过 IModelCallback 回调给调用者（Presenter）
                modelCallback.onError(code, desc);
            }

            @Override
            public void onCancel() {
                //通过 IModelCallback 回调给调用者（Presenter）
                modelCallback.onCancel();
            }
        });
    }
}
```

- Model 负责构造数据传递给调用者（Presenter）,在没有 P 层的MVC 中可能就直接回调给 V 层，从而导致 V / M 耦合不易维护
- IModelCallback 只是为了规范和便于维护添加的一层回调

### 1.4 ViewModel
这里没有对 ViewModel 进行任何封装，在 MVP 的模式下使用 Jetpack 提供的 ViewModel 将 特定的 ViewModel 直接绑定到 View

###  2.MVVM 使用

### 2.1 View 接口定义
```
/**
 * ILoginView
 */
public interface ILoginView extends IMVVMView {
    public void loginSuccess(UserBean userBean);

    public void onError(int code, String desc);
}
```
### 2.2 Model 实现
```
public class LoginModel {

    /**
     * 登录
     */
    public void login(String account, String password, LifecycleOwner lifecycle, final IModelCallback.Http<UserBean> modelCallback) {
        //构建请求参数
        TreeMap<String, Object> request = new TreeMap<>();
        request.put("username", account);
        request.put("password", password);
        //发送请求
        new RHttp.Builder()
                .post()
                .apiUrl("user/login")
                .addParameter(request)
                .lifecycle(lifecycle)
                .build()
                .execute(new HttpCallback<Response<UserBean>>() {
                    @Override
                    public void onSuccess(Response<UserBean> object) {
                        modelCallback.onSuccess(object.getData());
                    }

                    @Override
                    public void onError(int code, String msg) {
                        modelCallback.onError(code, msg);
                    }

                    @Override
                    public void onCancel() {
                        modelCallback.onCancel();
                    }
                });
    }
}
```
### 2.3 Presenter 实现
```
public class LoginPresenter extends MVVMPresenter<ILoginView> {

    /**
     * 登录
     */
    public void login(String account, String password) {
        ModelFactory.getModel(LoginModel.class).login(account, password, getView().getLifecycleOwner(), new IModelCallback.Http<UserBean>() {
            @Override
            public void onSuccess(UserBean object) {
                if (!isAttached()) return;
                getView().loginSuccess(object);
            }

            @Override
            public void onError(int code, String msg) {
                if (isAttached()) getView().onError(code, msg);
            }

            @Override
            public void onCancel() {
            }
        });
    }
}
```
### 2.4 Activity/Fragment 实现
```
public class LoginActivity extends BaseActivity<ILoginView, LoginPresenter> implements ILoginView {

    private UserViewModel userViewModel;
    private LoginDataBinding dataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dataBinding
        dataBinding = DataBindingUtil.setContentView(this, layoutId());
        //ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        getViewDataBinding().setLifecycleOwner(this);
        dataBinding.setUserViewModel(userViewModel);

        //登录
        getMVVMPresenter().login("aaa", "xxx");
    }

    @Override
    protected int layoutId() {
        return R.layout.layout_sample;
    }

    @Override
    public LoginPresenter makePresenter() {
        return new LoginPresenter();
    }

    @Override
    public void loginSuccess(UserBean userBean) {
        //update ui
        //findViewById(R.id.tv_user_name).setText(userBean.getNickname());//传统方式更新UI
        //dataBinding.tvUserName.setText(userBean.getNickname());//dataBinding获取控件更新UI
        userViewModel.getUserBean().setValue(userBean);//ViewModel绑定数据直接更新UI
    }

    @Override
    public void onError(int code, String desc) {
        //update ui
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }
}
```
### 2.5 ViewModel 实现
```
package com.ruffian.android.mvvm.sample;

public class UserViewModel extends ViewModel {
    private MutableLiveData<UserBean> userBeanLiveData;

    public MutableLiveData<UserBean> getUserBean() {
        if (userBeanLiveData == null) userBeanLiveData = new MutableLiveData<>();
        return userBeanLiveData;
    }
}
```
### 2.6 layout.xml 实现
```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data class="LoginDataBinding">

        <variable
            name="userViewModel"
            type="com.ruffian.android.mvvm.sample.UserViewModel" />

    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@color/white">

        <TextView
            android:id="@+id/tv_user_name"
            style="@style/wrap_tv_14_black"
            android:text="@{userViewModel.userBean.nickname??@string/placeholder}"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            tools:text="@string/placeholder" />

    </androidx.constraintlayout.widget.ConstraintLayout>

</layout>
```