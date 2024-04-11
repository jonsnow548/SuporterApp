package ro.ase.com.suporterapp.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ro.ase.com.suporterapp.R;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_cart_activity);

        // Inițializează RecyclerView
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inițializează și setează adapterul pentru RecyclerView
        // Folosim ShoppingCart.CART_ITEMS pentru a obține produsele din coș
        productAdapter = new ProductAdapter(this, ShoppingCart.getCartItems(),
                product -> {
                    // Acest callback poate fi gol în CartActivity,
                    // deoarece în contextul coșului de cumpărături, probabil nu vei adăuga produse direct din această activitate.
                },
                product -> {
                    // Callback pentru eliminarea unui produs din coș
                    ShoppingCart.removeFromCart(product);
                    Toast.makeText(CartActivity.this, product.getName() + " a fost eliminat din coș", Toast.LENGTH_SHORT).show();
                    productAdapter.notifyDataSetChanged(); // Actualizează lista pentru a reflecta schimbările
                });

        cartRecyclerView.setAdapter(productAdapter);


        FloatingActionButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aici poți să gestionezi revenirea la activitatea anterioară
                finish(); // Exemplu de cod pentru a închide activitatea curentă și a reveni la cea anterioară
            }
        });

        // Buton pentru finalizarea comenzii
        Button checkoutButton = findViewById(R.id.checkoutButton);

        checkoutButton.setOnClickListener(v -> {
            // Verifică dacă coșul de cumpărături este gol
            if (ShoppingCart.getCartItems().isEmpty()) {
                // Afisează un Toast dacă coșul este gol
                Toast.makeText(CartActivity.this, "Nu aveți niciun articol selectat în coș.", Toast.LENGTH_LONG).show();
            } else {
                // Dacă coșul nu este gol, deschide activitatea de Checkout
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        productAdapter.notifyDataSetChanged(); // Asigură-te că lista este actualizată când revii în activitate
    }
}

