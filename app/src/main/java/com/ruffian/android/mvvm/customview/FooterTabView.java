package com.ruffian.android.mvvm.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruffian.android.mvvm.R;
import com.ruffian.library.widget.RRelativeLayout;


/**
 * FooterTabView
 *
 * @author ZhongDaFeng
 */
public class FooterTabView extends RRelativeLayout {

    /**
     * 上下文对象
     */
    private Context mContext;
    /**
     * 选中的字体颜色
     */
    private int mTabTextColorSelected;
    /**
     * 默认或不选中的字体颜色
     */
    private int mTabTextColorDefault;
    /**
     * 选中的图片
     */
    private int mTabIconSelected;
    /**
     * 不被选中的图片
     */
    private int mTabIconDefault;
    /**
     * 是否被选中
     */
    private boolean mTabChecked;
    /**
     * 显示文本
     */
    private String mTabText;
    /**
     * 视图
     */
    private View footView;
    /**
     * 图标
     */
    private ImageView ivTabIcon;
    /**
     * 文本
     */
    private TextView tvTabText;

    private int mBgColorSelected = R.array.array_btn_bg;
    private int mBgColorDefault = R.array.array_tab_bg_default;

    public FooterTabView(Context context) {
        this(context, null, 0);
    }

    public FooterTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FooterTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
    }


    private void initView(Context context, AttributeSet attrs) {
        this.mContext = context;
        this.setGravity(Gravity.CENTER);
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.footerTabView);
        mTabIconDefault = attr.getResourceId(R.styleable.footerTabView_tab_icon_default, R.mipmap.ic_launcher);
        mTabIconSelected = attr.getResourceId(R.styleable.footerTabView_tab_icon_selected, mTabIconDefault);
        mTabTextColorSelected = attr.getResourceId(R.styleable.footerTabView_tab_text_color_selected, R.color.black);
        mTabTextColorDefault = attr.getResourceId(R.styleable.footerTabView_tab_text_color_default, R.color.black_70);
        mTabChecked = attr.getBoolean(R.styleable.footerTabView_tab_checked, false);
        mTabText = attr.getString(R.styleable.footerTabView_tab_text);
        footView = View.inflate(context, R.layout.layout_footer_tab, null);
        addView(footView);

        ivTabIcon = (ImageView) footView.findViewById(R.id.iv_tab_icon);
        tvTabText = (TextView) footView.findViewById(R.id.tv_tab_text);

        tvTabText.setText(mTabText);
        tvTabText.setVisibility(TextUtils.isEmpty(mTabText) ? View.GONE : VISIBLE);

        setChecked(mTabChecked);

        attr.recycle();
    }

    /**
     * 设置是否选中
     *
     * @param isChecked true为选中,false为未选中
     */
    public void setChecked(boolean isChecked) {
        this.mTabChecked = isChecked;
        ivTabIcon.setImageResource(mTabChecked ? mTabIconSelected : mTabIconDefault);
        tvTabText.setTextColor(mContext.getResources().getColor(mTabChecked ? mTabTextColorSelected : mTabTextColorDefault));
        getHelper().setBackgroundColorNormalArray(mContext.getResources().getIntArray(mTabChecked ? mBgColorSelected : mBgColorDefault));
    }

}
