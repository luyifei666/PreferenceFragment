package com.clfsjkj.govcar.index;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.clfsjkj.govcar.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FunctionBlockAdapter extends RecyclerView.Adapter<FunctionBlockAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private List<FunctionItem> data = new ArrayList<>();
    private LayoutInflater inflater;


    private Context context;
    private Boolean isEdit = false;


    //----------------------------------------------------------自己加的点击事件------------------------------------------------------------------
    private OnItemClickListener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public void setEdit(Boolean isEdit){
        this.isEdit = isEdit;
        notifyDataSetChanged();
    }

//----------------------------------------------------------自己加的点击事件------------------------------------------------------------------

    public FunctionBlockAdapter(Context context, @NonNull List<FunctionItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        if (data != null) {
            this.data = data;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.layout_grid_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final int index = position;
        FunctionItem fi = data.get(position);
        setImage(fi.imageUrl, holder.iv);
        holder.text.setText(fi.name);
       if (isEdit){
           holder.btn.setVisibility(View.VISIBLE);
       }else {
           holder.btn.setVisibility(View.GONE);
       }
        holder.btn.setImageResource(R.drawable.ic_block_delete);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FunctionItem fi = data.remove(index);
                if (listener != null) {
                    listener.remove(fi);
                }
                notifyDataSetChanged();
            }
        });


//----------------------------------------------------------自己加的点击事件------------------------------------------------------------------
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickLitener.onItemClick(view, position);
                }
            });
        }

//----------------------------------------------------------自己加的点击事件------------------------------------------------------------------
    }

    public void setImage(String url, ImageView iv) {
        try {
            int rid = context.getResources().getIdentifier(url,"drawable",context.getPackageName());
            iv.setImageResource(rid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onItemMove(RecyclerView.ViewHolder holder, int fromPosition, int targetPosition) {
        if (fromPosition < data.size() && targetPosition < data.size()) {
            Collections.swap(data, fromPosition, targetPosition);
            notifyItemMoved(fromPosition, targetPosition);

            //移动后保存数据
            SFUtils sfUtils = new SFUtils(context);
            sfUtils.saveSelectFunctionItem(data);
        }
    }

    @Override
    public void onItemSelect(RecyclerView.ViewHolder holder) {
        holder.itemView.setScaleX(0.8f);
        holder.itemView.setScaleY(0.8f);
    }

    @Override
    public void onItemClear(RecyclerView.ViewHolder holder) {
        holder.itemView.setScaleX(1.0f);
        holder.itemView.setScaleY(1.0f);
    }

    @Override
    public void onItemDismiss(RecyclerView.ViewHolder holder) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv, btn;
        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            text = (TextView) itemView.findViewById(R.id.text);
            btn = (ImageView) itemView.findViewById(R.id.btn);
        }
    }

    public interface OnItemRemoveListener {
        void remove(FunctionItem item);
    }

    private OnItemRemoveListener listener;

    public void setOnItemRemoveListener(OnItemRemoveListener listener) {
        this.listener = listener;
    }
}
