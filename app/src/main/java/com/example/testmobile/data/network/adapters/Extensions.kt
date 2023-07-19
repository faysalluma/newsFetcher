package com.example.testmobile.data.network.adapters

import com.example.testmobile.data.dto.ArticleDTO
import com.example.testmobile.data.network.bodies.results.Article

fun Article.asDto() = ArticleDTO(title, description, url, urlToImage, publishedAt)
