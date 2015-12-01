package com.lvzheng.test.lucianandroidstudio.customwidgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.lvzheng.test.lucianandroidstudio.R;

/**
 * Created by lvzheng on 15/11/20.
 */
public class CircleArcView extends View{
    public static final String TAG = CircleArcView.class.getSimpleName();

    private final float ANIMAL_SPEED = 20.17f; // the time-consume of onDrow method (ms)

    private Paint mPaint;                // The paint to draw

    private int mArcStrokeColor;         // The arc stroke color

    private float mArcStrokeWidth;       // The arc stroke width

    private float mArcStartDegree;       // The beginning degree of drawing arc

    private float mArcSweepDegree;       // The degree of sweep

    private float mAnimalDegree;         // The degree that once drawing of animal

    private float mAlertMinValue;        // The alert of minimal value

    private float mAlertMaxValue;        // The alert of maximal value

    private boolean mEnableMaxAlert;     // The toggle of  maximal alert

    private boolean mEnableMinAlert;     // The toggle of minimal alert

    private int mAlertArcStrokeColor;    // The color of stroke that

    private Paint.Cap mCap;

    private Paint.Style mPaintStyle;     // The style of paint

    private boolean mEnableAnimal;       // The toggle of animal

    private float mAnimalDuration;       // The duration of animal

    private int mReDrawTimes;            // The times of redrawing

    private float mD; // 公差

    private int mDrawCount = 0;

    private RectF mRectF;

    /**
     * Simple constructor to use when creating a view from code.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     */
    public CircleArcView(Context context) {
        super(context);
        init(context, null);
    }

    /**
     * Constructor that is called when inflating a view from XML. This is called
     * when a view is being constructed from an XML file, supplying attributes
     * that were specified in the XML file. This version uses a default style of
     * 0, so the only attribute values applied are those in the Context's Theme
     * and the given AttributeSet.
     * <p/>
     * <p/>
     * The method onFinishInflate() will be called after all children have been
     * added.
     *
     * @param context The Context the view is running in, through which it can
     *                access the current theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     * @see #CircleArcView(Context, AttributeSet, int)
     */
    public CircleArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a
     * theme attribute. This constructor of View allows subclasses to use their
     * own base style when they are inflating. For example, a Button class's
     * constructor would call this version of the super class constructor and
     * supply <code>R.attr.buttonStyle</code> for <var>defStyleAttr</var>; this
     * allows the theme's button style to modify all of the base view attributes
     * (in particular its background) as well as the Button class's attributes.
     *
     * @param context      The Context the view is running in, through which it can
     *                     access the current theme, resources, etc.
     * @param attrs        The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     *                     the view. Can be 0 to not look for defaults.
     * @see #CircleArcView(Context, AttributeSet)
     */
    public CircleArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * Perform inflation from XML and apply a class-specific base style from a
     * theme attribute or style resource. This constructor of View allows
     * subclasses to use their own base style when they are inflating.
     * <p/>
     * When determining the final value of a particular attribute, there are
     * four inputs that come into play:
     * <ol>
     * <li>Any attribute values in the given AttributeSet.
     * <li>The style resource specified in the AttributeSet (named "style").
     * <li>The default style specified by <var>defStyleAttr</var>.
     * <li>The default style specified by <var>defStyleRes</var>.
     * <li>The base values in this theme.
     * </ol>
     * <p/>
     * Each of these inputs is considered in-order, with the first listed taking
     * precedence over the following ones. In other words, if in the
     * AttributeSet you have supplied <code>&lt;Button * textColor="#ff000000"&gt;</code>
     * , then the button's text will <em>always</em> be black, regardless of
     * what is specified in any of the styles.
     *
     * @param context      The Context the view is running in, through which it can
     *                     access the current theme, resources, etc.
     * @param attrs        The attributes of the XML tag that is inflating the view.
     * @param defStyleAttr An attribute in the current theme that contains a
     *                     reference to a style resource that supplies default values for
     *                     the view. Can be 0 to not look for defaults.
     * @param defStyleRes  A resource identifier of a style resource that
     *                     supplies default values for the view, used only if
     *                     defStyleAttr is 0 or can not be found in the theme. Can be 0
     *                     to not look for defaults.
     * @see #CircleArcView(Context, AttributeSet, int)
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleArcView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    /**
     * init the params of CircleView
     * @param context
     * @param attrs
     */
    private void init(Context context,AttributeSet attrs){

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleArcView);

        int strokeColor;
        int alertColor;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            strokeColor = context.getResources().getColor(android.R.color.holo_blue_dark);
            alertColor = context.getResources().getColor(android.R.color.holo_red_dark);
        }else {
            strokeColor = context.getColor(android.R.color.holo_blue_dark);
            alertColor = context.getColor(android.R.color.holo_red_dark);
        }

        mArcStrokeColor = typedArray.getColor(R.styleable.CircleArcView_arcStrokeColor, strokeColor);

        mArcStrokeWidth = typedArray.getDimension(R.styleable.CircleArcView_arcStrokeWidth, 4f);

        mArcStartDegree = typedArray.getFloat(R.styleable.CircleArcView_arcStartDegree, 0f);

        mArcSweepDegree = typedArray.getFloat(R.styleable.CircleArcView_arcSweepDegree,360f);

        mAlertMaxValue = typedArray.getFloat(R.styleable.CircleArcView_arcMaxValue,360f);

        mAlertMinValue = typedArray.getFloat(R.styleable.CircleArcView_arcMinValue, 0f);

        mAlertArcStrokeColor = typedArray.getColor(R.styleable.CircleArcView_alertArcStrokeColor,alertColor);

        int  cap = typedArray.getInt(R.styleable.CircleArcView_arcStrokeCap,0);
        switch (cap){
            case 0:
                mCap = Paint.Cap.BUTT;
                break;
            case 1:
                mCap = Paint.Cap.SQUARE;
                break;
            case 2:
                mCap = Paint.Cap.ROUND;
                break;
            default:
                mCap = Paint.Cap.BUTT;
                break;
        }

        int style = typedArray.getInt(R.styleable.CircleArcView_arcPaintStyle,2);
        switch (style){
            case 0:
                mPaintStyle = Paint.Style.FILL;
                break;
            case 1:
                mPaintStyle = Paint.Style.FILL_AND_STROKE;
                break;
            case 2:
                mPaintStyle = Paint.Style.STROKE;
                break;
            default:
                mPaintStyle = Paint.Style.STROKE;
                break;
        }

        mPaint.setColor(mArcStrokeColor);

        mPaint.setStrokeWidth(mArcStrokeWidth);

        mPaint.setStrokeCap(mCap);

        mPaint.setStyle(mPaintStyle);

        mEnableMaxAlert = typedArray.getBoolean(R.styleable.CircleArcView_enableMaxAlert,false);

        mEnableMinAlert = typedArray.getBoolean(R.styleable.CircleArcView_enableMinAlert,false);

        mEnableAnimal = typedArray.getBoolean(R.styleable.CircleArcView_enableAnimal,false);

        mAnimalDuration = typedArray.getFloat(R.styleable.CircleArcView_animalDuration, 50f);
        mReDrawTimes = (int) (mAnimalDuration/ANIMAL_SPEED);

        mRectF = new RectF();

        typedArray.recycle();

        mDrawCount = 0;

        mAnimalDegree = mArcStartDegree;

//        mArcSweepDegree = mArcStartDegree*mReDrawTimes + (mReDrawTimes*(mReDrawTimes - 1))/2 * mD;
        // 用等差数列前n项和公式，求出公差
        mD = (mArcSweepDegree - mArcStartDegree*mReDrawTimes)/((mReDrawTimes*(mReDrawTimes -1))/2);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if((mEnableMaxAlert && (mArcSweepDegree > mAlertMaxValue))
                || mEnableMinAlert && (mArcSweepDegree < mAlertMinValue)){
            mPaint.setColor(mAlertArcStrokeColor);
        }else {
            mPaint.setColor(mArcStrokeColor);
        }

        float gap = mArcStrokeWidth / 2;

        mRectF.set(0f + gap, 0f + gap, getWidth() - gap, getHeight()-gap);

        if(mEnableAnimal){

            if(mDrawCount < mReDrawTimes){

//                mAnimalDegree += mArcSweepDegree/mReDrawTimes;
                mDrawCount++;
                mAnimalDegree += (mDrawCount-1)*mD;

                canvas.drawArc(mRectF, mArcStartDegree, mAnimalDegree, false,mPaint);

                invalidate();
            }else{

                canvas.drawArc(mRectF, mArcStartDegree, mAnimalDegree, false,mPaint);
                mDrawCount = 0;
                mAnimalDegree = mArcStartDegree;
            }

        } else {
            canvas.drawArc(mRectF, mArcStartDegree, mArcSweepDegree, false,mPaint);
        }
    }

    /**
     * Start the animal of drawing
     */
    public void startAnimal(){
        startAnimal(mAnimalDuration);
    }

    /**
     * Start the animal of drawing
     * @param duration
     *              The duration of animal
     */
    public void startAnimal(float duration){
        mDrawCount = 0;

        // calculate the times of redraw
        mReDrawTimes = (int) (duration/ANIMAL_SPEED);

        mAnimalDegree = mArcStartDegree;
        // 用等差数列前n项和公式，求出公差
        mD = (mArcSweepDegree - mArcStartDegree*mReDrawTimes)/((mReDrawTimes*(mReDrawTimes -1))/2);
        invalidate();
    }

    public int getmArcStrokeColor() {
        return mArcStrokeColor;
    }

    public void setmArcStrokeColor(int mArcStrokeColor) {
        this.mArcStrokeColor = mArcStrokeColor;
    }

    public float getmArcStrokeWidth() {
        return mArcStrokeWidth;
    }

    public void setmArcStrokeWidth(float mArcStrokeWidth) {
        this.mArcStrokeWidth = mArcStrokeWidth;
    }

    public float getmArcStartDegree() {
        return mArcStartDegree;
    }

    public void setmArcStartDegree(float mArcStartDegree) {
        this.mArcStartDegree = mArcStartDegree;
    }

    public float getmArcSweepDegree() {
        return mArcSweepDegree;
    }

    public void setmArcSweepDegree(float mArcSweepDegree) {
        this.mArcSweepDegree = mArcSweepDegree;
    }

    public float getmAlertMinValue() {
        return mAlertMinValue;
    }

    public void setmAlertMinValue(float mAlertMinValue) {
        this.mAlertMinValue = mAlertMinValue;
    }

    public float getmAlertMaxValue() {
        return mAlertMaxValue;
    }

    public void setmAlertMaxValue(float mAlertMaxValue) {
        this.mAlertMaxValue = mAlertMaxValue;
    }

    public boolean ismEnableMaxAlert() {
        return mEnableMaxAlert;
    }

    public void setmEnableMaxAlert(boolean mEnableMaxAlert) {
        this.mEnableMaxAlert = mEnableMaxAlert;
    }

    public boolean ismEnableMinAlert() {
        return mEnableMinAlert;
    }

    public void setmEnableMinAlert(boolean mEnableMinAlert) {
        this.mEnableMinAlert = mEnableMinAlert;
    }

    public int getmAlertArcStrokeColor() {
        return mAlertArcStrokeColor;
    }

    public void setmAlertArcStrokeColor(int mAlertArcStrokeColor) {
        this.mAlertArcStrokeColor = mAlertArcStrokeColor;
    }

    public boolean ismEnableAnimal() {
        return mEnableAnimal;
    }

    public void setmEnableAnimal(boolean mEnableAnimal) {
        this.mEnableAnimal = mEnableAnimal;
    }

    public float getmAnimalDuration() {
        return mAnimalDuration;
    }

    public void setmAnimalDuration(float mAnimalDuration) {
        this.mAnimalDuration = mAnimalDuration;
    }

    public Paint.Cap getmCap() {
        return mCap;
    }

    public void setmCap(Paint.Cap mCap) {
        this.mCap = mCap;
        mPaint.setStrokeCap(mCap);
    }

    public Paint.Style getmPaintStyle() {
        return mPaintStyle;
    }

    public void setmPaintStyle(Paint.Style mPaintStyle) {
        this.mPaintStyle = mPaintStyle;
        mPaint.setStyle(mPaintStyle);
    }
}
