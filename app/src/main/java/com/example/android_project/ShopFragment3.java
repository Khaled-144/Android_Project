package com.example.android_project;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ShopFragment3 extends Fragment {

    Connection conn;
    Statement stat;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop3, container, false);

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://SQL6031.site4now.net/db_aa717c_android", "db_aa717c_android_admin", "W3P@g8pWivuWW2");
            stat = conn.createStatement();

            String nn = "android"; // Assuming 'android' is the customer name

            // Insert into itemorder table
            String query = "INSERT INTO itemorder (custname, total) VALUES ('" + nn + "', " + shopActivity1.tot + ")";
            int rs = stat.executeUpdate(query);

            // Retrieve the order ID of the latest entry
            query = "SELECT * FROM itemorder WHERE custname = '" + nn + "' ORDER BY id DESC";
            ResultSet re = stat.executeQuery(query);
            int idd = 0;
            if (re.next()) {
                idd = re.getInt("id");
            }

            // Insert into orderline table for each item in the purchase
            for (shopActivity1.Buy item : shopActivity1.buys) {
                query = "INSERT INTO orderline (orderid, itemname, itemquantt, itemprice) VALUES (" + idd + ", '" + item.itemsname + "', " + item.itemquant + ", " + item.itemprice + ")";
                rs = stat.executeUpdate(query);

                // Update item quantity in the item table
                query = "UPDATE item SET itemquantity = itemquantity - " + item.itemquant + " WHERE title = '" + item.itemsname + "'";
                rs = stat.executeUpdate(query);
            }

            conn.close();

            // Update the TextView in the fragment
            TextView tv = rootView.findViewById(R.id.txtfinal);
            tv.setText("Thank You For Your Purchase");
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return rootView;
    }
}
