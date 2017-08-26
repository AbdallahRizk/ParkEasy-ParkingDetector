package detectingApplication;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Image;

import java.awt.Graphics2D;
//import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import java.io.FileWriter;


import detectingApplication.Rec_det;
import org.eclipse.wb.swt.SWTResourceManager;

public class gui_rec {
	//private static JFileChooser jfc = new JFileChooser();
	Display display = Display.getDefault();
	protected Shell shlGui;
	
	String file_path= null;
	String file_name = null;
	String String1_v;
	String String3_v,dll_path;
	int bottoming_v=0;
	int bottomLat_v=0;
	int topLng_v=0;
	int topLat_v=0;
	private Text txt_open;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			gui_rec window = new gui_rec();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		//Display display = Display.getDefault();
		createContents();
		shlGui.open();
		shlGui.layout();
		while (!shlGui.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlGui = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN );
		shlGui.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		shlGui.setSize(945, 566);
		
		//shell.setMaximized(true);
		
		shlGui.setText("Graphical User Interface");
		//scrolledComposite.setMinSize(lbl_Image.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		Label lbl_image = new Label(shlGui, SWT.BORDER | SWT.CENTER);
		lbl_image.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lbl_image.setBounds(10, 10, 361, 465);
		
		txt_open = new Text(shlGui, SWT.BORDER);
		txt_open.setBounds(93, 493, 278, 25);
		txt_open.setEditable(false);
		
		
		
		Label lbl_rec = new Label(shlGui, SWT.BORDER | SWT.CENTER);
		lbl_rec.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		lbl_rec.setBounds(408, 10, 361, 465);
		
		Button btnNewButton = new Button(shlGui, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Rec_det detect = new Rec_det();
				detect.setDll_path(dll_path);
				detect.setFile_name("Coordinates_temp.txt");
				detect.setString1(String1_v);
				detect.setString3(String3_v);
				detect.setBottoming(bottoming_v);
				detect.setBottomLat(bottomLat_v);
				detect.setTopLat(topLat_v);
				detect.setTopLng(topLng_v);
				detect.det(file_path);
				detect.setFile_name("Coordinates.txt");
				detect.det(file_path);
				image_resize(lbl_image, "result_new.jpg");
				
				CsvFileWriter csv = new CsvFileWriter();
				try {
					csv.writeCsvFile("Coordinates_result.csv","Coordinates_temp.txt");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}   
	            lbl_rec.setImage(new Image(display, "saved.png"));;
			}
		});
		btnNewButton.setBounds(530, 493, 112, 25);
		btnNewButton.setText("initiate recognition");
		
		Button btnOpen = new Button(shlGui, SWT.NONE);
		btnOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				FileDialog dlg = new FileDialog(shlGui, SWT.MULTI);
		        
		        String fn = dlg.open();
		        if (fn != null) {
		        	txt_open.setText(dlg.getFilterPath());
		        	file_name = dlg.getFileName();
		            file_path = dlg.getFilterPath();
		            file_path = file_path+File.separator+file_name;
					//System.out.println(file_path);
		            image_resize(lbl_image,file_path);		            	            
		           
		            lbl_image.setImage(new Image(display, "saved.png"));;
		        }
		     
			}
		});
		
		btnOpen.setBounds(10, 493, 75, 25);
		btnOpen.setText("Open");
		
		Button btnOpenInNew = new Button(shlGui, SWT.NONE);
		btnOpenInNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				BufferedImage img;
				try {
					img = ImageIO.read(new File("result_new.jpg"));
					image_Open_nw nw = new image_Open_nw();
					nw.Mouse(img);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				CsvFileWriter csv = new CsvFileWriter();
				try {
					csv.writeCsvFile("Coordinates_original.csv","Coordinates.txt");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnOpenInNew.setBounds(788, 93, 141, 25);
		btnOpenInNew.setText("Open in new window");
		
		Label lblToViewThe = new Label(shlGui, SWT.NONE);
		lblToViewThe.setAlignment(SWT.CENTER);
		lblToViewThe.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		lblToViewThe.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblToViewThe.setBounds(788, 11, 141, 65);
		lblToViewThe.setText("To view the output image\r\nof the Recognition process\r\nmore clearly and for the \r\npost processing ");
		
		Button btnNewButton_1 = new Button(shlGui, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				/*CsvFileWriter csv = new CsvFileWriter();
				try {
					csv.writeCsvFile("eee.csv");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				User_Input();
			}
		});
		btnNewButton_1.setBounds(821, 491, 75, 25);
		btnNewButton_1.setText("User Inputs");
		
		Button btnNewButton_2 = new Button(shlGui, SWT.NONE);
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
					FileDialog dlg = new FileDialog(shlGui, SWT.MULTI);
		        
		        String fn = dlg.open();
		        if (fn != null) {
		        	txt_open.setText(dlg.getFilterPath());
		        	file_name = dlg.getFileName();
		            file_path = dlg.getFilterPath();
		            dll_path = file_path+File.separator+file_name;
					
		        }
			}
		});
		btnNewButton_2.setBounds(821, 392, 75, 25);
		btnNewButton_2.setText("Set dll");
				
		
	}
	
	protected void image_resize( Label lbl_image, String file_path){

		try {
			 BufferedImage img1 = ImageIO.read(new File(file_path));
										
			int h = img1.getHeight(null);
			int w = img1.getWidth(null);
			
			int temp=0,temp1 = 0;
			
			for(int i=1; i< 100; i++){
				 
	             temp = h/i;
	             temp1 = w/i;
	             //System.out.println(temp);
	             if(temp < lbl_image.getSize().y && temp1 < lbl_image.getSize().x){
	            	 i = 102;
	             }
	          
	         }
			
		   BufferedImage resizedImg = new BufferedImage(temp1, temp, BufferedImage.TRANSLUCENT);
		   Graphics2D g2 = resizedImg.createGraphics();
		   g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		   g2.drawImage(img1, 0, 0,temp1, temp, null);
		   g2.dispose();
            
            File outputfile = new File("saved.png");
            ImageIO.write(resizedImg, "png", outputfile); 
            
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	public static void writeCsvFile(String fileName){
		final String FILE_HEADER = "header1,header2,header3,header4,header5,x1 header,y1 header,x2 header,y2 header,x3 header,y3 header"
				+ ",x4 header,y4 header,header6,header7,header8";
		final String NEW_LINE_SEPARATOR = "\n";
		final String COMMA_DELIMITER = ",";


		FileWriter fileWriter = null;
		try{
			fileWriter = new FileWriter(fileName);
			fileWriter.append(FILE_HEADER.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append("dwafa");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("fafa");
			
			 System.out.println("CSV file was created successfully !!!");

		}catch(Exception e){
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();

		}finally{
			try{
				fileWriter.flush();
				fileWriter.close();

			}catch(IOException e){
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
				
			}
		}

	}
	
	protected void User_Input(){
		JFrame frame = new JFrame("Processed Image");
		JPanel userin_panel = new JPanel();
		
		JButton save = new JButton("Save");
		JLabel bottoming = new JLabel("Bottom Lng");
		JLabel bottomLat = new JLabel("Bottom Lat");
		JLabel topLng = new JLabel("Top Lng");
		JLabel topLat = new JLabel("Top Lat");
		JLabel String1 = new JLabel("Mall ID");
		JLabel String3 = new JLabel("Level");
		
		JTextField bottoming_txt = new JTextField (30);
		JTextField bottomLat_txt = new JTextField (30);
		JTextField topLng_txt = new JTextField (30);
		JTextField topLat_txt = new JTextField (30);
		JTextField String1_txt = new JTextField (30);
		JTextField String3_txt = new JTextField (30);
		
		bottoming.setBounds(10, 10, 100, 25);
		bottomLat.setBounds(10, 45, 100, 25);
		topLng.setBounds(10, 80, 100, 25);
		topLat.setBounds(10, 115,100, 25);
		String1.setBounds(10,150 , 100, 25);
		String3.setBounds(10,185 , 100, 25);
		
		bottoming_txt.setBounds(130, 10, 100, 25);
		bottomLat_txt.setBounds(130, 45, 100, 25);
		topLng_txt.setBounds(130, 80, 100, 25);
		topLat_txt.setBounds(130, 115, 100, 25);
		String1_txt.setBounds(130, 150, 100, 25);
		String3_txt.setBounds(130, 185, 100, 25);
		
		save.setBounds(130, 240, 100,25);
		frame.setSize(270,325);
		userin_panel.setBounds(800, 800, 200, 100);
		userin_panel.setLayout(null);
		userin_panel.add(save);
		userin_panel.add(bottoming);
		userin_panel.add(bottomLat);
		userin_panel.add(topLng);
		userin_panel.add(topLat);
		userin_panel.add(String1);
		userin_panel.add(String3);
		
		userin_panel.add(bottoming_txt);
		userin_panel.add(bottomLat_txt);
		userin_panel.add(topLng_txt);
		userin_panel.add(topLat_txt);
		userin_panel.add(String1_txt);
		userin_panel.add(String3_txt);
		
		bottoming_txt.addKeyListener(new KeyAdapter()
		{
		      public void keyTyped(KeyEvent ke)
		{


		    char c = ke.getKeyChar();
		    if((!(Character.isDigit(c))) && // Only digits
		    (c != '\b') ) // For backspace
		    {
		         ke.consume();
		    }
		 }

		public void keyReleased(KeyEvent e){}
		public void keyPressed(KeyEvent e){}
		});
		
		bottomLat_txt.addKeyListener(new KeyAdapter()
		{
		      public void keyTyped(KeyEvent ke)
		{


		    char c = ke.getKeyChar();
		    if((!(Character.isDigit(c))) && // Only digits
		    (c != '\b') ) // For backspace
		    {
		         ke.consume();
		    }
		 }

		public void keyReleased(KeyEvent e){}
		public void keyPressed(KeyEvent e){}
		});
		
		
		topLng_txt.addKeyListener(new KeyAdapter()
		{
		      public void keyTyped(KeyEvent ke)
		{


		    char c = ke.getKeyChar();
		    if((!(Character.isDigit(c))) && // Only digits
		    (c != '\b') ) // For backspace
		    {
		         ke.consume();
		    }
		 }

		public void keyReleased(KeyEvent e){}
		public void keyPressed(KeyEvent e){}
		});
		
		
		topLat_txt.addKeyListener(new KeyAdapter()
		{
		      public void keyTyped(KeyEvent ke)
		{


		    char c = ke.getKeyChar();
		    if((!(Character.isDigit(c))) && // Only digits
		    (c != '\b') ) // For backspace
		    {
		         ke.consume();
		    }
		 }

		public void keyReleased(KeyEvent e){}
		public void keyPressed(KeyEvent e){}
		});
		
		
		frame.getContentPane().add(userin_panel);
		frame.setVisible(true);
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
		    {
				bottoming_v = Integer.valueOf(bottoming_txt.getText());
				bottomLat_v = Integer.valueOf(bottomLat_txt.getText());
				topLng_v = Integer.valueOf(topLng_txt.getText());
				topLat_v = Integer.valueOf(topLat_txt.getText());
				String1_v = String1_txt.getText();
				String3_v = String3_txt.getText();
		       frame.dispose();
		    }
		});
		
		
	}
}
