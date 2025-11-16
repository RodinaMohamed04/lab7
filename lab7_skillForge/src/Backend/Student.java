package Backend;

import java.util.ArrayList;

// Class to track progress per course without Map
class StudentCourseProgress {


    String courseId;
    ArrayList<String> completedLessons;

    public StudentCourseProgress(String courseId) {
        this.courseId = courseId;
        this.completedLessons = new ArrayList<>();
    }


}
public class Student extends User {
    private ArrayList<String> enrolledCourses;
    private ArrayList<StudentCourseProgress> coursesProgress;

    public Student(String userName, String userId, String email, String passwordHash) {
        super(userName, userId, email, passwordHash, "student");
        this.enrolledCourses = new ArrayList<>();
        this.coursesProgress = new ArrayList<>();
    }
    public void enrollCourse(String courseId) {
        if (!enrolledCourses.contains(courseId)) {
            enrolledCourses.add(courseId);
            coursesProgress.add(new StudentCourseProgress(courseId));
        }
    }
    public void markLessonCompleted(String courseId, String lessonId) {
        for (StudentCourseProgress progress : coursesProgress) {
            if (progress.courseId.equals(courseId) && !progress.completedLessons.contains(lessonId)) {
                progress.completedLessons.add(lessonId);
                break;
            }
        }
    }

    public ArrayList<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    public ArrayList<StudentCourseProgress> getCoursesProgress() {
        return coursesProgress;
    }
}