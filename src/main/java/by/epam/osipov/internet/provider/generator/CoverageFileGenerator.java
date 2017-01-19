package by.epam.osipov.internet.provider.generator;


import by.epam.osipov.internet.provider.dao.impl.CityDAO;
import by.epam.osipov.internet.provider.dao.impl.CoverageDAO;
import by.epam.osipov.internet.provider.entity.impl.City;
import by.epam.osipov.internet.provider.entity.impl.Coverage;
import by.epam.osipov.internet.provider.pool.ConnectionPool;
import by.epam.osipov.internet.provider.pool.ConnectionProxy;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
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
    public static void main(String[] args) throws Exception {

        List<String> addresses = null;

        try (ConnectionProxy connection = ConnectionPool.getInstance().getConnection()) {
            CoverageDAO coverageDAO = new CoverageDAO(connection);
            CityDAO cityDAO = new CityDAO(connection);

            List<Coverage> coverageList = coverageDAO.findAll();
            List<City> cityList = cityDAO.findAll();

            addresses = getFullAddresses(coverageList, cityList);

        } catch (Exception e) {
            System.out.println("ex");
        }

        List<String> lines = new ArrayList<>();

        for (String s : addresses) {
            String[] coordinates = getLatLongPositions(s);
            String line = s + "," + coordinates[0] + " " + coordinates[1];
            lines.add(line);
        }

        createFile(lines);
    }

    public static void createFile(List<String> lines) {
        //List<String> lines = Arrays.asList("The first line", "The second line");

        Path file = Paths.get("the-file-name.csv");

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

    public static String[] getLatLongPositions(String address) throws Exception {
        int responseCode = 0;
        String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=true";
        System.out.println("URL : " + api);
        URL url = new URL(api);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.connect();
        responseCode = httpConnection.getResponseCode();
        if (responseCode == 200) {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            ;
            Document document = builder.parse(httpConnection.getInputStream());
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("/GeocodeResponse/status");
            String status = (String) expr.evaluate(document, XPathConstants.STRING);
            if (status.equals("OK")) {
                expr = xpath.compile("//geometry/location/lat");
                String latitude = (String) expr.evaluate(document, XPathConstants.STRING);
                expr = xpath.compile("//geometry/location/lng");
                String longitude = (String) expr.evaluate(document, XPathConstants.STRING);
                return new String[]{latitude, longitude};
            } else {
                throw new Exception("Error from the API - response status: " + status);
            }
        }
        return null;
    }
}
