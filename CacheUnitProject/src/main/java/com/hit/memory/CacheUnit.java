package com.hit.memory;

import java.util.ArrayList;


import com.hit.algorithm.IAlgoCache;
import com.hit.dm.DataModel;

import javafx.beans.InvalidationListener;
import com.hit.server.*;

public class CacheUnit <T> implements Observable{

	IAlgoCache<Long, DataModel<T>>  algo;
	
	// ArrayList to keep all the observers
    private ArrayList<Observer> clients;
    
    // members to update all the observers
	private int capacity;
	private int available;
	private int numOfGetActions;
	private int numOfPutAndRemove;
	
	public CacheUnit(IAlgoCache<Long,DataModel<T>> algo,int capacity)
	{
		this.algo = algo;
				
		this.capacity = capacity;
		this.available = capacity;
		this.numOfGetActions = 0;
		this.numOfPutAndRemove = 0;
		this.clients =  new ArrayList<Observer>(); 

	}
	
	public DataModel<T>[] getDataModels(Long[] ids) 
	{
		DataModel<T>[] returnedArray= new DataModel[ids.length];
		
		for(int i = 0; i<ids.length ; i++)
		{
			returnedArray[i] = this.algo.getElement(ids[i]);
			setNumOfGetActions(this.numOfGetActions+1);
		}
		
		return returnedArray;
	}
	
	public DataModel<T>[] putDataModels(DataModel<T>[] datamodels)
	{
		DataModel<T>[] replacedPages = new DataModel[datamodels.length];
		
		for(int i = 0; i<datamodels.length ; i++)
		{
			//Checks if data model already exist in cache memory
			boolean exist = true;
			if( this.algo.getElement(datamodels[i].getDataModelId())==null)
				exist = false;
				
			// put data model in cache unit, if paging happened return the replaced page
			
			replacedPages[i] = this.algo.putElement(datamodels[i].getDataModelId(),datamodels[i]);
			
			if(replacedPages[i]!=null)
			
				// memory is full, paging happened
				setNumOfPutAndRemove(this.numOfPutAndRemove+1);
			
			else
			{
				// no paging happened, there is still memory available/the data model to put in cache already exist in cache, updates the the member available 
				if(!exist)setAvailable(this.available-1);
			}
						
		}
		
		return replacedPages;
	}
	
	public void removeDataModels(Long[] ids)
	{
		
		for(int i = 0; i<ids.length ; i++)
		{
			//Checks if data model already exist in cache memory

			if( this.algo.getElement(ids[i])!=null)
			{
				this.algo.removeElement(ids[i]);
				setAvailable(this.available+1);
			}
		}
	}

	

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
		 notifyObservers();
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
		 notifyObservers();
	}

	public int getNumOfGetActions() {
		return numOfGetActions;
	}

	public void setNumOfGetActions(int numOfGetActions) {
		this.numOfGetActions = numOfGetActions;
		 notifyObservers();
	}

	public int getNumOfPutAndRemove() {
		return numOfPutAndRemove;
	}

	public void setNumOfPutAndRemove(int numOfPutAndRemove) {
		this.numOfPutAndRemove = numOfPutAndRemove;
		 notifyObservers();
	}

	@Override
	public void notifyObservers() {
		
		for(Observer observer : clients)
			observer.update(this.capacity,this.available,this.numOfGetActions,this.numOfPutAndRemove);
		
	}

	@Override
	public void register(Observer o) {
		
		this.clients.add(o);
		
	}

	@Override
	public void unregister(Observer o) {
		this.clients.remove(o);
	}
	
	
}

