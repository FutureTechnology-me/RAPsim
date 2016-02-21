package sgs.model.gridObjects;

import java.util.GregorianCalendar;

import sgs.controller.simulation.Weather;
import sgs.model.gridData.GridDataEnum;
import sgs.model.variables.EnumPV;


/**
 * A SmartGridObject.
 * @author tobi, Kristofer Schweiger
 */
public class House extends Consumer {

    private static final double Default_PowerDemand = 1.0;

    /**
     * 
     */
    public House() {
    	super();
    	this.setProperties(true, true, EnumPV.powerDemand);
    	setPowerDemand(Default_PowerDemand); //variableSet.set(EnumPV.powerDemand, Default_PowerDemand);
    }

    /**
     * @param powerDemand
     */
    public void setPowerDemand(double powerDemand) {
    	super.variableSet.get(EnumPV.powerDemand).setValue(powerDemand);
    }
    
    // --------------------------------------------------------
    // --- Override -------------------------------------------
    
//    //@Override
//    public LinkedHashMap<String, String> getDataAsMap() {
//        LinkedHashMap<String, String> map = new LinkedHashMap<>();
//        map.put("current Voltage", String.format("%.2f", getBusVoltage()));
//        map.put("Active Power Demand", String.format("%.2f", powerDemand));
//  //      map.put("Active Power Demand", String.format("%.2f", activePowerDemand));
//   //     map.put("Reactive Power Demand", String.format("%.2f", reactivePowerDemand));
//        map.put("Current Power Consumption", String.format("%.2f", currentPowerConsumption));
//        for (int i = 0; i < getOffers().size(); i++) {
//            map.put("Offer " + i + " For", "" + String.format("%.2f", getOffers().get(i).getPrice()));
//            map.put("Offer " + i + " Buy", "" + String.format("%.2f", getOffers().get(i).getAmount()));
//        }
//        return map;
//    }
//
//    @Override
//    public void setData(ArrayList<String> data) {
//    	
//        powerDemand = Double.parseDouble(data.get(1));
//        for (int i = 3; i < data.size() - 1; i += 2) {
//        	double price  = testAndParseDouble(data.get(i  ));
//        	double amount = testAndParseDouble(data.get(i+1));
//            getOffers().get((i - 2) / 2).setPrice(price);
//            getOffers().get((i - 2) / 2).setAmount(amount);
//        }
//    }
//    
//    /** TODO: move it ??
//     * 
//     * @param number
//     * @return parsed number OR 0 at error
//     */
//    private static double testAndParseDouble(String number){
//    	
//    	double n=0;
//    	
//    	try{
//    		n = Double.parseDouble(number);
//    	}
//    	catch(NullPointerException | NumberFormatException ex){
//    		JOptionPane.showMessageDialog(null, "Some values are not numbers, setting them to zero.", "Error", JOptionPane.ERROR_MESSAGE);
//    	}
//    	
//    	return n;
//    }
    
    @Override
    public void setProductionToWeatherAndTime(GregorianCalendar currentTime, Weather weather) {
    	// in future maybe
    }
    
	@Override
	public GridDataEnum getEnum() {
		//return GridDataEnum.HOUSE;
		return null;
	}

    
}