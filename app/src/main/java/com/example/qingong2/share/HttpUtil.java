package com.example.qingong2.share;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){//okhttp3.CallbackOkHttp库自带的一个回调接口
        OkHttpClient client = new OkHttpClient(); //创建OkHttpClient的实例
        Request request = new Request.Builder().url(address).build(); //建立一个request对象，通过url()方法设置目标的网络地址
        client.newCall(request).enqueue(callback); //开子线程、执行HTTP请求、请求结果回调到okhttp3.Callback
    }
}
