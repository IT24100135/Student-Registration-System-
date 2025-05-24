package service;

import model.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class RegistrationService {
    private final ArrayList<Student> students;
    private final CourseDetails courseDetails;
    private final HashMap<String, String> registrations;
    private final FileService fileService;
    private final Admin admin;
    private final ArrayList<Feedback> feedbackList;

    private static final DateTimeFormatter TIMESTAMP_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
                    .withZone(ZoneId.of("Asia/Colombo"));

    public RegistrationService(ArrayList<Student> students, CourseDetails courseDetails) {
        this.students = Objects.requireNonNull(students, "Students list cannot be null");
        this.courseDetails = Objects.requireNonNull(courseDetails, "CourseDetails cannot be null");
        this.registrations = new HashMap<>();
        this.fileService = new FileService();
        this.admin = new Admin();
        this.feedbackList = new ArrayList<>();
        loadData();
        courseDetails.initializeDefaultCourses();
    }

    private void loadData() {
        fileService.loadStudents(students);
        fileService.loadCourses(courseDetails);
        fileService.loadRegistrations(registrations, this);
        fileService.loadFeedback(feedbackList);
    }

    //This part is implimented By Nirubana(IT24100251)
    // Student-related operations
    public Student findStudentById(String studentId) {
        return students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);
    }

    public void addStudent(Student student) {
        if (student != null && !students.contains(student)) {
            students.add(student);
            fileService.saveStudent(student);
        }
    }

    public Student validateStudentLogin(String studentId, String password) {
        Student student = findStudentById(studentId);
        return (student != null && student.getPassword().equals(password)) ? student : null;
    }

    public boolean validateAdminLogin(String username, String password) {
        return admin.validateLogin(username, password);
    }

    //


    public void updateStudent(Student updatedStudent) {
        students.replaceAll(s ->
                s.getStudentId().equals(updatedStudent.getStudentId()) ? updatedStudent : s);
        fileService.saveStudent(updatedStudent);
    }

    // Course registration operations
    public void registerCourse(Student student, Course course) {
        if (student != null && course != null) {
            boolean registrationSuccess = course.addStudent(student);
            if (registrationSuccess) {
                student.registerCourse(course);
                long timestamp = System.currentTimeMillis();
                registrations.put(student.getStudentId() + "-" + course.getCourseCode(),
                        "registered:" + timestamp);
                fileService.saveRegistration(student.getStudentId(),
                        course.getCourseCode(),
                        "registered",
                        timestamp);
            }
        }
    }

    public void dropCourse(Student student, Course course) {
        if (student != null && course != null && student.dropCourse(course)) {
            course.removeStudent(student);
            registrations.remove(student.getStudentId() + "-" + course.getCourseCode());
            fileService.saveRegistration(student.getStudentId(),
                    course.getCourseCode(),
                    "dropped",
                    System.currentTimeMillis());
        }
    }

    public void requestCourseRegistration(Student student, Course course) {
        if (student != null && course != null &&
                course.hasAvailableSlots() &&
                !student.getRegisteredCourses().contains(course)) {
            admin.addToRegistrationQueue(student, course.getCourseCode());
        }
    }

    // Course management operations
    public boolean addCourse(Course course) {
        if (courseDetails.addCourse(course)) {
            fileService.saveCourse(course);
            return true;
        }
        return false;
    }

    private ArrayList<Course> determineCourseList(String courseCode) {
        if (courseCode.startsWith("IT")) return courseDetails.getUndergraduateITCourses();
        if (courseCode.startsWith("BUS")) return courseDetails.getUndergraduateBusinessCourses();
        if (courseCode.startsWith("PIT")) return courseDetails.getPostgraduateITCourses();
        if (courseCode.startsWith("PG")) return courseDetails.getPostgraduateBusinessCourses();
        return null;
    }

    public boolean removeCourse(String courseCode) {
        if (courseDetails.removeCourse(courseCode)) {
            registrations.entrySet().removeIf(entry -> entry.getKey().endsWith("-" + courseCode));
            fileService.removeCourse(courseCode);
            return true;
        }
        return false;
    }

    public boolean updateCourse(String courseCode, String newTitle, int newCapacity) {
        boolean updated = courseDetails.updateCourse(courseCode, newTitle, newCapacity);
        if (updated) {
            // Save the updated course to file
            Course updatedCourse = courseDetails.findCourseByCode(courseCode);
            fileService.saveCourse(updatedCourse); //
            return true;
        }
        return false;
    }


    // Student management operations
    public boolean removeStudent(String studentId) {
        Student student = findStudentById(studentId);
        if (student == null) return false;

        List<Course> registeredCourses = new ArrayList<>(student.getRegisteredCourses());
        registeredCourses.forEach(course -> dropCourse(student, course));

        if (students.removeIf(s -> s.getStudentId().equals(studentId))) {
            fileService.removeStudentFromFile(studentId);
            registrations.entrySet().removeIf(entry -> entry.getKey().startsWith(studentId + "-"));
            return true;
        }
        return false;
    }

    // Query methods
    public ArrayList<Course> getCoursesByCategory(String category) {
        if (category == null) return new ArrayList<>();

        return switch (category) {
            case "Undergraduate IT" -> new ArrayList<>(courseDetails.getUndergraduateITCourses());
            case "Undergraduate Business" -> new ArrayList<>(courseDetails.getUndergraduateBusinessCourses());
            case "Postgraduate IT" -> new ArrayList<>(courseDetails.getPostgraduateITCourses());
            case "Postgraduate Business" -> new ArrayList<>(courseDetails.getPostgraduateBusinessCourses());
            default -> new ArrayList<>();
        };
    }

    public Course findCourseById(String courseId) {
        return courseDetails.findCourseByCode(courseId);
    }

    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public ArrayList<Course> getAllCourses() {
        return new ArrayList<>(courseDetails.getAllCourses());
    }

    public Admin getAdmin() {
        return admin;
    }

    // Feedback operations
    public void addFeedback(String studentId, String feedbackText) {
        if (studentId != null && feedbackText != null && !feedbackText.trim().isEmpty()) {
            Feedback feedback = new Feedback(studentId, feedbackText, System.currentTimeMillis());
            feedbackList.add(feedback);
            fileService.saveFeedback(feedbackList);
        }
    }

    public ArrayList<Feedback> getAllFeedback() {
        return new ArrayList<>(feedbackList);
    }

    // Utility method (kept as it might be used by other components)
    public String formatTimestamp(Long timestamp) {
        return timestamp != null ?
                TIMESTAMP_FORMATTER.format(Instant.ofEpochMilli(timestamp)) :
                "Unknown";
    }
}