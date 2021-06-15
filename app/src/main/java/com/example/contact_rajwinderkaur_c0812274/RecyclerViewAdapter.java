package com.example.contact_rajwinderkaur_c0812274;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contact_rajwinderkaur_c0812274.model.Contacts;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    private List<Contacts> contactsList;
    private List<Contacts>contactsFullList;
    private Context context;
    private OnContactsClickListener onContactsClickListener;

    public RecyclerViewAdapter(List<Contacts> contactsList, Context context, OnContactsClickListener onContactsClickListener) {
        this.contactsList = contactsList;
        this.context = context;
        this.onContactsClickListener = onContactsClickListener;
        contactsFullList = new ArrayList<>(contactsList);


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_contacts, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contacts contacts = contactsList.get(position);
        holder.first_name.setText(contacts.getFirst_Name());
        holder.last_name.setText(contacts.getLast_Name());
        holder.E_mail.setText(contacts.getEmail());
        holder.phone_number.setText(contacts.getPhoneNumber());
        holder.address.setText(contacts.getAddress());

    }

    @Override
    public int getItemCount() {

        return contactsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView first_name;
        private TextView last_name;
        private TextView E_mail;
        private TextView phone_number;
        private TextView address;

        public ViewHolder(@NonNull View view) {
            super(view);

            first_name = view.findViewById(R.id.First_name);
            last_name = view.findViewById(R.id.Last_Name);
            E_mail = view.findViewById(R.id.Email);
            phone_number = view.findViewById(R.id.Phone_number);
            address = view.findViewById(R.id.Address);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view ) {
            onContactsClickListener.onContactsClick(getAdapterPosition());

        }

    }
    public interface OnContactsClickListener {
        void onContactsClick(int position);

    }
    @Override
    public Filter getFilter() {
        return filter;
    }
    public Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Contacts> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(contactsFullList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Contacts item : contactsFullList){
                    if(item.getFirst_Name().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                contactsList.clear();
                contactsList.addAll((List)results.values);
                notifyDataSetChanged();
        }
    };

}

