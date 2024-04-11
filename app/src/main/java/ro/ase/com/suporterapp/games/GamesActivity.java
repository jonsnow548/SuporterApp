package ro.ase.com.suporterapp.games;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ro.ase.com.suporterapp.R;

public class GamesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.games_activity);
        Button btnQuiz;
        Button btnPredictor;
        btnQuiz = findViewById(R.id.btnQuiz);
        btnQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(GamesActivity.this, QuizActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btnPredictor).setOnClickListener(v -> startActivity(new Intent(GamesActivity.this, PredictorActivity.class)));
    }}
