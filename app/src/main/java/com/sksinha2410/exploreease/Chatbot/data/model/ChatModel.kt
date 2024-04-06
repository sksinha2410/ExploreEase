package com.sksinha2410.exploreease.Chatbot.data.model

import android.graphics.Bitmap

data class ChatModel (
    val prompt: String,
    val bitmap: Bitmap?,
    val isFromUser : Boolean
)