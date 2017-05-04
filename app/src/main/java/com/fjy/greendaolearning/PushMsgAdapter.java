package com.fjy.greendaolearning;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fjy.greendaolearning.pojo.PushMsg;

import java.util.ArrayList;
import java.util.List;

public class PushMsgAdapter extends RecyclerView.Adapter<PushMsgAdapter.PushMsgViewHolder> {

    private MsgClickListener clickListener;
    private List<PushMsg> dataset;

    public interface MsgClickListener {
        void onMsgClick(int position);
    }

    static class PushMsgViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        public PushMsgViewHolder(View itemView, final MsgClickListener clickListener) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener != null) {
                        clickListener.onMsgClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public PushMsgAdapter(MsgClickListener clickListener) {
        this.clickListener = clickListener;
        this.dataset = new ArrayList<PushMsg>();
    }

    public void setNotes(@NonNull List<PushMsg> pushMsgs) {
        dataset = pushMsgs;
        notifyDataSetChanged();
    }

    public PushMsg getNote(int position) {
        return dataset.get(position);
    }

    @Override
    public PushMsgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_push_msg, parent, false);
        return new PushMsgViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(PushMsgViewHolder holder, int position) {
        PushMsg msg = dataset.get(position);
        holder.title.setText(msg.getTitle());
        holder.description.setText(msg.getDescription());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}