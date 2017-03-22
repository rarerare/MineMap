package com.example.rarerare.minemap;

import android.view.View;
import android.widget.TextView;

/**
 * Created by rarerare on 1/5/17.
 */
public class AnnotationTNT {
    private TextView titleView;
    private TextView annotationView;
    private boolean opened=false;
    private String title;
    private String annotation;


    public AnnotationTNT(TextView titleView,TextView annotationView,String title,String annotation) {
        this.titleView=titleView;
        this.annotationView=annotationView;
        this.title=title;
        this.annotation=annotation;
        titleView.setText(title);
        titleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleText();
            }
        });
    }

    public TextView getTitleView() {
        return titleView;
    }

    public void setTitleView(TextView titleView) {
        this.titleView = titleView;
    }

    public TextView getAnnotationView() {
        return annotationView;
    }

    public void setAnnotationView(TextView annotationView) {
        this.annotationView = annotationView;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }
    public void toggleText(){
        if (isOpened()==false){
            annotationView.setText(annotation);
            opened=true;
        }else {
            annotationView.setText(null);
            opened=false;
        }
    }
}
