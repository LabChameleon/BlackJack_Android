package com.adt.blackjack;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTitle("Black Jack");
        setContentView(R.layout.activity_main);
    }

    public void spielBeginnen(View button) {
        final Intent intent = new Intent(this,SpielenActivity.class);
        startActivity(intent);
    }

 }
