package evarancorp.stression;

/**
 * Created by evaran on 11.04.2018.
 */

public class LessonEntry {
    private String time;
    private String course;
    private String aud;
    private String type;

    public LessonEntry() {}
    public LessonEntry(String time, String course, String aud, String type)
    {
        this.time = time;
        this.aud = aud;
        this.course = course;
        this.type = type;
    }


    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public String getAud() {
        return aud;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
