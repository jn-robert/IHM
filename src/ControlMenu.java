import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlMenu implements ActionListener {
    private Vue vue;
    private boolean pause = false;

    public ControlMenu(Vue vue){
        this.vue = vue;
        vue.setMenuContoler(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vue.getItemInterface1()){
            vue.t.stop();
            vue.newGame();
            vue.refresh();
        }
        if (e.getSource() == vue.getItemInterface2()){
            if (pause){
                vue.refresh();
                vue.t.start();
                pause=false;
            }
            else {
                vue.pause();
                pause=true;
            }
        }
        if ( e.getSource() == vue.getItemInterface3()){
            vue.topScore();
        }
    }
}