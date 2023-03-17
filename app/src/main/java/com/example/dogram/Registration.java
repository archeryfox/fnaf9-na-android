package com.example.dogram;

import static com.example.dogram.R.id.RegistrationName;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void SelectImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    Drawable d = null;
    Bitmap selectedImage = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        selectedImage = BitmapFactory.decodeStream(imageStream);
                        d = new BitmapDrawable(getResources(), selectedImage);
                        ((CardView) findViewById(R.id.ProfileRegPic)).setForeground(d);
                        saveBitmapToFile(selectedImage, Bitmap.CompressFormat.JPEG, 100);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                }
        }
    }


    public Bitmap drawableToBitmap(PictureDrawable pd) {
        Bitmap bm = Bitmap.createBitmap(pd.getIntrinsicWidth(), pd.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        canvas.drawPicture(pd.getPicture());
        return bm;
    }

    boolean saveBitmapToFile(Bitmap bm, Bitmap.CompressFormat format, int quality) throws FileNotFoundException {
        File imageFile = new File("");
        users = new Gson().fromJson(new FileReader(this.getFilesDir() + "/Users.json"), new ArrayList<User>().getClass());
        imageFile = new File("/data/user/0/com.example.dogram/files", "profile_pic_ID" + (users.size()) + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);
            bm.compress(format, quality, fos);
            fos.close();
            return true;
        } catch (IOException e) {
            Log.e("app", e.getMessage());
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return false;
    }

    List<User> users = new ArrayList<User>();

    public void ToProfile(View view) throws IOException {
        Gson gson = new Gson();
        String Name = "";
        String Password = "";
        if (!((EditText) findViewById(R.id.RegistrationName)).getText().equals("")) {
            Name = ((EditText) findViewById(RegistrationName)).getText().toString();
        }
        if (!((EditText) findViewById(R.id.PassReg)).getText().equals("")) {
            Password = ((EditText) findViewById(R.id.PassReg)).getText().toString();
        }
        if (!Password.equals("") && !Name.equals("")) {
            Toast.makeText(this, Name + " " + Password + "\n" + this.getFilesDir().toString(), Toast.LENGTH_SHORT).show();

            User newUser = new User();
            if (!(new File("/data/user/0/com.example.dogram/files/" + "profile_pic_ID0.jpg").exists())) {
                newUser = new User(Name, Password, "/data/user/0/com.example.dogram/files" + "/profile_pic_ID" + 0 + ".jpg", true);
            } else {
                newUser = new User(Name, Password, "/data/user/0/com.example.dogram/files" + "/profile_pic_ID" + (users.size()) + ".jpg", true);
            }
            String file = "Users.json";
            File textFile = new File("/data/user/0/com.example.dogram/files", file);
            FileWriter writer = new FileWriter(textFile);
            if (new File("/data/user/0/com.example.dogram/files/" + file).exists()) {
                BufferedReader reader = new BufferedReader(new FileReader("/data/user/0/com.example.dogram/files/Users.json"));
                String line2 = "";
                Path path = Paths.get(textFile + "");
                Scanner scanner = new Scanner(path);
                System.err.println("Читаю текст из файла...");
                String maText = "";
                String l = "";
                /*while ((reader.readLine() != null)) {
                    line2 = reader.readLine();
                    maText += line2;
                }*/
                System.out.println("Строчка!1: " + maText);
                scanner.close();
                if (line2.equals("")) {
                    BufferedWriter br = new BufferedWriter(new FileWriter("/data/user/0/com.example.dogram/files/Users.json"));
                    line2 = "[]";
                    br.write(line2 + "");
                }
                List<User> us = null;
                us = (List<User>) (new FileWork<User>().JsonReader("/data/user/0/com.example.dogram/files/Users.json"));
//                us = new Gson().fromJson(line2, new ArrayList<User>().getClass());
                // line2 работает
                // чтение нихера не работает
//                new Gson().fromJson(line2, new ArrayList<User>().getClass());
                if (us != null) {
                    us.add(newUser);
                    String profiles = gson.toJson(us);
                    writer.write(profiles);
                }
            } else {
                users.add(newUser);
                String profiles = gson.toJson(users);
                writer.write(profiles);
            }
            writer.close();
            if (!newUser.getName().equals("") && !newUser.getPassword().equals("")) {
                Intent intent = new Intent(this, Profile.class);
                startActivity(intent);
            }
        }
    }
}