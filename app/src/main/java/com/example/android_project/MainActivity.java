package com.example.android_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    ArrayList<Books> books = new ArrayList<>();
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        books.add(new Books("1", "Book1", "book1  is ......", "https://picsum.photos/250?image=2", 30));
        books.add(new Books("2", "Book2", "book2  is ......", "https://picsum.photos/250?image=3", 50));
        books.add(new Books("3", "Book3", "book3  is ......", "https://picsum.photos/250?image=4", 60));
        books.add(new Books("4", "Book4", "book4  is ......", "https://picsum.photos/250?image=5", 40));
        books.add(new Books("5", "Book5", "book5  is ......", "https://picsum.photos/250?image=6", 30));
        books.add(new Books("6", "Book6", "book6  is ......", "https://picsum.photos/250?image=7", 40));
        books.add(new Books("7", "Book7", "book7  is ......", "https://picsum.photos/250?image=8", 50));
        books.add(new Books("8", "Book8", "book8  is ......", "https://picsum.photos/250?image=2", 60));

        list = (ListView) findViewById(R.id.listView1);
        list.setAdapter(new ListViewCustomAdapter(this, books));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> argo, View arg1, int position, long arg3) {
                Intent intent = new Intent(MainActivity.this, home.class);
                intent.putExtra("itemPosition", position);
                startActivity(intent);

                Books mybook = books.get(position);
                Toast.makeText(MainActivity.this, "Item: " + mybook.booksname, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public  class Books {
        String bookid;
        String booksname;
        String bookdesc;
        String  bookimages;
        Integer  bookprice;
        public Books(String id, String name, String desc, String images,Integer price) {
            this.bookid=id;
            this.booksname=name;
            this.bookdesc=desc;
            this.bookimages=images;
            this.bookprice=price;
        }
    }

    public class ListViewCustomAdapter extends ArrayAdapter<Books> {
        private Activity context;
        ViewHolder holder;
        public ListViewCustomAdapter(Activity context, ArrayList<Books> books ) {
            super(context, R.layout.listitem_row, books);
            this.context=context;
        }
        public  class ViewHolder {
            String imgurl;
            Bitmap bitmap;
            ImageView imageView1;

        }

        public View getView(int position, View view, ViewGroup parent) {
            holder = new ViewHolder();
            Books book= books.get(position);
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.listitem_row, null,true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
            TextView extra= (TextView) rowView.findViewById(R.id.textView1);
            TextView pr = (TextView) rowView.findViewById(R.id.textView2);
            pr.setText(book.bookprice + "SR");

            holder.imageView1 = (ImageView) rowView.findViewById(R.id.icon);
            holder.imgurl = book.bookimages;
            new DownloadImage().execute(holder);

            return rowView;
        };}

    private class DownloadImage extends AsyncTask <ListViewCustomAdapter.ViewHolder, Void, ListViewCustomAdapter.ViewHolder> {
        @Override
        protected ListViewCustomAdapter.ViewHolder doInBackground(ListViewCustomAdapter.ViewHolder... VH) {
            ListViewCustomAdapter.ViewHolder viewHolder = VH[0];
            String imageURL = viewHolder.imgurl;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                viewHolder.bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();}
            return viewHolder;
        }
        @Override
        protected void onPostExecute(ListViewCustomAdapter.ViewHolder vh) {
            vh.imageView1.setImageBitmap(vh.bitmap);
        }}}









