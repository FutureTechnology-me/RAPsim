package sgs.model;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


/**
 * Interface holding a few constants for the Controller.
 * Use e.g. with static import.
 * Variables in interface are automatically final.
 * 
 * @author Kristofer Schweiger
 */
public interface ProgramConstants {
	
	/** path to image files and other application data files **/
	public static String dataPath  = "Data2/";
	
	/** default file in dataPath where project is stored **/
	public static File defaultSimulationFile = new File(dataPath+"data.zip");
	
	/** file in dataPath where the program configuration is stored **/
	public static File parameterFile = new File(dataPath+"parameters.zip");
	
	/** the date format used **/
	public static DateFormat df = new SimpleDateFormat("yyyy.MM.dd - HH:mm");
	
}
