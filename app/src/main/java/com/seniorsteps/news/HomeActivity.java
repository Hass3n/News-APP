package com.seniorsteps.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.seniorsteps.news.API.ApiManager;
import com.seniorsteps.news.API.Responses.ArticlesResponse.Article;
import com.seniorsteps.news.API.Responses.ArticlesResponse.ArticlesResponse;
import com.seniorsteps.news.API.Responses.SourcesResponse.Source;
import com.seniorsteps.news.API.Responses.SourcesResponse.SourcesResponse;
import com.seniorsteps.news.Adapters.ArticlesRecyclerAdapter;
import com.seniorsteps.news.Base.MyBaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends MyBaseActivity {

    TabLayout NewsSources;
    RecyclerView News;
    Map<String,List<Source>> Categories;
    public static final String APIKEY="17c3229d4c5242758b2a4a079a38c177";
    ArticlesRecyclerAdapter adapter;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NewsSources = findViewById(R.id.news_sources);
        News = findViewById(R.id.news);
        LoadCategories();

       }


    public void LoadCategories(){
        ShowProgressBar();
        ApiManager.getAPIs().getNewsSources("en",APIKEY)
                .enqueue(new Callback<SourcesResponse>() {
                    @Override
                    public void onResponse(Call<SourcesResponse> call,
                                           Response<SourcesResponse> response) {
                        HideProgressBar();
                        if(response.code()==200){
                            fillTabLayout(response.body().getSources());
                        }

                    }

                    @Override
                    public void onFailure(Call<SourcesResponse> call, Throwable t) {
                        HideProgressBar();
                        ShowMessage("error",t.getLocalizedMessage());
                    }
                });

    }

    public void FilterSourcesByCategory(List<Source>sourcesList){
        Categories = new HashMap<>();

        for(Source source:sourcesList){
            if(Categories.get(source.getCategory())==null){
                ArrayList<Source> arrayList = new ArrayList();
                arrayList.add(source);
                Categories.put(source.getCategory(),arrayList);
            }else {
                Categories.get(source.getCategory())
                        .add(source);
            }
        }
    }

    public void fillTabLayout(List<Source> sourcesList){

      /*  for(int i=0;i<sourcesList.size();i++){
            Source s = sourcesList.get(i);
        }*/
      FilterSourcesByCategory(sourcesList);

        for (Map.Entry<String,List<Source>> entry:Categories.entrySet()){
            NewsSources.addTab
                    (NewsSources.newTab().setText(entry.getKey()));
        }
        NewsSources.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String Category = tab.getText().toString();

                List<Source> sources=Categories.get(Category);
                String CommaSeparatedSources= getCommaSeperatedSources(sources);
                getArticles(CommaSeparatedSources);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                String Category = tab.getText().toString();

                List<Source> sources=Categories.get(Category);
                String CommaSeparatedSources= getCommaSeperatedSources(sources);
                getArticles(CommaSeparatedSources);

            }
        });
        NewsSources.getTabAt(0).select();

    }


    void getArticles(String sources){
        ShowProgressBar();
        ApiManager.getAPIs().getArticles("en",APIKEY,sources)
        .enqueue(new Callback<ArticlesResponse>() {
            @Override
            public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {
                HideProgressBar();
                if(response.code()==200){
                    setRecyclerView(response.body().getArticles());
                }

            }

            @Override
            public void onFailure(Call<ArticlesResponse> call, Throwable t) {
                HideProgressBar();
                ShowMessage("error",t.getLocalizedMessage());
            }
        });
    }

    public void setRecyclerView(List<Article> articles){

        adapter= new ArticlesRecyclerAdapter(articles,activity);
        layoutManager = new LinearLayoutManager(activity);
        News.setAdapter(adapter);
        News.setLayoutManager(layoutManager);


    }

    String getCommaSeperatedSources(List<Source> sources){
        String commaSeparatedSources="";
        if(sources!=null){
            for(Source s : sources){
                commaSeparatedSources=
                        commaSeparatedSources + s.getId()+",";
            }


        }
        return commaSeparatedSources;
    }
}
