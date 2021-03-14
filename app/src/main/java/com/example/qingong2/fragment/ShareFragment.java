package com.example.qingong2.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.qingong2.R;
import com.example.qingong2.share.Share;
import com.example.qingong2.share.ShareAdapter;
import com.example.qingong2.share.Share;
import com.example.qingong2.share.ShareAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class ShareFragment extends Fragment {

    private static final String TAG = "ShareFrament";

    List<Share> shareList = new ArrayList<>();

    private SwipeRefreshLayout swipeRefreshLayout;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.share_fragment,container,false);

        initData();
        //RecycerView
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.share_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());//设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);
        final ShareAdapter adapter = new ShareAdapter(shareList);//设置recyclerView适配器
        recyclerView.setAdapter(adapter);

        // SwipeRefreshLayout
        swipeRefreshLayout=view.findViewById(R.id.share_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);//设置下拉进度条颜色
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//设置监听器
            @Override
            public void onRefresh() {//内部为刷新逻辑（一般是网络上请求数据然后将这些数据展示出来）
                new Thread(new Runnable() {//开启一个线程
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(2000);//因为本地刷新太快，所以设置沉睡
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        getActivity().runOnUiThread(new Runnable() {//切回主线程
                            @Override
                            public void run() {
                                initData();
                                adapter.notifyDataSetChanged();//通知数据发生了变化
                                swipeRefreshLayout.setRefreshing(false);//刷新事件结束
                            }
                        });
                    }
                }).start();

            }
        });


        return view;
    }


    private void initData(){

        SimpleDateFormat   formatter   =   new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        String   time   =   formatter.format(curDate);
        Random random = new Random();
        for(int i = 0; i < random.nextInt(5)+1; i++){
            Share share = new Share(R.drawable.icon_image,"chirmy",time,getRandomLengthContent("中北保洁欢迎您! "),R.drawable.bing);
            shareList.add(share);
        }
    }

    private  String getRandomLengthContent(String content){
        Random random=new Random();
        int length=random.nextInt(20)+1;
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<length;i++){
            builder.append(content);
        }
        return builder.toString();
    }






}
