package com.example.testmobile.data.repositories

import android.util.Log
import com.example.testmobile.data.Constants
import com.example.testmobile.data.dto.ArticleDTO
import com.example.testmobile.data.network.SampleApiInterface
import com.example.testmobile.data.network.adapters.asDto
import com.example.testmobile.data.network.bodies.results.Article
import com.example.testmobile.domain.repositories.ISampleRepository
import com.example.testmobile.utils.Either
import com.example.testmobile.utils.Failure
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SampleRepository @Inject constructor(private val api: SampleApiInterface) : ISampleRepository {
    companion object {
        private val TAG = SampleRepository::class.simpleName
    }

    /* State Flow's */
    private val _articles = MutableStateFlow<Either<Failure, List<ArticleDTO>>?>(null)
    override val articles: StateFlow<Either<Failure, List<ArticleDTO>>?> = _articles

    /* Functions */
    override suspend fun getAllArticles(): Either<Failure, Boolean> {
        // Search Online
        try {
            val country = Locale.getDefault().country.lowercase()
            val response = api.getAllArticles("us", Constants.API_KEY_VAL).execute()
            if (response.isSuccessful) {
                val articlesDTO = (response.body()?.articles as List<Article>).map {
                    it.asDto()
                }
                _articles.emit(Either.Right(articlesDTO))
                return Either.Right(true)
            } else {
                Log.w(TAG, "server error")
                val error = Failure.NetworkFailure("Server error : HTTP CODE ERROR ${response.code()}")
                _articles.emit(Either.Left(error))
                return Either.Left(error)
            }
        } catch (e: IOException) {
            Log.w(TAG, "an exception occurred: ${e.stackTraceToString()}")
            val error = Failure.NetworkFailure(e.message ?: "")
            _articles.emit(Either.Left(error))
            return Either.Left(error)
        }
    }
}