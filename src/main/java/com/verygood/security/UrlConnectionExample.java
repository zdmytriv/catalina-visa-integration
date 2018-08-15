package com.verygood.security;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;

import static com.verygood.security.Common.VGS_FORWARD_PROXY_HOST;
import static com.verygood.security.Common.VGS_FORWARD_PROXY_PORT;
import static com.verygood.security.Common.VGS_PROXY_USER_NAME;
import static com.verygood.security.Common.VGS_PROXY_USER_PASSWORD;
import static com.verygood.security.Common.VISA_PASSWORD;
import static com.verygood.security.Common.VISA_UPSTREAM_ENDPOINT;
import static com.verygood.security.Common.VISA_USER;

public class UrlConnectionExample {

  public static void main(String[] args) throws Exception {
    // Need this for proxy authentication
    System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
    System.setProperty("jdk.http.auth.proxying.disabledSchemes", "");
    Authenticator.setDefault(
        new Authenticator() {
          @Override
          public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(
                VGS_PROXY_USER_NAME, VGS_PROXY_USER_PASSWORD.toCharArray());
          }
        }
    );

    // Visa payload
    String payload = Common.visaDirectPayload();

    // Connection
    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(VGS_FORWARD_PROXY_HOST, VGS_FORWARD_PROXY_PORT));
    HttpsURLConnection conn = (HttpsURLConnection) new URL(VISA_UPSTREAM_ENDPOINT).openConnection(proxy);

    conn.setSSLSocketFactory(Common.insecureSSLContext().getSocketFactory());
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Accept", "application/json,application/octet-stream");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setRequestProperty("X-Client-Transaction-Id", "tuqbNLP7");
    conn.setRequestProperty("Content-Length", String.valueOf(payload.getBytes().length));
    conn.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString((VISA_USER + ":" + VISA_PASSWORD).getBytes()));

    // Execute
    conn.setDoOutput(true);

    // Response
    conn.getOutputStream().write(payload.getBytes());
    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
    StringBuilder builder = new StringBuilder();
    for (String line; (line = reader.readLine()) != null; ) {
      builder.append(line).append("\n");
    }

    reader.close();
    conn.disconnect();

    System.out.println(builder.toString());
  }
}
