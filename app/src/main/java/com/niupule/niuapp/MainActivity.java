package com.niupule.niuapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.niupule.niuapp.mvp.about.AboutFragment;
import com.niupule.niuapp.mvp.category.CategoryFragment;
import com.niupule.niuapp.mvp.search.SearchActivity;
import com.niupule.niuapp.mvp.timeline.TimeLineFragment;
import com.niupule.niuapp.mvp.todo.TodoFragment;
import com.niupule.niuapp.util.SharedUtil;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView main_bottom_navigation;
    private Toolbar main_toolbar;

    private TimeLineFragment timeLineFragment;
    private CategoryFragment categoryFragment;
    private TodoFragment todoFragment;
    private AboutFragment aboutFragment;

    private static final String KEY_BOTTOM_NAVIGATION_SELECTED_ITEM = "bottom_navigation_selected_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initFragments(savedInstanceState);

        if (savedInstanceState != null) {
            //不是第一次使用
            int selectedId = savedInstanceState.getInt(KEY_BOTTOM_NAVIGATION_SELECTED_ITEM);
            switch (selectedId) {
                case 0:
                    showFragment(timeLineFragment);
                    break;
                case 1:
                    showFragment(categoryFragment);
                    break;
                case 2:
                    showFragment(todoFragment);
                    break;
                case 3:
                    showFragment(aboutFragment);
                    break;
                default:
                    break;
            }
        } else {
            showFragment(timeLineFragment);
        }

        main_bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_timeline:
                        showFragment(timeLineFragment);
                        break;
                    case R.id.nav_category:
                        showFragment(categoryFragment);
                        break;
                    case R.id.nav_todo:
                        showFragment(todoFragment);
                        break;
                    case R.id.nav_about:
                        showFragment(aboutFragment);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        //获取当前用户登录信息
        String username = SharedUtil.getInstance().getUsername();

    }

    /**
     * 初始话Fragments对象
     */
    protected void initFragments(Bundle savedInstanceState){
        FragmentManager manager = getSupportFragmentManager();
        if(savedInstanceState != null){
            timeLineFragment = (TimeLineFragment) manager.getFragment(savedInstanceState,TimeLineFragment.class.getSimpleName());
            categoryFragment = (CategoryFragment) manager.getFragment(savedInstanceState,CategoryFragment.class.getSimpleName());
            todoFragment = (TodoFragment) manager.getFragment(savedInstanceState,TodoFragment.class.getSimpleName());
            aboutFragment = (AboutFragment) manager.getFragment(savedInstanceState,AboutFragment.class.getSimpleName());
        }else {
            timeLineFragment = TimeLineFragment.newInstance();
            categoryFragment = CategoryFragment.newInstance();
            todoFragment = TodoFragment.newInstance();
            aboutFragment = AboutFragment.newInstance();
        }
        if (!timeLineFragment.isAdded()){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout,timeLineFragment,TimeLineFragment.class.getSimpleName())
                    .commit();
        }
        if (!categoryFragment.isAdded()){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout,categoryFragment,CategoryFragment.class.getSimpleName())
                    .commit();
        }
        if (!todoFragment.isAdded()){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout,todoFragment,TodoFragment.class.getSimpleName())
                    .commit();
        }
        if (!aboutFragment.isAdded()){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout,aboutFragment,AboutFragment.class.getSimpleName())
                    .commit();
        }
    }

    /**
     * 展示fragment
     * @param fragment
     */
    protected void showFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        if (fragment instanceof TimeLineFragment){
            manager.beginTransaction()
                    .show(timeLineFragment)
                    .hide(categoryFragment)
                    .hide(todoFragment)
                    .hide(aboutFragment)
                    .commit();
            setTitle("文章");
        }
        if (fragment instanceof CategoryFragment){
            manager.beginTransaction()
                    .show(categoryFragment)
                    .hide(timeLineFragment)
                    .hide(todoFragment)
                    .hide(aboutFragment)
                    .commit();
            setTitle("分类");
        }
        if (fragment instanceof TodoFragment){
            manager.beginTransaction()
                    .show(todoFragment)
                    .hide(timeLineFragment)
                    .hide(categoryFragment)
                    .hide(aboutFragment)
                    .commit();
            setTitle("笔记");
        }
        if (fragment instanceof AboutFragment){
            manager.beginTransaction()
                    .show(aboutFragment)
                    .hide(timeLineFragment)
                    .hide(categoryFragment)
                    .hide(todoFragment)
                    .commit();
            setTitle("关于");
        }
    }

    /**
     * 初始化页面
     */
    protected void initView() {
        main_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(main_toolbar);
        main_bottom_navigation = findViewById(R.id.bottom_navigation_view);
    }

    /**
     * 初始话右边的搜索内容
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * 设置toolbar点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 设置当前状态被保存
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_BOTTOM_NAVIGATION_SELECTED_ITEM, main_bottom_navigation.getSelectedItemId());
        FragmentManager manager = getSupportFragmentManager();
        if (timeLineFragment.isAdded()) {
            manager.putFragment(outState, TimeLineFragment.class.getSimpleName(), timeLineFragment);
        }
        if (categoryFragment.isAdded()) {
            manager.putFragment(outState, CategoryFragment.class.getSimpleName(), categoryFragment);
        }
        if (todoFragment.isAdded()) {
            manager.putFragment(outState, TodoFragment.class.getSimpleName(), todoFragment);
        }
        if (aboutFragment.isAdded()) {
            manager.putFragment(outState, AboutFragment.class.getSimpleName(), aboutFragment);
        }
    }
}
