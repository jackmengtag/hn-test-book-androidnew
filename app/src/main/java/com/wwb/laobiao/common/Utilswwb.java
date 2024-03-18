package com.wwb.laobiao.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utilswwb {
    private Utilswwbinterface utilswwbinterface;

    public static Bitmap getBitmapx(String path) throws IOException {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        RequestOptions options = new RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher);//两张固定图片而已
//        Glide.with(Objects.requireNonNull(getActivity())).load(model.getBody().getAccount().getLogo()).apply(options).into(roundedImageView);//为了加载网络图片的两步方法


        return null;
    }

    //设置网络图片
    public  void setImageURL(final String path) {
        //开启一个线程用于联网
        new Thread() {
            @Override
            public void run() {
                try {
                    //把传过来的路径转成URL
                    URL url = new URL(path);
                    //获取连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //使用GET方法访问网络
                    connection.setRequestMethod("GET");
                    //超时时间为10秒
                    connection.setConnectTimeout(10000);
                    //获取返回码
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = connection.getInputStream();
                        //使用工厂把网络的输入流生产Bitmap
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        //利用Message把图片发给Handler
//                        Message msg = Message.obtain();
//                        msg.obj = bitmap;
//                        msg.what = GET_DATA_SUCCESS;
//                        handler.sendMessage(msg); postBitmap
//                         Rinker.getInstance().postBitmap(bitmap);
                         if(utilswwbinterface!=null)
                         {
                             utilswwbinterface.setback(bitmap);
                         }
                         inputStream.close();
                    }else {
                        //服务启发生错误
//                        handler.sendEmptyMessage(SERVER_ERROR);
                        if(utilswwbinterface!=null)
                        {
                            utilswwbinterface.setback(null);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //网络连接错误
//                    handler.sendEmptyMessage(NETWORK_ERROR);
                    if(utilswwbinterface!=null)
                    {
                        utilswwbinterface.setback(null);
                    }
                }
            }
        }.start();
    }

    public void setcb(Utilswwbinterface utilswwbinterface) {
        this.utilswwbinterface=utilswwbinterface;
    }

    public interface Utilswwbinterface {
        void setback(Bitmap bitmap);
    }
}
