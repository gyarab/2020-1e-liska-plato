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
public class VoidTile extends Rectangle {
    
    public VoidTile() {
        this.setHeight(64);
        this.setWidth(64);
        this.setFill(Color.valueOf("#1b1c24"));
    }
}
