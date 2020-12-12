package com.example.sim.Glide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

/**
 * 数据库CRUD
 */
public class DatabaseManager {
    private static DatabaseManager manager;
    private static SQLiteDatabase db;
    private static String DatabaseName = "cache.db";

    /**
     * 单例模式
     * @param context
     * @return
     */
    public static DatabaseManager getInstance(Context context) {
        if (manager == null) {
            manager = new DatabaseManager(context);
        }
        return manager;
    }

    /**
     * getWritableDatabase()：它以读写的方式去打开数据库，当数据库的磁盘空间满了时，就只能读不能写。
     * getReadableDatabase()：它内部也调用了getWritableDatabase(),以读写的方式打开，如果数据库磁盘空间满了，则打开失败。
     * 打开失败后，如果继续尝试对数据库的读，则会读取数据，不能写
     *
     * @param context
     */
    private DatabaseManager(Context context) {
        DatabaseSQLite sqlIte = new DatabaseSQLite(context, DatabaseName, null, 1);
        db = sqlIte.getWritableDatabase();
    }

    /**
     * 插入数据进数据库
     * @param md5Key 图片的key
     * @param value 图片value
     */
    public static void put(String md5Key, byte[] value) {
        ContentValues values = new ContentValues();
        values.put("ke", md5Key);//md5
        values.put("content", value);//图片资源
        db.insert("cache_table", null, values);
    }

    /**
     * 读取数据库数据
     * @param key 图片key
     * @return 返回Bitmap
     */
    public static Bitmap get(String key) {
        String md5Key;//存放图片key
        byte[] PicContent = null;//存放图片二进制
        Bitmap resultPic = null;//返回图片
        Cursor query = db.query(true, "cache_table", null,
                null, null, null, null, null, null);
        if (query.moveToFirst()) {
            do {
                //查询到当前图片的key时
                md5Key = query.getString(query.getColumnIndex("ke"));
                //一个key，对应一个value，当查询到了就break退出
                if (key.equals(md5Key)){
                    //拿到图片的value
                    PicContent = query.getBlob(query.getColumnIndex("content"));
                    break;
                }
            } while (query.moveToNext());
        }
        //如果数据为null时则不进行转换
        if (PicContent != null) {
            resultPic = BitmapFactory.decodeByteArray(PicContent, 0, PicContent.length);
        }
        query.close();
        return resultPic;
    }

    /**
     * 删除数据库缓存
     */
    public static void remove() {
        try {
            db.delete("cache_table", null,null);
            Log.e("delete", "删除成功");
        } catch (Exception e) {
            Log.e("delete", "数据库暂无数据");
        }

    }
}
