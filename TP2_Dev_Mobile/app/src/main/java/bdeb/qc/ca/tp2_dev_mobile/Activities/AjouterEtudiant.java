package bdeb.qc.ca.tp2_dev_mobile.Activities;

        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.View;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.recyclerview.widget.ItemTouchHelper;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import java.util.ArrayList;

        import bdeb.qc.ca.tp2_dev_mobile.Adapters.AdminProfAdapter;
        import bdeb.qc.ca.tp2_dev_mobile.Model.Etudiant;
        import bdeb.qc.ca.tp2_dev_mobile.R;

public class AjouterEtudiant extends AppCompatActivity
{
    private ArrayList<Etudiant> listEnseignant;
    private AdminProfAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_compte);
        setupToolbar();
        addSampleDataToListEtudiant();
        createRecyclerView();
    }

    /**
     * Cette méthode ajoute des données dans la liste d'étudiants.
     * Pour l'instant, elle sert parce que nous n'arrivons pas à avoir accès à l'API.
     */
    private void addSampleDataToListEtudiant()
    {
        listEnseignant = new ArrayList<>();
        listEnseignant.add(new Etudiant("Cadieux", "Olivier", "ocadz@hotmail.ca", true, "", "", "password"));
        listEnseignant.add(new Etudiant("De La Barrière", "Guillaume", "mightguy@hotmail.ca", true, "", "", "password"));
        listEnseignant.add(new Etudiant("Phalakhone", "Nick", "nick@hotmail.ca", true, "", "", "password"));
    }

    /**
     * Cette méthode set la toolbar avec un onClick qui ferme l'activité
     */
    private void setupToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbarAjout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Cette méthode initialise les propriétés du recycler view et du adapter.
     */
    private void createRecyclerView()
    {
        RecyclerView recyclerView = findViewById(R.id.recycleViewAdmin);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new AdminProfAdapter(listEnseignant);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_accueil_admin_professor, menu);
        return true;
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            listEnseignant.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();
        }
    };
}
