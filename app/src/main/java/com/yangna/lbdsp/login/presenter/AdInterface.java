package com.yangna.lbdsp.login.presenter;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yc on 2018/8/19.
 */

public interface AdInterface {
//    @GET("version")
//    Call<List<Advertisement>> getAdMsg();
    @GET("dsapi/{fenxiang_img}")
    Call<ResponseBody> getForm(@Path("fenxiang_img") String fenxiang_img);
}
