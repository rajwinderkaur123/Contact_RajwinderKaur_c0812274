package com.example.contact_rajwinderkaur_c0812274;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.contact_rajwinderkaur_c0812274.model.Contacts;
import com.example.contact_rajwinderkaur_c0812274.model.ContactsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements  RecyclerViewAdapter.OnContactsClickListener {

    private static final String TAG = "MainActivity";
    public static final int ADD_CONTACTS_REQUEST_CODE = 1;
    public static final String CONTACTS_ID  = "contacts_id";


    private ContactsViewModel contactsViewModel;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    private Contacts deletedContacts;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        contactsViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication())
                .create(ContactsViewModel.class);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactsViewModel.getAllContacts().observe(this, contacts -> {

            recyclerViewAdapter = new RecyclerViewAdapter(contacts, this,this );
            recyclerView.setAdapter(recyclerViewAdapter);
        });

        FloatingActionButton Add = findViewById(R.id.add);
        Add.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddContacts.class);
            startActivityForResult(intent ,ADD_CONTACTS_REQUEST_CODE);
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Contacts contacts = contactsViewModel.getAllContacts().getValue().get(position);

            switch (direction) {
                case ItemTouchHelper.LEFT:
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Are you sure to delete Contact");
                    builder.setPositiveButton("YES", (dialog, which) -> {
                        deletedContacts = contacts;
                        contactsViewModel.delete(contacts);
                        Snackbar.make(recyclerView, deletedContacts.getFirst_Name() + " is Deleted", Snackbar.LENGTH_LONG)
                                .setAction("UNDO", v -> contactsViewModel.insert(deletedContacts)).show();
                    });

                    builder.setNegativeButton("NO", (dialog, which) -> recyclerViewAdapter.notifyDataSetChanged());
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        break;
            }
        }

        @Override
        public void onChildDraw(@NonNull  Canvas c, @NonNull  RecyclerView recyclerView, @NonNull  RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .setIconHorizontalMargin(1,1)
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
        @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_CONTACTS_REQUEST_CODE && resultCode == RESULT_OK){

            Log.d(TAG,"onActivityResult: " + data.getStringExtra(AddContacts.FIRSTNAME_REPLY));

            String firstname = data.getStringExtra(AddContacts.FIRSTNAME_REPLY);
            String lastName = data.getStringExtra(AddContacts.LASTNAME_REPLY);
            String email = data.getStringExtra(AddContacts.EMAIL_REPLY);
            String phonenumber = data.getStringExtra(AddContacts.PHONENUMBER_REPLY);
            String address = data.getStringExtra(AddContacts.ADDRESS_REPLY);

            Contacts contacts = new Contacts(firstname,lastName,email,phonenumber,address);
            contactsViewModel.insert(contacts);
        }
    }

    @Override
    public  void onContactsClick(int position) {
        Contacts contacts = contactsViewModel.getAllContacts().getValue().get(position);
        Intent intent = new Intent(MainActivity.this, AddContacts.class);
        intent.putExtra(CONTACTS_ID, contacts.getId());
        startActivity(intent);
    }
    /*
    @Override
    public  void onContactsClick(int position){
        Contacts contacts = contactsViewModel.getAllContacts().getValue().get(position);
        Intent intent = new Intent(MainActivity . this,CallandSms.class);
        intent.putExtra("Name", contacts.getFirst_Name());
        intent.putExtra("PhoneNumber", contacts.getPhoneNumber());
        startActivity(intent);
    }
*/


    ///////////////Search Contacts ////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.serach_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.search_bar);
         SearchView searchView = (SearchView) searchItem.getActionView();

         searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

         searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
              recyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
        }

}
