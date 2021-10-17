/*
package com.example.qingong.News;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qingong.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
           */
/*    * 展示新闻列表
    *//*
public class NewsTitleFragment extends Fragment {
      private boolean isTwopane;//判断是否显示双页
               @Nullable
     @Override
      public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                 View view=inflater.inflate(R.layout.news_title_frag,container,false);
                         return view;
             }
      @Override
      public void onActivityCreated(@Nullable Bundle savedInstanceState) {
                 super.onActivityCreated(savedInstanceState);
                 if(getActivity().findViewById(R.id.news_content_fragment)!=null){
                         isTwopane=true;//可以找到news_content_layout布局时，为双页模式
                     }else{
                         isTwopane=false;//找不到news_content_layout布局时，为单页模式
                     }
             }




     }
*/
package com.example.qingong2.News;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qingong2.R;
import com.example.qingong2.NewsContentActivity;

import com.example.qingong2.NewsContentActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 展示新闻列表
 */
public class NewsTitleFragment extends Fragment {
//    private boolean isTwopane;//判断是否显示双页

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.news_title_frag,container,false);
        RecyclerView newsTitleRecyclerView=(RecyclerView)view.findViewById(R.id.news_title_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager((getActivity()));
        newsTitleRecyclerView.setLayoutManager(layoutManager);
        NewsAdapter adapter=new NewsAdapter(getNews());
        newsTitleRecyclerView.setAdapter(adapter);


        return view;
    }

    private List<News> getNews() {
        List<News> newsList=new ArrayList<>();
        for(int i=0;i<=50;i++){
            News news=new News();
            /*news.setTitle("This is news title "+i);
            news.setContent(getRandomLengthContent("This is news content "+i+"."));*/
            news.setTitle("勤工宝APP正式上线啦");
            news.setContent(getRandomLengthContent("目前此APP正处于开发阶段，大家敬请期待。" ));
            newsList.add(news);
        }
   /*     News news = new News();
        news.setTitle("勤工宝APP正式上线啦");
        news.setContent("目前此APP正处于开发阶段，大家敬请期待。");
        newsList.add(news);*/

        return newsList;
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    /*    if(getActivity().findViewById(R.id.news_content_layout)!=null){
            isTwopane=true;//可以找到news_content_layout布局时，为双页模式
        }else{
            isTwopane=false;//找不到news_content_layout布局时，为单页模式
        }*/
    }


    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

        private List<News> mNewsList;

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView newsTitleText;
            //构造器传入一个View参数，View参数就是RecyclerView子项的最外层布局
            public ViewHolder(View view){
                super(view);
                newsTitleText=(TextView)view.findViewById(R.id.news_title);
            }
        }

        //NewsAdapter内部类的构造器，这个方法用于将要展示在界面的数据源传进来，并赋值给一个全局变量mFruitAdapter
        public NewsAdapter(List<News> newsList){
            mNewsList=newsList;
        }

        @NonNull
        @Override
        //因为NewsAdapter是继承RecyclerView.Adapeter的，所以必须重写以下三个方法
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);//加载子项布局
            final ViewHolder holder=new ViewHolder(view);//将加载的布局传入到ViewHolder类构造函数中
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {//当用户点击每一个新闻标题时，就会显示新闻内容
                    News news=mNewsList.get(holder.getAdapterPosition());
                    /*if(isTwopane){//如果是双页模式，则刷新NewsContentFragment的内容
                        NewsContentFragment newsContentFragment=(NewsContentFragment)getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getTitle(),news.getContent());
                    }else{//如果是单页模式，直接启动NewsContentActivity

                    }*/
                    NewsContentActivity.actionStart(getActivity(),news.getTitle(),news.getContent());
                }
            });
            return holder;
        }

        @Override
        //该方法用于对RecyclerView子项的数据进行赋值，会在每个子项被滚动到屏幕内的时候执行
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            News news=mNewsList.get(position);//通过position得到当前项的News实例
            holder.newsTitleText.setText(news.getTitle());//在将数据设置到ViewHolder的newsTitleText
        }

        @Override
        //返回数据源长度
        public int getItemCount() {
            return mNewsList.size();
        }
    }
}
