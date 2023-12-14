import constants.Constantes;
import org.eclipse.paho.client.mqttv3.*;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class MeteoServer {

    public static void main(String[] args) {
        String publisherId = UUID.randomUUID().toString();
        Jedis jedis = new Jedis(Constantes.REDIS_SERVER_URI, Constantes.REDIS_SERVER_PORT);
        MqttClient client = null;

        while (true) {
            try {
                client = new MqttClient(Constantes.MQTT_SERVER_URI, publisherId);
                Constantes.connectMqttClient(client);
                client.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable throwable) {
                        System.out.println("Connection to Solace broker lost! " + throwable.getMessage());
                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                        // Resto del código...
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    }
                });

                client.subscribe(Constantes.subscribeAllStationsTopics, 0);
            } catch (MqttException e) {
                throw new RuntimeException(e);
            } finally {
                if (client != null) {
                    try {
                        client.disconnect();
                        client.close();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
                jedis.close();
            }
        }
    }
}