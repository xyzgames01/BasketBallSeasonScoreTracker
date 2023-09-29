package sample;

/* =============================================================
Author : Zachary Zawodny
Class : ITN261
Class Section : 201
Date : 11/30/2022
Assignment : Final Project
Notes : Basketball Score Analyzer

There are some issues, You can edit the Scores outside of the scores tab, I ran out of time and couldn't fix it,
So I made a little work around to not show the scores after you click a button.
================================================================*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import javafx.scene.control.ListView;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main extends Application {

    static ArrayList<Game> games = new ArrayList<>();
    ObservableList<String> gameData = FXCollections.observableArrayList();

    ListView<String> gameListView = new ListView<>(gameData);

    List<String> lines;

    public boolean selected = true;

    static TextField hq1 = new TextField();
    static TextField hq2 = new TextField();
    static TextField hq3 = new TextField();
    static TextField hq4 = new TextField();
    static TextField aq1 = new TextField();
    static TextField aq2 = new TextField();
    static TextField aq3 = new TextField();
    static TextField aq4 = new TextField();

    @Override
    public void start(Stage stage) throws Exception{

        stage.setTitle("Game Log");

        BorderPane border = new BorderPane();

        HBox buttonMenu = new HBox();

        VBox gameDataPanel = new VBox();
        gameDataPanel.setPrefSize(835, 300);

        VBox gameEditPanel = new VBox();
        gameEditPanel.setPrefSize(300, 100);
        gameEditPanel.setAlignment(Pos.TOP_CENTER);

        loadGames("src/sample/game_list.txt");

        gameEditPanel.getChildren().add(hq1);
        gameEditPanel.getChildren().add(hq2);
        gameEditPanel.getChildren().add(hq3);
        gameEditPanel.getChildren().add(hq4);
        gameEditPanel.getChildren().add(aq1);
        gameEditPanel.getChildren().add(aq2);
        gameEditPanel.getChildren().add(aq3);
        gameEditPanel.getChildren().add(aq4);

        gameDataPanel.getChildren().add(gameListView);

        gameListView.setOnMouseClicked(mouseEvent -> EditScores());

        Button editScores = new Button("Enter/Edit Scores");
        editScores.setOnAction(event -> loadGames("src/sample/game_list.txt"));
        buttonMenu.getChildren().add(editScores);

        Button avgPoints = new Button("Average Points");
        avgPoints.setOnAction(event -> CalculateAverages());
        buttonMenu.getChildren().add(avgPoints);

        Button avgPointDifferential = new Button("Average Point Differential");
        avgPointDifferential.setOnAction(event -> CalculateAveragePointDifferential());
        buttonMenu.getChildren().add(avgPointDifferential);

        Button highLowScored = new Button("Highest and Lowest Point Total");
        highLowScored.setOnAction(event -> GameHighLow());
        buttonMenu.getChildren().add(highLowScored);

        Button submit = new Button("Submit");
        submit.setOnAction(event -> Submit());
        gameEditPanel.getChildren().add(submit);

        border.setTop(buttonMenu);
        border.setCenter(gameDataPanel);
        border.setRight(gameEditPanel);

        Scene scene = new Scene(border, 835, 400);

        stage.setScene(scene);

        stage.show();

    }

    public void EditScores(){

        if(!selected) return;

        hq1.setText(Integer.toString(games.get(gameListView.getSelectionModel().getSelectedIndex()).homeQ1Score));
        hq2.setText(Integer.toString(games.get(gameListView.getSelectionModel().getSelectedIndex()).homeQ2Score));
        hq3.setText(Integer.toString(games.get(gameListView.getSelectionModel().getSelectedIndex()).homeQ3Score));
        hq4.setText(Integer.toString(games.get(gameListView.getSelectionModel().getSelectedIndex()).homeQ4Score));
        aq1.setText(Integer.toString(games.get(gameListView.getSelectionModel().getSelectedIndex()).awayQ1Score));
        aq2.setText(Integer.toString(games.get(gameListView.getSelectionModel().getSelectedIndex()).awayQ2Score));
        aq3.setText(Integer.toString(games.get(gameListView.getSelectionModel().getSelectedIndex()).awayQ3Score));
        aq4.setText(Integer.toString(games.get(gameListView.getSelectionModel().getSelectedIndex()).awayQ4Score));

    }



    public void GameHighLow(){

        gameListView.getItems().clear();

        hq1.setText("");
        hq2.setText("");
        hq3.setText("");
        hq4.setText("");
        aq1.setText("");
        aq2.setText("");
        aq3.setText("");
        aq4.setText("");

        List<Integer> low = new ArrayList<Integer>();

        for(Game each: games){
            low.add(each.homeQ1Score);
            low.add(each.homeQ2Score);
            low.add(each.homeQ3Score);
            low.add(each.homeQ4Score);

            low.add(each.awayQ1Score);
            low.add(each.awayQ2Score);
            low.add(each.awayQ3Score);
            low.add(each.awayQ4Score);
        }

        Collections.sort(low);

        String haLow;
        haLow = String.format("The Season low is: %s points in a quarter", low.get(0));
        gameData.add(haLow);

        String haHigh;
        haHigh = String.format("The Season high is: %s points in a quarter", low.get(79));;
        gameData.add(haHigh);

    }

    public void CalculateAveragePointDifferential(){
        gameListView.getItems().clear();

        hq1.setText("");
        hq2.setText("");
        hq3.setText("");
        hq4.setText("");
        aq1.setText("");
        aq2.setText("");
        aq3.setText("");
        aq4.setText("");

        float hQ1PD = 0;
        float hQ2PD = 0;
        float hQ3PD = 0;
        float hQ4PD = 0;

        float hFHPD = 0;
        float hSHPD = 0;

        float hFPD = 0;

        float aQ1PD = 0;
        float aQ2PD = 0;
        float aQ3PD = 0;
        float aQ4PD = 0;

        float aFHPD = 0;
        float aSHPD = 0;

        float aFPD = 0;

        for(Game each: games) {

            hQ1PD += each.homeQ1PointDiff;
            hQ2PD += each.homeQ2PointDiff;
            hQ3PD += each.homeQ3PointDiff;
            hQ4PD += each.homeQ4PointDiff;

            hFHPD += each.homeFHPointDiff;
            hSHPD += each.homeSHPointDiff;

            hFPD += each.homeFinalPointDiff;

            aQ1PD += each.awayQ1PointDiff;
            aQ2PD += each.awayQ2PointDiff;
            aQ3PD += each.awayQ3PointDiff;
            aQ4PD += each.awayQ4PointDiff;

            aFHPD += each.awayFHPointDiff;
            aSHPD += each.awaySHPointDiff;

            aFPD += each.awayFinalPointDiff;

        }

        hQ1PD = hQ1PD/10;
        hQ2PD = hQ2PD/10;
        hQ3PD = hQ3PD/10;
        hQ4PD = hQ4PD/10;

        hFHPD = hFHPD/10;
        hSHPD = hSHPD/10;

        hFPD = hFPD/10;

        aQ1PD = aQ1PD/10;
        aQ2PD = aQ2PD/10;
        aQ3PD = aQ3PD/10;
        aQ4PD = aQ4PD/10;

        aFHPD = aFHPD/10;
        aSHPD = aSHPD/10;

        aFPD = aFPD/10;

        String hADPQ;
        hADPQ = String.format("Home Team's Average Point Differential Per Quarter: Q1: %.1f Q2: %.1f Q3: %.1f Q4: %.1f", hQ1PD, hQ2PD, hQ3PD, hQ4PD);
        gameData.add(hADPQ);

        String aADPQ;
        aADPQ = String.format("Home Team's Average Point Differential Per Quarter: Q1: %.1f Q2: %.1f Q3: %.1f Q4: %.1f", aQ1PD, aQ2PD, aQ3PD, aQ4PD);
        gameData.add(aADPQ);

        String hADPH;
        hADPH = String.format("Home Team's Average Point Differential Per Half: First Half: %.1f Second Half: %.1f", hFHPD, hSHPD);
        gameData.add(hADPH);

        String aADPH;
        aADPH = String.format("Away Team's Average Point Differential Per Half: First Half: %.1f Second Half: %.1f", aFHPD, aSHPD);
        gameData.add(aADPH);

        String hADPG;
        hADPG = String.format("Home Team's Average Point Differential Per Game: %.1f", hFPD);
        gameData.add(hADPG);

        String aADPG;
        aADPG = String.format("Away Team's Average Point Differential Per Game: %.1f", aFPD);
        gameData.add(aADPG);


    }

    public void CalculateAverages(){

        gameListView.getItems().clear();

        hq1.setText("");
        hq2.setText("");
        hq3.setText("");
        hq4.setText("");
        aq1.setText("");
        aq2.setText("");
        aq3.setText("");
        aq4.setText("");

        float hPPQ1 = 0;
        float hPPQ2 = 0;
        float hPPQ3 = 0;
        float hPPQ4 = 0;

        float hPPFH = 0;
        float hPPSH = 0;

        float hPPG = 0;

        float aPPQ1 = 0;
        float aPPQ2 = 0;
        float aPPQ3 = 0;
        float aPPQ4 = 0;

        float aPPFH = 0;
        float aPPSH = 0;

        float aPPG = 0;

        for(Game each: games){

            hPPQ1 += each.homeQ1Score;
            hPPQ2 += each.homeQ2Score;
            hPPQ3 += each.homeQ3Score;
            hPPQ4 += each.homeQ4Score;

            hPPFH += each.homePAFH;
            hPPSH += each.homePASH;

            hPPG += each.homeFinal;

            aPPQ1 += each.awayQ1Score;
            aPPQ2 += each.awayQ2Score;
            aPPQ3 += each.awayQ3Score;
            aPPQ4 += each.awayQ4Score;

            aPPFH += each.awayPAFH;
            aPPSH += each.awayPASH;

            aPPG += each.awayFinal;

        }

        hPPQ1 = hPPQ1/10;
        hPPQ2 = hPPQ2/10;
        hPPQ3 = hPPQ3/10;
        hPPQ4 = hPPQ4/10;

        hPPFH = hPPFH/10;
        hPPSH = hPPSH/10;

        hPPG = hPPG/10;

        aPPQ1 = aPPQ1/10;
        aPPQ2 = aPPQ2/10;
        aPPQ3 = aPPQ3/10;
        aPPQ4 = aPPQ4/10;

        aPPFH = aPPFH/10;
        aPPSH = aPPSH/10;

        aPPG = aPPG/10;

        String hAPQ;
        hAPQ = String.format("Home Team's Average Points Per Quarter: Q1: %.1f Q2: %.1f Q3: %.1f Q4: %.1f", hPPQ1, hPPQ2, hPPQ3, hPPQ4);
        gameData.add(hAPQ);

        String aAPQ;
        aAPQ = String.format("Away Team's Average Points Per Quarter: Q1: %.1f Q2: %.1f Q3: %.1f Q4: %.1f", aPPQ1, aPPQ2, aPPQ3, aPPQ4);
        gameData.add(aAPQ);

        String hAPH;
        hAPH = String.format("Home Team's Average Points Per Half: First Half: %.1f Second Half: %.1f", hPPFH, hPPSH);
        gameData.add(hAPH);

        String aAPH;
        aAPH = String.format("Away Team's Average Points Per Half: First Half: %.1f Second Half: %.1f", aPPFH, aPPSH);
        gameData.add(aAPH);

        String hAPG;
        hAPG = String.format("Home Team's Average Points Per Game: %.1f", hPPG);
        gameData.add(hAPG);

        String aAPG;
        aAPG = String.format("Away Team's Average Points Per Game: %.1f", aPPG);
        gameData.add(aAPG);

    }



    public void Submit(){

        if (!selected) return;

        try {
            File file = new File("src/sample/game_list.txt");
            lines = Files.readAllLines(file.toPath());

            int i = games.indexOf(games.get(gameListView.getSelectionModel().getSelectedIndex()));

            lines.set(i,hq1.getText() + ", " + hq2.getText() + ", " + hq3.getText() + ", " + hq4.getText() + ", " + aq1.getText()
                    + ", " + aq2.getText() + ", " + aq3.getText() + ", " + aq4.getText());

            Files.write(file.toPath(), lines);

            loadGames("src/sample/game_list.txt");

            hq1.setText("");
            hq2.setText("");
            hq3.setText("");
            hq4.setText("");
            aq1.setText("");
            aq2.setText("");
            aq3.setText("");
            aq4.setText("");

        }
        catch (Exception e){

        }


    }

    public void loadGames(String filename){
        try{
            games.clear();
            gameData.clear();


            FileReader file = new FileReader(filename);
            BufferedReader br = new BufferedReader(file);
            String line = br.readLine();


            while (line != null) {

                String[] data = line.trim().split(", ");

                Game game = Game.AddGame(data);

                games.add(game);

                line = br.readLine();
            }

            for (Game each: games){
                String scores;
                scores = String.format("Home Score: Q1: %s Q2: %s Q3: %s Q4: %s Away Score: Q1: %s Q2: %s Q3: %s Q4:" +
                        " %s Final: %s-%s",each.homeQ1Score, each.homeQ2Score, each.homeQ3Score,each.homeQ4Score,
                        each.awayQ1Score, each.awayQ2Score,each.awayQ3Score,each.awayQ4Score, each.homeFinal,
                        each.awayFinal);
                gameData.add(scores);
            }

            br.close();

        } catch (Exception e){

            System.out.println(e);

        }
    }





    public static void main(String[] args) {
        launch(args);



    }
}
