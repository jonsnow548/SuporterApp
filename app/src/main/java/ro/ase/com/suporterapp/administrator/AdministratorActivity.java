package ro.ase.com.suporterapp.administrator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ro.ase.com.suporterapp.R;
import ro.ase.com.suporterapp.games.AddQuestionActivity;
import ro.ase.com.suporterapp.login.RegisterActivity;
import ro.ase.com.suporterapp.lot_echipa.AddPlayersActivity;
import ro.ase.com.suporterapp.news.AddNewsActivity;

public class AdministratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administrator_activity);

        Button btnViewSponsors = findViewById(R.id.btnViewSponsors);
        Button btnViewBudget = findViewById(R.id.btnViewBudget);
        Button btnViewExpenses = findViewById(R.id.btnViewExpenses);
        Button btncreateplayer = findViewById(R.id.btncreateplayer);
        Button btnaddnews = findViewById(R.id.addnewsbtn);
        Button btnaddquestion = findViewById(R.id.addQuestionButton);
        Button btnViewOrders = findViewById(R.id.btnViewOrders);
        Button btnAddPlayer = findViewById(R.id.AdaugaJucator);
        btnAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorActivity.this, AddPlayersActivity.class);
                intent.putExtra("userType", "ADMINISTRATOR");
                startActivity(intent);
            }
        });
        btnViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorActivity.this, ViewOrdersActivity.class);
                startActivity(intent);
            }
        });
       btnViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorActivity.this, ViewOrdersActivity.class);
                startActivity(intent);
            }
        });
        // Setare listeneri pentru fiecare buton
        btnaddquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorActivity.this, AddQuestionActivity.class);
                startActivity(intent);
            }
        });
        btnViewSponsors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deschide activitatea/fragmentul pentru vizualizarea sponsorilor

            }
        });

        btnViewBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deschide activitatea/fragmentul pentru vizualizarea bugetului
            }
        });

        btnViewExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deschide activitatea/fragmentul pentru vizualizarea cheltuielilor
            }
        });
        btncreateplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorActivity.this, RegisterActivity.class);
                intent.putExtra("userType", "ADMINISTRATOR");
                startActivity(intent);

            }
        });
        btnaddnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministratorActivity.this, AddNewsActivity.class);
                startActivity(intent);
            }
        });

    }
}
