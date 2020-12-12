package com.example.sim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sim.Glide.Glide;
import com.example.sim.Glide.bitmapRequest;
import com.example.sim.adapter.recyclerAdapter;
import com.example.sim.bean.picBean;
import com.example.sim.utils.LruCacheUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    public static String tag[];
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private List<picBean> picBeans = new ArrayList<>();
    private picBean picBean[] = {
            new picBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607537894426&di=e03f96a69cbc2feac9f83e9712ed3d92&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F64%2F52%2F01300000407527124482522224765.jpg",R.mipmap.ic_launcher),
            new picBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607537894426&di=5edd3afcb9299f2e1d88e670de6e69b0&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F70%2F91%2F01300000261284122542917592865.jpg",R.mipmap.ic_launcher),
            new picBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607537894426&di=399e3f5ea2486e79eb17228be60b927f&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F30%2F29%2F01300000201438121627296084016.jpg",R.mipmap.ic_launcher),
            new picBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607537894424&di=82a605abcc096d0113808a7a1ca46bee&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F10%2F01%2F01300000091985121196015965747.jpg",R.mipmap.ic_launcher),
            new picBean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607537975204&di=3993a659ec460c682885ce23a81e336b&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F47%2F01200000033870124166473043277.jpg",R.mipmap.ic_launcher),
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        byid();
        adapter();
    }

    private void adapter() {
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerAdapter adapter = new recyclerAdapter(data(),this);
        recyclerView.setAdapter(adapter);
    }

    private List<picBean> data() {
        picBeans.clear();
        int count = picBean.length;
        for (int i =0;i<count;i++){
            picBeans.add(picBean[i]);
        }
        return picBeans;
    }

    private void byid() {
        refreshLayout = findViewById(R.id.refresh);
        recyclerView = findViewById(R.id.recyclerView);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    public void clearCache(View view) {
        ArrayList<String> key = bitmapRequest.md5;
        for (String value : key){
            LruCacheUtils.lruCacheUtils.remove(value);
        }
    }

    public void clearSQL(View view) {
        Glide.with(this).removeToSqlCache();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}