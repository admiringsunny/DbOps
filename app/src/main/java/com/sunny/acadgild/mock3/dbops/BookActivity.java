package com.sunny.acadgild.mock3.dbops;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BookActivity extends AppCompatActivity {

    private TextView title, author;
    private EditText titleEdit, authorEdit;
    private DbOps db;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        // initialize title and author
        title = (TextView) findViewById(R.id.book_title);
        author = (TextView) findViewById(R.id.book_author);
        titleEdit = (EditText) findViewById(R.id.titleEdit);
        authorEdit = (EditText) findViewById(R.id.authorEdit);

        Intent intent = getIntent();
        int id = intent.getIntExtra("book", -1);

        db = new DbOps(this);
        book = db.retrieveBook(id);

        initializeViews();
    }

    private void initializeViews() {
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
    }

    public void update(View view) {
        Toast.makeText(getApplicationContext(),"This book is updated.",Toast.LENGTH_LONG).show();
        book.setTitle(titleEdit.getText().toString());
        book.setAuthor(authorEdit.getText().toString());
        db.updateEntry(book);
        finish();
    }
    public void delete(View view) {
        Toast.makeText(getApplicationContext(), "This book is deleted.", Toast.LENGTH_LONG).show();
        db.deleteBook(book);
        finish();
    }
}
