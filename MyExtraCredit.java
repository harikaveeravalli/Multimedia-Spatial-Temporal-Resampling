import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyExtraCredit {
	
	JFrame frame;
	JLabel lbIm1;
	JLabel lbIm2;
	BufferedImage img;
	int width = 512;
	int height = 512;
	
	 static int n;
	 //Number of Rotations per second
	 static double s;
	 //Number of frames to be displayed per second
	 static double fps;
	 //draw lines with the given value of Radians

	// Draws a black line on the given buffered image from the pixel defined by (x1, y1) to (x2, y2)
	public void drawLine(BufferedImage image, int x1, int y1, int x2, int y2) {
		Graphics2D g = image.createGraphics();
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(1));
		g.drawLine(x1, y1, x2, y2);
		g.drawImage(image, 0, 0, null);
	}
	
        public ImageIcon paintImage(int width,int height,int n,double rotateval){
		int x0=width/2,y0=height/2;
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		setBackColour(img,width,height);
		//draw boundaries 
			drawLine(img,0, 0, 511, 0);				// top edge
			drawLine(img,0, 0, 0, 511);				// left edge
			drawLine(img,0, 511, 511, 511);	// bottom edge
			drawLine(img,511, 511, 511, 0);
		for(double i=0;i<360;i+=360/(double)n){
			double degreeval = i + rotateval;
			int x1=(int)(x0+width*Math.cos(Math.toRadians(degreeval)));
			int y1=(int)(y0+height*Math.sin(Math.toRadians(degreeval)));
			drawLine(img, x0, y0, x1, y1);	
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

		drawLine(img, 0, 0, width-1, 0);				// top edge
		drawLine(img, 0, 0, 0, height-1);				// left edge
		drawLine(img, 0, height-1, width-1, height-1);	// bottom edge
		drawLine(img, width-1, height-1, width-1, 0); 	// right edge
		//Code to draw the wheel pattern
		double startangle = 0;
		int centerX = width / 2;
		int centerY = height / 2;
		//Graphics2D g1 = img.createGraphics();
		//G2D.setColor(Color.BLACK);
		//G2D.setStroke(new BasicStroke(1));
		//G2D.setColor(new Color(0,0,0,255));
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
		outputPanel p1 = new outputPanel(args);
		rotateipwheel obj1 = new rotateipwheel(args,p1);
		int n,aliaisng1;
		double s,fps,scalefactor;
		if(args.length < 5) {
			System.out.println("please enter all the required arguments");
			System.exit(0);
		}
		else if(args.length == 5) {
			n = Integer.parseInt(args[0]);
			s =Double.parseDouble(args[1]);
			fps =Double.parseDouble(args[2]);
			scalefactor = Double.parseDouble(args[3]);
			aliaisng1 = Integer.parseInt(args[4]);

		}
		
			JFrame frame = new JFrame();
			
			frame.setLayout(new GridLayout(1,2,1,1));
			frame.getContentPane().setBackground(Color.WHITE);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(obj1);
			frame.add(p1);
			frame.pack();
			frame.setSize(1026,512);
			frame.setVisible(true);
			System.out.println();
	}
}
// display wheel with certain fps


@SuppressWarnings("serial")
 class rotateipwheel extends JPanel implements ActionListener {
	
	private double LEFT_IMAGE_FPS = 30;
	private int originalImage_delay  = (int)Math.round(1000.0f/LEFT_IMAGE_FPS);
	private Timer timer = new Timer(1000/60,this);
	private double dt = Math.PI / 90;
	JLabel leftside;
	private double theta;
	private BufferedImage image;
	private ImageIcon leftimage;
	int nu;
	double s;
	double fps1;
	String[] arguments1;
	outputPanel objout;
	double inputdelay = Math.round(1000.0f/30);

	 
	public rotateipwheel(String[] args1,outputPanel op) {

		this.setPreferredSize(new Dimension(512,512));
		MyExtraCredit ren = new MyExtraCredit();
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
		MyExtraCredit obj = new MyExtraCredit();
		theta+=360*s/1000;
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

 class outputPanel extends JPanel implements ActionListener {
	
	private double dt = Math.PI / 90;
	private double theta;
	private double speed;
	private int nlines;
	JLabel rightside;
	public ImageIcon image;
	private double opCounter = 0, degreechange;
	final int delaytime;
	private Timer timer1;
	private double scaling;
	private int alias;
	//rotateipwheel objipwheel;
	private double fps;
	int WIDTH=512;
	int HEIGHT=512;

	public void drawLine(BufferedImage image, int x1, int y1, int x2, int y2) {
		Graphics2D g = image.createGraphics();
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(1));
		g.drawLine(x1, y1, x2, y2);
		g.drawImage(image, 0, 0, null);
	}

	public ImageIcon paintImage(int width,int height,int n,double dt,double scale,int Aliasing){
		int x0=width/2,y0=height/2;
		
		int newWidth =  (int)(512/scale);
		int newHeight = (int)(512/scale);
		BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		setBackColour(img,width,height);
		for(double i=0;i<360;i+=360/(double)n){
			int x1=(int)(x0+width*Math.cos(Math.toRadians(i+dt)));
			int y1=(int)(y0+height*Math.sin(Math.toRadians(i+dt)));

			drawLine(img,x0, y0, x1, y1);
			drawLine(img,0, 0, 511, 0);				// top edge
			drawLine(img,0, 0, 0, 511);				// left edge
			drawLine(img,0, 511, 511, 511);	// bottom edge
			drawLine(img,511, 511, 511, 0);
		}
		BufferedImage output = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_INT_RGB);
		//BufferedImage finalImage=nearestNeighbour(img,width,height,newWidth,newHeight);

		if(Aliasing == 1 && scale > 1) {
		for( int ii=0;ii<newWidth;ii++){
			for(int jj=0;jj<newHeight;jj++){
				long Rval=0;
				long Gval=0;
				long Bval=0;
				int tempwidth=0,tempheight=0;
				int si=(int)(ii*scale),sj=(int)(jj*scale);
				int count =0;
				int mm=si-1;
				int scalefactor1=1000;
				//for(int mm=si-1;mm<=si+1;mm++)
					while(mm<=si+1){
						if(scalefactor1 > 1000) {
							//cannot scale image below this
							Rval+=0;
							Gval+=0;
							Bval+=0;
						}
						for(int nn=sj-1;nn<=sj+1;nn++){
							if(mm>=0 && mm<512 && nn>=0 && nn<512){
								Color color=new Color(img.getRGB(mm, nn));
								Rval+=color.getRed();
								Gval+=color.getGreen();
								Bval+=color.getBlue();
								count++;
							}
					}
					mm++;
					}
					int pix = 0xff000000 | (((byte)(Rval/count) & 0xff) << 16) | (((byte)(Gval/count) & 0xff) << 8) | ((byte)(Bval/count) & 0xff);
				output.setRGB(ii, jj, pix);
			}
		}
	}
	//without antialiasing
	else if(Aliasing == 1 && scale == 1.0){
		antialiasingImage(newWidth,newHeight,scale,img,output);
	}
	else 
	{
		antialiasingImage(newWidth,newHeight,scale,img,output);
		
	}
	return new ImageIcon(output);
	}
	public void antialiasingImage(int width, int height,double scale, BufferedImage img, BufferedImage op){
		for (int newX=0;newX<width;newX++) {
			for (int newY=0;newY<height;newY++) {
				int oldX=(int)(newX*scale);
				int oldY=(int)(newY*scale);
				op.setRGB(newX,newY,img.getRGB(oldX,oldY));
			}
		}
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
	public outputPanel(String[] arg2) {
		MyExtraCredit obj1 = new MyExtraCredit();
		nlines = Integer.parseInt(arg2[0]);
		speed = Double.parseDouble(arg2[1]);
		fps= Double.parseDouble(arg2[2]);
		scaling = Double.parseDouble(arg2[3]);
		alias = Integer.parseInt(arg2[4]);
		timer1= new Timer((int)(1000/fps),this);
		delaytime = (int) Math.round(1000.0f/fps);
		//timer = new Timer(delaytime,this);
		degreechange = (360/fps)*speed;
		rightside = new JLabel(paintImage(512,512,nlines,0,scaling,alias));
		this.add(rightside);
		timer1.start();//timer1.start();
	}
	
	public void actionPerformed(ActionEvent e1) {
		image=paintImage(512,512,nlines,theta,scaling,alias);
		theta += (360/fps)*speed;
		rightside.setIcon(image);
		rightside.repaint();
	}
}