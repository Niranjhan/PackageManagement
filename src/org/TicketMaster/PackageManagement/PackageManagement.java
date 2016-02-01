package org.TicketMaster.PackageManagement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
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
		if(!packageMap.containsKey(items[0]))
		{
			packageMap.put(items[0], new Node(items[0]));
		}
		
		Node currentNode = packageMap.get(items[0]);
		
		for(int i=1;i<items.length;i++)
		{
			if(!packageMap.containsKey(items[i]))
			{
			packageMap.put(items[i], new Node(items[i]));
			}
			if(!currentNode.hasDependency(items[i]))
			{
				currentNode.addDependency(items[i]);
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
			if(!installedItems.contains(item))
			{
				installedItems.add(item);
				System.out.println("Installing: "+item);
			}
			else
			{
				System.out.println(item+" found already installed");
			}
			
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
		if(!packageMap.containsKey(name) || !installedItems.contains(name))
		{
			return;
		}
		installedItems.remove(name);
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
		

	    LOOP : 
		while(true)
		{
		System.out.println("Enter your command:");
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		String[] inputarr = input.split("\\s+");  
		if(inputarr==null || inputarr.length == 0)
		{
			System.out.println("Invalid Command");
			continue;
		}
		String command = inputarr[0];
		String[] commandArguments = Arrays.copyOfRange(inputarr, 1, inputarr.length);
	
		switch(command.toLowerCase())
		{
		case "depend" : PM.depend(commandArguments);
		break;
		case "install": PM.install(commandArguments[0]);
		break;
		case  "list": PM.List() ;
		break;
		case "remove" : PM.remove(commandArguments[0]);
		break;
		case "end" : break LOOP ;
		default : System.out.println("Invalid command");
		}	
		}
		
	}


}
