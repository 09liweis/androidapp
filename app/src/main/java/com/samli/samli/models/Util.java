package com.samli.samli.models;

import android.content.Context;
import android.widget.Toast;

public class Util {
  public static void showToast(Context c,String msg) {
    Toast.makeText(c, msg, Toast.LENGTH_LONG).show();
  }
}
