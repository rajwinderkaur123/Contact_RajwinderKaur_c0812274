package com.example.contact_rajwinderkaur_c0812274.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.contact_rajwinderkaur_c0812274.model.Contacts;
import com.example.contact_rajwinderkaur_c0812274.util.ContactsRoomDatabase;

import java.util.List;

public class ContactRepsitory {

    private ContactDao contactDao;
    private LiveData<List<Contacts>> allContacts;

    public ContactRepsitory(Application application){
        ContactsRoomDatabase db = ContactsRoomDatabase.getINSTANCE(application);
        contactDao = db.contactDao();
        allContacts = contactDao.getAllContacts();

    }
    public  LiveData<List<Contacts>>getAllContacts(){
        return  allContacts;
    }
    public LiveData<Contacts> getContact(int id){
        return  contactDao.getContacts(id);

    }
    public void  insert(Contacts contacts){
        ContactsRoomDatabase.databaseWriteExecutor.execute(() -> contactDao.insert(contacts));
    }
    public void  update(Contacts contacts){
        ContactsRoomDatabase.databaseWriteExecutor.execute(() -> contactDao.update(contacts));
    }
    public void  delete(Contacts contacts){
        ContactsRoomDatabase.databaseWriteExecutor.execute(() -> contactDao.delete(contacts));
    }
}
