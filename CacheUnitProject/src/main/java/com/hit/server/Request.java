package com.hit.server;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class Request<T> implements Serializable
{
	private String action;
	private T body;
	
	public Request(String action, T body) {
		super();
		this.action = action;
		this.body = body;
	}
	
	public Request() {
		// TODO Auto-generated constructor stub
	}

	public T getBody() {
		return body;
	}

	public String getAction() {
		return action;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public void setAction(String action) {
		this.action = action;
	}

    @Override
    public String toString(){
       return "Action:" + action + "content:" + body.toString();
    }
	
   
}
