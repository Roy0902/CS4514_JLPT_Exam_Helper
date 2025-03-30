package com.example.cs4514_jlpt_exam_helper;

import android.util.Log;

import com.example.cs4514_jlpt_exam_helper.data.Account;
import com.example.cs4514_jlpt_exam_helper.network.repository.AccountRepository;
import com.google.firebase.messaging.FirebaseMessagingService;

public class FirebaseTokenService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        sendTokenToServer(token);
    }

    private void sendTokenToServer(String token) {
        String session_token = SessionManager.getSessionToken(this);
        AccountRepository repository = AccountRepository.getInstance();
        repository.updateFirebaseToken(session_token, token);
    }
}
