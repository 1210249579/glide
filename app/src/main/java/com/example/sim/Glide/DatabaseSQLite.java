package com.example.sim.Glide;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;

/**
 * 数据库创建
 * 充当二级缓存
 */
public class DatabaseSQLite extends SQLiteOpenHelper {
    public static final String CREATE_TABLE = "create table cache_table (" +
            "id integer primary key autoincrement," +
            "ke text unique," +
            "content blob" +
            ")";
    private Context context;
    public DatabaseSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Toast.makeText(context,"表创建成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
