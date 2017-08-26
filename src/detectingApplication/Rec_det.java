package detectingApplication;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.*;

import java.io.PrintWriter;
import java.util.*;


public class Rec_det {
	String file_name,String1, String3,dll_path;
	int bottoming;
	public String getDll_path() {
		return dll_path;
	}
	public void setDll_path(String dll_path) {
		this.dll_path = dll_path;
	}
	int bottomLat;
	int topLng;
	int topLat;
  public int getBottoming() {
		return bottoming;
	}
	public void setBottoming(int bottoming) {
		this.bottoming = bottoming;
	}
	public int getBottomLat() {
		return bottomLat;
	}
	public void setBottomLat(int bottomLat) {
		this.bottomLat = bottomLat;
	}
	public int getTopLng() {
		return topLng;
	}
	public void setTopLng(int topLng) {
		this.topLng = topLng;
	}
	public int getTopLat() {
		return topLat;
	}
	public void setTopLat(int topLat) {
		this.topLat = topLat;
	}
public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getString1() {
		return String1;
	}
	public void setString1(String string1) {
		String1 = string1;
	}
	public String getString3() {
		return String3;
	}
	public void setString3(String string3) {
		String3 = string3;
	}
public void det(String file_path ){
	
   
      try{

    	 // System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	  System.load(dll_path);
    	    // reading image 
    	    Mat image = Highgui.imread( file_path, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
    	       	   
    	    
    	    Mat destination1 = new Mat(image.rows(),image.cols(),image.type());
    	    Mat destination2 = new Mat(image.rows(),image.cols(),image.type());
    	    destination2.empty();
    	    
    	    int x = image.height();
    	   // System.out.print(x);
    	    // thresholding the image to make a binary image
    	    
    	    	 Imgproc.threshold(image, image, 250, 255, Imgproc.THRESH_BINARY_INV);
    	    	 Imgproc.threshold(image, destination1, 250, 255, Imgproc.THRESH_BINARY_INV);
    	    	 Highgui.imwrite("thresh.jpg", image);
    	    	    
    	   
    	    int erode_size = 1;
            
            Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new  Size(2*erode_size + 1, 2*erode_size+1));
            Imgproc.dilate(image, image, element);
            Highgui.imwrite("dilation.jpg", image);
            
    	    // finding the contours
    	    ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    	        	   
    	    int n=0;
    	    int imagearea = image.width() * image.height();
    	    
    	    Imgproc.findContours(image, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
            Imgproc.drawContours(image, contours, -1, new Scalar(255,255,0));
            
            //Highgui.imwrite("result2.jpg", image);
            
            PrintWriter out = new PrintWriter(file_name);
            String format = "%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%s%n";
            
            //finding the bounding box for each contour
            for (MatOfPoint contour : contours) {
    	        Rect rect = Imgproc.boundingRect(contour);
    	       
    	        double contourarea = rect.area();
    	        
    	        int temp = 0;
    	        
    	        if (rect.width > rect.height){
    	        	 temp = rect.width / rect.height;
    	        }
    	        else{
    	        	temp = rect.height / rect.width;
    	        }
    	       
    	        double imgvscon_area = imagearea / contourarea;
    	       
	    	        if(temp > 1.9 && temp < 2.6 && imgvscon_area>1000 && imgvscon_area<6000 ){
	    	        	 n = n +1;
	    	        	//System.out.println(contourarea);
	    	        	//save the coordinates in a test file ("Coordinates.txt")
	    	        	 double xresult = bottoming+((rect.x*Math.abs((bottoming-topLng)))/rect.width);
	    	        	 double yresult = bottomLat+((rect.y*Math.abs((bottomLat-topLat)))/rect.height);
	    	        	 
	    	        	 if(file_name == "Coordinates_temp.txt"){
	    	        		 rect.x = (int)xresult;
	    	        		 rect.y = (int)yresult;
	    	        	 }
	    	        	out.printf(format, String1,"Parking lot no",String3,"av","normal","(" +rect.x +","+ rect.y+")","("+(rect.x + rect.width)+","+rect.y+")","("+(rect.x + rect.width)+","+(rect.y + rect.height)+")","("+rect.x+","+(rect.y + rect.height)+")","Entrance Lat","Entrance Lng","Navigation Point ID" );
	    	        	 
	    	            Core.rectangle( destination2, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 255, 0));
	    	        }
    	                   
    	    } 	    
            out.close();
            
            //no of detected rectangles
            System.out.println(n);  	      	 
    	    
            //save the detected rectangle image
            Highgui.imwrite("result_new.jpg", destination2);
    	   
      }
         catch (Exception e) {
         System.out.println("error: " + e.getMessage());
      }
   }
   
}

