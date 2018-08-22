package simulation_main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import ppsim_additions.Simulation_Settings;
import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal.continuous.ContinuousPortrayal2D;

public class Mason_Sim_Main_GUI extends GUIState {

	public JFrame displayFrame;
	ContinuousPortrayal2D tractPortrayal = new ContinuousPortrayal2D();
	
	public void start()
	{
		super.start();
		setupPortrayals();
	}
	public void load(SimState state)
	{
		super.load(state);
		setupPortrayals();
	}
	public void setupPortrayals()
	{
		// tell the portrayals what to portray and how to portray them
		tractPortrayal.setField( Mason_Sim_Main.sim_env.tract );
		//yardPortrayal.setPortrayalForAll(new OvalPortrayal2D());
		// reschedule the displayer
		Mason_Sim_Main.display.reset();
		//PPsim_robocalc_main.display.setBackdrop(Color.black);
		// redraw the display
		Mason_Sim_Main.display.repaint();
		
	}
	public void init(Controller c)
	{
		super.init(c);
		
		if(Mason_Sim_Main.simParams == null)
		{
			//XMLFileUtilities.readSettingsFile(PPsim_robocalc_main.paramFilePath);
			//PPsim_robocalc_main.simParams = new system_parameters();
			// Now using a singleton to get the parameters from the XML file
			Mason_Sim_Main.simParams = Simulation_Settings.getInstance();
		}
		
		// Testing box: // DELETE THIS LATER
		//ppsim.simParams.initialGridHeight=50;
		//ppsim.simParams.initialGridLength=50;
		
		// Actual:
		// Set up the display to the dimensions the user entered
		Mason_Sim_Main.display = new Display2D(Mason_Sim_Main.simParams.initialGridLength,Mason_Sim_Main.simParams.initialGridHeight,this);
		//Simulation_Main.display = new Display2D(700,254,this);
		
		// create the display frame to hold it
		displayFrame = Mason_Sim_Main.display.createFrame();
	
				
		// Set the display frame to be the width of the users screen, plus around 400 (so as much of intestine as possible is shown)
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		displayFrame.setSize(dim.width, 200);
		c.registerFrame(displayFrame);

		// attach all representations of the environment & cells
		Mason_Sim_Main.display.attach(tractPortrayal,"Agents");
		Mason_Sim_Main.display.setBackdrop(Color.black);
		//ppsim.display.setBackdrop(Color.DARK_GRAY);
		displayFrame.setTitle("Intestine Tract Display");
		
		displayFrame.setVisible(true);
		
	}
	
	public void quit()
	{
		super.quit();
		if (displayFrame!=null) displayFrame.dispose();
		displayFrame = null;
		Mason_Sim_Main.display = null;
	}
	
	public static void main(String[] args)
	{
		//String paramFilePath_In = "/home/kja505/Dropbox/RoboCalc/LHC_Analysis/LHC/paramFile_1.xml";
		String paramFilePath_In = "/home/kja505/Dropbox/RoboCalc/parameters-master.xml";
		String runDescription = "robocalc";
		String filepath = "/home/kja505/Desktop/";
		
		// Testing seed of 21
		Mason_Sim_Main new_sim = new Mason_Sim_Main(21,paramFilePath_In, runDescription, filepath);
		//Simulation_Main new_sim = new Simulation_Main(System.currentTimeMillis(),paramFilePath_In, runDescription, filepath);
		
		Mason_Sim_Main_GUI vid = new  Mason_Sim_Main_GUI(paramFilePath_In, runDescription, filepath, new_sim);
		Console c = new Console(vid);
		c.setVisible(true);
		
	}
	
	public Mason_Sim_Main_GUI(String paramFilePath_In, String runDescription, String filepath, Mason_Sim_Main new_sim)
	{
		super(new_sim);
	}
	public Mason_Sim_Main_GUI(SimState state)
	{
		super(state);
	}
	
	public static String getName()
	{
		return "PPSim Robocalc";
	}
	
	public Object getSimulationInspectedObject()
	{
		return state;
	}
	
	public Inspector getInspector()
	{
		Inspector i = super.getInspector();
		i.setVolatile(true);
		return i;
	}
}
