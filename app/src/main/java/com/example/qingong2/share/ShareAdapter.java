package com.example.qingong2.share;

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

public class ShareAdapter  extends RecyclerView.Adapter<ShareAdapter.ViewHolder> {

    private List<Share> mShareList;

    public ShareAdapter(List<Share> shareList) {
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView headImage;
        TextView name;
        TextView time;
        TextView content;
        ImageView contentImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            headImage =(ImageView)itemView.findViewById(R.id.share_item_head);
            name = (TextView)itemView.findViewById(R.id.share_item_name);
            time = (TextView)itemView.findViewById(R.id.share_item_time);
            content = (TextView)itemView.findViewById(R.id.share_item_conText);
            contentImage = (ImageView)itemView.findViewById(R.id.share_item_conImg);


        }
    }


    @NonNull
    @Override
    public ShareAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.share_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(),"clicked imagehead",Toast.LENGTH_LONG).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShareAdapter.ViewHolder holder, int position) {

        Share share = mShareList.get(position);
        holder.headImage.setImageResource(share.getHeadImgId());
        holder.name.setText(share.getName());
        holder.time.setText(share.getTime());
        holder.content.setText(share.getTextcontent());
        holder.contentImage.setImageResource(share.getConImgId());
    }

    @Override
    public int getItemCount() {
        return mShareList.size();
    }
}
