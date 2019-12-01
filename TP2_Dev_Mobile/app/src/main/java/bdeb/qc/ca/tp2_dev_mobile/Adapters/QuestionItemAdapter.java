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
    private ArrayList<QuestionListItem> questions;
    private OnItemClickListener listener;

    @NonNull
    @Override
    public QuestionItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.questions_recycler_view_item,
                                        parent,
                                        false);
        return new QuestionItemViewHolder(v, listener);
    }

    public void setListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionItemViewHolder holder, int position)
    {
        QuestionListItem item = questions.get(position);
        holder.question.setText(item.getQuestion());
    }

    @Override
    public int getItemCount()
    {
        return questions.size();
    }

    public QuestionItemAdapter(ArrayList<QuestionListItem> questions)
    {
        this.questions = questions;
    }

    public static class QuestionItemViewHolder extends RecyclerView.ViewHolder
    {
        public TextView question;
        public QuestionItemViewHolder(@NonNull View itemView, final OnItemClickListener listener)
        {
            super(itemView);
            question = itemView.findViewById(R.id.questions_tvTitle);
            setListener(itemView, listener);
        }

        private void setListener(View itemView, final OnItemClickListener listener)
        {
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (listener == null) return;
                    int pos = getAdapterPosition();
                    if (pos == RecyclerView.NO_POSITION) return;
                    listener.onItemClick(pos);
                }
            });
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(int pos);
    }
}
