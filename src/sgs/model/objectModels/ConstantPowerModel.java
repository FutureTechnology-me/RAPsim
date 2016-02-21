package sgs.model.objectModels;

import java.util.GregorianCalendar;

import javax.swing.ImageIcon;

import sgs.controller.simulation.Weather;
import sgs.model.gridObjects.PowerPlant;

/**
 * 
 * @author bbreilin
 *
 */

public class ConstantPowerModel extends AbstractCustomPowerPlantModel{
	
	public ConstantPowerModel( PowerPlant powerPlant) {
		super(powerPlant);
// public class ConstantPowerModel extends AbstractFossilFuelPowerPlantModel{
// public ConstantPowerModel(FossilFuelPowerPlant powerPlant) {		super(powerPlant);
//		initVariableSet();
		icon = new ImageIcon("Data2/GeothermalPowerPlant_ICON.png");
		modelName = "ConstantPowerModel";
		description = "Constant generatioin with peak power. ";
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateVariables(GregorianCalendar currentTime,
			Weather weather, int resolution) {
		this.powerProduction = this.ratedPower;
		
//		powerPlant.setPowerProduction(this.powerProduction.getValueNumeric());
//		powerPlant.setPeakPower(this.ratedPower.getValueNumeric());

	}

	@Override
	protected void initVariableSet() {
	}

}
