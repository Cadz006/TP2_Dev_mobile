package bdeb.qc.ca.tp2_dev_mobile;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.EtudiantViewHolder> {

    private ArrayList<Etudiant> rveList;
    private AdapterView.OnItemClickListener listener;

    public class EtudiantViewHolder extends RecyclerView.ViewHolder {



        public EtudiantViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public EtudiantAdapter.EtudiantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull EtudiantAdapter.EtudiantViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
