package com.hit.memory;

import com.hit.server.*;
public interface Observable {
	
	public void register(Observer o);
	public void unregister(Observer o);
	public void notifyObservers();
		
}
