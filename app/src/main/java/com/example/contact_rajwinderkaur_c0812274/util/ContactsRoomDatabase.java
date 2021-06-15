package com.example.contact_rajwinderkaur_c0812274.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.contact_rajwinderkaur_c0812274.data.ContactDao;
import com.example.contact_rajwinderkaur_c0812274.model.Contacts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Contacts.class}, version = 1, exportSchema = false)
public abstract class ContactsRoomDatabase extends RoomDatabase {

    public abstract ContactDao contactDao();

    private static volatile ContactsRoomDatabase INSTANCE;

    private static  final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public  static ContactsRoomDatabase getINSTANCE(final Context context){
        if (INSTANCE == null){
            synchronized (ContactsRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ContactsRoomDatabase.class,"Contacts")
                            .addCallback(sRoomDatabaseCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
   private static final RoomDatabase.Callback sRoomDatabaseCallBack = new RoomDatabase.Callback(){
        @Override
       public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                ContactDao contactDao = INSTANCE.contactDao();
                contactDao.deleteAll();
            });
        }
   };
}
