package com.sksinha2410.exploreease

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class FirebaseService:FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        try {
            val intent = Intent(this,MainActivity::class.java)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationID = Random.nextInt()
            //test image
            val imageUrl = message.data["imageUrl"]

            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            val input = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(input)
            //testing image


            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                createNotificationChannel(notificationManager)
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(this,0,intent,
                FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(message.data["title"])
                .setContentText(message.data["message"])
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(bitmap)
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.logo)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

            notificationManager.notify(notificationID,notification)

        }catch (e:Exception){}

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = CHANNEL_DESCRIPTION
            enableLights(true)
            lightColor = Color.GREEN
        }
        notificationManager.createNotificationChannel(channel)
    }
}