package com.example.android_project;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.InputStream;

public class home extends AppCompatActivity {
    ImageView image;
    String[] desc = {"book1 is ...... ", "book2 is ...... ", "book3 is ...... ", "book4 is ...... ","book5 is ...... ", "book6 is ...... ","book7 is ...... ", "book8 is ...... " };
    String[] itemprice ={ "30", "50", "60", "40","30","40", "40", "50", "60" };
    String[] imageURLArray = new String[]{
            "https://picsum.photos/250?image=2",
            "https://picsum.photos/250?image=3",
            "https://picsum.photos/250?image=4",
            "https://picsum.photos/250?image=5",
            "https://picsum.photos/250?image=6",
            "https://picsum.photos/250?image=7",
            "https://picsum.photos/250?image=8",
            "https://picsum.photos/250?image=2"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->{
            Insets systemBars = insets.getInsets(WindowInsetsCompat. Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle appleData = getIntent().getExtras();
        int count = appleData.getInt("itemPosition", -1);
        TextView tvBacon = (TextView) findViewById(R.id.textView);
        tvBacon.setText(desc[count] + " the price = " + itemprice[count]);
        image = (ImageView) findViewById(R.id.image);
        new DownloadImage().execute(imageURLArray[count]);
    }
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();}
            return bitmap;}
        @Override
        protected void onPostExecute(Bitmap result) {
            image.setImageBitmap(result);
        }}
    public void goToActivityOne(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }}


