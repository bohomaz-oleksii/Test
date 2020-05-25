package com.example.test;

import java.util.Random;

public class User {


    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String PHOTO = "photo";
    public static final String ID = "id";


    private static String[] names = {"Алексей", "Константин", "Паша", "Иван", "Вова"};
    private static String[] sunames = {"Фомин", "Богомаз", "Павелко", "Іванов", "Вовкодав"};
    private static String[] emails = {"asdf@gmail.com", "sardgf@io.ua", "wer@gmail.com", "sad@gmail.com"};
    private static String[] numbers ={"0992545789", "0505487456", "0731545789"};
    private static int[] photos  = {R.drawable.face_1, R.drawable.face_2, R.drawable.face_3};

    private String first_name, last_name, email, phone_number;
    private int id;
    private int photo_user;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getPhoto_user() {
        return photo_user;
    }

    public void setPhoto_user(int photo_user) {
        this.photo_user = photo_user;
    }

    public static User random(){
        Random random = new Random();
        User user = new User();
        user.setId(-1);
        user.setFirst_name(names[random.nextInt(names.length)]);
        user.setLast_name(sunames[random.nextInt(sunames.length)]);
        user.setEmail(emails[random.nextInt(emails.length)]);
        user.setPhone_number(numbers[random.nextInt(numbers.length)]);
        user.setPhoto_user(photos[random.nextInt(photos.length)]);
        return user;
    }

}
