package com.example.sim.Glide;

import android.content.Context;

public class Glide {
    public static bitmapRequest with(Context context){
        return new bitmapRequest(context);
    }
}
