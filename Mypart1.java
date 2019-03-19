import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;
public class Mypart1 {

	JFrame frame;
	JLabel lbIm1;
	JLabel lbIm2;
	BufferedImage img;
	int width = 512;
	int height = 512;

	// Draws a black line on the given buffered image from the pixel defined by (x1, y1) to (x2, y2)
	public void drawLine(BufferedImage image, int x1, int y1, int x2, int y2) {
		Graphics2D g = image.createGraphics();
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(1));
		g.drawLine(x1, y1, x2, y2);
		g.drawImage(image, 0, 0, null);
	}
	
	public void showIms(String[] args){

		// Read a parameter from command line
		byte[] bytes=null;
		String param0 = args[0];
		String scaleval = args[1];
		Double Scalevalue = Double.parseDouble(scaleval);
		int antialiasval = Integer.parseInt(args[2]);
		Boolean antialias = (antialiasval==1)?true:false;
		int newWidth =  (int)(512/Scalevalue);
		int newHeight = (int)(512/Scalevalue);

		System.out.println("The first parameter was: " + param0);
		System.out.println("the second Parameter is:" + Scalevalue);
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
		Graphics2D g1 = img.createGraphics();
		g1.setColor(Color.BLACK);
		g1.setStroke(new BasicStroke(1));
		double divisions = Double.parseDouble(param0);
		double angles = 360.0 / divisions;
		double startangle = 0;
		int centerX = width / 2;
		int centerY = height / 2;
		for(double i=0;i<360;i+=360/(double)divisions){
			int x1=(int)(centerX+width*Math.cos(Math.toRadians(i)));
			int y1=(int)(centerY+height*Math.sin(Math.toRadians(i)));
			g1.drawLine(centerX,centerY, x1, y1);
		}
		int radius = Math.min(centerX,centerY)*2;
		
		g1.dispose();
		//long len = img.length();
		//bytes = new byte[(int)len];
		BufferedImage output = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_INT_RGB);
		//BufferedImage finalImage=nearestNeighbour(img,width,height,newWidth,newHeight);
		if(antialias == true && Scalevalue > 1 ) {
		for( int i=0;i<newWidth;i++){
			for(int j=0;j<newHeight;j++){
				long Rval=0;
				long Gval=0;
				long Bval=0;
				int tempwidth=0,tempheight=0;
				int si=(int)(i*Scalevalue),sj=(int)(j*Scalevalue);
				int count =0;
				if (tempwidth == 1 && tempheight == 1 ) {
					//No aliasing for the image

					for (int o=0;o<newWidth;o++) {
						for (int p=0;p<newHeight;p++) {
							Color x = new Color(img.getRGB((int)(o*Scalevalue),(int)(p*Scalevalue)));
							Rval = Rval+ x.getRed();
							Gval = Gval+ x.getGreen();
							Bval = Bval + x.getBlue();

						
					
					int pix = 0xff000000 | ((byte)(Rval & 0xff) << 16) | ((byte)(Gval & 0xff) << 8) | ( (byte)(Bval) & 0xff);
					output.setRGB(o,p,pix);
				}
			}
				}
				for(int m=si-1;m<=si+1;m++)
						for(int n=sj-1;n<=sj+1;n++){
							if(m>=0 && m<512 && n>=0 && n<512){
								Color color=new Color(img.getRGB(m, n));
								Rval+=color.getRed();
								Gval+=color.getGreen();
								Bval+=color.getBlue();
								count++;
							}
					}
					int pix = 0xff000000 | (((byte)(Rval/count) & 0xff) << 16) | (((byte)(Gval/count) & 0xff) << 8) | ((byte)(Bval/count) & 0xff);
				output.setRGB(i, j, pix);
			}
		}
	}
	//without antialiasing
	else {
		for(int i=0;i<newWidth;i++) {
			for(int j=0;j<newHeight;j++) {
				int color = img.getRGB((int)(i*Scalevalue),(int)(j*Scalevalue));
				output.setRGB(i,j,color);
			}
		}
	}
		// Use labels to display the images
		frame = new JFrame();
		GridBagLayout gLayout = new GridBagLayout();
		frame.getContentPane().setLayout(gLayout);

		JLabel lbText1 = new JLabel("Original image (Left)");
		lbText1.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel lbText2 = new JLabel("Image after modification (Right)");
		lbText2.setHorizontalAlignment(SwingConstants.CENTER);
		lbIm1 = new JLabel(new ImageIcon(img));
		lbIm2 = new JLabel(new ImageIcon(output));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		frame.getContentPane().add(lbText1, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		frame.getContentPane().add(lbText2, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		frame.getContentPane().add(lbIm1, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		frame.getContentPane().add(lbIm2, c);

		frame.pack();
		frame.setVisible(true);
	}
	public BufferedImage nearestNeighbour(BufferedImage img,int width,int height,int newwidth,int newheight) {
		byte [] data = null;
		
		BufferedImage alteredImage = new BufferedImage(newwidth,newheight, BufferedImage.TYPE_INT_RGB);
		//BufferedImage alteredImage = makeEmptyPicture(newwidth,newheight);
		int xdash=0, ydash=0, ind=0;
		for(int y = 0; y < newheight; y++)
		{
			for(int x = 0; x < newwidth; x++)
			{
				xdash = (int)((x*width)/(float)newwidth);
				ydash = (int)((y*height)/(float)newheight);

				ind = ydash*width + xdash;
				//System.out.println(ind+ "  " + xdash +" " + ydash);
				byte a = 0;
				byte r = data[ind];
				byte g = data[ind+height*width];
				byte b = data[ind+height*width*2];
				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);

				alteredImage.setRGB(x,y,pix);
			}
			
}
		return alteredImage;

	}
	public static void main(String[] args) {
		Mypart1 objMypart1 = new Mypart1();
		objMypart1.showIms(args);
		

	}

}