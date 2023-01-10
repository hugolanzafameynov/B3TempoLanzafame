package com.example.b3tempoLanzafame;

import static com.example.b3tempoLanzafame.MainActivity.edfApi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.b3tempoLanzafame.databinding.ActivityHistory2Binding;
import com.example.b3tempoLanzafame.databinding.ActivityMainBinding;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity2 extends AppCompatActivity {
    private static final String LOG_TAG = HistoryActivity.class.getSimpleName();

    // Init views
    private ActivityHistory2Binding binding;

    // Data model
    private List<TempoDate> tempoDates = new ArrayList<>();

    // RV adapter
    private TempoDateAdapter2 tempoDateAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistory2Binding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_history);
        setContentView(binding.getRoot());

        // Init recycler view
        binding.tempoHistoryRv.setHasFixedSize(true);
        binding.tempoHistoryRv.setLayoutManager(new LinearLayoutManager(this));

        tempoDateAdapter2 = new TempoDateAdapter2(tempoDates, this);

        binding.tempoHistoryRv.setAdapter(tempoDateAdapter2);

        if (edfApi != null) {
            // Create call to getTempoDaysLeft
            Call<TempoHistory> call = edfApi.getTempoHistory("2021", "2022");

            call.enqueue(new Callback<TempoHistory>() {
                @Override
                public void onResponse(@NonNull Call<TempoHistory> call, @NonNull Response<TempoHistory> response) {
                    tempoDates.clear();
                    if (response.code() == HttpURLConnection.HTTP_OK && response.body() != null) {
                        tempoDates.addAll(response.body().getTempoDates());
                        Log.d(LOG_TAG,"nb elements = " + tempoDates.size());
                    }
                    tempoDateAdapter2.notifyDataSetChanged();
                }

                @Override
                public void onFailure(@NonNull Call<TempoHistory> call, @NonNull Throwable t) {

                }
            });

        }

    }
}