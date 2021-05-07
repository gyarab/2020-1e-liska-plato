/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plató;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author foxjo
 */
public class Glow extends Circle{
    private int glowLevel;
    
    public Glow() {
        this.glowLevel = 1;
        this.setRadius(19);
        this.setFill(Color.valueOf("#31b2a1c2"));
    }
    
    public void intensify(){
        glowLevel++;
        switch(glowLevel){
            case(2):{
                this.setRadius(21);
                this.setFill(Color.valueOf("#66d347c2"));
                break;
            }
            case(3):{
                this.setRadius(23);
                this.setFill(Color.valueOf("#b3c945c2"));
                break;
            }
            case(4):{
                this.setRadius(26);
                this.setFill(Color.valueOf("ae9341c2"));
                break;
            }
            case(5):{
                this.setRadius(28);
                this.setFill(Color.valueOf("a1463bc2"));
                break;
            }
            default:{
            }
        }
    }
 
    public int getGlowLevel(){
        return (glowLevel);
    }
}
