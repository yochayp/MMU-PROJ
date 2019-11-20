package com.hit.serverAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.algorithm.RandomAlgoCacheImpl;
import com.hit.algorithm.SecondChance;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;



public class Controller<T> {
	
	private CacheUnit<T> cache;
	private IDao<Long,DataModel<T>> idao;
	private int capacity;
	public Controller() {
		
		//reads from (System.in) cache unit configuration - cache unit paging algorithm and capacity
	
		Scanner scn = new Scanner(System.in);
		System.out.println("enter cache paging algo (LRU/Random/SecondChance)");
		String algo = scn.nextLine();
		System.out.println("enter capacity of cache (int number)");
		int capacity = scn.nextInt();
		
		// set cache unit,paging algorithm and capacity configuration
		
		switch(algo)
		{
		case "LRU": this.cache = new CacheUnit(new LRUAlgoCacheImpl<Long,String>(3),capacity);
		case "Random" :this.cache = new CacheUnit(new RandomAlgoCacheImpl<Long,String>(3),capacity);
		case "SecondChance" :this.cache = new CacheUnit(new SecondChance<Long,String>(3),capacity);
		}
		
		// member idao communicate with database (in our case "new.txt" file)
		
		this.idao = new DaoFileImpl<>("src//main//resources//new.txt");
		 
	}
	
	public CacheUnit<T> getCache() {
		return cache;
	}

	public void setCache(CacheUnit<T> cache) {
		this.cache = cache;
	}

	public IDao<Long, DataModel<T>> getIdao() {
		return idao;
	}

	public void setIdao(IDao<Long, DataModel<T>> idao) {
		this.idao = idao;
	}

	public void create (DataModel<T>[] datamodels)
	{
		
		cache.putDataModels(datamodels);
		for(DataModel<T> datamodel : datamodels)
			try {
				this.idao.save(datamodel);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}

	public DataModel<T>[] read (Long[] ids)
	{
		int length;
		
		DataModel<T>[] dataModelsFromCache = cache.getDataModels(ids); // read's all data models you find in cache 
		
		HashMap<Long, DataModel<T>> dataModelsToReturn = new HashMap<Long, DataModel<T>>();
		if(dataModelsFromCache[0]!=null) {
		for(DataModel<T> datamodel : dataModelsFromCache)   
		{
			// convert data models found in cache , from array to hash map        
		if(datamodel!=null)
			dataModelsToReturn.put(datamodel.getDataModelId(), datamodel);
		}}
		
		for(Long id : ids)                                             // iterate through id's, if data model not found in cache find it in idao(database)
			if(!dataModelsToReturn.containsKey(id))
			{
				DataModel<T>  data= idao.find(id);
                
				if(data != null)                                        //  if data model found in idao(database), put it on  hash map 
					dataModelsToReturn.put(id, idao.find(id));
			}
		
		length = dataModelsToReturn.size();
		
		DataModel<T>[] datamodels= new DataModel[length];
		
		int i = 0;
		
		for(Long id : dataModelsToReturn.keySet() )                      // convert hash map to array
		{
			datamodels[i] = dataModelsToReturn.get(id);
			i++;
		}
		
		return datamodels;
	}
	
	public DataModel<T>[] update (DataModel<T>[] datamodels)
	{
		DataModel<T>[] updateNotFoundIds = new DataModel[datamodels.length];
		Long[] ids = new Long[datamodels.length];
		
		for (int i = 0;i<datamodels.length;i++)
		{
			ids[i] = datamodels[i].getDataModelId();
		}
		
		cache.removeDataModels(ids);
		cache.putDataModels(datamodels);
		
		
		for (int i = 0;i<datamodels.length;i++)
			
		if (idao.find(ids[i]) != null)
			idao.delete(datamodels[i]);
		else
			updateNotFoundIds[i] =  datamodels[i];
		return updateNotFoundIds;
		}
	
	public DataModel<T>[] delete (Long[] ids)
	{
		DataModel<T>[] deleteNotFoundIds = new DataModel[ids.length];
		DataModel<T> datamodel;
		cache.removeDataModels(ids);
		
		for(int i=0; i<ids.length;i++)
		
		{
			datamodel = idao.find(ids[i]);
			if(datamodel != null)
				idao.delete(datamodel);
			else
				deleteNotFoundIds[i] = new DataModel(ids[i],"");
		}
		
		return deleteNotFoundIds;
	}
}
