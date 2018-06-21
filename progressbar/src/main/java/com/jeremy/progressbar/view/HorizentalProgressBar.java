package com.jeremy.progressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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

    private Paint mPaint=new Paint();
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
        mPaint.setTextSize(mTextSize);
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

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //这里是定义水平的进度条，所以用户必须指定宽度，所以对宽度mode不进行适配
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height=measureHeight(heightMeasureSpec);

        setMeasuredDimension(width,height);

        mRealWidth=getMeasuredWidth()-getPaddingLeft()-getPaddingRight();//实际绘制区域宽度
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getPaddingLeft(),getHeight()/2);

        boolean noNeedUnReach=false;

        String text=getProgress()+"%";
        int textWidth= (int) mPaint.measureText(text);
        float ratio =getProgress()*1.0f/getMax();
        float progressX=ratio*mRealWidth;
        if (progressX+textWidth>mRealWidth){
            progressX=mRealWidth-textWidth;
            noNeedUnReach=true;
        }

        float endX=progressX-mTextOffset/2;
        if (endX>0){
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeight);
            canvas.drawLine(0,0,endX,0,mPaint);
        }

        //draw text
        mPaint.setColor(mTextColor);
        int y= (int) (-(mPaint.descent()+mPaint.ascent())/2);
        canvas.drawText(text,progressX,y,mPaint);

        //draw unreach bar
        if (!noNeedUnReach){
            float start=progressX+mTextOffset/2+textWidth;
            mPaint.setColor(mUnReachColor);
            mPaint.setStrokeWidth(mUnReachHeight);
            canvas.drawLine(start,0,mRealWidth,0,mPaint);
        }
    }

    private int measureHeight(int heightMeasureSpec) {
        int result=0;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode==MeasureSpec.EXACTLY){
            result=heightSize;
        }else {
            int textHeight= (int) (mPaint.descent()-mPaint.ascent());//文字高度
            result=getPaddingBottom()+getPaddingTop()+
                    Math.max(Math.max(mReachHeight,mUnReachHeight),Math.abs(textHeight));
            if (heightMode==MeasureSpec.AT_MOST){
                result=Math.min(result,heightSize);
            }
        }
        return result;
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
