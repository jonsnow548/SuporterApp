package ro.ase.com.suporterapp.lot_echipa;

public class Player {

    private String name;
    private String position;
    private String nationality;
    private int number;
    private int age;
    private String imageUrl;

    public Player() {
    }
    public Player(String name, String position, String nationality, int number, int age, String imageUrl) {
        this.name = name;
        this.position = position;
        this.nationality = nationality;
        this.number = number;
        this.age = age;
        this.imageUrl = imageUrl;
    }
    // Getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getNationality() {
        return nationality;

    }
    public void setNationality(String nationality) {
        this.nationality=nationality;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
