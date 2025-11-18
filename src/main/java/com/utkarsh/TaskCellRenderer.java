package com.utkarsh;

import javax.swing.*;
import java.awt.*;

public class TaskCellRenderer extends DefaultListCellRenderer{

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if(value instanceof Task){
            Task task = (Task) value;
            String text = task.toString();
            if(task.getStatus() == Status.DONE){
                setText("<html><strike>" + text + "</strike></html>");
                c.setForeground(Color.GRAY);
            }else{
                setText(text);
                c.setForeground(Color.BLACK);
            }
            if(isSelected){
                c.setBackground(list.getSelectionBackground());
                c.setForeground(list.getSelectionForeground());
            }else{
                 c.setBackground(list.getBackground());
            }
        }
        return c;
    }
}