package listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import model.*;
import service.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        ArrayList<Student> students = new ArrayList<>();
        CourseDetails courseDetails = new CourseDetails();
        RegistrationService registrationService = new RegistrationService(students, courseDetails);
        context.setAttribute("registrationService", registrationService);



        System.out.println("AppContextListener: Application initialized with RegistrationService.");
    }

    private void copyResourceToData(String fileName) {
        try {
            URL resource = getClass().getClassLoader().getResource(fileName);
            if (resource == null) {
                System.out.println("AppContextListener: Resource " + fileName + " not found.");
                return;
            }
            File sourceFile = new File(resource.toURI());
            File destFile = new File("C:\\Users\\HP\\Desktop\\StudentCourseRegistrationSystem\\src\\main\\resources\\META-INF\\data" + fileName);
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(destFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            System.out.println("AppContextListener: Copied " + fileName + " to " + destFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("AppContextListener: Error copying " + fileName + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("AppContextListener: Application destroyed.");
    }
}