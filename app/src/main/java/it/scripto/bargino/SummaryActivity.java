package it.scripto.bargino;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import it.scripto.models.Coffee;
import it.scripto.util.BaseActivity;


public class SummaryActivity extends BaseActivity {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_summary;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get Toolbar and set as ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Set home back/home button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get coffee
        Intent intent = getIntent();
        Coffee coffee = intent.getParcelableExtra(MainActivity.EXTRA_COFFEE);

        // Set summary
        TextView textTextView = (TextView) findViewById(R.id.text);
        textTextView.setText(coffee.summarize());

        // Set total
        TextView totalTextView = (TextView) findViewById(R.id.total);
        totalTextView.setText(coffee.getStringTotal());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // TODO: reshow menu
        //getMenuInflater().inflate(R.menu.menu_summary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
