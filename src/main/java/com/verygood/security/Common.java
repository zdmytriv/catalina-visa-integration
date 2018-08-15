package com.verygood.security;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Common {

  // VGS
  static final String VGS_PROXY_USER_NAME = System.getProperty("vgs.proxy.user");
  static final String VGS_PROXY_USER_PASSWORD = System.getProperty("vgs.proxy.password");
  static final String VGS_FORWARD_PROXY_HOST = System.getProperty("vgs.proxy.host");
  static final int VGS_FORWARD_PROXY_PORT = 8080;

  // VISA
  static final String VISA_UPSTREAM_ENDPOINT = "https://sandbox.api.visa.com/visadirect/fundstransfer/v1/pushfundstransactions";
  static final String VISA_USER = System.getProperty("visa.user");
  static final String VISA_PASSWORD = System.getProperty("visa.password");

  static SSLContext insecureSSLContext() throws NoSuchAlgorithmException, KeyManagementException {
    SSLContext sslContext = SSLContext.getInstance("SSL");

    sslContext.init(null, new TrustManager[]{new X509TrustManager() {
      @Override
      public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
      }

      @Override
      public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
      }

      @Override
      public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[]{};
      }
    }}, new java.security.SecureRandom());

    return sslContext;
  }

  static String visaDirectPayload() {
    return "{\n" +
        "    \"systemsTraceAuditNumber\": 350420,\n" +
        "    \"retrievalReferenceNumber\": \"401010350420\",\n" +
        "    \"localTransactionDateTime\": \"2018-08-15\",\n" +
        "    \"acquiringBin\": 408999,\n" +
        "    \"acquirerCountryCode\": \"101\",\n" +
        "    \"senderAccountNumber\": \"tok_sandbox_hkpa5XQ1Sr78AsWPSDBfJ5\",\n" +
        "    \"transactionCurrencyCode\": \"USD\",\n" +
        "    \"senderName\": \"John Smith\",\n" +
        "    \"senderCountryCode\": \"USA\",\n" +
        "    \"senderAddress\": \"44 Market St.\",\n" +
        "    \"senderCity\": \"San Francisco\",\n" +
        "    \"senderStateCode\": \"CA\",\n" +
        "    \"recipientName\": \"Adam Smith\",\n" +
        "    \"recipientPrimaryAccountNumber\": \"4957030420210462\",\n" +
        "    \"amount\": \"350.00\",\n" +
        "    \"businessApplicationId\": \"AA\",\n" +
        "    \"transactionIdentifier\": 234234322342343,\n" +
        "    \"merchantCategoryCode\": 6012,\n" +
        "    \"sourceOfFundsCode\": \"03\",\n" +
        "    \"cardAcceptor\": {\n" +
        "      \"name\": \"Acceptor 1\",\n" +
        "      \"terminalId\": \"13655392\",\n" +
        "      \"idCode\": \"VMT200911026070\",\n" +
        "      \"address\": {\n" +
        "        \"state\": \"CA\",\n" +
        "        \"county\": \"081\",\n" +
        "        \"country\": \"USA\",\n" +
        "        \"zipCode\": \"94105\"\n" +
        "      }\n" +
        "    },\n" +
        "    \"feeProgramIndicator\": \"123\"\n" +
        "}";
  }
}
