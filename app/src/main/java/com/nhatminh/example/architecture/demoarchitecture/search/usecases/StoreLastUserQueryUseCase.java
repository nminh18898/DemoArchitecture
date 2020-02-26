package com.nhatminh.example.architecture.demoarchitecture.search.usecases;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;

public class StoreLastUserQueryUseCase {

    public static final String LAST_QUERY = "LastQuery";

    Context context;

    @Inject
    public StoreLastUserQueryUseCase(Context context) {
        this.context = context;
    }

    public void saveLastQuery(String query){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LAST_QUERY, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_QUERY, query);
        editor.apply();
    }

    public String retrieveLastQuery(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LAST_QUERY, MODE_PRIVATE);
        String lastQuery = sharedPreferences.getString(LAST_QUERY, "");
        return lastQuery;
    }
}
