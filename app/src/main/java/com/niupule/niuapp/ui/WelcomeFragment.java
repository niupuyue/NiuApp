package com.niupule.niuapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.niupule.niuapp.R;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/8/24
 * Time: 17:25
 * Desc:
 * Version:
 */
public class WelcomeFragment extends Fragment {

    private int index;

    private AppCompatTextView selectedLabel;
    private AppCompatTextView selectedInfo;
    private ImageView selectedImg;

    public static final String KEY_WELCOME_SELECTED_NUM = "selected_num";

    public static WelcomeFragment newInstance(int position) {
        WelcomeFragment fragment = new WelcomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_WELCOME_SELECTED_NUM, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = getArguments().getInt(KEY_WELCOME_SELECTED_NUM);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_welcome, container, false);
        initView(root);
        switch (index) {
            case 0:
                selectedImg.setImageResource(R.drawable.ic_camera_black_24dp);
                selectedLabel.setText("这里你可以获取海量知识信息");
                selectedInfo.setText("包含了基础是指，Gradle，JNI，OpenGL等方向的文章");
                break;
            case 1:
                selectedImg.setImageResource(R.drawable.ic_beenhere_black_24dp);
                selectedInfo.setText("便签功能，可以让你随时记录学习心得");
                selectedLabel.setText("包括标签的添加，删除，修改等操作");
                break;
            case 2:
                selectedImg.setImageResource(R.drawable.ic_notifications_black_24dp);
                selectedInfo.setText("一个新的世界正在向你招手");
                selectedLabel.setText("向着更美好的自己出发！");
                break;
            default:
                break;
        }
        return root;
    }

    protected void initView(View root) {
        selectedInfo = root.findViewById(R.id.section_intro);
        selectedLabel = root.findViewById(R.id.section_label);
        selectedImg = root.findViewById(R.id.selected_img);
    }
}
