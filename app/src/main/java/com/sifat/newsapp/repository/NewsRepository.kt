package com.sifat.newsapp.repository

import com.sifat.newsapp.api.RetrofitInstance
import com.sifat.newsapp.db.ArticleDatabase
import com.sifat.newsapp.models.Article

class NewsRepository(val db:ArticleDatabase) {
    suspend fun getHeadlines(countryCode: String, pageNumber:Int) =
        RetrofitInstance.api.getHeadline(countryCode, pageNumber)

    suspend fun searchNews(searchQuery:String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDAO().upsert(article)

    fun getFavoriteNews() = db.getArticleDAO().getArticle()

    suspend fun deleteArticle (article: Article) = db.getArticleDAO().deleteArticle(article)
}