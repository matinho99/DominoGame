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
	public JButton getABone;
	public ArrayList<DBone> bonesInHand;
	public ArrayList<DBone> bonesOnTable;
	public ArrayList<DBone> allBones;
	
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
		initBoneImageArray();
		bonesInHand = new ArrayList<DBone>();
		bonesOnTable = new ArrayList<DBone>();
		JMenuBar menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		hostGame = new JMenuItem("Host Game");
		joinGame = new JMenuItem("Join Game");
		getABone = new JButton("GAME's LOCKED! Get A Bone");
		JFrame frame = new JFrame("DominoGame");
		bonesPanel = new JPanel(new FlowLayout());
		JPanel menuPanel = new JPanel(new GridBagLayout());
		gamePanel = new JPanel(null);
		JScrollPane bonesScrollPane = new JScrollPane(bonesPanel);
		bonesScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		bonesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		JScrollPane gameScrollPane = new JScrollPane(gamePanel);
		gameScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		gameScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		menuBar.add(menu);
		menu.add(hostGame);
		menu.add(joinGame);
		GridBagConstraints c = new GridBagConstraints();
		frame.setSize(1280, 720);
		bonesPanel.setBackground(new Color(128,128,128));
		frame.setLocation(100, 100);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.ipady=66;
		c.gridx = 0;
		c.gridy = 0;
		menuPanel.add(bonesScrollPane, c);
		c.fill = GridBagConstraints.BOTH;
		c.weightx=0.0;
		c.gridwidth=3;
		c.ipady=580;
		c.insets.left = 0;
		c.insets.right=0;
		c.insets.bottom=0;
		c.insets.top=0;
		gamePanel.setBackground(new Color(0,48,15));
		c.gridx = 0;
		c.gridy = 1;
		menuPanel.add(gameScrollPane, c);
		frame.add(menuPanel);
		frame.setJMenuBar(menuBar);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev)
			{
				System.exit(0);
			}
		});
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
					allBones.get(allBones.size()-1).setIcon(new ImageIcon("C:/Users/matinho1706/workspace/DominoGame/src/main/java/edu/mosowiec/domino/view/DBones/"+i+j+".png"));
					allBones.get(allBones.size()-1).setPreferredSize(new Dimension(100, 50));
					allBones.get(allBones.size()-1).p.setLocation(i, j);
			}
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
		System.out.println("Adding " + p);
		DBone tmpBone = null;
		
		for(int i=0; i<allBones.size(); i++)
		{
			tmpBone=allBones.get(i);
			
			if(tmpBone!=null && tmpBone.p.x==p.x && tmpBone.p.y==p.y)
			{
				if(rotate)
				{
					rotateIcon(tmpBone);
				}
				
				bonesOnTable.add(tmpBone);
				gamePanel.add(tmpBone);
				break;
			}
		}
		
		arrangeBonesOnTable();
		gamePanel.revalidate();
		gamePanel.repaint();
	}

	private void arrangeBonesOnTable()
	{
		DBone tmpBone = null;
		
		for(int i=0; i<bonesOnTable.size(); i++)
		{
			tmpBone = bonesOnTable.get(i);
			tmpBone.setLocation(i*100+10, 275);
		}
	}

	private void rotateIcon(DBone tmpBone)
	{
		// TODO Auto-generated method stub
		
	}

	public void update(Observable obs, Object obj)
	{
		bonesPanel.removeAll();
		gamePanel.removeAll();		
		bonesInHand.clear();		
		bonesOnTable.clear();
				
		if(engine.gameLocked())
		{
			bonesPanel.add(getABone);
		}
		
		for(int i=0; i<engine.bonesInHand.size(); i++) {
			addBoneToHand(engine.bonesInHand.get(i).p);
		}
		
		for(int i=0; i<engine.bonesOnTable.size(); i++) {
			boolean rotate = engine.bonesOnTable.get(i).isRotated;
			addBoneToTable(engine.bonesOnTable.get(i).p, rotate);
		}
	}

	public void addController(ActionListener dController) {
		hostGame.addActionListener(dController);
		joinGame.addActionListener(dController);
		getABone.addActionListener(dController);
		
		for(int i=0; i<28; i++)
		{
			allBones.get(i).addActionListener(dController);
		}
	}

	public void addModel(DEngine dEngine) {
		this.engine=dEngine;		
	}
}
