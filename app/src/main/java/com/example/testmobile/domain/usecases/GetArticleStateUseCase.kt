package com.example.testmobile.domain.usecases

import com.example.testmobile.data.dto.ArticleDTO
import com.example.testmobile.domain.repositories.ISampleRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetArticleStateUseCase @Inject constructor(private val sampleRepository: ISampleRepository) : StateUseCase<List<ArticleDTO>>() {
    override fun provideState() = sampleRepository.articles
}