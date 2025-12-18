package com.ifba.meuifba.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

object ImageUtils {

    // Comprimir imagem
    fun compressImage(context: Context, imageUri: Uri, maxSizeKB: Int = 500): ByteArray? {
        return try {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            var quality = 100
            val outputStream = ByteArrayOutputStream()

            do {
                outputStream.reset()
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                quality -= 10
            } while (outputStream.size() > maxSizeKB * 1024 && quality > 0)

            outputStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Redimensionar imagem
    fun resizeImage(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val ratioBitmap = width.toFloat() / height.toFloat()
        val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

        var finalWidth = maxWidth
        var finalHeight = maxHeight

        if (ratioMax > ratioBitmap) {
            finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
        } else {
            finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
        }

        return Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, true)
    }

    // Salvar imagem no storage interno
    fun saveImageToInternalStorage(context: Context, bitmap: Bitmap, fileName: String): String? {
        return try {
            val directory = File(context.filesDir, "images")
            if (!directory.exists()) {
                directory.mkdirs()
            }

            val file = File(directory, fileName)
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            outputStream.close()

            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Deletar imagem
    fun deleteImage(imagePath: String): Boolean {
        return try {
            val file = File(imagePath)
            file.delete()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Obter tamanho de arquivo de Uri
    fun getFileSize(context: Context, uri: Uri): Long {
        return try {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val sizeIndex = it.getColumnIndex(android.provider.OpenableColumns.SIZE)
                    it.getLong(sizeIndex)
                } else {
                    0L
                }
            } ?: 0L
        } catch (e: Exception) {
            0L
        }
    }

    // Obter MIME type de Uri
    fun getMimeType(context: Context, uri: Uri): String? {
        return context.contentResolver.getType(uri)
    }

    // Validar se é imagem válida
    fun isValidImage(context: Context, uri: Uri): Boolean {
        val mimeType = getMimeType(context, uri) ?: return false
        val fileSize = getFileSize(context, uri)

        return ValidationUtils.isValidImageType(mimeType) &&
                ValidationUtils.isValidFileSize(fileSize, Constants.MAX_IMAGE_SIZE_MB)
    }

    // Gerar nome único para imagem
    fun generateUniqueImageName(): String {
        return "img_${System.currentTimeMillis()}.jpg"
    }
}