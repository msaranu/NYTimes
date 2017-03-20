package com.codepath.nytimes.activities;

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
import android.widget.Toast;

import com.codepath.nytimes.R;
import com.codepath.nytimes.adapters.ArticleComplexAdapter;
import com.codepath.nytimes.decorators.EndlessRecyclerViewScrollListener;
import com.codepath.nytimes.decorators.SpacesItemDecoration;
import com.codepath.nytimes.fragments.FilterDialogFragment;
import com.codepath.nytimes.models.FilterSettings;
import com.codepath.nytimes.models.NYTArticle;
import com.codepath.nytimes.models.NYTArticleResponse;
import com.codepath.nytimes.network.NetworkUtil;
import com.codepath.nytimes.properties.Properties;
import com.codepath.nytimes.service.NYTimesClient;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.codepath.nytimes.properties.Properties.API_CALL_FAILED;
import static com.codepath.nytimes.properties.Properties.FIRST_PAGE;
import static com.codepath.nytimes.properties.Properties.FRAGMENT_MODAL_OVERLAY;
import static com.codepath.nytimes.properties.Properties.GRID_SPACEOUT;
import static com.codepath.nytimes.properties.Properties.NETWORK_FAILURE;
import static com.codepath.nytimes.properties.Properties.SETTINGS_OBJ_KEY;
import static com.codepath.nytimes.properties.Properties.SGRID_NO_OF_COLUMNS;


public class SearchActivity extends AppCompatActivity implements FilterDialogFragment.FilterDialogListener{

    @BindView(R.id.rvArticles)RecyclerView rvArticles;
    ArrayList<NYTArticle> articles;
    ArticleComplexAdapter adapter;
    FilterSettings fragFilterSettings;
    private EndlessRecyclerViewScrollListener scrollListener;
    String searchQuery;
    NYTimesClient NYTClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar tbSearch = (Toolbar) findViewById(R.id.tbSearch);
        setSupportActionBar(tbSearch);
        ButterKnife.bind(this);
        bindDataToAdapter(this);
        setRecyleViewLayout();

    }


    private void setRecyleViewLayout() {
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(SGRID_NO_OF_COLUMNS, StaggeredGridLayoutManager.VERTICAL);
        // Attach the layout manager to the recycler view
        rvArticles.setLayoutManager(gridLayoutManager);        // That's all!
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                      getArticles(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvArticles.addOnScrollListener(scrollListener);
        SpacesItemDecoration spacesitemdecor = new SpacesItemDecoration(GRID_SPACEOUT);
        rvArticles.addItemDecoration(spacesitemdecor);
    }

    private void bindDataToAdapter(SearchActivity searchActivity) {
        fragFilterSettings = new FilterSettings();
        // Create adapter passing in the sample user data
        articles = new ArrayList<>();
        adapter = new ArticleComplexAdapter(this, articles);
        // Attach the adapter to the recyclerview to populate items
        rvArticles.setAdapter(adapter);
    }

    public void getArticles(int page) {
        {
            if (page == FIRST_PAGE) {
                articles.clear();
                scrollListener.resetState();
            }
            callNYTAPI(page);

        }
    }

    private void callNYTAPI(int page) {

        if (NetworkUtil.isInternetAvailable(getApplicationContext()) && NetworkUtil.isOnline()) {

            NYTClient = NYTimesClient.getNewInstance();
            Call<NYTArticleResponse> call = NYTClient.NYTimesClientFactory().
                    getArticlesFromServer(getApplicationContext().getString(R.string.nyt_api_key), new Integer(page), searchQuery, fragFilterSettings.beginDate,
                            fragFilterSettings.sortOrder, fragFilterSettings.getNewsDeskQuery());

            call.enqueue(new Callback<NYTArticleResponse>() {
                @Override
                public void onResponse(Call<NYTArticleResponse> call, Response<NYTArticleResponse> response) {
                    NYTArticleResponse NYTAr = response.body();
                    if (NYTAr == null || NYTAr.getResponse().getArticles().isEmpty()) {
                        Toast.makeText(SearchActivity.this, Properties.NO_MATCH,
                                Toast.LENGTH_LONG).show();
                    } else {
                        articles.addAll(NYTAr.getResponse().getArticles());
                        adapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<NYTArticleResponse> call, Throwable t) {
                    Toast.makeText(SearchActivity.this, API_CALL_FAILED,
                            Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Toast.makeText(SearchActivity.this, NETWORK_FAILURE,
                    Toast.LENGTH_LONG).show();
            return;
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            onFilterSettings();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onFilterSettings(){
        FragmentManager fm = getSupportFragmentManager();
        FilterDialogFragment fdf = new FilterDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SETTINGS_OBJ_KEY, fragFilterSettings);
        fdf.setArguments(bundle);
        fdf.show(fm, FRAGMENT_MODAL_OVERLAY);
    }

    @Override
    public void onFinishFilterDialog(FilterSettings fs) {
        fragFilterSettings = fs;
        getArticles(FIRST_PAGE);
    }


}
