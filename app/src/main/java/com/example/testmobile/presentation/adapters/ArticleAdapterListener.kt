package com.example.testmobile.presentation.adapters

import com.example.testmobile.data.dto.ArticleDTO

interface ArticleAdapterListener {
    fun onArticleSelected(articleDTO: ArticleDTO)
}