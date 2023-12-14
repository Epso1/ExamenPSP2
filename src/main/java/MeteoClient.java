import constants.Constantes;
import redis.clients.jedis.Jedis;

import java.util.Scanner;
import java.util.Set;

public class MeteoClient {
    public static void main(String[] args) {
        String command = "";
        Scanner sc = new Scanner(System.in);

        try (Jedis jedis = new Jedis(Constantes.REDIS_SERVER_URI, Constantes.REDIS_SERVER_PORT)) {
            while (!command.equals("EXIT")){
                System.out.print("Introduce un comando: ");
                String rawCommand = sc.nextLine() + " ";
                String[] commandSplit = rawCommand.split(" ", 2);
                command = commandSplit[0];
                switch (command) {
                    case "LAST" -> {
                        String stationId = commandSplit[1].trim();
                        String jedisHash = Constantes.STUDENT_INITIALS + ":LASTMEASUREMENT:%s"+ stationId;
                        String dateTime = jedis.hget(jedisHash, "datetime");
                        String temperature = jedis.hget(jedisHash, "temperature");
                        System.out.printf("Últimas medidas de la estación %s: fecha y hora: %s, temperatura: %s\n", stationId, dateTime, temperature);
                    }
                    case "MAXTEMP" -> {
                        if (commandSplit[1].trim().equals("ALL")) {
                            Set<String> keys = jedis.keys(Constantes.STUDENT_INITIALS + ":TEMPERATURES:*");
                            float maxTemp = Float.MIN_VALUE;
                            for (String key : keys) {
                                for (String temp : jedis.lrange(key, 0, -1)) {
                                    maxTemp = Math.max(maxTemp, Float.parseFloat(temp));
                                }
                            }
                            System.out.printf("La temperatura más alta del sistema es: %.2f\n", maxTemp);
                        } else {
                            String key = Constantes.STUDENT_INITIALS +":TEMPERATURES:" + commandSplit[1].trim();
                            float maxTemp = Float.MIN_VALUE;
                            for (String temp : jedis.lrange(key, 0, -1)) {
                                maxTemp = Math.max(maxTemp, Float.parseFloat(temp));
                            }
                            System.out.printf("La temperatura más alta de la estación %s es: %.2f\n", commandSplit[1].trim(), maxTemp);
                        }
                    }
                    case "ALERTS" -> {
                        Set<String> alerts = jedis.smembers(Constantes.ALERTS_KEY_REDIS);
                        for (String alert : alerts) {
                            System.out.println(alert);
                            jedis.srem(Constantes.ALERTS_KEY_REDIS, alert);
                        }
                    }
                    case "EXIT" -> {
                        System.out.println("Saliendo de la aplicación...");
                    }
                }
            }
        }
    }
}