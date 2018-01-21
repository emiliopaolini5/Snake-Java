
/**
 * 
 * @carpediem75 (Ing. Simone Giuliani) 
 */

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.DataBufferInt;
import java.awt.*; //Color;
import javax.swing.JPanel;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.KeyEvent;

public class Schermo extends JPanel implements KeyListener
{
   int larghezza;
   int altezza;
   int[][][] m;
   BufferedImage img;
   int ultimoTastoPremutoKeyCode;
   char ultimoTastoPremutoKeyChar;

   public void keyTyped(KeyEvent e) 
   {
       
   }
     
   public void keyPressed(KeyEvent e) 
   {
       ultimoTastoPremutoKeyChar = e.getKeyChar();
       ultimoTastoPremutoKeyCode = e.getKeyCode();
   }
     
   public void keyReleased(KeyEvent e) 
   {
       ultimoTastoPremutoKeyChar = '£';
   }
     
   public Schermo(int w, int h)
    {
       this.setFocusable(true);
       this.requestFocus(true);
       larghezza = w;
       altezza = h;
       m = new int[4][h][w];
       drawRectangle(0,0,w,h,0,0,0);
       img = matricesToBufferedImage(m);
       setBounds(0,0,w,h);
       addKeyListener(this); 
       setFocusable(true);
       setFocusTraversalKeysEnabled(false);
       ultimoTastoPremutoKeyCode= -1;
       ultimoTastoPremutoKeyChar = '£';
   }

   public Schermo(String url)
    {
       this.setFocusable(true);
       this.requestFocus(true);
       img = caricaDaFile(url); 
       larghezza = img.getWidth();
       altezza = img.getHeight();
       m = new int[4][larghezza][altezza];
       imageTomatrices(img);
       addKeyListener(this); 
       setFocusable(true);
       setFocusTraversalKeysEnabled(false);
       ultimoTastoPremutoKeyCode= -1;
       ultimoTastoPremutoKeyChar = '£';
    }

   public void drawRectangle(int x, int y, int ww, int hh, int r, int g, int b)
   {
      for(int i=y;i<y+hh;i++)
         for(int j=x;j<x+ww;j++)
         {
            m[0][i][j] = 255;
            m[1][i][j] = r;
            m[2][i][j] = g;
            m[3][i][j] = b;
         }
   }
    
   public void drawHorizontalLine(int x1, int y, int x2, int r, int g, int b)
   {
      if (x1>x2) 
      {
         int buffer = x1;
         x1 = x2;
         x2 = buffer;
      }
      for(int i=x1;i<x2;i++)
         accendiPixel(i,y,r,g,b);
   }
   
   public void ridisegna()
   {
       img = matricesToBufferedImage(m);
   }
   
   public final BufferedImage caricaDaFile(String nomeFile)
   {
       BufferedImage img = null;
       try 
       {
           File file = new File(nomeFile);
           img = ImageIO.read(file);
       } catch (IOException e) 
       {
           System.out.println(e.toString());
       }
       return img;
   }
    
   public BufferedImage matricesToBufferedImage(int[][][] mat)
   {
       int w = m[0][0].length;
       int h = m[0].length;
       BufferedImage bi = new BufferedImage( w, h, BufferedImage.TYPE_INT_ARGB );
       for(int i=0;i<h;i++)
          for(int j=0;j<w;j++)
          {
              bi.setRGB(i,j,new Color(mat[1][j][i],mat[2][j][i],mat[3][j][i]).getRGB());
          }
       return bi;

   }
    
   public void accendiPixel(int x, int y, int r, int g, int b)
   {
      m[0][y][x] = 255;
      m[1][y][x] = r; 
      m[2][y][x] = g;
      m[3][y][x] = b;
   }
   
   
   public void imageTomatrices(BufferedImage image) 
   {

      final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
      final int width = image.getWidth();
      final int height = image.getHeight();

      final int pixelLength = 4;
      for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) 
      {
            int argb = 0;
            int a = pixels[pixel] & 0xff;
            int r = pixels[pixel + 1] & 0xff;
            int g = pixels[pixel + 2] & 0xff;
            int b = pixels[pixel + 3] & 0xff;
            argb += (((int) r & 0xff) << 24); // alpha
            argb += ((int) g & 0xff); // blue
            argb += (((int) b & 0xff) << 8); // green
            argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
            m[0][row][col] = argb;
            m[1][row][col] = r;
            m[2][row][col] = g;
            m[3][row][col] = b;
            col++;
            if (col == width) 
            {
               col = 0;
               row++;
            }
      }
   }
   
   public void paint(Graphics g) 
   {
       ridisegna();
       g.drawImage(img,0,0,larghezza,altezza,0,0,larghezza,altezza,null);
   }
}
