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
    private int[][] tab;
    private int nbAligne;

    public ControlButton(Vue vue, Model model){
        this.vue = vue;
        this.model = model;
    }

    public void actionPerformed(ActionEvent e){
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
        if (verifMouv()) {
            tab = model.getTableauValeurs();
            reverse(tab);

            for (int k = 0 ; k < 8 ; k++){
                for (int l = 0 ; l < 8 ; l++){
                    if (verficationLigne(k,l,tab)){

                        model.setTableauValeurs(tab);
                        vue.refresh();
                    }
                    if (verficationColonne(k,l,tab)){

                        model.setTableauValeurs(tab);
                        vue.refresh();
                    }
                }
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
        int nbAligne=0;
        try {
            if (tab[x][y] == tab[x][y+1] && tab[x][y] == tab[x][y+2]){
                resulta = true;
                try {
                    if (tab[x][y] == tab[x][y+2] && tab[x][y] == tab[x][y+3]){
                        try {
                            if (tab[x][y] == tab[x][y+3] && tab[x][y] == tab[x][y+4]){
                                dumpLigne(x,y,4);
                                nbAligne++;
                            }
                        } catch (ArrayIndexOutOfBoundsException e){}
                        dumpLigne(x,y,3);
                        nbAligne++;
                    }
                } catch (ArrayIndexOutOfBoundsException e){}
                System.out.println(x+" "+y);
                dumpLigne(x, y, 2);
                nbAligne+=3;
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
                try {
                    if (tab[x][y] == tab[x+2][y] && tab[x][y] == tab[x+3][y]){
                        try {
                            if (tab[x][y] == tab[x+3][y] && tab[x][y] == tab[x+4][y]){
                                for(int i = 0 ; i < 5 ; i++){
                                    nbAligne++;
                                }
                            }
                        } catch (ArrayIndexOutOfBoundsException e){}
                        for(int i = 0 ; i < 4 ; i++){
                            nbAligne++;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e){}
                nbAligne+=3;
                for (int i = 0 ; i < nbAligne ; i++) {
                    System.out.println(nbAligne);
                    dumpColonne(x + nbAligne, y);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e){}
        return resulta;
    }

    public void dumpLigne(int x, int y, int nbfois){
        for (int n = 0 ; n <= nbfois ; n++) {
            System.out.println("n ="+n);
            for (int i = x; i >= 1; i--) {
                System.out.println("i ="+i);
                tab[i][y] = tab[i - 1][y+n];
                System.out.println("Switch");
            }
            System.out.println("Random");
            tab[0][y+n] = (int) Math.floor(Math.random() * 7);
            System.out.println("n2 = "+n);
        }
    }

    public void dumpColonne(int x, int y){
        for ( int i = x ; i == 1 ; i--){
            tab[i][y] = tab[i-1][y];
        }
        tab[0][y] = (int) Math.floor(Math.random() * 7);
    }



}