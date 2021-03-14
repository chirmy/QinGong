package com.example.qingong2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qingong2.R;
import com.example.qingong2.News.News;
import com.example.qingong2.NewsActivity;

import com.example.qingong2.homepage.Apply;
import com.example.qingong2.homepage.ApplyRecyclerViewAdapter;
import com.example.qingong2.NewsActivity;
import com.example.qingong2.homepage.Apply;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment {

    FloatingActionButton fab;

    List<Apply> mApplyList = new ArrayList<>();


    private void initApplys(){
//        for(int i=0;i<10;i++) {
            Apply apply = new Apply("优秀个人申请", R.drawable.bg_apply_zuyuan);
            mApplyList.add(apply);
        apply=new Apply("月度人物申请", R.drawable.bg_apply_zuyuan);
        mApplyList.add(apply);
        apply=new Apply("月度人物申请", R.drawable.bg_apply_zuyuan);
        mApplyList.add(apply);
        apply=new Apply("年度人物申请", R.drawable.bg_apply_zuyuan);
        mApplyList.add(apply);
        apply=new Apply("组长面试申请", R.drawable.bg_apply_zuyuan);
        mApplyList.add(apply);
        apply=new Apply("替岗换岗申请", R.drawable.bg_apply_zuyuan);
        mApplyList.add(apply);
//        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.homepage_fragment,container,false);

        initApplys();
        //悬浮按钮设置点击事件
        fab=view.findViewById(R.id.home_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("222","555");
               /* Intent intent= new Intent(getActivity(), NewsActivity.class);
                startActivity(intent);*/
//                Intent intent = new Intent(getContext(), NewsActivity.class);
                startActivity( new Intent(getActivity(), NewsActivity.class));
            }
        });

        //horizontal RecyclerView
        RecyclerView recyclerView=view.findViewById(R.id.home_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        ApplyRecyclerViewAdapter adapter = new ApplyRecyclerViewAdapter(mApplyList);
        recyclerView.setAdapter(adapter);


        //vertical RecyclerView
        RecyclerView recyclerView2=view.findViewById(R.id.home_rv2);
//        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(view.getContext());
        GridLayoutManager linearLayoutManager2=new GridLayoutManager(view.getContext(),2);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        ApplyRecyclerViewAdapter adapter2 = new ApplyRecyclerViewAdapter(mApplyList);
        recyclerView2.setAdapter(adapter2);







        return view;
    }



}
