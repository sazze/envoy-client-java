/**
 * @author Janghwan Lee <jlee@sazze.com>
 *         Sazze, Inc. 2014
 * @since 11/17/14 8:46 PM
 */

package com.sazze.en.client;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(EmailTest.class.getName());

  @Test
  public void emailSending() {

    Email email = new TransactionEmail();
    email.to.add("jlee@sazze.com");
    email.from = "suppprt@local.progrids.com";
    email.subject = "test from java (thrift)";
    email.text = "test test\n test test ";

    SendClient client = new SendClient("0dee9b5fc033c43f20bb909bf741bac9", "local.progrids.com", "10.1.26.70", 9091);
    try {
      String resp = client.sendEmail(email);
      LOGGER.info(resp);
    } catch(RuntimeException e) {
      LOGGER.error("error!: " + e.getMessage());
    }

  }
}
