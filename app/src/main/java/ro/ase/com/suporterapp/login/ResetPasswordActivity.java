package ro.ase.com.suporterapp.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import ro.ase.com.suporterapp.R;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText emailResetEditText;
    private Button resetPasswordButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_reset_password);

        mAuth = FirebaseAuth.getInstance();

        emailResetEditText = findViewById(R.id.emailResetEditText);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);

        resetPasswordButton.setOnClickListener(v -> resetPassword());
    }

    private void resetPassword() {
        String email = emailResetEditText.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(ResetPasswordActivity.this, "Please enter your email address.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ResetPasswordActivity.this, "Reset link sent to your email.", Toast.LENGTH_SHORT).show();
                        finish(); // Optionally close the activity
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
