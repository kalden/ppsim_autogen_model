package ppsim_additions;

import sim.field.continuous.Continuous2D;
import sim.field.grid.ObjectGrid2D;

public class Environment {

	  /**
     * Environment in which the cells are moving
     */
    public Continuous2D tract;
    
    /**
   	 * Grid of 1x1 squares which overlays the Continuous2D grid
   	 */
   	public ObjectGrid2D lToGrid;
   	
   	/**
   	 * Grid for calculation of chemokine expression
   	 */
   	public ChemokineGrid chemoGrid;

    public Environment(double height, double length, double lto_diameter)
    {
    	tract = new Continuous2D(1.0,length,height);
    	lToGrid = new ObjectGrid2D((int)(length/lto_diameter),(int)(height/lto_diameter));
    	chemoGrid = new ChemokineGrid(length, height);
    }
    
}
