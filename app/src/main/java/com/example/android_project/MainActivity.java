package com.example.android_project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    ArrayList<Books> books = new ArrayList<>();
    GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // bottom nav bar
        bottomNavigation = findViewById(R.id.bottom_navigation);
        BadgeDrawable badge = bottomNavigation.getOrCreateBadge(R.id.bottom_navigation);
        badge.setVisible(true);
        badge.setNumber(3);

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    Toast.makeText(MainActivity.this, "new game ", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.phone) {
                    Toast.makeText(MainActivity.this, "help ", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return true; // return true;
            }
        });


        // Items
        books.add(new Books("1", "Apple iPhone 15 Pro Max", "book1 is ......", "https://www.jarir.com/cdn-cgi/image/fit=contain,width=auto,height=auto,quality=85,metadata=none/https://ak-asset.jarir.com/akeneo-prod/asset/9/1/f/b/91fb59d28a2cc685d305ebb985f76954c86cabd5_623632.jpg", 6699));
        books.add(new Books("2", "Book2", "book2 is ......", "https://imgtr.ee/images/2024/03/15/ad0e6491a3a2a692ecae2305ef3d73bc.jpeg", 50));
        books.add(new Books("3", "Book3", "book3 is ......", "https://imgtr.ee/images/2024/03/15/ffb828f40013216c00f21f67ac0452ad.jpeg", 60));
        books.add(new Books("4", "Book4", "book4 is ......", "https://imgtr.ee/images/2024/03/15/839b16632d36edb2b5e6420a0cd7c242.jpeg", 40));
        books.add(new Books("5", "Book5", "book5 is ......", "https://imgtr.ee/images/2024/03/15/5ac1302f7cc3068a302c94be65175bf1.jpeg", 30));
        books.add(new Books("6", "Book6", "book6 is ......", "https://imgtr.ee/images/2024/03/15/928dbdf8adb10ec5ed63d0bb69b0ac4d.jpeg", 40));
        books.add(new Books("7", "Book7", "book7 is ......", "https://imgtr.ee/images/2024/03/15/e23f5d0074a890e0e7f1c4092aafcef6.jpeg", 50));
        books.add(new Books("8", "Book8", "book8 is ......", "https://imgtr.ee/images/2024/03/15/646672de3540f9ba3f005d4b6945e31a.jpeg", 60));

        grid = (GridView)findViewById(R.id.grid);
        grid.setAdapter(new ListViewCustomAdapter(this, books));
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                intent.putExtra("itemPosition", position);
                startActivity(intent);

                Books mybook = books.get(position);
                Toast.makeText(MainActivity.this, "Item: " + mybook.booksname, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class Books {
        String bookid;
        String booksname;
        String bookdesc;
        String bookimages;
        Integer bookprice;

        public Books(String id, String name, String desc, String images, Integer price) {
            this.bookid = id;
            this.booksname = name;
            this.bookdesc = desc;
            this.bookimages = images;
            this.bookprice = price;
        }
    }

    public class ListViewCustomAdapter extends ArrayAdapter<Books> {
        private Activity context;
        ViewHolder holder;

        public ListViewCustomAdapter(Activity context, ArrayList<Books> books) {
            super(context, R.layout.listitem_row, books);
            this.context = context;
        }

        public class ViewHolder {
            String imgurl;
            Bitmap bitmap;
            ImageView imageView1;
        }

        public View getView(int position, View view, ViewGroup parent) {
            holder = new ViewHolder();
            Books book = books.get(position);
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.listitem_row, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
            TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
            TextView pr = (TextView) rowView.findViewById(R.id.textView1);
            txtTitle.setText(book.booksname);
            pr.setText(book.bookprice + "SR");
            extratxt.setText("Description " + book.bookdesc);

            holder.imageView1 = (ImageView) rowView.findViewById(R.id.icon);
            holder.imgurl = book.bookimages;
            new DownloadImage().execute(holder);
            return rowView;
        }

        ;
    }

    private class DownloadImage extends AsyncTask<ListViewCustomAdapter.ViewHolder, Void, ListViewCustomAdapter.ViewHolder> {
        @Override
        protected ListViewCustomAdapter.ViewHolder doInBackground(ListViewCustomAdapter.ViewHolder... VH) {
            ListViewCustomAdapter.ViewHolder viewHolder = VH[0];
            String imageURL = viewHolder.imgurl;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                viewHolder.bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return viewHolder;
        }

        @Override
        protected void onPostExecute(ListViewCustomAdapter.ViewHolder vh) {
            vh.imageView1.setImageBitmap(vh.bitmap);
        }
    }

    public void goToLoginActivity(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        // Go back to main activity
        startActivity(intent);
    }

    public void goToRegisterActivity(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        // Go back to main activity
        startActivity(intent);
    }

    public void goToCatalogueActivity(View view){
        Intent intent = new Intent(this, CatalogueActivity.class);
        // Go back to main activity
        startActivity(intent);
    }
    public void goToshop1Activity(View view){
        Intent intent = new Intent(this, shopActivity1.class);
        // Go back to main activity
        startActivity(intent);
    }
    public void goToshop2Activity(View view){
        Intent intent = new Intent(this, shopActivity2.class);
        // Go back to main activity
        startActivity(intent);
    }
}