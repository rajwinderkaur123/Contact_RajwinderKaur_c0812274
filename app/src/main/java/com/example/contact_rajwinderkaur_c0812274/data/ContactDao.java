package com.example.contact_rajwinderkaur_c0812274.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contact_rajwinderkaur_c0812274.model.Contacts;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Contacts contact);

    @Query("DELETE FROM contact")
    void deleteAll();

    @Query("SELECT * FROM contact ORDER BY first_Name ASC")
    LiveData<List<Contacts>>getAllContacts();

    @Query("SELECT * FROM contact WHERE id == :id")
    LiveData<Contacts> getContacts(int id);

    @Update
    void update(Contacts contact);

    @Delete
    void delete(Contacts contact);
}
