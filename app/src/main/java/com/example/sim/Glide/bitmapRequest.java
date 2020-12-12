package com.example.sim.Glide;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.example.sim.MD5Tool;
import com.example.sim.utils.LruCacheUtils;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

public class bitmapRequest {
    //上下文
    private Context context;
    //图片URL
    private String URL;
    //显示图片的控件
    private SoftReference<ImageView> imageView;
    //回调接口
    private RequestListener listener;
    //请求访问标志
    private String urlMD5;
    //占位图
    private int ResId;
    public static ArrayList<String> md5 = new ArrayList<>();
    public bitmapRequest(Context context) {
        this.context = context;
        //创建缓存图片的数据库
        DatabaseManager.getInstance(context);
    }
    public bitmapRequest load(String URL){
        this.URL = URL;
        this.urlMD5 = MD5Tool.getMD5(URL);
        md5.add(urlMD5);
        return this;
    }
    public void removeToSqlCache(){
        DatabaseManager.remove();
    }
    public bitmapRequest loadError(int redId){
        this.ResId = redId;
        return this;
    }
    public bitmapRequest listener(RequestListener listener){
        this.listener = listener;
        return this;
    }

    /**
     * 当执行到最后一步时，拿到RequestManager
     * 把请求加入请求队列
     * @param imageView
     */
    public void into(ImageView imageView){
        imageView.setTag(this.urlMD5);
        this.imageView = new SoftReference<>(imageView);
        RequestManager.getInstance().addRequest(this);
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public ImageView getImageView() {//软引用
        return imageView.get();
    }

    public void setImageView(SoftReference<ImageView> imageView) {
        this.imageView = imageView;
    }

    public RequestListener getListener() {
        return listener;
    }

    public void setListener(RequestListener listener) {
        this.listener = listener;
    }

    public String getUrlMD5() {
        return urlMD5;
    }

    public void setUrlMD5(String urlMD5) {
        this.urlMD5 = urlMD5;
    }

    public int getResId() {
        return ResId;
    }

    public void setResId(int resId) {
        ResId = resId;
    }
}
