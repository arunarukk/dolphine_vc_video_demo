import 'dart:async';
import 'dart:developer';

import 'package:flutter/services.dart';

const platformMethodChannel = MethodChannel("dolphinVc");

String baseUrl = 'https://test-blackis.dolphinvc.com';

String token =
    'eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ODYsInByb3ZpZGVyIjoiZW1haWwvcGhvbmUiLCJpbWdfdGh1bWJuYWlsIjoiaHR0cHM6Ly9yZXMuY2xvdWRpbmFyeS5jb20vYmxhY2stYm94L2ltYWdlL3VwbG9hZC92MTY3MjI5NDM5NC9jYXA3NW9lZDc5YXdkcmVhYWJyMC5qcGciLCJmaXJzdF9uYW1lIjoiYXJ1biIsImxhc3RfbmFtZSI6ImFyayIsImVtYWlsIjoiYXJ1bmFya0BsbGwuY29tIiwicGhvbmVfbnVtIjoiKzkxODg4ODg4ODg4MSIsInBhc3N3b3JkIjoiJDJiJDEyJFZ2ZkhCM0VLRWN6ZVVvR2t1Y29hamVXREFxYzdKOWE3MG5DR1JiNW5kNjJ0Ny9rWGhKNXppIiwiYWJvdXQiOiIiLCJvdHAiOjAsInZlcmlmaWVkIjpmYWxzZSwiYWRtaW4iOmZhbHNlLCJjcmVhdGVkX2F0IjoiMjAyMi0xMi0wMlQwOTozOToxNC4xNDlaIiwiY2xhc3Nyb29tX2lkIjozLCJ1cGRhdGVkX2F0IjoiMjAyMi0xMi0wMlQwOTozOToxNC4xNDlaIiwiaWF0IjoxNjczOTQxNDAwLCJhdWQiOiJodHRwczovL3d3dy5ibGFja2JveG5vdy5jb20iLCJpc3MiOiJibGFja2JveG5vdy5jb20iLCJzdWIiOiJibGFja2JveGRpZ2l0YWwyMkBnbWFpbC5jb20ifQ.bRkZOA1zasy-o-B9htCasNoYz57z8VELfmwuraerVb6RyEjBUl7kQUsTdKJ-xqbSakjLwZgWKpJA1KyXkiGQeqUcSOUg0FySaWKcPZb1rFEYFIOWWP_2uS1aT4JhGLWmoJkFQCe356fvRvw8_1t4ZDiTQSoJNefEsP35SthyQDZpHBT5J6TNxPuqntOJf2taKdJgLF1edbXWqkmlRqvsZX9lWarAf6s5gi_FcenKMu86eURp45aqYeIMo-KRT2J0UYlKLtDujPnySneKUrQZiZ681_RAJULelv3NKIWNsV2RIoszXYZpK-z_mNR5OF9MezFtCNgkI6Au15fs_3J9UkpMgzBfRKJmIKQW-fiqKQ8Gj7BwAG11xLz-WvPDS1-s8Rt8TdSPLUqoSD_QbOzbZSazpdp6iPlzkPGmPf4qnGgi9FkjdY_RQiSQCSSKm3juEMjUqWXhkSCVfXjq5qFob_4WPMD52DBm6VVG8NYcYERM7RS6aSGKdpdkW_GW-gpIBJN3hXXifXBvuNN7u_M0LHac1JJZ_2594j3YpPeIO8DjpiqYCw89KChSIC9j8IVcoShl3YLAN3XB4F2Ia7ESvExaZxsdWbo3grrmAnnjzX-PjwFoNG8hrQ_qPBnBjX25ZYZIuY0yDHJdYSmNEMzPhvCVPHHLSe-9PawzqkYah4s';

String? meetingID;

Future<Null> initialize() async {
  String _message;
  try {
    final String result = await platformMethodChannel.invokeMethod(
        'initialize', {'serverURL': baseUrl, 'token': token, 'domain': ''});

    _message = result;
  } on PlatformException catch (e) {
    _message = 'Can’t do native stuff ${e.message}.';
  }

  log(_message);
}

Future<Null> startMeeting() async {
  log('Startmeeting');
  String _message;
  try {
    final String result =
        await platformMethodChannel.invokeMethod('startMeeting');

    _message = result;
    meetingID = _message;
    log(_message);
  } on PlatformException catch (e) {
    _message = 'Can’t do native stuff ${e.message}.';
  }

  log(_message);
}

Future<Null> joinMeeting() async {
  log('joinMeeting');
  String _message;
  try {
    final String result = await platformMethodChannel
        .invokeMethod('joinMeeting', {'meetingId': meetingID});

    _message = result;
    log(_message);
  } on PlatformException catch (e) {
    _message = 'Can’t do native stuff ${e.message}.';
  }

  log(_message);
}
