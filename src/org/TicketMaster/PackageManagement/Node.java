package org.TicketMaster.PackageManagement;

import java.util.HashSet;

public class Node {
private String name;
private boolean hasDependency;
private HashSet<String> DependencySet;


	public Node()
	{	
	}
	
	public Node(String name)
	{
		this.name = name;
		this.hasDependency = false;
		DependencySet=new HashSet<String>();
	}
	
	public HashSet<String> getDependencySet()
	{
		return new HashSet<String>(DependencySet);
	}
	public boolean addDependency(String dependency)
	{	
		    hasDependency = true;
			DependencySet.add(dependency);
			return true;	
	}
	
	public boolean hasDependency()
	{
		if(hasDependency)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean hasDependency(String dependency)
	{
		if((DependencySet!=null))
		{
			if((DependencySet.contains(dependency)))
				return true;
			else
			{
				return false;
			}
		}		
		else
		{
			return false;
		}
	}
	
	
	public boolean equals(String name) {
		if(this.name.equals(name))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	
	
	public String toString()
	{
		return this.name;
		
	}
	
}
