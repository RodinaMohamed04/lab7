package Backend;

import java.util.ArrayList;

public class Instructor extends User {

    private ArrayList<String> createdCourses; //ArrayList of courses created by instructor

    public Instructor(String userName, String userId, String email, String passwordHash) {
        super(userName, userId, email, passwordHash, "instructor");
        this.createdCourses = new ArrayList<>();
    }

    public void addCourse(String courseId) {
        if (!createdCourses.contains(courseId)) {
            createdCourses.add(courseId);
        }
    }

    public void removeCourse(String courseId) {
        createdCourses.remove(courseId);
    }

    public ArrayList<String> getCreatedCourses() {
        return createdCourses;
}
}