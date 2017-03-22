package com.example.rarerare.minemap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by rarerare on 12/28/16.
 */

public class MapFragment extends Fragment {
    private TextView annotationTitle1;
    private TextView annotationTitle2;
    private TextView annotationTitle3;
    private TextView annotationTitle4;
    private Button hideAnnoButt;
    private AnnotationTNT[] annotationTitleNText;
    private MapView mMapView;
    private boolean annotationsDisplayed=false;

    private DisplayMetrics mDisplayMetrics;
    public static MapFragment newInstance(){
        return new MapFragment();
    }
   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       View v=inflater.inflate(R.layout.fragment_map,container,false);
       mDisplayMetrics=new DisplayMetrics();
       ((AppCompatActivity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
       /**annotationTitle1=(TextView)v.findViewById(R.id.annotation_text_1);
       annotationTitle2=(TextView)v.findViewById(R.id.annotation_text_2);
       annotationTitle3=(TextView)v.findViewById(R.id.annotation_text_3);
       annotationTitle4=(TextView)v.findViewById(R.id.annotation_text_4);*/
       annotationTitleNText=new AnnotationTNT[4];
       for (int i=0;i<4;i++){
           TextView annotationTitle=(TextView)v.findViewById(getResources().getIdentifier("annotation_title"+(i+1),"id",getContext().getPackageName()));
           TextView annotationText=(TextView)v.findViewById(getResources().getIdentifier("annotation_text_"+(i+1),"id",getContext().getPackageName()));
           //annotationTitle.setText();
           //annotationText.setText();
           annotationTitleNText[i]=new AnnotationTNT(annotationTitle,annotationText
                   ,getString(getResources().getIdentifier("annotation_title_"+(i+1)
                   ,"string",getContext().getPackageName())),getString(getResources().getIdentifier("annotation_text_"+(i+1)
                   ,"string",getContext().getPackageName())));

       }
       mMapView=(MapView)v.findViewById(R.id.mapview);
       /**annotationTitle1.setText(R.string.annotation_title_1);
       annotationTitle2.setText(R.string.annotation_title_2);
       annotationTitle3.setText(R.string.annotation_title_2);
       annotationTitle4.setText(R.string.annotation_title_4);*/
       //toggleAnnotations();
       hideAnnoButt=(Button) v.findViewById(R.id.hide_butt);
       hideAnnoButt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               closeAnnotations();
           }
       });
       return v;
   }
    public double getCanvWidth(){
        if (mMapView!=null){

            return mMapView.getWidth();
        }else {
            return ((MainActivity)getContext()).getCanvWidth();
        }
    }
    public double getCanvHeight(){
        if (mMapView!=null){
            return mMapView.getHeight();
        }else {
            return ((MainActivity)getContext()).getCanvHeight();
        }

    }
    public void displayAnnotations(){
        if (!annotationsDisplayed) {
            mMapView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , (int) (((MainActivity) getContext()).getCanvHeight() * 0.6)));
        }

    }
    public void closeAnnotations(){
        mMapView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , (int) (((MainActivity) getContext()).getCanvHeight() )));
    }

}
