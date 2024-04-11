package ro.ase.com.suporterapp.administrator;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import ro.ase.com.suporterapp.R;
import ro.ase.com.suporterapp.shop.Order;

public class ComenziLivrateActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private ArrayList<Order> ordersList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_comenzi_livrate);

        recyclerView = findViewById(R.id.recyclerViewComenziLivrate);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inițializează OrderAdapter cu un listener null, dacă nu ai nevoie de acțiuni la schimbarea stării comenzilor aici
        adapter = new OrderAdapter(ordersList, this, null);

        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        loadDeliveredOrders();
    }

    private void loadDeliveredOrders() {
        db.collection("orders").whereEqualTo("status", "livrat")
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.w("ComenziLivrateActivity", "Listen failed.", error);
                        return;
                    }

                    ordersList.clear();
                    if (value != null) {
                        for (QueryDocumentSnapshot doc : value) {
                            ordersList.add(doc.toObject(Order.class));
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Nu există comenzi livrate.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
