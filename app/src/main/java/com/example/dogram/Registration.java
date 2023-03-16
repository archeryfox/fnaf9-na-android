package com.example.dogram;

import static com.example.dogram.R.id.LoginName;
import static com.example.dogram.R.id.RegistrationName;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.google.gson.*;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

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

    boolean saveBitmapToFile(Bitmap bm, Bitmap.CompressFormat format, int quality) {
        File imageFile = new File(this.getFilesDir(), "profile_pic.jpg");

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

    public void ToProfile(View view) throws IOException {
        Gson gson = new Gson();
        String Name = "";
        String Password = "";
        if (!((EditText) findViewById(R.id.RegistrationName)).getText().toString().equals("")) {
            Name = ((EditText) findViewById(RegistrationName)).getText().toString();
        }
        if (!((EditText) findViewById(R.id.PassReg)).getText().toString().equals("")) {
            Password = ((EditText) findViewById(R.id.PassReg)).getText().toString();
        }
        Toast.makeText(this, Name + " " + Password + "\n" + this.getFilesDir().toString(), Toast.LENGTH_SHORT).show();
        ;
//        this.getFilesDir();
        String profile = gson.toJson(new User(Name, Password, this.getFilesDir().toString()));
        String file = "User.json";
        File textFile = new File(this.getFilesDir(), file);
        FileWriter writer = new FileWriter(textFile);
        writer.write(profile);
        writer.close();
        Toast.makeText(this, profile, Toast.LENGTH_LONG).show();
        ;
        if (!new User().equals(null)) {
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
        }
    }
}