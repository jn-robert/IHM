public class Model {
    private int[][] tableauValeurs = new int[8][8];

    public void rempliTab(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tableauValeurs[i][j] = (int) Math.floor(Math.random() * 7);
            }
        }
    }


    public int verficationLigneNb(int x, int y,int[][] tab){
        int i = 0;
        if (y <= 4) {
            while (tab[x][y] == tab[x][y + i]) {
                System.out.println(i);
                i++;
            }
        }
        return i;
    }

    public boolean verficationColonne(int x, int y,int[][] tab){
        if (x+2 >= 8)
            return false;
        if (tab[x][y] == tab[x+1][y] && tab[x][y] == tab[x+2][y])
            return true;
        return false;
    }

    public int verficationColonneNb(int x, int y,int[][] tab){
        int i = 0;
        int compteur = 0;
        if(x <= 5) {
            while (tab[x][y] == tab[x + i][y]) {
                if (tab[x][y] == tab[x + i][y]) {
                    compteur++;
                }
                i++;
            }
        }
        return compteur;
    }


    public int[][] getTableauValeurs() { return tableauValeurs; }

    public void setTableauValeurs(int[][] tableauValeurs) {
        this.tableauValeurs = tableauValeurs;
    }

}
