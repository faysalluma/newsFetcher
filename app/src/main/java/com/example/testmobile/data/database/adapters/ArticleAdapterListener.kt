package com.example.testmobile.data.database.adapters

import com.example.testmobile.data.dto.ArticleDTO

interface ArticleAdapterListener {
    fun onArticleSelected(articleDTO: ArticleDTO)
}