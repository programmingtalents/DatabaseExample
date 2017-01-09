package com.progammingtalents.android.databaseexample;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.progammingtalents.android.databaseexample.database.DBHelper;
import com.progammingtalents.android.databaseexample.model.BookData;
import com.progammingtalents.android.databaseexample.utils.CommonUtilities;
import com.progammingtalents.android.databaseexample.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    List<BookData> dataList;

    DBHelper dbHelper;
    String[] books_names= new String[]{
            "My experiments with Truth", "Far from the Madding Crowd", "Geetanjali", "The Merchant of venice", "A Tale of Two Cities", "Time Machine", "Arthashastra", "War and peace", "Raghuvamsa", "Adventures of Sherlock Holmes"
    };
    String[] authors_names= new String[]{
            "Mahatma M.K.Gandhi", "Thomas Hardy", "Rabindra Nath Tagore", "William shakespeare","Charles Dickens", "H.G. Wells", "Kautilya", "Leo Tolstoy", "Kalidas", "Arthur Conan Doyle"
    };
    String[] ids= new String[]{"1234", "2345", "3456", "4567", "5678", "6789", "7890", "8901", "9012", "1010"};
    ListView list;
    private static final int reqCode=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper= CommonUtilities.getDBObject(this);
        list= (ListView)findViewById(R.id.list);
        int count= dbHelper.getFullCount(Constants.BOOK_RECORD, null);
        if(count==0) {
            insertBookRecord();
        }
        list.setOnItemClickListener(this);

        dataList = dbHelper.getAllBooks();

        BookData bookData=dataList.get(0);
        bookData.getBookName();


        List<String> listTitle = new ArrayList<String>();

        for (int i = 0; i < dataList.size(); i++) {
            listTitle.add(i, dataList.get(i).getBookName());
        }

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,R.layout.row_layout, R.id.listText, listTitle);
        myAdapter.notifyDataSetChanged();

        list.setOnItemClickListener(this);
        list.setAdapter(myAdapter);


    }

    private void insertBookRecord(){
        for(int i=0; i<books_names.length; i++) {
            ContentValues vals = new ContentValues();
            vals.put(Constants.BOOK_ID, ids[i]);
            vals.put(Constants.BOOK_NAME, books_names[i]);
            vals.put(Constants.BOOK_AUTHOR, authors_names[i]);
            dbHelper.insertContentVals(Constants.BOOK_RECORD, vals);
        }
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i= new Intent(this, BookDetail.class);
        i.putExtra(Constants.BOOK_ID, ids[position]);
        startActivityForResult(i, reqCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==reqCode){
            // get all books again, because something changed
                dataList.clear();
                dataList = dbHelper.getAllBooks();

                List<String> listTitle = new ArrayList<String>();

                for (int i = 0; i < dataList.size(); i++) {
                    listTitle.add(i, dataList.get(i).getBookName());
                }

                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,R.layout.row_layout, R.id.listText, listTitle);
                myAdapter.notifyDataSetChanged();
                list.setOnItemClickListener(this);
                list.setAdapter(myAdapter);
            }
        }
    }
}
