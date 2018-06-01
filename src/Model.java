import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Model {
    private int[][] tableauValeurs = new int[8][8];
    private int niveau = 1;
    private int score = 0;
    private int tries = 15;
    private int scoreTimer = 50;
    private ArrayList<Integer>lScore = new ArrayList<>();

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

    public void scoreTimer(int scoreTimer){
        this.scoreTimer += scoreTimer;
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

    public void scoreAdd(int nb_Ali){
        switch (nb_Ali){
            case 3:
                score += 100*niveau;
                break;
            case 4:
                score += 300*niveau;
                break;
            case 5:
                score += 1000*niveau;
        }
    }

    public void testNiveau(){
        if (scoreTimer >= 100){
            this.niveau++;
            scoreTimer = 50;
        }
    }

    public boolean testEnd(){
        if ( tries == 0){
            return true;
        }
        if ( scoreTimer <= 0){
            return true;
        }
//        if (testDefaite()){
//            return true;
//        }
        return false;
    }

    public void triesFail(){
        this.tries--;
    }

    public String end(){
        String msg = "Top score : \n";
        lireScore();
        if (placeScore() > -1){
            msg += "    N°1 :"+lScore.get(0)+"\n";
            msg += "    N°2 :"+lScore.get(1)+"\n";
            msg += "    N°3 :"+lScore.get(2)+"\n";
            msg += "Votre score est placer n° "+(placeScore()+1)+"\n";
            msg += "Votre score est de :"+score;
        }
        else{
            msg += "    N°1 : "+lScore.get(0)+"\n";
            msg += "    N°2 : "+lScore.get(1)+"\n";
            msg += "    N°3 : "+lScore.get(2)+"\n";
            msg += "Votre score ne fait pas partie du top 3";
        }
        ecrireScore();

        return msg;
    }

    public String topScoreStr(){
        lireScore();
        String msg = "Top score : \n";
        msg += "    N°1 : "+lScore.get(0)+"\n";
        msg += "    N°2 : "+lScore.get(1)+"\n";
        msg += "    N°3 : "+lScore.get(2)+"\n";

        return msg;
    }

    public int placeScore(){
        for (int i = 0 ; i < 3 ; i++){
            if (score > lScore.get(i)){
                return i;
            }
        }
        return -10;
    }

    public void lireScore(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("score.txt"));
            String scoreLu;
            lScore.removeAll(lScore);

            while ((scoreLu = br.readLine()) != null){
                try {
                    lScore.add(Integer.parseInt(scoreLu));
                } catch (NumberFormatException en){}
            }

            br.close();
        }catch (IOException e) {}
        triScore();
    }

    public void triScore(){
        Collections.sort(lScore);
        Collections.reverse(lScore);
    }

    public void ecrireScore(){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("score.txt",true));
            lScore.add(score);
            bw.write(""+lScore.get(lScore.size()-1));
            bw.newLine();
            bw.close();
        }catch (IOException e2){}
    }

    public boolean testDefaite(){
        int[][] tabtest = tableauValeurs;
        for (int x = 0 ; x < 8 ; x++){
            for (int y = 0 ; y < 8 ; y++){
                if (y < 7){
                    int temp = tabtest[x][y];
                    tabtest[x][y] = tabtest[x][y+1];
                    tabtest[x][y+1] = temp;
                    if(testAli(tabtest)){
                        return false;
                    }
                    temp = tabtest[x][y];
                    tabtest[x][y] = tabtest[x][y+1];
                    tabtest[x][y+1] = temp;
                }
            }
        }
        tabtest = tableauValeurs;
        for (int x = 0 ; x < 8 ; x++){
            for (int y = 0 ; y < 8 ; y++){
                if (x < 7){
                    int temp = tabtest[x][y];
                    tabtest[x][y] = tabtest[x+1][y];
                    tabtest[x+1][y] = temp;
                    if(testAli(tabtest)){
                        return false;
                    }
                    temp = tabtest[x][y];
                    tabtest[x][y] = tabtest[x+1][y];
                    tabtest[x+1][y] = temp;
                }
            }
        }
        return true;
    }

    private boolean testAli(int[][] tabTest) {
        for (int x = 0 ; x < 8 ; x++){
            for (int y = 0 ; y < 8 ; y++){
                try {
                    if (tabTest[x][y] == tabTest[x][y+1] && tabTest[x][y] == tabTest[x][y+2]){
                        return true;
                    }
                } catch (ArrayIndexOutOfBoundsException e){}
                try {
                    if (tabTest[x][y] == tabTest[x+1][y] && tabTest[x][y] == tabTest[x+2][y]){
                        return true;
                    }
                } catch (ArrayIndexOutOfBoundsException e){}
            }
        }
        return false;
    }


    public int getScoreTimer() {
        return scoreTimer;
    }

    public void setScoreTimer(int scoreTimer) {
        this.scoreTimer = scoreTimer;
        testNiveau();
    }

    public void setNiveau(int niveau) { this.niveau = niveau; }

    public void setScore(int score) { this.score = score; }

    public void setTries(int tries) { this.tries = tries; }

    public int getTries(){ return tries; }

    public int getNiveau(){ return niveau; }

    public int getScore() { return score; }

    public int[][] getTableauValeurs() { return tableauValeurs; }

    public void setTableauValeurs(int[][] tableauValeurs) { this.tableauValeurs = tableauValeurs; }

    public ArrayList<Integer> getlScore() {
        return lScore;
    }
}
