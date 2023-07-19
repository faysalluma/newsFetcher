package com.example.testmobile.domain.repositories

import com.example.testmobile.data.dto.ArticleDTO
import com.example.testmobile.utils.Either
import com.example.testmobile.utils.Failure
import kotlinx.coroutines.flow.StateFlow

interface ISampleRepository {
    val articles: StateFlow<Either<Failure, List<ArticleDTO>>?>
    suspend fun getAllArticles(): Either<Failure, Boolean>
}