package com.example.trabalho3unidade.notificacao

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build

import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import com.example.trabalho3unidade.R
import com.example.trabalho3unidade.model.Cidade
import com.example.trabalho3unidade.model.Clima

object NotificacaoUtils {
    val CHANEL_ID = "default"

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChanel(context: Context){
        val notificationManager = context.getSystemService( Context.NOTIFICATION_SERVICE ) as NotificationManager

        val channelName = "Padrão"
        val channelDescription = "Canal Padrão de Notificações"

        val channel = NotificationChannel(
            CHANEL_ID, channelName, NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = channelDescription
            enableLights(true)
            lightColor = Color.CYAN
            vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        }

        notificationManager.createNotificationChannel(channel)
    }

    fun notificationSimple(context: Context, clima: Clima, cidade: Cidade){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChanel(context)
        }

        val notificationBuilder = NotificationCompat.Builder(context, CHANEL_ID)
            .setSmallIcon(R.drawable.ic_temperatura)
            .setContentTitle("${clima.name}")
            .setContentText(clima.toString())
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(getColor(clima))
            .setDefaults(Notification.DEFAULT_ALL)

        val notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(1, notificationBuilder.build())
    }

    fun notificacaoError(context: Context, cidade: Cidade){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChanel(context)
        }

        val notificationBuilder = NotificationCompat.Builder(context, CHANEL_ID)
            .setSmallIcon(R.drawable.ic_error)
            .setContentTitle("${cidade.nome}")
            .setContentText("Erro: não existe temperatura pra essa cidade")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(ActivityCompat.getColor(context, R.color.vermelho))
            .setDefaults(Notification.DEFAULT_ALL)

        val notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(1, notificationBuilder.build())
    }

    private fun getColor(clima: Clima): Int{
        var temp = clima.kelvin2celsius(clima.main.temp)
        var color = R.color.vermelho
        when{
            temp > 40 -> color = R.color.vermelho
            temp > 32 -> color = R.color.laranja
            temp > 24 -> color = R.color.amarelo
            temp > 16 -> color = R.color.azul
            temp <= 16 -> color = R.color.azul_escuro
        }

        return color
    }
}