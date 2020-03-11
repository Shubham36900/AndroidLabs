package com.example.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {
    private Bundle dataFromActivity;
    private long id;
    private AppCompatActivity parentActivity;
    boolean isTablet ;

   /* public DetailsFragment() {
        // Required empty public constructor
    }
*/
  public void setTablet(Boolean tablet){
        isTablet = tablet;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dataFromActivity = getArguments();
        long i = dataFromActivity.getLong(ChatRoomActivity.ID );
        View view  = inflater.inflate(R.layout.fragment_details, container, false);

        TextView message = view.findViewById(R.id.msg);
         message.setText("Message here - "+dataFromActivity.getString(ChatRoomActivity.ITEM_SELECTED));
        CheckBox chk = view.findViewById(R.id.checkBox2);

        chk.setChecked(dataFromActivity.getString(ChatRoomActivity.TYPE).equals("send"));
          Button hide = view.findViewById(R.id.hidden);


          hide.setOnClickListener(clik -> {
              if(isTablet) { //both the list and details are on the screen:
                  ChatRoomActivity parent = (ChatRoomActivity) getActivity();
                  // this is the object to be removed, so remove(this):
                  parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
              }
              //for Phone:
              else //You are only looking at the details, you need to go back to the previous list page
              {
                  Empty parent = (Empty) getActivity();
                  Intent backToFragmentExample = new Intent();
                  backToFragmentExample.putExtra(ChatRoomActivity.ID, dataFromActivity.getLong(ChatRoomActivity.ID ));

                  parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                  parent.finish(); //go back
              }

                       });


        TextView tex = view.findViewById(R.id.id1);
        tex.setText("id ="+ i);
         return view;

    }
}
