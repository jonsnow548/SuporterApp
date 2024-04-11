package ro.ase.com.suporterapp.games;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import ro.ase.com.suporterapp.R;

public class QuizActivity extends AppCompatActivity {
    private List<Question> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;

    private TextView textViewQuestion;
    private RadioGroup radioGroupAnswers;
    private RadioButton radioButtonAnswer1, radioButtonAnswer2, radioButtonAnswer3, radioButtonAnswer4;
    private Button buttonNext;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.games_quiz_activity);

        textViewQuestion = findViewById(R.id.textView_question);
        radioGroupAnswers = findViewById(R.id.radioGroup_answers);
        radioButtonAnswer1 = findViewById(R.id.radioButton_answer1);
        radioButtonAnswer2 = findViewById(R.id.radioButton_answer2);
        radioButtonAnswer3 = findViewById(R.id.radioButton_answer3);
        radioButtonAnswer4 = findViewById(R.id.radioButton_answer4);
        buttonNext = findViewById(R.id.button_next);

        db = FirebaseFirestore.getInstance(); // Inițializează Firestore

        loadQuestions(); // Încarcă întrebările

        buttonNext.setOnClickListener(v -> checkAnswerAndProceed());
    }

    private void loadQuestions() {
        db.collection("questions")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        questions.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Question question = document.toObject(Question.class);
                            questions.add(question);
                        }
                        if (!questions.isEmpty()) {
                            currentQuestionIndex = 0;
                            displayQuestion(); // Afișează prima întrebare
                        } else {
                            Toast.makeText(this, "Nu există întrebări disponibile.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Eroare la încărcarea întrebărilor.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void displayQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        textViewQuestion.setText(currentQuestion.getQuestionText());
        radioButtonAnswer1.setText(currentQuestion.getAnswer1());
        radioButtonAnswer2.setText(currentQuestion.getAnswer2());
        radioButtonAnswer3.setText(currentQuestion.getAnswer3());
        radioButtonAnswer4.setText(currentQuestion.getAnswer4());
        radioGroupAnswers.clearCheck(); // Curăță selecția anterioară
    }

    private void checkAnswerAndProceed() {
        int selectedAnswerIndex = getSelectedAnswerIndex();
        if (selectedAnswerIndex == -1) {
            Toast.makeText(this, "Selectează un răspuns!", Toast.LENGTH_SHORT).show();
            return;
        }

        Question currentQuestion = questions.get(currentQuestionIndex);
        if (selectedAnswerIndex + 1 == currentQuestion.getCorrectAnswer()) {
            score += 10;
            Toast.makeText(this, "Răspuns corect! +10 puncte", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Răspuns greșit!", Toast.LENGTH_SHORT).show();
        }

        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            displayQuestion();
        } else {
            showFinalResult();
        }
    }

    private int getSelectedAnswerIndex() {
        int selectedRadioButtonId = radioGroupAnswers.getCheckedRadioButtonId();
        if (selectedRadioButtonId == R.id.radioButton_answer1) return 0;
        if (selectedRadioButtonId == R.id.radioButton_answer2) return 1;
        if (selectedRadioButtonId == R.id.radioButton_answer3) return 2;
        if (selectedRadioButtonId == R.id.radioButton_answer4) return 3;
        return -1; // Niciun răspuns selectat
    }
    private void showFinalResult() {
        // Afișează un mesaj către utilizator cu rezultatul final
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.games_quiz_final_score, null);
        builder.setView(dialogView);

        TextView textViewFinalScore = dialogView.findViewById(R.id.textView_final_score);
        TextView textViewMessage = dialogView.findViewById(R.id.textView_message);
        Button buttonReturnToGames = dialogView.findViewById(R.id.button_return_to_games);

        textViewFinalScore.setText("Scor final: " + score);
        textViewMessage.setText("Felicitări, esti un suporter adevarat! Ai terminat cu succes quiz-ul!");

        final AlertDialog dialog = builder.create();

        buttonReturnToGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }
}
