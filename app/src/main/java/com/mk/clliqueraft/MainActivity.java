package com.mk.clliqueraft;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.material.snackbar.Snackbar;
import com.mk.clliqueraft.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    public static final String NOTIFICATION_CHANNEL_ID = "1000" ;
    private final static String default_notification_channel_id = "default" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.generateToast.setOnClickListener(view -> {
            String TEXT = binding.EditTextEdt.getText().toString();
            if (binding.EditTextEdt.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this, "Please Enter Text", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(MainActivity.this, TEXT, Toast.LENGTH_SHORT).show();
                Snackbar.make(binding.getRoot(),TEXT,Snackbar.LENGTH_SHORT).show();
            }
        });

        binding.generateNotification.setOnClickListener(view -> {
            String TEXT = binding.EditTextEdt.getText().toString();
            if (binding.EditTextEdt.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this, "Please Enter Text", Toast.LENGTH_SHORT).show();
            }else {
                createNotification(TEXT);

            }
        });

        binding.generateModal.setOnClickListener(view -> {
            String TEXT = binding.EditTextEdt.getText().toString();
            if (binding.EditTextEdt.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this, "Please Enter Text", Toast.LENGTH_SHORT).show();
            }else {
                showDialog(this,TEXT);
            }
        });

        binding.generateCompleted.setOnClickListener(view -> {
            String TEXT = binding.EditTextEdt.getText().toString();
            if (binding.EditTextEdt.getText().toString().isEmpty()){
                Toast.makeText(MainActivity.this, "Please Enter Text", Toast.LENGTH_SHORT).show();
            }else {
                binding.EditTextEdt.setText("COMPLETED");
                binding.generateCompleted.setText("COMPLETED");
            }
        });


    }

    private void createNotification(String text) {

        int NOTIFICATION_ID = ( int ) System. currentTimeMillis () ;
        PendingIntent pendingIntent = PendingIntent. getActivity ( this, 0 , new Intent() , 0 ) ;
        Intent buttonIntent = new Intent( this, NotificationBroadcastReceiver. class ) ;
        buttonIntent.putExtra( "notificationId" , NOTIFICATION_ID) ;
        PendingIntent btPendingIntent = PendingIntent. getBroadcast ( this, 0 , buttonIntent , 0 ) ;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() , default_notification_channel_id ) ;
        mBuilder.setContentTitle( text ) ;
        mBuilder.setContentIntent(pendingIntent) ;
        mBuilder.addAction(R.drawable. ic_launcher_foreground , "Cancel" , btPendingIntent) ;
        mBuilder.setContentText( text ) ;
        mBuilder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        mBuilder.setAutoCancel( true ) ;
        mBuilder.setDeleteIntent(getDeleteIntent()) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(NOTIFICATION_ID , mBuilder.build());
    }

    protected PendingIntent getDeleteIntent () {
        Intent intent = new Intent(MainActivity. this,
                NotificationBroadcastReceiver. class ) ;
        intent.setAction( "notification_cancelled" ) ;
        return PendingIntent. getBroadcast (MainActivity. this, 0 , intent , PendingIntent. FLAG_CANCEL_CURRENT ) ;
    }


    public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.modal_view);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }


}

