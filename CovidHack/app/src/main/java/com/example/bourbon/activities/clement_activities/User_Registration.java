package com.example.bourbon.activities.clement_activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bourbon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class User_Registration extends AppCompatActivity {

    @BindView(R.id.first_name)
    EditText firstName;
    @BindView(R.id.last_name)
    EditText lastName;
    @BindView(R.id.address)
    EditText address;
    Calendar myCalendar;
    @BindView(R.id.gender)
    Spinner gender;
    @BindView(R.id.dob)
    Button dob;
    SimpleDateFormat sdf;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        myCalendar = Calendar.getInstance();
        dob = findViewById(R.id.dob);
        final DatePickerDialog.OnDateSetListener dae = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yy"; //In which you need put here
                sdf = new SimpleDateFormat(myFormat, Locale.UK);
                Toast.makeText(User_Registration.this, sdf.format(myCalendar.getTime()), Toast.LENGTH_SHORT).show();
            }

        };

        dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(User_Registration.this, dae, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");


    }

    @OnClick(R.id.send)
    public void onViewClicked() {

        mDatabase.child(user.getUid()).child("Name").setValue(firstName.getText().toString() + " " + lastName.getText().toString());
        mDatabase.child(user.getUid()).child("Address").setValue(address.getText().toString());
        mDatabase.child(user.getUid()).child("Gender").setValue(gender.getSelectedItem().toString());
        mDatabase.child(user.getUid()).child("DOB").setValue(sdf.format(myCalendar.getTime()));

        Intent intent = new Intent(User_Registration.this,Main_menu.class);
        startActivity(intent);
    }

}
