package com.falcon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Falcon Suppression List System.
 *
 * This Spring Boot application provides:
 * - REST API for managing suppression lists (Part 1)
 * - Ad server integration with real-time suppression checking (Part 2)
 * - Advanced features (Part 3)
 */
@SpringBootApplication
public class SuppressionSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuppressionSystemApplication.class, args);
        System.out.println("\n" +
                "====================================\n" +
                "Falcon Suppression System Started!\n" +
                "====================================\n" +
                "Server running at: http://localhost:8080\n" +
                "\n" +
                "Available endpoints:\n" +
                "- POST   /api/suppression-lists        Create suppression list\n" +
                "- GET    /api/suppression-lists        Get all lists\n" +
                "- GET    /api/suppression-lists/{id}   Get specific list\n" +
                "- PUT    /api/suppression-lists/{id}   Update list\n" +
                "- DELETE /api/suppression-lists/{id}   Delete list\n" +
                "- POST   /api/check-suppression        Check user suppression\n" +
                "- POST   /api/serve-ad                 Serve ad with suppression\n" +
                "\n" +
                "Good luck with the assignment!\n" +
                "====================================\n");
    }
}
