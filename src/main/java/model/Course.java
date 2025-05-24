//This part is implimented By Rajin(IT24100135)
package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Course {
    private String courseCode;
    private String title;
    private int capacity;
    private LinkedHashMap<Student, Long> registeredStudents = new LinkedHashMap<>();

    public Course(String courseCode, String title, int capacity) {
        this.courseCode = courseCode;
        this.title = title;
        this.capacity = capacity;
    }

    // Core course properties accessors
    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public int getCapacity() { return capacity; }

    // Business logic methods
    public boolean hasAvailableSlots() {
        return registeredStudents.size() < capacity;
    }

    public boolean addStudent(Student student) {
        if (hasAvailableSlots()) {
            registeredStudents.put(student, System.currentTimeMillis());
            return true;
        }
        return false;
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

    // Modification methods
    public boolean updateTitle(String newTitle) {
        if (newTitle != null && !newTitle.trim().isEmpty()) {
            this.title = newTitle.trim();
            return true;
        }
        return false;
    }

    public boolean updateCapacity(int newCapacity) {
        if (newCapacity > 0 && newCapacity >= this.registeredStudents.size()) {
            this.capacity = newCapacity;
            return true;
        }
        return false;
    }


    public boolean isRegistered(Student student) {
        return registeredStudents.containsKey(student);
    }

    @Override
    public String toString() {
        return courseCode + "," + title + "," + capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseCode.equals(course.courseCode);
    }

    @Override
    public int hashCode() {
        return courseCode.hashCode();
    }
}