package com.sazze.en.client;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Janghwan Lee <jlee@sazze.com>
 *         Sazze, Inc. 2014
 * @since 11/17/14 4:26 PM
 */
public class Email {
  public List<String> to = new LinkedList<String>();
  public String from = "";
  public String replyTo = "";
  public String subject = "";
  public String html = "";
  public String text = "";
  public Map<String, Object> headers = new HashMap<String, Object>();

  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
