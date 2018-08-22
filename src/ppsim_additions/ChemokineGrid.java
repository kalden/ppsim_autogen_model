package ppsim_additions;

import sim.field.grid.Grid2D;
import sim.field.grid.IntGrid2D;
import sim.util.IntBag;

/**
 * Class to create a grid space which overlays the Continuous grid on which the cells move.  This grid is used
 * when calculating chemokine levels around a cell.  Though no values are actually stored on the grid, the methods 
 * that are made available by the IntGrid2D class do come in useful for calculating neighbourhoods around a cell and 
 * chemokine diffusion.
 * 
 * Note that this may determine a grid space at which a cell moves into, but this does not stop the cell from moving at 
 * any angle to get into this grid square.  This allows for a little randomness alongside controlled movement
 * 
 * @author Kieran Alden
 *
 */
public class ChemokineGrid 
{
	/**
	 * Grid of 1x1 squares which overlays the Continuous2D grid
	 */
	public IntGrid2D chemoGrid;
	
	/**
	 * Constructs the grid at the required length and width
	 * @param gridLength	Length of the grid
	 * @param gridHeight	Height of the grid
	 */
	public ChemokineGrid(double gridLength,double gridHeight)
	{
		chemoGrid = new IntGrid2D((int)gridLength,(int)gridHeight);
	}
	
	/**
	 * Utility function used when calculating where the cell is on this IntGrid2D.  Each cell has a coordinate but this is a double.  This method rounds the x value so that the cells location on an integer grid can be determined
	 * 
	 * @param x	The double x coordinate that needs to be rounded
	 * @param ppsim	The current state of the simulation
	 * @return int roundedX	An rounded integer value of the coordinate
	 */
	public int roundX(double x)
	{
		//int roundedX;
		//System.out.println("X COORD: "+x);
		
		// KA Robocalc conversion error: this should probably always be rounded up and one subtracted to avoid boundary conditions
		//if((int)x!=ppsim.currentGridLength)
		//	roundedX = (int)Math.round(x);
		//else
		//	roundedX = (int)Math.floor(x);
		//return roundedX;
		return (int)Math.ceil(x)-1;
	}
	
	/**
	 * Utility function used when calculating where the cell is on this IntGrid2D.  Each cell has a coordinate but this is a double.  This method rounds the y value so that the cells location on an integer grid can be determined
	 * 
	 * @param y	The double y coordinate that needs to be rounded
	 * @param ppsim	The current state of the simulation
	 * @return int roundedY	An rounded integer value of the coordinate
	 */
	public int roundY(double y)
	{
		//System.out.println("IN TO ROUND Y: "+y);
		//int roundedY;
		//System.out.println("Y COORD: "+y);
		// KA Robocalc conversion error: this should probably always be rounded up and one subtracted to avoid boundary conditions
		//if((int)y!=ppsim.currentGridHeight)
		//	roundedY = (int)Math.round(y);
		//else
		//	roundedY = (int)Math.floor(y);
		//return roundedY;
		//System.out.println("Returning: "+ ((int)Math.ceil(y)-1));
		return (int)Math.ceil(y)-1;
	}
	
	/**
	 * Takes the rounded x and y coordinates and returns an IntBag of the coordinates in the Moores Neighbourhood of this grid space.
	 * Takes a boolean as input to determine if the IntBag should be returning the x or y coordinates
	 * As this is the case, this method is usually called twice, once for x and once for y
	 * 
	 * @param x	The x coordinate of the cell around which to generate the neighbourhood
	 * @param y The y coordinate of the cell around which to generate the neighbourhood
	 * @param xaxis	Boolean to determine if the x coordinate neighbourhood is required (true) or y (false)
	 * @param ppsim	The current state of the simulation
	 * @return IntBag xPos/yPos	An IntBag containing either the x or y coordinates of all squares in the neighbourhood
	 */
	public IntBag generateMooresN(double x, double y,boolean xaxis)
	{
		//System.out.println("Input: "+x+" "+y);
		int roundX = roundX(x);
		int roundY = roundY(y);
		IntBag xPos = new IntBag();
		IntBag yPos = new IntBag();
		//System.out.println("Rounded: "+roundX+" "+roundY);
		//System.out.println(this.chemoGrid.getWidth()+ " "+this.chemoGrid.getHeight());
		
		// For new MASON:
		this.chemoGrid.getMooreNeighbors(roundX, roundY, 1, Grid2D.TOROIDAL, true, null, xPos, yPos);
		
		// Now deprecated:
		//this.chemoGrid.getNeighborsMaxDistance(roundX,roundY, 1, true, null, xPos, yPos);
		
		if(xaxis)
			return xPos;
		else
			return yPos;
	}
	
}

