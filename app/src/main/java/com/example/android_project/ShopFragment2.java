package com.example.android_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.braintreepayments.cardform.view.CardForm;

public class ShopFragment2 extends Fragment {

    CardForm cardForm;
    Button buy;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop2, container, false);

        cardForm = rootView.findViewById(R.id.card_form);
        buy = rootView.findViewById(R.id.btnBuy);

        // Set up CardForm and other UI elements...
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .setup(getActivity()); // Use getActivity() to get the hosting Activity context

        TextView tx = rootView.findViewById(R.id.textView);
        // Assuming ShopFragment1.tot is a static variable that holds the total
        tx.setText("Total " + ShopFragment1.tot);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), shopActivity3.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
