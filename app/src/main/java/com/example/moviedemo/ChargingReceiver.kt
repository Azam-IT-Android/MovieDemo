package com.example.moviedemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ChargingReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == "android.intent.action.ACTION_POWER_CONNECTED"){
            Toast.makeText(context, "Phone is Charging...", Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(context, "Charger Unplugged", Toast.LENGTH_SHORT).show()
        }
    }

}