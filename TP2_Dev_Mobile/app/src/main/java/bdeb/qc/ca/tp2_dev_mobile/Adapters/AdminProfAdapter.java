package bdeb.qc.ca.tp2_dev_mobile.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bdeb.qc.ca.tp2_dev_mobile.R;

public class AdminProfAdapter extends RecyclerView.Adapter<AdminProfAdapter.AdminProfViewHolder> {

    private ArrayList<AdminProfViewHolder> rviList;

    public static class AdminProfViewHolder extends RecyclerView.ViewHolder {
        public AdminProfViewHolder (View v){
            super(v);
        }
    }

    public AdminProfAdapter (ArrayList<AdminProfViewHolder> rviList){
        this.rviList =  rviList;
    }

    @NonNull
    @Override
    public AdminProfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_ajout_compte,
                parent, false);
        AdminProfViewHolder ivh = new AdminProfViewHolder(v);
        return ivh;

    }

    @Override
    public void onBindViewHolder(@NonNull AdminProfViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return rviList.size();
    }
}
