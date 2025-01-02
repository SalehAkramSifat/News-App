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
    // Safe Args থেকে article আনার জন্য
    private val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Safe Args থেকে article গ্রহণ
        val article = args.article

        // কনসোল লোগিং - নিশ্চিত করতে যে article এসেছে
        Log.d("ArticleFragment", "Received article: ${article.title}")

        // Article এর তথ্য UI তে দেখানো
        val titleTextView: TextView = view.findViewById(R.id.articleTitle)
        val descriptionTextView: TextView = view.findViewById(R.id.articleDescription)

        titleTextView.text = article.title // উদাহরণস্বরূপ
        descriptionTextView.text = article.description // উদাহরণস্বরূপ
    }
}
