package com.example.sim.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 这种压缩方法之所以称之为质量压缩，是因为它不会减少图片的像素。
 * 它是在保持像素的前提下改变图片的位深及透明度等，来达到压缩图片的目的。
 * 进过它压缩的图片文件大小会有改变，但是导入成bitmap后占得内存是不变的。
 * 因为要保持像素不变，所以它就无法无限压缩，到达一个值之后就不会继续变小了。
 * 显然这个方法并不适用与缩略图，其实也不适用于想通过压缩图片减少内存的适用，仅仅适用于想在保证图片质量的同时减少文件大小的情况而已。
 */
public class CompressTwo {
    public static Bitmap simaplePic(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,stream);
        stream.close();
        return bitmap;
    }
}
