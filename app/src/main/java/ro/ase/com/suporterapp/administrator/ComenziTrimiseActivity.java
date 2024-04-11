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

public class ComenziTrimiseActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private ArrayList<Order> ordersList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_comenzi_trimise);

        recyclerView = findViewById(R.id.recyclerViewComenziTrimise);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OrderAdapter(ordersList, this, this::loadOrders);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        loadOrders();
    }

    private void loadOrders() {
        db.collection("orders")
                .whereEqualTo("status", "trimis")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.w("ComenziTrimiseActivity", "Listen failed.", e);
                        return;
                    }

                    ordersList.clear();
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Order order = doc.toObject(Order.class);
                            ordersList.add(order);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d("ComenziTrimiseActivity", "Current data: null");
                        Toast.makeText(this, "Nu există comenzi trimise.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
