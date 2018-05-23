public class Model {
    private int[][] tableauValeurs = new int[8][8];

    public void initTab(){
        rempliTab();
        boolean end = true;
        while (end){
            end = false;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (verficationLigne(i, j)) {
                        end = true;
                    }
                    if (verficationColonne(i, j)) {
                        end = true;
                    }
                }
            }
        }
    }

    public void rempliTab(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tableauValeurs[i][j] = (int) Math.floor(Math.random() * 8);
            }
        }
    }

    public boolean verficationLigne(int x, int y){
        boolean resulta =false;
        try {
            if (tableauValeurs[x][y] == tableauValeurs[x][y+1] && tableauValeurs[x][y] == tableauValeurs[x][y+2]){
                resulta = true;
                try {
                    if (tableauValeurs[x][y] == tableauValeurs[x][y+3]){
                        try {
                            if (tableauValeurs[x][y] == tableauValeurs[x][y+4]){
                                dumpLigne(x,y,4);
                            }
                        } catch (ArrayIndexOutOfBoundsException e){}
                        dumpLigne(x,y,3);
                    }
                } catch (ArrayIndexOutOfBoundsException e){}
                dumpLigne(x, y, 2);
            }
        } catch (ArrayIndexOutOfBoundsException e){}
        return resulta;
    }

    public boolean verficationColonne(int x, int y){
        boolean resulta =false;
        int nbAligne=0;
        try {
            if (tableauValeurs[x][y] == tableauValeurs[x+1][y] && tableauValeurs[x][y] == tableauValeurs[x+2][y]){
                resulta = true;
                nbAligne = getNbAligne(x, y, nbAligne, tableauValeurs);
                nbAligne+=2;
                for (int i = 0 ; i <= nbAligne ; i++) {
                    dumpColonne(x + nbAligne, y);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e){}
        return resulta;
    }

    static int getNbAligne(int x, int y, int nbAligne, int[][] tableauValeurs) {
        try {
            if (tableauValeurs[x][y] == tableauValeurs[x+3][y]){
                try {
                    if (tableauValeurs[x][y] == tableauValeurs[x+4][y]){
                        nbAligne++;
                    }
                } catch (ArrayIndexOutOfBoundsException e){}
                nbAligne++;
            }
        } catch (ArrayIndexOutOfBoundsException e){}
        return nbAligne;
    }

    public void dumpLigne(int x, int y, int nbfois){
        for (int n = 0 ; n <= nbfois ; n++) {
            for (int i = x; i >= 1; i--) {
                tableauValeurs[i][y+n] = tableauValeurs[i - 1][y+n];
            }
            tableauValeurs[0][y+n] = (int) Math.floor(Math.random() * 8);
        }
    }

    public void dumpColonne(int x, int y){
        for ( int i = x ; i >= 1 ; i--){
            tableauValeurs[i][y] = tableauValeurs[i-1][y];
        }
        tableauValeurs[0][y] = (int) Math.floor(Math.random() * 8);
    }

    public int[][] getTableauValeurs() { return tableauValeurs; }

    public void setTableauValeurs(int[][] tableauValeurs) {
        this.tableauValeurs = tableauValeurs;
    }
}
