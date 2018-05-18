public class Model {
    private int[][] tableauValeurs = new int[8][8];

    public void rempliTab(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tableauValeurs[i][j] = (int) Math.floor(Math.random() * 8);
            }
        }
    }

    public int[][] getTableauValeurs() { return tableauValeurs; }

    public void setTableauValeurs(int[][] tableauValeurs) {
        this.tableauValeurs = tableauValeurs;
    }
}
