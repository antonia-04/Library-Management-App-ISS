package org.isslibrary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.isslibrary.controller.MainController;
import org.isslibrary.repository.BookRepository;
import org.isslibrary.repository.LibrarianRepository;
import org.isslibrary.repository.LoanRepository;
import org.isslibrary.repository.ReaderRepository;
import org.isslibrary.service.Service;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    private static Service s;

    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
            System.out.println("Database configuration loaded!");

            // Initialize repositories
            BookRepository bR = new BookRepository(props);
            LibrarianRepository lR = new LibrarianRepository(props);
            ReaderRepository rR = new ReaderRepository(props);
            LoanRepository loR = new LoanRepository(props, rR, bR);

            // Initialize service
            s = new Service(bR, lR, rR, loR);

            // Start JavaFX application
            startJavaFXApplication();

        } catch (IOException e) {
            System.out.println("Cannot find bd.config: " + e);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error initializing application: " + e);
            e.printStackTrace();
        }
    }

    private static void startJavaFXApplication() {
        Application.launch(JavaFXApp.class);
    }

    public static class JavaFXApp extends Application {
        @Override
        public void start(Stage primaryStage) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/isslibrary/fxml/hello-view.fxml"));
                Parent root = loader.load();
                MainController controller = loader.getController();
                controller.setService(s);

                Scene scene = new Scene(root, 470, 394);
                primaryStage.setTitle("Library Management System");
                primaryStage.setScene(scene);
                primaryStage.show();

            } catch (Exception e) {
                System.out.println("Error loading FXML: " + e);
                e.printStackTrace();
            }
        }
    }
}