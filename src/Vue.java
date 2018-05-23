import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Vue extends JFrame {
    private Model model;
    private ControlButton button;
    private JButton[][] tabBouton;
    private JPanel panel;
    private JMenuItem itemInterface1;
    private JMenuBar barMenu;
    private JMenu menu;

    public Vue(Model model){
        this.model = model;
        creerMenu();
        newGame();
        setTitle("Bejeweled");
        setResizable(true);
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        refresh();
    }

    public void display(){ setVisible(true); }

    public void init(){
        button = new ControlButton(this, model);
        panel = new JPanel(new GridLayout(8,8));
        tabBouton = new JButton[8][8];
        model.initTab();
    }

    public void creerMenu(){
        barMenu = new JMenuBar();
        menu = new JMenu("Options");
        itemInterface1 = new JMenuItem("Nouvelle partie");
        menu.add(itemInterface1);

        barMenu.add(menu);
        setJMenuBar(barMenu);
    }

    public void creerWidget(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabBouton[i][j] = (new JButton(new ImageIcon("Bejeweled"+model.getTableauValeurs()+".png")));
                tabBouton[i][j].addActionListener(button);
                tabBouton[i][j].setSize(100,100);
                tabBouton[i][j].setName(i+";"+j);
                panel.add(tabBouton[i][j]);
            }
        }
        setContentPane(panel);
    }

    public void newGame(){
        init();
        creerWidget();
    }

    public void setMenuContoler(ActionListener listener){
        itemInterface1.addActionListener(listener);
    }

    public void refresh(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabBouton[i][j].setIcon(new ImageIcon("Bejeweled"+model.getTableauValeurs()[i][j]+".png"));
                panel.add(tabBouton[i][j]);
            }
        }

    }

    public JButton[][] getTabBouton() {
        return tabBouton;
    }

    public JMenuItem getItemInterface1() { return itemInterface1; }
}
