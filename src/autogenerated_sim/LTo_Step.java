package autogenerated_sim;

public class LTo_Step {
	

	// Objects in Java version, were extended in C++ (but no multiple inheritance in Java)
	public LTo_Attributes LTo_Step_LTo_Attributes;
	public Operations LTo_Step_Operations;

	public LTo_Step()
	{
		this.LTo_Step_LTo_Attributes = new LTo_Attributes();
		this.LTo_Step_Operations = new Operations();
	}
		
		
	public void Sensors() {
		this.LTo_Step_LTo_Attributes.Sensors();
		this.LTo_Step_Operations.Sensors();
	}
	
	public void Actuators() {
		this.LTo_Step_LTo_Attributes.Actuators();
		this.LTo_Step_Operations.Actuators();
	}
	
	


}
		