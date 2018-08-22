package autogenerated_sim;

import roboCalcAPI.Stages;
import roboCalcAPI.State;
import roboCalcAPI.StateMachine;
import roboCalcAPI.Timer;
import roboCalcAPI.Transition;
import simulation_main.Mason_Sim_Main;

public class LTin_State_Machine extends StateMachine
{
	
	// Clocks and variable list:
	public Timer T;
	public LTin_Step R_LTin_Step;
	public LTin_Controller C_LTin_Controller;
public boolean move_finished;
public boolean adhered;
public int contactedCell_ID;
public double probabilityOfAdhesion;
public double distanceToMove;
public double distanceMoved;
public double movement_interval;
public double pJunctionValue;

// Constructor needs to go here, and check Execute
	
public LTin_State_Machine(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller) 
{
	super("LTin_State_Machine");
	this.R_LTin_Step = R_LTin_Step;  
	this.C_LTin_Controller = C_LTin_Controller;
						
						
		this.move_finished = false;
		this.adhered = false;
		this.contactedCell_ID = -1;
		this.probabilityOfAdhesion = 0;
		this.distanceToMove = 0;
		this.distanceMoved = 0;
		this.movement_interval = 0.1;

	pJunctionValue = Mason_Sim_Main.rng.nextDouble();
	T = new Timer("T");
						
	
	// instantiate states && add substates of machine
	i0 S_LTin_State_Machine_i0 = new i0(R_LTin_Step, C_LTin_Controller, this);
	states.add(S_LTin_State_Machine_i0);
	Moving S_LTin_State_Machine_Moving = new Moving(R_LTin_Step, C_LTin_Controller, this);
	states.add(S_LTin_State_Machine_Moving);
	Adhesion_Response S_LTin_State_Machine_Adhesion_Response = new Adhesion_Response(R_LTin_Step, C_LTin_Controller, this);
	states.add(S_LTin_State_Machine_Adhesion_Response);

	t0 S_LTin_State_Machine_t0 = new t0(R_LTin_Step, C_LTin_Controller, this, S_LTin_State_Machine_i0, S_LTin_State_Machine_Moving);
	S_LTin_State_Machine_i0.transitions.add(S_LTin_State_Machine_t0);
	t1 S_LTin_State_Machine_t1 = new t1(R_LTin_Step, C_LTin_Controller, this, S_LTin_State_Machine_Moving, S_LTin_State_Machine_Moving);
	S_LTin_State_Machine_Moving.transitions.add(S_LTin_State_Machine_t1);
	t2 S_LTin_State_Machine_t2 = new t2(R_LTin_Step, C_LTin_Controller, this, S_LTin_State_Machine_Adhesion_Response, S_LTin_State_Machine_Adhesion_Response);
	S_LTin_State_Machine_Adhesion_Response.transitions.add(S_LTin_State_Machine_t2);
	t3 S_LTin_State_Machine_t3 = new t3(R_LTin_Step, C_LTin_Controller, this, S_LTin_State_Machine_Moving, S_LTin_State_Machine_Adhesion_Response);
	S_LTin_State_Machine_Moving.transitions.add(S_LTin_State_Machine_t3);
	t4 S_LTin_State_Machine_t4 = new t4(R_LTin_Step, C_LTin_Controller, this, S_LTin_State_Machine_Adhesion_Response, S_LTin_State_Machine_Moving);
	S_LTin_State_Machine_Adhesion_Response.transitions.add(S_LTin_State_Machine_t4);

	stage = Stages.s_Enter;
						
}
					
					

	
	public int Initial()
	{
		return 0;
	}
	
	public void Execute()
	{
		super.Execute();
		T.IncCounter();
	}
	
	// Now to build the states and any other inner state machines

public class Moving extends State 
{
	private LTin_Step R_LTin_Step;
	private LTin_Controller C_LTin_Controller;
	private LTin_State_Machine S_LTin_State_Machine;
	
	public Moving(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine S_LTin_State_Machine)
	{
		super("Moving",Stages.s_Inactive);
		this.R_LTin_Step = R_LTin_Step;
		this.C_LTin_Controller = C_LTin_Controller;
		this.S_LTin_State_Machine = S_LTin_State_Machine;
		
		// instantiate states && add substates of machine
		i0 Moving_i0 = new i0(R_LTin_Step, C_LTin_Controller, S_LTin_State_Machine);
		states.add(Moving_i0);
		Motile Moving_Motile = new Motile(R_LTin_Step, C_LTin_Controller, S_LTin_State_Machine);
		states.add(Moving_Motile);
		Disassociated Moving_Disassociated = new Disassociated(R_LTin_Step, C_LTin_Controller, S_LTin_State_Machine);
		states.add(Moving_Disassociated);
		Associated Moving_Associated = new Associated(R_LTin_Step, C_LTin_Controller, S_LTin_State_Machine);
		states.add(Moving_Associated);
		p0 Moving_p0 = new p0(R_LTin_Step, C_LTin_Controller, S_LTin_State_Machine);
		states.add(Moving_p0);
		
		t0 Moving_t0 = new t0(R_LTin_Step, C_LTin_Controller, S_LTin_State_Machine, Moving_i0, Moving_Motile);
		Moving_i0.transitions.add(Moving_t0);
		t1 Moving_t1 = new t1(R_LTin_Step, C_LTin_Controller, S_LTin_State_Machine, Moving_p0, Moving_Disassociated);
		Moving_p0.transitions.add(Moving_t1);
		t2 Moving_t2 = new t2(R_LTin_Step, C_LTin_Controller, S_LTin_State_Machine, Moving_Motile, Moving_p0);
		Moving_Motile.transitions.add(Moving_t2);
		t3 Moving_t3 = new t3(R_LTin_Step, C_LTin_Controller, S_LTin_State_Machine, Moving_p0, Moving_Associated);
		Moving_p0.transitions.add(Moving_t3);
		t4 Moving_t4 = new t4(R_LTin_Step, C_LTin_Controller, S_LTin_State_Machine, Moving_Motile, Moving_Motile);
		Moving_Motile.transitions.add(Moving_t4);
	}
	public void Entry()
	{
		S_LTin_State_Machine.distanceToMove = R_LTin_Step.LTin_Step_LTin_Attributes.cellSpeed;
		S_LTin_State_Machine.distanceMoved = 0;
		S_LTin_State_Machine.movement_interval = 0.1;
		R_LTin_Step.LTin_Step_LTin_Attributes.angle_to_move = Functions.calculate_angle_from_direction(99);
		S_LTin_State_Machine.move_finished = false;
	}
	public void Exit() 
	{
		 R_LTin_Step.LTin_Step_Operations.update_tracking_stats(R_LTin_Step.LTin_Step_LTin_Attributes.tracking, R_LTin_Step.LTin_Step_LTin_Attributes.cell_id, R_LTin_Step.LTin_Step_LTin_Attributes.LTin_loc, S_LTin_State_Machine.distanceMoved); 
	}
	
	public class i0 extends State 
		{
			private LTin_Step R_LTin_Step;
			private LTin_Controller C_LTin_Controller;
			private LTin_State_Machine S_LTin_State_Machine;
			
			public i0(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine S_LTin_State_Machine)
			{
				super("i0",Stages.s_Inactive);
				this.R_LTin_Step = R_LTin_Step;
				this.C_LTin_Controller = C_LTin_Controller;
				this.S_LTin_State_Machine = S_LTin_State_Machine;
				
			}
			
		};
		
		public int Initial()
		{
			return 0;
		}
		public void Execute()
		{
			super.Execute();
		}
	public class Motile extends State 
		{
			private LTin_Step R_LTin_Step;
			private LTin_Controller C_LTin_Controller;
			private LTin_State_Machine S_LTin_State_Machine;
			
			public Motile(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine S_LTin_State_Machine)
			{
				super("Motile",Stages.s_Inactive);
				this.R_LTin_Step = R_LTin_Step;
				this.C_LTin_Controller = C_LTin_Controller;
				this.S_LTin_State_Machine = S_LTin_State_Machine;
				
			}
			public void Entry()
			{
				if (S_LTin_State_Machine.move_finished == false) 
				{
					if (S_LTin_State_Machine.movement_interval > S_LTin_State_Machine.distanceToMove) 
					{
						S_LTin_State_Machine.movement_interval = S_LTin_State_Machine.distanceToMove;
					}
					R_LTin_Step.LTin_Step_LTin_Attributes.LTin_loc = Functions.calculateNewPosition(R_LTin_Step.LTin_Step_LTin_Attributes.LTin_loc, R_LTin_Step.LTin_Step_LTin_Attributes.angle_to_move, S_LTin_State_Machine.movement_interval);
					S_LTin_State_Machine.distanceToMove = S_LTin_State_Machine.distanceToMove - S_LTin_State_Machine.movement_interval;
					S_LTin_State_Machine.distanceMoved = S_LTin_State_Machine.distanceMoved + S_LTin_State_Machine.movement_interval;
					 R_LTin_Step.LTin_Step_Operations.setPositionOnTract(R_LTin_Step.LTin_Step_LTin_Attributes.cell_id, R_LTin_Step.LTin_Step_LTin_Attributes.LTin_loc); 
					if (S_LTin_State_Machine.distanceToMove == 0) 
					{
						S_LTin_State_Machine.move_finished = true;
					}
					if (Functions.check_valid_location_on_grid(R_LTin_Step.LTin_Step_LTin_Attributes.LTin_loc) == true) 
					{
						S_LTin_State_Machine.contactedCell_ID = Functions.collision_check(R_LTin_Step.LTin_Step_LTin_Attributes.LTin_loc, R_LTin_Step.LTin_Step_LTin_Attributes.tracking, R_LTin_Step.LTin_Step_LTin_Attributes.cell_id, "LTin");
						S_LTin_State_Machine.probabilityOfAdhesion = Functions.calculate_probability_adhesion(S_LTin_State_Machine.contactedCell_ID);
					}
					else 
					{
						 R_LTin_Step.LTin_Step_Operations.stop_cell_on_schedule(R_LTin_Step.LTin_Step_LTin_Attributes.cell_id); 
						S_LTin_State_Machine.move_finished = true;
						S_LTin_State_Machine.probabilityOfAdhesion = 0;
					}
				}
			}
			
		};
	public class Disassociated extends State 
		{
			private LTin_Step R_LTin_Step;
			private LTin_Controller C_LTin_Controller;
			private LTin_State_Machine S_LTin_State_Machine;
			
			public Disassociated(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine S_LTin_State_Machine)
			{
				super("Disassociated",Stages.s_Inactive);
				this.R_LTin_Step = R_LTin_Step;
				this.C_LTin_Controller = C_LTin_Controller;
				this.S_LTin_State_Machine = S_LTin_State_Machine;
				
			}
			public void Entry()
			{
				R_LTin_Step.LTin_Step_LTin_Attributes.angle_to_move = Functions.avoidCellCollision(R_LTin_Step.LTin_Step_LTin_Attributes.LTin_loc, S_LTin_State_Machine.distanceToMove, S_LTin_State_Machine.contactedCell_ID);
				R_LTin_Step.LTin_Step_LTin_Attributes.LTin_loc = Functions.calculateNewPosition(R_LTin_Step.LTin_Step_LTin_Attributes.LTin_loc, R_LTin_Step.LTin_Step_LTin_Attributes.angle_to_move, S_LTin_State_Machine.distanceToMove);
				if (Functions.check_valid_location_on_grid(R_LTin_Step.LTin_Step_LTin_Attributes.LTin_loc) == true) 
				{
					 R_LTin_Step.LTin_Step_Operations.setPositionOnTract(R_LTin_Step.LTin_Step_LTin_Attributes.cell_id, R_LTin_Step.LTin_Step_LTin_Attributes.LTin_loc); 
				}
				else 
				{
					 R_LTin_Step.LTin_Step_Operations.stop_cell_on_schedule(R_LTin_Step.LTin_Step_LTin_Attributes.cell_id); 
				}
				S_LTin_State_Machine.distanceMoved = S_LTin_State_Machine.distanceMoved + S_LTin_State_Machine.distanceToMove;
				S_LTin_State_Machine.distanceToMove = 0;
				S_LTin_State_Machine.move_finished = true;
				S_LTin_State_Machine.contactedCell_ID = -1;
			}
			
		};
	public class Associated extends State 
		{
			private LTin_Step R_LTin_Step;
			private LTin_Controller C_LTin_Controller;
			private LTin_State_Machine S_LTin_State_Machine;
			
			public Associated(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine S_LTin_State_Machine)
			{
				super("Associated",Stages.s_Inactive);
				this.R_LTin_Step = R_LTin_Step;
				this.C_LTin_Controller = C_LTin_Controller;
				this.S_LTin_State_Machine = S_LTin_State_Machine;
				
			}
			public void Entry()
			{
				S_LTin_State_Machine.adhered = true;
			}
			
		};
	public class p0 extends State 
		{
			private LTin_Step R_LTin_Step;
			private LTin_Controller C_LTin_Controller;
			private LTin_State_Machine S_LTin_State_Machine;
			
			public p0(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine S_LTin_State_Machine)
			{
				super("p0",Stages.s_Inactive);
				this.R_LTin_Step = R_LTin_Step;
				this.C_LTin_Controller = C_LTin_Controller;
				this.S_LTin_State_Machine = S_LTin_State_Machine;
				
			}
			public void Entry() 
			{
				S_LTin_State_Machine.pJunctionValue =  Mason_Sim_Main.rng.nextDouble();
			}
			
		};
	public class t0 extends Transition {
			private LTin_Step R_LTin_Step;
			private LTin_Controller C_LTin_Controller;
			private LTin_State_Machine S_LTin_State_Machine;
				
				
			public 	t0(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine 
					S_LTin_State_Machine, State src, State tgt)
			{
				super("S_LTin_State_Machine_Moving_t0", src, tgt);
				this.R_LTin_Step = R_LTin_Step;
				this.C_LTin_Controller = C_LTin_Controller;
				this.S_LTin_State_Machine = S_LTin_State_Machine;
				
				// KA: May need to check this if we look at events - not 100% sure transferred correctly from C++
			}
					
		};
	public class t2 extends Transition {
			private LTin_Step R_LTin_Step;
			private LTin_Controller C_LTin_Controller;
			private LTin_State_Machine S_LTin_State_Machine;
				
				
			public 	t2(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine 
					S_LTin_State_Machine, State src, State tgt)
			{
				super("S_LTin_State_Machine_Moving_t2", src, tgt);
				this.R_LTin_Step = R_LTin_Step;
				this.C_LTin_Controller = C_LTin_Controller;
				this.S_LTin_State_Machine = S_LTin_State_Machine;
				
				// KA: May need to check this if we look at events - not 100% sure transferred correctly from C++
			}
					
				public boolean Condition() {
					if (S_LTin_State_Machine.contactedCell_ID != -1) {
						//System.out.println("Condition of transition S_LTin_State_Machine_Moving_t2 is true");
						return true;
					}
					else {
						//System.out.println("Condition of transition S_LTin_State_Machine_Moving_t2 is false");
						return false;
					}
				}
		};
	public class t4 extends Transition {
			private LTin_Step R_LTin_Step;
			private LTin_Controller C_LTin_Controller;
			private LTin_State_Machine S_LTin_State_Machine;
				
				
			public 	t4(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine 
					S_LTin_State_Machine, State src, State tgt)
			{
				super("S_LTin_State_Machine_Moving_t4", src, tgt);
				this.R_LTin_Step = R_LTin_Step;
				this.C_LTin_Controller = C_LTin_Controller;
				this.S_LTin_State_Machine = S_LTin_State_Machine;
				
				// KA: May need to check this if we look at events - not 100% sure transferred correctly from C++
			}
					
				public boolean Condition() {
					if (S_LTin_State_Machine.contactedCell_ID == -1 && S_LTin_State_Machine.move_finished == false) {
						//System.out.println("Condition of transition S_LTin_State_Machine_Moving_t4 is true");
						return true;
					}
					else {
						//System.out.println("Condition of transition S_LTin_State_Machine_Moving_t4 is false");
						return false;
					}
				}
		};
	public class t1 extends Transition {
			private LTin_Step R_LTin_Step;
			private LTin_Controller C_LTin_Controller;
			private LTin_State_Machine S_LTin_State_Machine;
			
			public t1(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine S_LTin_State_Machine, State src, State tgt)
			{
				super("S_LTin_State_Machine_Moving_t1", src, tgt);
				this.R_LTin_Step = R_LTin_Step;
				this.C_LTin_Controller = C_LTin_Controller;
				this.S_LTin_State_Machine = S_LTin_State_Machine;
			}
			
			public boolean Condition() 
			{
				if (S_LTin_State_Machine.pJunctionValue > (0) && S_LTin_State_Machine.pJunctionValue <= (0 + (1 - S_LTin_State_Machine.probabilityOfAdhesion))) {
						//System.out.println("Condition of transition S_LTin_State_Machine_Moving_t1 is true");
						return true;
				}
				else {
					//System.out.println("Condition of transition S_LTin_State_Machine_Moving_t1 is false");
					return false;
				}
			}
		};
	
	//0 + 1 - S_LTin_State_Machine.probabilityOfAdhesion
	public class t3 extends Transition {
			private LTin_Step R_LTin_Step;
			private LTin_Controller C_LTin_Controller;
			private LTin_State_Machine S_LTin_State_Machine;
			
			public t3(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine S_LTin_State_Machine, State src, State tgt)
			{
				super("S_LTin_State_Machine_Moving_t3", src, tgt);
				this.R_LTin_Step = R_LTin_Step;
				this.C_LTin_Controller = C_LTin_Controller;
				this.S_LTin_State_Machine = S_LTin_State_Machine;
			}
			
			public boolean Condition() 
			{
				if (S_LTin_State_Machine.pJunctionValue > (0 + 1 - S_LTin_State_Machine.probabilityOfAdhesion) && S_LTin_State_Machine.pJunctionValue <= (0 + 1 - S_LTin_State_Machine.probabilityOfAdhesion + (S_LTin_State_Machine.probabilityOfAdhesion))) {
						//System.out.println("Condition of transition S_LTin_State_Machine_Moving_t3 is true");
						return true;
				}
				else {
					//System.out.println("Condition of transition S_LTin_State_Machine_Moving_t3 is false");
					return false;
				}
			}
		};
	
	//0 + 1 - S_LTin_State_Machine.probabilityOfAdhesion + S_LTin_State_Machine.probabilityOfAdhesion
	//0
}
public class i0 extends State 
{
	private LTin_Step R_LTin_Step;
	private LTin_Controller C_LTin_Controller;
	private LTin_State_Machine S_LTin_State_Machine;
	
	public i0(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine S_LTin_State_Machine)
	{
		super("i0",Stages.s_Inactive);
		this.R_LTin_Step = R_LTin_Step;
		this.C_LTin_Controller = C_LTin_Controller;
		this.S_LTin_State_Machine = S_LTin_State_Machine;
		
	}
	
}
public class Adhesion_Response extends State 
{
	private LTin_Step R_LTin_Step;
	private LTin_Controller C_LTin_Controller;
	private LTin_State_Machine S_LTin_State_Machine;
	
	public Adhesion_Response(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine S_LTin_State_Machine)
	{
		super("Adhesion_Response",Stages.s_Inactive);
		this.R_LTin_Step = R_LTin_Step;
		this.C_LTin_Controller = C_LTin_Controller;
		this.S_LTin_State_Machine = S_LTin_State_Machine;
		
		// instantiate states && add substates of machine
		i0 Adhesion_Response_i0 = new i0(R_LTin_Step, C_LTin_Controller, S_LTin_State_Machine);
		states.add(Adhesion_Response_i0);
		Adhered Adhesion_Response_Adhered = new Adhered(R_LTin_Step, C_LTin_Controller, S_LTin_State_Machine);
		states.add(Adhesion_Response_Adhered);
		Disassociated Adhesion_Response_Disassociated = new Disassociated(R_LTin_Step, C_LTin_Controller, S_LTin_State_Machine);
		states.add(Adhesion_Response_Disassociated);
		p0 Adhesion_Response_p0 = new p0(R_LTin_Step, C_LTin_Controller, S_LTin_State_Machine);
		states.add(Adhesion_Response_p0);
		
		t0 Adhesion_Response_t0 = new t0(R_LTin_Step, C_LTin_Controller, S_LTin_State_Machine, Adhesion_Response_Adhered, Adhesion_Response_p0);
		Adhesion_Response_Adhered.transitions.add(Adhesion_Response_t0);
		t1 Adhesion_Response_t1 = new t1(R_LTin_Step, C_LTin_Controller, S_LTin_State_Machine, Adhesion_Response_p0, Adhesion_Response_Disassociated);
		Adhesion_Response_p0.transitions.add(Adhesion_Response_t1);
		t2 Adhesion_Response_t2 = new t2(R_LTin_Step, C_LTin_Controller, S_LTin_State_Machine, Adhesion_Response_p0, Adhesion_Response_Adhered);
		Adhesion_Response_p0.transitions.add(Adhesion_Response_t2);
		t3 Adhesion_Response_t3 = new t3(R_LTin_Step, C_LTin_Controller, S_LTin_State_Machine, Adhesion_Response_i0, Adhesion_Response_Adhered);
		Adhesion_Response_i0.transitions.add(Adhesion_Response_t3);
	}
	public void Entry()
	{
		S_LTin_State_Machine.distanceToMove = R_LTin_Step.LTin_Step_LTin_Attributes.cellSpeed;
		S_LTin_State_Machine.distanceMoved = 0;
		R_LTin_Step.LTin_Step_LTin_Attributes.angle_to_move = Functions.calculate_angle_from_direction(99);
		S_LTin_State_Machine.move_finished = false;
	}
	public void Exit() 
	{
		 R_LTin_Step.LTin_Step_Operations.update_tracking_stats(R_LTin_Step.LTin_Step_LTin_Attributes.tracking, R_LTin_Step.LTin_Step_LTin_Attributes.cell_id, R_LTin_Step.LTin_Step_LTin_Attributes.LTin_loc, S_LTin_State_Machine.distanceMoved); 
	}
	
	public class Adhered extends State 
		{
			private LTin_Step R_LTin_Step;
			private LTin_Controller C_LTin_Controller;
			private LTin_State_Machine S_LTin_State_Machine;
			
			public Adhered(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine S_LTin_State_Machine)
			{
				super("Adhered",Stages.s_Inactive);
				this.R_LTin_Step = R_LTin_Step;
				this.C_LTin_Controller = C_LTin_Controller;
				this.S_LTin_State_Machine = S_LTin_State_Machine;
				
			}
			public void Entry()
			{
				if (S_LTin_State_Machine.move_finished == false) 
				{
					S_LTin_State_Machine.probabilityOfAdhesion = Functions.calculate_probability_adhesion(S_LTin_State_Machine.contactedCell_ID);
				}
			}
			
		};
	public class Disassociated extends State 
		{
			private LTin_Step R_LTin_Step;
			private LTin_Controller C_LTin_Controller;
			private LTin_State_Machine S_LTin_State_Machine;
			
			public Disassociated(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine S_LTin_State_Machine)
			{
				super("Disassociated",Stages.s_Inactive);
				this.R_LTin_Step = R_LTin_Step;
				this.C_LTin_Controller = C_LTin_Controller;
				this.S_LTin_State_Machine = S_LTin_State_Machine;
				
			}
			public void Entry()
			{
				R_LTin_Step.LTin_Step_LTin_Attributes.angle_to_move = Functions.avoidCellCollision(R_LTin_Step.LTin_Step_LTin_Attributes.LTin_loc, S_LTin_State_Machine.distanceToMove, S_LTin_State_Machine.contactedCell_ID);
				R_LTin_Step.LTin_Step_LTin_Attributes.LTin_loc = Functions.calculateNewPosition(R_LTin_Step.LTin_Step_LTin_Attributes.LTin_loc, R_LTin_Step.LTin_Step_LTin_Attributes.angle_to_move, S_LTin_State_Machine.distanceToMove);
				if (Functions.check_valid_location_on_grid(R_LTin_Step.LTin_Step_LTin_Attributes.LTin_loc) == true) 
				{
					 R_LTin_Step.LTin_Step_Operations.setPositionOnTract(R_LTin_Step.LTin_Step_LTin_Attributes.cell_id, R_LTin_Step.LTin_Step_LTin_Attributes.LTin_loc); 
				}
				else 
				{
					 R_LTin_Step.LTin_Step_Operations.stop_cell_on_schedule(R_LTin_Step.LTin_Step_LTin_Attributes.cell_id); 
				}
				S_LTin_State_Machine.distanceMoved = S_LTin_State_Machine.distanceMoved + S_LTin_State_Machine.distanceToMove;
				S_LTin_State_Machine.distanceToMove = 0;
				S_LTin_State_Machine.move_finished = true;
				S_LTin_State_Machine.contactedCell_ID = -1;
				S_LTin_State_Machine.adhered = false;
			}
			
		};
	public class p0 extends State 
		{
			private LTin_Step R_LTin_Step;
			private LTin_Controller C_LTin_Controller;
			private LTin_State_Machine S_LTin_State_Machine;
			
			public p0(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine S_LTin_State_Machine)
			{
				super("p0",Stages.s_Inactive);
				this.R_LTin_Step = R_LTin_Step;
				this.C_LTin_Controller = C_LTin_Controller;
				this.S_LTin_State_Machine = S_LTin_State_Machine;
				
			}
			public void Entry() 
			{
				S_LTin_State_Machine.pJunctionValue =  Mason_Sim_Main.rng.nextDouble();
			}
			
		};
	public class i0 extends State 
		{
			private LTin_Step R_LTin_Step;
			private LTin_Controller C_LTin_Controller;
			private LTin_State_Machine S_LTin_State_Machine;
			
			public i0(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine S_LTin_State_Machine)
			{
				super("i0",Stages.s_Inactive);
				this.R_LTin_Step = R_LTin_Step;
				this.C_LTin_Controller = C_LTin_Controller;
				this.S_LTin_State_Machine = S_LTin_State_Machine;
				
			}
			
		};
		
		public int Initial()
		{
			return 0;
		}
		public void Execute()
		{
			super.Execute();
		}
	public class t0 extends Transition {
			private LTin_Step R_LTin_Step;
			private LTin_Controller C_LTin_Controller;
			private LTin_State_Machine S_LTin_State_Machine;
				
				
			public 	t0(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine 
					S_LTin_State_Machine, State src, State tgt)
			{
				super("S_LTin_State_Machine_Adhesion_Response_t0", src, tgt);
				this.R_LTin_Step = R_LTin_Step;
				this.C_LTin_Controller = C_LTin_Controller;
				this.S_LTin_State_Machine = S_LTin_State_Machine;
				
				// KA: May need to check this if we look at events - not 100% sure transferred correctly from C++
			}
					
				public boolean Condition() {
					if (S_LTin_State_Machine.move_finished == false) {
						//System.out.println("Condition of transition S_LTin_State_Machine_Adhesion_Response_t0 is true");
						return true;
					}
					else {
						//System.out.println("Condition of transition S_LTin_State_Machine_Adhesion_Response_t0 is false");
						return false;
					}
				}
		};
	public class t1 extends Transition {
			private LTin_Step R_LTin_Step;
			private LTin_Controller C_LTin_Controller;
			private LTin_State_Machine S_LTin_State_Machine;
			
			public t1(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine S_LTin_State_Machine, State src, State tgt)
			{
				super("S_LTin_State_Machine_Adhesion_Response_t1", src, tgt);
				this.R_LTin_Step = R_LTin_Step;
				this.C_LTin_Controller = C_LTin_Controller;
				this.S_LTin_State_Machine = S_LTin_State_Machine;
			}
			
			public boolean Condition() 
			{
				if (S_LTin_State_Machine.pJunctionValue > (0) && S_LTin_State_Machine.pJunctionValue <= (0 + (1 - S_LTin_State_Machine.probabilityOfAdhesion))) {
						//System.out.println("Condition of transition S_LTin_State_Machine_Adhesion_Response_t1 is true");
						return true;
				}
				else {
					//System.out.println("Condition of transition S_LTin_State_Machine_Adhesion_Response_t1 is false");
					return false;
				}
			}
		};
	
	//0 + 1 - S_LTin_State_Machine.probabilityOfAdhesion
	public class t2 extends Transition {
			private LTin_Step R_LTin_Step;
			private LTin_Controller C_LTin_Controller;
			private LTin_State_Machine S_LTin_State_Machine;
			
			public t2(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine S_LTin_State_Machine, State src, State tgt)
			{
				super("S_LTin_State_Machine_Adhesion_Response_t2", src, tgt);
				this.R_LTin_Step = R_LTin_Step;
				this.C_LTin_Controller = C_LTin_Controller;
				this.S_LTin_State_Machine = S_LTin_State_Machine;
			}
			
			public boolean Condition() 
			{
				if (S_LTin_State_Machine.pJunctionValue > (0 + 1 - S_LTin_State_Machine.probabilityOfAdhesion) && S_LTin_State_Machine.pJunctionValue <= (0 + 1 - S_LTin_State_Machine.probabilityOfAdhesion + (S_LTin_State_Machine.probabilityOfAdhesion))) {
						//System.out.println("Condition of transition S_LTin_State_Machine_Adhesion_Response_t2 is true");
						return true;
				}
				else {
					//System.out.println("Condition of transition S_LTin_State_Machine_Adhesion_Response_t2 is false");
					return false;
				}
			}
			public void Action() {
				S_LTin_State_Machine.move_finished = true;
			}
		};
	
	//0 + 1 - S_LTin_State_Machine.probabilityOfAdhesion + S_LTin_State_Machine.probabilityOfAdhesion
	//0
	public class t3 extends Transition {
			private LTin_Step R_LTin_Step;
			private LTin_Controller C_LTin_Controller;
			private LTin_State_Machine S_LTin_State_Machine;
				
				
			public 	t3(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine 
					S_LTin_State_Machine, State src, State tgt)
			{
				super("S_LTin_State_Machine_Adhesion_Response_t3", src, tgt);
				this.R_LTin_Step = R_LTin_Step;
				this.C_LTin_Controller = C_LTin_Controller;
				this.S_LTin_State_Machine = S_LTin_State_Machine;
				
				// KA: May need to check this if we look at events - not 100% sure transferred correctly from C++
			}
					
		};
}

public class t1 extends Transition {
		private LTin_Step R_LTin_Step;
		private LTin_Controller C_LTin_Controller;
		private LTin_State_Machine S_LTin_State_Machine;
			
			
		public 	t1(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine 
				S_LTin_State_Machine, State src, State tgt)
		{
			super("S_LTin_State_Machine_t1", src, tgt);
			this.R_LTin_Step = R_LTin_Step;
			this.C_LTin_Controller = C_LTin_Controller;
			this.S_LTin_State_Machine = S_LTin_State_Machine;
			
			// KA: May need to check this if we look at events - not 100% sure transferred correctly from C++
		}
				
			public boolean Condition() {
				if (S_LTin_State_Machine.contactedCell_ID == -1 && S_LTin_State_Machine.T.GetCounter() > 0) {
					//System.out.println("Condition of transition S_LTin_State_Machine_t1 is true");
					return true;
				}
				else {
					//System.out.println("Condition of transition S_LTin_State_Machine_t1 is false");
					return false;
				}
			}
			public void Action() {
				S_LTin_State_Machine.T.SetCounter(0);
				//System.out.println("Resetting Clock T");
			}
	};
public class t3 extends Transition {
		private LTin_Step R_LTin_Step;
		private LTin_Controller C_LTin_Controller;
		private LTin_State_Machine S_LTin_State_Machine;
			
			
		public 	t3(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine 
				S_LTin_State_Machine, State src, State tgt)
		{
			super("S_LTin_State_Machine_t3", src, tgt);
			this.R_LTin_Step = R_LTin_Step;
			this.C_LTin_Controller = C_LTin_Controller;
			this.S_LTin_State_Machine = S_LTin_State_Machine;
			
			// KA: May need to check this if we look at events - not 100% sure transferred correctly from C++
		}
				
			public boolean Condition() {
				if (S_LTin_State_Machine.contactedCell_ID != -1 && S_LTin_State_Machine.adhered == true && S_LTin_State_Machine.T.GetCounter() > 0) {
					//System.out.println("Condition of transition S_LTin_State_Machine_t3 is true");
					return true;
				}
				else {
					//System.out.println("Condition of transition S_LTin_State_Machine_t3 is false");
					return false;
				}
			}
			public void Action() {
				S_LTin_State_Machine.T.SetCounter(0);
				//System.out.println("Resetting Clock T");
			}
	};
public class t0 extends Transition {
		private LTin_Step R_LTin_Step;
		private LTin_Controller C_LTin_Controller;
		private LTin_State_Machine S_LTin_State_Machine;
			
			
		public 	t0(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine 
				S_LTin_State_Machine, State src, State tgt)
		{
			super("S_LTin_State_Machine_t0", src, tgt);
			this.R_LTin_Step = R_LTin_Step;
			this.C_LTin_Controller = C_LTin_Controller;
			this.S_LTin_State_Machine = S_LTin_State_Machine;
			
			// KA: May need to check this if we look at events - not 100% sure transferred correctly from C++
		}
				
			public void Action() {
				S_LTin_State_Machine.T.SetCounter(0);
				//System.out.println("Resetting Clock T");
			}
	};
public class t2 extends Transition {
		private LTin_Step R_LTin_Step;
		private LTin_Controller C_LTin_Controller;
		private LTin_State_Machine S_LTin_State_Machine;
			
			
		public 	t2(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine 
				S_LTin_State_Machine, State src, State tgt)
		{
			super("S_LTin_State_Machine_t2", src, tgt);
			this.R_LTin_Step = R_LTin_Step;
			this.C_LTin_Controller = C_LTin_Controller;
			this.S_LTin_State_Machine = S_LTin_State_Machine;
			
			// KA: May need to check this if we look at events - not 100% sure transferred correctly from C++
		}
				
			public boolean Condition() {
				if (S_LTin_State_Machine.contactedCell_ID != -1 && S_LTin_State_Machine.T.GetCounter() > 0) {
					//System.out.println("Condition of transition S_LTin_State_Machine_t2 is true");
					return true;
				}
				else {
					//System.out.println("Condition of transition S_LTin_State_Machine_t2 is false");
					return false;
				}
			}
			public void Action() {
				S_LTin_State_Machine.T.SetCounter(0);
				//System.out.println("Resetting Clock T");
			}
	};
public class t4 extends Transition {
		private LTin_Step R_LTin_Step;
		private LTin_Controller C_LTin_Controller;
		private LTin_State_Machine S_LTin_State_Machine;
			
			
		public 	t4(LTin_Step R_LTin_Step, LTin_Controller C_LTin_Controller, LTin_State_Machine 
				S_LTin_State_Machine, State src, State tgt)
		{
			super("S_LTin_State_Machine_t4", src, tgt);
			this.R_LTin_Step = R_LTin_Step;
			this.C_LTin_Controller = C_LTin_Controller;
			this.S_LTin_State_Machine = S_LTin_State_Machine;
			
			// KA: May need to check this if we look at events - not 100% sure transferred correctly from C++
		}
				
			public boolean Condition() {
				if (S_LTin_State_Machine.contactedCell_ID == -1 && S_LTin_State_Machine.T.GetCounter() > 0) {
					//System.out.println("Condition of transition S_LTin_State_Machine_t4 is true");
					return true;
				}
				else {
					//System.out.println("Condition of transition S_LTin_State_Machine_t4 is false");
					return false;
				}
			}
			public void Action() {
				S_LTin_State_Machine.T.SetCounter(0);
				//System.out.println("Resetting Clock T");
			}
	};
	
	}
