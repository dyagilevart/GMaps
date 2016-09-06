package ru.test.web.servlets;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.sql.*;
/**
 * Created by Denis on 05.09.2016.
 */

@WebServlet(name = "Servlet")
public class Servlet extends HttpServlet {
    public class Point
    {
        private double lat;
        private double lon;

        Point(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }

        public double getLat()
        {
            return lat;
        }

        public double getLon()
        {
            return lon;
        }
    }

    //private static double lat = 51.6627909;
    //private static double lon = 39.1580259;

    private ArrayList<Point> latlong = new ArrayList<Point>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        class Connec {
            static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
            static final String DB_URL = "jdbc:mysql://localhost/latlong";
            static final String USER = "root";
            static final String PASS = "admin";
            Connection conn;
            Statement stmt;
            public void GetLatLng() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    stmt = conn.createStatement();
                    String sql;
                    sql = "SELECT * FROM map";
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        latlong.add(new Point(rs.getDouble("latitude"), rs.getDouble("longtitude")));
                    }
                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (stmt != null)
                            stmt.close();
                    } catch (SQLException se2) {
                    }
                    try {
                        if (conn != null)
                            conn.close();
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            }
        }
        //latlong.add(new Point(51.6627909, 39.1580259));
        //latlong.add(new Point(51.6661116, 39.1735428));
        //latlong.add(new Point(51.6649138, 39.1725128));
        Connec c = new Connec();
        c.GetLatLng();
        PrintWriter out = response.getWriter();
        out.print(
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "  <title>Simple Map</title>\n" +
                "  <meta name=\"viewport\" content=\"initial-scale=1.0\">\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <style>\n" +
                "    html, body {\n" +
                "      height: 100%;\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "    }\n" +
                "    #map {\n" +
                "      height: 100%;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"map\"></div>\n" +
                "<script>\n" +
                "\n" +
                "  var map;\n" +
                "  function initMap() {\n" +
                "    map = new google.maps.Map(document.getElementById('map'), {\n" +
                "      center: {lat: 51.6627909, lng: 39.1580259},\n" +
                "      zoom: 15\n" +
                "    });\n");

        for (int i = 0; i < latlong.size(); i++)
        {
            out.print("  var marker = new google.maps.Marker({\n" +
                    "    position: {lat:" + latlong.get(i).getLat() + ", lng:" + latlong.get(i).getLon() +"},\n" );

            //out.print(latlong.get(i).getLat());
            //out.print(latlong.get(i).getLon());
            out.print("    map: map,\n" +
                    "  });");
        }
        out.print(
                "  }\n" +
                "\n" +
                "</script>\n" +
                "<script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyCgipunzbZ0dC5y33hV4uf4LrX_wK_G7UI&callback=initMap\"\n" +
                "        async defer></script>\n" +
                "</body>\n" +
                "</html>");
  /*
        for (int i = 0; i < latlong.size(); i++)
        {
            out.print(latlong.get(i).getLat());
            out.print(latlong.get(i).getLon());
        }
    */
    }
}
