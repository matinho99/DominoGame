package edu.mosowiec.domino.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class DEngine extends java.util.Observable {
	public ArrayList<DBone> allBones;
	public ArrayList<DBone> remainingBones;
	public ArrayList<DBone> bonesInHand;
	public ArrayList<DBone> bonesOnTable;
	boolean isGameInitialized;
	
	public class DBone {
		public Point p;
		public boolean isRotated;
		
		DBone()
		{
			p=new Point();
			isRotated=false;
		}
		
		DBone(int x, int y)
		{
			p=new Point(x, y);
			isRotated=false;
		}
	}
	
	public DEngine()
	{
		isGameInitialized=false;
		allBones = new ArrayList<DBone>();
		for(int i=0; i<7; i++)
		{
			for(int j=i; j<7; j++)
			{
				allBones.add(new DBone(i, j));
			}
		}
		bonesInHand = new ArrayList<DBone>();
		bonesOnTable = new ArrayList<DBone>();
	}

	public void initGame()
	{
		if(isGameInitialized==true) return;
		Random gen = new Random();
		DBone bone = null;
		
		for(int i=0; i<7; i++)
		{
			bone = allBones.get(gen.nextInt(allBones.size()));
			bonesInHand.add(bone);
			allBones.remove(bone);
		}
		
		setChanged();
		notifyObservers();
		isGameInitialized=true;
	}
	
	public void getABone()
	{
		Random gen = new Random();
		DBone bone = null;
		bone = allBones.get(gen.nextInt(allBones.size()));
		bonesInHand.add(bone);
		allBones.remove(bone);
		setChanged();
		notifyObservers();
	}
	
	public void putBoneOnTable(Point p)
	{
		DBone tmp = null;
		DBone first = null;
		DBone last = null;
		
		if(bonesOnTable.size()>0)
		{
			first = bonesOnTable.get(0);
			last = bonesOnTable.get(bonesOnTable.size()-1);
		}
		
		for(int i=0; i<bonesInHand.size(); i++)
		{
			tmp = bonesInHand.get(i);
			
			if(tmp!=null && tmp.p.x==p.x && tmp.p.y==p.y)
			{
				break;
			}
		}
		
		if(bonesOnTable.size()==0)
		{
			System.out.println("Adding to empty table");
			bonesOnTable.add(tmp);
			bonesInHand.remove(tmp);			
		}
		else if ( ( tmp.p.y==first.p.x && first.isRotated==false ) || ( tmp.p.y==first.p.y && first.isRotated==true ) )
		{
			System.out.println("Adding as first without rotating");
			bonesOnTable.add(0, tmp);
			bonesInHand.remove(tmp);
		}
		else if ( ( tmp.p.x==first.p.x && first.isRotated==false ) || ( tmp.p.x==first.p.y && first.isRotated==true ) )
		{
			System.out.println("Adding as first and rotating");
			bonesOnTable.add(0, tmp);
			tmp.isRotated=true;
			bonesInHand.remove(tmp);
		}
		else if ( ( tmp.p.x==last.p.y && last.isRotated==false ) || ( tmp.p.x==last.p.x && last.isRotated==true ) )
		{
			System.out.println("Adding as last without rotating");
			bonesOnTable.add(bonesOnTable.size(), tmp);
			bonesInHand.remove(tmp);
		}
		else if ( ( tmp.p.y==last.p.y && last.isRotated==false ) || ( tmp.p.y==last.p.x && last.isRotated==true ) )
		{
			System.out.println("Adding as last and rotating");
			bonesOnTable.add(bonesOnTable.size(), tmp);
			tmp.isRotated=true;
			bonesInHand.remove(tmp);
		}
		
		setChanged();
		notifyObservers();
	}
	
	public boolean gameLocked()
	{
		boolean result = true;
		DBone tmp = null;
		
		if(bonesOnTable.size()==0)
		{
			result=false;
		}
		else
		{
			DBone first = bonesOnTable.get(0);
			DBone last = bonesOnTable.get(bonesOnTable.size()-1);
			
			for(int i=0; i<bonesInHand.size(); i++)
			{
				tmp=bonesInHand.get(i);
				if ( ( tmp.p.y==first.p.x && first.isRotated==false ) || ( tmp.p.y==first.p.y && first.isRotated==true )
						|| ( tmp.p.x==first.p.x && first.isRotated==false ) || ( tmp.p.x==first.p.y && first.isRotated==true )
						|| ( tmp.p.x==last.p.y && last.isRotated==false ) || ( tmp.p.x==last.p.x && last.isRotated==true )
						|| ( tmp.p.y==last.p.y && last.isRotated==false ) || ( tmp.p.y==last.p.x && last.isRotated==true ) )
				{
					result = false;
					break;
				}
			}
		}
		
		return result;
	}
}
