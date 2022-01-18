package com.zv.geochat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.zv.geochat.service.ChatService;

import java.util.Random;

public class ChatActivityFragment extends Fragment {
    private static final String TAG = "ChatActivityFragment";
    EditText edtMessage;
    String userName = "user1";
    public ChatActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        Button btnJoinChat = (Button) v.findViewById(R.id.btnJoinChat);
        btnJoinChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sending to Chat Service: Join", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                joinChat(userName);
            }
        });

        Button btnLeaveChat = (Button) v.findViewById(R.id.btnLeaveChat);
        btnLeaveChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sending to Chat Service: Leave", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                leaveChat();
            }
        });

        Button btnSendMessage = (Button) v.findViewById(R.id.btnSendMessage);
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sending Message...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                sendMessage(edtMessage.getText().toString());
            }
        });

        Button btnReceiveMessage = (Button) v.findViewById(R.id.btnReceiveMessage);
        btnReceiveMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "New Message Arrived...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                simulateOnMessage();
            }
        });

        // send connect error xx
        Button btnSendConnectError = (Button) v.findViewById(R.id.btnSendConnectError);
        btnSendConnectError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Connect Error:" + Integer.toString(ChatService.CONNECT_ERROR_47), Snackbar.LENGTH_LONG)
                  .setAction("Action", null).show();
                sendConnectError();
            }
        });

        // send random ID
        Button btnSendRandomId = (Button) v.findViewById(R.id.btnSendRandomId);
        btnSendRandomId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // generate random ID
                int min = 10000;
                int max = 99999;
                Random r = new Random();
                int randomID = r.nextInt(max - min + 1) + min;

                Snackbar.make(view, "ChatService Received:" + Integer.toString(randomID), Snackbar.LENGTH_LONG)
                  .setAction("Action", null).show();
                sendRandomID(randomID);
            }
        });


        edtMessage = (EditText) v.findViewById(R.id.edtMessage);

        loadUserNameFromPreferences();

        return v;
    }

    private void loadUserNameFromPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        userName = prefs.getString(Constants.KEY_USER_NAME, "Name");
    }

    private void joinChat(String userName){
        Bundle data = new Bundle();
        data.putInt(ChatService.MSG_CMD, ChatService.CMD_JOIN_CHAT);
        data.putString(ChatService.KEY_USER_NAME, userName);
        Intent intent = new Intent(getContext(), ChatService.class);
        intent.putExtras(data);
        getActivity().startService(intent);
    }

    private void leaveChat(){
        Bundle data = new Bundle();
        data.putInt(ChatService.MSG_CMD, ChatService.CMD_LEAVE_CHAT);
        Intent intent = new Intent(getContext(), ChatService.class);
        intent.putExtras(data);
        getActivity().startService(intent);
    }

    private void sendMessage(String messageText){
        Bundle data = new Bundle();
        data.putInt(ChatService.MSG_CMD, ChatService.CMD_SEND_MESSAGE);
        data.putString(ChatService.KEY_MESSAGE_TEXT, messageText);
        Intent intent = new Intent(getContext(), ChatService.class);
        intent.putExtras(data);
        getActivity().startService(intent);
    }

    private void simulateOnMessage(){
        Bundle data = new Bundle();
        data.putInt(ChatService.MSG_CMD, ChatService.CMD_RECEIVE_MESSAGE);
        Intent intent = new Intent(getContext(), ChatService.class);
        intent.putExtras(data);
        getActivity().startService(intent);
    }

    private void sendConnectError(){
        Bundle data = new Bundle();
        data.putInt(ChatService.MSG_CMD, ChatService.CONNECT_ERROR_47);
        Intent intent = new Intent(getContext(), ChatService.class);
        intent.putExtras(data);
        getActivity().startService(intent);
    }

    private void sendRandomID(int randomID){
        Bundle data = new Bundle();
        data.putInt(ChatService.MSG_CMD, randomID);
        Intent intent = new Intent(getContext(), ChatService.class);
        intent.putExtras(data);
        getActivity().startService(intent);
    }

}
