package com.example.dogram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void SelectImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        //Тип получаемых объектов - image:
        photoPickerIntent.setType("image/*");
        //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
        startActivityForResult(photoPickerIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        //Получаем URI изображения, преобразуем его в Bitmap
                        //объект и отображаем в элементе ImageView нашего интерфейса:
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        Drawable d = new BitmapDrawable(getResources(), selectedImage);
                        ((CardView) findViewById(R.id.ProfileRegPic)).setForeground(d);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                }
        }
    }


    public void RegHome(View view) {
        Intent iny = new Intent(this, Registration.class);
        startActivity(iny);
    }

    /**
     * Авторизация на главном экране.
     * Проверяет в файлах приложения json
     *
     * @param view
     */
    public void Authorization(View view) throws FileNotFoundException {
        EditText login = findViewById(R.id.LoginName);
        EditText pass = findViewById(R.id.PassAuth);
        String name = login.getText().toString();
        String passw = pass.getText().toString();
        if (true) {
            // Авторизация прошла успешно
            Toast.makeText(this, "Avtorizacia", Toast.LENGTH_SHORT).show();
            UserList users1 = new Gson().fromJson(new FileReader("/data/user/0/com.example.dogram/files/Users.json"), UserList.class);
            for (User user : users1) {
                if (user.getName().equals(name)) {
                    if (users1.get(users1.indexOf(user)).getPassword().equals(passw)) {

                        // Авторизация прошла успешно
                        Toast.makeText(this, "Авторизация прошла успешно", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, Profile.class);
                        startActivity(intent);
                    }
                }
            }
        }
    }
}