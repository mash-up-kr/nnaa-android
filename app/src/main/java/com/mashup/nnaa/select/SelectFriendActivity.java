package com.mashup.nnaa.select;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.mashup.nnaa.R;
import com.mashup.nnaa.data.ContactItem;
import com.mashup.nnaa.util.SelectFriendAdapter;

import java.util.ArrayList;
import java.util.Collections;

import static android.provider.ContactsContract.Contacts.*;

public class SelectFriendActivity extends AppCompatActivity implements SelectFriendAdapter.IFriendItemClickListener {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friend);

        recyclerView = findViewById(R.id.rv_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SelectFriendAdapter(this));
        loadDataToRecyclerView();
    }

    private void loadDataToRecyclerView() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI,null, null, null, null);

        if (cursor == null) {
            return;
        }

        if (cursor.getCount() <= 0) {
            cursor.close();
            return;
        }

        ArrayList<ContactItem> itemList = new ArrayList<>();

        while (cursor.moveToNext()) {
            String id = cursor.getString(
                    cursor.getColumnIndex(_ID));
            String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
            String number = "";

            if (cursor.getInt(cursor.getColumnIndex(HAS_PHONE_NUMBER)) > 0) {
                Cursor pCur = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id}, null);

                while (pCur != null && pCur.moveToNext()) {
                    number = pCur.getString(pCur.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));
                }

                if (pCur != null)
                    pCur.close();

                // add if the phone number is existed
                itemList.add(new ContactItem(name, number));
            }
        }

        // sort itemList
        Collections.sort(itemList, (o1, o2) -> o1.getName().compareTo(o2.getName()));

        ((SelectFriendAdapter) recyclerView.getAdapter()).setData(itemList);
    }

    @Override
    public void onFriendItemClicked(ContactItem contactItem) {
        if (contactItem == null) {
            return;
        }

        Intent intent = new Intent(this, SetTypeOfFriendActivity.class);
        intent.putExtra("name", contactItem.getName());
        intent.putExtra("number", contactItem.getName());
        startActivity(intent);
    }
}
