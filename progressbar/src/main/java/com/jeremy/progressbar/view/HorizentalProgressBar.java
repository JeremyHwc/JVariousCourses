package com.jeremy.progressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.jeremy.progressbar.R;

public class HorizentalProgressBar extends ProgressBar {
    private static final int DEFAULT_TEXT_SIZE = 10;//SP
    private static final int DEFAULT_TEXT_COLOR = 0xFFFC00D1;
    private static final int DEFAULT_COLOR_UNREACH = 0xFFD3D6DA;
    private static final int DEFAULT_HEIGHT_UNREACH = 2;//DP
    private static final int DEFAULT_COLOR_REACH = DEFAULT_TEXT_COLOR;
    private static final int DEFAULT_HEIGHT_REACH = 2;//DP
    private static final int DEFAULT_TEXT_OFFSET = 10;//DP

    private int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private int mUnReachColor = DEFAULT_COLOR_UNREACH;
    private int mUnReachHeight = dp2px(DEFAULT_HEIGHT_UNREACH);
    private int mReachColor = DEFAULT_COLOR_REACH;
    private int mReachHeight = dp2px(DEFAULT_HEIGHT_REACH);
    private int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);

    private Paint paint=new Paint();
    private int mRealWidth;//除去了padding

    public HorizentalProgressBar(Context context) {
        this(context, null);
    }

    public HorizentalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizentalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyleAttrs(attrs);
    }

    /**
     * 获取自定义属性
     * @param attrs
     */
    private void obtainStyleAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HorizentalProgressBar);
        mReachColor= typedArray.getColor(R.styleable.HorizentalProgressBar_progress_reach_color,mReachColor);
        mUnReachColor= typedArray.getColor(R.styleable.HorizentalProgressBar_progress_unreach_color,mUnReachColor);
        mTextColor= typedArray.getColor(R.styleable.HorizentalProgressBar_progress_text_color,mTextColor);

        mReachHeight= (int) typedArray.getDimension(R.styleable.HorizentalProgressBar_progress_reach_height,mReachHeight);
        mUnReachHeight= (int) typedArray.getDimension(R.styleable.HorizentalProgressBar_progress_unreach_height,mUnReachHeight);
        mTextOffset= (int) typedArray.getDimension(R.styleable.HorizentalProgressBar_progress_text_offset,mTextOffset);
        mTextSize= (int) typedArray.getDimension(R.styleable.HorizentalProgressBar_progress_text_size,mTextSize);

        typedArray.recycle();

    }

    private int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                getResources().getDisplayMetrics());
    }

    private int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal,
                getResources().getDisplayMetrics());
    }
}
