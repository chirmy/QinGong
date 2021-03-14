package com.example.qingong2.homepage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qingong2.R;

import java.util.List;

public class ApplyRecyclerViewAdapter extends RecyclerView.Adapter<ApplyRecyclerViewAdapter.ViewHolder> {

    private List<Apply> mApplyList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View applyView;

        ImageView applyImage;

        TextView applyName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            applyView = itemView;
            applyImage = (ImageView)itemView.findViewById(R.id.apply_image);
            applyName = (TextView)itemView.findViewById(R.id.apply_name);
        }
    }

    public ApplyRecyclerViewAdapter(List<Apply> applyList){
        mApplyList=applyList;
    }

    @NonNull
    @Override
    public ApplyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.apply_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.applyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Apply apply = mApplyList.get(position);
                Toast.makeText(parent.getContext(),"you clicked view "+apply.getName(),Toast.LENGTH_LONG).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ApplyRecyclerViewAdapter.ViewHolder holder, int position) {//在每个子项展示的时候调用，就是很简单的通过位置设置值
        Apply apply=mApplyList.get(position);

        holder.applyImage.setImageResource(apply.getImageId());
        holder.applyName.setText(apply.getName());
    }

    @Override
    public int getItemCount() {
        return mApplyList.size();
    }
}
