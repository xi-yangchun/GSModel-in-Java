import java.util.Random;

import java.awt.Graphics;
import java.awt.Color;
import java.util.Arrays;

public class gsmodel {
    double[][] u;
    double[][] ru;
    double[][] v;
    double[][] rv;
    double delta;
    int h;
    int w;

    double Du=2*0.00001;
    double Dv=1*0.00001;
    double f=0.022;
    double k=0.051;

    Random rand=new Random();
    Color[] clr=new Color[256];

    gsmodel(int h,int w,double delta){
        this.h=h;
        this.w=w;
        this.u=gen_one_arr(h,w);
        this.v=gen_zero_arr(h,w);
        this.ru=gen_zero_arr(h,w);
        this.rv=gen_zero_arr(h,w);
        this.delta=delta;
        for(int i=0;i<256;i++){
            clr[i]=new Color(i,i,i);
        }
    }

    void init_square(double iu,double iv,int ss){
        for(int i=h/2-ss;i<h/2+ss;i++){
            for(int j=w/2-ss;j<w/2+ss;j++){
                u[i][j]=iu;
                v[i][j]=iv;
            }
        }
    }

    double[][] gen_zero_arr(int h, int w){
        double[][] arr=new double[h][w];
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                arr[i][j]=0;
            }
        }
        return arr;
    }

    double[][] gen_one_arr(int h, int w){
        double[][] arr=new double[h][w];
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                arr[i][j]=1;
            }
        }
        return arr;
    }

    void step(){
        int[] pos;
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                pos=ppos(i,j,0,-1);
                double u_left=u[pos[0]][pos[1]];
                pos=ppos(i,j,0,1);
                double u_right=u[pos[0]][pos[1]];
                pos=ppos(i,j,-1,0);
                double u_up=u[pos[0]][pos[1]];
                pos=ppos(i,j,1,0);
                double u_down=u[pos[0]][pos[1]];
                double u_out=4*u[i][j];
                double lap_u=(u_left+u_up+u_right+u_down-u_out)/(delta*delta);

                pos=ppos(i,j,0,-1);
                double v_left=v[pos[0]][pos[1]];
                pos=ppos(i,j,0,1);
                double v_right=v[pos[0]][pos[1]];
                pos=ppos(i,j,-1,0);
                double v_up=v[pos[0]][pos[1]];
                pos=ppos(i,j,1,0);
                double v_down=v[pos[0]][pos[1]];
                double v_out=4*v[i][j];
                double lap_v=(v_left+v_up+v_right+v_down-v_out)
                /(delta*delta);

                ru[i][j]=Du*lap_u-u[i][j]*v[i][j]*v[i][j]+f*(1-u[i][j]);
                rv[i][j]=Dv*lap_v+u[i][j]*v[i][j]*v[i][j]-v[i][j]*(f+k);
            }
        }

        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                u[i][j]+=ru[i][j];
                v[i][j]+=rv[i][j];
                //if(u[i][j]<0){u[i][j]=0;}
                //if(v[i][j]<0){v[i][j]=0;}
            }
        }
    }
    
    int[] ppos(int i,int j,int di,int dj){
        int[] pos=new int[2];
        int y=i+di;
        int x=j+dj;
        if(y<0){y=y+h;}
        else if(y>h-1){y=y-h;}
        if(x<0){x=x+w;}
        else if(x>w-1){x=x-w;}
        pos[1]=x;
        pos[0]=y;
        return pos;
    }

    void disp(Graphics g,int m){
        for(int i=0;i<h;i++){
            for(int j=0;j<w;j++){
                //printl(Double.toString(u[i][j]*255));
                int c=(int)(255*u[i][j]);
                //printl(Integer.toString(c));
                //printl(Integer.toString(c));
                g.setColor(clr[c]);
                g.fillRect(j*m,i*m,m,m);
            }
        }
    }

    void printl(String s){
        System.out.println(s);
    }

    void pr2darr(double[][] arr){
        int l=arr.length;
        for(int i=0;i<l;i++){
            printl(Arrays.toString(arr[i]));
        }
    }

}