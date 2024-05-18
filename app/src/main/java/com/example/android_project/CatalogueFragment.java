package com.example.android_project;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CatalogueFragment extends Fragment {
    ListView list;
    Connection conn;
    Statement stat;
    ArrayList<Items> Items = new ArrayList<>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public CatalogueFragment() {
        // Required empty public constructor
    }
    public static CatalogueFragment newInstance(String param1, String param2) {
        CatalogueFragment fragment = new CatalogueFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_catalogue, container, false);

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
            e.printStackTrace();
        }
        list = rootView.findViewById(R.id.listView1);
        list.setAdapter(new ListViewCustomAdapter(getActivity(), Items));
        return rootView;
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