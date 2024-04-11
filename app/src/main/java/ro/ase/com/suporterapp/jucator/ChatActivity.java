package ro.ase.com.suporterapp.jucator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ro.ase.com.suporterapp.R;

public class ChatActivity extends AppCompatActivity {
    private EditText editTextMessage;
    private Button buttonSend;
    private FloatingActionButton backButton;
    private RecyclerView chatRecyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messages;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jucator_chat_activity);

        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
// Ascunde titlul implicit al Toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
// Setează orice proprietăți dorite pentru toolbarTitle aici, dacă este necesar

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        messages = new ArrayList<>();

        messageAdapter = new MessageAdapter(new ArrayList<>());
        chatRecyclerView.setAdapter(messageAdapter);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonSend.setOnClickListener(v -> sendMessage());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true); // Afișează elementele noi la partea de jos
        chatRecyclerView.setLayoutManager(layoutManager);
        loadMessages();
    }

    private void sendMessage() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null && !editTextMessage.getText().toString().isEmpty()) {
            // Obțineți documentul utilizatorului curent din Firestore
            db.collection("users").document(currentUser.getUid()).get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    // Extrageți numele utilizatorului din document
                    String userName = documentSnapshot.getString("name");
                    if (userName == null) userName = "Anonim";
                    String text = editTextMessage.getText().toString();

                    // Crearea și trimiterea mesajului
                    Message message = new Message(userName, text);
                    // După adăugarea succesului mesajului în Firestore:
                    db.collection("messages").add(message)
                            .addOnSuccessListener(documentReference -> {
                                Toast.makeText(ChatActivity.this, "Mesaj trimis.", Toast.LENGTH_SHORT).show();
                                editTextMessage.setText("");
                                loadMessages(); // Reîncarcă mesajele
                                // Adaugă următoarea linie după reîncărcarea mesajelor pentru a derula automat
                                chatRecyclerView.post(() -> chatRecyclerView.scrollToPosition(messages.size() - 1));
                            })
                            .addOnFailureListener(e -> Toast.makeText(ChatActivity.this, "Eroare la trimiterea mesajului.", Toast.LENGTH_SHORT).show());

                } else {
                    Toast.makeText(this, "Profilul utilizatorului nu a fost găsit.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(e -> Toast.makeText(ChatActivity.this, "Eroare la obținerea datelor utilizatorului.", Toast.LENGTH_SHORT).show());
        }
    }


    private void loadMessages() {
        db.collection("messages")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        messages.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Message message = document.toObject(Message.class);
                            messages.add(message);
                        }
                        Collections.reverse(messages); // Asigură-te că cel mai recent mesaj este la sfârșitul listei
                        messageAdapter.setMessages(messages);
                        if (!messages.isEmpty()) {
                            chatRecyclerView.scrollToPosition(messages.size() - 1);
                        }
                    } else {
                        Toast.makeText(ChatActivity.this, "Eroare la încărcarea mesajelor.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
