package com.example.targetsapvtoda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    TextView tvLabel;
    // RecyclerView rvHistory;
    TextView tvTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);



    }
    public void openHistoryActivity(View view) {
        // Create an Intent to start HistoryActivity
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
}
