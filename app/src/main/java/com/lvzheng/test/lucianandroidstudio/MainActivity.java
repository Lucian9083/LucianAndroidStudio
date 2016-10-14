package com.lvzheng.test.lucianandroidstudio;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.lvzheng.test.lucianandroidstudio.customwidgets.CircleArcView;

import java.util.HashMap;


public class MainActivity extends Activity implements View.OnClickListener{
    public static final String TAG = MainActivity.class.getSimpleName();

    private EditText mEditText,mDegree;

    private CircleArcView mCircleArcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){

        findViewById(R.id.button).setOnClickListener(this);

        findViewById(R.id.button_speed).setOnClickListener(this);

        mEditText = (EditText)findViewById(R.id.edit_tx);

        mDegree = (EditText)findViewById(R.id.edit_tx_speed);

        mCircleArcView = (CircleArcView)findViewById(R.id.circle_arc);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                restartViewAnimal();
                break;
            case R.id.button_speed:

                productSpeed();
                break;
        }
    }

    private void restartViewAnimal(){
        float duration = 500f;

        float degree = 10f;

        try {
            duration = Float.parseFloat(mEditText.getText().toString());

        } catch (NumberFormatException e) {
        }
        try {
            degree = Float.parseFloat(mDegree.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        mCircleArcView.setmArcSweepDegree(degree);
        mCircleArcView.startAnimal(duration);
    }


    private void productSpeed(){
        final float[] speeds = new float[]{10,200,150,100,50,0,25,30,45,60,75,85,100,
                150,180,255,220,190,120,60,30,10,110,160,30};
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(final float s : speeds){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mCircleArcView.setmArcSweepDegree(s);
                            mCircleArcView.startAnimal();
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        Looper.prepareMainLooper();
        final HashMap testMap = new HashMap();
        testMap.put("","");
        testMap.get("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    /**
     *
     */
    public void test(){

    }

}
