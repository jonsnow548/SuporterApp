package ro.ase.com.suporterapp.antrenament;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ro.ase.com.suporterapp.R;

public class TrainingSessionAdapter extends RecyclerView.Adapter<TrainingSessionAdapter.ViewHolder> {
    private List<TrainingSession> trainingSessions;

    public TrainingSessionAdapter(List<TrainingSession> trainingSessions) {
        this.trainingSessions = trainingSessions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.antrenament_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrainingSession session = trainingSessions.get(position);
        holder.bind(session);
    }

    @Override
    public int getItemCount() {
        return trainingSessions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sessionNameTextView;
        private TextView sessionDescriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sessionNameTextView = itemView.findViewById(R.id.text_view_name);
            sessionDescriptionTextView = itemView.findViewById(R.id.text_view_description);
        }

        public void bind(TrainingSession session) {
            sessionNameTextView.setText(session.getSessionName());
            sessionDescriptionTextView.setText(session.getDescription());
        }
    }
}
