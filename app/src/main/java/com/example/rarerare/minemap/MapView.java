package com.example.rarerare.minemap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by rarerare on 12/28/16.
 */

public class MapView extends View {
    private Bitmap bitmap1;
    private Bitmap[][] bitmap2;
    private int bitmap1Id;
    private int[][] bitmap2Ids;
    private double x1=0,y1=0;
    private double width1,height1;
    private double mapWidth,mapHeight;
    private double side2;
    
    private MapFragment mMapFragment;

    private ArrayList<MPointer> pointers;
    private static final String TAG_VIEW_DEBUG="VIEW_DEBUG";
    private double O_WIDTH, O_HEIGHT;
    private double scale=1;

    private Rect rect1;
    private Rect[][] rect2;

    public MapView (Context context, AttributeSet attrs){
        super(context,attrs);
        bitmap1Id=getResources().getIdentifier("img1","drawable",context.getPackageName());
        bitmap1=BitmapFactory.decodeResource(getResources(),bitmap1Id);
        //bitmap1= BitmapFactory.decodeResource(getResources(),R.drawable.img1);
        bitmap2=new Bitmap[2][4];
        bitmap2Ids=new int[2][4];
        for (int i=0;i<2;i++){
            for(int j=0;j<4;j++){

                bitmap2Ids[i][j]=getResources().getIdentifier("x"+(i+1)+"y"+(j+1),"drawable",context.getPackageName());
                //bitmap2[i][j]=BitmapFactory.decodeResource(getResources(),id);
            }
        }
        width1=bitmap1.getWidth();
        height1=bitmap1.getHeight();
        mapWidth=width1;
        mapHeight=height1;
        O_WIDTH=width1;
        O_HEIGHT=height1;

        rect1=new Rect(0,0,(int)width1,(int)height1);
        pointers=new ArrayList<MPointer>();

        mMapFragment=((MainActivity)context).getFragment();
        if (mMapFragment.getCanvWidth()<width1&&mMapFragment.getCanvHeight()<height1){
            scale=Math.max((mMapFragment.getCanvWidth()/width1),(mMapFragment.getCanvHeight()/height1));

        }

        Log.i(TAG_VIEW_DEBUG,"width"+String.valueOf(mMapFragment.getCanvWidth())+"height"+String.valueOf(mMapFragment.getCanvHeight()));
        Log.i(TAG_VIEW_DEBUG,"width1"+String.valueOf(width1)+"height1"+String.valueOf(height1));
        rect2=new Rect[2][4];
        for (int i=0;i<2;i++){
            for (int j=0;j<4;j++){
                rect2[i][j]=new Rect();
            }
        }

        invalidate();
    }
    public MapView(Context context){
        this(context,null);
    }
    @Override
    public void onDraw(Canvas canvas){
        if (scale<=1){
            drawFirstLayer(canvas);
        }else {
            drawSecondLayer(canvas);
        }


    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        double onMapX=((event.getX()-x1)/scale)/mapWidth;
        double onMapY=((event.getY()-y1)/scale)/mapHeight;
        if (onMapX>0.4897&&onMapX<0.5582&&onMapY>0.065&&onMapY<0.1402){
            mMapFragment.displayAnnotations();
        }
        switch (event.getAction()&MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                handleDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                handleMove(event);
                break;
            case MotionEvent.ACTION_UP:
                handleUp(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                handlePointerDown(event);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                handlePointerup(event);
                break;


        }
        return true;
    }
    private void handleMove(MotionEvent event){
        if ((event.getPointerCount()==1)&&pointers.size()==1){

            double oldX=pointers.get(0).getX();
            double oldY=pointers.get(0).getY();
            double newX=event.getX();
            double newY=event.getY();
            double sX1=x1+(newX-oldX);
            double sY1=y1+(newY-oldY);
            if ((sX1<=0)&&(sX1>=(mMapFragment.getCanvWidth()-width1))){
                x1+=(newX-oldX);
            }else if (sX1>0){
                x1=0;
            }else if (sX1<(mMapFragment.getCanvWidth()-width1)){
                x1=mMapFragment.getCanvWidth()-width1;
            }

            if ((sY1<=0)&&(sY1>=(mMapFragment.getCanvHeight()-height1))){
                y1+=(newY-oldY);
            }else if (sY1>0){
                y1=0;
            }else if(sY1<(mMapFragment.getCanvHeight()-height1)){
                y1=mMapFragment.getCanvHeight()-height1;
            }

            pointers.get(0).setX(newX);
            pointers.get(0).setY(newY);
            invalidate();
        }else if (event.getPointerCount()==2){
            if ((pointers.size()<2)||((event.getPointerId(1))!=pointers.get(1).getId())||((event.getPointerId(0))!=pointers.get(0).getId())){
                pointers.clear();

                pointers.add(new MPointer(event.getPointerId(0),event.getX(0),event.getY(0)));
                pointers.add(new MPointer(event.getPointerId(1),event.getX(1),event.getY(1)));

            }
            double oldX1=pointers.get(0).getX();
            double oldX2=pointers.get(1).getX();
            double oldY1=pointers.get(0).getY();
            double oldY2=pointers.get(1).getY();
            double newX1=event.getX(0);
            double newX2=event.getX(1);
            double newY1=event.getY(0);
            double newY2=event.getY(1);
            double sScale=(Math.sqrt((newX1-newX2)*(newX1-newX2)+(newY1-newY2)*(newY1-newY2)))/
                    (Math.sqrt((oldX1-oldX2)*(oldX1-oldX2)+(oldY1-oldY2)*(oldY1-oldY2)));

            double sW1=O_WIDTH*scale*sScale;
            double sH1=O_HEIGHT*scale*sScale;
            double sX1=x1*sScale-mMapFragment.getCanvWidth()*(sScale-1)*0.5;
            double sY1=y1*sScale-mMapFragment.getCanvHeight()*(sScale-1)*0.5;

            Log.i(TAG_VIEW_DEBUG,"mMapFragment.getCanvWidth():"+String.valueOf(mMapFragment.getCanvWidth())+"mMapFragment.getCanvHeight()"+String.valueOf(mMapFragment.getCanvHeight()));
            Log.i(TAG_VIEW_DEBUG,"sW1:"+sW1+"sH1"+sH1);
            if (sW1>=mMapFragment.getCanvWidth()&&sH1>=mMapFragment.getCanvHeight()){
                scale=scale*sScale;
                if (sX1>=(mMapFragment.getCanvWidth()-sW1)&&(sX1<=0)){
                    x1=x1*sScale-mMapFragment.getCanvWidth()*(sScale-1)*0.5;


                }else if (sX1<=0){
                    x1=mMapFragment.getCanvWidth()-sW1;
                }else {
                    x1=0;
                }


                if (sY1>=(mMapFragment.getCanvHeight()-sH1)&&(sY1<=0)){
                    y1=y1*sScale-mMapFragment.getCanvHeight()*(sScale-1)*0.5;
                }else if (sY1<=0){
                    y1=mMapFragment.getCanvHeight()-sH1;
                }else {
                    sY1=0;
                }
            }

            /**
            width1=(int)(width1*scale);
            height1=(int)(height1*scale);
             */
            (pointers.get(0)).setX(newX1);
            (pointers.get(0)).setY(newY1);
            (pointers.get(1)).setX(newX2);
            (pointers.get(1)).setY(newY2);
            invalidate();


        }
        //Log.i(TAG_VIEW_DEBUG,"move"+"index"+String.valueOf(event.getActionIndex())+"count"+String.valueOf(event.getPointerCount()));
        if (event.getPointerCount()==2){
           // Log.i(TAG_VIEW_DEBUG,"id1"+String.valueOf(event.getPointerId(0))+"id2"+event.getPointerId(1));
        }

    }

    private void handleDown(MotionEvent event){
        pointers.add(new MPointer(event.getPointerId(event.getActionIndex()),event.getX(event.getActionIndex()),event.getY(event.getActionIndex())));
        //Log.i(TAG_VIEW_DEBUG,"down"+"index"+String.valueOf(event.getActionIndex())+"count"+String.valueOf(event.getPointerCount()));
    }
    private void handleUp(MotionEvent event){
        pointers.clear();
        //Log.i(TAG_VIEW_DEBUG,"up"+"index"+String.valueOf(event.getActionIndex())+"count"+String.valueOf(event.getPointerCount()));
    }
    private void handlePointerDown(MotionEvent event){

        //Log.i(TAG_VIEW_DEBUG,"pointer down"+"index"+String.valueOf(MotionEventCompat.getActionIndex(event))+"count"+String.valueOf(event.getPointerCount()));
    }
    private void handlePointerup(MotionEvent event){
        if (pointers.size()==2){
            if (pointers.get(0).getId()==event.getActionIndex()){
                pointers.remove(0);
            }else {
                pointers.remove(1);
            }
        }

        //Log.i(TAG_VIEW_DEBUG,"pointer up"+"index"+String.valueOf(MotionEventCompat.getActionIndex(event))+"count"+String.valueOf(event.getPointerCount()));
    }
    private void drawFirstLayer(Canvas canvas){
        for (int i=0;i<2;i++){
            for (int j=0;j<4;j++){
                if (bitmap2[i][j]!=null){
                    bitmap2[i][j]=null;
                }
            }
        }
        if (bitmap1==null){
            //int id1=getResources().getIdentifier("img1","drawable",context.getPackageName());
            bitmap1=BitmapFactory.decodeResource(getResources(),bitmap1Id);
        }
        width1=scale*O_WIDTH;
        height1=scale*O_HEIGHT;

        rect1.set((int)x1,(int)y1,(int)(x1+width1),(int)(y1+height1));
        canvas.drawBitmap(bitmap1,null,rect1,null);
    }
    private void drawSecondLayer(Canvas canvas){
        bitmap1=null;
        width1=scale*O_WIDTH;
        height1=scale*O_HEIGHT;
        side2=scale*O_WIDTH*0.5;

        for (int i=0;i<2;i++){
            for (int j=0;j<4;j++){
                if (((x1+i*side2)>mMapFragment.getCanvWidth())||((x1+i*side2+side2)<0)||((y1+j*side2)>mMapFragment.getCanvHeight())||((y1+j*side2+side2)<0)){
                    bitmap2[i][j]=null;
                }else {

                    rect2[i][j].set((int)(x1+i*side2),(int)(y1+j*side2),(int)(x1+i*side2+side2),(int)(y1+j*side2+side2));
                    if (bitmap2[i][j]==null){
                        bitmap2[i][j]=BitmapFactory.decodeResource(getResources(),bitmap2Ids[i][j]);
                    }
                    canvas.drawBitmap(bitmap2[i][j],null,rect2[i][j],null);
                }
            }
        }
    }
}
