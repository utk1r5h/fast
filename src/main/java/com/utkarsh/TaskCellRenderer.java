// src/main/java/com/utkarsh/TaskCellRenderer.java

package com.utkarsh;

import javax.swing.*;
import java.awt.*;

// This class will control HOW each item in the JList is drawn
public class TaskCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(
            JList<?> list, 
            Object value, 
            int index, 
            boolean isSelected, 
            boolean cellHasFocus) {

        // Get the component (a JLabel) that would normally be drawn
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof Task) {
            Task task = (Task) value;

            // Use the task's toString() method as the base text
            String text = task.toString();

            if (task.getStatus() == Status.DONE) {
                // For DONE tasks: add strikethrough and set color to gray
                // We can use simple HTML to format the label
                setText("<html><strike>" + text + "</strike></html>");
                c.setForeground(Color.GRAY);
            } else {
                // For TODO tasks: just set the text normally
                setText(text);
                c.setForeground(Color.BLACK); // Ensure it's black if re-used
            }
            
            // Handle selection color
            if (isSelected) {
                c.setBackground(list.getSelectionBackground());
                c.setForeground(list.getSelectionForeground());
            } else {
                 c.setBackground(list.getBackground());
                 // Foreground color is already set above
            }
        }
        
        return c;
    }
}