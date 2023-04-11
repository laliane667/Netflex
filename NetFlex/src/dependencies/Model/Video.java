package dependencies.Model;

import java.sql.Date;

public class Video {
    private int id;
    private String title;
    private String description;
    private int duration;
    private Date year;
    private String streamPath;
    private String thumbnailPath;
    private int startTime = 0;

    public Video(int id, String title, String description, int duration, Date year, String streamPath, String thumbnailPath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.year = year;
        this.streamPath = streamPath;
        this.thumbnailPath = thumbnailPath;
    }

    public Video(String title, String description, int duration, Date year, String streamPath, String thumbnailPath) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.year = year;
        this.streamPath = streamPath;
        this.thumbnailPath = thumbnailPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getStreamPath() {
        return streamPath;
    }

    public void setStreamPath(String streamPath) {
        this.streamPath = streamPath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
}
