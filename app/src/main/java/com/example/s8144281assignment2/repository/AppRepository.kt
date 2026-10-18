package com.example.s8144281assignment2.repository

import com.example.s8144281assignment2.api.ApiInterface
import com.example.s8144281assignment2.api.Entity
import com.example.s8144281assignment2.api.LoginRequest
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.cancellation.CancellationException

@Singleton
class AppRepository @Inject constructor(
    private val api: ApiInterface
) {

    // Returns the keypass if the login worked, or an error message if not
    suspend fun login(username: String, password: String): Result<String> =
        try {
            val response = api.login(LoginRequest(username, password))
            val keypass = response.body()?.keypass
            if (response.isSuccessful && keypass != null) {
                Result.success(keypass)
            } else {
                Result.failure(Exception("Login failed (code ${response.code()})"))
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(Exception("Network error: ${e.localizedMessage}"))
        }

    // Returns the list of artworks, or an error message
    suspend fun getEntities(keypass: String): Result<List<Entity>> =
        try {
            val response = api.getDashboard(keypass)
            val entities = response.body()?.entities
            if (response.isSuccessful && entities != null) {
                Result.success(entities)
            } else {
                Result.failure(Exception("Failed to load data (code ${response.code()})"))
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(Exception("Network error: ${e.localizedMessage}"))
        }
}