package petruscarvalho.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import petruscarvalho.beerlist.R;
import petruscarvalho.util.retrofit.DownloadImageTask;

/**
 * Created by petruscarvalho on 14/04/17.
 */

public class BeerIndividualActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_individual);
        String beerName = getIntent().getStringExtra("beerName");
        String beerDesc = getIntent().getStringExtra("beerDesc");
        String beerTag = getIntent().getStringExtra("beerTag");
        String beerImage = getIntent().getStringExtra("beerImage");

        TextView name = (TextView) findViewById(R.id.beerName);
        name.setText(beerName);

        TextView tag = (TextView) findViewById(R.id.beerTag);
        tag.setText(beerTag);

        TextView desc = (TextView) findViewById(R.id.beerDescription);
        desc.setText(beerDesc);

        ImageView image = (ImageView) findViewById(R.id.beerImage2);
        new DownloadImageTask(image).execute(beerImage);

    }
}
