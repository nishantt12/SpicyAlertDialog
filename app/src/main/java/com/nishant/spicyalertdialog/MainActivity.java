package com.nishant.spicyalertdialog;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.nishant.lib.SpicyAlertDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new SpicyAlertDialog(MainActivity.this, 1)
                        .setCustomImage(R.drawable.piggy_graphic)
                        .setTitleText("my title")
                        .setContentText("My content")
                        .setCancelClickListener(new SpicyAlertDialog.OnSpicyClickListener() {
                            @Override
                            public void onClick(SpicyAlertDialog spicyAlertDialog) {


                            }
                        })
                        .setConfirmClickListener(new SpicyAlertDialog.OnSpicyClickListener() {
                            @Override
                            public void onClick(SpicyAlertDialog spicyAlertDialog) {



                            }
                        })
                        .show();

            }
        });


    }
}
