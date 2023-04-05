package org.example;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.HttpURLConnection;

public class Main implements HttpFunction {
  @Override
  public void service(HttpRequest request, HttpResponse response)
      throws IOException {

    BufferedWriter writer = response.getWriter();

    switch (request.getMethod()) {
      case "GET":
        response.setStatusCode(HttpURLConnection.HTTP_OK);
        writer.write("Hello world!");
        break;
      case "PUT":
        response.setStatusCode(HttpURLConnection.HTTP_FORBIDDEN);
        writer.write("Forbidden!");
        break;
      default:
        response.setStatusCode(HttpURLConnection.HTTP_BAD_METHOD);
        writer.write("Something blew up!");
        break;
    }
  }

  public static void main(String[] args) {
    System.out.println("Hello world!");
  }
}