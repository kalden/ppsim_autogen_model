package simulation_main;

import ec.util.MersenneTwisterFast;
import sim.engine.SimState;
import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;
import autogenerated_sim.Functions;
import autogenerated_sim.LTiModule;
import autogenerated_sim.LTin_Module;
import autogenerated_sim.LTo_Module;
import ppsim_additions.Agent_Store;
import ppsim_additions.CellInputControl;
import ppsim_additions.CellTracking;
import ppsim_additions.ChemokineGrid;
import ppsim_additions.Environment;
import ppsim_additions.Setup_Stromal_Cell_Distribution;
import ppsim_additions.Simulation_Settings;
import sim.display.Display2D;
import sim.field.continuous.Continuous2D;
import sim.field.grid.ObjectGrid2D;
import sim.util.Double2D;
import sim.util.Int2D;


public class Mason_Sim_Main extends SimState {
	
	public static MersenneTwisterFast rng = new MersenneTwisterFast();
	// Set this to the number of steps for which simulation should execute 
	public int number_of_steps = 4321;
	
	// Variables added by hand
	/**
	 * Main simulation, for reference elsewhere. Could be automatically generated
	 */
	public static Mason_Sim_Main state;
	
	/**
	 * Object of the class which reads the simulation parameters from the XML file, making these available in the simulation
	 */
	public static Simulation_Settings simParams;
	
	/**
	 * All variables that have to do with the environment (the grids)
	 */
	public static Environment sim_env;
	
	/**
	 * All variables that have to do with storing and counting agents
	 */
	public static Agent_Store agent_store = new Agent_Store();
	
	/**
	 * Object used to perform the cell tracking during the run 
	 */
	public CellTracking cellTrackStats;
	
	/**
	 * Object used to set up the distribution of LTo and RLNonStromal Cells in the environment
	 */
	public Setup_Stromal_Cell_Distribution stromalCellEnvironment; 
	
	/**
     * Folder where simulation results should be output to
     */
    public static String filePath;
    
    /**
     * Description of this run - used to form the results folder name
     */
    public static String runDescription;
    
    /**
     * Full file path to the parameter XML file
     */
    public static String paramFilePath;
    
    /**
	 * Whether cells start in a defined place. Used in testing
	 */
	public static boolean set_cell_start_positions = false;
	
	 /**
     * Which setup we are using - test or normal setup
     */
    public boolean test_setup = false;
    
    /**
	 * Used for displaying simulation on a GUI
	 */
	public static Display2D display;
    
	
	public Mason_Sim_Main(long seed)
	{
		super(seed);
		//rng = new MersenneTwisterFast(seed);
		rng.setSeed(seed);
	}
	
	/**
	 * Constructor used by the GUI
	 * @param seed
	 * @param paramFilePath_In
	 * @param runDescription_In
	 * @param filepath_In
	 */
	public Mason_Sim_Main(long seed, String paramFilePath_In, String runDescription_In, String filepath_In)
	{
		super(seed);
		//rng = new MersenneTwisterFast(seed);
		rng.setSeed(seed);
		
		
		paramFilePath = paramFilePath_In;
		filePath = filepath_In;
	    runDescription = runDescription_In;
	}
	
	public void finish()
	{
		super.finish();
	}
	
	public void start()
	{
		super.start();
		if(simParams == null)
		{
			// Now using a singleton to get the parameters from the XML file
			simParams = Simulation_Settings.getInstance();
		}
		
		// Initialise the environment
		sim_env = new Environment(this.simParams.initialGridHeight, this.simParams.initialGridLength, this.simParams.LTO_DIAMETER);
		
		// Initialise agent store
		//agent_store = new Agent_Store();
		
		if(!this.test_setup)
		{
			// A normal simulation
			normal_start();
		}
		else
		{
			// Test simulation setup
			test_sim_setup();
		}
		
	}
	
	public static void main(String[] args)
	{
		// Set the seed here
		long seed = System.currentTimeMillis();
		
		state = new Mason_Sim_Main(seed);
		
		if(args.length > 0)
		{
			// Command line run (arguments should have been supplied!)
			state.runDescription = args[0];
			state.paramFilePath = args[1];
			state.filePath = args[2];
		}
		else
		{
			//paramFilePath = "/home/kja505/Dropbox/RoboCalc/parameters-master.xml";
			state.paramFilePath = "/home/kja505/Dropbox/RoboCalc/LHC_Analysis/LHC/paramFile_1.xml";
			state.runDescription = "robocalc";
			state.filePath = "/home/kja505/Desktop/";
		}
		
		
		
				
		state.start();
				
		do
		{
			if(!state.schedule.step(state)) break;
		}
		while(state.schedule.getSteps() < state.number_of_steps);
				
		state.finish();
			
			      
		System.exit(0);	
		
	}
	
	/// CHEMOKINE DEBUGGING START:
	public void test_sim_setup()
	{	
		// Change the grid size
		simParams.initialGridLength = 50;
		simParams.initialGridHeight = 50;
		simParams.currentGridLength = simParams.initialGridLength;
		simParams.currentGridHeight = simParams.initialGridHeight;
		simParams.thresholdBindProbability = 0.5;
		
		// As we're testing, we make sure all LTo express RET ligand
		simParams.retLigandProbability = 1;
		
		sim_env.tract = new Continuous2D(2.0,simParams.initialGridLength,simParams.initialGridHeight);
		
		
		new File(filePath+"/"+runDescription).mkdir();
	
		
		// Set the first tracking range
		if(!simParams.trackingHourRanges.isEmpty())
		{
			StringTokenizer st = new StringTokenizer(simParams.trackingHourRanges.get(0),"-");
			simParams.trackingSnapStartHr = Integer.parseInt(st.nextToken());
			simParams.trackingSnapEndHr = Integer.parseInt(st.nextToken());
			simParams.trackingHourRanges.remove(0);
		}
		
		// clear the yard
		sim_env.tract.clear();
		
		// E: Initialise Cell Division Control - now taken care of in state machine 
		//ltoCellDivision = new CellDivision(this);
		//ltoCellDivision.setStopper(schedule.scheduleRepeating(ltoCellDivision,1, 1));
		
		// G: Begin cell tracking if enabled - tracks cells over a set period, captures images of the tract etc
		if(simParams.cellTrackingEnabled)
		{
			cellTrackStats = new CellTracking();
			cellTrackStats.setStopper(schedule.scheduleRepeating(cellTrackStats,3,1));
		}

		// In this test case, we're going to use four LTi cells, so we can see how these interact
		// KA NOTE FOR TESTING: CELL STARTS IN THE CLOSE BAG, SO WE GET A TRACE OF WHAT THE CELL IS DOING
		//create_test_ltin(new Double2D(4,4), 0, 5);
		create_test_lti(new Double2D(46,4), 1, 6);
		//create_test_lti(new Double2D(4,46), 2, 7);
		//create_test_lti(new Double2D(5,23), 3, 8);
		
		// Going to place one LTo on the stroma
		int xLoc = (int)(32/simParams.LTO_DIAMETER);
		int yLoc = (int)(32/simParams.LTO_DIAMETER);
		//System.out.println(xLoc+" "+yLoc);
		
		Int2D gridLocation = new Int2D(xLoc,yLoc);
		// put the object at that location in the grid
		// needs adjusting as boxes are the diameter of the LTo cell - plus add half to correct drawing function
		// else draws on edge of screen
		Double2D location = new Double2D((xLoc*simParams.LTO_DIAMETER)+simParams.LTO_DIAMETER/2,(yLoc*simParams.LTO_DIAMETER)+simParams.LTO_DIAMETER/2);
		LTo_Module ltoCell = new LTo_Module();
		ltoCell.Init();
		// No need to set entry state, as this defaults to start
		ltoCell.getLTo_Module_LTo_Step().LTo_Step_LTo_Attributes.adhesionExpressed = 0;
		ltoCell.getLTo_Module_LTo_Step().LTo_Step_LTo_Attributes.chemokineExpressed = simParams.chemoUpperLinearAdjust;
		ltoCell.getLTo_Module_LTo_Step().LTo_Step_LTo_Attributes.maxVCAMProbability = simParams.maxVCAMProbability;
		ltoCell.getLTo_Module_LTo_Step().LTo_Step_LTo_Attributes.retLigandTime = simParams.numHoursRETLigandLToActive;
		ltoCell.getLTo_Module_LTo_Step().LTo_Step_LTo_Attributes.maxExpressionReached = false;
		ltoCell.getLTo_Module_LTo_Step().LTo_Step_LTo_Attributes.adhesionIncrement = 0.05;
		ltoCell.getLTo_Module_LTo_Step().LTo_Step_LTo_Attributes.chemokineDecrement = 0.005;
		ltoCell.getLTo_Module_LTo_Step().LTo_Step_LTo_Attributes.chemokineExpressionLimit = simParams.chemoLowerLinearAdjust;
		ltoCell.getLTo_Module_LTo_Step().LTo_Step_LTo_Attributes.cellDivisionTime = simParams.cellDivisionTime;
		ltoCell.getLTo_Module_LTo_Step().LTo_Step_LTo_Attributes.cell_id = agent_store.num_cells_created;
		agent_store.num_cells_created++;
		agent_store.allCells.put(ltoCell.getLTo_Module_LTo_Step().LTo_Step_LTo_Attributes.cell_id, ltoCell);
		
		ltoCell.getLTo_Module_LTo_Step().LTo_Step_LTo_Attributes.LTo_loc = location;
		ltoCell.getLTo_Module_LTo_Step().LTo_Step_LTo_Attributes.gridLoc = gridLocation;
		
		sim_env.tract.setObjectLocation(ltoCell,location);
		ltoCell.setStopper(schedule.scheduleRepeating(ltoCell,4,1));
		agent_store.ltoCellsBag.add(ltoCell);
		// Set the cell to be expressing chemokine and adhesion factors
		ltoCell.getLTo_Module_LTo_Step().LTo_Step_LTo_Attributes.lTiContactStateChangeTimePoint = 0;
		ltoCell.getLTo_Module_LTo_Step().LTo_Step_LTo_Attributes.lTinContactStateChangeTimePoint=0;
		ltoCell.getLTo_Module_LTo_Step().LTo_Step_LTo_Attributes.adhesionExpressed = 0.6;
		ltoCell.getLTo_Module_LTo_Step().LTo_Step_LTo_Attributes.chemokineExpressed = 0.02;
		ltoCell.getLTo_Module_LTo_Step().LTo_Step_LTo_Attributes.expressingRET = true;
		
		//chemokine_in_environment = true;
	
		
		// Set up the grid
		sim_env.lToGrid = new ObjectGrid2D((int)(simParams.initialGridLength/simParams.LTO_DIAMETER),(int)(simParams.initialGridHeight/simParams.LTO_DIAMETER));
						
		// set the grid to show the cell has been placed here
		sim_env.lToGrid.set(xLoc, yLoc, ltoCell);
		
		// Set up the chemokine grid
		sim_env.chemoGrid = new ChemokineGrid(simParams.initialGridLength, simParams.initialGridHeight);
	
	}
	
	// Normal Simulation Setup
	public void normal_start()
	{
		// Initialise:
		new File(filePath+"/"+runDescription).mkdir();
			
		// Set the first tracking range
		if(!simParams.trackingHourRanges.isEmpty())
		{
			StringTokenizer st = new StringTokenizer(simParams.trackingHourRanges.get(0),"-");
			simParams.trackingSnapStartHr = Integer.parseInt(st.nextToken());
			simParams.trackingSnapEndHr = Integer.parseInt(st.nextToken());
			simParams.trackingHourRanges.remove(0);
		}
			
		// clear the yard
		sim_env.tract.clear();
			
		// E: Initialise Cell Division Control - now taken care of in state machine
		//ltoCellDivision = new CellDivision(this);
		//ltoCellDivision.setStopper(schedule.scheduleRepeating(ltoCellDivision,1, 1));
		
		CellInputControl hemCells = new CellInputControl();	
		hemCells.setStopper(schedule.scheduleRepeating(hemCells, 2, 1));
			
		// G: Begin cell tracking if enabled - tracks cells over a set period, captures images of the tract etc
		if(simParams.cellTrackingEnabled)
		{
			cellTrackStats = new CellTracking();
			cellTrackStats.setStopper(schedule.scheduleRepeating(cellTrackStats,3,1));
		}
			
		// C: Place the LTo on the stroma
		stromalCellEnvironment = new Setup_Stromal_Cell_Distribution();
			
		// Set up the chemokine grid
		sim_env.chemoGrid = new ChemokineGrid(simParams.initialGridLength, simParams.initialGridHeight);
			
	}
	
	public void create_test_lti(Double2D location, int id, int schedule_position)
	{
		//LTi_Module lti = new LTi_Module(location, new Operations(), this, id);
		LTiModule lti = new LTiModule();
		lti.Init();
		lti.getLTiModule_LTiStep().LTiStep_LTi_Attributes.cell_id = agent_store.num_cells_created;
		agent_store.num_cells_created++;
		agent_store.allCells.put(lti.getLTiModule_LTiStep().LTiStep_LTi_Attributes.cell_id, lti);
		lti.getLTiModule_LTiStep().LTiStep_LTi_Attributes.cellSpeed = Functions.set_cell_speed(rng.nextDouble(), simParams.cellSpeedMinLowBound, simParams.cellSpeedMinUpBound);
		lti.getLTiModule_LTiStep().LTiStep_LTi_Attributes.LTi_loc = location;
		
		lti.getLTiModule_LTiStep().LTiStep_LTi_Attributes.cellSpeed = 1;
		
		sim_env.tract.setObjectLocation(lti, location);
		lti.setStopper(schedule.scheduleRepeating(lti,schedule_position,1));
		agent_store.allLTis.add(lti);
		
		// KA NOTE FOR TESTING: CELL STARTS IN THE CLOSE BAG, SO WE GET A TRACE OF WHAT THE CELL IS DOING
		agent_store.trackedCells_Close.add(lti);
		lti.getLTiModule_LTiStep().LTiStep_LTi_Attributes.tracking.agentTrackStartLocation = location;
	}
	
	public void create_test_ltin(Double2D location, int id, int schedule_position)
	{
		//LTin_Module ltin = new LTin_Module(location, new Operations(), this, id);
		LTin_Module ltin = new LTin_Module();
		ltin.Init();
		ltin.getLTin_Module_LTin_Step().LTin_Step_LTin_Attributes.cell_id = agent_store.num_cells_created;
		agent_store.num_cells_created++;
		agent_store.allCells.put(ltin.getLTin_Module_LTin_Step().LTin_Step_LTin_Attributes.cell_id, ltin);
		ltin.getLTin_Module_LTin_Step().LTin_Step_LTin_Attributes.cellSpeed = Functions.set_cell_speed(rng.nextDouble(), simParams.cellSpeedMinLowBound, simParams.cellSpeedMinUpBound);
		ltin.getLTin_Module_LTin_Step().LTin_Step_LTin_Attributes.LTin_loc = location;
		
		ltin.getLTin_Module_LTin_Step().LTin_Step_LTin_Attributes.cellSpeed = 1;
		
		//ltin.ltin_Attributes.cellSpeed = 1;
		//ltin.ltin_Attributes.originalLocation = location;
		
		sim_env.tract.setObjectLocation(ltin, location);
		ltin.setStopper(schedule.scheduleRepeating(ltin,schedule_position,1));
		agent_store.allLTins.add(ltin);
		
		// KA NOTE FOR TESTING: CELL STARTS IN THE CLOSE BAG, SO WE GET A TRACE OF WHAT THE CELL IS DOING
		agent_store.trackedCells_Close.add(ltin);
		ltin.getLTin_Module_LTin_Step().LTin_Step_LTin_Attributes.tracking.agentTrackStartLocation = location;
	}
	
	public static ArrayList<Integer> generate_random_number_set()
	{
		ArrayList<Integer> random_ints = new ArrayList<>();
		for(int i=0;i<10;i++)
		{
			random_ints.add(rng.nextInt(360));
		}
		return random_ints;
	}
	
}
