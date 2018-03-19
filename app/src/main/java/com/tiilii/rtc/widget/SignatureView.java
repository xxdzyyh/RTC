package com.tiilii.rtc.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Retina975
 * @since 2017/07/21
 */

public class SignatureView extends View {


    private static final float MAX_STROKE_WIDTH = 80f;

    private boolean mTouchable = true;
    private Context mContext;
    private Map<Float, Brush> brushes = new HashMap<>();
    private float mLastKey = 0;
    private Brush mLastBrush = null;
    private float mCurrentKey = 0;
    private Brush mCurrentBrush = null;

    private int mWidth;
    private int mHeight;

    private OnSavePictureListener onSavePictureListener = null;

    public SignatureView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

    }

    /**
     * Erases the signature.
     */
//    public void clear() {
//        path.reset();
//
//        // Repaints the entire view.
//        invalidate();
//    }
    @Override
    protected void onDraw(Canvas canvas) {
        Set<Float> keys = brushes.keySet();
        for (float key : keys) {

            Brush brush = brushes.get(key);
            canvas.drawPath(brush.getPath(), brush.getPaint());

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        mHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);

    }

    private void initBrush(float pressure) {
        // 将压力值精确到小数点后1位
        DecimalFormat df = new DecimalFormat("0.0");
        pressure = Float.valueOf(df.format(pressure));

        if (!brushes.containsKey(pressure)) {

            float brushWidth = MAX_STROKE_WIDTH * pressure;
            mCurrentBrush = new Brush(brushWidth);
            brushes.put(pressure, mCurrentBrush);
        } else {

            mCurrentBrush = brushes.get(pressure);
        }
        mCurrentKey = pressure;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mTouchable) {

            return true;
        }
        float eventX = event.getX();
        float eventY = event.getY();

        float pressure = event.getPressure();
        initBrush(pressure);
        Path path = mCurrentBrush.getPath();
        float HALF_STROKE_WIDTH = mCurrentBrush.getHalfStrokeWidth();


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                mCurrentBrush.setLastTouchX(eventX);
                mCurrentBrush.setLastTouchY(eventY);
                mLastBrush = null;
                // There is no end point yet, so don't waste cycles invalidating.
                return true;

            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                if (mLastKey != mCurrentKey) {
                    float x, y;
                    if (mLastBrush != null) {

                        x = mLastBrush.getLastTouchX();
                        y = mLastBrush.getLastTouchY();
                    } else {

                        x = eventX;
                        y = eventY;
                    }
                    path.moveTo(x, y);
                    mCurrentBrush.setLastTouchX(x);
                    mCurrentBrush.setLastTouchY(y);
                }
                // Start tracking the dirty region.
                resetDirtyRect(mCurrentKey, eventX, eventY);

                // When the hardware tracks events faster than they are delivered, the
                // event will contain a history of those skipped points.
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) {
                    float historicalX = event.getHistoricalX(i);
                    float historicalY = event.getHistoricalY(i);
                    expandDirtyRect(mCurrentKey, historicalX, historicalY);
                    path.lineTo(historicalX, historicalY);
                }

                // After replaying history, connect the line to the touch point.
                path.lineTo(eventX, eventY);
                break;

            default:
//                debug("Ignored touch event: " + event.toString());
                Log.d("debug", "Ignored touch event: " + event.toString());
                return false;
        }

        // Include half the stroke width to avoid clipping.
        RectF dirtyRect = mCurrentBrush.getDirtyRect();
        invalidate(
                (int) (dirtyRect.left - HALF_STROKE_WIDTH),
                (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

        mCurrentBrush.setLastTouchX(eventX);
        mCurrentBrush.setLastTouchY(eventY);

        mLastBrush = mCurrentBrush;
        mLastKey = mCurrentKey;
        return true;
    }

    /**
     * Called when replaying history to ensure the dirty region includes all
     * points.
     */
    private void expandDirtyRect(float key, float historicalX, float historicalY) {
        Brush brush = brushes.get(key);
        RectF dirtyRect = brush.getDirtyRect();
        if (historicalX < dirtyRect.left) {
            dirtyRect.left = historicalX;
        } else if (historicalX > dirtyRect.right) {
            dirtyRect.right = historicalX;
        }
        if (historicalY < dirtyRect.top) {
            dirtyRect.top = historicalY;
        } else if (historicalY > dirtyRect.bottom) {
            dirtyRect.bottom = historicalY;
        }
        brush.setDirtyRect(dirtyRect);
    }

    /**
     * Resets the dirty region when the motion event occurs.
     */
    private void resetDirtyRect(float key, float eventX, float eventY) {
        Brush brush = brushes.get(key);
        RectF dirtyRect = brush.getDirtyRect();
        float lastTouchX = brush.getLastTouchX();
        float lastTouchY = brush.getLastTouchY();

        // The lastTouchX and lastTouchY were set when the ACTION_DOWN
        // motion event occurred.
        dirtyRect.left = Math.min(lastTouchX, eventX);
        dirtyRect.right = Math.max(lastTouchX, eventX);
        dirtyRect.top = Math.min(lastTouchY, eventY);
        dirtyRect.bottom = Math.max(lastTouchY, eventY);

        brush.setDirtyRect(dirtyRect);
    }

    public void setTouchable(boolean touchable) {
        mTouchable = touchable;
    }

    public void saveBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mWidth/*width*/, mHeight/*height*/, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRGB(255, 255, 255);

        Set<Float> keys = brushes.keySet();
        for (float key : keys) {

            Brush brush = brushes.get(key);
            canvas.drawPath(brush.getPath(), brush.getPaint());

        }

        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Files/";
        String fileName = "img_" + "test" + ".jpeg";

        File f = new File(filePath + fileName);

        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            if (onSavePictureListener != null) {
                onSavePictureListener.onSuccess();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (onSavePictureListener != null) {
                onSavePictureListener.onFailure();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (onSavePictureListener != null) {
                onSavePictureListener.onFailure();
            }
        }
    }


    public void setOnSavePictureListener(OnSavePictureListener onSavePictureListener) {
        this.onSavePictureListener = onSavePictureListener;
    }

    public interface OnSavePictureListener {
        void onSuccess();

        void onFailure();
    }

    public class Brush {

        private float mStrokeWidth;
        private float mHalfStrokeWidth;

        /**
         * Optimizes painting by invalidating the smallest possible area.
         */
        private float mLastTouchX;
        private float mLastTouchY;

        private RectF mDirtyRect;

        private Paint mPaint;
        private Path mPath;

        public Brush(float strokeWidth) {

            mStrokeWidth = strokeWidth;
            mHalfStrokeWidth = strokeWidth / 2;

            mDirtyRect = new RectF();
            mPaint = new Paint();

            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.BLACK);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeWidth(mStrokeWidth);

            mPath = new Path();
        }

        public Paint getPaint() {
            return mPaint;
        }

        public Path getPath() {
            return mPath;
        }

        public float getLastTouchX() {
            return mLastTouchX;
        }

        public float getLastTouchY() {
            return mLastTouchY;
        }

        public RectF getDirtyRect() {
            return mDirtyRect;
        }

        public float getHalfStrokeWidth() {
            return mHalfStrokeWidth;
        }

        public void setLastTouchX(float mLastTouchX) {
            this.mLastTouchX = mLastTouchX;
        }

        public void setLastTouchY(float mLastTouchY) {
            this.mLastTouchY = mLastTouchY;
        }

        public void setDirtyRect(RectF mDirtyRect) {
            this.mDirtyRect = mDirtyRect;
        }
    }
}
