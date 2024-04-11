package ro.ase.com.suporterapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import ro.ase.com.suporterapp.administrator.AdministratorActivity;
import ro.ase.com.suporterapp.games.GamesActivity;
import ro.ase.com.suporterapp.jucator.JucatorActivity;
import ro.ase.com.suporterapp.lot_echipa.TeamRosterActivity;
import ro.ase.com.suporterapp.news.NewsActivity;
import ro.ase.com.suporterapp.shop.ShopActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnEditProfile, btnTeamRoster, btnTeamCalendar, btnNews, btnShop, btnSendEmail, btnGames;
    private Button btnAdministrator, btnJucator, btnAntrenor; // Special buttons

    // Firebase instances
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Bind views for the buttons available to all users
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnTeamRoster = findViewById(R.id.btnTeamRoster);
        btnTeamCalendar = findViewById(R.id.btnTeamCalendar);
        btnNews = findViewById(R.id.btnNews);
        btnShop = findViewById(R.id.btnShop);
        btnSendEmail = findViewById(R.id.btnSendEmail);
        btnGames = findViewById(R.id.btnGames);

        // Bind views for special role buttons
        btnAdministrator = findViewById(R.id.btnAdministrator);
        btnJucator = findViewById(R.id.btnJucator);
        btnAntrenor = findViewById(R.id.btnAntrenor);

        // Check user type and update UI accordingly
        updateUserInterfaceBasedOnUserType();
        btnEditProfile.setOnClickListener(Intent -> {
            Intent intent = new Intent(MainActivity.this, EditProfilActivity.class);
            startActivity(intent);
        });
        btnTeamRoster.setOnClickListener(Intent -> {
            Intent intent = new Intent(MainActivity.this, TeamRosterActivity.class);
            startActivity(intent);
        });
        btnTeamCalendar.setOnClickListener(Intent -> {
            Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
            startActivity(intent);
        });
        btnNews.setOnClickListener(Intent -> {
            Intent intent = new Intent(MainActivity.this, NewsActivity.class);
            startActivity(intent);
        });
        btnShop.setOnClickListener(Intent -> {
            Intent intent = new Intent(MainActivity.this, ShopActivity.class);
            startActivity(intent);
        });
       btnGames.setOnClickListener(Intent -> {
            Intent intent = new Intent(MainActivity.this, GamesActivity.class);
            startActivity(intent);
        });

        btnAdministrator.setOnClickListener(Intent -> {
            Intent intent = new Intent(MainActivity.this, AdministratorActivity.class);
            startActivity(intent);
        });
        btnJucator.setOnClickListener(Intent -> {
            Intent intent = new Intent(MainActivity.this, JucatorActivity.class);
            startActivity(intent);
        });
        btnAntrenor.setOnClickListener(Intent -> {
            Intent intent = new Intent(MainActivity.this, AntrenorActivity.class);
            startActivity(intent);
        });
    }

    private void updateUserInterfaceBasedOnUserType() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            db.collection("users").document(currentUser.getUid()).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                String userType = document.getString("userType");
                                // Set visibility for special role buttons based on user type
                                switch (userType) {
                                    case "ADMINISTRATOR":
                                        btnAdministrator.setVisibility(View.VISIBLE);
                                        break;
                                    case "JUCĂTOR":
                                        btnJucator.setVisibility(View.VISIBLE);
                                        break;
                                    case "ANTRENOR":
                                        btnAntrenor.setVisibility(View.VISIBLE);
                                        break;
                                    // No special buttons for "FAN" or other roles
                                    default:
                                        break;
                                }
                            } else {
                                // The user does not have a profile in Firestore, handle this case
                            }
                        } else {
                            // Firestore task was not successful, handle the error
                        }
                    });
        }
    }
}
