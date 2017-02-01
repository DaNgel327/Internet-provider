package by.epam.osipov.internet.provider.generator;


import by.epam.osipov.internet.provider.entity.impl.City;
import by.epam.osipov.internet.provider.entity.impl.Coverage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 19.01.2017.
 */

/**
 * This class will get the lat long values.
 */
public class CoverageFileGenerator {

    public static void createFile(List<String> lines) {

        Path file = Paths.get("C:\\Users\\Lenovo\\InternetProvider\\src\\main\\webapp\\resource\\csv\\coverage.csv");

        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static List<String> getFullAddresses(List<Coverage> coverageList, List<City> cityList) {
        List<String> result = new ArrayList<>();

        for (Coverage coverage : coverageList) {
            int idCity = coverage.getIdCity();
            for (City city : cityList) {
                if (city.getId() == idCity) {
                    result.add(city.getName() + " " + coverage.getStreet() + " " + coverage.getHouseNumber());
                }
            }
        }

        return result;
    }

    private static List<String> getTextValuesByTagName(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            list.add(getTextValue(nodeList.item(i)));
        }
        return list;
    }

    private static String getTextValue(Node node) {
        StringBuffer textValue = new StringBuffer();
        int length = node.getChildNodes().getLength();
        for (int i = 0; i < length; i ++) {
            Node c = node.getChildNodes().item(i);
            if (c.getNodeType() == Node.TEXT_NODE) {
                textValue.append(c.getNodeValue());
            }
        }
        return textValue.toString().trim();
    }

    public static String getLatLongPositions(String address) throws IOException, ParserConfigurationException, SAXException {
        int responseCode = 0;
        String api = "https://geocode-maps.yandex.ru/1.x/?geocode=" + URLEncoder.encode(address, "UTF-8");
        System.out.println("URL : " + api);
        URL url = new URL(api);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.connect();

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        Document document = builder.parse(httpConnection.getInputStream());

        List<String> poses = getTextValuesByTagName(document.getDocumentElement(),"pos");

        return poses.get(0);
    }

}
