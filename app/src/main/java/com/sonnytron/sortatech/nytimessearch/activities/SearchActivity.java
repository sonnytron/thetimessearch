package com.sonnytron.sortatech.nytimessearch.activities;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sonnytron.sortatech.nytimessearch.ArticleArrayAdapter;
import com.sonnytron.sortatech.nytimessearch.ArticleHolder;
import com.sonnytron.sortatech.nytimessearch.R;
import com.sonnytron.sortatech.nytimessearch.models.Article;
import com.sonnytron.sortatech.nytimessearch.models.SearchFilters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity implements ArticleHolder.articleCallback, FiltersActivity.SearchCallback {
    private static final String apiKey = "3de2a7dcd6f04f60b0982f3f85f19145";
    private static final String baseUrl = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
    private final int FILTERS_CODE = 20;
    EditText bookQuery;
    Button buttonSearch;
    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;
    SearchFilters mFilters;

    private RecyclerView mArticleRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViews();
    }

    @Override
    public void searchFiltersApplied(SearchFilters filters) {
        mFilters = filters;
    }

    public void setupViews() {
        bookQuery = (EditText) findViewById(R.id.btnQuery);
        buttonSearch = (Button) findViewById(R.id.btnSearch);
        articles = new ArrayList<>();

        adapter = new ArticleArrayAdapter(this, articles);

        mArticleRecyclerView = (RecyclerView) findViewById(R.id.article_grid_recycler);
        mArticleRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        mArticleRecyclerView.setAdapter(adapter);
//        bookGridView.setAdapter(adapter);
//        adapter = new ArticleArrayAdapter(this, articles);
//        bookGridView.setAdapter(adapter);
//        bookGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
//                // get the article to display
//                Article article = articles.get(position);
//                i.putExtra("article", article);
//                startActivity(i);
//            }
//        });
    }

    @Override
    public void onArticleSelected(Article article) {
        Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
        i.putExtra("article", article);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search_field);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 0) {
                    AsyncHttpClient client = new AsyncHttpClient();

                    RequestParams params = new RequestParams();
                    params.put("api-key", apiKey);
                    params.put("page", 0);
                    params.put("q", query);

                    if (mFilters != null) {
                        if (mFilters.getStartDate() != null) {
                            params.put("begin_date", mFilters.getStartDate());
                        }
                        if (mFilters.getSortString().length() > 0) {
                            params.put("sort", mFilters.getSortString());
                        }
                        if (mFilters.getNewsDeskString().length() > 0) {
                            params.put("fq", mFilters.getNewsDeskString());
                        }
                    }

                    client.get(baseUrl, params, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            JSONArray articleResults = null;
                            try {
                                articleResults = response.getJSONObject("response").getJSONArray("docs");
                                adapter.setArticles(Article.fromJSONArray(articleResults));
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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
            Intent intent = new Intent(this, FiltersActivity.class);
            intent.putExtra("mode", 2);
            startActivityForResult(intent, FILTERS_CODE);
            return true;
        }

        if (id == R.id.search_field) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onArticleSearch(View view) {
        String query = bookQuery.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("api-key", apiKey);
        params.put("page", 0);
        params.put("q", query);

        client.get(baseUrl, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());

                JSONArray articleResults = null;
                try {
                    articleResults = response.getJSONObject("response").getJSONArray("docs");
                    adapter.setArticles(Article.fromJSONArray(articleResults));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == FILTERS_CODE) {
            SearchFilters filters = data.getParcelableExtra("filters");
            if (filters != null) {
                mFilters = filters;
            }
        }
    }
}
