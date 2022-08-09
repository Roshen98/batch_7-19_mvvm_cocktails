package com.example.android.mvvmtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.mvvmtest.model.CocktailDetails
import com.example.android.mvvmtest.model.CocktailSearch
import com.example.android.mvvmtest.model.remote.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CocktailViewModel: ViewModel() {

    private val _cocktailSearchResult = MutableLiveData<CocktailSearch>()

    // Backing Field
    val cocktailSearchResult: LiveData<CocktailSearch>
     get() = _cocktailSearchResult


    private val _errorMessages = MutableLiveData("")
    val errorMessages: LiveData<String>
     get() = _errorMessages


    private val _cocktailDetails = MutableLiveData<CocktailDetails>()

    // Backing Field
    val cocktailDetails: LiveData<CocktailDetails>
    get() = _cocktailDetails


    fun searchCocktail(cocktailName: String){
        API.cocktailApi.queryCocktailByName(cocktailName)
            .enqueue(
                object: Callback<CocktailSearch> {
                    override fun onResponse(
                        call: Call<CocktailSearch>,
                        response: Response<CocktailSearch>
                    ) {
                        if(response.isSuccessful){
                            response.body()?.let {
                                _cocktailSearchResult.value = it
                            } ?: kotlin.run { _errorMessages.value = response.message()
                            }
                        }
                        else{
                            _errorMessages.value = response.message()
                        }
                    }

                    override fun onFailure(call: Call<CocktailSearch>, t: Throwable) {
                        t.printStackTrace()
                        _errorMessages.value = t.message ?: "Unknown error"
                    }
                }
            )
    }



    fun detailsCocktail(cocktailID: String){
        API.cocktailApi.queryCocktailDetails(cocktailID)
            .enqueue(
                object: Callback<CocktailDetails>{
                    override fun onResponse(
                        call: Call<CocktailDetails>,
                        response: Response<CocktailDetails>
                    ) {
                        if(response.isSuccessful){
                            response.body()?.let {
                                _cocktailDetails.value = it
                            } ?: kotlin.run { _errorMessages.value = response.message()
                            }
                        }
                        else{
                            _errorMessages.value = response.message()
                        }
                    }

                    override fun onFailure(call: Call<CocktailDetails>, t: Throwable) {
                        t.printStackTrace()
                        _errorMessages.value = t.message ?: "Unknown error"
                    }
                }
            )
    }
}