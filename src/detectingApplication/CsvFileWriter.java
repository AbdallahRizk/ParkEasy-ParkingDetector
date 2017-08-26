package detectingApplication;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CsvFileWriter {
	 
	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	//CSV file header
	private static final String FILE_HEADER = "Mall name,Parking lot no,Level,Availbility status,Parking lot type,Lat 1,Lng 1,Lat 2,Lng 2,Lat 3,Lng 3"
			+ ",Lat 4,Lng 4,Entrance Lat,Entrance Lng,Navigation Point ID";
	static String sCurrentLine;
	String new_string;
	public static void writeCsvFile(String fileName,String txt_file_name) throws IOException {
		FileWriter fileWriter = null;
		
		fileWriter = new FileWriter(fileName);
		fileWriter.append(FILE_HEADER.toString());
        fileWriter.append(NEW_LINE_SEPARATOR);
        
		 try (BufferedReader br = new BufferedReader(new FileReader(txt_file_name))){
			 while ((sCurrentLine = br.readLine()) != null) {
				 String[] anArr1 = sCurrentLine.split("\\)\\s*");
				 				 
				 		String[] anArr  = anArr1[0].split("\\(\\s*");
				 		
				        int x0 = Integer.parseInt(anArr[1].substring(0,anArr[1].indexOf(",")));
				        int y0 = Integer.parseInt(anArr[1].substring(anArr[1].indexOf(",") + 1, anArr[1].length()));
				        
				        int x1 = Integer.parseInt(anArr1[1].substring(1,anArr1[1].indexOf(",")));
				        int y1 = Integer.parseInt(anArr1[1].substring(anArr1[1].indexOf(",") + 1, anArr1[1].length()));
				        
				        int x2 = Integer.parseInt(anArr1[2].substring(1,anArr1[2].indexOf(",")));
				        int y2 = Integer.parseInt(anArr1[2].substring(anArr1[2].indexOf(",") + 1, anArr1[2].length()));
				        
				        int x3 = Integer.parseInt(anArr1[3].substring(1,anArr1[3].indexOf(",")));
				        int y3 = Integer.parseInt(anArr1[3].substring(anArr1[3].indexOf(",") + 1, anArr1[3].length()));
				        
				        String [] a0 = anArr[0].split("\\     \\s*");
				        String [] a1 = anArr1[4].split("\\     \\s*");
				        
				        fileWriter.append(a0[0]);
						fileWriter.append(COMMA_DELIMITER);
						fileWriter.append(a0[1]);
						fileWriter.append(COMMA_DELIMITER);
						fileWriter.append(a0[2]);
						fileWriter.append(COMMA_DELIMITER);
						fileWriter.append(a0[3]);
						fileWriter.append(COMMA_DELIMITER);
						fileWriter.append(a0[4]);
						fileWriter.append(COMMA_DELIMITER);
						fileWriter.append(String.valueOf(x0));
						fileWriter.append(COMMA_DELIMITER);
						fileWriter.append(String.valueOf(y0));
						fileWriter.append(COMMA_DELIMITER);
						fileWriter.append(String.valueOf(x1));
						fileWriter.append(COMMA_DELIMITER);
						fileWriter.append(String.valueOf(y1));
						fileWriter.append(COMMA_DELIMITER);
						fileWriter.append(String.valueOf(x2));
						fileWriter.append(COMMA_DELIMITER);
						fileWriter.append(String.valueOf(y2));
						fileWriter.append(COMMA_DELIMITER);
						fileWriter.append(String.valueOf(x3));
						fileWriter.append(COMMA_DELIMITER);
						fileWriter.append(String.valueOf(y3));
						fileWriter.append(COMMA_DELIMITER);
						fileWriter.append(a1[0]);
						fileWriter.append(COMMA_DELIMITER);
						fileWriter.append(a1[1]);
						fileWriter.append(COMMA_DELIMITER);
						fileWriter.append(a1[2]);
						fileWriter.append(NEW_LINE_SEPARATOR);
	
			 }
			 System.out.println("CSV file was created successfully !!!");

			 br.close();
		 }catch (IOException e1) {
				e1.printStackTrace();
		 } 

		 finally {
			
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
			}
			
		}
	}
}