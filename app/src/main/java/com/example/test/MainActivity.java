package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_user, img_saved;
    private ListView listView;
    private ArrayList<User> list = new ArrayList<>();
    private ArrayList<User> list1 = new ArrayList<>();
    private DataAdapter adapter;
    private DBHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title_user);

        listView = findViewById(R.id.list_view);
        img_user = findViewById(R.id.im_contact);
        img_saved = findViewById(R.id.im_saved);
        img_user.setOnClickListener(this);
        img_saved.setOnClickListener(this);
        setupListView();
        dbHelper = new DBHelper(this);
    }



    private void setupListView() {
        for(int i = 0; i < 10; i++){
            list.add(User.random());
        }
        for(User user: list){
            list1.add(user);
        }

        adapter = new DataAdapter(this, list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EditUser.class);
                intent.putExtra(User.FIRST_NAME, list.get(position).getFirst_name());
                intent.putExtra(User.LAST_NAME, list.get(position).getLast_name());
                intent.putExtra(User.EMAIL, list.get(position).getEmail());
                intent.putExtra(User.PHONE, list.get(position).getPhone_number());
                intent.putExtra(User.PHOTO, list.get(position).getPhoto_user());
                intent.putExtra(User.ID, list.get(position).getId());
                startActivityForResult(intent, 1);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getResources().getString(R.string.delete));
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //delete item
                        if(list.get(position).getId() != -1){
                            dbHelper.deleteContact(list.get(position).getId());
                            list.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.setCancelable(true);
                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.im_contact:
                setTitle(R.string.title_user);
                img_user.setImageDrawable(getDrawable(R.drawable.tab_users_active));
                img_saved.setImageDrawable(getDrawable(R.drawable.tab_saved));
                list.clear();
                for(User user: list1){
                    list.add(user);
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.im_saved:
                saveBtn();
                break;
        }
    }

    public void saveBtn(){
        setTitle(R.string.title_saved);
        img_user.setImageDrawable(getDrawable(R.drawable.tab_users));
        img_saved.setImageDrawable(getDrawable(R.drawable.tab_saved_active));
        list.clear();
        list = dbHelper.getAllData(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {return;}
        Boolean ifSave = false;
        ifSave = data.getBooleanExtra("SAVE", false);
        if(ifSave){
            saveBtn();
        }
    }
}
