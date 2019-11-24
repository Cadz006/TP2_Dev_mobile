package bdeb.qc.ca.tp2_dev_mobile.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import bdeb.qc.ca.tp2_dev_mobile.Adapters.MetierItemAdapter;
import bdeb.qc.ca.tp2_dev_mobile.Model.MetierListItem;
import bdeb.qc.ca.tp2_dev_mobile.R;

public class MetierActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metier);
    }
}
