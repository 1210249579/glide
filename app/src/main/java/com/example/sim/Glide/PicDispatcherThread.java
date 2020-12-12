package com.example.sim.Glide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sim.utils.CompressPic;
import com.example.sim.utils.CompressTwo;
import com.example.sim.utils.LruCacheUtils;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建请求队列中的每个线程
 */
public class PicDispatcherThread extends Thread {
    //内存缓存
    LruCacheUtils lruCacheUtils;
    //请求队列
    private LinkedBlockingQueue<bitmapRequest> queue;
    //拿到主线程
    private Handler handler = new Handler(Looper.getMainLooper());

    public PicDispatcherThread(LinkedBlockingQueue<bitmapRequest> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        super.run();
        //无限循环请求对象
        while (true) {
            try {
                bitmapRequest request = queue.take();
                //占位图片
                showDefaultPic(request);
                //下载图片
                Bitmap bitmap = loadBitmap(request);
                //加载图片
                showPic(request, bitmap);
            } catch (Exception e) {
                Log.e("thread:", "下载异常");
                e.printStackTrace();
            }

        }
    }

    /**
     * 加载图片到控件中
     *
     * @param request
     * @param bitmap
     */
    private void showPic(bitmapRequest request, final Bitmap bitmap) {
        final ImageView imageView = request.getImageView();
        //md5解决图片错位问题，给每个图片设置tag
        if (bitmap != null && request.getImageView() != null && request.getImageView().getTag().equals(request.getUrlMD5())) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });
        }
    }

    /**
     * 请求图片，使用三方框架
     * OKhttp
     *
     * @param request
     * @return
     */
    private Bitmap loadBitmap(bitmapRequest request) throws Exception {
        Bitmap bitmap = null;
        lruCacheUtils = LruCacheUtils.getInstance();
        //从缓存拿
        bitmap = (Bitmap) lruCacheUtils.get(request.getUrlMD5());
        if (bitmap != null) {
            Log.e("缓存", "从缓存中拿到");
            return bitmap;
        }
        //从数据库拿
        bitmap = DatabaseManager.get(request.getUrlMD5());
        if (bitmap != null) {
            Log.e("数据库", "从数据库中拿到");
            lruCacheUtils.put(request.getUrlMD5(), bitmap);
            return bitmap;
        }
        //下载图片
        bitmap = downLoadUrlPic(request.getURL(),request);
        if (bitmap != null) {
            //加入缓存中
            Cache_put(request, bitmap);
        }
        return bitmap;
    }

    private void Cache_put(bitmapRequest request, Bitmap bitmap) {
        //内存缓存
        lruCacheUtils.put(request.getUrlMD5(), bitmap);
        //数据库缓存-将bitmap转换为byte
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] bit = outputStream.toByteArray();
        DatabaseManager.put(request.getUrlMD5(), bit);
    }

    private Bitmap downLoadUrlPic(String URL,bitmapRequest requestBitmap) {
        Bitmap bitmap = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(URL).build();
        try {
            Response response = client.newCall(request).execute();
            Log.e("下载", "图片 "+requestBitmap.getURL()+"下载成功");
            //bitmap = CompressTwo.simaplePic(BitmapFactory.decodeStream(response.body().byteStream()));
            bitmap = CompressPic.decodeBitmap(response.body().bytes(), 100, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void showDefaultPic(bitmapRequest request) {
        //切回主线程
        if (request.getResId() > 0 && request.getImageView() != null) {
            final int res = request.getResId();
            final ImageView imageView = request.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(res);
                }
            });
        }
    }
}
