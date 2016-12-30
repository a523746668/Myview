package com.example.a52374.myapplicationopen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by 52374 on 2016/12/30.
 */

//图片自动滚动
public class Photoscroll extends View {


     //每张图片的宽高标准
      private int bitmapw=150;
      private int bitmaph=150;


   private int index=0;//主要用于控制图片的循环播放，与I配合

    //画的第一张图片从屏幕的offert位置（canvas.drawbitmap(第二个参数）开始画
    private int offert=0;

     //图片播放的速度
    private  int speed=2;
    //设置是否播放
    private boolean flag=true;
      private int[] bitmapu=new int[]{R.mipmap.aa,R.mipmap.bb,R.mipmap.cc,R.mipmap.dd,R.mipmap.ee,R.mipmap.ff,R.mipmap.gg,R.mipmap.hh};

    //数据源
    private ArrayList<Bitmap> list=new ArrayList<>(bitmapu.length);


    public Photoscroll(Context context) {
        super(context);
        init();
    }
    //  初始化数据源
     private void init() {
         for(int i:bitmapu){
             Bitmap bitmap= BitmapFactory.decodeResource(getResources(),i);
             list.add(makephoto(bitmap));

         }

    }

    public Photoscroll(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       int width=getWidth(); //获得View的宽度
       int bw=list.get(0).getWidth();
        //当最左边的图片从屏幕完全消失,从新的最左边的图片开始画
         if(offert<=-bw){
             offert+=list.get(0).getWidth();
             index++;
         }
        float left=offert;


        //当屏幕还有剩余空间，填满
        for(int i=0;left<=width;i++){

            Bitmap b=list.get((i+index)%list.size());
            canvas.drawBitmap(b,left,0,null);
            left+=bw;


        }

        //让最左边的图片以speed的速度不停向左边移动
        if(flag){
            offert-=speed;
            postInvalidate();
        }

    }


    //将每张图片缩放到150*150的宽高
    private Bitmap makephoto(Bitmap bitmap){
       Bitmap bit=null;
        bit=Bitmap.createScaledBitmap(bitmap,bitmapw,bitmaph,true);
        return  bit;
    }



    //提供接口控制开始自动播放 和停止自动播放
    public  void start(){
        if(!flag){
            flag=true;
            postInvalidate();
        }
    }

    public void stop(){
        if(flag){
            flag=false;
            invalidate();
        }
    }



}
