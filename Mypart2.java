import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mypart2 {
	
	JFrame frame;
	JLabel lbIm1;
	JLabel lbIm2;
	BufferedImage img;
	int width = 512;
	int height = 512;
	private static int n;
	private static double s;
	private static double fps;
	
	

	// Draws a black line on the given buffered image from the pixel defined by (x1, y1) to (x2, y2)
	public void drawLine(BufferedImage image, int x1, int y1, int x2, int y2,int redline) {
		Graphics2D g = image.createGraphics();
		if(redline == 1) 
		{
			g.setColor(Color.BLACK);
		}
		else 
		{
			g.setColor(Color.BLACK);
		}
		//g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(1));
		g.drawLine(x1, y1, x2, y2);
		g.drawImage(image, 0, 0, null);
	}
	
        public ImageIcon paintImage(int width,int height,int n,double dt){
		int x0=width/2,y0=height/2;int redline =0;
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		setBackColour(img,width,height);
			drawLine(img,0, 0, 511, 0,0);				// top edge
			drawLine(img,0, 0, 0, 511,0);				// left edge
			drawLine(img,0, 511, 511, 511,0);	// bottom edge
			drawLine(img,511, 511, 511, 0,0);
		for(double i=0;i<360;i+=360/(double)n){
			double deg = i + dt;
			int x1=(int)(x0+width*Math.cos(Math.toRadians(deg)));
			int y1=(int)(y0+height*Math.sin(Math.toRadians(deg)));
			//System.out.println(dt);
			if(dt%360 == 0) {
				redline = 1;
			}
			drawLine(img,x0, y0, x1, y1,redline);
			
		}
		return new ImageIcon(img);
	}
	public void setBackColour(BufferedImage img, int width, int Height) {
		 
		for(int i=0;i<Height;i++) {
			for (int j = 0;j<width;j++) {
				byte r =(byte) 255;
				byte g = (byte) 255;
				byte b = (byte) 255;
				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
				//int pix = ((a << 24) + (r << 16) + (g << 8) + b);
				img.setRGB(i,j,pix);
			}

			}
		}
	
	public void showIms(String[] args,Graphics2D G2D,double dt){

		// Read a parameter from command line
		byte[] bytes=null;
		String param0 = args[0];

		System.out.println("The first parameter was: " + param0);
		System.out.println("the second Parameter is:" + s);
		// Initialize a plain white image
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		int ind = 0;
		for(int y = 0; y < height; y++){

			for(int x = 0; x < width; x++){

				// byte a = (byte) 255;
				byte r = (byte) 255;
				byte g = (byte) 255;
				byte b = (byte) 255;

				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
				//int pix = ((a << 24) + (r << 16) + (g << 8) + b);
				img.setRGB(x,y,pix);
				ind++;
			}
		}

		drawLine(img, 0, 0, width-1, 0,0);				// top edge
		drawLine(img, 0, 0, 0, height-1,0);				// left edge
		drawLine(img, 0, height-1, width-1, height-1,0);	// bottom edge
		drawLine(img, width-1, height-1, width-1, 0,0); 	// right edge
		//Code to draw the wheel pattern
		double startangle = 0;
		int centerX = width / 2;
		int centerY = height / 2;
		
		double divisions = Double.parseDouble(param0);
		double angles = 360.0 / divisions;
		int radius = Math.min(centerX,centerY)*2;
		//Point2D centerPoint = new Point2D.Double(centerX, centerY);
		double angle=startangle;
		for (int index = 0; index < divisions; index++) {
			//Point2D point = pointAt(Math.toRadians(angle),radius,dt);
			//point = translate(point, centerPoint);
			//G2D.draw(new Line2D.Double(centerPoint,point));
			angle += angles;
		}
		//g1.dispose();
		//return img;

		
	}
	
	public static void main(String[] args) {
		if(args.length < 3) {
			System.out.println("please enter all the required arguments");
			System.exit(0);
		}
		else if(args.length == 3) {
			n = Integer.parseInt(args[0]);
			s =Double.parseDouble(args[1]);
			fps =Double.parseDouble(args[2]);
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
            public void run() {
			JFrame frame = new JFrame();
			outputPanel1 p1 = new outputPanel1(args);
			rotateipwheel1 obj1 = new rotateipwheel1(args,p1);
			frame.setLayout(new GridLayout(1,2,1,1));
			frame.getContentPane().setBackground(Color.WHITE);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(obj1);
			frame.add(p1);
			frame.pack();
			frame.setSize(1026,512);
			frame.setVisible(true);
			//new Thread(p1).start();
				//Mypart2
			//Mypart2 ren = new Mypart2();
		//ren.showIms(args);
			}
		});
	}
}
// display wheel with certain fps


@SuppressWarnings("serial")
 class rotateipwheel1 extends JPanel implements ActionListener {
	
	private double LEFT_IMAGE_FPS = 30;
	private int originalImage_delay  = (int)Math.round(1000.0f/LEFT_IMAGE_FPS);
	private Timer timer = new Timer(1000/30,this);
	private double dt = Math.PI / 90;
	JLabel leftside;
	private double theta;
	private BufferedImage image;
	private ImageIcon leftimage;
	int nu;
	double s;
	double fps1;
	String[] arguments1;
	outputPanel1 objout;
	double inputdelay = Math.round(1000.0f/30);

	 
	public rotateipwheel1(String[] args1,outputPanel1 op) {

		this.setPreferredSize(new Dimension(512,512));
		Mypart2 ren = new Mypart2();
		//image = ren.showIms(args1);
		nu = Integer.parseInt(args1[0]);
		//timer.start();
		s = Double.parseDouble(args1[1]);
		arguments1 = args1;
		objout = op;
		leftside = new JLabel(ren.paintImage(512,512,nu,0));
		this.add(leftside);
		timer.start();
	}
	
	public void rotateleftImage(){
		Mypart2 obj = new Mypart2();
		//theta=(theta + (360*s/60))%360;;
		theta+= 360*s/30;
		//obj.showIms(arguments1,g2d,theta);
		leftimage = obj.paintImage(512,512,nu,theta);
		leftside.setIcon(leftimage);
		leftside.repaint();
		
	}
	@Override
	public void actionPerformed(ActionEvent e){
		rotateleftImage();
		
	}
	
}

 class outputPanel1 extends JPanel implements ActionListener {
	private double theta;
	private double speed;
	private int nlines;
	JLabel rightside;
	public ImageIcon image;
	private double opCounter = 0, degreechange;
	final int delaytime;
	private Timer timer1;
	//rotateipwheel objipwheel;
	private double fps;
	int WIDTH=512;
	int HEIGHT=512;
	public outputPanel1(String[] arg2) {
		Mypart2 obj1 = new Mypart2();
		nlines = Integer.parseInt(arg2[0]);
		speed = Double.parseDouble(arg2[1]);
		fps= Float.parseFloat(arg2[2]);
		timer1= new Timer((int)(1000/fps),this);
		delaytime = (int) Math.round(1000.0f/fps);
		//timer = new Timer(delaytime,this);
		degreechange = (360/fps)*speed;
		rightside = new JLabel(obj1.paintImage(512,512,nlines,0));
		this.add(rightside);
		timer1.start();//timer1.start();
		//image=new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
	}
 
	public void paintoutput() {
	
		//output2d.drawImage(image,0,0,null);
		Mypart2 obj2 = new Mypart2();
		double anglechange = (360/fps)*speed;
		//theta =(theta + anglechange)%360;
		theta+=(360/fps)*speed;
		image=obj2.paintImage(512,512,nlines,theta);
		
		rightside.setIcon(image);
		//rightside.revalidate();
		rightside.repaint();
	}

	
	public void actionPerformed(ActionEvent e1) {
		paintoutput();
	}
	
		
	
	
}