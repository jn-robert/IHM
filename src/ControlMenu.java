import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlMenu implements ActionListener {
    private Vue vue;

    public ControlMenu(){}

    public ControlMenu(Vue vue){
        this.vue = vue;
        vue.setMenuContoler(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vue.getItemInterface1()){
            vue.newGame();
            vue.refresh();
        }
    }
}