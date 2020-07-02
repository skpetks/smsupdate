package com.example.contactapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.example.contactapp.Modal.ContactModal;
import com.example.contactapp.adater.contactadapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    // GUI Widget
    Button btn_server, btnInbox;
    TextView lblMsg, lblNo;
    ListView lvMsg;
    private RecyclerView contact_Recycler;
    // Cursor Adapter
    SimpleCursorAdapter adapter;

    public Dialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init GUI Widget
        btnInbox = (Button) findViewById(R.id.btnInbox);
        btnInbox.setOnClickListener(this);
        btn_server= (Button) findViewById(R.id.btn_server);
        btn_server.setOnClickListener(this);
        lvMsg = (ListView) findViewById(R.id.lvMsg);
        contact_Recycler= (RecyclerView) findViewById(R.id.contact_Recycler);

        LoadAllMessage();
    }


    @Override
    public void onClick(View v) {
        if (v == btnInbox) {
            LoadAllMessage();
        }
        if(v==btn_server)
        {
            mProgressDialog = popupDialogMessage(MainActivity.this);

            mProgressDialog.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgressDialog.dismiss();
                    finish();
                }
            }, 5000L);
        }
    }


    public void LoadAllMessage()
    {
        // Create Inbox box URI
        Uri inboxURI = Uri.parse("content://sms/inbox");

        // List required columns
        String[] reqCols = new String[] { "_id", "address", "body", "date" };

        // Get Content Resolver object, which will deal with Content
        // Provider
        ContentResolver cr = getContentResolver();

        // Fetch Inbox SMS Message from Built-in Content Provider
        Cursor c = cr.query(inboxURI, reqCols, null, null, null);

        List<ContactModal> lst=new ArrayList<ContactModal>();
        if (c.moveToFirst()) {
            do {
                ContactModal object = new ContactModal();
                object.setId(c.getString(c.getColumnIndex("_id")));
                object.setMessage(c.getString(c.getColumnIndex("address")));
                object.setBody(c.getString(c.getColumnIndex("body")));
                String body=c.getString(c.getColumnIndex("body"));
                String date=c.getString(c.getColumnIndex("date"));
                long milliSeconds = c.getLong(c.getColumnIndex("date"));
                DateFormat formatter = new SimpleDateFormat("MMM dd,hh:mm");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(milliSeconds);
                String finalDateString = formatter.format(calendar.getTime());
                object.setDate(finalDateString);

                if((body.toLowerCase().contains("bal") || body.toLowerCase().contains("amount") || body.toLowerCase().contains("receipt") ||  body.toLowerCase().contains("by rs.") ) && (body.toLowerCase().contains("debit")|| body.toLowerCase().contains("credit") )) {
                    lst.add(object);
                }

            } while (c.moveToNext());
        }


        contactadapter adapter1 = new contactadapter(MainActivity.this, lst);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        contact_Recycler.setLayoutManager(mLayoutManager);
        contact_Recycler.setItemAnimator(new DefaultItemAnimator());
        contact_Recycler.addItemDecoration(new DividerItemDecoration(contact_Recycler.getContext(), DividerItemDecoration.VERTICAL));

        contact_Recycler.setAdapter(adapter1);



    }


    public static Dialog popupDialogMessage (Activity activity)
    {
        Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //here we set layout of progress dialog
        dialog.setContentView(R.layout.progresspost);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        ImageView  imgLogo=(ImageView)dialog.findViewById(R.id.img_rotate);


        RotateAnimation  anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        //Setup anim with desired properties
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE); //Repeat animation indefinitely
        anim.setDuration(700); //Put desired duration per anim cycle here, in milliseconds
        //Start animation
        imgLogo.startAnimation(anim);

        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                // TODO Auto-generated method stub

            }
        });
        return dialog;
    }
}
