package com.hit.memory;

import org.junit.Test;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;



public class CacheUnitTest {
	
	@Test
	public void testCacheUnit()
	{
		
		IDao<Long, DataModel<String>> testDao = new DaoFileImpl<>("src\\main\\resources\\new.txt");
		IAlgoCache<Long, DataModel<String>> algo = new LRUAlgoCacheImpl<>(3);
	
		
	}
	
	


}
