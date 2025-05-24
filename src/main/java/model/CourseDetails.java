//This part is implimented By Rajin(IT24100135)
package model;

import java.util.ArrayList;

public class CourseDetails {
    private final ArrayList<Course> undergraduateITCourses = new ArrayList<>();
    private final ArrayList<Course> undergraduateBusinessCourses = new ArrayList<>();
    private final ArrayList<Course> postgraduateITCourses = new ArrayList<>();
    private final ArrayList<Course> postgraduateBusinessCourses = new ArrayList<>();

    // Initialization
    public void initializeDefaultCourses() {
        if (areAllCourseListsEmpty()) {
            initializeUndergraduateITCourses();
            initializeUndergraduateBusinessCourses();
            initializePostgraduateITCourses();
            initializePostgraduateBusinessCourses();
        }
    }

    private boolean areAllCourseListsEmpty() {
        return undergraduateITCourses.isEmpty() &&
                undergraduateBusinessCourses.isEmpty() &&
                postgraduateITCourses.isEmpty() &&
                postgraduateBusinessCourses.isEmpty();
    }

    // Course management methods
    public boolean addCourse(Course course) {
        if (course == null) return false;

        ArrayList<Course> targetList = determineCourseList(course.getCourseCode());
        if (targetList != null && !targetList.contains(course)) {
            return targetList.add(course);
        }
        return false;
    }

    public boolean removeCourse(String courseCode) {
        if (courseCode == null) return false;

        ArrayList<Course> targetList = determineCourseList(courseCode);
        if (targetList != null) {
            return targetList.removeIf(c -> c.getCourseCode().equals(courseCode));
        }
        return false;
    }

    public boolean updateCourse(String courseCode, String newTitle, int newCapacity) {
        Course course = findCourseByCode(courseCode);
        if (course != null) {
            course.updateTitle(newTitle);
            course.updateCapacity(newCapacity);
            return true;
        }
        return false;
    }

    // Search methods
    public Course findCourseByCode(String courseCode) {
        return getAllCourses().stream()
                .filter(c -> c.getCourseCode().equals(courseCode))
                .findFirst()
                .orElse(null);
    }

    // Getter methods
    public ArrayList<Course> getUndergraduateITCourses() {
        return new ArrayList<>(undergraduateITCourses);
    }

    public ArrayList<Course> getUndergraduateBusinessCourses() {
        return new ArrayList<>(undergraduateBusinessCourses);
    }

    public ArrayList<Course> getPostgraduateITCourses() {
        return new ArrayList<>(postgraduateITCourses);
    }

    public ArrayList<Course> getPostgraduateBusinessCourses() {
        return new ArrayList<>(postgraduateBusinessCourses);
    }

    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> allCourses = new ArrayList<>();
        allCourses.addAll(undergraduateITCourses);
        allCourses.addAll(undergraduateBusinessCourses);
        allCourses.addAll(postgraduateITCourses);
        allCourses.addAll(postgraduateBusinessCourses);
        return allCourses;
    }

    // Helper methods
    private ArrayList<Course> determineCourseList(String courseCode) {
        if (courseCode.startsWith("IT")) return undergraduateITCourses;
        if (courseCode.startsWith("BUS")) return undergraduateBusinessCourses;
        if (courseCode.startsWith("PIT")) return postgraduateITCourses;
        if (courseCode.startsWith("PG")) return postgraduateBusinessCourses;
        return null;
    }

    private void initializeUndergraduateITCourses() {
        undergraduateITCourses.add(new Course("IT101", "BSc(hons) in Data Science", 30));
        undergraduateITCourses.add(new Course("IT102", "BSc(hons) in Software Engineering", 25));
        undergraduateITCourses.add(new Course("IT103", "BSc(hons) in Interactive Media", 20));
        undergraduateITCourses.add(new Course("IT104", "BSc(hons) in Computer Science", 30));
        undergraduateITCourses.add(new Course("IT105", "BSc(hons) in Computer System Engineering", 25));
    }

    private void initializeUndergraduateBusinessCourses() {
        undergraduateBusinessCourses.add(new Course("BUS101", "BBA General", 30));
        undergraduateBusinessCourses.add(new Course("BUS102", "BBA in Marketing", 25));
        undergraduateBusinessCourses.add(new Course("BUS103", "BBA in Accounting", 20));
        undergraduateBusinessCourses.add(new Course("BUS104", "BBA in Business Analytics", 25));
    }

    private void initializePostgraduateITCourses() {
        postgraduateITCourses.add(new Course("PIT101", "MSC in IT", 20));
        postgraduateITCourses.add(new Course("PIT102", "MSC in Software Engineering", 15));
        postgraduateITCourses.add(new Course("PIT103", "MSC in Computer Science", 20));
    }

    private void initializePostgraduateBusinessCourses() {
        postgraduateBusinessCourses.add(new Course("PG101", "MBA General", 30));
        postgraduateBusinessCourses.add(new Course("PG102", "MBA Specializations", 25));
    }
}