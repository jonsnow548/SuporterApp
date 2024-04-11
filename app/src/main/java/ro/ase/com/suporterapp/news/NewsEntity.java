package ro.ase.com.suporterapp.news;

public class NewsEntity {
    private String title;
    private String description;
    private String imageUrl;

    public NewsEntity() {
    }

    public NewsEntity(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
