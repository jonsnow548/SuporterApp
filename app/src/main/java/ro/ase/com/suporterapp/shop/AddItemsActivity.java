package ro.ase.com.suporterapp.shop;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ro.ase.com.suporterapp.R;

public class AddItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_add_items_activity);

        final EditText etProductName = findViewById(R.id.etProductName);
        final EditText etProductDescription = findViewById(R.id.etProductDescription);
        final EditText etProductPrice = findViewById(R.id.etProductPrice);
        Button btnAddProduct = findViewById(R.id.btnAddProduct);

        btnAddProduct.setOnClickListener(v -> {
            String name = etProductName.getText().toString();
            String description = etProductDescription.getText().toString();
            double price = Double.parseDouble(etProductPrice.getText().toString());
            int imageResourceId = R.drawable.ic_launcher_background; // Exemplu de resursă alocată static

            // Aici ar trebui să adaugi produsul în lista ta de produse sau în baza de date
            // De exemplu, adăugarea unui produs nou în Firestore
            Product newProduct = new Product(0, name, description, price, imageResourceId);

            // Pentru simplificare, afișăm un Toast
            Toast.makeText(this, "Produs adăugat: " + name, Toast.LENGTH_SHORT).show();

            // Închide activitatea
            finish();
        });
    }
}
