package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Course {
    private String courseCode;
    private String title;
    private int capacity;
    private LinkedHashMap<Student, Long> registeredStudents = new LinkedHashMap<>(); // Track registration time

    public Course(String courseCode, String title, int capacity) {
        this.courseCode = courseCode;
        this.title = title;
        this.capacity = capacity;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean hasAvailableSlots() {
        return registeredStudents.size() < capacity;
    }

    public void addStudent(Student student) {
        if (hasAvailableSlots()) {
            registeredStudents.put(student, System.currentTimeMillis()); // Track registration time
        }
    }

    public void removeStudent(Student student) {
        registeredStudents.remove(student);
    }

    public ArrayList<Student> getRegisteredStudents() {
        return new ArrayList<>(registeredStudents.keySet());
    }

    public LinkedHashMap<Student, Long> getRegisteredStudentsWithTime() {
        return registeredStudents;
    }

    public void setTitle(String title) {
        if (title != null && !title.trim().isEmpty()) {
            this.title = title.trim();
        }
    }

    public void setCapacity(int capacity) {
        if (capacity > 0) {
            this.capacity = capacity;
        }
    }

    @Override
    public String toString() {
        return courseCode + "," + title + "," + capacity;
    }



}