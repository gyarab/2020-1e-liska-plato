/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plató;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author foxjo
 */
public class Box extends Rectangle {
 
    public Box() {
        this.setHeight(45);
        this.setWidth(45);
        this.setArcHeight(8);
        this.setArcWidth(8);
        this.setFill(Color.valueOf("#b58b51"));
    }
}
