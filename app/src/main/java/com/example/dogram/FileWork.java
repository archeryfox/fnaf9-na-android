package com.example.dogram;

import android.icu.lang.UScript;

import java.io.*;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileWork<T> {
    private String Path = System.getProperty("user.home") + "\\OneDrive\\Рабочий стол";

    /**
     * @param path Путь до файла
     */
    public FileWork(String path) {
        Path = path;
    }

    public String getPath() {
        return Path;
    }

    public String JsonWriter(ArrayList<T> LIST) throws IOException {
        GsonBuilder gbdr = new GsonBuilder();
        Gson gson = gbdr.create();
        String sjon = gson.toJson(LIST);
//        System.out.println(sjon);
        String userDesktopFolder = System.getProperty("user.home") + "\\OneDrive\\Рабочий стол";
        String file = "MyJavaJson.json";
        File textFile = new File(userDesktopFolder, file);
        FileWriter writer = new FileWriter(textFile);
        writer.write(sjon);
        writer.close();
        return sjon;
    }

    public User JsonReader(String file) throws IOException {
        Path path = Paths.get(file + "/" + "User.json");
        Scanner scanner = new Scanner(path);
        System.err.println("Читаю текст из файла...");
        String maText = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            maText += line;
        }
        System.out.println("Строчка: " + maText);
        scanner.close();
        return new Gson().fromJson(maText, User.class);
}

    public String FileWrite(String text) throws IOException {
        String userDesktopFolder = System.getProperty("user.home") + "\\OneDrive\\Рабочий стол";
        String file = "MyText.txt";
        File textFile = new File(userDesktopFolder, file);
        FileWriter writer = new FileWriter(textFile);
        writer.write(text);
        writer.close();
        return text;
    }

    public String FileReader(String file) throws IOException {
        if (file == null) {
            file = "MyText.txt";
        }
        String userDesktopFolder = System.getProperty("user.home") + "\\OneDrive\\Рабочий стол";
        Path path = Paths.get(userDesktopFolder + "\\" + file);
        Scanner scanner = new Scanner(path);
        System.err.println("Читаю текст из файла...");
        String maText = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            maText += line;
        }
//         System.out.println("Строчка: " + maText);
        scanner.close();
        return maText;
    }
}
