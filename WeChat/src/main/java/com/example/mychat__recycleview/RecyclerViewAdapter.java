package com.example.mychat__recycleview;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Myviewholder> {
    //昵称列表
    private List<String> mList;
    //个性签名列表
    private List<String> nList;

    private Context context;

    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;


    //带参构造函数
    public RecyclerViewAdapter(List<String> mList, List<String> nList, Context context) {
        //昵称列表
        this.mList = mList;
        //个性签名列表
        this.nList = nList;

        this.context = context;
    }


    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        Myviewholder myviewholder = new Myviewholder((view));
        return myviewholder;
    }


    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        holder.counter.setText(position + 1 + " ");
        holder.nickname.setText(mList.get(position));
        holder.personalized_signature.setText(nList.get(position));

        //实现对点击item的事件函数
        final String content = mList.get(position);
        holder.itemView.setContentDescription(content);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "您点击的联系人是：" + content, Toast.LENGTH_SHORT).show();

                //跳转到好友详情页面
                Intent intent = new Intent();
                intent.setClass(context,leimuActivity.class);
                context.startActivity(intent);
            }
        });

/*        //实现分组吸顶悬浮
        if (position == 0) {
            holder.tvGroup.setVisibility(View.VISIBLE);
            holder.tvGroup.setText(mList.get(position));
            holder.itemView.setTag(FIRST_STICKY_VIEW);
        } else {
            if (!TextUtils.equals(mList.get(position), mList.get(position - 1))) {
                holder.tvGroup.setVisibility(View.VISIBLE);
                holder.tvGroup.setText(mList.get(position));
                holder.itemView.setTag(HAS_STICKY_VIEW);
            } else {
                holder.tvGroup.setVisibility(View.GONE);
                holder.itemView.setTag(NONE_STICKY_VIEW);
            }
        }
*/

    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        TextView counter, nickname, personalized_signature;
        TextView tvGroup;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            counter = itemView.findViewById((R.id.counter));
            nickname = itemView.findViewById(R.id.nickname);
            personalized_signature = itemView.findViewById(R.id.personalized_signature);
            tvGroup = itemView.findViewById(R.id.textView_group_name);
        }
    }

}
