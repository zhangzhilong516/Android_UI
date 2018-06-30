package subdragon.ui.com.android_ui.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * author zhangzhilong
 * on 2018/6/30.
 * Description:
 */

public class DotIndicatorView extends View {
    private Drawable mDrawable;

    public DotIndicatorView(Context context) {
        this(context, null);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置Drawable
     *
     * @param drawable
     */
    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
        invalidate();//重绘，会调用onDraw方法
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawable != null) {
//            mDrawable.setBounds(0,0,getMeasuredWidth(),getMeasuredHeight());
//            mDrawable.draw(canvas);
            //画圆
            Bitmap bitmap = drawableToBitmap(mDrawable);

            Bitmap circleBitmap = getCircleBitmap(bitmap);

            //绘制
            canvas.drawBitmap(circleBitmap, 0, 0, null);
        }
    }

    /**
     * 获取圆形Bitmap
     *
     * @param bitmap
     * @return
     */
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        //创建一个Bitmap
        Bitmap circleBitmap = Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setFilterBitmap(true);
        paint.setDither(true);//防抖动
        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,getMeasuredWidth()/2,paint);
        //再把原来的bitmap会知道新的圆bitmap上面
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//取两个bitmap的交集
        canvas.drawBitmap(bitmap,0,0,paint);

        //内存优化，回收bitmap
        bitmap.recycle();
        bitmap=null;
        return circleBitmap;
    }


    /**
     * 把Drawable转换成Bitmap
     *
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        // ColorDrawable
        //创建一个空bitmap
        Bitmap outBitmap = Bitmap.createBitmap(getMeasuredWidth(),getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap );
        //把Drawable画到Bitmap上
        drawable.setBounds(0,0,getMeasuredWidth(),getMeasuredHeight());
        drawable.draw(canvas);

        return outBitmap;
    }
}
