package sgs.model.simulation;
import java.util.LinkedList;

import sgs.model.gridObjects.PowerPlant;
import sgs.model.gridObjects.SmartGridObject;
import sgs.model.variables.EnumPV;
import sgs.model.variables.NumericValue;
import sgs.model.variables.VariableSet;
import sgs.model.variables.collector.VariableCollection;
import sgs.model.variables.collector.VariableOwner;

/**
 * A bus combines multiple (1 or more) grid objects, which are not seperated by any resistance.
 * 
 * (Was named Cluster before)
 * @author Tobi, Schweiger
 */
public class Bus implements VariableOwner {
	private int busNumber;
	
	public LinkedList<SmartGridObject> busGridObjects;
	public VariableCollection variableCollection;
	
	private NumericValue currentBusVoltage = new NumericValue(1.0);
	private VariableSet tmpVariableSet;
	private NumericValue tmpNettoPowerProduction;

	
	/**
	 * Constructor: 
	 * @param number
	 * @param busGridObjects
	 */
	public Bus(int busNumber, LinkedList<SmartGridObject> busGridObjects, VariableCollection variableCollection){
		this.busNumber= busNumber;
		this.busGridObjects = busGridObjects;
		this.variableCollection = variableCollection;
	}

	
	/** 
	 * Call after values were refreshed.
	 * 
	 * @return 	true if real netto power production <= 0
	 * @see {@link #refreshValues(), #isGeneratorBus(), #isSwingBus()}}
	 */
	public boolean isLoadBus(){
		assert tmpNettoPowerProduction!=null;
		double realNettoPowerProduction = tmpNettoPowerProduction.r();
		return realNettoPowerProduction <= 0;
	}
	/** 
	 * Call after values were refreshed.
	 * 
	 * @return 	true if real netto power production >= 0. 
	 * 			false otherwise or at error.
	 * @see {@link #refreshValues(), #isLoadBus(), #isSwingBus()}}
	 */
	public boolean isGeneratorBus(){
		assert tmpNettoPowerProduction!=null;
		double realNettoPowerProduction = tmpNettoPowerProduction.r();
		return realNettoPowerProduction >= 0;
	}
	
	/**
	 * @return (powerProduction-powerDemand) or null, if values do not exist
	 */
	public NumericValue getNettoPowerProduction() {
		return tmpNettoPowerProduction;
	}
	
	/**
	 * Calculate netto power from VariableSet (uses powerProduction, powerDemand)
	 */
	private void calculateNettoPowerProduction(){
		try{
			NumericValue powerProduction = tmpVariableSet.get(EnumPV.powerProduction).getValueNumeric();
			NumericValue powerDemand = tmpVariableSet.get(EnumPV.powerDemand).getValueNumeric();
			
			tmpNettoPowerProduction = powerProduction.copy().subtract(powerDemand);
		}
		catch(NullPointerException e){
			tmpNettoPowerProduction = null;
		}
	}
	
	/**
	 * @param sgo
	 * @return true, if object is contained in bus
	 */
	public boolean contains(SmartGridObject sgo){
		return getGridObjects().contains(sgo);
	}

	/**
	 * @param sgo
	 * @return true, if any object of the same type as sgo is contained in the bus. recommended to use with constructor as argument sgo
	 */
	public boolean containsInstanceOf(SmartGridObject sgo){
		Class<?> cls = sgo.getClass();
		boolean flag = false;
		for (int cnt=0;  cnt<busGridObjects.size(); cnt++ ){
			if ( busGridObjects.get(cnt).getClass().equals(cls)) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	
	/**
	 * Add object to bus
	 * @param sgo
	 */
	public void addObject(SmartGridObject sgo){
		busGridObjects.add(sgo);
	}

	/**
	 * @param objects the objects to set
	 */
	public void setGridObjects(LinkedList<SmartGridObject> objects) {
		this.busGridObjects = objects;
	}

	/**
	 * @return the contained grid objects
	 */
	public LinkedList<SmartGridObject> getGridObjects() {
		return busGridObjects;
	}

	/** @return the busNumber **/
	public int getNumber() {
		return busNumber;
	}

	/** @param busNumber - busNumber to set **/
	public void setBusNumber(int busNumber) {
		this.busNumber = busNumber;
	}

	/** @param voltage - voltage to set for every object (special) **/
	public void setBusVoltage(NumericValue voltage){
		currentBusVoltage = voltage;
		double voltageAngle = Math.acos(currentBusVoltage.getReal()/currentBusVoltage.abs());
		voltageAngle = Math.round(voltageAngle*100000)/100000.0;
		voltageAngle = voltageAngle/Math.PI*180.0;
		for (SmartGridObject sgo : busGridObjects){
			sgo.setCurrentVoltage(voltage);
			sgo.variableSet.get(EnumPV.voltageAngle).setValue(voltageAngle);
		}
	}

	/** @return the Bus voltage (should be equal for whole bus), stored locally **/
	public NumericValue getBusVoltage(){
		for(SmartGridObject sgo: busGridObjects){
			if(sgo instanceof PowerPlant){
				if(currentBusVoltage.getReal() != sgo.variableSet.get(EnumPV.currentVoltage).getValueNumeric().getReal() 
						|| currentBusVoltage.getImaginary() != sgo.variableSet.get(EnumPV.currentVoltage).getValueNumeric().getImaginary()){
					this.setBusVoltage(sgo.variableSet.get(EnumPV.currentVoltage).getValueNumeric());
				}
			}
		}
		return currentBusVoltage;
	}
	
	@Override
	public VariableSet getVariableSet() {		// ------------ new default for this type of object
		return tmpVariableSet;
	}
	
	/**
	 * @param variableName
	 * @return value for variableName, or null if name is not found.
	 */
	public NumericValue getValue(EnumPV variableName){		// ------------ new default for this type of object
		return variableCollection.getValue(variableName);
	}
	
	/**
	 * Collects current simulation values with variableCollector
	 */
	public void refreshValues(){		// ------------ new default for this type of object
		variableCollection.restValues();
		for(SmartGridObject sgo : busGridObjects)
			variableCollection.collectFrom(sgo);
		
		tmpVariableSet = variableCollection.getVariableSet();
		calculateNettoPowerProduction();
	}

	@Override
	public String toString(){
		return "Bus"+busNumber;
	}
}
