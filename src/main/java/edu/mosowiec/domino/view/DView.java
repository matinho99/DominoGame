package edu.mosowiec.domino.view;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.awt.*;
import javax.swing.*;

import edu.mosowiec.domino.model.DEngine;

public class DView implements java.util.Observer
{	
	private DEngine engine;
	private JPanel bonesPanel;
	private JPanel gamePanel;
	private JMenu menu;
	public JMenuItem hostGame, joinGame;
	public JButton getABone, remainingBonesCounter, gameCompletelyBlocked, gameFinished, choiceNeeded;
	public ArrayList<DBone> bonesInHand;
	public ArrayList<DBone> bonesOnTable;
	public ArrayList<DBone> allBones;
	public DBone firstPutOnTable, needingChoice;
	
	public class DBone extends JButton
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public Point p;
		boolean isRotated;
		
		public DBone() {
			p = new Point();
			isRotated=false;
		}
	}
		
	public DView()
	{
		bonesInHand = new ArrayList<DBone>();
		bonesOnTable = new ArrayList<DBone>();
		JMenuBar menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		hostGame = new JMenuItem("Host Game");
		joinGame = new JMenuItem("Join Game");
		getABone = new JButton("GAME's BLOCKED! Get A Bone");
		JFrame frame = new JFrame("DominoGame");
		bonesPanel = new JPanel(new FlowLayout());
		JPanel menuPanel = new JPanel(new GridBagLayout());
		gamePanel = new JPanel(null);
		JScrollPane bonesScrollPane = new JScrollPane(bonesPanel);
		JScrollPane gameScrollPane = new JScrollPane(gamePanel);
		GridBagConstraints c = new GridBagConstraints();
		remainingBonesCounter=new JButton();
		gameFinished=new JButton("GAME OVER! YOU'VE WON");
		choiceNeeded=new JButton();
		gameCompletelyBlocked=new JButton("GAME IS COMPLETELY BLOCKED! GAME OVER!");
		remainingBonesCounter.setPreferredSize(new Dimension(300, 20));
		initBoneImageArray();
		firstPutOnTable=null;
		needingChoice=null;
		bonesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		bonesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);		
		gameScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		gameScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		menuBar.add(menu);
		menu.add(hostGame);
		menu.add(joinGame);
		bonesPanel.setBackground(new Color(128,128,128));
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.ipady=66;
		c.gridx = 0;
		c.gridy = 0;
		menuPanel.add(bonesScrollPane, c);
		c.fill = GridBagConstraints.BOTH;
		c.weightx=0.0;
		c.gridwidth=3;
		c.ipady=585;
		c.insets.left = 0;
		c.insets.right=0;
		c.insets.bottom=0;
		c.insets.top=0;
		gamePanel.setBackground(new Color(0,48,15));
		c.gridx = 0;
		c.gridy = 1;
		menuPanel.add(gamePanel, c);
		frame.setLocation(100, 100);
		frame.add(menuPanel);
		frame.setJMenuBar(menuBar);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev)
			{
				System.exit(0);
			}
		});
		frame.setSize(1280, 720);
		frame.setResizable(false);
		frame.setVisible(true);		
	}
	
	public void initBoneImageArray()
	{
		allBones = new ArrayList<DBone>();
		for(int i=0; i<7; i++) 
		{
			for(int j=i; j<7; j++) 
			{
					allBones.add(new DBone());
					allBones.get(allBones.size()-1).p.setLocation(i, j);
			}
		}
		
		setAllBonesDefault();
	}
	
	public void setAllBonesDefault()
	{
		DBone tmp = null;
		
		for(int i=0; i<allBones.size(); i++) 
		{
			tmp = allBones.get(i);
			tmp.setIcon(new ImageIcon("C:/Users/matinho1706/workspace/DominoGame"
					+ "/src/main/java/edu/mosowiec/domino/view/DBones/"+tmp.p.x+tmp.p.y+".png"));
			tmp.setPreferredSize(new Dimension(100, 50));
		}
	}
	
	public void addBoneToHand(Point p)
	{		
		DBone tmpBone = null;
		
		for(int i=0; i<allBones.size(); i++)
		{
			tmpBone = allBones.get(i);
			
			if(tmpBone != null && tmpBone.p.x==p.x && tmpBone.p.y==p.y)
			{
				bonesInHand.add(tmpBone);
				bonesPanel.add(tmpBone);
				break;
			}
		}
		
		bonesPanel.revalidate();
		bonesPanel.repaint();
	}
	
	public void addBoneToTable(Point p, boolean rotate)
	{
		//System.out.println("Adding " + p);
		DBone tmpBone = null;
		
		for(int i=0; i<allBones.size(); i++)
		{
			tmpBone=allBones.get(i);
			
			if(tmpBone!=null && tmpBone.p.x==p.x && tmpBone.p.y==p.y)
			{
				tmpBone.isRotated=rotate;
				bonesOnTable.add(tmpBone);
				gamePanel.add(tmpBone);				
				break;
			}
		}
		
		gamePanel.revalidate();
		gamePanel.repaint();
	}

	private void arrangeBonesOnTable()
	{
		DBone tmpBone = null;
		int firstPutIndex = bonesOnTable.indexOf(firstPutOnTable);
		Point beg = new Point(590, 250);
		Point end = new Point(590, 250);
		boolean endToRight = true;
		boolean begToLeft = true;
		
		if(firstPutOnTable!=null)
		{
			firstPutOnTable.setLocation(590, 250);
		}
		
		if(firstPutIndex>0)
		{
			for(int i=firstPutIndex-1; i>-1; i--)
			{
				tmpBone = bonesOnTable.get(i);
				
				if(begToLeft && beg.x>=100)
				{
					if(tmpBone.isRotated)
					{
						rotateDBone(tmpBone, 2);
					}
					
					beg.x=beg.x-100;
					tmpBone.setLocation(beg.x, beg.y);
				}
				else if(begToLeft && beg.x<100)
				{
					if(!tmpBone.isRotated)
					{
						rotateDBone(tmpBone, 1);
					}
					else if(tmpBone.isRotated)
					{
						rotateDBone(tmpBone, 3);
					}

					begToLeft=false;
					beg.y=beg.y+50;
					tmpBone.setLocation(beg.x, beg.y);					
					beg.x=beg.x-50;
					beg.y=beg.y+50;
				}
				else if(!begToLeft && 1180-beg.x>=100)
				{
					if(!tmpBone.isRotated && tmpBone.p.x!=tmpBone.p.y)
					{
						rotateDBone(tmpBone, 2);
					}
					
					beg.x=beg.x+100;					
					tmpBone.setLocation(beg.x, beg.y);
				}
				else if(!begToLeft && 1180-beg.x<100)
				{
					rotateDBone(tmpBone, 3);
					begToLeft=true;
					beg.y=beg.y+50;
					beg.x=beg.x+50;
					tmpBone.setLocation(beg.x, beg.y);
					beg.y=beg.y+50;
				}
			}
		}
		
		if(firstPutIndex<bonesOnTable.size()-1)
		{
			for(int i=firstPutIndex+1; i<bonesOnTable.size(); i++)
			{
				tmpBone = bonesOnTable.get(i);
				
				if(endToRight && 1180-end.x>=100)
				{
					if(tmpBone.isRotated)
					{
						rotateDBone(tmpBone, 2);
					}
					
					end.x=end.x+100;
					tmpBone.setLocation(end.x, end.y);
				}
				else if(endToRight && 1180-end.x<100)
				{
					if(!tmpBone.isRotated)
					{
						rotateDBone(tmpBone, 1);
					}
					else if(tmpBone.isRotated)
					{
						rotateDBone(tmpBone, 3);
					}
					
					endToRight=false;
					end.y=end.y-100;
					end.x=end.x+50;
					tmpBone.setLocation(end.x, end.y);
				}
				else if(!endToRight && end.x>=100)
				{
					if(!tmpBone.isRotated && tmpBone.p.x!=tmpBone.p.y)
					{
						rotateDBone(tmpBone, 2);
					}
					
					end.x=end.x-100;					
					tmpBone.setLocation(end.x, end.y);
				}
				else if(!endToRight && end.x<100)
				{
					rotateDBone(tmpBone, 1);
					endToRight=true;
					end.y=end.y-100;
					tmpBone.setLocation(end.x, end.y);
					end.x=end.x-50;
				}
			}
		}
	}

	private void rotateDBone(DBone tmp, int rotType)
	{		
		System.out.println("Rotating.. "+tmp.p);
		
		if(tmp.p.x!=tmp.p.y)
		{
			if(rotType==1)
			{
				tmp.setIcon(null);
				tmp.setIcon(new ImageIcon("C:/Users/matinho1706/workspace/DominoGame"
						+ "/src/main/java/edu/mosowiec/domino/view/DBones/Left/"+tmp.p.x+tmp.p.y+"_L.png"));
				tmp.setSize(50, 100);
			}
			else if(rotType==2)
			{
				tmp.setIcon(null);
				tmp.setIcon(new ImageIcon("C:/Users/matinho1706/workspace/DominoGame"
						+ "/src/main/java/edu/mosowiec/domino/view/DBones/Down/"+tmp.p.x+tmp.p.y+"_Down.png"));
				tmp.setSize(100, 50);
			}
			else if(rotType==3)
			{
				tmp.setIcon(null);
				tmp.setIcon(new ImageIcon("C:/Users/matinho1706/workspace/DominoGame"
						+ "/src/main/java/edu/mosowiec/domino/view/DBones/Right/"+tmp.p.x+tmp.p.y+"_R.png"));
				tmp.setSize(50, 100);
			}
		}
		else if(tmp.p.x==tmp.p.y && ( rotType==1 || rotType==3 ) )
		{
			tmp.setIcon(null);
			tmp.setIcon(new ImageIcon("C:/Users/matinho1706/workspace/DominoGame"
					+ "/src/main/java/edu/mosowiec/domino/view/DBones/"+tmp.p.x+tmp.p.y+"R.png"));
			tmp.setSize(50, 100);			
		}
	}

	public void update(Observable obs, Object obj)
	{
		bonesPanel.removeAll();
		gamePanel.removeAll();		
		bonesInHand.clear();		
		bonesOnTable.clear();
		
		for(int i=0; i<engine.bonesInHand.size(); i++)
		{
			addBoneToHand(engine.bonesInHand.get(i).p);
		}
		
		for(int i=0; i<engine.bonesOnTable.size(); i++)
		{			
			boolean rotate = engine.bonesOnTable.get(i).isRotated;
			addBoneToTable(engine.bonesOnTable.get(i).p, rotate);
			
			if(firstPutOnTable==null && bonesOnTable.size()==1)
			{
				firstPutOnTable = bonesOnTable.get(0);
			}
		}
		
		if(engine.isGameFinished)
		{
			bonesPanel.removeAll();
			gamePanel.add(gameFinished);
			gameFinished.setLocation(450, 205);
			gameFinished.setSize(200, 40);
		}
		else if(engine.gameLocked() && engine.allBones.size()==0)
		{
			bonesPanel.removeAll();
			gamePanel.add(gameCompletelyBlocked);
			gameCompletelyBlocked.setLocation(400, 205);
			gameCompletelyBlocked.setSize(300, 40);
		}
		else if(engine.gameLocked())
		{
			gamePanel.add(getABone);
			getABone.setLocation(10, 35);
			getABone.setSize(200, 30);
		}
		else if(engine.choiceNeeded)
		{
			gamePanel.add(choiceNeeded);
			choiceNeeded.setText("Press the end on which you want to place chosen bone");
			choiceNeeded.setLocation(400, 205);
			choiceNeeded.setSize(200, 40);
		}
		
		gamePanel.add(remainingBonesCounter);
		remainingBonesCounter.setText("Remaining bones: "+engine.allBones.size());
		remainingBonesCounter.setLocation(10, 10);
		remainingBonesCounter.setSize(200, 20);
		
		setAllBonesDefault();
		arrangeBonesOnTable();
	}

	public void addController(ActionListener dController)
	{
		hostGame.addActionListener(dController);
		joinGame.addActionListener(dController);
		getABone.addActionListener(dController);
		
		for(int i=0; i<allBones.size(); i++)
		{
			allBones.get(i).addActionListener(dController);
		}
	}

	public void addModel(DEngine dEngine)
	{
		this.engine=dEngine;		
	}
}
