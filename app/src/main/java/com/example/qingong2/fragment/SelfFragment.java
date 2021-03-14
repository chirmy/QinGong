package com.example.qingong2.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.qingong2.LocationActivity;
import com.example.qingong2.R;
import com.example.qingong2.share.BingPic;
import com.example.qingong2.share.HttpUtil;
import com.example.qingong2.signin.SignInActivity;
import com.google.gson.Gson;
import com.leon.lib.settingview.LSettingItem;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.view.View.GONE;

public class SelfFragment extends Fragment  {

    private static final String TAG = "SelfFrament";

    private ImageView bingPicImg;

    private ProgressBar progressBar ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.self_fragment,container,false);

        progressBar = view.findViewById(R.id.self_progress_bar);

        //网络图片请求 初始化各控件
        bingPicImg = view.findViewById(R.id.share_imgView);
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
//        String bingPicURL = prefs.getString("bing_pic",null);
//        if(bingPicURL!=null){
//            Glide.with(getActivity()).load(bingPicURL).into(bingPicImg);
//        }else {
//            loadBingPic();
//        }
        progressBar.setVisibility(View.VISIBLE);
        loadBingPic();



        RequestListener mRequestListener = new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                Log.d(TAG, "onException: " + e.toString()+"  model:"+model+" isFirstResource: "+isFirstResource);
                bingPicImg.setImageResource(R.mipmap.ic_launcher);
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                Log.e(TAG,  "model:"+model+" isFirstResource: "+isFirstResource);
                return false;
            }


        };

        //
        //对一个控件进行点击事件
        LSettingItem daKa =view.findViewById(R.id.self_sign_item);

        daKa.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                //Toast.makeText(view.getContext(),"clicked 资料",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
            }
        });




        return view;
    }

    public void loadBingPic(){
        String requestBingpic = "https://api.xygeng.cn/Bing/url/";
        HttpUtil.sendOkHttpRequest(requestBingpic, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String Json = response.body().string();//获取必应背景图的链接
                        final String bingPic = parseJSONWithGSON(Json);//从Json中获取url
                       /* SharedPreferences.Editor editor= (SharedPreferences.Editor) PreferenceManager.getDefaultSharedPreferences(getContext());
                        editor.putString("bing_pic",bingPic);
                        editor.apply();*/


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Glide.with(getActivity()).load(bingPic).into(bingPicImg);
                                progressBar.setVisibility(GONE);
                            }
                        });
                    }
                }

        );
    }

    private String parseJSONWithGSON(String jsonData){
        Gson gson = new Gson();
        String  bingPic = gson.fromJson(jsonData, BingPic.class).getData();
        if (bingPic.startsWith("http://")) {
            bingPic = bingPic.replace("http://", "https://");
        }
        return bingPic;
    }

}
