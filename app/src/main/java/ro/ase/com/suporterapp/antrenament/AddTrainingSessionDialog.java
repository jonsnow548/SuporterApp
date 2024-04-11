package ro.ase.com.suporterapp.antrenament;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import ro.ase.com.suporterapp.R;

public class AddTrainingSessionDialog extends DialogFragment {
    private EditText etSessionName;
    private EditText etSessionDescription;
    private Button btnAdd;
    private AddTrainingSessionListener listener;

    public interface AddTrainingSessionListener {
        void onTrainingSessionAdded(TrainingSession session);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (AddTrainingSessionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement AddTrainingSessionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_training_session, null);

        etSessionName = view.findViewById(R.id.etSessionName);
        etSessionDescription = view.findViewById(R.id.etSessionDescription);
        btnAdd = view.findViewById(R.id.btnAdd);

        builder.setView(view)
                .setTitle("Add Training Session")
                .setNegativeButton("Cancel", (dialog, which) -> dismiss());

        btnAdd.setOnClickListener(v -> {
            String sessionName = etSessionName.getText().toString().trim();
            String sessionDescription = etSessionDescription.getText().toString().trim();
            if (!sessionName.isEmpty()) {
                TrainingSession session = new TrainingSession(sessionName, sessionDescription);
                listener.onTrainingSessionAdded(session);
                dismiss();
            } else {
                etSessionName.setError("Session name cannot be empty");
            }
        });

        return builder.create();
    }
}

