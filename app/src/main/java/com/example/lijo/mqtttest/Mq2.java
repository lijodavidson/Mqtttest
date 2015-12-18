package com.example.lijo.mqtttest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import java.io.UnsupportedEncodingException;

public class Mq2 extends Activity {

    private static final String TAG ="Mqtactivity" ;
    private EditText qttopic;
    private EditText qtmessage;
    private Button qtpub;
   private Button subbutt;
    private EditText subtxt;
    private EditText qtmessage1;

    public static final String   DEBUG_TAG = "MqttService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mq2);

        String clientId = MqttClient.generateClientId();
        final MqttAndroidClient client =
                new MqttAndroidClient(this.getApplicationContext(), "tcp://10.50.8.24:9244", clientId);
                /* new MqttAndroidClient(this.getApplicationContext(), "tcp://10.50.17.78:1883", clientId);*/



        qttopic=(EditText)findViewById(R.id.topi);
        qtmessage=(EditText)findViewById(R.id.msg);
        qtmessage1=(EditText)findViewById(R.id.msg1);
        qtpub=(Button)findViewById(R.id.button);
        subbutt=(Button)findViewById(R.id.sub_button);
        subtxt=(EditText)findViewById(R.id.subtxt);


qtpub.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        /*Toast.makeText(getApplicationContext(), "button clicked", Toast.LENGTH_SHORT).show();*/
        final String top= qttopic.getText().toString();
        final String masss=qtmessage.getText().toString();
        final String masss1=qtmessage1.getText().toString();

        final String unamepass=masss+"   "+masss1;

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(getApplicationContext(), "ok some shiits happening", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onSuccess");
                    String topic = top;
                    String payload =unamepass;
                    byte[] encodedPayload = new byte[0];
                    try {
                        encodedPayload = payload.getBytes("UTF-8");
                        MqttMessage message = new MqttMessage(encodedPayload);
                        message.setRetained(true);
                        client.publish(topic, message);







                 class examplecallback implements MqttCallback
                        {


                            @Override
                            public void connectionLost(Throwable cause) {



                            }

                            @Override
                            public void messageArrived(String topic, MqttMessage message) throws Exception {

                                Toast.makeText(getApplicationContext(), "Message Arrived: " + message+ " on tipic: " + topic, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void deliveryComplete(IMqttDeliveryToken token) {


                            }
                        }






                        /*Toast.makeText(getApplicationContext(), "Message Arrived: " + message+ " on tipic: " + topic, Toast.LENGTH_SHORT).show();*/
                       /* System.out.println("Message Arrived: " + message.getPayload() + " on tipic: " + topic.getBytes());*/

                    }


                    catch (UnsupportedEncodingException | MqttException e) {
                        e.printStackTrace();




                    }

                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(getApplicationContext(), "some shits wrong", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
});







        subbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String subs=subtxt.getText().toString();
        /*Toast.makeText(getApplicationContext(), "button clicked", Toast.LENGTH_SHORT).show();*/


                try {
                    IMqttToken token = client.connect();
                    token.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            // We are connected
                            Toast.makeText(getApplicationContext(), "its connecting", Toast.LENGTH_SHORT).show();



                            String topic = subs;
                            int qos = 1;
                            try {
                                final IMqttToken subToken = client.subscribe(topic, qos);

                                subToken.setActionCallback(new IMqttActionListener() {
                                    @Override
                                    public void onSuccess(IMqttToken asyncActionToken) {










                                    }

                                    @Override
                                    public void onFailure(IMqttToken asyncActionToken,
                                                          Throwable exception) {
                                        // The subscription could not be performed, maybe the user was not
                                        // authorized to subscribe on the specified topic e.g. using wildcards

                                    }







                                });
                            } catch (MqttException e) {
                                e.printStackTrace();
                            }




                        }
                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            // Something went wrong e.g. connection timeout or firewall problems
                            Toast.makeText(getApplicationContext(), "some shits wrong", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onFailure");

                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });





























        /*subbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String subs=subtxt.getText().toString();





                String pus=subs;
                int qos = 1;
                try {
                    IMqttToken token = client.connect();
                    IMqttToken subToken = client.subscribe(pus, qos);
                    subToken.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            Toast.makeText(getApplicationContext(), "subscribed to "+subs+" ", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken,
                                              Throwable exception) {

                            Toast.makeText(getApplicationContext(), "some shits wrong", Toast.LENGTH_SHORT).show();

                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }




            }
        });
*/







    }

    private void messageArrived(MqttTopic topic, MqttMessage message) {









    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mq2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
