package com.soloviev.contactsapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ContactActivity extends Activity {
    TextView mNameView;
    TextView mPhoneView;
    TextView mEmailView;
    TextView mAddressView;
    TextView mBirthDateView;
    TextView mOccupationView;
    Button mSave;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        mNameView = (TextView) findViewById(R.id.name);
        mPhoneView = (TextView) findViewById(R.id.phone);
        mEmailView = (TextView) findViewById(R.id.email);
        mAddressView = (TextView) findViewById(R.id.address);
        mBirthDateView = (TextView) findViewById(R.id.birthdate);
        mOccupationView = (TextView) findViewById(R.id.occupation);
        mSave = (Button) findViewById(R.id.save);

        init((int) getIntent().getSerializableExtra("contact"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;

    }

    private void init(int id) {
        contact = ContactsRepository.getInstance().getContact(id);
        mNameView.setText(contact.getName());
        mPhoneView.setText(contact.getPhone());
        mEmailView.setText(contact.getEmail());
        mAddressView.setText(contact.getAddress());
        mBirthDateView.setText(DateFormat.getDateFormat(this).format(contact.getBirthDate()));
        mOccupationView.setText(contact.getOccupation());
    }
public void editContact(){
    contact.setName(mNameView.getText().toString());
    contact.setPhone(mPhoneView.getText().toString());
    contact.setEmail(mEmailView.getText().toString());
    contact.setAddress(mAddressView.getText().toString());
    /*TODO*/
    //contact.setBirthDate((java.util.Date) mBirthDateView.getText());
    contact.setOccupation(mOccupationView.getText().toString());

}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case R.id.action_edit: {
                mSave.setVisibility(View.VISIBLE);
                mSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editContact();
                        mSave.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
