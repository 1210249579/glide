package com.example.sim.bean;

import android.graphics.Bitmap;

public class picBean {
  private int pic;
  private String url;

    public picBean(String url,int pic) {
        this.url = url;
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
