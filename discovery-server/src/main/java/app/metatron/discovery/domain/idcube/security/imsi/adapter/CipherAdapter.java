package app.metatron.discovery.domain.idcube.security.imsi.adapter;

import app.metatron.discovery.common.exception.MetatronException;
import app.metatron.discovery.domain.idcube.IdCubeProperties;
import app.metatron.discovery.util.AuthUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CipherAdapter {
  private static final Logger LOGGER = LoggerFactory.getLogger(CipherAdapter.class);

  private IdCubeProperties idCubeProperties;

  @Autowired
  public void setIdCubeProperties(IdCubeProperties idCubeProperties) {
    this.idCubeProperties = idCubeProperties;
  }

  public List<String> encryptOrDecrypt(String cipherType, List<String> collectData) {
    final HttpHeaders headers = new HttpHeaders();
    headers.set("X-Api-Key", idCubeProperties.getImsi().getCipherServer().getApiKey());
    headers.set("Content-Type", "application/json");

    HttpEntity<Map<String, Object>> entity = new HttpEntity(new HashMap<>(), headers);
    entity.getBody().put("userId", AuthUtils.getAuthUserName());
    entity.getBody().put("datas", collectData);

    final String url = idCubeProperties.getImsi().getCipherServer().getApiUrl() + "/security/xdr/" + cipherType;
    RestTemplate restTemplate;
    try {
      restTemplate = getRestTemplateForDisableSSLVerification();
    } catch (Exception e) {
      LOGGER.error("RestTemplate create error", e);
      throw new MetatronException("RestTemplate create error");
    }

    try {
      ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
      if(response.getStatusCode() != HttpStatus.OK) {
        LOGGER.error("cipher api call response status error-" + "url:" + url + ", parameters:" + entity.getBody() + ", status:" + response.getStatusCode().toString());
        throw new MetatronException("cipher api call response status error:" + response.getStatusCode().toString());
      }
      final Integer resCode = (Integer)response.getBody().get("rescode");

      if (resCode != 0) {
        LOGGER.error("cipher api call rescode error-" + "url:" + url + ", parameters:" + entity.getBody() + ", rescode:" + resCode + ", message:" + response.getBody().get("message"));
        throw new MetatronException("cipher api call returnCode error: " + resCode);
      }

      return (List<String>)response.getBody().get("datas");
    } catch(RestClientException rce) {
      LOGGER.error("cipher api call error-" + "url:" + url + ", parameters" + entity.getBody());
      LOGGER.error("cipher api call error-", rce);
      throw new MetatronException(rce);
    }
  }

  private RestTemplate getRestTemplateForDisableSSLVerification() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
    TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setHttpClient(httpClient);
    RestTemplate restTemplate = new RestTemplate(requestFactory);
    return restTemplate;
  }
}