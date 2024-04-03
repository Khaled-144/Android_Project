package com.example.android_project;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CatalogueActivity extends AppCompatActivity {

    ListView list;
    Connection conn;
    Statement stat;
    ArrayList<Items> Items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_catalogue);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://SQL6031.site4now.net/db_aa717c_android", "db_aa717c_android_admin", "W3P@g8pWivuWW2");
            stat = conn.createStatement();
            String query = "select * from item";
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                Items.add(new Items(rs.getString("Id"), rs.getString("title"), rs.getString("description"), rs.getString("image")));
            }
            conn.close();
        } catch (Exception e) {
        }

        list = (ListView) findViewById(R.id.listView1);
        list.setAdapter(new ListViewCustomAdapter(this, Items));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Items myitem = Items.get(position);
                Toast.makeText(CatalogueActivity.this, "Item: " + myitem.itemname, Toast.LENGTH_LONG).show();

            }
        });

    }

    public class Items {
        String itemid;
        String itemname;
        String itemdesc;
        String itemimages;

        public Items(String id, String name, String desc, String images) {
            this.itemid = id;
            this.itemname = name;
            this.itemdesc = desc;
            this.itemimages = images;
        }
    }

    public class ViewHolder {
        ImageView imageView1;
        Bitmap bitmap;
        String imgurl;
    }

    public class ListViewCustomAdapter extends ArrayAdapter<Items> {
        ViewHolder holder;
        private Activity context;

        public ListViewCustomAdapter(Activity context, ArrayList<Items> items) {
            super(context, R.layout.listitem_row, items);
            this.context = context;

        }

        public View getView(int position, View view, ViewGroup parent) {
            holder = new ViewHolder();

            Items item = Items.get(position);
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.listitem_row, null, true);

            TextView txtTitle1 = (TextView) rowView.findViewById(R.id.item);
            TextView extratxt1 = (TextView) rowView.findViewById(R.id.textView1);
            txtTitle1.setText(item.itemname);
            extratxt1.setText("Description: " + item.itemdesc);
            holder.imageView1 = (ImageView) rowView.findViewById(R.id.icon);
            holder.imgurl = item.itemimages;
            new DownloadImage().execute(holder);
            return rowView;
        };

        private class DownloadImage extends AsyncTask<ViewHolder, Void, ViewHolder> {
            @Override
            protected ViewHolder doInBackground(ViewHolder... VH) {
                ViewHolder viewHolder = VH[0];
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
            protected void onPostExecute(ViewHolder vh) {
                vh.imageView1.setImageBitmap(vh.bitmap);
            }
        }
    }




}