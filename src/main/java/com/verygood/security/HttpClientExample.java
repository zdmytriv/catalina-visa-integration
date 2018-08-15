package com.verygood.security;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;

import java.nio.charset.Charset;
import java.util.Base64;

import static com.verygood.security.Common.VGS_FORWARD_PROXY_HOST;
import static com.verygood.security.Common.VGS_FORWARD_PROXY_PORT;
import static com.verygood.security.Common.VGS_PROXY_USER_NAME;
import static com.verygood.security.Common.VGS_PROXY_USER_PASSWORD;
import static com.verygood.security.Common.VISA_PASSWORD;
import static com.verygood.security.Common.VISA_UPSTREAM_ENDPOINT;
import static com.verygood.security.Common.VISA_USER;

public class HttpClientExample {

  public static void main(String[] args) throws Exception {
    // Prep proxy
    HttpHost proxy = new HttpHost(VGS_FORWARD_PROXY_HOST, VGS_FORWARD_PROXY_PORT, "http");

    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider.setCredentials(
        new AuthScope(proxy),
        new UsernamePasswordCredentials(VGS_PROXY_USER_NAME, VGS_PROXY_USER_PASSWORD));

    // Prep http client
    SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(
        Common.insecureSSLContext(),
        new AllowAllHostnameVerifier());

    HttpClient httpClient = HttpClientBuilder.create()
        .setProxy(proxy)
        .setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
        .setSSLSocketFactory(connectionFactory)
        .setDefaultCredentialsProvider(credentialsProvider)
        .setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy())
        .build();

    // Request
    HttpPost request = new HttpPost(VISA_UPSTREAM_ENDPOINT);
    request.setHeader("Accept", "application/json,application/octet-stream");
    request.setHeader("Content-Type", "application/json");
    request.setHeader("X-Client-Transaction-Id", "tuqbNLP7");
    request.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((VISA_USER + ":" + VISA_PASSWORD).getBytes()));
    request.setEntity(new StringEntity(Common.visaDirectPayload()));

    // Response
    HttpResponse httpResponse = httpClient.execute(request);

    System.out.println("Status code: " + httpResponse.getStatusLine().getStatusCode());
    System.out.println(IOUtils.toString(httpResponse.getEntity().getContent(), Charset.defaultCharset()));
  }
}
