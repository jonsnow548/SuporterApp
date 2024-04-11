package ro.ase.com.suporterapp.lot_echipa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import ro.ase.com.suporterapp.R;

public class AddPlayersActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText nameInput, numberInput, ageInput;
    private Spinner positionSpinner, nationalitySpinner;
    private ImageView playerImageView;
    private Uri imageUri;
    private Button uploadButton, saveButton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference("uploads");
    private List<String> getCountries() {
        Locale[] locales = Locale.getAvailableLocales();
        Set<String> countries = new TreeSet<>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (!country.isEmpty()) {
                countries.add(country);
            }
        }
        return new ArrayList<>(countries);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lot_echipa_add_player_activity);

        // Initialize inputs
        nameInput = findViewById(R.id.nameInput);
        numberInput = findViewById(R.id.numberInput);
        ageInput = findViewById(R.id.ageInput);
        positionSpinner = findViewById(R.id.positionSpinner);
        nationalitySpinner = findViewById(R.id.nationalitySpinner);
        playerImageView = findViewById(R.id.playerImageView);
        uploadButton = findViewById(R.id.uploadButton);
        saveButton = findViewById(R.id.saveButton);

        uploadButton.setOnClickListener(v -> openFileChooser());
        saveButton.setOnClickListener(v -> uploadFile());

        setUpSpinners();
    }

    private void setUpSpinners() {
        ArrayAdapter<CharSequence> positionAdapter = ArrayAdapter.createFromResource(this,
                R.array.positions_array, android.R.layout.simple_spinner_item);
        positionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        positionSpinner.setAdapter(positionAdapter);

        // Setup pentru nationalitySpinner
        ArrayAdapter<String> nationalityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getCountries());
        nationalityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nationalitySpinner.setAdapter(nationalityAdapter);
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            playerImageView.setImageURI(imageUri);
        }
    }

    private String getFileExtension(Uri uri) {
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(getContentResolver().getType(uri));
    }

    private void uploadFile() {
        if (imageUri != null) {
            StorageReference fileReference = storageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        savePlayer(imageUrl);
                    }))
                    .addOnFailureListener(e -> Toast.makeText(AddPlayersActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void savePlayer(String imageUrl) {
        String name = nameInput.getText().toString().trim();
        String position = positionSpinner.getSelectedItem().toString();
        String nationality = nationalitySpinner.getSelectedItem().toString();
        int number = Integer.parseInt(numberInput.getText().toString().trim());
        int age = Integer.parseInt(ageInput.getText().toString().trim());

        Player player = new Player(name, position, nationality, number, age, imageUrl);
        db.collection("players").add(player)
                .addOnSuccessListener(documentReference -> Toast.makeText(AddPlayersActivity.this, "Player added", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(AddPlayersActivity.this, "Error adding player", Toast.LENGTH_SHORT).show());
    }
}

