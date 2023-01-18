package com.example.dolphin_test_flutter;

import android.content.Context;
import android.content.Intent;

import com.nouveaulabs.sdk.DolphinVC;
import com.nouveaulabs.sdk.listeners.DolphinInitListener;
import com.nouveaulabs.sdk.listeners.StartMeetingListener;
import com.nouveaulabs.sdk.models.StartMeetingOptions;
import com.nouveaulabs.sdk.models.StartMeetingResponse;

import io.flutter.plugin.common.MethodChannel;

public class VcManager {
    public static void initilize(Context context, String serverURL, String token, String domain, MethodChannel.Result result) {
        DolphinVC.initialize(context, serverURL, token, domain, new DolphinInitListener() {
            @Override
            public void onInitFailed(String reason) {
                result.error("error",reason,null);
            }

            @Override
            public void onInitSuccess() {
             result.success("success");
            }
        });
    }
    public static void  startMeeting(Context context, MethodChannel.Result result){
        StartMeetingOptions meetingOptions = new StartMeetingOptions();
        meetingOptions.setDefaultMeeting(false);
        meetingOptions.setIncludeAudio(true);
        meetingOptions.setIncludeVideo(true);
        meetingOptions.setStartWithAudio(true);
        meetingOptions.setMeetingName("User01's Meeting");
        meetingOptions.setMeetingPassword("0000");
        meetingOptions.setModeratorUserName("arunark@lll.com");


        DolphinVC.startMeeting(context, meetingOptions, new StartMeetingListener() {
            @Override
            public void onSuccess(StartMeetingResponse meetingDetails) {
                navigateToMeetActivity(context,meetingDetails.meetingLink);
                result.success("success");
            }

            @Override
            public void onFailure(String reason) {
                result.error("error",reason,null);
            }
        });
    }
    private static void navigateToMeetActivity(Context context,String meetingLink){
        Intent meetingIntent = new Intent(context,MeetActivity.class);
        meetingIntent.putExtra("MEETING_LINK",meetingLink);
        context.startActivity(meetingIntent);
    }
}
