package ppsim_additions;

import java.util.HashMap;

import sim.util.Bag;

public class Agent_Store {

	 /**
     * All LTo cells in this simulation
     */
    public Bag ltoCellsBag;
    
    /**
     * All cells in this simulation
     */
    public HashMap<Integer,Object> allCells;
    
    /**
	 * Tracked cells close to a forming PP
	 */
	public Bag trackedCells_Close;
	
	/**
	 * Tracked cells away from a forming PP
	 */
	public Bag trackedCells_Away;
	
	/**
     * Used to set unique cell id, that becomes key for above map
     */
    public int num_cells_created = 0;
    
    /**
	 * Scheduler start reference for LTo cells
	 */
	public int lto_scheduler_ref = 4;
	
	/**
	 * Scheduler start reference for LTin cells
	 */
	public int ltin_scheduler_ref = 38;
	

	/**
	 * Count of how many LTin cells created
	 */
	public int ltin_cells_created = 0;
	
	/**
	 * Count of how many LTi cells created
	 */
	public int lti_cells_created = 0;
	
	/**
	 * Bag containing all LTis in the simulation
	 */
	public Bag allLTis;
	
	/**
	 * Bag containing all LTins in the simulation
	 */
	public Bag allLTins;
	
	
	/**
	 * Scheduler start reference for LTi cells
	 */
	public static int lti_scheduler_ref = 239;
    
    public Agent_Store()
    {
    	this.ltoCellsBag = new Bag();
    	this.allCells = new HashMap<>();
    	this.trackedCells_Close = new Bag();
    	this.trackedCells_Away = new Bag();
    	this.allLTis = new Bag();
    	this.allLTins = new Bag();
    }
	
}
