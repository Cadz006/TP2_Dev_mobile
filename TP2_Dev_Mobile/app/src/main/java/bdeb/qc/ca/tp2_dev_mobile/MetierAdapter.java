package bdeb.qc.ca.tp2_dev_mobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MetierAdapter extends RecyclerView.Adapter<MetierAdapter.MetierViewHolder> {

    private ArrayList<Metier> metierList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) { this.listener = listener; }

    public static class MetierViewHolder extends RecyclerView.ViewHolder {

        public TextView tvMetierLetter;
        public ProgressBar pbProgress;

        public MetierViewHolder(View metierView, final OnItemClickListener listener) {
            super(metierView);

            tvMetierLetter = metierView.findViewById(R.id.tvMetierLetter);
            pbProgress = metierView.findViewById(R.id.pbProgress);
        }
    }

    public MetierAdapter(ArrayList<Metier> metierList) { this.metierList = metierList;}

    @NonNull
    @Override
    public MetierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_metier_progress, parent, false);
        MetierViewHolder mvh = new MetierViewHolder(v, listener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MetierViewHolder holder, int position) {
        Metier metier = metierList.get(position);

        holder.tvMetierLetter.setText(metier.getMetierLetter());
        holder.pbProgress.setProgress(metier.getProgress());
    }

    @Override
    public int getItemCount() {
        return metierList.size();
    }
}
