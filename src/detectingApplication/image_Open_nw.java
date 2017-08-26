package detectingApplication;

import java.awt.BorderLayout; 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.eclipse.swt.widgets.Display;

public class image_Open_nw {
	Display display = Display.getDefault();
	String sCurrentLine, new_string;
	int gx,gy,gw,gh;
	int changed_line =0;
       public void Mouse(BufferedImage img) {
    	   Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                try {
				    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
				}

				//BufferedImage img = ImageIO.read(new File("result.jpg"));
				final ImagePanel imgPane = new ImagePanel(img);
				JScrollPane scrollPane = new JScrollPane(imgPane);
				Graphics g = img.getGraphics();
				final JLabel report = new JLabel("...");
					            
				final JButton bt = new JButton("save");
				bt.setPreferredSize(new Dimension(55,20));
				//Color color = new Color(255, 255, 0, 255 * 50 / 100);
				
				imgPane.addMouseListener(new MouseAdapter() {
				    @Override
				    public void mouseClicked(MouseEvent e) {
				        Point panelPoint = e.getPoint();
				        Point imgContext = imgPane.toImageContext(panelPoint);
				      
				       
				        JFrame f = new JFrame();
			            f.setUndecorated(true);
			            f.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			            f.getContentPane().setLayout(new FlowLayout());
				       	//f.setAlwaysOnTop(true);
			            
			            //report.setText("clicked at " + scrollPane.getVerticalScrollBar().getHeight() + "    " + scrollPane.getHeight() );
				                                  
				        try (BufferedReader br = new BufferedReader(new FileReader("Coordinates.txt")))
						{
				        	             			                    			
							while ((sCurrentLine = br.readLine()) != null) {
							
								 
								 String[] anArr1 = sCurrentLine.split("\\)\\s*");
								 List<Point> points = new ArrayList<Point>();
								 
								 		String[] anArr  = anArr1[0].split("\\(\\s*");
								 		
								        int x = Integer.parseInt(anArr[1].substring(0,anArr[1].indexOf(",")));
								        int y = Integer.parseInt(anArr[1].substring(anArr[1].indexOf(",") + 1, anArr[1].length()));
								        int x1 = Integer.parseInt(anArr1[2].substring(1,anArr1[2].indexOf(",")));
								        int y1 = Integer.parseInt(anArr1[2].substring(anArr1[2].indexOf(",") + 1, anArr1[2].length()));
								        
								        Point p = new Point(x,y);
								        points.add(p);
								        
								        String [] a0 = anArr[0].split("\\     \\s*");
								        String [] a1 = anArr1[4].split("\\     \\s*");
								                           				                            				        
								        if(panelPoint.x > x && panelPoint.x < x1 && panelPoint.y > y && panelPoint.y < y1){
								        	Color color = new Color(0, 0, 0);
								        	g.setColor(color);
								        	g.fillRect(gx, gy, gw, gh);
								        	scrollPane.repaint();
								        	Color color1 = new Color(255, 255, 0, 255 * 50 / 100);
								        	g.setColor(color1);
								        	g.fillRect(x+4, y+4, x1-x-8, y1-y-8);
										    scrollPane.repaint();	
								        	JTextField Text1 = new JTextField(a0[0]);
								        	JTextField Text2 = new JTextField(a0[1]);
								        	JTextField Text2_1 = new JTextField(a0[2]);
								        	JTextField Text2_2 = new JTextField(a0[3]);
								        	JTextField Text2_3 = new JTextField(a0[4]);
								        	JTextField Text3 = new JTextField("  "+"("+anArr[1]+")"+ "  " + anArr1[1] + ")" + "  "+ anArr1[2] + ")" +"  "+ anArr1[3] + ")" );
								        	JTextField Text4 = new JTextField(a1[0]);
								        	JTextField Text5 = new JTextField(a1[1]);
								        	JTextField Text6 = new JTextField(a1[2]);
								        	
								        	Text3.setEditable(false);
								        	
								        	gx = x+4; gy=y+4; gw = x1-x-8; gh = y1-y-8;
								        	f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
									       // f.getContentPane().setLayout(new FlowLayout());
									        f.getContentPane().add(Text1);
									        f.getContentPane().add(Text2);
									        f.getContentPane().add(Text2_1);
									        f.getContentPane().add(Text2_2);
									        f.getContentPane().add(Text2_3);
									        f.getContentPane().add(Text3);
									        f.getContentPane().add(Text4);
									        f.getContentPane().add(Text5);
									        f.getContentPane().add(Text6);
									        
									        f.getContentPane().add(bt,BorderLayout.AFTER_LAST_LINE);           					        
				   					        f.pack();
				   					        
				   					        f.setLocation(scrollPane .getWidth()/2 - f.getWidth()/2,scrollPane .getHeight()/4);
									       
									        f.setVisible(true);
									        
									        bt.addMouseListener(new MouseAdapter(){
									        	@Override
						                        public void mouseClicked(MouseEvent e1) {
									        		f.setVisible(false);
									        		write("Coordinates.txt",panelPoint,Text1,Text2,Text2_1,Text2_2,Text2_3,Text3,Text4,Text5,Text6);
									        		write("Coordinates_temp.txt",panelPoint,Text1,Text2,Text2_1,Text2_2,Text2_3,Text3,Text4,Text5,Text6);
									        		
									        	}
									        });
							
								          }
								        
							
							}
							 br.close();
							
						} catch (IOException e1) {
							e1.printStackTrace();
						} 
				    }
				});

				JFrame frame = new JFrame("Processed Image");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(scrollPane);
				frame.add(report, BorderLayout.SOUTH);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
            }
        });
    }

    public void write(String filename,Point panelPoint,JTextField Text1,JTextField Text2,JTextField Text2_1,JTextField Text2_2,JTextField Text2_3,JTextField Text3,JTextField Text4,JTextField Text5,JTextField Text6){
    	String format = "%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%s%n";
        int line = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename)))
		{
        	PrintWriter out = new PrintWriter("Coordinates_new.txt");
        	
		                    			
			while ((sCurrentLine = br.readLine()) != null) {
				line = line +1;
				 
				 String[] anArr1 = sCurrentLine.split("\\)\\s*");
				 List<Point> points = new ArrayList<Point>();
				 
				 		String[] anArr  = anArr1[0].split("\\(\\s*");
				 		
				        int x = Integer.parseInt(anArr[1].substring(0,anArr[1].indexOf(",")));
				        int y = Integer.parseInt(anArr[1].substring(anArr[1].indexOf(",") + 1, anArr[1].length()));
				        int x1 = Integer.parseInt(anArr1[2].substring(1,anArr1[2].indexOf(",")));
				        int y1 = Integer.parseInt(anArr1[2].substring(anArr1[2].indexOf(",") + 1, anArr1[2].length()));
				        
				        Point p = new Point(x,y);
				        points.add(p);
				        
				        String [] a0 = anArr[0].split("\\     \\s*");
				        String [] a1 = anArr1[4].split("\\     \\s*");
				        
				        if(filename =="Coordinates.txt"){
					        if(panelPoint.x > x && panelPoint.x < x1 && panelPoint.y > y && panelPoint.y < y1){
					        	changed_line = line;
					        	
						        out.printf(format,Text1.getText(),Text2.getText(),Text2_1.getText(),Text2_2.getText(),Text2_3.getText(),"("+anArr[1]+")", anArr1[1] + ")" , anArr1[2] + ")" , anArr1[3] + ")" ,Text4.getText(),Text5.getText(),Text6.getText());
						  
					        }
					        else{
					        	out.printf(format,a0[0],a0[1],a0[2],a0[3],a0[4],"("+anArr[1]+")", anArr1[1] + ")" , anArr1[2] + ")" , anArr1[3] + ")" ,a1[0],a1[1],a1[2]);  
					        }
				        }
				        else{
				        	
				        	if(line == changed_line){
				        		out.printf(format,Text1.getText(),Text2.getText(),Text2_1.getText(),Text2_2.getText(),Text2_3.getText(),"("+anArr[1]+")", anArr1[1] + ")" , anArr1[2] + ")" , anArr1[3] + ")" ,Text4.getText(),Text5.getText(),Text6.getText());
				        	}
				        	else{
					        	out.printf(format,a0[0],a0[1],a0[2],a0[3],a0[4],"("+anArr[1]+")", anArr1[1] + ")" , anArr1[2] + ")" , anArr1[3] + ")" ,a1[0],a1[1],a1[2]);  
					        }
				        }
				        
			
			}
			 br.close();
			 out.close();
			
			 File realName = new File(filename);
			 realName.delete(); // remove the old file
			 new File("Coordinates_new.txt").renameTo(realName); 

		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        CsvFileWriter csv = new CsvFileWriter();
		try {
			if(filename == "Coordinates.txt" ){
				csv.writeCsvFile("Coordinates_original.csv",filename);
			}
			else{
				csv.writeCsvFile("Coordinates_result.csv",filename);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    
    }
    public class ImagePanel extends JPanel {

        private BufferedImage img;
                
        public ImagePanel(BufferedImage img) {
        	this.img =img;
        	
        	int h = img.getHeight(null);
			int w = img.getWidth(null);
			int ii=0;
			int temp=0,temp1 = 0;
			
			for(int i=1; i< 100; i++){
				 
	             temp = h/i;
	             temp1 = w/i;
	             //System.out.println(temp);
	             if(temp < 800){
	            	 ii = i;
	            	 i = 102;
	             }
	          
	         }
			   BufferedImage resizedImg = new BufferedImage(temp1, temp, BufferedImage.TRANSLUCENT);
			   Graphics2D g2 = resizedImg.createGraphics();
			   g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			   g2.drawImage(img, 0, 0,temp1, temp, null);
			   g2.dispose();
        	
           // this.img = resizedImg;
            
        }

        @Override
        public Dimension getPreferredSize() {
            return img == null ? super.getPreferredSize() : new Dimension(img.getWidth(), img.getHeight());
        }

        protected Point getImageLocation() {

            Point p = null;
            if (img != null) {
                int x = (getWidth() - (img.getWidth())) / 2;
                int y = (getHeight() - (img.getHeight())) / 2;
                p = new Point(x, y);
            }
            return p;

        }

        public Point toImageContext(Point p) {
            Point imgLocation = getImageLocation();
            Point relative = new Point(p);
            relative.x -= imgLocation.x;
            relative.y -= imgLocation.y;
            return relative;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (img != null) {
                Point p = getImageLocation();
                g.drawImage(img, p.x, p.y, this);
            }
        }

    }

}