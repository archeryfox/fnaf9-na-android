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
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {
    User currentUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        List<User> AuthorisedUsers = null;
        try {
            AuthorisedUsers = (List<User>) (new FileWork<User>().JsonReader(this.getFilesDir() + "/Users.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < AuthorisedUsers.size(); i++) {
            try {
                if (AuthorisedUsers.get(i).getIsCurrent()) {
                    currentUser = AuthorisedUsers.get(i);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        ((TextView) findViewById(R.id.log)).setText(currentUser.getName());
        ((TextView) findViewById(R.id.pas)).setText(currentUser.getPassword());
        if (new File(currentUser.getAvatar()).exists()) {
            Drawable piic = Drawable.createFromPath(currentUser.getAvatar());
            ((CardView) findViewById(R.id.ProfileRegPic)).setForeground(piic);
        }
    }

    public void ToAuth(View view) {
        final Context _th = this;
        new AlertDialog.Builder(this)
                .setTitle("Выход из аккаунта")
                .setMessage("Вы уверены, что хотите выйти?")
                .setNegativeButton("Остаться", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
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