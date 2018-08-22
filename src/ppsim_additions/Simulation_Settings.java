package ppsim_additions;

import java.util.ArrayList;

import simulation_main.Mason_Sim_Main;
import ycil_XMLUtilities.XMLFileUtilities;

public class Simulation_Settings 
{
	private static Simulation_Settings simParams = new Simulation_Settings();
	
	public static Simulation_Settings getInstance( ) {
	     return simParams;
	 }
	
	
	public double initialGridHeight;
	public double initialGridLength;
	public double inputCircumferencePercentage;
	public double simulationTime;
	public double thresholdBindProbability;
	
	public double maxVCAMProbability;
	public double retLigandTime;
	public double maxChemokineExpression;
	public int cellDivisionTime;
	public double chemoThreshold;
	
	// THIS MAY BE IN THE MODEL:
	public double lToDivisionTime;
	public double stromalCellDensity;
	public String stromalCellRETLigandPlacement;
	public double stromalCellCircumferencePercentage;
	public double percentStromaRETLigands;
	public double percentRETLigandNonStromal;
	public double numHoursRETLigandLToActive;
	//public double probabilityLTinLTiRETLigands;
	public double percentLTinfromFACStain;
	public int lTinInputTime;
	public String lTinInputRateGraphType;
	public double lTinInputRateGraphConstant;
	public double percentLTifromFACStain;
	public int lTiInputDelayTime;
	public int lTiInputTime;
	public String lTiInputRateGraphType;
	public double lTiInputRateGraphConstant;
	public boolean retLigandKnockOut;
	public boolean chemoKnockOut;
	//public boolean vcamKnockOut;
	public double chemoLowerLinearAdjust;
	public double chemoUpperLinearAdjust;
	public String ltoSetup;
	public boolean cellTrackingEnabled;
	//public boolean minByminTrackingImages;
	//public ArrayList<String> cellResponseMeasures;
	//public boolean twelveHourSnaps;
	//public boolean generateLToStats;
	//public String patchStatsOutputHours;
	public ArrayList<String> trackingHourRanges;
	//public ArrayList<Integer> patchStatHours;
	public double vcamSlope;
	
	public double trackingSnapStartHr;
	public double trackingSnapEndHr;
	
	// Constants:
	public final double HCELL_DIAMETER = 2;
	public final double LTO_DIAMETER = 6;
	
	// Not sure why this is not in the parameter file
	public double retLigandProbability = 1.0;
	/**
	 * Lower bound to use to generate cell speed. Can be changed on the console before running the simulation.
	 */
	public double cellSpeedMinLowBound = 0.95;
	
	/**
	 * Upper bound to use to generate cell speed. Can be changed on the console before running the simulation
	 * As 1 pixel is 4micrometres, initial coding represents an upper bound move of 16micrometres a minute
	 */
	public double cellSpeedMinUpBound = 2.2;
		
	// To add:
	//public boolean chemokine_in_environment = false;
	public double chemoSigThreshold = 3;
	
	/**
	 * If the environment is growing, the current length of the grid
	 * In RoboChart model we don't capture growth, so we set this to the initial length
	 */
	public double currentGridLength;
	
	/**
	 * If the environment is growing, the current height of the grid
	 * In RoboChart model we don't capture growth, so we set this to the initial height
	 */
	public double currentGridHeight;
	
	
	public Simulation_Settings()
	{
		XMLFileUtilities.readSettingsFile(Mason_Sim_Main.paramFilePath);
		
		initialGridHeight = Double.valueOf(XMLFileUtilities.getParamDouble("initialGridHeight"));
		// Copy to current as we're not capturing growth (saves rewriting lots of code that monitors growth)
		currentGridHeight = initialGridHeight;
		initialGridLength = Double.valueOf(XMLFileUtilities.getParamDouble("initialGridLength"));
		// Copy to current as we're not capturing growth (saves rewriting lots of code that monitors growth)
		currentGridLength = initialGridLength;
		inputCircumferencePercentage = Double.valueOf(XMLFileUtilities.getParamDouble("inputCircumferencePercentage"));
		// Simulation time is scaled here to ease reference later
		simulationTime = Integer.valueOf(XMLFileUtilities.getParamInteger("simulationTime"))*60;
		thresholdBindProbability = Double.valueOf(XMLFileUtilities.getParamDouble("thresholdBindProbability"));
		stromalCellDensity = Integer.valueOf(XMLFileUtilities.getParamInteger("stromalCellDensity"));
		stromalCellRETLigandPlacement = XMLFileUtilities.getParam("stromalCellRETLigandPlacement");
		stromalCellCircumferencePercentage = Double.valueOf(XMLFileUtilities.getParamDouble("stromalCellCircumferencePercentage"));
		percentStromaRETLigands = Double.valueOf(XMLFileUtilities.getParamDouble("percentStromaRETLigands"));
		percentRETLigandNonStromal = Double.valueOf(XMLFileUtilities.getParamDouble("percentRETLigandNonStromal"));
		numHoursRETLigandLToActive = Integer.valueOf(XMLFileUtilities.getParamInteger("numHoursRETLigandLToActive"));
		//probabilityLTinLTiRETLigands = Double.valueOf(XMLFileUtilities.getParamDouble("probabilityLTinLTiRETLigands"));
		//retLigandTime = XMLFileUtilities.getParamInteger("imLToActiveTime");
		// Cell division time is scaled here to ease reference later
		cellDivisionTime = Integer.valueOf(XMLFileUtilities.getParamInteger("lToDivisionTime"))*60;
		percentLTinfromFACStain = Double.valueOf(XMLFileUtilities.getParamDouble("percentLTinfromFACStain"));
		lTinInputTime = Integer.valueOf(XMLFileUtilities.getParamInteger("lTinInputTime"));
		lTinInputRateGraphType = XMLFileUtilities.getParam("lTinInputRateGraphType");
		lTinInputRateGraphConstant = Double.valueOf(XMLFileUtilities.getParamDouble("lTinInputRateGraphConstant"));
		percentLTifromFACStain = Double.valueOf(XMLFileUtilities.getParamDouble("percentLTifromFACStain"));
		lTiInputDelayTime = Integer.valueOf(XMLFileUtilities.getParamInteger("lTiInputDelayTime"));
		lTiInputTime = Integer.valueOf(XMLFileUtilities.getParamInteger("lTiInputTime"));
		lTiInputRateGraphType = XMLFileUtilities.getParam("lTiInputRateGraphType");
		lTiInputRateGraphConstant = Double.valueOf(XMLFileUtilities.getParamDouble("lTiInputRateGraphConstant"));
		chemoThreshold = Double.valueOf(XMLFileUtilities.getParamDouble("chemoThreshold"));
		chemoUpperLinearAdjust = Double.valueOf(XMLFileUtilities.getParamDouble("chemoUpperLinearAdjust"));
		chemoLowerLinearAdjust = Double.valueOf(XMLFileUtilities.getParamDouble("chemoLowerLinearAdjust"));
		maxVCAMProbability = Double.valueOf(XMLFileUtilities.getParamDouble("maxVCAMeffectProbabilityCutoff"));
		vcamSlope = Double.valueOf(XMLFileUtilities.getParamDouble("vcamSlope"));
		//retLigandKnockOut = XMLFileUtilities.getParamBoolean("retLigandKnockOut").booleanValue();
		chemoKnockOut = XMLFileUtilities.getParamBoolean("chemoKnockOut").booleanValue();
		//vcamKnockOut = XMLFileUtilities.getParamBoolean("vcamKnockOut").booleanValue();
		ltoSetup = XMLFileUtilities.getParam("ltoSetup");
		cellTrackingEnabled = XMLFileUtilities.getParamBoolean("cellTrackingEnabled").booleanValue();
		//minByminTrackingImages = XMLFileUtilities.getParamBoolean("minByminTrackingImages").booleanValue();
		//cellResponseMeasures = XMLFileUtilities.getParamStringList("cellResponseMeasures");
		//twelveHourSnaps = XMLFileUtilities.getParamBoolean("twelveHourSnaps").booleanValue();
		//generateLToStats = XMLFileUtilities.getParamBoolean("generateLToStats").booleanValue();
		//patchStatsOutputHours = XMLFileUtilities.getParam("patchStatsOutputHours");
		trackingHourRanges = XMLFileUtilities.getParamStringList("trackingHourRanges");
		//patchStatHours = XMLFileUtilities.getParamIntegerList("patchStatsOutputHours");
	}
	
}
