package com.sazze.en.client;

import com.google.gson.Gson;

/**
 * @author Janghwan Lee <jlee@sazze.com>
 *         Sazze, Inc. 2014
 * @since 11/17/14 4:29 PM
 */
public class SendRequest {
  public String sig = "";
  public String email = "";
  public String time = "";
  public String senderDomain = "";

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
