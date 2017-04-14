package petruscarvalho.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import petruscarvalho.beerlist.R;
import petruscarvalho.model.Beer;
import petruscarvalho.util.retrofit.DownloadImageTask;
import petruscarvalho.util.retrofit.RetroClient;
import petruscarvalho.util.retrofit.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeerListActivity extends AppCompatActivity {

    BeerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_list);
        ListView lv = (ListView)findViewById(R.id.listViewCountry);
        populateBeers();

    }

    private void setAdapter(BeerAdapter adap){
        this.adapter = adap;
    }

    private void populateBeers() {

        Call<List<Beer>> beerCatalog = RetroClient.getApiService().getBeerJSON();
        final ProgressDialog progressDialog = Util.retornaProgressDialog(this);
        // show it
        progressDialog.show();
        beerCatalog.enqueue(new Callback<List<Beer>>() {
            @Override
            public void onResponse(Call<List<Beer>> call, Response<List<Beer>> response) {
                ArrayList<Beer> beersCatalog = new ArrayList<>();
                if(!response.isSuccessful()){
                    Log.i("TAG", "ERRo:" + response.code());
                }else{
                    List<Beer> catalog = response.body();
                    for(Beer beer: catalog){
                        beersCatalog.add(beer);
                    }
                }
                ListView lv = (ListView)findViewById(R.id.listViewCountry);
                BeerAdapter adapter = new BeerAdapter(BeerListActivity.this, beersCatalog);
                lv.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Beer>> call, Throwable t) {
                Log.i("ERROR:",t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
    private class BeerAdapter extends BaseAdapter implements Filterable {

        private final Activity activity;
        ArrayList<Beer> beers = new ArrayList<>();
        ArrayList<Beer> beersTotal = new ArrayList<>();
        public BeerAdapter(Activity activity, ArrayList<Beer> beers) {
            this.activity = activity;
            this.beers = beers;
            this.beersTotal = beers;
            setAdapter(this);
            adapter.notifyDataSetChanged();
            if (beers.size() == 0) {
                activity.finish();
            }
        }

       @Override
        public int getCount() {
            if (beers.size() == 0) {
                activity.finish();
            }
            return beers.size();
        }

        @Override
        public Object getItem(int position) {
            return beers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return beers.get(position).hashCode();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final Beer beer = beers.get(position);
            BeerItemViewHolder holder;

            if (convertView == null){
                convertView = View.inflate(BeerListActivity.this,
                        R.layout.item_beer, null);
                holder = new BeerItemViewHolder();
                holder.beerName = (TextView) convertView.findViewById(R.id.beerName);
                holder.beerTag = (TextView) convertView.findViewById(R.id.beerTag);
                holder.beerImage = (ImageView) convertView.findViewById(R.id.beerImage);
                convertView.setTag(holder);

            }else{
                holder = (BeerItemViewHolder) convertView.getTag();
            }

            holder.linha = convertView.findViewById(R.id.item);
            holder.beerName.setText(beer.getBeerName());
            holder.beerTag.setText(beer.getBeerTag());
            new DownloadImageTask(holder.beerImage).execute(beer.getBeerImage());

            holder.linha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(BeerListActivity.this, BeerIndividualActivity.class);
                    myIntent.putExtra("beerName", beer.getBeerName());
                    myIntent.putExtra("beerDesc", beer.getBeerDesc());
                    myIntent.putExtra("beerTag", beer.getBeerTag());
                    myIntent.putExtra("beerImage", beer.getBeerImage());
                    BeerListActivity.this.startActivity(myIntent);
                }
            });

            return convertView;
        }

        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                ArrayList<Beer> tempList = new ArrayList<>();
                //constraint is the result from text you want to filter against.
                //objects is your data set you will filter from
                if (constraint == null || constraint.length() == 0) {
                    // set the Original result to return
                    filterResults.values = beersTotal;
                    filterResults.count = beersTotal.size();
                }else {
                    int length = beers.size();
                    int i = 0;
                    while(i < length){
                        Beer item = beers.get(i);
                        //adding result set output array
                        //do whatever you wanna do here
                        if(item.getBeerName().contains(constraint)){
                            tempList.add(item);
                        }

                        i++;
                    }
                    //following two lines is very important
                    //as publish result can only take FilterResults objects
                    filterResults.values = tempList;
                    filterResults.count = tempList.size();
                }
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence contraint, FilterResults results) {
                beers = (ArrayList<Beer>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        @Override
        public Filter getFilter() {
            return myFilter;
        }

        class BeerItemViewHolder {

            private View linha;
            private ImageView beerImage;
            private TextView beerName;
            private TextView beerTag;

        }
    }
}
