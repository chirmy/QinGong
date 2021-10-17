package com.example.qingong2.News;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qingong2.R;

public class NewsContentFragment extends Fragment {

    private View view;

             @Nullable
            @Override
             //加载news_content_frag布局
     public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                 view=inflater.inflate(R.layout.news_content_frags,container,false);
                 return view;
             }
     public void refresh(String newsTitle,String newsContent){
                 View visibilityLayout=view.findViewById(R.id.visibility_layout);//获取新闻布局
                 visibilityLayout.setVisibility(View.VISIBLE);//将新闻布局设置成可见
                 TextView newsTitleText=(TextView)view.findViewById(R.id.news_title);//获取新闻标题的控件
                 TextView newsContentText=(TextView)view.findViewById(R.id.news_content);//获取新闻内容的控件
                 newsTitleText.setText(newsTitle); //刷新新闻的标题
                 newsContentText.setText(newsContent);//刷新新闻的内容
             }
}
