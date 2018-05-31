import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlButton implements ActionListener {
    Vue vue;
    Model model;
    private int fromX;
    private int fromY;
    private int toX;
    private int toY;
    private boolean selection = false;
    private boolean play = false;
    private int[][] tab;

    public ControlButton(Vue vue, Model model){
        this.vue = vue;
        this.model = model;
    }

    public void actionPerformed(ActionEvent e){
        if (!play){
            vue.t.start();
            play = true;
        }
        JButton button = (JButton) e.getSource();
        int x = Integer.parseInt(String.valueOf(button.getName().charAt(0)));
        int y = Integer.parseInt(String.valueOf(button.getName().charAt(2)));
        vue.refresh();

        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                if (button == vue.getTabBouton()[i][j]){
                    if (!selection){
                        fromX = x;
                        fromY = y;
                        selection=true;
                    }
                    else {
                        toX=x;
                        toY=y;
                        selection=false;
                        test();
                    }
                }
            }
        }
    }

    public  void test(){
        boolean restart;
        boolean checkReverse = false;
        if (verifMouv()) {
            tab = model.getTableauValeurs();
            reverse(tab);
            restart = true;
            while (restart) {
                restart = false;
                for (int k = 0; k < 8; k++) {
                    for (int l = 0; l < 8; l++) {
                        if (verficationLigne(k, l, tab)) {
                            restart = true;
                            checkReverse = true;
                            model.setTableauValeurs(tab);
                            vue.refresh();
                        }
                        if (verficationColonne(k, l, tab)) {
                            restart = true;
                            checkReverse = true;
                            model.setTableauValeurs(tab);
                            vue.refresh();
                        }
                    }
                }
            }
            if (! checkReverse){
                reverse(tab);
                model.setTableauValeurs(tab);
                model.triesFail();
            }
            if (model.testEnd()){
                vue.t.stop();
                vue.msg(model.end());
                vue.newGame();
            }
            vue.refresh();
        }
    }

    public boolean verifMouv(){
        boolean test = false;
        try {
            if (toX == fromX && toX == fromY)
                test = false;
            if ((toX == fromX+1 || toX == fromX-1) && toY == fromY){
                test = true;
            }
            if ((toY == fromY+1 || toY == fromY-1) && toX == fromX){
                test = true;
            }
        } catch (ArrayIndexOutOfBoundsException ex){
        }
        return test;
    }

    public int[][] reverse(int[][] tabV){
        int temp = tabV[fromX][fromY];
        tabV[fromX][fromY] = tabV[toX][toY];
        tabV[toX][toY] = temp;
        return tabV;
    }

    public boolean verficationLigne(int x, int y,int[][] tab){
        boolean resulta =false;
        try {
            if (tab[x][y] == tab[x][y+1] && tab[x][y] == tab[x][y+2]){
                resulta = true;
                try {
                    if (tab[x][y] == tab[x][y+3]){
                        try {
                            if (tab[x][y] == tab[x][y+4]){
                                dumpLigne(x,y,4);
                                model.scoreTimer(5);
                                model.scoreAdd(5);
                            }
                        } catch (ArrayIndexOutOfBoundsException e){}
                        dumpLigne(x,y,3);
                        model.scoreTimer(4);
                        model.scoreAdd(4);
                    }
                } catch (ArrayIndexOutOfBoundsException e){}
                dumpLigne(x, y, 2);
                model.scoreTimer(3);
                model.scoreAdd(3);
            }
        } catch (ArrayIndexOutOfBoundsException e){}
        return resulta;
    }

    public boolean verficationColonne(int x, int y,int[][] tab){
        boolean resulta =false;
        int nbAligne=0;
        try {
            if (tab[x][y] == tab[x+1][y] && tab[x][y] == tab[x+2][y]){
                resulta = true;
                nbAligne = Model.getNbAligne(x, y, nbAligne, tab);
                nbAligne+=2;
                model.scoreTimer(nbAligne);
                model.scoreAdd(nbAligne+1);
                for (int i = 0 ; i <= nbAligne ; i++) {
                    dumpColonne(x + nbAligne, y);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e){}
        return resulta;
    }

    public void dumpLigne(int x, int y, int nbfois){
        for (int n = 0 ; n <= nbfois ; n++) {
            for (int i = x; i >= 1; i--) {
                tab[i][y+n] = tab[i - 1][y+n];
            }
            tab[0][y+n] = (int) Math.floor(Math.random() * 8);
        }
    }

    public void dumpColonne(int x, int y){
        for ( int i = x ; i >= 1 ; i--){
            tab[i][y] = tab[i-1][y];
        }
        tab[0][y] = (int) Math.floor(Math.random() * 8);
    }

}