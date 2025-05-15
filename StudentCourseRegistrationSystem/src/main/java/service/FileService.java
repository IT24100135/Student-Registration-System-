package service;

import model.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.logging.Level;

public class FileService {
    private static final String DATA_DIR = "data";
    private static final String STUDENTS_FILE = "students.txt";
    private static final String COURSES_FILE = "courses.txt";
    private static final String REGISTRATIONS_FILE = "registrations.txt";
    private static final Logger LOGGER = Logger.getLogger(FileService.class.getName());

    public FileService() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
    }

    public void loadStudents(ArrayList<Student> students) {
        try {
            URL resource = getClass().getClassLoader().getResource(STUDENTS_FILE);
            if (resource == null) {
                throw new FileNotFoundException("Resource file not found: " + STUDENTS_FILE);
            }
            File file = new File(resource.toURI());
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

    public void loadCourses(CourseDetails courseDetails) {
        // Clear existing courses to avoid duplicates
        courseDetails.getUndergraduateITCourses().clear();
        courseDetails.getUndergraduateBusinessCourses().clear();
        courseDetails.getPostgraduateITCourses().clear();
        courseDetails.getPostgraduateBusinessCourses().clear();

        try {
            URL resource = getClass().getClassLoader().getResource(COURSES_FILE);
            if (resource != null) {
                File file = new File(resource.toURI());
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        String courseCode = parts[0];
                        String title = parts[1];
                        int capacity = Integer.parseInt(parts[2]);
                        String category = parts[3];
                        Course course = new Course(courseCode, title, capacity);
                        ArrayList<Course> courseList = switch (category) {
                            case "Undergraduate IT" -> courseDetails.getUndergraduateITCourses();
                            case "Undergraduate Business" -> courseDetails.getUndergraduateBusinessCourses();
                            case "Postgraduate IT" -> courseDetails.getPostgraduateITCourses();
                            case "Postgraduate Business" -> courseDetails.getPostgraduateBusinessCourses();
                            default -> throw new IllegalArgumentException("Invalid category: " + category);
                        };
                        courseList.add(course);
                    }
                }
                reader.close();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load courses from " + COURSES_FILE, e);
        }
    }

    public void loadRegistrations(HashMap<String, String> registrations, RegistrationService service) {
        try {
            URL resource = getClass().getClassLoader().getResource(REGISTRATIONS_FILE);
            if (resource == null) {
                throw new FileNotFoundException("Resource file not found: " + REGISTRATIONS_FILE);
            }
            File file = new File(resource.toURI());
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String studentId = parts[0];
                    String courseId = parts[1];
                    String status = parts[2];
                    String timestamp = parts[3];
                    if (status.equals("registered")) {
                        Student student = service.findStudentById(studentId);
                        Course course = service.findCourseById(courseId);
                        if (student != null && course != null) {
                            student.registerCourse(course);
                            registrations.put(studentId + "-" + courseId, status + ":" + timestamp);
                        }
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to load registrations from " + REGISTRATIONS_FILE, e);
        }
    }

    public void saveStudent(Student updatedStudent) {
        try {
            File file = new File(DATA_DIR + "/" + STUDENTS_FILE);
            File tempFile = new File(DATA_DIR + "/temp_" + STUDENTS_FILE);
            boolean studentUpdated = false;

            // Read existing students and update the matching one
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

            // If the student wasn't found, append it (new student case)
            if (!studentUpdated) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true));
                writer.write(updatedStudent.toString());
                writer.newLine();
                writer.close();
            }

            // Replace the old file with the updated one
            if (file.exists()) {
                file.delete();
            }
            tempFile.renameTo(file);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save student to " + STUDENTS_FILE, e);
        }
    }

    public void saveCourse(Course course) {
        try {
            File file = new File(DATA_DIR + "/" + COURSES_FILE);
            File tempFile = new File(DATA_DIR + "/temp_" + COURSES_FILE);
            boolean courseUpdated = false;

            // Read existing courses and update the matching one
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length > 0 && parts[0].equals(course.getCourseCode())) {
                        writer.write(course.toString() + "," + determineCategory(course.getCourseCode()));
                        courseUpdated = true;
                    } else {
                        writer.write(line);
                    }
                    writer.newLine();
                }
                reader.close();
                writer.close();
            }

            // If the course wasn't found, append it (new course case)
            if (!courseUpdated) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, true));
                writer.write(course.toString() + "," + determineCategory(course.getCourseCode()));
                writer.newLine();
                writer.close();
            }

            // Replace the old file with the updated one
            if (file.exists()) {
                file.delete();
            }
            tempFile.renameTo(file);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to save course to " + COURSES_FILE, e);
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
            file.delete();
            tempFile.renameTo(file);
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
            file.delete();
            tempFile.renameTo(file);
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
            file.delete();
            tempFile.renameTo(file);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to remove course from " + COURSES_FILE, e);
        }
    }
}