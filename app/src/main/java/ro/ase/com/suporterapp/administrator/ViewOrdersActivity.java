package ro.ase.com.suporterapp.administrator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import ro.ase.com.suporterapp.R;
import ro.ase.com.suporterapp.shop.Order;

public class ViewOrdersActivity extends AppCompatActivity {

    private RecyclerView ordersRecyclerView;
    private ArrayList<Order> ordersList = new ArrayList<>();
    private OrderAdapter orderAdapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_view_orders_activity);

        ordersRecyclerView = findViewById(R.id.ordersRecyclerView);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderAdapter = new OrderAdapter(ordersList, this, () -> {
            // Optionally refresh the orders list or handle the status change
        });
        ordersRecyclerView.setAdapter(orderAdapter);

        db = FirebaseFirestore.getInstance();
        loadOrders();

        Button comenzileTrimiseButton = findViewById(R.id.btnComenziTrimise);
        comenzileTrimiseButton.setOnClickListener(v -> {
           Intent intent = new Intent(ViewOrdersActivity.this, ComenziTrimiseActivity.class);
              startActivity(intent);
        });
        Button comenzileLivrateButton = findViewById(R.id.btnComenziLivrate);
        comenzileLivrateButton.setOnClickListener(v -> {
           Intent intent = new Intent(ViewOrdersActivity.this, ComenziLivrateActivity.class);
              startActivity(intent);
        });
    }

    private void loadOrders() {
        db.collection("orders").whereEqualTo("status", "nou").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ordersList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Order order = document.toObject(Order.class);
                            ordersList.add(order);
                        }
                        orderAdapter.notifyDataSetChanged();
                    } else {
                        Log.w("ViewOrdersActivity", "Error getting documents: ", task.getException());
                    }
                });
    }
}
