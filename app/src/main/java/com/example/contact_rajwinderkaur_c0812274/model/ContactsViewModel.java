package com.example.contact_rajwinderkaur_c0812274.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.contact_rajwinderkaur_c0812274.data.ContactRepsitory;

import java.util.List;

public class ContactsViewModel extends AndroidViewModel {

    private ContactRepsitory repository;
    private  final LiveData<List<Contacts>> allContacts;

    public  ContactsViewModel(@NonNull Application application){
        super(application);

        repository = new ContactRepsitory(application);
        allContacts = repository.getAllContacts();

    }

    public  LiveData<List<Contacts>> getAllContacts() {
        return  allContacts;
    }
    public   LiveData<Contacts> getContacts (int id){
        return repository.getContact(id);
    }
    public  void insert(Contacts contacts) {
        repository.insert(contacts);
    }
    public  void update(Contacts contacts) {
        repository.update(contacts);
    }
    public  void delete(Contacts contacts) {
        repository.delete(contacts);
    }
}
