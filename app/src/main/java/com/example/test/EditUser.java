package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditUser extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_user, btn_back;
    private TextView change_photo, btn_save;
    private EditText ed_first, ed_last, ed_email, ed_phone;
    private String first_name = "", last_name = "", email = "", phone = "";
    private int photo, id;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);



        first_name = getIntent().getStringExtra(User.FIRST_NAME);
        last_name = getIntent().getStringExtra(User.LAST_NAME);
        email = getIntent().getStringExtra(User.EMAIL);
        phone = getIntent().getStringExtra(User.PHONE);
        photo = getIntent().getIntExtra(User.PHOTO, 0);
        id = getIntent().getIntExtra(User.ID, -1);

        btn_back = findViewById(R.id.btn_back);
        btn_save = findViewById(R.id.btn_save);
        img_user = findViewById(R.id.edit_image);
        change_photo = findViewById(R.id.change_photo);
        ed_first = findViewById(R.id.first_name_txt);
        ed_last = findViewById(R.id.last_name_txt);
        ed_email = findViewById(R.id.email_txt);
        ed_phone = findViewById(R.id.phone_txt);

        setImage();
        setED();

        change_photo.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        dbHelper = new DBHelper(this);
    }

    private void setImage(){
        if(photo == 0){
            img_user.setImageDrawable(getDrawable(R.drawable.no_photo));
        }else {
            img_user.setImageDrawable(getDrawable(photo));
        }
    }

    private void setED(){
        ed_phone.setText(phone);
        ed_phone.setHint(phone);

        ed_email.setText(email);
        ed_email.setHint(email);

        ed_first.setText(first_name);
        ed_first.setHint(first_name);

        ed_last.setText(last_name);
        ed_last.setHint(last_name);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_photo:
                //change photo
                break;
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_save:
                //save
                if(!ed_first.getText().toString().equals("") && !ed_last.getText().toString().equals("") && !ed_email.getText().toString().equals("")
                && !ed_phone.getText().toString().equals("")) {
                    User user = new User();
                    user.setFirst_name(ed_first.getText().toString());
                    user.setLast_name(ed_last.getText().toString());
                    user.setEmail(ed_email.getText().toString());
                    user.setPhone_number(ed_phone.getText().toString());
                    user.setPhoto_user(photo);

                    if(id == -1) {
                        dbHelper.addContact(user);
                    }else{
                        dbHelper.updateContact(user);
                    }
                } else {
                    Toast.makeText(this, getResources().getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent();
                intent.putExtra("SAVE", true);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
