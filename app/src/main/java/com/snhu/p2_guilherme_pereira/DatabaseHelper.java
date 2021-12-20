package com.snhu.p2_guilherme_pereira;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Fields
    private final Context context;
    private static final String DATABASE_NAME = "Inventory.db";
    private static final int VERSION = 1;

    // SQL commands & references for user table
    private static final String TABLE_USER = "Table_User";
    private static final String COL_ID = "ID";
    private static final String COL_USERNAME = "Username";
    private static final String COL_PASSWORD = "Password";

    // SQL commands & references for item table
    private static final String TABLE_ITEM = "Table_Item";
    private static final String COL_ITEM_ID = "ID";
    private static final String COL_LINKED_ID = "LinkedID";
    private static final String COL_ITEM_NAME = "Name";
    private static final String COL_ITEM_AMOUNT = "Amount";


    /*** Constructors ***/
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    /** Functions - Defaults **/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryUser = "CREATE TABLE " + TABLE_USER
                + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_PASSWORD + " TEXT)";

        String queryItem = "CREATE TABLE " + TABLE_ITEM
                + " (" + COL_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_LINKED_ID + " INTEGER, " +
                COL_ITEM_NAME + " TEXT," +
                COL_ITEM_AMOUNT + " INTEGER)";

        db.execSQL(queryUser); // Passes our query to create an user table for our database.
        db.execSQL(queryItem); // Passes our query to create an item table for our database.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        onCreate(db); // When we upgrade our table, we call onCreate method
    }

    /*** Functions - Modify ***/

    /** Creating **/
    public boolean addUser(UserModel user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues(); // Takes pairs of values

        // Do not place the ID because then it will not auto-increment correctly.
        cv.put(COL_USERNAME, user.getUsername());
        cv.put(COL_PASSWORD, user.getPassword());

        long insert = db.insert(TABLE_USER, null, cv);

        if (insert == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean addItem(ItemModel item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Like before, ITEM_ID is not placed so they can auto increment correctly.
        cv.put(COL_ITEM_NAME, item.getName());
        cv.put(COL_ITEM_AMOUNT, item.getAmount());
        cv.put(COL_LINKED_ID, 1); // TODO: 1 connects to the first ID - try to connect to respected ID

        long insert = db.insert(TABLE_ITEM, null, cv);

        if (insert == -1){
            System.out.println("Failed to add");
            return false;
        } else {
            System.out.println("Successfully added");
            return true;
        }
    }

    /** Reading **/
    public boolean checkItem(String itemName){
        String queryString = "SELECT * FROM " + TABLE_ITEM;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(queryString + " WHERE " + COL_ITEM_NAME + " = ?", new String[] {itemName});
        if(cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUserName(String username){
        String queryString = "SELECT * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(queryString + " WHERE " + COL_USERNAME + " = ?", new String[] {username});
        if(cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    public boolean checkAccount(String username, String password){
        String queryString = "SELECT * FROM " + TABLE_USER + " WHERE " + COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, new String[] {username, password});

        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_ITEM;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    /** Updating **/
    public void updateItem(String originalName, String newName, String newAmount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_ITEM_NAME, newName);
        cv.put(COL_ITEM_AMOUNT, newAmount);
        cv.put(COL_LINKED_ID, 1); // TODO: 1 connects to the first ID - try to connect to respected ID

        db.update(TABLE_ITEM, cv, "name=?", new String[]{originalName});
        db.close();
    }

    public void updateItemAmount(String itemName, int newAmount){
        System.out.println("Updating item amount in database");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_ITEM_NAME, itemName);
        cv.put(COL_ITEM_AMOUNT, newAmount);

        db.update(TABLE_ITEM, cv, "name=?", new String[]{itemName});
        db.close();

    }

    /** Deleting **/
    public void deleteItem(String itemName){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_ITEM, "name=?", new String[]{itemName});
        db.close();
    }
}