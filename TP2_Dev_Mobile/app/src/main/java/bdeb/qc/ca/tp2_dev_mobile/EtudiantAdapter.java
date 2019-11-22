package bdeb.qc.ca.tp2_dev_mobile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EtudiantAdapter extends RecyclerView.Adapter<EtudiantAdapter.EtudiantViewHolder> {

    private ArrayList<Etudiant> rveList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class EtudiantViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNom;
        public TextView tvPrenom;
        public TextView tvEmail;


        public EtudiantViewHolder( View itemView, final OnItemClickListener listener) {

            super(itemView);

            tvNom = itemView.findViewById(R.id.nomFamille);
            tvPrenom = itemView.findViewById(R.id.prenom);


        }
    }

    public EtudiantAdapter(ArrayList<Etudiant> rveList) { this.rveList = rveList; }

    public void setOnItemClickListener(OnItemClickListener listener) { this.listener = listener; }

    @NonNull
    @Override
    public EtudiantAdapter.EtudiantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_etudiant, parent, false);
        EtudiantViewHolder cvh = new EtudiantViewHolder(v, listener);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull EtudiantAdapter.EtudiantViewHolder holder, int position) {
        Etudiant etudiant = rveList.get(position);

        holder.tvNom.setText(etudiant.getNom());
        holder.tvPrenom.setText(etudiant.getPrenom());

    }

    @Override
    public int getItemCount() {
        return rveList.size();
    }
}
