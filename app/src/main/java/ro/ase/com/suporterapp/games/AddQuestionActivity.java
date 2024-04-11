package ro.ase.com.suporterapp.games;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import ro.ase.com.suporterapp.R;

public class AddQuestionActivity extends AppCompatActivity {

    private EditText questionEditText, answer1EditText, answer2EditText, answer3EditText, answer4EditText, correctAnswerEditText;
    private Button saveQuestionButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.games_add_question_activity);

        db = FirebaseFirestore.getInstance();

        questionEditText = findViewById(R.id.question_edittext);
        answer1EditText = findViewById(R.id.answer1_edittext);
        answer2EditText = findViewById(R.id.answer2_edittext);
        answer3EditText = findViewById(R.id.answer3_edittext);
        answer4EditText = findViewById(R.id.answer4_edittext);
        correctAnswerEditText = findViewById(R.id.correct_answer_number_edittext);
        saveQuestionButton = findViewById(R.id.save_question_button);

        saveQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQuestion();
            }
        });
    }

    private void saveQuestion() {
        String questionText = questionEditText.getText().toString();
        String answer1 = answer1EditText.getText().toString();
        String answer2 = answer2EditText.getText().toString();
        String answer3 = answer3EditText.getText().toString();
        String answer4 = answer4EditText.getText().toString();
        String correctAnswerStr = correctAnswerEditText.getText().toString();

        if (questionText.isEmpty() || answer1.isEmpty() || answer2.isEmpty() || answer3.isEmpty() || answer4.isEmpty() || correctAnswerStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int correctAnswer = Integer.parseInt(correctAnswerStr);
        if (correctAnswer < 1 || correctAnswer > 4) {
            Toast.makeText(this, "Correct answer number must be between 1 and 4", Toast.LENGTH_SHORT).show();
            return;
        }

        Question question = new Question(questionText, answer1, answer2, answer3, answer4, correctAnswer);

        db.collection("questions")
                .add(question)
                .addOnSuccessListener(documentReference -> Toast.makeText(AddQuestionActivity.this, "Question added successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(AddQuestionActivity.this, "Error adding question", Toast.LENGTH_SHORT).show());
    }
}
