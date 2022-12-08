import java.awt.Graphics;
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.util.Random;

//import java.util.LinkedList;

public class main_gsm{
    public static void main(String[] args) {
        GameWindow gw = new GameWindow("pgrain",600,600);
        DrawCanvas2 cd=new DrawCanvas2();
        gsmodel gs=new gsmodel(600,600, 0.01);
        gs.init_square(0.5,0.25,20);
        cd.set_model(gs);
        gw.add(cd);
        gw.setVisible(true);
        gw.startGameLoop();
    }
}

//スレッドとして利用したい->Runnableインタフェースが必要
class GameWindow extends JFrame implements Runnable{
    private Thread th = null;
    public GameWindow(String title, int width, int height) {
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width,height);
        setLocationRelativeTo(null);
        setResizable(false);
    }
 
    //ゲームループの開始メソッド
    public synchronized void startGameLoop(){
        if ( th == null ) {
            th = new Thread(this);
            th.start();
        }
    }
    //ゲームループの終了メソッド
    public synchronized void stopGameLoop(){
        if ( th != null ) {
            th = null;
        }
    }
    public void run(){
        //ゲームループ（定期的に再描画を実行）
        while(th != null){
            try{
                Thread.sleep(25);
                repaint();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
 
class DrawCanvas2 extends JPanel{
    Random rand =new Random();
    gsmodel gs;
    int m=1;

    void set_model(gsmodel g){
        this.gs=g;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 600, 600);
        gs.step();
        gs.disp(g,m);        
    }
}
