package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatRoomActivity extends AppCompatActivity {
    private ArrayList<Message> message = new ArrayList<Message>();
    private MyChat myAdapter;
    SQLiteDatabase db;
    private static String send;
    Button ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        loadDataFromDatabase();
        ListView ls = findViewById(R.id.theListView);
        ls.setAdapter(myAdapter = new MyChat());

        EditText edit = findViewById(R.id.ed);

        ss = findViewById(R.id.send);

        ss.setOnClickListener(click -> {

            send = edit.getText().toString();
            /*  edit.setText("");*/

            //add to the database and get the new ID
            ContentValues newRowValues = new ContentValues();

            //Now provide a value for every database column defined in MyOpener.java:
            //put string name in the NAME column:
            newRowValues.put(Opener.COL_NAME, send);
            newRowValues.put(Opener.COL_TYPE, "send");
            long newId = db.insert(Opener.TABLE_NAME, null, newRowValues);
            Message msg = new Message(send, "send", newId);
            message.add(msg);
            myAdapter.notifyDataSetChanged();
            edit.setText("");
        });
        Button re = findViewById(R.id.re);

        re.setOnClickListener(click -> {
            long newId;
            String receive = edit.getText().toString();
            edit.setText("");

            ContentValues newRowValues = new ContentValues();

            //Now provide a value for every database column defined in MyOpener.java:
            //put string name in the NAME column:
            newRowValues.put(Opener.COL_NAME, receive);
            newRowValues.put(Opener.COL_TYPE, "receive");
            newId = db.insert(Opener.TABLE_NAME, null, newRowValues);
            Message msg = new Message(receive, " ", newId);
            message.add(msg);
            myAdapter.notifyDataSetChanged();
            edit.setText("");
        });


        ls.setOnItemLongClickListener((a, b, c, d) -> {
            Message selectedContact = message.get(c);
            View contact_view = getLayoutInflater().inflate(R.layout.row, null);
            //get the TextViews
            TextView rowId = contact_view.findViewById(R.id.id);
            TextView rowName = contact_view.findViewById(R.id.name);
            rowName.setText("Message : " + selectedContact.getMessage());

            rowId.setText("id:" + selectedContact.getId());
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            //alertDialogBuilder.setTitle("A title")
            alertDialogBuilder.setTitle("You clicked on item #" + c)
                    .setView(contact_view)
                    .setMessage("Do you want to delete it")
                    .setPositiveButton("Yes", (click, arg) -> {

                        message.remove(c);
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Delete", (click, s) -> {

                        deleteContact(selectedContact); //remove the contact from database
                        message.remove(c); //remove the contact from contact list
                        myAdapter.notifyDataSetChanged(); //there is one less item so update the list
                    })
                    .create().show();
            return true;
        });

    }

    public void printCursor(Cursor c, int version) {

        int columns = c.getColumnCount();
        String cNames[] = c.getColumnNames();
        int re = c.getCount();

        Log.i("Version", Integer.toString(db.getVersion()));
        Log.i("names of the string", Arrays.toString(cNames));
        Log.i("number of columns -", Integer.toString(columns));
        Log.i("number of the results", Integer.toString(re));
        Log.i("Result", DatabaseUtils.dumpCursorToString(c));
    }

    private void loadDataFromDatabase() {
        //get a database connection:
        Opener dbOpener = new Opener(this);
        db = dbOpener.getWritableDatabase();


        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String[] columns = {Opener.COL_ID, Opener.COL_NAME, Opener.COL_TYPE};
        //query all the results from the database:
        Cursor results = db.query(false, Opener.TABLE_NAME, columns, null, null, null, null, null, null);

        //Now the results object has rows of results that match the query.
        //find the column indices:

        int nameColIndex = results.getColumnIndex(Opener.COL_NAME);
        int idColIndex = results.getColumnIndex(Opener.COL_ID);
        int type = results.getColumnIndex(Opener.COL_TYPE);
        //iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            String name = results.getString(nameColIndex);
            String types = results.getString(type);
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            message.add(new Message(name, types, id));
        }
        printCursor(results, Opener.VERSION_NUM);
        //At this point, the contactsList array has loaded every row from the cursor.
    }

    protected void deleteContact(Message c) {
        db.delete(Opener.TABLE_NAME, Opener.COL_ID + "= ?", new String[]{Long.toString(c.getId())});
    }

    private class MyChat extends BaseAdapter {
        @Override
        public int getCount() {
            return message.size();
        }

        @Override
        public Object getItem(int position) {
            return message.get(position).getMessage();
        }

        @Override
        public long getItemId(int position) {
            return message.get(position).getId();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View newView = inflater.inflate(R.layout.chatlayout, parent, false);
            View newView2 = inflater.inflate(R.layout.chat, parent, false);


            if (message.get(position).getType().equals("send")) {


                TextView text = newView.findViewById(R.id.chathere);
                text.setText(getItem(position).toString());
                return newView;
            } else {
                TextView text = newView2.findViewById(R.id.chathere);
                text.setText(getItem(position).toString());
                return newView2;


            }

        }


    }

}
