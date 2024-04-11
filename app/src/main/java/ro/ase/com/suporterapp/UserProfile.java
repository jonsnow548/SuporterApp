package ro.ase.com.suporterapp;

public class UserProfile {
    private String name;
    private int age;
    private String userType;

    // Constructor gol necesar pentru Firebase
    public UserProfile() {
    }

    // Constructor cu parametri
    public UserProfile(String name, int age, String userType) {
        this.name = name;
        this.age = age;
        this.userType = userType;
    }

    // Getteri și Setteri
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
