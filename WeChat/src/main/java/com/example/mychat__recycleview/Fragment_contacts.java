package com.example.mychat__recycleview;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class Fragment_contacts extends Fragment {
    private Context context;
    //昵称列表
    private List<String> mList = new ArrayList<>();
    //个性签名列表
    private List<String> nList = new ArrayList<>();

    public Fragment_contacts() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        context = view.getContext();

        //添加数据
        InitNickNameList();
        InitSignatureList();

        //创建Adapter对象
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_contacts);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mList, nList, context);
        recyclerView.setAdapter(adapter);

        //LinearLayoutManager是一个工具类，承担了一个View(RecyclerView)的布局、测量、子View 创建、 复用、 回收、 缓存、 滚动等操作。
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        //addItemDecoration用来进行分割线设计
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));

        return view;
    }

    private void InitNickNameList() {
        mList.add("安欣");
        mList.add("高启强");
        mList.add("高启盛");
        mList.add("徐江");
        mList.add("陈书婷");
        mList.add("李响");
        mList.add("老默");
        mList.add("李有田");
        mList.add("白江波");
        mList.add("唐小龙");
    }

    private void InitSignatureList() {
        nList.add("黑白两道都关心，我是天使小安欣。");
        nList.add("咖啡干嚼不加糖，我是建工高启强。");
        nList.add("手拿冻鱼追一路，我叫启盛你记住。");
        nList.add("头疼来自烟灰缸，我的名字叫徐江。");
        nList.add("老公被埋不知情，我是美女陈书婷。");
        nList.add("师傅牺牲我撒谎，我的名字叫李响。");
        nList.add("鱼摊卖鱼开大货，鲨人还得陈金默。");
        nList.add("脚踩五菱没刹车，记住葬村有田哥。");
        nList.add("吃饭就坐小孩桌，我是沙场白江波。");
        nList.add("刀哥递杯红糖水，献血大使是石磊。");
    }


}
