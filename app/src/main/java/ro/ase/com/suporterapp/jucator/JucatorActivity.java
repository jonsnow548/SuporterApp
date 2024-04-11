package ro.ase.com.suporterapp.jucator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ro.ase.com.suporterapp.R;
import ro.ase.com.suporterapp.antrenament.AntrenamentActivity;

public class JucatorActivity extends AppCompatActivity {
    private String userEmail;
    FloatingActionButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jucator_activity);
        userEmail = getIntent().getStringExtra("UserEmail");
        back= findViewById(R.id.back);

        findViewById(R.id.btnTrainingSchedule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deschide activitatea pentru vizualizarea programului de antrenamente
                Intent intent = new Intent(JucatorActivity.this, AntrenamentActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnPerformanceStats).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deschide activitatea pentru vizualizarea statisticilor de performanță
                // Intent intent = new Intent(JucatorActivity.this, PerformanceStatsActivity.class);
                // startActivity(intent);
            }
        });

        findViewById(R.id.btnTeamCommunication).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deschide activitatea sau fragmentul pentru comunicare în echipă
                Intent intent = new Intent(JucatorActivity.this, ChatActivity.class);
                intent.putExtra("UserEmail", userEmail);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
