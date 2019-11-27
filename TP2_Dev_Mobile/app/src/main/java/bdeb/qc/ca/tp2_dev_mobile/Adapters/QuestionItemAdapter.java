package bdeb.qc.ca.tp2_dev_mobile.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bdeb.qc.ca.tp2_dev_mobile.Model.QuestionListItem;
import bdeb.qc.ca.tp2_dev_mobile.R;

public class QuestionItemAdapter extends RecyclerView.Adapter<QuestionItemAdapter.QuestionItemViewHolder>
{
    private ArrayList<QuestionListItem> metierItems;
    @NonNull
    @Override
    public QuestionItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.metier_recycler_view_item,
                                        parent,
                                        false);
        return new QuestionItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionItemViewHolder holder, int position)
    {
        QuestionListItem item = metierItems.get(position);
        holder.title.setText(item.getTitle());
    }

    @Override
    public int getItemCount()
    {
        return metierItems.size();
    }

    public QuestionItemAdapter(ArrayList<QuestionListItem> metierItems)
    {
        this.metierItems = metierItems;
    }

    public static class QuestionItemViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title;
        public TextView completion;
        public QuestionItemViewHolder(@NonNull View itemView)
        {
            super(itemView);
            title = itemView.findViewById(R.id.metier_tvTitle);
            completion = itemView.findViewById(R.id.metier_tvCompletion);
        }
    }
}
