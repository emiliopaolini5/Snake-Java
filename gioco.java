import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.*;
public class gioco
{
    static JFrame finestra; 
    static final int ny=35;
    static final int nx=35;
    static int[][]plancia=new int[ny][nx];
    static int heightSector;
    static int widthSector;
    static Schermo s;
    static int[]snake=new int[10];
    static int vel;
    static int corpoUtile;
    static int frutta;
    static int score;
    static boolean disegna;
    /*static boolean inside(int x1,int y1,int largh,int alt,int px,int py)
    {
       int x2 = x1+largh;
       int y2 = y1+alt;
       if (px>=x1 && px<=x2 && py>=y1 && py<=y2)
          return true;
       return false;  
    }
    
    static boolean collision (int x1,int y1,int alt1,int largh1,int x2,int y2,int alt2,int largh2)
    {
        if (inside(x1,y1,largh1,alt1,x2,y2) ||
            inside(x1,y1,largh1,alt1,x2+largh2,y2) ||
            inside(x1,y1,largh1,alt1,x2,y2+alt2) ||
            inside(x1,y1,largh1,alt1,x2+largh2,y2+alt2))
           return true;
        if (inside(x2,y2,largh2,alt2,x1,y1) ||
            inside(x2,y2,largh2,alt2,x1+largh1,y1) ||
            inside(x2,y2,largh2,alt2,x1,y1+alt1) ||
            inside(x2,y2,largh2,alt2,x1+largh1,y1+alt1))
           return true;
        return false;
    }
    /*
    static void updatePosition(int quale,int[]x,int[]y,int[]vx,int[]vy,int[]alt,int[]largh,int altezza,int larghezza,boolean[]fall)
    {
        int nx=x[quale]+vx[quale];
        int ny=y[quale]+vy[quale];
        int contax=0;
        int contay=0;
        for(int j=0;j<x.length;j++)
           if(j!=quale)
           
              if(collision(nx,ny,alt[quale],largh[quale],x[j],y[j],alt[j],largh[j]))
              {
                  if(collision(nx,y[quale],alt[quale],largh[quale],x[j],y[j],alt[j],largh[j]))
                     contax++;
                  if(collision(x[quale],ny,alt[quale],largh[quale],x[j],y[j],alt[j],largh[j]))
                     contay++;
              }
        if (x[quale]+largh[quale]>=larghezza) 
        {
           contax++; 
           x[quale] = larghezza-largh[quale]-1;
        }
        if (x[quale]<=0)
        {
           contax++;
           x[quale] = 1;
        }
        if (y[quale]+alt[quale]>=altezza) 
        {
           fall[quale]=true;
           vy[quale] = 0;
           vx[quale] = 0;
           y[quale] = 0;
           x[quale]=0;
           largh[quale]=0;
           alt[quale]=0;
        }
        if (y[quale]<=0)
        {
           contay++;
           y[quale] = 1;
        }         
        
        if(contax>0)
           vx[quale]=-vx[quale];
        if(contay>0)
           vy[quale]=-vy[quale];
        
        if(collision(nx,ny,alt[quale],largh[quale],playerX,playerY,altPlayer,larghPlayer))
        {
            if(collision(x[quale],ny,alt[quale],largh[quale],playerX,playerY,altPlayer,larghPlayer))
               vy[quale]=-(Math.abs(vy[quale]));
            if(collision(nx,y[quale],alt[quale],largh[quale],playerX,playerY,altPlayer,larghPlayer))
               vx[quale]=-vx[quale];
       }
    }
    
    /*static void aggiornaPallina(int i,int[] x, int[] y, int[] vx, int[] vy, int[] largh, int[] alt, int larghezza,int altezza,boolean[]fall)
    {
        x[i] = x[i] + vx[i];
        y[i] = y[i] + vy[i];
        for(int j=0;j<x.length;j++)
           updatePosition(j,x,y,vx,vy,alt,largh,altezza,larghezza,fall);
    }*/
    /*
    static void initAll(int[] x, int[] y,int[] vx, int[] vy,int[] largh, int[] alt)
    {
       playerX = 200;
       playerY = 350;
       for (int i=0;i<x.length;i++)
       {
           do{vx[i] = (int)(Math.random()*6)-3;}while(vx[i]==0);
           do{vy[i] = (int)(Math.random()*6)-3;}while(vy[i]==0);
           x[i] = (int)(Math.random()*400);
           y[i] = 0;
           largh[i] = (int)(Math.random()*20+5);
           alt[i] = (int)(Math.random()*20+5);
       }       
    }
    */
   
    static void clearScreen(Schermo s, int larghezza, int altezza)
    {
        s.drawRectangle(0,0,larghezza,altezza,0,0,0);
    }
    
    static void slow(int millisecondi)
    {
           try
           { 
                  Thread.sleep(millisecondi); 
           } catch (Exception ex) 
           { 
                  System.out.println(ex.toString()); 
           }
    }
    
    static void controllaInput(Schermo s)
    {
        char c = s.ultimoTastoPremutoKeyChar;
    
        if (c!='£')
        {
            if (c=='a')
                if(vel!=1)
                    vel=-1;
            if (c=='d')
                if(vel!=-1)
                    vel=1;
            if (c=='w') 
                if(vel!=nx)
                    vel=-nx;
            if (c=='s')
                if(vel!=-nx)
                    vel=nx;
        }
    }
     
    static boolean end ()
    {
        int j=0;
        do
        {
         if(j!=corpoUtile-1)
            if(snake[corpoUtile-1]==snake[j])
                return false;
            j++;
        }while(j<corpoUtile);
        return true;        
    }
    
    static void mangiaFrutta(int[] unita,int[] decine)
    {
        
        if(snake[corpoUtile-1]==frutta)
        {
            corpoUtile++;
            snake[corpoUtile-1]=frutta+vel;
            frutta=aggiornaFrutta();
            score++;
            cambiaPunteggio(unita,decine);
        }
        
    }
    static void disegnaPlancia()
    {
        for(int i=0;i<plancia.length;i++)
          for(int j=0;j<plancia[0].length;j++)
             s.drawRectangle(j*widthSector,i*heightSector,widthSector,heightSector,0,200,0);
    }
    
    static void initSnake(int testa)
    {
        for(int i=0;i<corpoUtile;i++)
           snake[i]=testa+i;
    }
    
    static void disegnaSnake()
    {
        for(int i=0;i<corpoUtile;i++)
        {
            int x=snake[i]%plancia[0].length;
            int y=snake[i]/plancia.length;
            s.drawRectangle(x*widthSector,y*heightSector,widthSector,heightSector,255,0,0);
        }
    }
    
    static void aggiornaSnake(int []unita,int[] decine)
    {
        int x=snake[corpoUtile-1]%plancia[0].length;
        int y=snake[corpoUtile-1]/plancia.length;
        for(int i=0;i<corpoUtile-1;i++)
            snake[i]=snake[i+1];
        snake[corpoUtile-1]+=vel;
        mangiaFrutta(unita,decine);
        if(vel==-1)
           if(x<=0)
              snake[corpoUtile-1]+=nx;
        if(vel==1)
           if(snake[corpoUtile-1]>(y+1)*nx-1)
              snake[corpoUtile-1]-=nx;
        if(vel==-nx)
           if(y<=0)
              snake[corpoUtile-1]+=(ny*nx);
        if(vel==nx)
           if(snake[corpoUtile-1]>nx*ny)
              snake[corpoUtile-1]-=ny*nx;
            }
    
    static void drawFrutta(int x,int y)
    {
        s.drawRectangle(x,y,widthSector,heightSector,242,255,6);
    }
    
    static int aggiornaFrutta ()
    {
        int x;
        int y;
        boolean flag;
        do
        {
           flag=false;
           x=(int)(Math.random()*(nx-2)+1);
           y=(int)(Math.random()*(ny-2)+1);
           for(int j=0;j<corpoUtile;j++)
              if(snake[j]==(y*plancia.length+x))
                 flag=true;
        }while(flag);
        return y*plancia.length+x;
    }
    
    static void cambiaPunteggio(int[]unita,int[]decine)
    {
        if(score<=9)
        {
           cambiaCifra(score,unita);   
        }
        else
           if(score<99)
           {
              int dec=score/10;
              int un=score%10;
              cambiaCifra(dec,decine);
              cambiaCifra(un,unita);
            }
    }
    
    static void cambiaCifra(int num,int[]numero)
    {
       if(num==0)
       {
           numero[0]=1;
           numero[1]=1;
           numero[2]=1;
           numero[3]=1;
           numero[4]=1;
           numero[5]=1;
           numero[6]=0;
       }
       if(num==1)
       {
           numero[0]=0;
           numero[1]=1;
           numero[2]=1;
           numero[3]=0;
           numero[4]=0;
           numero[5]=0;
           numero[6]=0;
       }
       if(num==2) 
       {
           numero[0]=1;
           numero[1]=1;
           numero[2]=0;
           numero[3]=1;
           numero[4]=1;
           numero[5]=0;
           numero[6]=1;
        }
       if(num==3)
       {
           
           numero[0]=1;
           numero[1]=1;
           numero[2]=1;
           numero[3]=1;
           numero[4]=0;
           numero[5]=0;
           numero[6]=1;
        }
       if(num==4){
           
           numero[0]=0;
           numero[1]=1;
           numero[2]=1;
           numero[3]=0;
           numero[4]=0;
           numero[5]=1;
           numero[6]=1;
        }
       if(num==5) {
          
           numero[0]=1;
           numero[1]=0;
           numero[2]=1;
           numero[3]=1;
           numero[4]=0;
           numero[5]=1;
           numero[6]=1;
        }
       if(num==6){
           
           numero[0]=1;
           numero[1]=0;
           numero[2]=1;
           numero[3]=1;
           numero[4]=1;
           numero[5]=1;
           numero[6]=1;
        }
       if(num==7) {
           
           numero[0]=1;
           numero[1]=1;
           numero[2]=1;
           numero[3]=0;
           numero[4]=0;
           numero[5]=0;
           numero[6]=0;
        }
       if(num==8){
           
           numero[0]=1;
           numero[1]=1;
           numero[2]=1;
           numero[3]=1;
           numero[4]=1;
           numero[5]=1;
           numero[6]=1;
        }
       if(num==9){
           
           numero[0]=1;
           numero[1]=1;
           numero[2]=1;
           numero[3]=0;
           numero[4]=0;
           numero[5]=1;
           numero[6]=1;
        }
    }
    
    static void drawScore(int[]unita,int[]decine)
    {
        drawCifra(355,50,decine);
        drawCifra(373,50,unita);
    }
    
    static void drawCifra(int x,int y,int[]num)
    {
        if(num[0]==1)
           s.drawRectangle(x+2,y,10,3,255,0,0);
        if(num[1]==1)
           s.drawRectangle(x+12,y+3,3,10,255,0,0);
        if(num[2]==1)
           s.drawRectangle(x+12,y+15,3,10,255,0,0);
        if(num[3]==1)
           s.drawRectangle(x+2,y+25,10,3,255,0,0);
        if(num[4]==1)
           s.drawRectangle(x,y+15,3,10,255,0,0);
        if(num[5]==1)
           s.drawRectangle(x,y+3,3,10,255,0,0);
        if(num[6]==1)
           s.drawRectangle(x+2,y+13,10,3,255,0,0);
    }
    
    static void initAll(int[] unita,int[]decine)
    {
       for(int i=0,c=0;i<plancia.length;i++,c++)
           for(int j=0;j<plancia[0].length;j++,c++)
               plancia[i][j]=c;
       score=0;
       corpoUtile=2;
       initSnake(700);
       frutta=aggiornaFrutta();
       disegna=true;
       unita[0]=1;
       unita[1]=1;
       unita[2]=1;
       unita[3]=1;
       unita[4]=1;
       unita[5]=1;
       unita[6]=0;
       decine[0]=1;
       decine[1]=1;
       decine[2]=1;
       decine[3]=1;
       decine[4]=1;
       decine[5]=1;
       decine[6]=0; 
    }
    
    static void restartGame(Schermo s,int[]unita,int[]decine,int larghezza,int altezza)
    {
        char c = s.ultimoTastoPremutoKeyChar;
        if((int)c==32)
           theGame(unita,decine,s,larghezza,altezza);
    }
    
    static void theGame(int[]unita,int[]decine,Schermo s,int larghezza,int altezza)
    {
       initAll(unita,decine);
       do
       {
           clearScreen(s,larghezza,altezza);
           disegnaPlancia();
           controllaInput(s);
           aggiornaSnake(unita,decine);
           disegnaSnake();
           if(vel!=0)
                disegna=end();
           drawFrutta((frutta%nx)*widthSector,(frutta/ny)*heightSector);
           drawScore(unita,decine);
           //cancella lo schermo
           finestra.repaint();//ridisegna tutto
           slow(90);
       } while (corpoUtile<snake.length && disegna);
       paintResult();
       while(true){
          restartGame(s,unita,decine,larghezza,altezza);
       }
    }
    
    static void paintResult()
    {
        int[][]gameover={{1,1,1,1,1,0,0,0,1,0,0,0,1,0,0,0,0,0,1,0,1,1,1,1},
                        {1,0,0,0,0,0,0,1,0,1,0,0,1,1,0,0,0,1,1,0,1,0,0,0},
                        {1,0,0,0,0,0,1,0,0,0,1,0,1,0,1,0,1,0,1,0,1,0,0,0},
                        {1,0,1,1,1,0,1,1,1,1,1,0,1,0,0,1,0,0,1,0,1,1,1,1},
                        {1,0,0,0,1,0,1,0,0,0,1,0,1,0,0,0,0,0,1,0,1,0,0,0},
                        {1,1,1,1,1,0,1,0,0,0,1,0,1,0,0,0,0,0,1,0,1,1,1,1},
                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                        {1,1,1,1,1,0,1,0,0,0,0,0,1,0,1,1,1,1,0,1,1,1,1,1},
                        {1,0,0,0,1,0,1,0,0,0,0,0,1,0,1,0,0,0,0,1,0,0,0,1},
                        {1,0,0,0,1,0,0,1,0,0,0,1,0,0,1,0,0,0,0,1,1,1,1,1},
                        {1,0,0,0,1,0,0,1,0,0,0,1,0,0,1,1,1,1,0,1,0,1,0,0},
                        {1,0,0,0,1,0,0,0,1,0,1,0,0,0,1,0,0,0,0,1,0,0,1,0},
                        {1,1,1,1,1,0,0,0,0,1,0,0,0,0,1,1,1,1,0,1,0,0,0,1},};
                        
       int [][]victory={{1,0,0,0,1,0,1,1,1,1,1,0,1,0,0,0,1},
                        {0,1,0,1,0,0,1,0,0,0,1,0,1,0,0,0,1},
                        {0,0,1,0,0,0,1,0,0,0,1,0,1,0,0,0,1},
                        {0,0,1,0,0,0,1,0,0,0,1,0,1,0,0,0,1},
                        {0,0,1,0,0,0,1,0,0,0,1,0,1,0,0,0,1},
                        {0,0,1,0,0,0,1,1,1,1,1,0,1,1,1,1,1},
                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                        {1,0,0,0,0,0,0,0,1,0,1,0,1,0,0,0,1},
                        {1,0,0,0,0,0,0,0,1,0,1,0,1,1,0,0,1},
                        {0,1,0,0,1,0,0,1,0,0,1,0,1,0,1,0,1},
                        {0,1,0,0,1,0,0,1,0,0,1,0,1,0,0,1,1},
                        {0,0,1,1,0,1,1,0,0,0,1,0,1,0,0,0,1},
                        {0,0,1,0,0,0,1,0,0,0,1,0,1,0,0,0,1},};
       if(!(corpoUtile==snake.length))
       {
           int y=3;
           for(int i=0;i<gameover.length;i++,y++){
              int x=6;
              for(int j=0;j<gameover[0].length;j++,x++)
                 if(gameover[i][j]==1)
                    s.drawRectangle(x*widthSector,y*heightSector,widthSector,heightSector,0,0,0);
                 
            } 
           finestra.repaint();
       }
       else{
           int y=3;
           for(int i=0;i<victory.length;i++,y++){
              int x=9;
              for(int j=0;j<victory[0].length;j++,x++)
                 if(victory[i][j]==1)
                    s.drawRectangle(x*widthSector,y*heightSector,widthSector,heightSector,0,0,0);
                 
            }  
           finestra.repaint();
        }
       slow(500);
       int[][]restart={{1,1,1,0,1,1,1,0,1,1,1,0,1,1,1,0,1,1,1},
                       {1,0,1,0,1,0,1,0,1,0,0,0,1,0,0,0,1,0,0},
                       {1,1,1,0,1,1,1,0,1,1,1,0,1,1,1,0,1,1,1},
                       {1,0,0,0,1,1,0,0,1,0,0,0,0,0,1,0,0,0,1},
                       {1,0,0,0,1,0,1,0,1,1,1,0,1,1,1,0,1,1,1},
                       {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                       {1,1,1,0,1,1,1,0,1,1,1,0,1,1,1,0,1,1,1},
                       {1,0,0,0,1,0,1,0,1,0,1,0,1,0,0,0,1,0,0},
                       {1,1,1,0,1,1,1,0,1,1,1,0,1,0,0,0,1,1,1},
                       {0,0,1,0,1,0,0,0,1,0,1,0,1,0,0,0,1,0,0},
                       {1,1,1,0,1,0,0,0,1,0,1,0,1,1,1,0,1,1,1},
                    };
       int y=18;
       for(int i=0;i<restart.length;i++,y++){
              int x=6;
              for(int j=0;j<restart[0].length;j++,x++)
                 if(restart[i][j]==1)
                    s.drawRectangle(x*widthSector,y*heightSector,widthSector,heightSector,0,0,0);
                 
            }  
       finestra.repaint();
    }
    
    static void main()
    {
       finestra = new JFrame();
       s = new Schermo(400,400);
       int larghezza = s.larghezza;
       int altezza = s.altezza;
       finestra.add(s);
       finestra.setBounds(10,10,larghezza,altezza);
       finestra.setVisible(true);
       finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       heightSector= 10;
       widthSector = 10;
       int[]unita={1,1,1,1,1,1,0};
       int[]decine={1,1,1,1,1,1,0};
       
       theGame(unita,decine,s,larghezza,altezza);
       
}
    }

