package com.example.dolphin_test_flutter

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant


class MainActivity: FlutterActivity() {
    private val CHANNEL = "dolphinVc"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
                .setMethodCallHandler { call: MethodCall, result: MethodChannel.Result ->

                    when (call.method) {
                        "initialize" -> {
                            val serverURL = call.argument<String>("serverURL")
                            val token = call.argument<String>("token")
                            val  domain = call.argument<String>("domain")
                            VcManager.initialize(this@MainActivity,serverURL,token,domain, result )
            //                        result.success(myMessage)
                        }
                        "startMeeting" -> {
                            VcManager.startMeeting(this@MainActivity,result)
                        }
                        "joinMeeting" -> {
                            val meetingId = call.argument<String>("meetingId")
                            val passcode = call.argument<String>("passcode")
                            val displayName = call.argument<String>("displayName")
                            VcManager.joinMeeting(this@MainActivity,result,meetingId,passcode,displayName)
                        }
                    }
                }
    }
}
