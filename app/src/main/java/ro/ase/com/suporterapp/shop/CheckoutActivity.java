package ro.ase.com.suporterapp.shop;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import ro.ase.com.suporterapp.R;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_checkout_activity);

        final TextView totalPriceTextView = findViewById(R.id.totalPriceTextView);
        final EditText addressEditText = findViewById(R.id.addressEditText);
        final EditText telephoneEditText = findViewById(R.id.phoneEditText);
        final RadioGroup paymentMethodRadioGroup = findViewById(R.id.paymentMethodRadioGroup);
        final Button confirmOrderButton = findViewById(R.id.confirmOrderButton);


        // Calcul și afișare suma totală
        double total = ShoppingCart.getTotal();
        totalPriceTextView.setText(String.format("Total: $%.2f", total));

        // Butonul de revenire
        FloatingActionButton backButton = findViewById(R.id.backButton2);
        backButton.setOnClickListener(view -> finish()); // Închide activitatea curentă

        // Listener pentru butonul de confirmare a comenzii
        confirmOrderButton.setOnClickListener(v -> {
            String address = addressEditText.getText().toString();
            String telephone = telephoneEditText.getText().toString();
            if (address.isEmpty() || telephone.isEmpty()) {
                Toast.makeText(CheckoutActivity.this, "Te rugăm să completezi toate câmpurile.", Toast.LENGTH_LONG).show();
                return;
            }

            // Verificarea validității numărului de telefon
            if (telephone.length() != 10 || !telephone.matches("\\d+")) {
                Toast.makeText(CheckoutActivity.this, "Te rugăm să introduci un număr de telefon valid.", Toast.LENGTH_LONG).show();
                return;
            }

            int selectedPaymentMethodId = paymentMethodRadioGroup.getCheckedRadioButtonId();
            String paymentMethod = selectedPaymentMethodId == R.id.cardPaymentRadioButton ? "Card" : "Cash la livrare";

            // Crearea și salvarea comenzii în Firestore
            Order order = new Order(address, total, paymentMethod, telephone);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("orders").add(order)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(CheckoutActivity.this, "Comanda a fost plasată cu succes!", Toast.LENGTH_LONG).show();
                        ShoppingCart.clear(); // Șterge produsele din coșul de cumpărături după ce comanda a fost plasată
                        finish(); // Închide activitatea
                    })
                    .addOnFailureListener(e -> Toast.makeText(CheckoutActivity.this, "Eroare la plasarea comenzii.", Toast.LENGTH_LONG).show());
        });
    }
}
