package ro.ase.com.suporterapp.jucator;

public class Message {
    private String text;
    private String userName; // Numele utilizatorului care a scris mesajul
    private long timestamp;

    public Message() {
        // Constructor necesar pentru Firebase
    }

    public Message(String userName, String text) {
        this.userName = userName;
        this.text = text;
        this.timestamp = System.currentTimeMillis(); // Salvarea timestamp-ului mesajului
    }

    // Getteri și setteri
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
