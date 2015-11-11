//package com.zsayed.mtanextbus;
//
//import android.content.DialogInterface;
//import android.support.v7.app.AlertDialog;
//import android.widget.EditText;
//
///**
// * Created by e-zrsd on 10/30/2015.
// */
//public class CustomActions {
//    AlertDialog.Builder alert = new AlertDialog.Builder(this);
//
//    alert.setTitle("Title");
//    alert.setMessage("Message");
//
//    // Set an EditText view to get user input
//    final EditText input = new EditText(this);
//    alert.setView(input);
//
//    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//        public void onClick(DialogInterface dialog, int whichButton) {
//            String value = input.getText().toString();
//            // Do something with value!
//        }
//    });
//
//    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//        public void onClick(DialogInterface dialog, int whichButton) {
//            // Canceled.
//        }
//    });
//
//    alert.show();
//}
