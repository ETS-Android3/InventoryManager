package com.snhu.p2_guilherme_pereira;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private Context context;
    private ArrayList itemId, itemLinkedId, itemName, itemAmount;
    private ImageButton IBN_inc, IBN_dec, IBN_clr;

    RecyclerAdapter(Context context,
                    ArrayList itemId,
                    ArrayList itemLinkedId,
                    ArrayList itemName,
                    ArrayList itemAmount){
        this.context= context;
        this.itemId = itemId;
        this.itemLinkedId = itemLinkedId;
        this.itemName = itemName;
        this.itemAmount = itemAmount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txv_itemName.setText(String.valueOf(itemName.get(position)));
        holder.txv_itemAmount.setText(String.valueOf(itemAmount.get(position)));
    }

    @Override
    public int getItemCount() {
        return itemAmount.size(); // Returns how many items there are.
    }

    private Context getApplicationContext() {
        return this.context;
    } // Returns current app context

    public String increase(int position){
        int num = (int)itemAmount.get(position);
        num++;

        itemAmount.set(position, num); // Updates new number but not database
        String amount = String.valueOf(num); // Convert int to String
        return amount;
    }

    public String decrease(int position) {
        int num = (int)itemAmount.get(position);
        if (num > 0) {
            num--;
        } else {
            Toast.makeText(getApplicationContext(), "Decrease denied", Toast.LENGTH_SHORT).show();
        }
        itemAmount.set(position, num);
        String amount = String.valueOf(num);
        System.out.println("New amount: " + amount);

        return amount;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txv_itemName, txv_itemAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txv_itemName = itemView.findViewById(R.id.TXV_itemName);
            txv_itemAmount = itemView.findViewById((R.id.TXV_itemAmount));
            IBN_inc = itemView.findViewById(R.id.IBN_inc);
            IBN_dec = itemView.findViewById(R.id.IBN_dec);
            IBN_clr = itemView.findViewById(R.id.IBN_remove);

            IBN_inc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                    String str_itemName = txv_itemName.getText().toString().trim();
                    String newAmount = increase(getAdapterPosition());

                    txv_itemAmount.setText(newAmount);
                    dbHelper.updateItemAmount(str_itemName, Integer.parseInt((newAmount)));
                }
            });

            IBN_dec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                    String str_itemName = txv_itemName.getText().toString().trim();
                    String newAmount = decrease(getAdapterPosition());

                    txv_itemAmount.setText(newAmount);
                    dbHelper.updateItemAmount(str_itemName, Integer.parseInt((newAmount)));
                }
            });

            IBN_clr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                    String str_itemName = txv_itemName.getText().toString().trim();

                    System.out.println("Deleting item");
                    dbHelper.deleteItem(str_itemName); // Removes from database

                    // Refresh menu
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
