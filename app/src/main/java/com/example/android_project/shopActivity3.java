package com.example.android_project;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class shopActivity3 extends AppCompatActivity {
    Connection conn;
    Statement stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shop3);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        } );

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:jtds:sqlserver://SQL6031.site4now.net/db_aa717c_android", "db_aa717c_android_admin", "W3P@g8pWivuWW2");
            stat = conn.createStatement();
            String nn = "android";
            String query = "insert into  itemorder (custname,total) values ( '" + nn + "', '" + shopActivity1.tot + "' ) ";
            int rs = stat.executeUpdate(query);
            query = "select * from itemorder  where custname= '" + nn + "'  order by id desc ";
            ResultSet re = stat.executeQuery(query);
            Integer idd = 0;
            if (re.next()) {
                idd = re.getInt("id");
            }
            for (int i = 0; i < shopActivity1.buys.size(); i++) {
                shopActivity1.Buy item = shopActivity1.buys.get(i);
                query = "insert into  orderline (orderid,itemname,itemquant,itemprice) values ( '" + idd + "'  ,'" + item.itemsname + "' ,'" + item.itemquant + "','" + item.itemprice + "') ";
                rs = stat.executeUpdate(query);
                query = "update item set itemquantity = itemquantity - '" + item.itemquant + "'  where title = '" + item.itemsname + "'";
                rs = stat.executeUpdate(query);
            }
            conn.close();
            TextView tv = (TextView) findViewById(R.id.txtfinal);
            tv.setText("Thank You For your Purchase");
        } catch (Exception e) {
            Toast.makeText(shopActivity3.this, "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }



}