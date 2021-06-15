package com.example.contact_rajwinderkaur_c0812274;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contact_rajwinderkaur_c0812274.model.Contacts;
import com.example.contact_rajwinderkaur_c0812274.model.ContactsViewModel;

import java.lang.reflect.Array;
import java.util.Arrays;

public class AddContacts extends AppCompatActivity {

    private EditText etfrstName, etlastName, email, phonenumber, address;

    private ContactsViewModel contactsViewModel;

    private boolean isediting = false;
    private int contactsId = 0;
    private Contacts contactToBeUpdated;

    public static final String FIRSTNAME_REPLY = "firstname_reply";
    public static final String LASTNAME_REPLY = "lastname_reply";
    public static final String EMAIL_REPLY = "email_reply";
    public static final String PHONENUMBER_REPLY = "phone_number_reply";
    public static final String ADDRESS_REPLY = "address_reply";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        contactsViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication())
                .create(ContactsViewModel.class);

        etfrstName = findViewById(R.id.First_name);
        etlastName = findViewById(R.id.Last_Name);
        email = findViewById(R.id.Email);
        phonenumber = findViewById(R.id.Phone_number);
        address = findViewById(R.id.Address);

        Button UpdateButton  = findViewById(R.id.btn_add_Contact);

        UpdateButton.setOnClickListener(v -> {
            addUpdate();
        });

        if(getIntent().hasExtra(MainActivity.CONTACTS_ID)){
            contactsId = getIntent().getIntExtra(MainActivity.CONTACTS_ID,0);
            Log.d("TAG","onCreate:" + contactsId);

            contactsViewModel.getContacts(contactsId).observe(this, contacts -> {
                if(contacts != null){
                    etfrstName.setText(contacts.getFirst_Name());
                    etlastName.setText(contacts.getLast_Name());
                    email.setText(contacts.getEmail());
                    phonenumber.setText(contacts.getPhoneNumber());
                    address.setText(contacts.getAddress());
                    contactToBeUpdated = contacts;
                }
            });

           TextView label = findViewById(R.id.label);
           isediting = true;
           label.setText("Update Contacts");
           TextView label2 = findViewById(R.id.btn_add_Contact);
           label2.setText("Update Contact");

        }
    }

    private void addUpdate() {

        String firstName = etfrstName.getText().toString().trim();
        String lastname = etlastName.getText().toString().trim();
        String e_mail = email.getText().toString().trim();
        String phoneNumber = phonenumber.getText().toString().trim();
        String Address = address.getText().toString().trim();

        if(firstName.isEmpty()) {
            etfrstName.setError("First name cannot be empty");
            etfrstName.requestFocus();
            return;
        }
            if(lastname.isEmpty()) {
                etlastName.setError("Last name cannot be empty");
                etlastName.requestFocus();
                return;
            }
                if(e_mail.isEmpty()) {
                    email.setError("Email cannot be empty");
                    email.requestFocus();
                    return;
                }
                    if(phoneNumber.isEmpty()) {
                        phonenumber.setError("Phone number  cannot be empty");
                        phonenumber.requestFocus();
                        return;
                    }
                        if(Address.isEmpty()){
                            address.setError("First name cannot be empty");
                            address.requestFocus();
                            return;
        }
                        if(isediting){
                            Contacts contacts = new Contacts();
                            contacts.setId(contactsId);
                            contacts.setFirst_Name(firstName);
                            contacts.setLast_Name(lastname);
                            contacts.setEmail(e_mail);
                            contacts.setPhoneNumber(phoneNumber);
                            contacts.setAddress(Address);
                            contactsViewModel.update(contacts);
                        }else {
                            Intent replyIntent = new Intent();
                            replyIntent.putExtra(FIRSTNAME_REPLY, firstName);
                            replyIntent.putExtra(LASTNAME_REPLY, lastname);
                            replyIntent.putExtra(EMAIL_REPLY, e_mail);
                            replyIntent.putExtra(PHONENUMBER_REPLY, phoneNumber);
                            replyIntent.putExtra(ADDRESS_REPLY, Address);
                            setResult(RESULT_OK, replyIntent);


                        }
                        finish();
    }
}