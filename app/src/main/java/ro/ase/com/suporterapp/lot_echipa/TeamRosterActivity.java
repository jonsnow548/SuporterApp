package ro.ase.com.suporterapp.lot_echipa;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import ro.ase.com.suporterapp.R;

public class TeamRosterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlayerAdapter playerAdapter;
    private List<Player> playerList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lot_echipa_jucatori_activity);

        recyclerView = findViewById(R.id.recyclerViewPlayers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        playerAdapter = new PlayerAdapter(this, playerList);
        recyclerView.setAdapter(playerAdapter);

        loadPlayers();
    }

    private void loadPlayers() {
        db.collection("players")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Player player = document.toObject(Player.class);
                            playerList.add(player);
                        }
                        playerAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Error loading players: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
