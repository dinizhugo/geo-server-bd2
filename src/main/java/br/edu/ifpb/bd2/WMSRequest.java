package br.edu.ifpb.bd2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class WMSRequest {

    private static final String WMS_URL = "http://localhost:8080/geoserver/wms";

    public static void requestWMSImage(String layerName) {
        try {

            String requestURL = WMS_URL + "?service=WMS&version=1.3.0&request=GetMap" +
                    "&layers=" + layerName + ":PB_Municipios_2023" +  // Nome correto do workspace
                    "&styles=" +
                    "&crs=EPSG:4326" +
                    "&bbox=-7.22,-36.06,-6.82,-35.66" +
                    "&width=800&height=600" +
                    "&format=image/png" +
                    "&scale=136000" +
                    "&transparent=true";


            URL url = URI.create(requestURL).toURL();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();
                BufferedImage image = ImageIO.read(inputStream);

                // Salva a imagem
                File outputFile = new File("esperanca_wms.png");
                ImageIO.write(image, "png", outputFile);

                System.out.println("Imagem salva em: " + outputFile.getAbsolutePath());
            } else {
                System.out.println("Erro na requisição: " + connection.getResponseMessage());
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao requisitar a imagem do WMS");
        }
    }
}
