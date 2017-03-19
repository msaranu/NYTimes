package com.codepath.nytimes.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.nytimes.R;
import com.codepath.nytimes.adapters.ArticleAdapter;
import com.codepath.nytimes.decorators.SpacesItemDecoration;
import com.codepath.nytimes.fragments.FilterDialogFragment;
import com.codepath.nytimes.listeners.EndlessRecyclerViewScrollListener;
import com.codepath.nytimes.models.FilterSettings;
import com.codepath.nytimes.models.NYTArticle;
import com.codepath.nytimes.models.NYTArticleResponse;
import com.codepath.nytimes.network.NetworkUtil;
import com.codepath.nytimes.service.NYTimesClient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements FilterDialogFragment.FilterDialogListener{

    @BindView(R.id.etQuery)EditText etQuery;
    @BindView(R.id.btnSearch)Button btnSearch;
    @BindView(R.id.rvArticles)RecyclerView rvArticles;
    ArrayList<NYTArticle> articles;
    ArticleAdapter adapter;
    int FIRST_PAGE =0;
    FilterSettings fragFilterSettings;
    private EndlessRecyclerViewScrollListener scrollListener;
    String searchQuery;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        fragFilterSettings = new FilterSettings();
         // Create adapter passing in the sample user data
        articles = new ArrayList<>();
        adapter = new ArticleAdapter(this, articles);
        // Attach the adapter to the recyclerview to populate items
        rvArticles.setAdapter(adapter);
        setUpClickListener();
        // Set layout manager to position the items
// First param is number of columns and second param is orientation i.e Vertical or Horizontal
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        // Attach the layout manager to the recycler view
        rvArticles.setLayoutManager(gridLayoutManager);        // That's all!
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                //pageNumber = page;
                getArticles(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvArticles.addOnScrollListener(scrollListener);
        SpacesItemDecoration spacesitemdecor = new SpacesItemDecoration(10);
        rvArticles.addItemDecoration(spacesitemdecor);


    }

    private void setUpClickListener() {

        btnSearch.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                getArticles(FIRST_PAGE);
            }
        });
    }

    public void getArticles(int page) {
        {
           // String query = etQuery.getText().toString();
          //  String BASE_URL = "https://api.nytimes.com/";
            if (page == FIRST_PAGE) {
                articles.clear();
                scrollListener.resetState();
            }

            NYTimesClient NYTClient = NYTimesClient.getNewInstance();

            if (NetworkUtil.isInternetAvailable(getApplicationContext()) && NetworkUtil.isOnline()) {

                Call<NYTArticleResponse> call = NYTClient.NYTimesClientFactory().
                        getArticlesFromServer("a5ac3eb802f44561b5fa0f398b07f65f", new Integer(page), searchQuery, fragFilterSettings.beginDate,
                                fragFilterSettings.sortOrder, fragFilterSettings.getNewsDeskQuery());

               //  progressDialog = new ProgressDialog(getApplicationContext());
                //progressDialog.setMessage("Fetching The File....");
               // progressDialog.show();

                call.enqueue(new Callback<NYTArticleResponse>() {
                    @Override
                    public void onResponse(Call<NYTArticleResponse> call, Response<NYTArticleResponse> response) {
                        NYTArticleResponse NYTAr = response.body();
                        if (NYTAr == null) {

                            Toast.makeText(SearchActivity.this, "No articles matching search!",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            articles.addAll(NYTAr.getResponse().getArticles());
                            adapter.notifyDataSetChanged();
                        }
//                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<NYTArticleResponse> call, Throwable t) {

                        Toast.makeText(SearchActivity.this, "Request to API failed!",
                                Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                Toast.makeText(SearchActivity.this, "Internet Connection is unavailable. Try again later",
                        Toast.LENGTH_LONG).show();
                return;
            }
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                searchQuery = query;
                getArticles(FIRST_PAGE);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            View v = findViewById(R.id.action_settings);
            onFilterSettings(v);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onFilterSettings(View v){
        FragmentManager fm = getSupportFragmentManager();
        FilterDialogFragment fdf = new FilterDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("SettingObj", fragFilterSettings);
        fdf.setArguments(bundle);
        fdf.show(fm, "FRAGMENT FILTER");
    }

    @Override
    public void onFinishFilterDialog(FilterSettings fs) {
        fragFilterSettings = fs;
        getArticles(FIRST_PAGE);
    }


}
