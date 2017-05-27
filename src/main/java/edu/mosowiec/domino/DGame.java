package edu.mosowiec.domino;

import edu.mosowiec.domino.controller.DController;
import edu.mosowiec.domino.model.DClient;
import edu.mosowiec.domino.model.DEngine;
import edu.mosowiec.domino.view.DView;

public class DGame 
{
	public DGame()
	{
		DEngine dEngine = new DEngine();
		DView dView = new DView();
		DClient dClient = new DClient();
		DController dController = new DController();
		//dEngine.addObserver(dClient);
		dEngine.addObserver(dView);
		dClient.addObserver(dView);
		dController.addModel(dEngine);
		dController.addView(dView);
		dView.addController(dController);
		dView.addModel(dEngine);
	}
	
	public void run()
	{
		System.out.println("It is on!");
	}
	
    public static void main( String[] args )
    {
        DGame dGame = new DGame();
        dGame.run();
    }
}
