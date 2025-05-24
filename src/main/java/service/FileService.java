package service;

import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.logging.Level;

public class FileService {
    private static final String DATA_DIR = "C:\\Users\\HP\\Desktop\\StudentCourseRegistrationSystem\\src\\main\\resources\\META-INF\\data";
    private static final String STUDENTS_FILE = "students.txt";
    private static final String COURSES_FILE = "courses.txt";
    private static final String REGISTRATIONS_FILE = "registrations.txt";
    private static final String FEEDBACK_FILE = "feedback.txt";
    private static final Logger LOGGER = Logger.getLogger(FileService.class.getName());

    public FileService() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            boolean success = dataDir.mkdir();
            if (!success) {
                LOGGER.log(Level.WARNING, "Failed to create directory: " + DATA_DIR);
            }
        }
    }
    //This part is implimented By nirubana & Hithurshan(IT24100251 & IT24100135)
    public void loadStudents(ArrayList<Student> students) {
        try {
            File file = new File(DATA_DIR + "/" + STUDENTS_FILE);
            if (!file.exists()) {
                LOGGER.log(Level.WARNING, "File not found in DATA_DIR: " + file.getAbsolutePath());
                return;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    students.add(new Student(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]));
                }
            }
            reader.close();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load students from " + STUDENTS_FILE, e);
        }
    }

    //This part is implimented By Rajin(IT24100272)
    public void loadCourses(CourseDetails courseDetails) {
        File file = new File(DATA_DIR + File.separator + COURSES_FILE);
        if (!file.exists()) {
            courseDetails.initializeDefaultCourses();
            saveAllCourses(courseDetails);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String code = parts[0];
                    String title = parts[1];
                    int capacity = Integer.parseInt(parts[2]);
                    courseDetails.addCourse(new Course(code, title, capacity));
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading courses", e);
        }
    }

    //This part is implimented By Rajin(IT24100272)
    public void loadRegistrations(HashMap<String, String> registrations, RegistrationService service) {
        try {
            File file = new File(DATA_DIR + "/" + REGISTRATIONS_FILE);
            if (!file.exists()) return;

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String studentId = parts[0];
                    String courseId = parts[1];
                    String status = parts[2];
                    long timestamp = Long.parseLong(parts[3]);

                    Student student = service.findStudentById(studentId);
                    Course course = service.findCourseById(courseId);

                    if (student != null && course != null && "registered".equals(status)) {
                        // Ensure bidirectional relationship
                        if (!course.getRegisteredStudents().contains(student)) {
                            course.addStudent(student);
                        }
                        if (!student.getRegisteredCourses().contains(course)) {
                            student.registerCourse(course);
                        }
                        registrations.put(studentId + "-" + courseId, status + ":" + timestamp);
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load registrations", e);
        }
    }
    //This part is implimented By Hithurshan(IT24100135)
    public void saveStudent(Student updatedStudent) {
        try {
            File file = new File(DATA_DIR + "/" + STUDENTS_FILE);
            File tempFile = new File(DATA_DIR + "/temp_" + STUDENTS_FILE);
            boolean studentUpdated = false;

            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length > 0 && parts[0].equals(updatedStudent.getStudentId())) {
                        writer.write(updatedStudent.toString());
                        studentUpdated = true;
                    } else {
                        writer.write(line);
                    }
                    writer.newLine();
                }
                reader.close();
                writer.close();
            }

            if (!studentUpdated) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true));
                writer.write(updatedStudent.toString());
                writer.newLine();
                writer.close();
            }

            if (file.exists()) {
                boolean success = file.delete();
                if (!success) {
                    LOGGER.log(Level.WARNING, "Failed to delete file: " + file.getAbsolutePath());
                }
            }
            boolean renameSuccess = tempFile.renameTo(file);
            if (!renameSuccess) {
                LOGGER.log(Level.WARNING, "Failed to rename temp file to: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save student to " + STUDENTS_FILE, e);
        }
    }

    public void saveCourse(Course course) {
        try {
            // First load all courses
            ArrayList<Course> allCourses = new ArrayList<>();
            File file = new File(DATA_DIR + "/" + COURSES_FILE);

            if (file.exists()) {
                // Read existing courses
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3 && !parts[0].equals(course.getCourseCode())) {
                        // Keep all courses except the one being updated
                        allCourses.add(new Course(parts[0], parts[1], Integer.parseInt(parts[2])));
                    }
                }
                reader.close();
            }

            // Add the updated course
            allCourses.add(course);

            // Rewrite the entire file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Course c : allCourses) {
                writer.write(c.getCourseCode() + "," + c.getTitle() + "," + c.getCapacity());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving course", e);
        }
    }

    public void saveAllCourses(CourseDetails courseDetails) {
        File file = new File(DATA_DIR + File.separator + COURSES_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Course course : courseDetails.getAllCourses()) {
                writer.write(course.getCourseCode() + "," + course.getTitle() + "," + course.getCapacity());
                writer.newLine();
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving all courses", e);
        }
    }

    private String determineCategory(String courseCode) {
        if (courseCode.startsWith("IT")) return "Undergraduate IT";
        if (courseCode.startsWith("BUS")) return "Undergraduate Business";
        if (courseCode.startsWith("PIT")) return "Postgraduate IT";
        if (courseCode.startsWith("PG")) return "Postgraduate Business";
        return "Unknown";
    }

    public void saveRegistration(String studentId, String courseId, String status, long timestamp) {
        try {
            File file = new File(DATA_DIR + "/" + REGISTRATIONS_FILE);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(studentId + "," + courseId + "," + status + "," + timestamp);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save registration to " + REGISTRATIONS_FILE, e);
        }
    }

    public void saveRegistration(String studentId, String courseId, String status) {
        saveRegistration(studentId, courseId, status, System.currentTimeMillis());
    }

    public void removeStudentFromFile(String studentId) {
        try {
            File file = new File(DATA_DIR + "/" + STUDENTS_FILE);
            File tempFile = new File(DATA_DIR + "/temp_" + STUDENTS_FILE);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && !parts[0].equals(studentId)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            reader.close();
            writer.close();
            if (file.exists()) {
                boolean success = file.delete();
                if (!success) {
                    LOGGER.log(Level.WARNING, "Failed to delete file: " + file.getAbsolutePath());
                }
            }
            boolean renameSuccess = tempFile.renameTo(file);
            if (!renameSuccess) {
                LOGGER.log(Level.WARNING, "Failed to rename temp file to: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to remove student from " + STUDENTS_FILE, e);
        }
    }

    public void removeRegistrationsForStudent(String studentId) {
        try {
            File file = new File(DATA_DIR + "/" + REGISTRATIONS_FILE);
            File tempFile = new File(DATA_DIR + "/temp_" + REGISTRATIONS_FILE);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && !parts[0].equals(studentId)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            reader.close();
            writer.close();
            if (file.exists()) {
                boolean success = file.delete();
                if (!success) {
                    LOGGER.log(Level.WARNING, "Failed to delete file: " + file.getAbsolutePath());
                }
            }
            boolean renameSuccess = tempFile.renameTo(file);
            if (!renameSuccess) {
                LOGGER.log(Level.WARNING, "Failed to rename temp file to: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to remove registrations for student from " + REGISTRATIONS_FILE, e);
        }
    }

    public void removeCourse(String courseCode) {
        try {
            File file = new File(DATA_DIR + "/" + COURSES_FILE);
            File tempFile = new File(DATA_DIR + "/temp_" + COURSES_FILE);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && !parts[0].equals(courseCode)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            reader.close();
            writer.close();
            if (file.exists()) {
                boolean success = file.delete();
                if (!success) {
                    LOGGER.log(Level.WARNING, "Failed to delete file: " + file.getAbsolutePath());
                }
            }
            boolean renameSuccess = tempFile.renameTo(file);
            if (!renameSuccess) {
                LOGGER.log(Level.WARNING, "Failed to rename temp file to: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to remove course from " + COURSES_FILE, e);
        }
    }

    public void saveFeedback(ArrayList<Feedback> feedbackList) {
        try {
            File file = new File(DATA_DIR + "/" + FEEDBACK_FILE);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Feedback feedback : feedbackList) {
                writer.write(feedback.toString());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save feedback to " + FEEDBACK_FILE, e);
        }
    }

    public void loadFeedback(ArrayList<Feedback> feedbackList) {
        try {
            File file = new File(DATA_DIR + "/" + FEEDBACK_FILE);
            if (!file.exists()) {
                LOGGER.log(Level.WARNING, "File not found in DATA_DIR: " + file.getAbsolutePath());
                return;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    Feedback feedback = new Feedback(parts[0], parts[1], Long.parseLong(parts[2]));
                    feedbackList.add(feedback);
                }
            }
            reader.close();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load feedback from " + FEEDBACK_FILE, e);
        }
    }
}