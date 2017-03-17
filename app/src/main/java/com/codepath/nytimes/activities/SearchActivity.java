package com.codepath.nytimes.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.nytimes.R;
import com.codepath.nytimes.adapters.ArticleAdapter;
import com.codepath.nytimes.decorators.RecyclerViewItemDecorator;
import com.codepath.nytimes.models.NYTArticle;
import com.codepath.nytimes.models.NYTArticleResponse;
import com.codepath.nytimes.service.NYTimesClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.etQuery)EditText etQuery;
    @BindView(R.id.btnSearch)Button btnSearch;
    @BindView(R.id.rvArticles)RecyclerView rvArticles;
    ArrayList<NYTArticle> articles;
    ArticleAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
         // Create adapter passing in the sample user data
        articles = new ArrayList<>();
        adapter = new ArticleAdapter(this, articles);
        // Attach the adapter to the recyclerview to populate items
        rvArticles.addItemDecoration(new RecyclerViewItemDecorator(4));
        rvArticles.setAdapter(adapter);
        setUpClickListener();
        // Set layout manager to position the items
// First param is number of columns and second param is orientation i.e Vertical or Horizontal
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        // Attach the layout manager to the recycler view
        rvArticles.setLayoutManager(gridLayoutManager);        // That's all!

    }

    private void setUpClickListener() {

        btnSearch.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = etQuery.getText().toString();
                String BASE_URL = "https://api.nytimes.com/";

                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .create();


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                NYTimesClient.NYTimesService NYservice = retrofit.create(NYTimesClient.NYTimesService.class);

                Call<NYTArticleResponse> call = NYservice.getArticlesFromServer();
                call.enqueue(new Callback<NYTArticleResponse>(){
                      @Override
                    public void onResponse(Call<NYTArticleResponse> call, Response<NYTArticleResponse> response) {
                          Toast.makeText(SearchActivity.this, "This is my Toast SUCCESS!",
                                  Toast.LENGTH_LONG).show();
                          NYTArticleResponse NYTAr = response.body();
                          if(NYTAr == null) {

                              Toast.makeText(SearchActivity.this, "No results matching search!",
                                      Toast.LENGTH_LONG).show();
                          }else {
                              articles.addAll(NYTAr.getResponse().getArticles());
                              adapter.notifyDataSetChanged();
                          }
                      }

                    @Override
                    public void onFailure(Call<NYTArticleResponse> call, Throwable t) {

                        Toast.makeText(SearchActivity.this, "This is my Toast FAILURE!",
                                Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
