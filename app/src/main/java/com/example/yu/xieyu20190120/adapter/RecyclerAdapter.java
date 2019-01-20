package com.example.yu.xieyu20190120.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yu.xieyu20190120.R;
import com.example.yu.xieyu20190120.activity.DetailActivity;
import com.example.yu.xieyu20190120.bean.ProductBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ProductBean.Data> list;

    public RecyclerAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<ProductBean.Data> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.recycler_item_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final ProductBean.Data data = list.get(i);
        ViewHolder holder= (ViewHolder) viewHolder;
        holder.price.setText("¥"+data.getPrice());
        holder.title.setText(data.getTitle());
        String[] split = data.getImages().split("\\|");
        holder.simpleDraweeView.setImageURI(split[0]);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,DetailActivity.class);
                intent.putExtra("pid",data.getPid());
                context.startActivity(intent);
            }
        });
        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                final float x = v.getX();
                //设置属性动画
                ObjectAnimator translateX=ObjectAnimator.ofFloat(v,"translationX",x,-1000);
                translateX.setDuration(3000);
                translateX.start();
                translateX.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setX(x);
                        list.remove(i);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recycler_item)
        RelativeLayout relativeLayout;
        @BindView(R.id.recycler_simple)
        SimpleDraweeView simpleDraweeView;
        @BindView(R.id.recycler_title)
        TextView title;
        @BindView(R.id.recycler_price)
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
