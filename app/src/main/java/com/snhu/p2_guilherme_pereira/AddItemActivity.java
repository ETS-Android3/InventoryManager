package com.snhu.p2_guilherme_pereira;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddItemActivity extends AppCompatActivity {
    private Button BTN_confirm, BTN_cancel;
    private ImageButton IBN_settings;
    private EditText EDT_itemName, EDT_itemAmount;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String str_itemName = EDT_itemName.getText().toString().trim();
            String str_itemAmount = EDT_itemAmount.getText().toString().trim();

            BTN_confirm.setEnabled(!str_itemName.isEmpty() && !str_itemAmount.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Do nothing
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        /*** View IDs ***/

        BTN_confirm = findViewById(R.id.BTN_confirm);
        BTN_cancel = findViewById(R.id.BTN_cancel);
        IBN_settings = findViewById(R.id.IBN_settings);
        EDT_itemName = findViewById(R.id.EDT_itemName);
        EDT_itemAmount = findViewById(R.id.EDT_itemAmount);

        /*** Button listeners ***/
        EDT_itemName.addTextChangedListener(textWatcher);
        EDT_itemAmount.addTextChangedListener(textWatcher);

        BTN_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemModel item;

                DatabaseHelper dbHelper = new DatabaseHelper(AddItemActivity.this);

                String str_itemName = EDT_itemName.getText().toString().trim();
                String str_itemAmount = EDT_itemAmount.getText().toString().trim();
                int int_itemAmount = Integer.parseInt(str_itemAmount);

                boolean itemExists = dbHelper.checkItem(str_itemName);

                if(!itemExists){ // Item does not exist, add to the table
                    System.out.println("Registering item");
                    try {
                        item = new ItemModel(-1, str_itemName, int_itemAmount);
                        dbHelper.addItem(item);
                        Toast.makeText(AddItemActivity.this, "Item added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);

                    } catch (Exception e) {
                        Toast.makeText(AddItemActivity.this, "Item could not be added", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddItemActivity.this, "Item already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });

        BTN_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddItemActivity.this, "Add Item Cancelled", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        IBN_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddItemActivity.this, "Not Available Yet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}