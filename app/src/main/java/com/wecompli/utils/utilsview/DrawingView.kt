package com.wecompli.utils.utilsview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent

import androidx.appcompat.widget.AppCompatImageView

import com.wecompli.utils.sheardpreference.PreferenceConstent

class DrawingView(c: Context, attrs: AttributeSet) : AppCompatImageView(c, attrs) {
    /* private int color = Color.parseColor("#0000FF");
        private float width = 4f;
        private List<Holder> holderList = new ArrayList<Holder>();

private class Holder {
    Path path;
    Paint paint;

    Holder(int color, float width) {
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(width);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }
}

    public DrawingView(Context context) {
        super(context);
        init();
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        holderList.add(new Holder(color, width));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Holder holder : holderList) {
            canvas.drawPath(holder.path, holder.paint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                holderList.add(new Holder(color,width));
                holderList.get(holderList.size() - 1).path.moveTo(eventX, eventY);
                return true;
            case MotionEvent.ACTION_MOVE:
                holderList.get(holderList.size() - 1).path.lineTo(eventX, eventY);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public void resetPaths() {
        for (Holder holder : holderList) {
            holder.path.reset();
        }
        invalidate();
    }

    public void setBrushColor(int color) {
        this.color = color;
    }

    public void setWidth(float width) {
        this.width = width;
    }*/
    var mBitmap: Bitmap?=null
    var mCanvas: Canvas?=null
    private val mPath: Path
    private val mBitmapPaint: Paint
    private val mPaint: Paint

    private var mX: Float = 0.toFloat()
    private var mY: Float = 0.toFloat()

    //this.measure(100, 100);
    //this.layout(0, 0, 100, 100);
    val bitmap: Bitmap
        get() {
            this.isDrawingCacheEnabled = true
            this.buildDrawingCache()
            val bmp = Bitmap.createBitmap(this.drawingCache)
            this.isDrawingCacheEnabled = false
            return bmp
        }


    init {
        mPath = Path()
        mBitmapPaint = Paint(Paint.DITHER_FLAG)
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.color = -0xdabd79
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = 11f

    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)

        mCanvas = Canvas(mBitmap!!)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(mBitmap!!, 0f, 0f, mBitmapPaint)
        canvas.drawPath(mPath, mPaint)


    }

    private fun touch_start(x: Float, y: Float) {
        mPath.reset()
        mPath.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun touch_move(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    private fun touch_up() {
        mPath.lineTo(mX, mY)
        // commit the path to our offscreen
        mCanvas!!.drawPath(mPath, mPaint)
        // kill this so we don't double draw
        mPath.reset()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        // PreferenceConstent.signdraw=true;
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touch_start(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touch_move(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touch_up()
                invalidate()
            }
        }
        return true
    }


    fun clear() {
        mBitmap!!.eraseColor(Color.parseColor("#aaaaaa"))
        invalidate()
        System.gc()

    }

    companion object {
        private val TOUCH_TOLERANCE = 4f
    }
}