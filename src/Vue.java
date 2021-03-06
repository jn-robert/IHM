import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Vue extends JFrame {
    private Model model;
    private ControlButton button;
    private JButton[][] tabBouton;
    private JPanel panel;

    private JMenuItem itemInterface1;
    private JMenuItem itemInterface2;
    private JMenuItem itemInterface3;
    private JMenuBar barMenu;
    private JMenu menu;
    private JPanel text;

    private JLabel score;
    private JLabel tries;
    private JLabel level;

    private JPanel global;

    public Timer t;
    JProgressBar prog=new JProgressBar(0,100);

    public Vue(Model model){
        this.model = model;
        creerMenu();
        newGame();
        setTitle("Bejeweled");
        setResizable(true);
        setSize(500,650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        refresh();
    }

    public void display(){ setVisible(true); }

    public void init(){
        button = new ControlButton(this, model);
        panel = new JPanel(new GridLayout(8,8));
        text = new JPanel();
        global = new JPanel();
        global.setLayout(new BoxLayout(global,BoxLayout.Y_AXIS));
        tabBouton = new JButton[8][8];
        model.initTab();
        prog.setStringPainted(true);
        prog.setValue(model.getScoreTimer());
    }

    public void creerText(){
        score = new JLabel("Score : " + model.getScore());
        level = new JLabel("Level : " + model.getNiveau());
        tries = new JLabel("Tries : " + model.getTries());
        text.add(level);
        text.add(tries);
        text.add(score);
    }

    public void creerMenu(){
        barMenu = new JMenuBar();
        menu = new JMenu("Options");
        itemInterface1 = new JMenuItem("Nouvelle partie");
        itemInterface2 = new JMenuItem("Pause");
        itemInterface3 = new JMenuItem("Top score");
        menu.add(itemInterface1);
        menu.add(itemInterface2);
        menu.add(itemInterface3);

        barMenu.add(menu);
        setJMenuBar(barMenu);
    }

    public void creerWidget(){
        creerText();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabBouton[i][j] = (new JButton(new ImageIcon("Bejeweled"+model.getTableauValeurs()+".png")));
                tabBouton[i][j].addActionListener(button);
                Dimension dim = new Dimension();
                dim.setSize(60,60);
                tabBouton[i][j].setPreferredSize(dim);
                tabBouton[i][j].setName(i+";"+j);
                panel.add(tabBouton[i][j]);
            }
        }
        timer();

        JPanel p=new JPanel();
        p.add(new JLabel("Progress :"));
        p.add(prog);

        global.add(text);
        global.add(panel);
        global.add(p);
        setContentPane(global);
    }

    public void newGame(){
        init();
        creerWidget();
        model.setNiveau(1);
        model.setScore(0);
        model.setTries(15);
        model.setScoreTimer(50);
        refresh();
    }

    public void setMenuContoler(ActionListener listener){
        itemInterface1.addActionListener(listener);
        itemInterface2.addActionListener(listener);
        itemInterface3.addActionListener(listener);
    }

    public void refresh(){
        global.removeAll();
        score.setText("Score : " + model.getScore());
        level.setText("Level : " + model.getNiveau());
        tries.setText("Tries : " + model.getTries());
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tabBouton[i][j].setIcon(new ImageIcon("Bejeweled"+model.getTableauValeurs()[i][j]+".png"));
                panel.add(tabBouton[i][j]);
            }
        }
        JPanel text = new JPanel();
        text.add(score);
        text.add(level);
        text.add(tries);

        JPanel p=new JPanel();
        p.add(new JLabel("Progress :"));
        p.add(prog);

        global.add(text);
        global.add(panel);
        global.add(p);
        global.updateUI();
    }

    public void timer(){
        final int[] cpt = {0};
        t=new Timer(1000,new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(cpt[0] == 3){
                    model.setScoreTimer(model.getScoreTimer()-model.getNiveau());
                    prog.setValue(model.getScoreTimer());
                    cpt[0]=0;
                }
                cpt[0]++;
                if(model.testEnd()){
                    t.stop();
                    msgWarn("Game over");
                    msgWarn(model.end());
                    newGame();
                    refresh();
                }
            }
        });
    }

    public void pause(){
        t.stop();
        global.removeAll();
        global.updateUI();
    }

    public void topScore(){
        msgInfo(model.topScoreStr());
    }

    public JButton[][] getTabBouton() {
        return tabBouton;
    }

    public JMenuItem getItemInterface1() { return itemInterface1; }
    public JMenuItem getItemInterface2() { return itemInterface2; }
    public JMenuItem getItemInterface3() { return itemInterface3; }

    public void msgWarn(String msgErreur){
        JOptionPane d = new JOptionPane();
        d.showMessageDialog(this, msgErreur, "Game over", JOptionPane.WARNING_MESSAGE);
        JDialog fenErr = d.createDialog(this, "Fin");
    }

    public void msgInfo(String msgInfo){
        JOptionPane d = new JOptionPane();
        d.showMessageDialog(this, msgInfo, "Information", JOptionPane.INFORMATION_MESSAGE);
        JDialog fenErr = d.createDialog(this, "Fin");
    }
}
