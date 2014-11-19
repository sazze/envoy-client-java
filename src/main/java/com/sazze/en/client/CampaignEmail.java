package com.sazze.en.client;

import com.google.gson.Gson;

/**
 * @author Janghwan Lee <jlee@sazze.com>
 *         Sazze, Inc. 2014
 * @since 11/17/14 8:40 PM
 */
public class CampaignEmail extends Email{
  public CampaignEmail(String json) {
    Gson gson = new Gson();
    Email email = gson.fromJson(json, Email.class);
    this.from = email.from;
    this.to = email.to;
    this.headers = email.headers;
    this.html = email.html;
    this.replyTo = email.replyTo;
    this.subject = email.subject;
    this.text = email.text;
    this.to = email.to;
    this.headers.put("X-Envoy-Type", 3);
  }

  public CampaignEmail() {
    this.headers.put("X-Envoy-Type", 3);
  }
}
