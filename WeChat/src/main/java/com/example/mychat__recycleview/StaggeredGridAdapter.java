package com.example.mychat__recycleview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StaggeredGridAdapter extends RecyclerView.Adapter<StaggeredGridAdapter.DataViewHolder>{
    private Context mContext;
    private RecyclerView recyclerView;
    private List<Integer> mList;
    private List<Integer> mHeight;

    //带参构造函数
    public StaggeredGridAdapter(Context mContext, List<Integer> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    /**
     * 初始化每个Item的高度（瀑布流效果）
     * @return
     */
    public List<Integer> initHeight(){
        mHeight = new ArrayList<>();
        for (int i=0;i<mList.size();i++){
            //随机设置图片高度
            mHeight.add((int) (Math.random()*300)+300);
        }
        return mHeight;
    }

    /**
     * 用于创建ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_image,null);
        //使用代码设置宽高（xml布局设置无效时）
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        DataViewHolder holder = new DataViewHolder(view);
        return holder;
    }

    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        //设置每个Item的高度
        ViewGroup.LayoutParams h = holder.iv_data.getLayoutParams();
        h.height = mHeight.get(position);
        //设置图片
        holder.iv_data.setImageResource(mList.get(position));
    }

    /**
     * 选项总数
     * @return
     */
    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 创建ViewHolder
     */
    public static class DataViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_data;
        public DataViewHolder(View itemView) {
            super(itemView);
            iv_data = (ImageView) itemView.findViewById(R.id.item_ImageView);
        }
    }
    /**
     *   将RecycleView附加到Adapter上
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView= recyclerView;
        //初始化每个Item的高度
        initHeight();
    }
    /**
     *   将RecycleView从Adapter解除
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }
}