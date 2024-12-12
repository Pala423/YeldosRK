package com.example.photoauth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    // База данных в памяти для хранения пользователей
    private final Map<String, String> userDatabase = new HashMap<>();

    // Обработка запроса на авторизацию
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        String photoName = request.get("photoName");

        if (photoName == null || photoName.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Photo name is required");
        }

        String username = userDatabase.get(photoName);
        if (username != null) {
            return ResponseEntity.ok("Welcome, " + username + "!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid photo name");
        }
    }

    // Обработка запроса на регистрацию с фото
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam("username") String username,
                                           @RequestParam("file") MultipartFile file) {
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is required");
        }

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is required");
        }

        try {
            // Путь к папке uploads
            Path uploadPath = Paths.get("C:\\Users\\elDOS\\IdeaProjects\\PhotoAuth\\uploads");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath); // Создаем папку, если она не существует
            }

            // Получаем имя файла
            String fileName = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // Сохраняем файл в указанную папку
            file.transferTo(filePath.toFile());

            // Добавляем информацию о пользователе в базу данных
            userDatabase.put(fileName, username);

            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully with photo");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }
}
