package model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import service.RegistrationService; // Import RegistrationService

public class Admin {
    private final String username = "admin"; // Made final
    private final String password = "admin123"; // Made final
    private Queue<Map.Entry<Student, String>> registrationQueue = new LinkedList<>(); // Queue for registration requests

    public boolean validateLogin(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void addNewStudent(ArrayList<Student> students, String studentId, String name, String phoneNumber, String email, String gender, String district) {
        students.add(new Student(studentId, name, phoneNumber, email, gender, district));
    }

    public void addNewCourse(ArrayList<Course> courses, String courseCode, String title, int capacity) {
        courses.add(new Course(courseCode, title, capacity));
    }

    public boolean removeStudent(ArrayList<Student> students, String studentId, ArrayList<Course> allCourses) {
        Student studentToRemove = null;
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                studentToRemove = student;
                break;
            }
        }
        if (studentToRemove != null) {
            final Student finalStudentToRemove = studentToRemove; // Make effectively final for lambda
            for (Course course : allCourses) {
                course.removeStudent(finalStudentToRemove);
            }
            registrationQueue.removeIf(entry -> entry.getKey().equals(finalStudentToRemove));
            students.remove(finalStudentToRemove);
            return true;
        }
        return false;
    }

    public void viewRegisteredStudentDetails(ArrayList<Student> students) {
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public List<Map.Entry<Student, Long>> viewRegisteredCourseStudents(ArrayList<Course> courses, String courseCode) {
        Course course = findCourseByCode(courses, courseCode);
        if (course != null) {
            LinkedHashMap<Student, Long> studentsWithTime = course.getRegisteredStudentsWithTime();
            return insertionSortByRegistrationTime(studentsWithTime);
        }
        return new ArrayList<>();
    }

    public void processRegistrationRequest(String studentId, String courseCode, boolean approve, RegistrationService service) {
        Iterator<Map.Entry<Student, String>> iterator = registrationQueue.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Student, String> entry = iterator.next();
            Student student = entry.getKey();
            String queuedCourseCode = entry.getValue();
            if (student.getStudentId().equals(studentId) && queuedCourseCode.equals(courseCode)) {
                iterator.remove();
                if (approve) {
                    Course course = service.findCourseById(courseCode);
                    if (course != null && course.hasAvailableSlots()) {
                        service.registerCourse(student, course);
                    }
                } else {
                    Course course = service.findCourseById(courseCode);
                    if (course != null && student.getRegisteredCourses().contains(course)) {
                        service.dropCourse(student, course);
                    }
                }
                break;
            }
        }
    }

    public void processRegistrationRequests(RegistrationService service) {
        while (!registrationQueue.isEmpty()) {
            Map.Entry<Student, String> entry = registrationQueue.poll();
            if (entry != null) {
                Student student = entry.getKey();
                String courseCode = entry.getValue();
                Course course = service.findCourseById(courseCode);
                if (course != null && course.hasAvailableSlots()) {
                    service.registerCourse(student, course);
                }
            }
        }
    }

    public Queue<Map.Entry<Student, String>> getRegistrationQueue() {
        return registrationQueue;
    }

    public void addToRegistrationQueue(Student student, String courseCode) {
        registrationQueue.add(new AbstractMap.SimpleEntry<>(student, courseCode));
    }

    private List<Map.Entry<Student, Long>> insertionSortByRegistrationTime(LinkedHashMap<Student, Long> studentsWithTime) {
        List<Map.Entry<Student, Long>> list = new ArrayList<>(studentsWithTime.entrySet());
        for (int i = 1; i < list.size(); i++) {
            Map.Entry<Student, Long> current = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j).getValue() > current.getValue()) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, current);
        }
        return list;
    }

    private Course findCourseByCode(ArrayList<Course> courses, String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public String formatRegistrationTime(long timestamp) {
        LocalDateTime registrationTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.of("Asia/Colombo"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return registrationTime.format(formatter);
    }
}