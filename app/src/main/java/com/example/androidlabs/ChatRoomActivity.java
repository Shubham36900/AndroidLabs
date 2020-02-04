package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
   private ArrayList<Message> message = new ArrayList<Message>();
    private MyChat myAdapter;
private static String send;
    Button ss ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView ls = findViewById(R.id.theListView);
        ls.setAdapter(myAdapter = new MyChat());

        EditText edit = findViewById(R.id.ed);

         ss = findViewById(R.id.send);

        ss.setOnClickListener(click -> {

            send  = edit.getText().toString();
            edit.setText("");
       message.add(new Message(send, "send"));
            myAdapter.notifyDataSetChanged();
       });
        Button re = findViewById(R.id.re);

          re.setOnClickListener(click -> {

              String receive  = edit.getText().toString();
              edit.setText("");

          message.add(new Message(receive,"receive"));
          myAdapter.notifyDataSetChanged();
          } );
        ls.setOnItemLongClickListener((a,b,c,d)-> {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("A title")
                    .setMessage("Do you want to delete it")
                    .setPositiveButton("Yes",(click , arg)->{

                      message.remove(c);
                        myAdapter.notifyDataSetChanged();
                    })
                    .create().show();
            return true;
        });

    }
    private class MyChat extends BaseAdapter{
    @Override
        public int getCount(){
        return message.size();
        }

        @Override
        public Object getItem(int position) {
            return message.get(position).getMessage();
        }

        @Override
        public long getItemId(int position) {
            return (long)position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View newView =  inflater.inflate(R.layout.chatlayout, parent, false);
            View newView2 = inflater.inflate(R.layout.chat, parent, false);


               if(message.get(position).getType().equals("send")) {


                   TextView text = newView.findViewById(R.id.chathere);
                  text.setText(getItem(position).toString());
                   return newView;
               }else {
                   message.get(position).getType().equals("receive") ;


                TextView text = newView2.findViewById(R.id.chathere);
                text.setText(getItem(position).toString());
                return newView2;

            }

        }



    }

}
