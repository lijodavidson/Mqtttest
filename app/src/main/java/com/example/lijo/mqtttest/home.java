package com.example.lijo.mqtttest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import java.io.UnsupportedEncodingException;

public class home extends Activity {

    private static final String TAG ="MQTTActivity" ;
    private Button pub_button;
    private Button sub_button;
    private EditText pub_topic;
    private EditText pub_message;
    private EditText sub_topic;
    private TextView sub_res;

    MqttAndroidClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sub_button=(Button)findViewById(R.id.subscribe_button);
        sub_topic=(EditText)findViewById(R.id.subscribe_edit_text);
        pub_topic=(EditText)findViewById(R.id.publish_edit_text);
        pub_message=(EditText)findViewById(R.id.publish_edit_text_message);
        pub_button=(Button)findViewById(R.id.publish_button);
        sub_res=(TextView)findViewById(R.id.sub_respon);

        String clientId = MqttClient.generateClientId();
      client = new MqttAndroidClient(this.getApplicationContext(), "tcp://10.50.8.24:9244", clientId);


     //TESTING CONNECTION WITH THE SERVER

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "onSuccess");
                    Toast.makeText(getApplicationContext(), "Connection Successful", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "onFailure");
                    Toast.makeText(getApplicationContext(), "Connection Unsuccessful", Toast.LENGTH_SHORT).show();

                }


            });
        } catch (MqttException e) {
            e.printStackTrace();
        }



//subscribe part
        sub_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                subscribe();


            }
        });


pub_button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        publish();
    }
});









    }

    private void publish() {

        String Topic=pub_topic.getText().toString();
        String Message=pub_message.getText().toString();


        Toast.makeText(getApplicationContext(), "Publish Method is called", Toast.LENGTH_SHORT).show();
        String topic = Topic;
        String payload = Message;
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        }

        catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }

    private void subscribe() {

        String Subtopic=sub_topic.getText().toString();
        String topic = Subtopic;
        int qos = 0;
        try {
            IMqttToken subToken = client.subscribe(topic, qos);


            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {

                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {






                    Log.d(getClass().getCanonicalName(), "Message arrived:" + topic + ":" + message.toString());
                    Toast.makeText(getApplicationContext(), "message arrived" +topic+ ":"+message.toString(), Toast.LENGTH_SHORT).show();

                    sub_res.setText(message.toString());




                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });





        } catch (MqttException e) {
            e.printStackTrace();
        }





    }







}
