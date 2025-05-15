package model;

import java.util.ArrayList;

public class Student {
    private String studentId;
    private String name;
    private String phoneNumber;
    private String email;
    private String gender;
    private String district;
    private String password; // Password field
    private ArrayList<Course> registeredCourses;

    public Student(String studentId, String name, String phoneNumber, String email, String gender, String district) {
        this.studentId = studentId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.district = district;
        this.password = "student123";
        this.registeredCourses = new ArrayList<>();
    }

    public Student(String studentId, String name, String phoneNumber, String email, String gender, String district, String password) {
        this.studentId = studentId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.district = district;
        this.password = password;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public boolean registerCourse(Course course) {
        if (course == null) {
            return false;
        }
        if (course.getRegisteredStudents().size() < course.getCapacity() && !registeredCourses.contains(course)) {
            course.addStudent(this);
            registeredCourses.add(course);
            return true;
        }
        return false;
    }

    public boolean dropCourse(Course course) {
        if (course == null) {
            return false;
        }
        if (registeredCourses.contains(course)) {
            course.removeStudent(this);
            registeredCourses.remove(course);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return studentId + "," + name + "," + phoneNumber + "," + email + "," + gender + "," + district + "," + password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentId != null && studentId.equals(student.studentId);
    }

    @Override
    public int hashCode() {
        return studentId != null ? studentId.hashCode() : 0;
    }
}