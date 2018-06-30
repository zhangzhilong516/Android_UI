package subdragon.ui.com.android_ui.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import subdragon.ui.com.android_ui.R;


/**
 * author zhangzhilong
 * on 2018/6/30.
 * Description: 字体变色View
 */

public class ColorTrackTextView extends AppCompatTextView {

    //两支画笔，一支绘制原始颜色，另一支绘制变化的颜色
    private Paint mOriginalPaint;
    private Paint mChangedPaint;


    private Direction mDirection = Direction.LEFT_TO_RIGHT;

    public enum Direction {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }

    //定义一个进度值
    private float mCurrentProgress = 0.0f;

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context, attrs);
    }

    /**
     * 初始化画笔,并获取属性值
     */
    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        int originColor = typedArray.getColor(R.styleable.ColorTrackTextView_OriginalColor,
                getTextColors().getDefaultColor());
        int changedColor = typedArray.getColor(R.styleable.ColorTrackTextView_ChangedColor,
                getTextColors().getDefaultColor());

        mOriginalPaint = getPaintByColor(originColor);
        mChangedPaint = getPaintByColor(changedColor);

        typedArray.recycle();
    }


    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);//抗锯齿
        paint.setDither(true);//防抖动
        paint.setTextSize(getTextSize());//设置字体大小，就是TextView的字体大小
        return paint;
    }


    @Override
    protected void onDraw(Canvas canvas) {

        int middle = (int) (mCurrentProgress * getWidth());

        if (mDirection == Direction.LEFT_TO_RIGHT) {

            drawText(canvas, mChangedPaint, 0, middle);
            drawText(canvas, mOriginalPaint, middle, getWidth());

        } else if (mDirection == Direction.RIGHT_TO_LEFT) {

            drawText(canvas, mChangedPaint, getWidth() - middle, getWidth());
            drawText(canvas, mOriginalPaint, 0, getWidth() - middle);

        }
    }

    private void drawText(Canvas canvas, Paint paint, int start, int end) {
        //裁剪Rect,绘制不变色的文字部分
        canvas.save();//保存画布
        Rect rect = new Rect(start, 0, end, getHeight());
        canvas.clipRect(rect);

        String text = getText().toString();
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        //获取字体宽度
        int x = getWidth() / 2 - bounds.width() / 2;
        //基线baseline
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseline = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseline, paint);
        canvas.restore();//回收画布
    }

    /**
     * 设置方向
     */
    public void setDirection(Direction direction) {
        this.mDirection = direction;
    }

    /**
     * 设置进度
     */
    public void setCurrentProgress(float progress) {
        this.mCurrentProgress = progress;
        invalidate();
    }

    public void setChangedColor(int changeColor) {
        this.mChangedPaint.setColor(changeColor);
    }

    public void setOriginalColor(int changeColor) {
        this.mOriginalPaint.setColor(changeColor);
    }

}
