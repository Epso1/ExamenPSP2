import constants.Constantes;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MeteoStations extends Thread {
    private String uniqueID = "ID";
    private static int uniqueNumber = 0;

    public MeteoStations(){
        uniqueID += uniqueNumber;
        uniqueNumber++;
        setName(uniqueID);
    }

    @Override
    public void run() {
        String publisherId = UUID.randomUUID().toString();
        try (MqttClient client = new MqttClient(Constantes.MQTT_SERVER_URI, publisherId)){
            Constantes.connectMqttClient(client);
            while (true) {
                client.publish(createMqttTopic(), new MqttMessage(createMqttMsg()));
                sleep(5000);
            }
        } catch (MqttException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public float generateTemperature(){
        int min = -10;
        int max = 40;

        return new Random().nextFloat() * (max - min) + min;
    }

    public byte[] createMqttMsg(){
        String msg = String.format("date=%s#hour=%s#temperature=%.2f",
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")),
                generateTemperature());

        return msg.getBytes();
    }

    public String createMqttTopic(){
        String topic = String.format("/%s/METEO/%s/MEASUREMENTS", Constantes.STUDENT_INITIALS, uniqueID);

        return topic;
    }

    public static void main(String[] args) {
        int maxThreads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(maxThreads);
        for (int i = 0; i < maxThreads; i++){
            executor.execute(new MeteoStations());
        }
    }
}