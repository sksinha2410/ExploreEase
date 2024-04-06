package com.sksinha2410.exploreease.Chatbot

import android.graphics.Bitmap
import com.sksinha2410.exploreease.Chatbot.data.model.ChatModel

data class ChatState(
    val chatList : MutableList<ChatModel> = mutableListOf(),
    val prompt : String = "",
    val bitmap : Bitmap? = null
)
