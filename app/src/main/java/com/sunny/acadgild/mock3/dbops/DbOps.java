package com.sunny.acadgild.mock3.dbops;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

public class DbOps extends SQLiteOpenHelper {

    // database info
    private static final String NAME = "library";
    private static final int VERSION = 1;

    // table info
    private static final String TABLE = "books";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final String[] COLUMNS = {ID, TITLE, AUTHOR};

    // arged constructor for accessing Db from outside
    public DbOps(Context context) {
        super(context, NAME, null, VERSION);
    }

    // Override onCreate() to create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE + " ("
                        + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + TITLE + " TEXT, "
                        + AUTHOR + " TEXT); "
        );
    }

    // Override onUpgrade() to DROP TABLE IF EXISTS and create again with new Version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        this.onCreate(db);
    }

    // method for insert operation
    public void insertBook(Book book) {

        // get reference of the database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(TITLE, book.getTitle());
        values.put(AUTHOR, book.getAuthor());

        // insert book with values (title and author) into Db
        db.insert(TABLE, null, values);

        // close database transaction
        db.close();
    }

    // method for retrieve operation
    public Book retrieveBook(int id) {

        // get reference of the database
        SQLiteDatabase db = this.getReadableDatabase();

        // get query
        Cursor cursor = db.query(TABLE,
                COLUMNS, " id = ?", new String[] { String.valueOf(id) },
                null, null, null, null);

        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        // set book id, title and author and return book
        Book book = new Book();
        book.setId(Integer.parseInt(cursor.getString(0)));
        book.setTitle(cursor.getString(1));
        book.setAuthor(cursor.getString(2));
        return book;
    }

    // method for retrieve all books operation
    public List<Book> retrieveAllBooks() {

        List<Book> books = new LinkedList<>();

        // select all (*) query
        String query = "SELECT  * FROM " + TABLE;

        // get reference of the database
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        if (cursor.moveToFirst()) {
            do {
                // set book id, title and author and add to books list
                Book book = new Book();
                book.setId(Integer.parseInt(cursor.getString(0)));
                book.setTitle(cursor.getString(1));
                book.setAuthor(cursor.getString(2));

                // Add book to books list to pojoList
                books.add(book);
            } while (cursor.moveToNext());
        }
        return books;
    }

    // method for update book operation
    public int updateEntry(Book book) {

        // get reference to the database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put("title", book.getTitle()); // get title
        values.put("author", book.getAuthor()); // get author

        // update query
        int i = db.update(TABLE, values, ID + " = ?", new String[] { String.valueOf(book.getId()) });
        db.close();
        return i;
    }

    // method for delete book operation
    public void deleteBook(Book book) {

        // get reference to the database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete query
        db.delete(TABLE, ID + " = ?", new String[] { String.valueOf(book.getId()) });
        db.close();
    }

}
