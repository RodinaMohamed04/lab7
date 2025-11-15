
package Backend;

public class User {
    protected String userName;
    protected String userId;
    protected String email;
    protected String passwordHash;
    protected String role;

    public User(String userName, String userId, String email, String passwordHash, String role) {
        this.userName = userName;
        this.userId = userId;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    

    public String getRole() {
        return role;
    }
    
    
}
