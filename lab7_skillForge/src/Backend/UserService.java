package Backend;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private List<User> users;

    public UserService() {
        users = JSONDataBaseManager.readUsers(); // load from JSON
        if (users == null) users = new ArrayList<>();
    }

    // ---------------- Register ----------------
    public boolean registerStudent(String userName, String userId, String email, String passwordHash) {
        if (getUserById(userId) != null) return false;

        Student s = new Student(userName, userId, email, passwordHash);
        users.add(s);
        JSONDataBaseManager.saveUsers(users);
        return true;
    }

    public boolean registerInstructor(String userName, String userId, String email, String passwordHash) {
        if (getUserById(userId) != null) return false;

        Instructor i = new Instructor(userName, userId, email, passwordHash);
        users.add(i);
        JSONDataBaseManager.saveUsers(users);
        return true;
    }

    // ---------------- Login ----------------
    public User login(String email, String hashedPassword) {
        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPasswordHash().equals(hashedPassword)) {
                return u;
            }
        }
        return null;
    }

    // ---------------- Utilities ----------------
    public User getUserById(String userId) {
        for (User u : users) {
            if (u.getUserId().equals(userId)) return u;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void updateUsers() {
        JSONDataBaseManager.saveUsers(users); // save changes
    }
}
