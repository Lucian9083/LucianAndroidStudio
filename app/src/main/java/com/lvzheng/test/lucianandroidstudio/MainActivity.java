package com.lvzheng.test.lucianandroidstudio;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.lvzheng.test.lucianandroidstudio.customwidgets.CircleArcView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    public static final String TAG = MainActivity.class.getSimpleName();

    private EditText mEditText;

    private CircleArcView mCircleArcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){

        findViewById(R.id.button).setOnClickListener(this);

        mEditText = (EditText)findViewById(R.id.edit_tx);

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
        }
    }

    private void restartViewAnimal(){
        float duration = 500f;

        try {
            duration = Float.parseFloat(mEditText.getText().toString());
        } catch (NumberFormatException e) {
        }

        mCircleArcView.startAnimal(duration);
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

}
