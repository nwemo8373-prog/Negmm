package com.negm.app.supabase

import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

object SupabaseManager {

    // بيانات Supabase الخاصة بمشروعك
    private const val SUPABASE_URL = "https://lgwtpqcvfefgdnbwlbys.supabase.co"

    // مفتاح Anon / Publishable الخاص بالتطبيق
    private const val SUPABASE_ANON_KEY = "sb_publishable_XNeRaTGje_0mo_1G8lbtmw_EY-JbLoP"

    // أسماء Buckets داخل Supabase Storage
    const val PROFILE_BUCKET = "profiles"
    const val STORIES_BUCKET = "stories"
    const val CHAT_BUCKET = "chat-media"
    const val MEMORIES_BUCKET = "memories"

    private fun uploadUrl(bucket: String, fileName: String): String {
        return "$SUPABASE_URL/storage/v1/object/$bucket/$fileName"
    }

    fun publicUrl(bucket: String, fileName: String): String {
        return "$SUPABASE_URL/storage/v1/object/public/$bucket/$fileName"
    }

    suspend fun uploadFile(
        bucket: String,
        uri: Uri,
        contentResolver: android.content.ContentResolver,
        mimeType: String?
    ): Result<String> = withContext(Dispatchers.IO) {

        try {
            val inputStream = contentResolver.openInputStream(uri)
                ?: return@withContext Result.failure(
                    Exception("تعذر قراءة الملف")
                )

            val bytes = inputStream.use { it.readBytes() }

            val extension = when {
                mimeType?.contains("jpeg") == true -> "jpg"
                mimeType?.contains("png") == true -> "png"
                mimeType?.contains("webp") == true -> "webp"
                mimeType?.contains("mp4") == true -> "mp4"
                mimeType?.contains("video") == true -> "mp4"
                else -> "bin"
            }

            val fileName = "${UUID.randomUUID()}.$extension"

            val connection = URL(
                uploadUrl(bucket, fileName)
            ).openConnection() as HttpURLConnection

            connection.requestMethod = "POST"
            connection.doOutput = true

            connection.setRequestProperty(
                "Authorization",
                "Bearer $SUPABASE_ANON_KEY"
            )

            connection.setRequestProperty(
                "apikey",
                SUPABASE_ANON_KEY
            )

            connection.setRequestProperty(
                "Content-Type",
                mimeType ?: "application/octet-stream"
            )

            connection.setRequestProperty(
                "x-upsert",
                "false"
            )

            connection.outputStream.use { output ->
                output.write(bytes)
            }

            val responseCode = connection.responseCode

            if (responseCode in 200..299) {
                Result.success(
                    publicUrl(bucket, fileName)
                )
            } else {
                Result.failure(
                    Exception(
                        "Supabase upload failed: HTTP $responseCode"
                    )
                )
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun uploadProfileImage(
        uri: Uri,
        contentResolver: android.content.ContentResolver,
        mimeType: String?
    ): Result<String> {
        return uploadFile(
            bucket = PROFILE_BUCKET,
            uri = uri,
            contentResolver = contentResolver,
            mimeType = mimeType
        )
    }

    suspend fun uploadStory(
        uri: Uri,
        contentResolver: android.content.ContentResolver,
        mimeType: String?
    ): Result<String> {
        return uploadFile(
            bucket = STORIES_BUCKET,
            uri = uri,
            contentResolver = contentResolver,
            mimeType = mimeType
        )
    }

    suspend fun uploadChatMedia(
        uri: Uri,
        contentResolver: android.content.ContentResolver,
        mimeType: String?
    ): Result<String> {
        return uploadFile(
            bucket = CHAT_BUCKET,
            uri = uri,
            contentResolver = contentResolver,
            mimeType = mimeType
        )
    }

    suspend fun uploadMemory(
        uri: Uri,
        contentResolver: android.content.ContentResolver,
        mimeType: String?
    ): Result<String> {
        return uploadFile(
            bucket = MEMORIES_BUCKET,
            uri = uri,
            contentResolver = contentResolver,
            mimeType = mimeType
        )
    }

    suspend fun deleteFile(
        bucket: String,
        fileName: String
    ): Result<Boolean> = withContext(Dispatchers.IO) {

        try {
            val connection = URL(
                "$SUPABASE_URL/storage/v1/object/$bucket/$fileName"
            ).openConnection() as HttpURLConnection

            connection.requestMethod = "DELETE"

            connection.setRequestProperty(
                "Authorization",
                "Bearer $SUPABASE_ANON_KEY"
            )

            connection.setRequestProperty(
                "apikey",
                SUPABASE_ANON_KEY
            )

            val responseCode = connection.responseCode

            if (responseCode in 200..299) {
                Result.success(true)
            } else {
                Result.failure(
                    Exception(
                        "Supabase delete failed: HTTP $responseCode"
                    )
                )
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
