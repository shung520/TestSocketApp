package com.example.lab.myapplication;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread implements Runnable{

    Socket socket = null;
    Handler handler = null;

    BufferedReader br = null;

    public ClientThread(Socket socket, Handler handler) {
        this.socket = socket;
        this.handler = handler;
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        String content = null;
        try {
            while((content = br.readLine())!=null){
                Message msg = new Message();
                msg.what = 1;
                msg.obj = content;
                handler.sendMessage(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
