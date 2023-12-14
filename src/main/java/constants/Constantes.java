package constants;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Constantes {
    public static final String MQTT_SERVER_URI = "tcp://localhost:1883";
    public static final String REDIS_SERVER_URI = "localhost";
    public static final int REDIS_SERVER_PORT = 6379;

    public static final String STUDENT_INITIALS = "CAR";
    public static final String subscribeAllStationsTopics = "/CAR/METEO/#";
    public static final String ALERTS_KEY_REDIS = "CAR:ALERTS";


    // Define un método para conectar un cliente MQTT
    public static void connectMqttClient(MqttClient client) throws MqttException {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        client.connect(options);
    }
}
