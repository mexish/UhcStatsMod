package me.mexish.uhcstatsmod.requests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class MojangRequest {

    String username;

    public static UUID getUUID(String username) {
        String uuid = "";
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String uuidWithoutHyphens = response.toString().split("\"")[3];
                uuid = uuidWithoutHyphens.substring(0, 8) + "-" + uuidWithoutHyphens.substring(8, 12) + "-"
                        + uuidWithoutHyphens.substring(12, 16) + "-" + uuidWithoutHyphens.substring(16, 20) + "-"
                        + uuidWithoutHyphens.substring(20);

                System.out.println("UUID for " + username + " is " + uuid);
            }
        } catch (Exception e) {
            return null;
        }

        return UUID.fromString(uuid);
    }












}

