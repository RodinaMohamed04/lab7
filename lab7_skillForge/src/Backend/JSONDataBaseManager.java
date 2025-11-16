package Backend;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSONDataBaseManager {

    private static final String USERS_FILE = "users.json";
    private static final String COURSES_FILE = "courses.json";

    // ------------------- Users -------------------
    public static void saveUsers(List<User> users) {
        JSONArray arr = new JSONArray();
        for (User u : users) {
            JSONObject obj = new JSONObject();
            obj.put("userId", u.getUserId());
            obj.put("userName", u.getUserName());
            obj.put("email", u.getEmail());
            obj.put("passwordHash", u.getPasswordHash());
            obj.put("role", u.getRole());

            if (u instanceof Student student) {
                JSONArray enrolledCourses = new JSONArray(student.getEnrolledCourses());
                obj.put("enrolledCourses", enrolledCourses);

                JSONArray coursesProgress = new JSONArray();
                for (StudentCourseProgress progress : student.getCoursesProgress()) {
                    JSONObject p = new JSONObject();
                    p.put("courseId", progress.courseId);
                    p.put("completedLessons", new JSONArray(progress.completedLessons));
                    coursesProgress.put(p);
                }
                obj.put("coursesProgress", coursesProgress);

            } else if (u instanceof Instructor instructor) {
                obj.put("createdCourses", new JSONArray(instructor.getCreatedCourses()));
            }

            arr.put(obj);
        }

        try (FileWriter fw = new FileWriter(USERS_FILE)) {
            fw.write(arr.toString(4)); // 4 spaces for pretty print
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<User> readUsers() {
        List<User> users = new ArrayList<>();
        try {
            String json = new String(Files.readAllBytes(Paths.get(USERS_FILE)));
            JSONArray arr = new JSONArray(json);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String role = obj.getString("role");

                if (role.equals("student")) {
                    Student s = new Student(
                            obj.getString("userName"),
                            obj.getString("userId"),
                            obj.getString("email"),
                            obj.getString("passwordHash")
                    );

                    JSONArray enrolled = obj.getJSONArray("enrolledCourses");
                    for (int j = 0; j < enrolled.length(); j++)
                        s.enrollCourse(enrolled.getString(j));

                    JSONArray progressArr = obj.getJSONArray("coursesProgress");
                    for (int j = 0; j < progressArr.length(); j++) {
                        JSONObject p = progressArr.getJSONObject(j);
                        String courseId = p.getString("courseId");
                        StudentCourseProgress scp = new StudentCourseProgress(courseId);
                        JSONArray completed = p.getJSONArray("completedLessons");
                        for (int k = 0; k < completed.length(); k++)
                            scp.completedLessons.add(completed.getString(k));
                        s.getCoursesProgress().add(scp);
                    }
                    users.add(s);

                } else if (role.equals("instructor")) {
                    Instructor ins = new Instructor(
                            obj.getString("userName"),
                            obj.getString("userId"),
                            obj.getString("email"),
                            obj.getString("passwordHash")
                    );

                    JSONArray created = obj.getJSONArray("createdCourses");
                    for (int j = 0; j < created.length(); j++)
                        ins.addCourse(created.getString(j));

                    users.add(ins);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    // ------------------- Courses -------------------
    public static void saveCourses(List<Courses> courses) {
        JSONArray arr = new JSONArray();
        for (Courses c : courses) {
            JSONObject obj = new JSONObject();
            obj.put("courseId", c.getCourseId());
            obj.put("courseName", c.getTitle());
            obj.put("description", c.getDescription());
            obj.put("instructorId", c.getInstructorId());

            // students
            obj.put("students", new JSONArray(c.getStudents()));

            // lessons
            JSONArray lessonsArr = new JSONArray();
            for (Lessons l : c.getLessons()) {
                JSONObject lObj = new JSONObject();
                lObj.put("lessonId", l.getLessonId());
                lObj.put("title", l.getTitle());
                lObj.put("content", l.getContent());
                lObj.put("resources", new JSONArray(l.getResources()));
                lessonsArr.put(lObj);
            }
            obj.put("lessons", lessonsArr);

            arr.put(obj);
        }

        try (FileWriter fw = new FileWriter(COURSES_FILE)) {
            fw.write(arr.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Courses> readCourses() {
        List<Courses> courses = new ArrayList<>();
        try {
            String json = new String(Files.readAllBytes(Paths.get(COURSES_FILE)));
            JSONArray arr = new JSONArray(json);

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Courses c = new Courses(
                        obj.getString("courseId"),
                        obj.getString("courseName"),
                        obj.getString("description"),
                        obj.getString("instructorId")
                );

                // students
                JSONArray studentsArr = obj.getJSONArray("students");
                for (int j = 0; j < studentsArr.length(); j++)
                    c.enrollStudent(studentsArr.getString(j));

                // lessons
                JSONArray lessonsArr = obj.getJSONArray("lessons");
                for (int j = 0; j < lessonsArr.length(); j++) {
                    JSONObject lObj = lessonsArr.getJSONObject(j);
                    Lessons l = new Lessons(
                            lObj.getString("lessonId"),
                            lObj.getString("title"),
                            lObj.getString("content")
                    );
                    JSONArray res = lObj.getJSONArray("resources");
                    for (int k = 0; k < res.length(); k++)
                        l.addResource(res.getString(k));
                    c.addLesson(l);
                }

                courses.add(c);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return courses;
    }
}
