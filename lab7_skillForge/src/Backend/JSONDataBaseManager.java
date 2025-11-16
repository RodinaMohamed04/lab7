package Backend;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSONDataBaseManager {

    private static final String userF = "users.json";
    private static final String courseF = "Courses.json";

    
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

            }
            else if (u instanceof Instructor instructor) {
                obj.put("createdCourses", new JSONArray(instructor.getCreatedCourses()));
            }

            arr.put(obj);
        }

        try (FileWriter fw = new FileWriter(userF)) {
            fw.write(arr.toString(4));
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    public static List<User> readUsers() {
        List<User> users = new ArrayList<>();
        File f = new File(userF);
        if (!f.exists()) return users; 

        try {
            String json = new String(Files.readAllBytes(Paths.get(userF)));
            if (json.isBlank()) return users;

            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String role = obj.optString("role", "");

                if (role.equals("student")) {
                    Student s = new Student(
                            obj.optString("userName", ""),
                            obj.optString("userId", ""),
                            obj.optString("email", ""),
                            obj.optString("passwordHash", "")
                    );

                    JSONArray enrolled = obj.optJSONArray("enrolledCourses");
                    if (enrolled != null) {
                        for (int j = 0; j < enrolled.length(); j++) {
                            s.enrollCourse(enrolled.getString(j));
                        }
                    }

                    JSONArray progressArr = obj.optJSONArray("coursesProgress");
                    if (progressArr != null) {
                        for (int j = 0; j < progressArr.length(); j++) {
                            JSONObject p = progressArr.getJSONObject(j);
                            String courseId = p.optString("courseId", "");
                            StudentCourseProgress scp = new StudentCourseProgress(courseId);
                            JSONArray completed = p.optJSONArray("completedLessons");
                            if (completed != null) {
                                for (int k = 0; k < completed.length(); k++) {
                                    scp.completedLessons.add(completed.getString(k));
                                }
                            }
                            s.getCoursesProgress().add(scp);
                        }
                    }
                    users.add(s);

                } else if (role.equals("instructor")) {
                    Instructor ins = new Instructor(
                            obj.optString("userName", ""),
                            obj.optString("userId", ""),
                            obj.optString("email", ""),
                            obj.optString("passwordHash", "")
                    );

                    JSONArray created = obj.optJSONArray("createdCourses");
                    if (created != null) {
                        for (int j = 0; j < created.length(); j++) {
                            ins.addCourse(created.getString(j));
                        }
                    }

                    users.add(ins);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading users: " + e.getMessage());
        }
        return users;
    }

    
    public static void saveCourses(List<Courses> courses) {
        JSONArray arr = new JSONArray();
        for (Courses c : courses) {
            JSONObject obj = new JSONObject();
            obj.put("courseId", c.getCourseId());
            obj.put("courseName", c.getTitle());
            obj.put("description", c.getDescription());
            obj.put("instructorId", c.getInstructorId());

            obj.put("students", new JSONArray(c.getStudents()));

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

        try (FileWriter fw = new FileWriter(courseF)) {
            fw.write(arr.toString(4));
        } catch (IOException e) {
            System.err.println("Error saving courses: " + e.getMessage());
        }
    }

    public static List<Courses> readCourses() {
        List<Courses> courses = new ArrayList<>();
        File f = new File(courseF);
        if (!f.exists()) return courses; 

        try {
            String json = new String(Files.readAllBytes(Paths.get(courseF)));
            if (json.isBlank()) return courses;

            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Courses c = new Courses(
                        obj.optString("courseId", ""),
                        obj.optString("courseName", ""),
                        obj.optString("description", ""),
                        obj.optString("instructorId", "")
                );

                JSONArray studentsArr = obj.optJSONArray("students");
                if (studentsArr != null) {
                    for (int j = 0; j < studentsArr.length(); j++) {
                        c.enrollStudent(studentsArr.getString(j));
                    }
                }

                JSONArray lessonsArr = obj.optJSONArray("lessons");
                if (lessonsArr != null) {
                    for (int j = 0; j < lessonsArr.length(); j++) {
                        JSONObject lObj = lessonsArr.getJSONObject(j);
                        Lessons l = new Lessons(
                                lObj.optString("lessonId", ""),
                                lObj.optString("title", ""),
                                lObj.optString("content", "")
                        );
                        JSONArray res = lObj.optJSONArray("resources");
                        if (res != null) {
                            for (int k = 0; k < res.length(); k++) {
                                l.addResource(res.getString(k));
                            }
                        }
                        c.addLesson(l);
                    }
                }

                courses.add(c);
            }

        } catch (IOException e) {
            System.err.println("Error reading courses: " + e.getMessage());
        }
        return courses;
    }
}
