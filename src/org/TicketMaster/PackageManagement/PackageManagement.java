package org.TicketMaster.PackageManagement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class PackageManagement {
	
	private ConcurrentHashMap<String,Node> packageMap;
	private HashSet<String> installedItems;
	
	public PackageManagement()
	{
		packageMap = new ConcurrentHashMap<String,Node>();
		installedItems = new HashSet<String>();
	}


	public void depend(String... items)
	{
		for(int i=0;i<items.length;i++)
		{
			if(!packageMap.containsKey(items[i]))
			{
				if(i>0)
				{
					packageMap.put(items[i], new Node(items[i]));
					Node dependant = packageMap.get(items[i-1]);
					if(!dependant.hasDependency(items[i]))
					{
						dependant.addDependency(items[i]);
					}
				}
				else
				{
					packageMap.put(items[i], new Node(items[i]));
				}
				
			}
			
		}
	}

	public void install(String item)
	{
		if(packageMap.containsKey(item))
		{
			Node currentNode = packageMap.get(item);
			if(currentNode.hasDependency())
			{
				HashSet<String> dependencies = currentNode.getDependencySet();
				for(String dependency : dependencies)
				{
					if(!packageMap.containsKey(dependency))
					{
						System.out.println("Cannot install "+item+" as Dependecyt "+dependency+" is not found");
					}
					install(dependency);
				}
			}
			installedItems.add(item);
			System.out.println("Installing: "+item);
		}
		else
		{
			System.out.println("Cannot install "+item);
		}
	}
	
	public void List()
	{
		for(String item : installedItems)
		{
			System.out.println(item);
		}
	}

	public void remove(String name)
	{
		if(!packageMap.containsKey(name))
		{
			return;
		}
		packageMap.remove(name);
		for(String item : packageMap.keySet())
		{
			if(packageMap.containsKey(item))
			{
				if(packageMap.get(item).getDependencySet().contains(name))
				{
					remove(item);
				}
			}
		}
	}
	
	
	public static void main(String[] args)
	{
		PackageManagement PM = new PackageManagement();
		PM.depend("item1","item2","item3");
	
		PM.install("item1");
		PM.List();
		PM.remove("item3");
		PM.install("item2");
		
	}


}
