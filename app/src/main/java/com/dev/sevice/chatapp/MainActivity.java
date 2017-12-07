package com.dev.sevice.chatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.Socket;
import com.github.nkzawa.socketio.client.IO;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;

    private ImageView emoji_btn,submit_btn;
    private EditText emojicon_edit_text;

    private com.github.nkzawa.socketio.client.Socket socket;
    private ArrayList<String> arrayListData = new ArrayList<String>();
    private ArrayList<String> arrayListPesan = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emoji_btn = (ImageView)findViewById(R.id.emoji_btn);
        submit_btn = (ImageView)findViewById(R.id.submit_btn);
        emojicon_edit_text = (EditText)findViewById(R.id.emojicon_edit_text);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        adapter = new ChatAdapter(arrayListData,arrayListPesan);
        recyclerView.setAdapter(adapter);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        try {
            socket = IO.socket("http://172.20.10.4:3000");
        }catch (URISyntaxException e){
            Log.d("error","onCreate: " + e.toString());
        }
        socket.connect();
        socket.on("pesan",handling);
    }

    private Emitter.Listener handling = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tempPesan(args[0].toString(),null);
                }
            });

        }
    };
    public void setData(View v){
        getMessagePesan();
    }

    private void getMessagePesan(){
        String strMessage = emojicon_edit_text.getText().toString();
        emojicon_edit_text.setText("");

        tempPesan(strMessage,"me");
        socket.emit("pesan",strMessage);
    }

    private void tempPesan(String message,String type){
        arrayListData.add(message);
        this.arrayListPesan.add(type);
        adapter.notifyItemInserted(arrayListData.size()-1);
        recyclerView.smoothScrollToPosition(arrayListData.size()-1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
