package com.michaelwijaya.xyzdictionary;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionAdapter.DefinitionViewHolder> {
    ArrayList<Definitions> definitions;
    Context context;

    public DefinitionAdapter(Context context, ArrayList<Definitions> definitions){
        this.definitions = definitions;
        this.context = context;
    }

    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View DefinitionView = inflater.inflate(R.layout.item_definition, parent, false);
        return new DefinitionViewHolder(DefinitionView);
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        holder.tvWordType.setText(definitions.get(position).getType());
        String imgRes = definitions.get(position).getImageUrl();
        if(imgRes == null){
            holder.ivDefImg.setImageResource(R.drawable.no_image_available);
        }else{
            Uri uri = Uri.parse(imgRes);
            Glide.with(context)
                    .load(uri)
                    .placeholder(R.drawable.loading_icon)
                    .centerCrop()
                    .into(holder.ivDefImg);
        }
        holder.tvDefDesc.setText(definitions.get(position).getDefinition());
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }

    static class DefinitionViewHolder extends RecyclerView.ViewHolder {
        TextView tvWordType;
        ImageView ivDefImg;
        TextView tvDefDesc;
        public DefinitionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWordType = itemView.findViewById(R.id.tv_word_type);
            ivDefImg = itemView.findViewById(R.id.iv_def_img);
            tvDefDesc = itemView.findViewById(R.id.tv_def_desc);
        }
    }
}
