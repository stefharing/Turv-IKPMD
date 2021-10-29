package nl.stefharing.turv.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nl.stefharing.turv.R;

public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<GroupRecyclerViewAdapter.ViewHolder> {

    private List<String> groupAdapterList;
    private LayoutInflater groupInflater;
    private ItemClickListener groupClickListener;

    public GroupRecyclerViewAdapter(Context context, List<String> data) {
        this.groupInflater = LayoutInflater.from(context);
        this.groupAdapterList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = groupInflater.inflate(R.layout.groups_recyclerview_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String groupName = groupAdapterList.get(position);
        holder.textView.setText(groupName);
    }

    @Override
    public int getItemCount() {
        return groupAdapterList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.group_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (groupClickListener != null) groupClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public String getItem(int id) {
        return groupAdapterList.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.groupClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}