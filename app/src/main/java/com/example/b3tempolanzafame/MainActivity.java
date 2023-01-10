package com.example.b3tempoLanzafame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.b3tempoLanzafame.databinding.ActivityMainBinding;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String CHANNEL_ID = "tempo_alert_channel_id";
    public static IEdfApi edfApi;
    private static final int ALARM_MANAGER_REQUEST_CODE = 2023;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // init views
        binding.historyBt.setOnClickListener(this::onClick);
        binding.historyBt2.setOnClickListener(this::onClick2);

        // init alarm
        //initAlarmManager();

        createNotificationChannel();

        // Init Retrofit client
        Retrofit retrofitClient = ApiClient.get();
        if (retrofitClient != null) {
            // Create EDF API Call interface
            edfApi = retrofitClient.create(IEdfApi.class);
        } else {
            Log.e(LOG_TAG, "unable to initialize Retrofit client");
            finish();
        }
    }

    protected void onResume(){
        super.onResume();
        updateTempoDateColor();
        updateTempoDaysLeft();

    }

    /*
    public void showHistory(View view) {
        Intent intent = new Intent();
        intent.setClass(this,HistoryActivity.class);
        startActivity(intent);
    }
    */

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= 32) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void checkColor4Notif(TempoColor nextDayColor){
        //if (nextDayColor == TempoColor.RED || nextDayColor == TempoColor.WHITE) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getString(R.string.notif_nextdaycolor_title))
                    .setContentText(getString(R.string.notif_nextdaycolor_description) + nextDayColor)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(Tools.getNextNotifId(), builder.build());
        //}
    }

    /*
    private void initAlarmManager(){
        // create a pending intent
        Intent intent = new Intent(this, TempoAlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(
                this,
                ALARM_MANAGER_REQUEST_CODE,
                intent,
                0
        );
    }
    */

    private void updateTempoDateColor(){
        Call<TempoDaysColor> call2;
        call2 = edfApi.getTempoDaysColor("2022-12-12",IEdfApi.EDF_TEMPO_API_ALERT_TYPE);

        call2.enqueue(new Callback<TempoDaysColor>() {
            @Override
            public void onResponse(@NonNull Call<TempoDaysColor> call, @NonNull Response<TempoDaysColor> response) {
                TempoDaysColor tempoDaysColor = response.body();
                if (response.code() == HttpURLConnection.HTTP_OK && tempoDaysColor != null) {
                    Log.d(LOG_TAG,"Today color = " + tempoDaysColor.getCouleurJourJ().toString());
                    Log.d(LOG_TAG,"Tomorrow color = " + tempoDaysColor.getCouleurJourJ1().toString());
                    binding.todayDcv.setDayCircleColor(tempoDaysColor.getCouleurJourJ());
                    binding.tomorrowDcv.setDayCircleColor(tempoDaysColor.getCouleurJourJ1());
                    binding.todayDcv.setCaptionTextColor(tempoDaysColor.getCouleurJourJ());
                    binding.tomorrowDcv.setCaptionTextColor(tempoDaysColor.getCouleurJourJ1());
                    checkColor4Notif(tempoDaysColor.getCouleurJourJ1());
                } else {
                    Log.w(LOG_TAG, "call to getTempoDaysColor() failed with error code " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TempoDaysColor> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, "call to getTempoDaysColor() failed ");
            }
        });
    }

    private void updateTempoDaysLeft(){
        Call<TempoDaysLeft> call = edfApi.getTempoDaysLeft(IEdfApi.EDF_TEMPO_API_ALERT_TYPE);

        call.enqueue(new Callback<TempoDaysLeft>() {
            @Override
            public void onResponse(@NonNull Call<TempoDaysLeft> call, @NonNull Response<TempoDaysLeft> response) {
                TempoDaysLeft tempoDaysLeft = response.body();
                if (response.code() == HttpURLConnection.HTTP_OK && tempoDaysLeft != null) {
                    Log.d(LOG_TAG, "nb red days = " + tempoDaysLeft.getParamNbJRouge());
                    Log.d(LOG_TAG, "nb white days = " + tempoDaysLeft.getParamNbJBlanc());
                    Log.d(LOG_TAG, "nb blue days = " + tempoDaysLeft.getParamNbJBleu());
                    binding.blueDaysTv.setText(String.valueOf(tempoDaysLeft.getParamNbJBleu()));
                    binding.whiteDaysTv.setText(String.valueOf(tempoDaysLeft.getParamNbJBlanc()));
                    binding.redDaysTv.setText(String.valueOf(tempoDaysLeft.getParamNbJRouge()));
                } else {
                    Log.w(LOG_TAG, "call to getTempoDaysLeft () failed with error code " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TempoDaysLeft> call, @NonNull Throwable t) {
                Log.e(LOG_TAG, "call to getTempoDaysLeft () failed ");
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(this,HistoryActivity.class);
        startActivity(intent);
    }

    public void onClick2(View v) {
        Intent intent = new Intent();
        intent.setClass(this,HistoryActivity2.class);
        startActivity(intent);
    }
}
