package com.hit.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

public interface IDao <ID extends Serializable,T> 
{
	void save(T entity) throws FileNotFoundException, IOException;
	void delete(T entity);
	T find(ID id);
}
