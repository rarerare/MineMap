package com.example.rarerare.minemap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    private Display mDisplay;
    private DisplayMetrics mDisplayMetrics;
    private Fragment mFragment;




    private boolean annotationsShown=false;


    public Fragment createFragment(){
        return MapFragment.newInstance();


    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FragmentManager fm=getSupportFragmentManager();
        mFragment=fm.findFragmentById(R.id.fragment_container);
        if (mFragment==null){
            mFragment=createFragment();
            fm.beginTransaction().add(R.id.fragment_container,mFragment).commit();
        }
        mDisplayMetrics=new DisplayMetrics();
        mDisplay=getWindowManager().getDefaultDisplay();
        mDisplay.getMetrics(mDisplayMetrics);





    }

    public MapFragment getFragment() {
        return (MapFragment)mFragment;
    }
    public double getCanvWidth(){

            return mDisplayMetrics.widthPixels;

    }
    public double getCanvHeight(){

            return mDisplayMetrics.heightPixels;


    }
}
