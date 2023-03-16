package com.example.dogram;

import android.R.drawable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        User user = new User();
        try {
            user = new FileWork<User>("").JsonReader(this.getFilesDir().getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((TextView) findViewById(R.id.log)).setText(user.getName());
        ((TextView) findViewById(R.id.pas)).setText(user.getPassword());
        Drawable piic = Drawable.createFromPath(this.getFilesDir()+"/profile_pic.jpg");
        ((CardView) findViewById(R.id.ProfileRegPic)).setForeground(piic);
    }

    public void ToAuth(View view) {
        final Context _th = this;
        new AlertDialog.Builder(this)
                .setTitle("Выход из аккаунта")
                .setMessage("Вы уверены, что хотите выйти?")
                .setNegativeButton("Остаться", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(_th, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}