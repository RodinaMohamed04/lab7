package Backend;

import java.util.ArrayList;
import java.util.List;

public class Lessons {
    private String lessonId;
    private String title;
    private String content;
    private List<String> resources = new ArrayList<>();

    public Lessons(String lessonId, String title, String content) {
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
    }

    public String getLessonId() { return lessonId; }
    public String getTitle() { return title; }
    public String getContent() {
        return content; }
    public List<String> getResources() {
        return resources; }
    public void addResource(String resource) {
        resources.add(resource);
    }

}

