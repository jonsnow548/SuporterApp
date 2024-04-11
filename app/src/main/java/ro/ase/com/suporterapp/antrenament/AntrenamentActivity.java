package ro.ase.com.suporterapp.antrenament;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import ro.ase.com.suporterapp.R;

public class AntrenamentActivity extends AppCompatActivity implements AddTrainingSessionDialog.AddTrainingSessionListener {
    private RecyclerView recyclerView;
    private List<TrainingSession> trainingSessions;
    private TrainingSessionAdapter adapter;
    private CollectionReference collectionReference;

    private FloatingActionButton fabAddTrainingSession;
    private FloatingActionButton fabBack;

    private boolean isTrainerOrAdmin = false; // Flag pentru a verifica dacă utilizatorul este antrenor sau administrator

    // Firebase instances
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.antrenament_activity);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Inițializează RecyclerView și adapterul
        recyclerView = findViewById(R.id.recyclerViewTrainingSessions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        trainingSessions = new ArrayList<>();
        adapter = new TrainingSessionAdapter(trainingSessions);
        recyclerView.setAdapter(adapter);

        // Inițializează referința către colecția de documente din Firestore
        collectionReference = FirebaseFirestore.getInstance().collection("training_sessions");

        // Încarcă datele din Firestore
        loadTrainingSessionsFromFirestore();

        fabAddTrainingSession = findViewById(R.id.fabAddTrainingSession);
        fabAddTrainingSession.setOnClickListener(v -> showAddTrainingSessionDialog());
        fabBack = findViewById(R.id.fabBack);
        fabBack.setOnClickListener(v -> finish());

        // Verifică rolul utilizatorului
        updateUserInterfaceBasedOnUserType();
    }

    // Metodă pentru încărcarea sesiunilor de antrenament din Firestore
    private void loadTrainingSessionsFromFirestore() {
        collectionReference.addSnapshotListener((value, error) -> {
            if (error != null) {
                // Handle database error
                Log.e("AntrenamentActivity", "Error getting training sessions", error);
                return;
            }

            if (value != null) {
                trainingSessions.clear();
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    TrainingSession session = documentChange.getDocument().toObject(TrainingSession.class);
                    if (session != null) {
                        trainingSessions.add(session);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    // Implementarea metodei pentru adăugarea unei sesiuni de antrenament
    @Override
    public void onTrainingSessionAdded(TrainingSession session) {
        // Adaugă un document nou în colecția "training_sessions" din Firestore
        collectionReference.add(session)
                .addOnSuccessListener(documentReference -> Log.d("AntrenamentActivity", "Training session added successfully"))
                .addOnFailureListener(e -> Log.e("AntrenamentActivity", "Error adding training session", e));
    }

    // Metodă pentru afișarea dialogului pentru adăugarea unei sesiuni de antrenament
    private void showAddTrainingSessionDialog() {
        AddTrainingSessionDialog dialog = new AddTrainingSessionDialog();
        dialog.show(getSupportFragmentManager(), "AddTrainingSessionDialog");
    }

    // Metodă pentru verificarea rolului utilizatorului
    private void updateUserInterfaceBasedOnUserType() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            db.collection("users").document(currentUser.getUid()).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                String userType = document.getString("userType");
                                // Verifică tipul de utilizator și afișează butonul special corespunzător
                                switch (userType) {
                                    case "ANTRENOR":
                                        fabAddTrainingSession.setVisibility(View.VISIBLE);
                                        break;
                                    // Dacă utilizatorul nu este antrenor, nu afișa butonul
                                    default:
                                        fabAddTrainingSession.setVisibility(View.GONE);
                                        break;
                                }
                            } else {
                                // Utilizatorul nu are un profil în Firestore, gestionează acest caz
                            }
                        } else {
                            // Task-ul Firestore nu a fost realizat cu succes, gestionează eroarea
                        }
                    });
        }
    }
}
