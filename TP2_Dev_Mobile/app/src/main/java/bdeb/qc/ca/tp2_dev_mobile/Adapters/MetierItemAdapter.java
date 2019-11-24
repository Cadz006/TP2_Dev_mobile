package bdeb.qc.ca.tp2_dev_mobile.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bdeb.qc.ca.tp2_dev_mobile.Model.MetierListItem;
import bdeb.qc.ca.tp2_dev_mobile.R;

public class MetierItemAdapter extends RecyclerView.Adapter<MetierItemAdapter.MetierItemViewHolder>
{
    private ArrayList<MetierListItem> metierItems;
    @NonNull
    @Override
    public MetierItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.metier_recycler_view_item,
                                        parent,
                                        false);
        return new MetierItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MetierItemViewHolder holder, int position)
    {
        MetierListItem item = metierItems.get(position);
        holder.title.setText(item.getTitle());
        holder.completion.setText("2/5"); // todo percentage of completed questions
    }

    @Override
    public int getItemCount()
    {
        return metierItems.size();
    }

    public MetierItemAdapter(ArrayList<MetierListItem> metierItems)
    {
        this.metierItems = metierItems;
    }

    public static class MetierItemViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title;
        public TextView completion;
        public MetierItemViewHolder(@NonNull View itemView)
        {
            super(itemView);
            title = itemView.findViewById(R.id.metier_tvTitle);
            completion = itemView.findViewById(R.id.metier_tvCompletion);
        }
    }
}
