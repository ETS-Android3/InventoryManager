package com.snhu.p2_guilherme_pereira;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateItemActivity extends AppCompatActivity {
    private Button BTN_confirm, BTN_cancel;
    private ImageButton IBN_settings;
    private EditText EDT_itemName, EDT_newItemName, EDT_newItemAmount;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String str_itemName = EDT_itemName.getText().toString().trim();
            String str_newItemName = EDT_newItemName.getText().toString().trim();
            String str_itemAmount = EDT_newItemAmount.getText().toString().trim();

            BTN_confirm.setEnabled(!str_itemName.isEmpty() && !str_newItemName.isEmpty() && !str_itemAmount.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Do nothing
        }
    };

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        /*** View IDS ***/

        BTN_confirm = findViewById(R.id.BTN_confirm);
        BTN_cancel = findViewById(R.id.BTN_cancel);
        IBN_settings = findViewById(R.id.IBN_settings);
        EDT_itemName = findViewById(R.id.EDT_itemName);
        EDT_newItemName = findViewById(R.id.EDT_newItemName);
        EDT_newItemAmount = findViewById(R.id.EDT_newItemAmount);

        /*** Button Listeners ***/
        EDT_itemName.addTextChangedListener(textWatcher);
        EDT_newItemName.addTextChangedListener(textWatcher);
        EDT_newItemAmount.addTextChangedListener(textWatcher);

        BTN_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemModel item;
                DatabaseHelper dbHelper = new DatabaseHelper(UpdateItemActivity.this);

                String str_itemName = EDT_itemName.getText().toString().trim();
                String str_newItemName = EDT_newItemName.getText().toString().trim();
                String str_newItemAmount = EDT_newItemAmount.getText().toString().trim();
                //int int_newItemAmount = Integer.parseInt(str_newItemAmount);

                boolean itemExists = dbHelper.checkItem(str_itemName);

                if (itemExists) { // Item exists, so we can update it appropriately.
                    System.out.println("Updating Existing Item");
                    try {
                        dbHelper.updateItem(str_itemName, str_newItemName, str_newItemAmount);
                        Toast.makeText(UpdateItemActivity.this, "Item Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(UpdateItemActivity.this, "Item could not be updated", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UpdateItemActivity.this, "Item does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        BTN_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateItemActivity.this, "Update cancelled", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}




