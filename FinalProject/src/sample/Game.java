package sample;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {

    public int homeQ1Score, homeQ2Score, homeQ3Score, homeQ4Score;

    public int awayQ1Score, awayQ2Score, awayQ3Score, awayQ4Score;

    public int homeFinal, awayFinal;

    public int homePAFH, awayPAFH, homePASH, awayPASH;

    public int homeQ1PointDiff, awayQ1PointDiff;

    public int homeQ2PointDiff, awayQ2PointDiff;

    public int homeQ3PointDiff, awayQ3PointDiff;

    public int homeQ4PointDiff, awayQ4PointDiff;

    public int homeFHPointDiff, awayFHPointDiff;

    public int homeSHPointDiff, awaySHPointDiff;

    public int homeFinalPointDiff, awayFinalPointDiff;

    public int homeQuarterLow, homeQuarterHigh;

    public int awayQuarterLow, awayQuarterHigh;

    public static Game AddGame(String[] data){


        int hq1 = Integer.parseInt(data[0]);
        int hq2 = Integer.parseInt(data[1]);
        int hq3 = Integer.parseInt(data[2]);
        int hq4 = Integer.parseInt(data[3]);

        int aq1 = Integer.parseInt(data[4]);
        int aq2 = Integer.parseInt(data[5]);
        int aq3 = Integer.parseInt(data[6]);
        int aq4 = Integer.parseInt(data[7]);

        return new Game(hq1,hq2,hq3,hq4,aq1,aq2,aq3,aq4);

    }


    public Game(int hQ1, int hQ2, int hQ3, int hQ4, int aQ1, int aQ2, int aQ3, int aQ4){

        homeQ1Score = hQ1;
        homeQ2Score = hQ2;
        homeQ3Score = hQ3;
        homeQ4Score = hQ4;

        awayQ1Score = aQ1;
        awayQ2Score = aQ2;
        awayQ3Score = aQ3;
        awayQ4Score = aQ4;

        homeFinal = hQ1+hQ2+hQ3+hQ4;
        awayFinal = aQ1+aQ2+aQ3+aQ4;

        homePAFH = hQ1 + hQ2;
        homePASH = hQ3 + hQ4;

        awayPAFH = aQ1 + aQ2;
        awayPASH = aQ3 + aQ4;

        homeQ1PointDiff = hQ1 - aQ1;
        homeQ2PointDiff = hQ2 - aQ2;
        homeQ3PointDiff = hQ3 - aQ3;
        homeQ4PointDiff = hQ4 - aQ4;

        homeFHPointDiff = homePAFH - awayPAFH;
        homeSHPointDiff = homePASH - awayPASH;

        homeFinalPointDiff = homeFinal - awayFinal;

        awayQ1PointDiff = aQ1 - hQ1;
        awayQ2PointDiff = aQ2 - hQ2;
        awayQ3PointDiff = aQ3 - hQ3;
        awayQ4PointDiff = aQ4 - hQ4;

        awayFHPointDiff = awayPAFH - homePAFH;
        awaySHPointDiff = awayPASH - homePASH;

        awayFinalPointDiff = awayFinal - homeFinal;

    }


}
