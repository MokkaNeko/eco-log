package com.mokaneko.recycle2.utils.helper

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.mokaneko.recycle2.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object CloudinaryHelper {
    private val config = HashMap<String, String>().apply {
        put("cloud_name", BuildConfig.CLOUDINARY_CLOUD_NAME)
        put("api_key", BuildConfig.CLOUDINARY_API_KEY)
        put("api_secret", BuildConfig.CLOUDINARY_API_SECRET)
    }

    private val cloudinary = Cloudinary(config)

    suspend fun uploadImage(file: File): String = withContext(Dispatchers.IO) {
        val uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap())
        uploadResult["secure_url"] as String
    }

    fun extractPublicIdFromUrl(imageUrl: String): String? {
        val regex = Regex(".*/v\\d+/([^\\.]+).*")
        return regex.find(imageUrl)?.groupValues?.get(1)
    }

    suspend fun deleteImageByUrl(imageUrl: String): Boolean = withContext(Dispatchers.IO) {
        val publicId = extractPublicIdFromUrl(imageUrl) ?: return@withContext false
        val result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap())
        return@withContext result["result"] == "ok"
    }
}