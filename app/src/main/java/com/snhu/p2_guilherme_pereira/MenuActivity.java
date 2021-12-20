package com.snhu.p2_guilherme_pereira;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    private int STORAGE_PERMISSION_CODE = 1;

    private Button BTN_add, BTN_event, BTN_update;
    private ImageButton IBN_exit, IBN_notification;
    private RecyclerView RCV_items;
    private TextView TXV_itemAmount;

    DatabaseHelper dbHelper;
    RecyclerAdapter rvAdapter;
    ArrayList<String> itemName;
    ArrayList<Integer> itemId, itemLinkedId, itemAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        /*** View IDs ***/

        BTN_add = findViewById(R.id.BTN_add);
        BTN_event = findViewById(R.id.BTN_event);
        BTN_update = findViewById(R.id.BTN_update);
        IBN_exit = findViewById(R.id.IBN_exit);
        IBN_notification = findViewById(R.id.IBN_notification);
        RCV_items = findViewById(R.id.RCV_items);
        TXV_itemAmount = findViewById(R.id.TXV_itemAmount);

        /** Button listeners **/

        BTN_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivity(intentAdd);
            }
        });

        BTN_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUpdate = new Intent(getApplicationContext(), UpdateItemActivity.class);
                startActivity(intentUpdate);
            }
        });

        IBN_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        IBN_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MenuActivity.this,
                        Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MenuActivity.this, "Permission already granted", Toast.LENGTH_SHORT);
                } else {
                    requestStoragePermission();
                }
            }
        });

        /** Recycler View **/

        dbHelper = new DatabaseHelper(MenuActivity.this);
        itemId = new ArrayList<>();
        itemLinkedId = new ArrayList<>();
        itemName = new ArrayList<>();
        itemAmount = new ArrayList<>();

        storeArrayData();

        rvAdapter = new RecyclerAdapter(MenuActivity.this,
                itemId, itemLinkedId, itemName, itemAmount);
        RCV_items.setAdapter(rvAdapter);
        RCV_items.setLayoutManager(new LinearLayoutManager(MenuActivity.this));

    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("Permission is required to notify you about low inventory")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MenuActivity.this, new String[] {Manifest.permission.RECEIVE_SMS}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();

        } else {
            ActivityCompat.requestPermissions(MenuActivity.this, new String[] {Manifest.permission.RECEIVE_SMS}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MenuActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MenuActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void storeArrayData(){
        Cursor cursor = dbHelper.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(MenuActivity.this, "No data to display", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()){
                //itemId.add(cursor.getInt(0));
                //itemLinkedId.add(cursor.getInt(1));
                itemName.add(cursor.getString(2));
                itemAmount.add(cursor.getInt(3));
            }
        }
    }
}