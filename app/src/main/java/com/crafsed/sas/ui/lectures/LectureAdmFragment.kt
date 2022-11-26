package com.crafsed.sas.ui.lectures

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment

class LectureAdmFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun scanWiFi() {
        val wifiManager = requireContext().getSystemService(Context.WIFI_SERVICE) as WifiManager

        wifiManager.startLocalOnlyHotspot(
            object : WifiManager.LocalOnlyHotspotCallback() {
                override fun onStarted(reservation: WifiManager.LocalOnlyHotspotReservation?) {
                    super.onStarted(reservation)
                    reservation.toString()
                }
            },
            null
        )

        val success = wifiManager.startScan()
        if (!success) {
            // scan failure handling
            scanFailure(wifiManager)
        }
    }

    @SuppressLint("MissingPermission")
    private fun scanSuccess(wifiManager: WifiManager) {
        Log.e("TAG", "scanSuccess: OK", )
        val results = wifiManager.scanResults.forEach {
            Log.e("TAG", "scanSuccess: $it")
        }
    }


    @SuppressLint("MissingPermission")
    private fun scanFailure(wifiManager: WifiManager) {
        Log.e("TAG", "scanFailure: NOT OK", )
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        val results = wifiManager.scanResults.forEach {

            Log.e("TAG", "scanSuccess: $it")
        }
    }
}