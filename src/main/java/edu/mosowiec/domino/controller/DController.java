package edu.mosowiec.domino.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.mosowiec.domino.model.DEngine;
import edu.mosowiec.domino.view.DView;

public class DController implements ActionListener {
	DEngine engine;
	DView view;
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == view.hostGame)
        {
            System.out.println("You pressed hostGame.");
            engine.initGame();
        }
		else if (e.getSource() == view.joinGame)
        {
        	System.out.println("You pressed joinGame.");
        }
		else if (e.getSource() == view.getABone)
		{
			System.out.println("You pressed getABone.");
			engine.getABone();
		}
		else
		{
			for(int i=0; i<view.bonesInHand.size(); i++)
			{
				if(e.getSource()==view.bonesInHand.get(i))
				{
					System.out.println("You pressed Bone " + view.bonesInHand.get(i).p);
					engine.putBoneOnTable(view.bonesInHand.get(i).p);
					break;
				}
			}
		}
	}

	public void addModel(DEngine dEngine) {
		this.engine=dEngine;
	}

	public void addView(DView dView) {
		this.view=dView;
	}

}
