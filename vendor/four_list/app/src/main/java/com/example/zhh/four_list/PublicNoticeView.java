package com.example.zhh.four_list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
/*
**公告栏的滚动
**
 */
public class PublicNoticeView extends LinearLayout {

    private static final String TAG = "PUBLICNOTICEVIEW";
    private Context mContext;
    private ViewFlipper mViewFlipper;
    private View mScrollTitleView;

    public PublicNoticeView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public PublicNoticeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        bindLinearLayout();
        bindNotices();
    }

    /**
     * 初始化自定义的布局
     */
    private void bindLinearLayout() {
        mScrollTitleView = LayoutInflater.from(mContext).inflate(R.layout.scrollnoticebar, null);
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        addView(mScrollTitleView, params);
        mViewFlipper = (ViewFlipper) mScrollTitleView.findViewById(R.id.id_scrollNoticeTitle);
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_bottom));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_top));
        mViewFlipper.startFlipping();
    }

    /**
     * 网络请求内容后进行适配
     */
    protected void bindNotices() {
        mViewFlipper.removeAllViews();
        int i = 0;
        while (i < 5) {
            TextView textView = new TextView(mContext);
            if (i == 0) {
                String text = "公告:用户小苹果购买了一件商品!";
                textView.setText(text);
            }
            if (i == 1) {
                String text = "公告:用户小ss购买了一件商品!";
                textView.setText(text);
            }
            if (i == 2) {
                String text = "公告:用户小dd购买了一件商品!";
                textView.setText(text);
            }
            if (i == 3) {
                String text = "公告:用户小ff购买了一件商品!";
                textView.setText(text);
            }
            if (i == 4) {
                String text = "公告:用户小gg购买了一件商品!";
                textView.setText(text);
            }

            LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
            mViewFlipper.addView(textView, layoutParams);
            i++;
        }
    }

}
