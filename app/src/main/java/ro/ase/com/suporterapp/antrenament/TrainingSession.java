package ro.ase.com.suporterapp.antrenament;

public class TrainingSession {
    private String sessionName;
    private String description;

    public TrainingSession() {
        // Constructorul gol necesar pentru Firebase
    }

    public TrainingSession(String sessionName, String description) {
        this.sessionName = sessionName;
        this.description = description;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
