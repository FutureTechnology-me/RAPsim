package sgs.model.gridObjects;

import sgs.model.gridData.GridDataEnum;
import sgs.model.objectModels.ConstantPowerModel;
/**
 * 
 * @author bbreilin
 *
 */
public class CustomPowerPlant extends PowerPlant {
	
//	private static final NumericValue Default_PowerProduction = new NumericValue(2.0);

	public CustomPowerPlant(){
		super();
		this.objectName = "CustomPowerPlant";
		this.model = new ConstantPowerModel(this);
//    	setPowerProduction(Default_PowerProduction);		//variableSet.set(EnumPV.powerProduction, Default_PowerProduction);
//    	setPeakPower( CustomPowerPlant.Default_PowerProduction );
	}
	
	public GridDataEnum getEnum() {
		return GridDataEnum.CUSTOM_POWER_PLANT;
	}
}
