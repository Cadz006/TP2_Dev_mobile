package bdeb.qc.ca.tp2_dev_mobile.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bdeb.qc.ca.tp2_dev_mobile.Model.Etudiant;
import bdeb.qc.ca.tp2_dev_mobile.R;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.EtudiantViewHolder> {

    private ArrayList<Etudiant> rveList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) { this.listener = listener; }

    public static class EtudiantViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNom;
        public TextView tvPrenom;
        public TextView tvEmail;


        public EtudiantViewHolder( View itemView, final OnItemClickListener listener) {

            super(itemView);

            tvNom = itemView.findViewById(R.id.nomFamille);
            tvPrenom = itemView.findViewById(R.id.prenom);
            tvEmail = itemView.findViewById(R.id.email);

        }
    }

    public EtudiantAdapter(ArrayList<Etudiant> rveList) { this.rveList = rveList; }



    @NonNull
    @Override
    public EtudiantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_etudiant, parent, false);
        EtudiantViewHolder cvh = new EtudiantViewHolder(v, listener);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull EtudiantAdapter.EtudiantViewHolder holder, int position) {
        Etudiant etudiant = rveList.get(position);

        holder.tvNom.setText(etudiant.getNom());
        holder.tvPrenom.setText(etudiant.getPrenom());
        holder.tvEmail.setText(etudiant.getEmail());

    }

    @Override
    public int getItemCount() {
        return rveList.size();
    }
}
