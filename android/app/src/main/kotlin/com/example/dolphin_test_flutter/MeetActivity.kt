package com.example.dolphin_test_flutter

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nouveaulabs.sdk.DVCBroadcastEvent

import android.content.Intent

import androidx.localbroadcastmanager.content.LocalBroadcastManager

import android.content.IntentFilter

import com.nouveaulabs.sdk.DVCMeetActivityDelegate

import com.facebook.react.modules.core.PermissionListener

import com.nouveaulabs.sdk.DVCMeetView

import com.nouveaulabs.sdk.DVCMeetConferenceOptions

import android.content.BroadcastReceiver
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

import com.nouveaulabs.sdk.DVCMeetActivityInterface
import com.nouveaulabs.sdk.DVCMeetOngoingConferenceService
import dolphinvc.ZYXBroadcastEvent


class MeetActivity : AppCompatActivity(), DVCMeetActivityInterface {
    private var dvcMeetView: DVCMeetView? = null
    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            onBroadcastReceived(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val options = DVCMeetConferenceOptions.Builder()
                .setFeatureFlag("welcomepage.enabled", false)
                .setFeatureFlag("server-url-change.enabled", false)
                .setFeatureFlag("call-integration.enabled", false)
                .setMeetingLink(intent.extras!!.getString("MEETING_LINK"))
                .build()
        dvcMeetView = DVCMeetView(this)
        dvcMeetView!!.join(options)
        setContentView(dvcMeetView)
        registerForBroadcastMessages()
    }

    // DVCMeetActivityInterface
    // Request Permissions
    override fun requestPermissions(permissions: Array<String>, requestCode: Int, listener: PermissionListener) {
        DVCMeetActivityDelegate.requestPermissions(this, permissions, requestCode, listener)
    }


    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        DVCMeetActivityDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //Conference Events
    override fun onUserLeaveHint() {
        dvcMeetView!!.enterPictureInPicture()
    }

    fun onConferenceJoined(map: HashMap<String?, Any?>?) {
        DVCMeetOngoingConferenceService.launch(this, map)
    }


    fun onConferenceTerminated(map: Map<String?, Any?>?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask()
        }
    }

    fun onConferenceWillJoin(map: Map<String?, Any?>?) {}
    override fun onDestroy() {
        if (dvcMeetView != null) {
            dvcMeetView!!.leave()
            dvcMeetView!!.dispose()
            dvcMeetView = null
        }
        DVCMeetOngoingConferenceService.abort(this)
        DVCMeetActivityDelegate.onHostDestroy(this)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    override fun onBackPressed() {
        DVCMeetActivityDelegate.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        DVCMeetActivityDelegate.onHostResume(this)
    }

    override fun onStop() {
        super.onStop()
        DVCMeetActivityDelegate.onHostPause(this)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        DVCMeetActivityDelegate.onActivityResult(
                this, requestCode, resultCode, data)
    }

    private fun registerForBroadcastMessages() {
        val intentFilter = IntentFilter()
        for (type in ZYXBroadcastEvent.Type.values()) {
            intentFilter.addAction(type.action)
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter)
    }

    private fun onBroadcastReceived(intent: Intent?) {
        if (intent != null) {
            val event = DVCBroadcastEvent(intent)
            when (event.type) {
                ZYXBroadcastEvent.Type.CONFERENCE_JOINED -> onConferenceJoined(event.data)
                ZYXBroadcastEvent.Type.CONFERENCE_WILL_JOIN -> onConferenceWillJoin(event.data)
                ZYXBroadcastEvent.Type.CONFERENCE_TERMINATED -> onConferenceTerminated(event.data)
            }
        }
    }
}