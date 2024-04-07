package com.sksinha2410.exploreease.Chatbot.data
import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.ResponseStoppedException
import com.google.ai.client.generativeai.type.content
import com.sksinha2410.exploreease.Chatbot.data.model.ChatModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ChatData {
    val api_key = "AIzaSyClHp7SmVPrLLqgUCBcXSDzMJdOZufhAB0"

    suspend fun getResponse(prompt: String) : ChatModel {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro", apiKey = api_key
        )
        return try {
            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(prompt)
            }
            ChatModel(
                prompt = response.text?:"error",
                bitmap = null,
                isFromUser = false
            )
        } catch (e: ResponseStoppedException) {
            ChatModel("Error", null, false)
        }
    }

    suspend fun getResponseWithImage(prompt: String, bitmap: Bitmap) : ChatModel {
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro-vision", apiKey = api_key
        )
        try {
            val inputContent = content {
                image(bitmap)
                text(prompt)
            }
            val response = withContext(Dispatchers.IO) {
                generativeModel.generateContent(inputContent)
            }
            return ChatModel(
                prompt = response.text?:"error",
                bitmap = null,
                isFromUser = false
            )
        } catch (e: ResponseStoppedException) {
            return ChatModel("Error", null, false)
        }
    }

}