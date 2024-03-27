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

public class ItemActivity extends AppCompatActivity {

    ImageView image;
    String[] desc = {"book1 is ...... ","book2 is ......", "book3 is ......","book4 is ......", "book5 is ......","book6 is ......", "book7 is ......","book8 is ......"};
    String[] itemprice ={ "30", "50", "60", "40", "30", "40", "50", "60"};
    String[] imageURLArray = new String[]{
            "https://imgtr.ee/images/2024/03/15/234c05fb92f3ba9267a54e74e4485941.jpeg",
            "https://imgtr.ee/images/2024/03/15/ad0e6491a3a2a692ecae2305ef3d73bc.jpeg",
            "https://imgtr.ee/images/2024/03/15/ffb828f40013216c00f21f67ac0452ad.jpeg",
            "https://imgtr.ee/images/2024/03/15/839b16632d36edb2b5e6420a0cd7c242.jpeg",
            "https://imgtr.ee/images/2024/03/15/5ac1302f7cc3068a302c94be65175bf1.jpeg",
            "https://imgtr.ee/images/2024/03/15/928dbdf8adb10ec5ed63d0bb69b0ac4d.jpeg",
            "https://imgtr.ee/images/2024/03/15/e23f5d0074a890e0e7f1c4092aafcef6.jpeg",
            "https://imgtr.ee/images/2024/03/15/646672de3540f9ba3f005d4b6945e31a.jpeg",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
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
        }
    }
    public void goToActivityOne(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}