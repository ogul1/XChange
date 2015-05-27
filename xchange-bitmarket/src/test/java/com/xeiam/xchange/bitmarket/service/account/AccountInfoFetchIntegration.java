package com.xeiam.xchange.bitmarket.service.account;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitmarket.ExchangeUtils;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import static org.junit.Assert.assertNotNull;

/**
 * @author kfonal
 */
public class AccountInfoFetchIntegration {
  static {
    HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
      public boolean verify(String hostname, SSLSession session) {
        // ip address of the service URL(like.23.28.244.244)
        if (hostname.equals("www.test.bitmarket.pl"))
          return true;
        return false;
      }
    });
  }

  private Exchange exchange;

  @Before
  public void setUp() {

    exchange = ExchangeUtils.createExchangeFromProperties();
  }

  @Test
  public void fetchAccountInfoTest() throws Exception {

    if (exchange.getExchangeSpecification().getApiKey() == null || exchange.getExchangeSpecification().getSecretKey() == null) {
      return;  // forces pass if there is no keys passed
    }

    PollingAccountService service = exchange.getPollingAccountService();
    assertNotNull(service);
    //verify account info exists
    AccountInfo info = service.getAccountInfo();
    assertNotNull(info);
  }
}
