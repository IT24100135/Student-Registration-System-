package service;

import model.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class RegistrationService {
    private ArrayList<Student> students;
    private CourseDetails courseDetails;
    private HashMap<String, String> registrations;
    private FileService fileService;
    private Admin admin;
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter
            .ofPattern("dd-MM-yyyy HH:mm:ss")
            .withZone(ZoneId.of("Asia/Colombo"));

    public RegistrationService(ArrayList<Student> students, CourseDetails courseDetails) {
        if (students == null || courseDetails == null) {
            throw new IllegalArgumentException("Students and CourseDetails cannot be null");
        }
        this.students = students;
        this.courseDetails = courseDetails;
        this.registrations = new HashMap<>();
        this.fileService = new FileService();
        this.admin = new Admin();
        loadData();
    }

    private void loadData() {
        fileService.loadStudents(students);
        fileService.loadCourses(courseDetails);
        fileService.loadRegistrations(registrations, this);
    }

    public Student findStudentById(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public Student validateStudentLogin(String studentId, String password) {
        Student student = findStudentById(studentId);
        if (student != null && student.getPassword().equals(password)) {
            return student;
        }
        return null;
    }

    public boolean validateAdminLogin(String username, String password) {
        return admin.validateLogin(username, password);
    }

    public void registerCourse(Student student, Course course) {
        if (student == null || course == null) {
            System.out.println("RegistrationService: Student or course is null, cannot register.");
            return;
        }
        if (student.registerCourse(course) && course.hasAvailableSlots()) {
            course.addStudent(student);
            long timestamp = System.currentTimeMillis();
            String registrationEntry = "registered:" + timestamp;
            registrations.put(student.getStudentId() + "-" + course.getCourseCode(), registrationEntry);
            fileService.saveRegistration(student.getStudentId(), course.getCourseCode(), "registered", timestamp);
            System.out.println("RegistrationService: Registered student " + student.getStudentId() + " for course " + course.getCourseCode() + " at " + timestamp);
        } else {
            System.out.println("RegistrationService: Failed to register student " + student.getStudentId() + " for course " + course.getCourseCode());
        }
    }

    public void requestCourseRegistration(Student student, Course course) {
        if (student != null && course != null && course.hasAvailableSlots() && !student.getRegisteredCourses().contains(course)) {
            admin.addToRegistrationQueue(student, course.getCourseCode());
            System.out.println("RegistrationService: Added registration request for " + student.getStudentId() + " to queue for course " + course.getCourseCode());
        }
    }

    public void dropCourse(Student student, Course course) {
        if (student == null || course == null) {
            System.out.println("RegistrationService: Student or course is null, cannot drop.");
            return;
        }
        if (student.dropCourse(course)) {
            course.removeStudent(student);
            registrations.remove(student.getStudentId() + "-" + course.getCourseCode());
            long timestamp = System.currentTimeMillis();
            fileService.saveRegistration(student.getStudentId(), course.getCourseCode(), "dropped", timestamp);
            System.out.println("RegistrationService: Dropped student " + student.getStudentId() + " from course " + course.getCourseCode() + " at " + timestamp);
        } else {
            System.out.println("RegistrationService: Failed to drop student " + student.getStudentId() + " from course " + course.getCourseCode());
        }
    }

    public boolean removeCourse(String courseCode) {
        Course course = findCourseById(courseCode);
        if (course == null) {
            System.out.println("RegistrationService: Course " + courseCode + " not found.");
            return false;
        }
        for (Student student : students) {
            if (student.getRegisteredCourses().contains(course)) {
                dropCourse(student, course);
            }
        }
        ArrayList<Course> allCourses = courseDetails.getAllCourses();
        boolean removed = allCourses.remove(course);
        if (removed) {
            fileService.removeCourse(courseCode);
            registrations.entrySet().removeIf(entry -> entry.getKey().endsWith("-" + courseCode));
            fileService.loadCourses(courseDetails);
            System.out.println("RegistrationService: Successfully removed course " + courseCode + " from memory and file.");
            return true;
        }
        System.out.println("RegistrationService: Failed to remove course " + courseCode + " from courseDetails.");
        return false;
    }

    public boolean removeStudent(String studentId) {
        System.out.println("RegistrationService: Attempting to remove student with ID = " + studentId);
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("RegistrationService: Student with ID " + studentId + " not found in in-memory data.");
            return false;
        }
        System.out.println("RegistrationService: Removing student " + studentId + " from in-memory data...");
        boolean removedFromMemory = admin.removeStudent(students, studentId, courseDetails.getAllCourses());
        if (!removedFromMemory) {
            System.out.println("RegistrationService: Failed to remove student " + studentId + " from in-memory data.");
            return false;
        }
        System.out.println("RegistrationService: Removing student " + studentId + " from students.txt...");
        fileService.removeStudentFromFile(studentId);
        System.out.println("RegistrationService: Removing registrations for student " + studentId + " from registrations.txt...");
        fileService.removeRegistrationsForStudent(studentId);
        System.out.println("RegistrationService: Updating in-memory registrations for student " + studentId + "...");
        int initialSize = registrations.size();
        Iterator<String> iterator = registrations.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (key.startsWith(studentId + "-")) {
                iterator.remove();
            }
        }
        int removedCount = initialSize - registrations.size();
        System.out.println("RegistrationService: Removed " + removedCount + " registration entries for student " + studentId + ".");
        System.out.println("RegistrationService: Student " + studentId + " removal completed.");
        return true;
    }

    public ArrayList<Course> getCoursesByCategory(String category) {
        return switch (category) {
            case "Undergraduate IT" -> courseDetails.getUndergraduateITCourses();
            case "Undergraduate Business" -> courseDetails.getUndergraduateBusinessCourses();
            case "Postgraduate IT" -> courseDetails.getPostgraduateITCourses();
            case "Postgraduate Business" -> courseDetails.getPostgraduateBusinessCourses();
            default -> new ArrayList<>();
        };
    }

    public Course findCourseById(String courseId) {
        return courseDetails.findCourseByCode(courseId);
    }

    public void addStudent(Student student) {
        students.add(student);
        fileService.saveStudent(student);
    }

    public void addCourse(String category, Course course) {
        ArrayList<Course> courseList = switch (category) {
            case "Undergraduate IT" -> courseDetails.getUndergraduateITCourses();
            case "Undergraduate Business" -> courseDetails.getUndergraduateBusinessCourses();
            case "Postgraduate IT" -> courseDetails.getPostgraduateITCourses();
            case "Postgraduate Business" -> courseDetails.getPostgraduateBusinessCourses();
            default -> throw new IllegalArgumentException("Invalid category");
        };
        // Check if the course already exists; update if found, add if new
        boolean updated = false;
        for (int i = 0; i < courseList.size(); i++) {
            if (courseList.get(i).getCourseCode().equals(course.getCourseCode())) {
                courseList.set(i, course);
                updated = true;
                break;
            }
        }
        if (!updated) {
            admin.addNewCourse(courseList, course.getCourseCode(), course.getTitle(), course.getCapacity());
        }
        fileService.saveCourse(course);
    }

    public void updateStudent(Student updatedStudent) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId().equals(updatedStudent.getStudentId())) {
                students.set(i, updatedStudent);
                break;
            }
        }
        fileService.saveStudent(updatedStudent);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Course> getAllCourses() {
        return courseDetails.getAllCourses();
    }

    public Admin getAdmin() {
        return admin;
    }

    public String getRegistrationStatus(String studentId, String courseCode) {
        if (studentId == null || courseCode == null) {
            System.out.println("RegistrationService: Student ID or course code is null, cannot get registration status.");
            return null;
        }
        String entry = registrations.get(studentId + "-" + courseCode);
        if (entry == null) {
            System.out.println("RegistrationService: No registration entry found for " + studentId + "-" + courseCode);
            return null;
        }
        String[] parts = entry.split(":");
        return parts.length > 0 ? parts[0] : null;
    }

    public Long getRegistrationTimestamp(String studentId, String courseCode) {
        if (studentId == null || courseCode == null) {
            System.out.println("RegistrationService: Student ID or course code is null, cannot get registration timestamp.");
            return null;
        }
        String entry = registrations.get(studentId + "-" + courseCode);
        if (entry == null) {
            System.out.println("RegistrationService: No registration entry found for " + studentId + "-" + courseCode);
            return null;
        }
        String[] parts = entry.split(":");
        if (parts.length > 1) {
            try {
                return Long.parseLong(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("RegistrationService: Invalid timestamp format for " + studentId + "-" + courseCode + ": " + parts[1]);
                return null;
            }
        }
        return null;
    }

    public String formatTimestamp(Long timestamp) {
        if (timestamp == null) {
            System.out.println("RegistrationService: Timestamp is null, cannot format.");
            return "Unknown";
        }
        try {
            Instant instant = Instant.ofEpochMilli(timestamp);
            return TIMESTAMP_FORMATTER.format(instant);
        } catch (Exception e) {
            System.out.println("RegistrationService: Error formatting timestamp " + timestamp + ": " + e.getMessage());
            return "Unknown";
        }
    }
}