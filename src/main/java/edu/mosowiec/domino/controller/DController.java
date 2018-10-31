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
		else if ( engine.choiceNeeded 
				&& ( e.getSource() == view.bonesOnTable.get(0) || e.getSource() == view.bonesOnTable.get(view.bonesOnTable.size()-1) ) )
		{
			System.out.println("You made a choice.");
			
			if(e.getSource() == view.bonesOnTable.get(0))
			{
				System.out.println(".. to beginning");
				engine.putBoneOnTable(view.needingChoice.p, 1);
			}
			else if(e.getSource() == view.bonesOnTable.get(view.bonesOnTable.size()-1))
			{
				System.out.println(".. to end");
				engine.putBoneOnTable(view.needingChoice.p, 2);
			}
			
			engine.choiceNeeded=false;
			view.needingChoice=null;
		}
		else if(!engine.choiceNeeded)
		{
			for(int i=0; i<view.bonesInHand.size(); i++)
			{
				if(e.getSource()==view.bonesInHand.get(i))
				{
					System.out.println("You pressed Bone " + view.bonesInHand.get(i).p);
					engine.putBoneOnTable(view.bonesInHand.get(i).p, 0);
					
					if(engine.choiceNeeded)
					{
						view.needingChoice=view.bonesInHand.get(i);
					}
					
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
