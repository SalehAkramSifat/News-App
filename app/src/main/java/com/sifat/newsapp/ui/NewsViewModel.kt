package com.sifat.newsapp.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sifat.newsapp.models.Article
import com.sifat.newsapp.models.NewsResponse
import com.sifat.newsapp.repository.NewsRepository
import com.sifat.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import okio.IOException

class NewsViewModel(app: Application, val newsRepository: NewsRepository) : AndroidViewModel(app) {

    val headlines: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var headlinesPage = 1
    var headlinesResponse: NewsResponse? = null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null
    var newSearchQuery: String? = null
    var oldSearchQuery: String? = null

    init {
        getHeadlines("bn")
    }

    fun getHeadlines(countryCode: String) = viewModelScope.launch {
        safeHeadlinesCall(countryCode)
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        newSearchQuery = searchQuery
        safeSearchNewsCall(searchQuery)
    }

    private fun handleResponse(response: Response<NewsResponse>, isSearch: Boolean = false): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (isSearch) {
                    if (searchNewsResponse == null || newSearchQuery != oldSearchQuery) {
                        searchNewsPage = 1
                        oldSearchQuery = newSearchQuery
                        searchNewsResponse = resultResponse
                    } else {
                        searchNewsPage++
                        val oldArticles = searchNewsResponse?.articles
                        val newArticles = resultResponse.articles
                        oldArticles?.addAll(newArticles)
                    }
                    return Resource.Success(searchNewsResponse ?: resultResponse)
                } else {
                    headlinesPage++
                    if (headlinesResponse == null) {
                        headlinesResponse = resultResponse
                    } else {
                        val oldArticles = headlinesResponse?.articles
                        val newArticles = resultResponse.articles
                        oldArticles?.addAll(newArticles)
                    }
                    return Resource.Success(headlinesResponse ?: resultResponse)
                }
            }
        }
        return Resource.Error(response.message())
    }


    private suspend fun safeHeadlinesCall(countryCode: String) {
        headlines.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.getHeadlines(countryCode, headlinesPage)
                headlines.postValue(handleResponse(response))
            } else {
                headlines.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> headlines.postValue(Resource.Error("Network Failure"))
                else -> headlines.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    private suspend fun safeSearchNewsCall(searchQuery: String) {
        searchNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = newsRepository.searchNews(searchQuery, searchNewsPage)
                searchNews.postValue(handleResponse(response, isSearch = true))
            } else {
                searchNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> searchNews.postValue(Resource.Error("Network Failure"))
                else -> searchNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    fun addToFavorites(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getFavoriteNews() = newsRepository.getFavoriteNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}
