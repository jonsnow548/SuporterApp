package ro.ase.com.suporterapp.shop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import ro.ase.com.suporterapp.R;

public class ShopActivity extends AppCompatActivity {
    private RecyclerView shopRecyclerView;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity);

        // Inițializează RecyclerView
        shopRecyclerView = findViewById(R.id.shopRecyclerView);
        shopRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inițializează și setează adapterul pentru RecyclerView
        productAdapter = new ProductAdapter(this, getAvailableProducts(),
                product -> {
                    // Adaugă produsul în coș și afișează un mesaj
                    ShoppingCart.addToCart(product);
                    Toast.makeText(ShopActivity.this, product.getName() + " adăugat în coș", Toast.LENGTH_SHORT).show();
                    productAdapter.notifyDataSetChanged(); // Actualizează lista pentru a reflecta schimbările
                },
                product -> {
                    // Elimină produsul din coș și afișează un mesaj
                    ShoppingCart.removeFromCart(product);
                    Toast.makeText(ShopActivity.this, product.getName() + " eliminat din coș", Toast.LENGTH_SHORT).show();
                    productAdapter.notifyDataSetChanged(); // Actualizează lista pentru a reflecta schimbările
                });
        FloatingActionButton backButton = findViewById(R.id.backButton1);
        FloatingActionButton additems = findViewById(R.id.addItems);
        checkIfUserIsAdmin();

        // Setează vizibilitatea butonului addItems în funcție de rolul utilizatorului
        //additems.setVisibility(isAdmin ? View.VISIBLE : View.GONE);

        backButton.setOnClickListener(view -> finish());
        additems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopActivity.this, AddItemsActivity.class);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Aici poți să gestionezi revenirea la activitatea anterioară
                finish(); // Exemplu de cod pentru a închide activitatea curentă și a reveni la cea anterioară
            }
        });
        shopRecyclerView.setAdapter(productAdapter);
        Button viewCartButton = findViewById(R.id.vizualizeaza_cos);
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }
    private void checkIfUserIsAdmin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FloatingActionButton addItemsButton = findViewById(R.id.addItems);
        if (user != null) {
            String userId = user.getUid();
            FirebaseFirestore.getInstance().collection("users").document(userId)
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Boolean isAdmin = task.getResult().getBoolean("isAdmin");
                            // Ajustează vizibilitatea butonului addItems direct aici
                            runOnUiThread(() -> addItemsButton.setVisibility(isAdmin != null && isAdmin ? View.VISIBLE : View.GONE));
                        } else {
                            // Tratează cazul de eroare sau dacă documentul utilizatorului nu există
                            Log.e("ShopActivity", "Eroare la verificarea rolului de administrator", task.getException());
                            runOnUiThread(() -> addItemsButton.setVisibility(View.GONE)); // Ascunde butonul dacă există o eroare
                        }
                    });
        } else {
            // Utilizatorul nu este autentificat, tratează acest caz
            addItemsButton.setVisibility(View.GONE); // Ascunde butonul dacă utilizatorul nu este autentificat
        }
    }


    private List<Product> getAvailableProducts() {
        // Aici ar trebui să returnezi lista reală de produse disponibile
        // De exemplu, dintr-o bază de date sau un serviciu web
        // Momentan, returnăm o listă de test
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1, "Tricou", "Tricou din bumbac 100%", 250.0, R.drawable.tricou));
        products.add(new Product(2, "Eșarfă", "Eșarfă din lână fină", 35.0, R.drawable.esarfa));
        products.add(new Product(3, "Geacă", "Geacă de iarnă, rezistentă la apă", 150.0, R.drawable.geaca));
        return products;
    }
}
