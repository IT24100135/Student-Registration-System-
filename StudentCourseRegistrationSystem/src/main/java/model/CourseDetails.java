package model;

import java.util.ArrayList;

public class CourseDetails {
    private ArrayList<Course> undergraduateITCourses = new ArrayList<>();
    private ArrayList<Course> undergraduateBusinessCourses = new ArrayList<>();
    private ArrayList<Course> postgraduateITCourses = new ArrayList<>();
    private ArrayList<Course> postgraduateBusinessCourses = new ArrayList<>();

    public CourseDetails() {
        // Do not initialize courses here; let FileService handle it
    }

    public void initializeCoursesIfEmpty() {
        // Only initialize if the lists are empty (i.e., no courses were loaded from file)
        if (undergraduateITCourses.isEmpty() && undergraduateBusinessCourses.isEmpty() &&
                postgraduateITCourses.isEmpty() && postgraduateBusinessCourses.isEmpty()) {
            // Undergraduate IT Courses
            undergraduateITCourses.add(new Course("IT101", "BSc(hons) in Data Science", 30));
            undergraduateITCourses.add(new Course("IT102", "BSc(hons) in Software Engineering", 25));
            undergraduateITCourses.add(new Course("IT103", "BSc(hons) in Interactive Media", 20));
            undergraduateITCourses.add(new Course("IT104", "BSc(hons) in Computer Science", 30));
            undergraduateITCourses.add(new Course("IT105", "BSc(hons) in Computer System Engineering", 25));

            // Undergraduate Business Courses
            undergraduateBusinessCourses.add(new Course("BUS101", "BBA General", 30));
            undergraduateBusinessCourses.add(new Course("BUS102", "BBA in Marketing", 25));
            undergraduateBusinessCourses.add(new Course("BUS103", "BBA in Accounting", 20));
            undergraduateBusinessCourses.add(new Course("BUS104", "BBA in Business Analytics", 25));

            // Postgraduate IT Courses
            postgraduateITCourses.add(new Course("PIT101", "MSC in IT", 20));
            postgraduateITCourses.add(new Course("PIT102", "MSC in Software Engineering", 15));
            postgraduateITCourses.add(new Course("PIT103", "MSC in Computer Science", 20));

            // Postgraduate Business Courses
            postgraduateBusinessCourses.add(new Course("PG101", "MBA General", 30));
            postgraduateBusinessCourses.add(new Course("PG102", "MBA Specializations", 25));
        }
    }

    public ArrayList<Course> getUndergraduateITCourses() {
        return undergraduateITCourses;
    }

    public ArrayList<Course> getUndergraduateBusinessCourses() {
        return undergraduateBusinessCourses;
    }

    public ArrayList<Course> getPostgraduateITCourses() {
        return postgraduateITCourses;
    }

    public ArrayList<Course> getPostgraduateBusinessCourses() {
        return postgraduateBusinessCourses;
    }

    public Course findCourseByCode(String courseCode) {
        for (Course course : undergraduateITCourses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        for (Course course : undergraduateBusinessCourses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        for (Course course : postgraduateITCourses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        for (Course course : postgraduateBusinessCourses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> allCourses = new ArrayList<>();
        allCourses.addAll(undergraduateITCourses);
        allCourses.addAll(undergraduateBusinessCourses);
        allCourses.addAll(postgraduateITCourses);
        allCourses.addAll(postgraduateBusinessCourses);
        return allCourses;
    }


}