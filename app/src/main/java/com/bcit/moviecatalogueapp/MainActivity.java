package com.bcit.moviecatalogueapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    List<String> mTitle = new ArrayList<>();
    List<String> mLink = new ArrayList<>();
    List<String> mDescription = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Movies");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle.add("Jumaji");
        mDescription.add("When two kids find and play a magical " +
                "board game, they release a man trapped in it for " +
                "decades--and a host of dangers that can " +
                "only be stopped by finishing the game.");
        mLink.add("https://www.imdb.com/title/tt0113497/");

        listView = findViewById(R.id.listView);

        MyAdapter adapter = new MyAdapter(this, mTitle, mLink, mDescription);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                final EditText descriptionEditText = new EditText(this);
                final EditText linkEditText = new EditText(this);
                final EditText titleEditText = new EditText(this);


                final AlertDialog description = new AlertDialog.Builder(this)
                        .setTitle("Add the Description")
                        .setMessage("Describe the movie?")
                        .setView(descriptionEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String description = String.valueOf(descriptionEditText.getText());
                                mDescription.add(description);

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();

                final AlertDialog link = new AlertDialog.Builder(this)
                        .setTitle("Add a new link")
                        .setMessage("What is the IMDB link?")
                        .setView(linkEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String link = String.valueOf(linkEditText.getText());
                                mLink.add(link);
                                }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();




                AlertDialog title = new AlertDialog.Builder(this)
                        .setTitle("Add a new title")
                        .setMessage("What is the title of the movie?")
                        .setView(titleEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String title = String.valueOf(titleEditText.getText());
                                mTitle.add(title);
                                }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();

                title.show();
                link.show();
                description.show();

                // Adds the previous Item in the list to Firebase.
                String key = myRef.push().getKey();
                myRef.child(key).setValue(
                        new Movie(
                                mTitle.get(mTitle.size()-1),
                                mDescription.get(mDescription.size()-1),
                                mLink.get(mLink.size()-1)));

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        List<String> rTitle;
        List<String> rDescription;
        List<String> rLink;

        MyAdapter (Context c, List<String> title,List<String> link , List<String> description) {
            super(c, R.layout.movie_item, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rLink = link;
            this.rDescription = description;
            

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View movie_item = layoutInflater.inflate(R.layout.movie_item, parent, false);
            TextView myTitle = movie_item.findViewById(R.id.textView1);
            TextView myLink = movie_item.findViewById(R.id.textView2);
            TextView myDescription = movie_item.findViewById(R.id.textView3);

            // now set our resources on views
            myTitle.setText(rTitle.get(position));
            myLink.setText(rLink.get(position));
            myDescription.setText(rDescription.get(position));



            return movie_item;
        }

    }
}
