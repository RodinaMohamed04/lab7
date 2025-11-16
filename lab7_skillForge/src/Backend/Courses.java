/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rodina Mohamed
 */
public class Courses {
    private String courseId;
    private String courseName;
    private String description;
    private String instructorId;
    private int creditHours;
    private List<Lessons> lessons = new ArrayList<>();
    private List<String> students = new ArrayList<>();

    public Courses(String courseId, String courseName, String description, String instructorId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.description = description;
        this.instructorId = instructorId;
    }

    public String getCourseId() { return courseId; }
    public String getTitle() { return courseName; }
    public String getDescription() { return description; }
    public String getInstructorId() { return instructorId; }
    public List<Lessons> getLessons() { return lessons; }
    public List<String> getStudents() { return students; }

    public void addLesson(Lessons l) {
        lessons.add(l);
    }
    public void removeLessonById(String lessonId) {

        lessons.removeIf(x -> x.getLessonId().equals(lessonId));
    }
    public void enrollStudent(String userId) {
        if (!students.contains(userId)) students.add(userId);
    }
}



