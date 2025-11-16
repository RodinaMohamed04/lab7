/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

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

    public Courses(String courseName, int creditHours) {
        this.courseName = courseName;
        this.creditHours = creditHours;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }
    
}

