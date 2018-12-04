package com.umutbey.stateviews.Samples;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.umutbey.stateviews.StateView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    StateView mStatusPage;
    Button mLoading, mError, mCustom, mSearch, mHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoading = findViewById(R.id.button_loading);
        mError = findViewById(R.id.button_error);
        mCustom = findViewById(R.id.button_custom);
        mSearch = findViewById(R.id.button_search);
        mHide = findViewById(R.id.button_hide);

        mLoading.setOnClickListener(this);
        mError.setOnClickListener(this);
        mCustom.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mHide.setOnClickListener(this);

        mStatusPage = findViewById(R.id.status_page);
        mStatusPage.setOnStateButtonClicked(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getTag().toString()){
                    case "custom":
                        mStatusPage.hideStates();
                        Toast.makeText(MainActivity.this, "Is State Showing: "+mStatusPage.isStateShowing(), Toast.LENGTH_SHORT).show();
                        break;
                    default: Toast.makeText(MainActivity.this, "Button Clicked " + mStatusPage.getShowingState(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_loading:
                mStatusPage.displayLoadingState();
                break;
            case R.id.button_error:
                mStatusPage.displayState("error");
                break;
            case R.id.button_custom:
                mStatusPage.displayState("custom");
                break;
            case R.id.button_search: {
                mStatusPage.displayState("search");
                break;
            }
            case R.id.button_hide: {
                mStatusPage.displayState("hide");
                break;
            }
        }
    }
}
