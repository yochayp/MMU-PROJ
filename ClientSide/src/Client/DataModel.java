package Client;

import java.io.Serializable;

public class DataModel <T> implements Serializable{
	
	private Long pageId;
	private T content;
	
	public DataModel(Long id, T content)
	{
		super();
		this.pageId = id;
		this.content = content;
	}
	
	public void setDataModelId(Long id)
	{
		this.pageId = id;
	}
	
	public void setContent(T content)
	{
		this.content = content;
	}

	public Long getDataModelId()
	{
		return this.pageId;
	}
	
	public T getContent()
	{
		return this.content;
	}
	public int hashCode()//By default, this method returns a random integer that is unique for each instance.As per the Java documentation, developers should override both methods in order to achieve a fully working equality mechanism — it's not enough to just implement the equals() method.
	{
		return this.pageId.intValue();
	}
	public boolean equals(Object obj)//  two objects are equal if and only if they are stored in the same memory address.
	{
		if (this.hashCode()==obj.hashCode())
			return true;
		return false;
	}
	public String toString()
	{
		return this.pageId +"@" + this.content;
	}


}