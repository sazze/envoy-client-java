package com.sazze.en.client;

/**
 * @author Janghwan Lee <jlee@sazze.com>
 *         Sazze, Inc. 2014
 * @since 11/17/14 4:08 PM
 */

import com.sazze.en.emitir.thrift.SendService;
import org.apache.commons.codec.binary.Hex;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.hashids.Hashids;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * current only support thrift
 */
public class SendClient {
  protected String secret;
  protected String senderDomain;
  protected String host;
  protected int port;
  protected SendService.Iface client;

  public static SimpleDateFormat rfc2822 = new SimpleDateFormat("EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);

  public SendClient(String secret, String sendorDomain, String host, int port) {
    rfc2822.setTimeZone(TimeZone.getTimeZone("UTC"));
    this.secret = secret;
    this.senderDomain = sendorDomain;
    this.host = host;
    this.port = port;
    TSocket transport = new TSocket(host, port);

    try {
      transport.open();
    } catch (TTransportException e) {
      throw new RuntimeException("Unable to open thrift transport: " + e.getMessage());
    }

    TBinaryProtocol protocol = new TBinaryProtocol(transport);
    this.client = new SendService.Client(protocol);
  }

  /**
   *
   * @param email
   * @return json string
   */
  public String sendEmail(Email email) throws RuntimeException {
    SendRequest req = buildRequest(email);
    try {
      String resp = client.send(req.toString());
      return resp;
    } catch (Exception e) {
      throw new RuntimeException("error in thrift call: " + e.getMessage());
    }

  }

  protected SendRequest buildRequest(Email email) {
    SendRequest ret = new SendRequest();
    ret.email = email.toString();
    Date now = new Date();
    ret.time = rfc2822.format(now);
    ret.sig = signEmail(ret.email, now);
    ret.senderDomain = senderDomain;
    return ret;
  }

  protected String signEmail(String email, Date time) {
    Hashids hid = new Hashids(secret);
    String key = hid.encode(time.getTime() / 1000) + email;
    try {
      byte[] bytesOfMessage = key.getBytes("UTF-8");
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] thedigest = md.digest(bytesOfMessage);
      String hex = new String(Hex.encodeHex(thedigest));

      return hex;
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Error while signing email: " + e.getMessage());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Error while signing email: " + e.getMessage());
    }
  }
}
