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

public class AdminProfAdapter extends RecyclerView.Adapter<AdminProfAdapter.AdminProfViewHolder> {

    private ArrayList<Etudiant> rveList;


    public static class AdminProfViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNom;
        public TextView tvPrenom;
        public TextView tvEmail;
        public TextView tvPassword;

        public AdminProfViewHolder (View v){
            super(v);
            tvNom = v.findViewById(R.id.txtNomAdmin);
            tvPrenom = v.findViewById(R.id.txtPrenomAdmin);
            tvEmail = v.findViewById(R.id.txtEmailAdmin);
            tvPassword = v.findViewById(R.id.txtPassword);
        }
    }

    public AdminProfAdapter (ArrayList<Etudiant> rveList){
        this.rveList =  rveList;
    }

    @NonNull
    @Override
    public AdminProfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_admin,
                parent, false);
        AdminProfViewHolder ivh = new AdminProfViewHolder(v);
        return ivh;

    }

    @Override
    public void onBindViewHolder(@NonNull AdminProfViewHolder holder, int position) {
        Etudiant etudiant = rveList.get(position);

        holder.tvNom.setText(etudiant.getNom());
        holder.tvPrenom.setText(etudiant.getPrenom());
        holder.tvEmail.setText(etudiant.getEmail());
        holder.tvPassword.setText(etudiant.getPassword());
    }

    @Override
    public int getItemCount() {
        return rveList.size();
    }
}
