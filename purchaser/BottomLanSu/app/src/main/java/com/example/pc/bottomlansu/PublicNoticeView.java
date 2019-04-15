package com.example.pc.bottomlansu;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

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
            String text = "恭喜王**喜提茅台一箱";
            String text1 = "恭喜赵**免费得到888满减优惠";
            String text2 = "恭喜孙**抢到AE86一辆";

            TextView textView = new TextView(mContext);
            int temp = (int)(1+Math.random()*(3));
            if (temp==1){
                textView.setText(text);
            }else if(temp==2){
                textView.setText(text1);
            }else{
                textView.setText(text2);
            }

            LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
            mViewFlipper.addView(textView, layoutParams);
            i++;
        }
    }

}
