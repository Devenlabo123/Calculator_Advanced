package com.example.assignment2;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.content.Intent;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Initialize variables
    private EditText check_amount;
    private EditText num_people;
    private EditText tip_percent;
    private EditText urlText;


    private TextView total_bill;
    private TextView bill_per_person;
    private TextView total_tip;
    private TextView tip_per_person;
    private TextView err;

    private Button tip_button;
    private Button web_button;
    private Button dial_button;
    private Button map_button;


    private static final String tag = "Widgets";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        check_amount = findViewById(R.id.mealprice);
        num_people = findViewById(R.id.numpeopleprice);
        tip_percent = findViewById(R.id.tip);

        total_bill = findViewById(R.id.billanswer);
        bill_per_person = findViewById(R.id.bill_perperson_answer);
        total_tip = findViewById(R.id.tipanswer);
        tip_per_person = findViewById(R.id.tip_perperson_answer);


        tip_button = findViewById(R.id.calculate);
        tip_button.setOnClickListener(this);

        web_button = findViewById(R.id.web);
        web_button.setOnClickListener(this);

        dial_button = findViewById(R.id.dial);
        dial_button.setOnClickListener(this);

        map_button = findViewById(R.id.map);
        map_button.setOnClickListener(this);

        err = findViewById(R.id.errorlogs);
    }

    //On clicks
    public void onClick(View v) {
        Log.i(tag, "onClick invoked.");
        Log.i(tag, "View: " + v.getId() + " Clicked");

        try {

            if(v.getId() == R.id.web){
                Log.i(tag, "Web button clicked");
                Intent i1 = new Intent(this, web.class);
                startActivity(i1);
                return;
            }
            else if(v.getId() == R.id.calculate) {
                Log.i(tag, "Tip button clicked");

                // grab the inputs from the UI
                String mealprice = check_amount.getText().toString();
                String tip_percentage = tip_percent.getText().toString();
                String number_people = num_people.getText().toString();
                String bill_price_answer;

                Log.i(tag, "mealprice is $" + mealprice);


                // check to see if the meal price includes a "$"
                if (mealprice.contains("$"))
                    mealprice = mealprice.substring(1);

                // check to see if the tip percent includes a "%"
                if (tip_percentage.contains("%"))
                    tip_percentage = tip_percentage.substring(0, tip_percentage.length() - 1);
                if (tip_percentage.equals(""))
                    tip_percentage = "15";

                //Turns meal price into float
                float fmp = Float.parseFloat(mealprice);

                //Calculates and formats the meal price to include tip
                bill_price_answer = String.format("$%.2f", (fmp) * (1 + ((Float.parseFloat(tip_percentage)) * .01f)));
                Log.i(tag, "Total Meal price is $" + fmp);

                //Calculates bill per person
                float bpp = Float.parseFloat(bill_price_answer.substring(1)) / Float.parseFloat(number_people);
                Log.i(tag, "Bill per person is $" + bpp);

                //Calculates total tip and tip per person
                String tt = String.format("$%.2f", (fmp) * ((Float.parseFloat(tip_percentage)) * .01f));
                Log.i(tag, "Total Tip is $" + tt);
                String ttpp = String.format("$%.2f", Float.parseFloat(tt.substring(1)) / Float.parseFloat(number_people));
                Log.i(tag, "Total Tip per person is $" + tt);

                // display the answers
                total_bill.setText(bill_price_answer);
                bill_per_person.setText(String.format("$%.2f", bpp));
                total_tip.setText(tt);
                tip_per_person.setText(ttpp);
            }

            //Logs
            Log.i(tag, "onClick complete.");

        } catch (Exception e) {
            err.setText("Error: " + e.getMessage());
            Log.e(tag, "Failed to Calculate Tip:" + e.getMessage());

        }

    }
}