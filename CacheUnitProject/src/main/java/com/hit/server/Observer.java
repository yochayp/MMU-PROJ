package com.hit.server;

public interface Observer {
	

	public void update(int capacity, int available, int numOfGetActions, int numOfPutAndRemove);

}
