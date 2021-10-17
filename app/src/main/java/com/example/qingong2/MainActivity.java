package com.example.qingong2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.qingong2.R;
import com.example.qingong2.adapter.MyAdapter;
import com.example.qingong2.fragment.DepthPageTransformer;
import com.example.qingong2.fragment.HomePageFragment;
import com.example.qingong2.fragment.SelfFragment;
import com.example.qingong2.fragment.ShareFragment;
import com.example.qingong2.share.HttpUtil;
import com.example.qingong2.adapter.MyAdapter;
import com.example.qingong2.fragment.DepthPageTransformer;
import com.example.qingong2.fragment.HomePageFragment;
import com.example.qingong2.fragment.SelfFragment;
import com.example.qingong2.fragment.ShareFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> fragmentList;

    private ViewPager viewPager;

    private TabLayout tableLayout;

    private DrawerLayout mDrawerLayout;

    private NavigationView mNavigation;



    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //与状态栏融合
        /*if(Build.VERSION.SDK_INT>=21){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }*/
        setContentView(R.layout.activity_main);
        /*ActionBar actionBar=getSupportActionBar();//隐藏ActionBar
        actionBar.hide();*/

        //设置ActonBar
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //搜索
        doSearchQuery(getIntent());

        //注册nav列表
        RecyclerView recyclerView=new RecyclerView(MainActivity.this);
        registerForContextMenu(recyclerView);

        //开启侧页滑动导航栏
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);//将导航按钮显示出来
            actionBar.setHomeAsUpIndicator(R.drawable.icon_nav);//设置导航按钮tub
        }


        mNavigation=findViewById(R.id.nav_view);
        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_photos:
                        Toast.makeText(MainActivity.this,"You clicked 相册",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_second_fragment:
                        Toast.makeText(MainActivity.this,"You clicked nav_second_fragment",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_setting:
                        Toast.makeText(MainActivity.this,"You clicked 设置",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_five:
                        Toast.makeText(MainActivity.this,"You clicked sub item 2",Toast.LENGTH_LONG).show();
                        break;
                    default:
                }
                return false;
            }

        });


        //多页滑动
        viewPager=findViewById(R.id.in_viewpager);
        LayoutInflater layoutInflater = getLayoutInflater().from(MainActivity.this);
        fragmentList=new ArrayList<>();
        fragmentList.add(new HomePageFragment());
        fragmentList.add(new ShareFragment());
        fragmentList.add(new SelfFragment());
        viewPager.setAdapter(new MyAdapter(fragmentList,getSupportFragmentManager()));
        viewPager.setPageTransformer(true,new DepthPageTransformer());

        tableLayout=(TabLayout) findViewById(R.id.tab);
        tableLayout.setupWithViewPager(viewPager);




    }



    /*search*/
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        doSearchQuery(intent);
    }

    //设置搜索响应1
    private void doSearchQuery(Intent intent){// 对searchable activity的调用仍是标准的intent，我们可以从intent中获取信息，即要搜索的内容
        if(intent == null)
            return;

        String queryAction = intent.getAction();
        if( Intent.ACTION_SEARCH.equals( intent.getAction())){  //如果是通过ACTION_SEARCH来调用，即如果通过搜索调用
            String queryString = intent.getStringExtra(SearchManager.QUERY); //获取搜索内容
            if("中北保洁".equals(queryString)){
                Toast.makeText(MainActivity.this,queryString+"欢迎您",Toast.LENGTH_LONG).show();
            }

        }

    }


    /*menu*/
    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        //searchView展开
/*        MenuItem searchItem = menu.findItem(R.id.menu_search);
        searchItem.expandActionView();*/

        // 获取SearchView对象
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        //设置文字
        searchView.setQuery("中北保洁", false);

        //searView清除焦点，关闭虚拟键盘
        searchView.clearFocus();

        if(searchView == null){
            Log.e("SearchView","Fail to get Search View.");
            return true;
        }
        searchView.setIconifiedByDefault(true); // 缺省值就是true，可能不专门进行设置，false和true的效果图如下，true的输入框更大

        // 获取搜索服务管理器
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        // searchable activity的component name，由此系统可通过intent进行唤起
        ComponentName cn = new ComponentName(this,MainActivity.class);
        // 通过搜索管理器，从searchable activity中获取相关搜索信息，就是searchable的xml设置。如果返回null，表示该activity不存在，或者不是searchable
        SearchableInfo info = searchManager.getSearchableInfo(cn);
        if(info == null){
            Log.e("SearchableInfo","Fail to get search info.");
        }
        // 将searchable activity的搜索信息与search view关联
        searchView.setSearchableInfo(info);


        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                mDrawerLayout.setDrawerShadow(R.drawable.ic_launcher_background, Gravity.LEFT);
                break;
            case R.id.scan:
                Toast.makeText(MainActivity.this,"You clicked 扫一扫" ,Toast.LENGTH_LONG).show();
                break;
            case R.id.friends:
                Toast.makeText(MainActivity.this,"You clicked 加好友/群",Toast.LENGTH_LONG).show();
                break;

            default:
        }

//        return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {//用于显示menu的icon
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }



}