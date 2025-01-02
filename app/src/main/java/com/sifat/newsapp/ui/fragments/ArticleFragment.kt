package com.sifat.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.sifat.newsapp.R
import com.sifat.newsapp.models.Article

class ArticleFragment : Fragment(R.layout.fragment_article) {
    private val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = args.article

        Log.d("ArticleFragment", "Received article: ${article.title}")

        val titleTextView: TextView = view.findViewById(R.id.articleTitle)
        val descriptionTextView: TextView = view.findViewById(R.id.articleDescription)

        titleTextView.text = article.title
        descriptionTextView.text = article.description
    }
}
