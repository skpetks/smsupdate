package com.example.contactapp.Common;

import android.app.Activity;
import android.widget.Toast;

public class Utils {

    public static void customtoastonly(String message, Activity ct)
    {
        Toast.makeText(ct, ""+message, Toast.LENGTH_LONG).show();
    }
}
