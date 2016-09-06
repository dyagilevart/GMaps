<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 04.09.2016
  Time: 22:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Simple Map</title>
  <meta name="viewport" content="initial-scale=1.0">
  <meta charset="utf-8">
  <style>
    html, body {
      height: 100%;
      margin: 0;
      padding: 0;
    }
    #map {
      height: 100%;
    }
  </style>
</head>
<body>
<div> Широта и Долгота = <%=request.getAttribute("lat")%>, <%=request.getAttribute("long")%></div>>
<div id="map"></div>
<script>

  var map;
  function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
      center: {lat: -34.397, lng: 150.644},
      zoom: 8


    });
    google.maps.event.addListener(map, 'click', function(event) {
      placeMarker(event.latLng);
    });

    function placeMarker(location) {
      var marker = new google.maps.Marker({
        position: location,
        map: map
      });
      document.location.href='redirect';
    }
  }

</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCgipunzbZ0dC5y33hV4uf4LrX_wK_G7UI&callback=initMap"
        async defer></script>
</body>
</html>

