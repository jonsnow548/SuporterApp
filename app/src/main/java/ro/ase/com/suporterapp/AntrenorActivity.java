package ro.ase.com.suporterapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ro.ase.com.suporterapp.antrenament.AntrenamentActivity;
import ro.ase.com.suporterapp.jucator.ChatActivity;

public class AntrenorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.antrenor_activity);

        // Inițializare butoane
        Button btnViewTeamSchedule = findViewById(R.id.btnViewTeamSchedule);
        Button btnViewPlayerStats = findViewById(R.id.btnViewPlayerStats);
        Button btnCommunicateWithPlayers = findViewById(R.id.btnCommunicateWithPlayers);
        Button btnManageTrainingSessions = findViewById(R.id.btnManageTrainingSessions);

        // Setare listener pentru butoane
        /*btnViewTeamSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deschide activitatea pentru vizualizarea programului echipei
                Intent intent = new Intent(AntrenorActivity.this, .class);
                startActivity(intent);
            }
        });

        btnViewPlayerStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deschide activitatea pentru vizualizarea statisticilor jucătorilor
                Intent intent = new Intent(AntrenorActivity.this, ViewPlayerStatsActivity.class);
                startActivity(intent);
            }
        });*/

        btnCommunicateWithPlayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deschide activitatea pentru comunicare cu jucătorii
                Intent intent = new Intent(AntrenorActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

        btnManageTrainingSessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deschide activitatea pentru administrarea sesiunilor de antrenament
                Intent intent = new Intent(AntrenorActivity.this, AntrenamentActivity.class);
                startActivity(intent);
            }
        });
    }
}
