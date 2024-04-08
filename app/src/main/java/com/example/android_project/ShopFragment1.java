package com.example.android_project;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ShopFragment1 extends Fragment {

    ListView list;
    Connection conn;
    Statement stat;
    public static Integer tot;
    ArrayList<Items> items = new ArrayList<>();
    public static ArrayList<Buy> buys = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop1, container, false);
        list = rootView.findViewById(R.id.listView1);

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://SQL6031.site4now.net/db_aa717c_android", "db_aa717c_android_admin", "W3P@g8pWivuWW2");
            stat = conn.createStatement();
            String query = "SELECT * FROM item WHERE cata = 1";
            ResultSet rs1 = stat.executeQuery(query);
            while (rs1.next()) {
                items.add(new Items(rs1.getString("id"), rs1.getString("title"),
                        rs1.getString("description"), rs1.getString("image"), rs1.getString("price")));
            }
            list.setAdapter(new ListViewCustomAdapter(getActivity(), items));
            conn.close();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("ShopFragment1", "Error retrieving items", e);
        }

        return rootView;
    }

    // Inner classes (Items, Buy, ViewHolder, and ListViewCustomAdapter) should remain the same...
    public void show(View view) {
        int tot = 0;
        String ss = "";
        for (Buy buy : buys) {
            int subtot = buy.itemprice * buy.itemquant;
            tot += subtot;
            ss += "\n" + buy.itemsname + "  " + buy.itemquant + " " + subtot + " SR";
        }
        ss += "\n" + "Total: " + tot;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirm Purchase");
        builder.setMessage("Purchased Items: " + ss);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), shopActivity2.class);
                startActivity(intent); // go to this activity
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "You pressed No", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ititem(View view) {
        items.clear();
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://SQL6031.site4now.net/db_aa717c_android", "db_aa717c_android_admin", "W3P@g8pWivuWW2");
            stat = conn.createStatement();
            String query = "SELECT * FROM item WHERE cata = 1";
            ResultSet rs1 = stat.executeQuery(query);
            while (rs1.next()) {
                items.add(new Items(rs1.getString("id"), rs1.getString("title"),
                        rs1.getString("description"), rs1.getString("image"), rs1.getString("price")));
            }
            list.setAdapter(new ListViewCustomAdapter(getActivity(), items));
            conn.close();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Item: " + "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("ShopActivity1", "Error retrieving it items", e);
        }
    }

    public void citem(View view) {
        items.clear();
        try {
            // Establish database connection and execute query for cata = 2
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://SQL6031.site4now.net/db_aa717c_android", "db_aa717c_android_admin", "W3P@g8pWivuWW2");
            stat = conn.createStatement();
            String query = "SELECT * FROM item WHERE cata = 2";
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                items.add(new Items(rs.getString("id"), rs.getString("title"), rs.getString("description"), rs.getString("image"), rs.getString("price")));
            }
            list.setAdapter(new ListViewCustomAdapter(getActivity(), items));
            conn.close();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Item: " + "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("ShopActivity1", "Error retrieving C items", e);
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
            itemdesc.setText("Description: "+item.itemdesc);
            pri.setText(item.itemprice + "SR");
            TextView quant = (TextView) rowView.findViewById(R.id.txt1);
            for (int i = 0; i < buys.size(); i++) {
                Buy selitem= buys.get(i);
                if (selitem.itemsname.equals( itemTitle.getText().toString()))
                {quant.setText(selitem.itemquant.toString());}
            }
            abtn.setOnClickListener(
                    new ImageButton.OnClickListener(){
                        public void onClick(View v){
                            String qu = quant.getText().toString();
                            int qut = Integer.parseInt(qu) + 1;
                            quant.setText(Integer.toString(qut));
                            String itemsname= itemTitle.getText().toString();
                            int flage = 0;
                            for (int i = 0; i < buys.size(); i++) {
                                Buy remitem = buys.get(i);
                                if (remitem.itemsname.equals(itemsname)) {
                                    buys.get(i).itemquant = qut;
                                    flage = 1;
                                    break;}
                            }
                            if ( flage == 0)  buys.add(new Buy(item.itemsname, Integer.parseInt(item.itemprice), qut));
                        }
                    });
            rbtn.setOnClickListener(
                    new ImageButton.OnClickListener(){
                        public void onClick(View v){
                            String qu = quant.getText().toString();
                            int qut = Integer.parseInt(qu) - 1;
                            if (qut >= 0) {
                                quant.setText(Integer.toString(qut));
                                String itemsname = itemTitle.getText().toString();
                                for (int i = 0; i < buys.size(); i++) {
                                    Buy remitem = buys.get(i);
                                    if (remitem.itemsname.equals(itemsname)) {
                                        buys.get(i).itemquant = qut;
                                        break;
                                    }
                                }
                            }
                        }
                    });


            holder.imageView1 = (ImageView) rowView.findViewById(R.id.icon);
            holder.imgurl = item.itemimages;
            new ListViewCustomAdapter.DownloadImage().execute(holder);
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
            }
        }
    }

    public void goToMainActivity(View view) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

}
