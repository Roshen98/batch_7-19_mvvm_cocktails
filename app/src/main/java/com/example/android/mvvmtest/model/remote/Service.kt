package com.example.android.mvvmtest.model.remote

import com.example.android.mvvmtest.model.CocktailDetails
import com.example.android.mvvmtest.model.CocktailSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/*
1.- Retrofit dependencies.
2.- Create Retrofit Interface(Service)
3.- In  the service create the HTTP verbs
4.- Create the Retrofit (singleton)
Base URL seperated by dots
https://www.thecocktaildb.com/
resources seperated by /
api/json/v1/1/search.php
Args seperated by ?
?s=margarita
multiple arguments seperated by &z
&next

https://www.thecocktaildb.com/
api/json/v1/1/lookup.php
?i=11007
 */

interface Service {
    @GET(ENDPOINT_SEARCH)
    fun queryCocktailByName(
        @Query(ARG_SEARCH) searchInput: String
    ) : Call<CocktailSearch>

    @GET(ENDPOINT_DETAIL)
    fun queryCocktailDetails(
        @Query(ARG_DETAIL) cocktailID: String
    ) : Call<CocktailDetails>
}

