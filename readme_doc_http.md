# RHttp

> RHttp 是基于 Retrofit2 + OkHttp3 + RxJava2 +Lifecycle 封装的网络请求框架

- 基本的get、post、put、delete、4种请求
- 单/多文件上传
- 断点续传下载
- 支持手动取消网络请求
- 绑定组件生命周期自动管理网络请求
- 支持表单格式，String，json格式数据提交请求


### 1.全局配置

`初始化 RHttp 必须调用 init `

```
public class RApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

		//必须初始化RHttp
        RHttp.Configure.get().init(this);//初始化
    }

}
```

`Configure 配置参数说明`

```
        RHttp.Configure.get()
                .baseHeader(new TreeMap<String, Object>())//基础header，配置后所有请求携带
                .baseParameter(new TreeMap<String, Object>())//基础参数，配置后所有请求携带
                .baseUrl("https://www.xxx.com/")//基础URL
                .showLog(true)//是否打印日志
                .timeout(15)//请求超时时间
                .timeUnit(TimeUnit.SECONDS)//超时时间单位
                .init(this);//初始化
```


### 2.使用Http请求

### 2.1 RHttp api接口说明
```
        RHttp http = new RHttp.Builder()
                .post()//请求方式
                .baseUrl("https://www.xxx.com/")//基础URL，会覆盖全局设置
                .apiUrl("user/login")//接口地址
                .addHeader(new TreeMap<String, Object>())//添加header,不会覆盖
                .setHeader(new TreeMap<String, Object>())//设置header，覆盖所有（包括全局配置）
                .addParameter(new TreeMap<String, Object>())//添加参数,不会覆盖
                .setParameter(new TreeMap<String, Object>())//设置参数，覆盖所有（包括全局配置）
                .tag("request_tag")//请求唯一标识，用于取消请求的key
                .file(new TreeMap<String, File>())//上传文件时，设置文件集合
                .file("key", new ArrayList<File>())//上传文件时，一个Key对应多个文件
                .setBodyString("bodyString", true)//bodyString:设置String类型参数;isJson:是否强制JSON格式(bodyString设置后Parameter无效)
                .timeout(15)//请求超时时间
                .timeUnit(TimeUnit.SECONDS)//超时时间单位
                .lifecycle(this)//自动管理生命周期，不传可以通过手动取消请求
                .build();
                
        http.cancel();//取消当前网络请求
        http.isCanceled();//当前网络请求是否已经取消
        http.execute(new HttpCallback<Object>() {}); //普通请求
        http.execute(new UploadCallback<Object>() {});//携带文件上传请求
        
```
### 2.2 RHttp 数据解析，以及回调逻辑说明
### 2.2.1 RHttp 框架处理最终回调

> RHttp将接口逻辑失败的情况直接回调 onError(int code,String msg);
> 何时认为接口逻辑失败？需要开发者告诉框架，实现 IResponse 接口

`IResponse.java`

```
/**
 * 响应接口定义
 * 建议开发者实现此接口，并且设置重写对应的方法
 * 1.重写：框架会根据 isISuccess() 处理 逻辑接口失败时 直接回调 onError
 * 2.不重写：开发者自行实现 逻辑接口失败 时回调到 onError
 */
public interface IResponse {

    /**
     * 接口逻辑是否成功
     * 备注：例如 code == 0 表示接口处理成功
     *
     * @return
     */
    public boolean isISuccess();

    /**
     * 接口响应码
     *
     * @return
     */
    public int getICode();

    /**
     * 接口响应描述
     *
     * @return
     */
    public String getIMsg();

}
```

开发者将需要解析的数据实体，实现 IResponse 接口

`Response.java`
```
/**
 * 解析接口响应实体类 : HttpCallback<T>
 * 备注：
 * 1.实现接口 IResponse 重写函数，告知 http 框架 code == 0 认为是接口逻辑成功，code !=0 情况由 http 直接回调 onError
 * 2.如果不想实现 IResponse，则可以传任意类型到 HttpCallback<T>
 */
public class Response<T> implements Serializable, IResponse {

    /*
    {
        "data": {},
        "errorCode": 0,
        "errorMsg": ""
    }
    */

    /**
     * 描述信息
     */
    @SerializedName("errorMsg")
    private String msg;

    /**
     * 状态码
     */
    @SerializedName("errorCode")
    private int code;

    /**
     * 数据对象/成功返回对象
     */
    @SerializedName("data")
    private T data;


    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean isISuccess() {
        return code == 0;
    }

    @Override
    public int getICode() {
        return code;
    }

    @Override
    public String getIMsg() {
        return msg;
    }
}
```
`此时请求使用示例`
```
        new RHttp.Builder()
                .post()
                .baseUrl("https://www.xxx.com/")
                .apiUrl("user/login")
                .build()
                .execute(new HttpCallback<Response<UserBean>>() {
                    @Override
                    public void onSuccess(Response<UserBean> object) {
                        UserBean userBean = object.getData();
                    }

                    @Override
                    public void onError(int code, String msg) {
                        //todo
                    }

                    @Override
                    public void onCancel() {
                        //todo
                    }
                });
```

### 2.2.2 开发者自行实现回调逻辑

不必实现 IResponse 接口，可以传递任意能够正常解析的对象。例如 String, JSONObject, JSONArray, 其他实体类
但此时只要网络请求成功就会将源数据回调 onSuccess(),开发者需要自行处理逻辑失败的情况

```
        new RHttp.Builder()
                .post()
                .baseUrl("https://www.xxx.com/")
                .apiUrl("user/login")
                .build()
                .execute(new HttpCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject object) {
                        try {
                            int code = object.getInt("code");
                            if (code != 0) {//登录失败
                                String msg = object.getString("msg");
                                onError(code, msg);//错误回调
                            } else {
                                //登录成功
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(int code, String msg) {
                        //todo
                    }

                    @Override
                    public void onCancel() {
                        //todo
                    }
                });

```

### 2.3 RHttp 上传文件使用示例

`UploadCallback<T>回调接口说明`

```
public interface UploadCallback<T> extends HttpCallback<T> {

    /**
     * 上传进度回调
     * @param file         文件
     * @param currentSize  当前值
     * @param totalSize    总大小
     * @param progress     进度
     * @param currentIndex 当前下标
     * @param totalFile    总文件数
     */
    void progress(File file, long currentSize, long totalSize, float progress, int currentIndex, int totalFile);
}
```

`使用示例`

```
        //Header参数
        TreeMap<String, Object> header = new TreeMap<>();
        header.put("H1", "H100");

        //请求参数
        TreeMap<String, Object> parameter = new TreeMap<>();
        parameter.put("P1", "P100");

        //文件参数
        TreeMap<String, File> fileMap = new TreeMap<>();
        parameter.put("icon", new File("/file/xx.png"));
        parameter.put("file", new File("/file/yy.txt"));

        new RHttp.Builder()
                .post()                     //请求方式
                .baseUrl("https://www.xxx.com/")//baseUrl
                .apiUrl("user/login")       //接口地址
                .addParameter(parameter)    //参数
                .addHeader(header)          //请求头
                .file(fileMap)              //文件集合
                .lifecycle(this)            //自动管理生命周期
                .build()
                .execute(new UploadCallback<Response<UserBean>>() {
                    @Override
                    public void onSuccess(Response<UserBean> object) {
                        //成功
                    }

                    @Override
                    public void onError(int code, String msg) {
                        //todo
                    }

                    @Override
                    public void onCancel() {
                        //todo
                    }

                    @Override
                    public void progress(File file, long currentSize, long totalSize, float progress, int currentIndex, int totalFile) {
                        //文件上传过程进度回调
                    }
                });
```

### 3.使用 RDownload 下载

### 3.1 RDownload api接口说明

```
        RDownLoad.get().startDownload(new Download());//开始下载
        RDownLoad.get().stopDownload(new Download());//暂停下载
        RDownLoad.get().stopAllDownload();//暂停所有下载
        RDownLoad.get().getDownloadList(Download.class);//下载列表
        RDownLoad.get().removeDownload(new Download(), true);//删除下载
```

### 3.2 Download 实体类说明
```
@Table("download")
public class Download implements Serializable {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column("_id")
    private long id;//id

    @Column("localUrl")
    private String localUrl;//本地存储地址

    @Column("serverUrl")
    private String serverUrl;//下载地址

    @Column("totalSize")
    private long totalSize;//文件大小

    @Column("currentSize")
    private long currentSize;//当前大小

    @Column("state")
    private State state = State.NONE;//下载状态

    @Ignore
    private APIService apiService;//接口service

    @Ignore
    private DownloadCallback callback;//回调接口

    public Download() {
    }

    public Download(String url) {
        setServerUrl(url);
    }

    public Download(String url, DownloadCallback callback) {
        setServerUrl(url);
        setCallback(callback);
    }

    /**
     * 枚举下载状态
     */
    public enum State {
        NONE,           //无状态
        WAITING,        //等待
        LOADING,        //下载中
        PAUSE,          //暂停
        ERROR,          //错误
        FINISH,         //完成
    }
}
```
`拓展下载实体类  使用框架的 Download 或者其子类`

```
    public class DownloadApk extends Download {

        /**
         * 额外字段，apk图标
         */
        private String icon;

        public DownloadApk(String url, String icon, String localUrl) {
            setServerUrl(url);
            setLocalUrl(localUrl);
            setIcon(icon);
        }

        //get/set
    }
```

### 3.3 构建下载

```
        //文件存储路径和名称
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "QQ.apk");
        //下载地址
        String url = "http://imtt.dd.qq.com/16891/FC92B1B4471DE5AAD0D009DF9BF1AD01.apk?fsname=com.tencent.mobileqq_7.7.5_896.apk&csr=1bbd";
        //额外参数icon
        String icon = "http://pp.myapp.com/ma_icon/0/icon_6633_1535456193/96";

        DownloadApk bean = new DownloadApk(url, icon, file.getAbsolutePath());
        /*设置回调监听*/
        bean.setCallback(new DownloadCallback<DownloadApk>() {
            @Override
            public void onProgress(Download.State state, long currentSize, long totalSize, float progress) {
                //State:  NONE=无状态 WAITING=等待 LOADING=下载中 ERROR=错误 FINISH=完成
                //currentSize 已下载
                //totalSize 文件大小
                //progress 进度
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSuccess(DownloadApk object) {
                //下载成功
                //object.getLocalUrl();
            }
        });

        /*开始/继续*/
        RDownLoad.get().startDownload(bean);
        /*暂停*/
        RDownLoad.get().stopDownload(bean);
        /*暂停全部*/
        RDownLoad.get().stopAllDownload();
        /*移除下载*/
        RDownLoad.get().removeDownload(bean, true);
        /*下载中列表*/
        RDownLoad.get().getDownloadList(DownloadApk.class);
```


