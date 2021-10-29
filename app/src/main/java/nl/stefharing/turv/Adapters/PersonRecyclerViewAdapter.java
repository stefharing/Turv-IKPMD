package nl.stefharing.turv.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import nl.stefharing.turv.R;

public class PersonRecyclerViewAdapter extends RecyclerView.Adapter<PersonRecyclerViewAdapter.ViewHolder> {

    private List<String> personAdapterList;
    private LayoutInflater personInflater;
    private ItemClickListener2 personClickListener;

    public PersonRecyclerViewAdapter(Context context, List<String> data) {
        this.personInflater = LayoutInflater.from(context);
        this.personAdapterList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = personInflater.inflate(R.layout.person_recyclerview_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String person = personAdapterList.get(position);
        holder.personTextView.setText(person);
    }

    @Override
    public int getItemCount() {
        return personAdapterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView personTextView;

        ViewHolder(View itemView) {
            super(itemView);
            personTextView = itemView.findViewById(R.id.person_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (personClickListener != null) personClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public String getItem(int id) {
        return personAdapterList.get(id);
    }

    public void setClickListener(ItemClickListener2 itemClickListener2) {
        this.personClickListener = itemClickListener2;
    }

    public interface ItemClickListener2 {
        void onItemClick(View view, int position);
    }
}