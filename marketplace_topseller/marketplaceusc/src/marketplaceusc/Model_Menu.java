/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package marketplaceusc;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Model_Menu {

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }
    
    public Model_Menu(String icon, String name, MenuType type){
        this.name = name;
        this.icon = icon;
        this.type = type;
     
    }
    
    public Model_Menu() {
    }

    private String icon;
    private String name;
    private MenuType type;
    
    public Icon toIcon(){
        return new ImageIcon(getClass().getResource(""));
    }
    public static enum MenuType {
        MENU, TITLE, EMPTY
    }
    
}
