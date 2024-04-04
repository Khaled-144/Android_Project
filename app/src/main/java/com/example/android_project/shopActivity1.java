package com.example.android_project;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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

public class shopActivity1 extends AppCompatActivity {

    ListView list;
    Connection conn;
    Statement stat;
    public static Integer tot;
    ArrayList<Items> items = new ArrayList<>();
    public static ArrayList<Buy> buys = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shop1);
        list = (ListView) findViewById(R.id.listView1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void show(View view) {
        tot = 0;
        String ss = "";
        Integer subtot = 0;
        for (int i = 0; i < buys.size(); i++) {
            Buy item= buys.get(i);
            subtot = item.itemprice* item.itemquant;
            tot += subtot;
            ss += "\n" + item.itemsname + "  " + item.itemquant + " " + subtot + "SR";
        }
        ss += "\n" + "Total: " + tot;
        AlertDialog.Builder builder = new AlertDialog.Builder(shopActivity1.this);
        builder.setTitle("Confirm Purchase");
        builder.setMessage("Purchased Items: " + ss);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent I = new Intent(shopActivity1.this, shopActivity2.class);
                startActivity(I); // go to this activity
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(shopActivity1.this, "you pressed no ",
                        Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ititem(View view) {
        // list.setAdapter(null);
        items.clear();
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://SQL6031.site4now.net/db_aa717c_android", "db_aa717c_android_admin", "W3P@g8pWivuWW2");
            stat = conn.createStatement();
            String query = "select * from item   where category =1 ";
            ResultSet rs1 = stat.executeQuery(query);
            int i = 0;
            while (rs1.next()) {
                items.add(new Items(rs1.getString("id"), rs1.getString("title"),
                rs1.getString("info"), rs1.getString("imgfile"), rs1.getString("price")));

            }

            list.setAdapter(new ListViewCustomAdapter (this, items ));
            conn.close();
        } catch (Exception e) {
            Toast.makeText(shopActivity1.this, "Item: " + "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void citem(View view) {
        try {
            items.clear();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://SQL6031.site4now.net/db_aa717c_android", "db_aa717c_android_admin", "W3P@g8pWivuWW2");
            stat = conn.createStatement();
            String query = "select * from item  where category = 2  ";
            ResultSet rs = stat.executeQuery(query);
            int i = 0;
            while (rs.next()) {
                items.add(new Items(rs.getString("id"), rs.getString("title"), rs.getString("info"), rs.getString("imgfile"), rs.getString("price")));
            }
            list.setAdapter(new shopActivity1.ListViewCustomAdapter(this, items));
            conn.close();
        } catch (Exception e) {
            Toast.makeText(shopActivity1.this, "Item: " + "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public  class Items {
        String itemid;
        String itemsname;
        String itemdesc;
        String  itemimages;
        String  itemprice;
        public Items(String id, String name, String desc, String images, String price) {
            this.itemid=id;
            this.itemsname=name;
            this.itemdesc=desc;
            this.itemimages=images;
            this.itemprice=price;
        }
    }
    public  class Buy {
        String itemsname;
        Integer  itemprice;
        Integer  itemquant;
        public Buy(String name, Integer price, Integer quant ) {
            this.itemsname=name;
            this.itemprice=price;
            this.itemquant=quant;
        }
    }
    public  class ViewHolder {
        ImageView imageView1;
        Bitmap bitmap;
        String  imgurl;
    }
    public class ListViewCustomAdapter extends ArrayAdapter<Items> {
        ViewHolder holder;
        private Activity context;

        public ListViewCustomAdapter(Activity context, ArrayList<Items> items)
        {
            super(context, R.layout.listitem_row, items);
            this.context=context;
        }

        public View getView(int position, View view, ViewGroup parent) {
            holder = new ViewHolder();
            Items item= items.get(position);
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.listitem_row1, null,true);
            TextView itemTitle = (TextView) rowView.findViewById(R.id.item);
            TextView itemdesc = (TextView) rowView.findViewById(R.id.textView1);
            TextView pri = (TextView) rowView.findViewById(R.id.textView2);
            ImageButton abtn = (ImageButton) rowView.findViewById(R.id.addbtn);
            ImageButton rbtn = (ImageButton) rowView.findViewById(R.id.removebtn);
            itemTitle.setText(item.itemsname);
            itemdesc.setText("Description "+item.itemdesc);
            pri.setText(item.itemprice + "SR");
            TextView quant = (TextView) rowView.findViewById(R.id.txt1);
            for (int i = 0; i < buys.size(); i++) {
                Buy selitem= buys.get(i);
                if (selitem.itemsname.equals( itemTitle.getText().toString()))
                {quant.setText(selitem.itemquant.toString());}
            }
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
                    e.printStackTrace();}
                return viewHolder;
            }
            @Override
            protected void onPostExecute(ViewHolder vh) {
                vh.imageView1.setImageBitmap(vh.bitmap);
            }}}}









