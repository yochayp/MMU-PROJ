package com.hit.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import com.hit.dm.DataModel;

public class DaoFileImpl<T> implements IDao<Long,DataModel<T>>


{
	private String filePath;
	private HashMap<Long,DataModel<T>> daoCache;
	
	public DaoFileImpl(String filePath,int capacity)
	{
		this.filePath = filePath;
		this.daoCache = new HashMap<>(capacity);
		try {
			firstLoad();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DaoFileImpl(String filePath)
	{
		this.filePath = filePath;
		this.daoCache = new HashMap<>();
		try {
			firstLoad();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void firstLoad () throws FileNotFoundException, IOException, ClassNotFoundException
	{
		DataModel<T> inDataModel = null;
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src//main//resources//new.txt"));
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("src//main//resources//new.txt"));
		if(in.available()!=0)
		   inDataModel = (DataModel<T>) in.readObject();
		while(inDataModel != null)
			daoCache.put(inDataModel.getDataModelId(), inDataModel);
		
		out.close();
		in.close();
		
			
	}
	
	
	private void update() throws IOException
	{
		FileOutputStream outputStream = new FileOutputStream(this.filePath);
		ObjectOutputStream out= new ObjectOutputStream(outputStream);
		out.writeObject(this.daoCache);
		out.flush();
		out.close();
		outputStream.close();
	}
	
	@Override
	public void save(DataModel<T> entity) throws FileNotFoundException, IOException {
		
		
        
		if(daoCache.containsKey(entity.getDataModelId())) // the data model is already exist in file...we need to update it 
		{
            this.daoCache.remove(entity.getDataModelId());
            this.daoCache.put(entity.getDataModelId(), entity);
            update();
		}
		else // the data model not exist in file , we need to save it
		{
			ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream("src//main//resources//new.txt"));
			this.daoCache.put(entity.getDataModelId(), entity);
			out.writeObject(entity);
			out.flush();
			out.close();
		}
		
	}

	@Override
	public void delete(DataModel<T> entity) 
	{
		if (entity==null) 
			throw new IllegalArgumentException("Illegal argument.");
		else
			{
				this.daoCache.remove(entity.getDataModelId());
				}
			try {
				update();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	

	@Override
	public DataModel<T> find(Long id) throws IllegalArgumentException 
	{
		if (id == null) 
			throw new IllegalArgumentException("Illegal argument.");
		else 
			if (this.daoCache.containsKey(id))
				return this.daoCache.get(id);
		return null;
	}
	
	

}
