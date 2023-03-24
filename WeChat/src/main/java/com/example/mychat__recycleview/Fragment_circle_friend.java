package com.example.mychat__recycleview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class Fragment_circle_friend extends Fragment {
    private Context context;
    //图片列表
    private List<Integer> mList = new ArrayList<Integer>();

    public Fragment_circle_friend() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle_friend, container, false);

        context = view.getContext();

        //添加数据
        InitImageList();

        //创建Adapter对象
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_circle_friend);
        StaggeredGridAdapter adapter = new StaggeredGridAdapter(context, mList);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        return view;
    }

    private void InitImageList() {
        mList.add(R.drawable.anxin);
        mList.add(R.drawable.gaoqiqiang);
        mList.add(R.drawable.gaoqishen);
        mList.add(R.drawable.xujiang);
        mList.add(R.drawable.chenshuting);
        mList.add(R.drawable.lixiang);
        mList.add(R.drawable.laomo);
        mList.add(R.drawable.liyoutian);
        mList.add(R.drawable.baijiangbo);
        mList.add(R.drawable.tangxiaolong);
    }

}
