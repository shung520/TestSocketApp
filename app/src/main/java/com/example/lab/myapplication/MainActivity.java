package com.example.lab.myapplication;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends Activity
{
    TextView textResponse;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;
    String str = "Hello";
    String modifiedSentence;
    byte[] send_data = new byte[1024];
    byte[] receiveData = new byte[1024];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonConnect = (Button) findViewById(R.id.connect);

        buttonConnect.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            client();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    public void client() throws IOException
    {
        DatagramSocket client_socket = new DatagramSocket(30000);
        InetAddress IPAddress = InetAddress.getByName("140.124.181.190");

        send_data = str.getBytes();

        DatagramPacket send_packet = new DatagramPacket(send_data, str.length(), IPAddress, 30000);
        client_socket.send(send_packet);

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        client_socket.receive(receivePacket);
        modifiedSentence = new String(receivePacket.getData());
        System.out.println("FROM SERVER:" + modifiedSentence);
        if (modifiedSentence.charAt(2) == '%')
        {
            Log.d("(modifiedSentence.su", (modifiedSentence.substring(0, 3)));
        }
        else
        {
            Log.d("modifiedSentence", modifiedSentence);
        }
        modifiedSentence = null;
        client_socket.close();
    }
}