package com.progammingtalents.android.databaseexample;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.progammingtalents.android.databaseexample.database.DBHelper;
import com.progammingtalents.android.databaseexample.utils.CommonUtilities;
import com.progammingtalents.android.databaseexample.utils.Constants;


public class BookDetail extends AppCompatActivity {

    TextView bookTitle;
    TextView authorName;
    EditText title_edt;
    EditText author_edt;
    DBHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book);

        bookTitle = (TextView) findViewById(R.id.title);
        authorName = (TextView) findViewById(R.id.author);

        title_edt=(EditText) findViewById(R.id.titleEdit);
        author_edt= (EditText) findViewById(R.id.authorEdit);

        // get the intent that we have passed from AndroidDatabaseExample
        Intent intent = getIntent();
        String id = intent.getStringExtra(Constants.BOOK_ID);

        // open the database of the application context
        db = CommonUtilities.getDBObject(this);

        // read the book with "id" from the database
        String bookName= db.getValue(Constants.BOOK_RECORD, Constants.BOOK_NAME, Constants.BOOK_ID + " = '" + id+ "'");
        String author_name= db.getValue(Constants.BOOK_RECORD, Constants.BOOK_AUTHOR, Constants.BOOK_ID + " = '" + id+ "'");
        Log.e("name", "= "+ bookName+  " author= "+ author_name);
        bookTitle.setText(bookName);
        authorName.setText(author_name);

    }

    public void update(View v) {
        Toast.makeText(getApplicationContext(), "This book is updated.", Toast.LENGTH_SHORT).show();

        ContentValues vals= new ContentValues();
        vals.put(Constants.BOOK_NAME, title_edt.getText().toString().trim());
        vals.put(Constants.BOOK_AUTHOR, author_edt.getText().toString().trim());
        db.updateRecords(Constants.BOOK_RECORD, vals, Constants.BOOK_ID+ " = '"+ getIntent().getExtras().getString(Constants.BOOK_ID)
        + "'", null);
        finish();
    }

    public void delete(View v) {
        Toast.makeText(getApplicationContext(), "This book is deleted.", Toast.LENGTH_SHORT).show();

        // delete selected book
        db.deleteRecords(Constants.BOOK_RECORD, Constants.BOOK_ID+ " = '" + getIntent().getExtras().getString(Constants.BOOK_ID)+
        "' ", null);
        finish();
    }
}
