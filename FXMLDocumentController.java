/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plató;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author foxjo
 */
public class FXMLDocumentController implements Initializable {
    
    private int xcord = 3;
    private int ycord = 3;
    Box[][] boxlist = new Box[7][7];
    Glow[][] glowlist = new Glow[7][7];
    VoidTile[][] voidlist = new VoidTile[7][7];
    private int tileCount = 49;
    private int boxCount = 3;
    private int glowCount = 0;
    private int turns = 0;
    private int addScore = 0;
    private int glowProbModifier = 2;
    
    @FXML
    private Label krabic, tahu, konecneSkore, aktualniSkore, nejvyssiSkore;
    @FXML
    private GridPane grid;
    @FXML
    private StackPane popUp;
    @FXML
    private Button left, down, right, up, waste, place, hrat;
    @FXML
    private Rectangle player, box;
    
    @FXML
    private void hrej() {
        writeDownScore();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if(voidlist[i][j] != null){
                    grid.getChildren().remove(voidlist[i][j]);
                    voidlist[i][j] = null;
                }
                else{
                    if(boxlist[i][j] != null){
                        grid.getChildren().remove(boxlist[i][j]);
                        boxlist[i][j] = null;
                    }
                    if(glowlist[i][j] != null){
                        grid.getChildren().remove(glowlist[i][j]);
                        glowlist[i][j] = null;
                    }
                }
                
            }
        }
        xcord = 3;
        ycord = 3;
        grid.getChildren().remove(player);
        grid.add(player, xcord, xcord);
        player.setVisible(true);
        
        tileCount = 49;
        boxCount = 3;        
        krabic.setText("" + boxCount);
        turns = 0;
        tahu.setText("" + turns);
        glowCount = 0;
        popUp.setVisible(false);
        right.setDisable(false);
        down.setDisable(false);
        left.setDisable(false);
        up.setDisable(false);
        waste.setDisable(false);
        place.setDisable(false);
        hrat.setText("Hrát znovu");
    }
    
    @FXML
    private void left() {
        move(-1, 0);
    }
    
    @FXML
    private void down() {
        move(0, 1);
    }
    
    @FXML
    private void right() {
        move(1, 0);
    }
    
    @FXML
    private void up() {
        move(0, -1);
    }
    
    @FXML
    private void waste() {
        move(0, 0);
    }
    
    private void move(int x, int y){
        int newX = xcord + x;
        int newY = ycord + y;
        if(!((newX == -1)||(newX == 7)||(newY == -1)||(newY == 7))){ //Pokud je nové políčko na hrací ploše
            if((boxlist[newX][newY] == null) && (voidlist[newX][newY] == null) || ((boxlist[newX][newY] != null) && (boxlist[xcord][ycord] != null))){ //Pokud je políčko ve stejné výšce (propadlé, prázdné, či s krabicí)
                if((glowlist[newX][newY] != null) && (glowlist[newX][newY].getGlowLevel() == 5)){ //Pokud je na novém políčku záře ve fázi pět
                    addScore += 200 + tileCount + turns / 4;
                    grid.getChildren().remove(glowlist[newX][newY]);
                    glowlist[newX][newY] = null;
                    if(newX + newY == xcord + ycord){ //Pokud hráč zůstává na místě
                        addScore -= 150 + turns / 12;
                    }
                    glowCount--;
                    boxCount++;
                    krabic.setText("" + boxCount);
                }
                xcord = newX;
                ycord = newY;
                grid.getChildren().remove(player);
                grid.add(player, xcord, ycord);
                updateCurrentScore();
                allGlowIntensify();
                createGlow();
            }
        } 
    }
    
    @FXML
    private void place() {
        if ((boxlist[xcord][ycord] == null) && (boxCount > 0)){
            boxlist[xcord][ycord] = new Box();
            grid.add(boxlist[xcord][ycord], xcord, ycord);
            GridPane.setHalignment(boxlist[xcord][ycord], HPos.CENTER);
            GridPane.setValignment(boxlist[xcord][ycord], VPos.CENTER);
            if(glowlist[xcord][ycord] != null){
                grid.getChildren().remove(glowlist[xcord][ycord]);
                grid.add(glowlist[xcord][ycord], xcord, ycord);
            }
            grid.getChildren().remove(player);
            grid.add(player, xcord, ycord);
            boxCount--;
            krabic.setText("" + boxCount);
            allGlowIntensify();
            updateCurrentScore();
            createGlow();
        }
        
    }
    
    private void updateCurrentScore(){
        turns++;
        addScore += (Math.pow(turns, 2) / 500.0 + tileCount) / 5 + 5;
        aktualniSkore.setText(Integer.toString(Integer.parseInt(aktualniSkore.getText()) + addScore));
        addScore = 0;
        tahu.setText("" + turns);
    }
    
    private void writeDownScore(){
        if(Integer.parseInt(aktualniSkore.getText())>Integer.parseInt(nejvyssiSkore.getText())){
            nejvyssiSkore.setText(aktualniSkore.getText());
        }
        aktualniSkore.setText("0");
    }
    
    private void allGlowIntensify(){
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                
                if(glowlist[i][j] != null){
                    glowlist[i][j].intensify();
                    
                    if(glowlist[i][j].getGlowLevel() > 5){
                        grid.getChildren().remove(glowlist[i][j]);
                        glowlist[i][j] = null;
                        glowCount--;
                        
                        if(boxlist[i][j] != null){
                            grid.getChildren().remove(boxlist[i][j]);
                            boxlist[i][j] = null;
                        }
                        else{
                            voidlist[i][j] = new VoidTile();
                            grid.add(voidlist[i][j], i, j);
                            GridPane.setHalignment(voidlist[i][j], HPos.CENTER);
                            GridPane.setValignment(voidlist[i][j], VPos.CENTER);
                            tileCount--;
                            if(tileCount == 3){
                                gameOver();
                            }
                        }
                    }
                }    
            }
        }
    }

    private void createGlow() {
        if(tileCount > glowCount){
            boolean i = false;
            if(Math.floor((tileCount / 49.0) * glowProbModifier * Math.random()) == 0){
                i = true;
            }
            while(i){
                int randomX = (int) Math.floor(7 * Math.random());
                int randomY = (int) Math.floor(7 * Math.random());
                if((glowlist[randomX][randomY] == null) && (voidlist[randomX][randomY] == null)){
                    glowlist[randomX][randomY] = new Glow();
                    grid.add(glowlist[randomX][randomY], randomX, randomY);
                    GridPane.setHalignment(glowlist[randomX][randomY], HPos.CENTER);
                    GridPane.setValignment(glowlist[randomX][randomY], VPos.CENTER);
                    glowCount++;
                    glowProbModifier = 6;
                    i = false;
                }
            }
        }
    }    

    private void gameOver() {
        konecneSkore.setText("Vaše skóre: " + aktualniSkore.getText());
        popUp.setVisible(true);
        right.setDisable(true);
        down.setDisable(true);
        left.setDisable(true);
        up.setDisable(true);
        waste.setDisable(true);
        place.setDisable(true);
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
