package ro.ase.com.suporterapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import ro.ase.com.suporterapp.R;
import ro.ase.com.suporterapp.UserProfile;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText, nameEditText, ageEditText;
    private Spinner userTypeSpinner;
    private Button registerButton;
    private FirebaseAuth mAuth; // Firebase Authentication instance
    private FirebaseFirestore db; // Firestore instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Bind views
        emailEditText = findViewById(R.id.registerEmailEditText);
        passwordEditText = findViewById(R.id.registerPasswordEditText);
        nameEditText = findViewById(R.id.nameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        userTypeSpinner = findViewById(R.id.userTypeSpinner);
        registerButton = findViewById(R.id.registerButton);

        // Primește tipul de utilizator trimis din LoginActivity sau AdministratorActivity
        String userType = getIntent().getStringExtra("userType");

        // Setează valoarea spinner-ului în funcție de tipul de utilizator primit
        if (userType != null && userType.equals("ADMINISTRATOR")) {
            // Dacă tipul de utilizator este ADMINISTRATOR, lasă utilizatorul să selecteze
            // tipul de utilizator folosind spinner-ul
            userTypeSpinner.setEnabled(true);
        } else {
            // Dacă tipul de utilizator este FAN, setează valoarea implicită și dezactivează spinner-ul
            userTypeSpinner.setSelection(getIndex(userTypeSpinner, "FAN"));
            userTypeSpinner.setEnabled(false);
        }

        // Set click listener for the register button
        registerButton.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String ageStr = ageEditText.getText().toString().trim();
        String userType = userTypeSpinner.getSelectedItem().toString();

        // Basic validation
        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || ageStr.isEmpty()) {
            Toast.makeText(this, "All fields must be filled in.", Toast.LENGTH_SHORT).show();
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid age.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register the user in Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // After a successful registration, save the additional user information
                        saveUserProfile(mAuth.getCurrentUser().getUid(), name, age, userType);
                    } else {
                        // If sign in fails, display a message to the user
                        Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserProfile(String userId, String name, int age, String userType) {
        UserProfile profile = new UserProfile(name, age, userType);
        db.collection("users").document(userId)
                .set(profile)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(RegisterActivity.this, "Registration successful. Please log in.", Toast.LENGTH_SHORT).show();
                    // Redirect the user to LoginActivity
                    redirectToLoginActivity();
                })
                .addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, "Failed to save user profile: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void redirectToLoginActivity() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Finish this activity so the user can't go back to it
    }

    // Metodă pentru a obține indexul unui element în Spinner
    private int getIndex(Spinner spinner, String myString) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        if (adapter != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItem(i).toString().equalsIgnoreCase(myString)) {
                    return i;
                }
            }
        }
        return 0;
    }
}
