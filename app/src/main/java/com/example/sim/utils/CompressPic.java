package com.example.sim.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.util.stream.Stream;

import okhttp3.Response;

public class CompressPic {
    /**
     * 图片缩小工具类
     * @param stream 图片的字节流
     * @param reqWidth 要缩小的宽
     * @param reqHeight 要缩小的高
     * @return
     */
    public static Bitmap decodeBitmap(byte[] stream, int reqWidth, int reqHeight){
        int inPictureHeight;
        int inPictureWidth;
        int inSample = 1;
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 设置为true时，bitmap = null，不加载进内存，但可以得到图片的宽高
        //只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
        options.inJustDecodeBounds = true;
        //byte, byte.length, options
        BitmapFactory.decodeByteArray(stream, 0,stream.length, options);
        //获取图片资源的宽高
        inPictureHeight = options.outHeight;
        inPictureWidth = options.outWidth;
        //图片缩小算法
        BitmapFactory.Options resultOption = calculationWidthAndHeight(options,inPictureHeight,inPictureWidth,reqWidth,reqHeight,inSample);
        return BitmapFactory.decodeByteArray(stream,0,stream.length,resultOption);

    }

    private static BitmapFactory.Options calculationWidthAndHeight(BitmapFactory.Options options, int inPictureHeight, int inPictureWidth,
                                                                   int reqWidth, int reqHeight, int inSample) {
        //更改原始像素为reqWidth，reqHeight比例,如果原始像素比自定义像素要大，则跳过此步骤
        if (inPictureWidth > reqWidth || inPictureHeight >reqHeight){
            while (inPictureWidth / inSample > reqWidth || inPictureHeight / inSample >reqHeight){
                //要求取值为n的2次冥，不是二次幂则四舍五入到最近的二次幂
                inSample = inSample *2;
            }
        }
        //得到的最终值
        options.inSampleSize = inSample;
        //设置编码为RGB_565,默认为ARGB_8888
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //设置为false，得到实际的bitmap，不然只会得到宽高等信息
        options.inJustDecodeBounds = false;
        //设置图片可以复用
        options.inMutable = true;
        return options;
    }
}
