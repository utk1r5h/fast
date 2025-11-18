// src/main/java/com/utkarsh/TaskMasterGUI.java

package com.utkarsh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter; // --- NEW ---
import java.awt.event.MouseEvent;   // --- NEW ---
import java.util.List;

public class TaskMasterGUI {

    // Backend: Re-uses your existing TaskManager
    private TaskManager taskManager;

    // GUI Components
    private JFrame frame;
    private DefaultListModel<Task> listModel;
    private JList<Task> taskList;
    private JTextField taskInputField;
    private JButton addButton;
    private JButton doneButton;
    private JButton editButton;
    private JButton clearButton;

    public TaskMasterGUI() {
        // 1. Initialize the backend
        taskManager = new TaskManager();

        // 2. Set up the main window (JFrame)
        frame = new JFrame("TaskMaster GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());

        // 3. Set up the task list display (JList)
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // --- NEW --- Use the custom renderer
        taskList.setCellRenderer(new TaskCellRenderer()); 
        
        JScrollPane scrollPane = new JScrollPane(taskList);
        frame.add(scrollPane, BorderLayout.CENTER);

        // 4. Set up the input panel at the bottom
        JPanel inputPanel = new JPanel(new BorderLayout());
        taskInputField = new JTextField();
        addButton = new JButton("Add Task");
        inputPanel.add(taskInputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // 5. Set up the button panel on the right
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        doneButton = new JButton("Mark as Done");
        editButton = new JButton("Edit Selected");
        clearButton = new JButton("Clear Done Tasks");

        // Add padding and set consistent size for buttons
        Dimension buttonSize = new Dimension(150, 30);
        doneButton.setPreferredSize(buttonSize);
        editButton.setPreferredSize(buttonSize);
        clearButton.setPreferredSize(buttonSize);
        doneButton.setMaximumSize(buttonSize);
        editButton.setMaximumSize(buttonSize);
        clearButton.setMaximumSize(buttonSize);

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(doneButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(editButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(clearButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame.add(buttonPanel, BorderLayout.EAST);

        // 6. Add "Action Listeners" to make buttons work
        
        // --- NEW --- Create a reusable Action for adding tasks
        Action addTaskAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String description = taskInputField.getText();
                if (description != null && !description.trim().isEmpty()) {
                    taskManager.addTask(description.trim());
                    taskInputField.setText(""); // Clear input field
                    refreshTaskList();
                }
            }
        };

        // Add Button
        addButton.addActionListener(addTaskAction);

        // --- NEW --- Add action to the text field for "Enter" key
        taskInputField.addActionListener(addTaskAction);


        // Done Button
        doneButton.addActionListener((ActionEvent e) -> {
            Task selectedTask = taskList.getSelectedValue();
            if (selectedTask != null) {
                taskManager.markTaskAsDone(selectedTask.getId());
                refreshTaskList();
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a task to mark as done.");
            }
        });

        // Edit Button
        editButton.addActionListener((ActionEvent e) -> {
            editSelectedTask(); // --- NEW --- Refactored to a helper method
        });
        
        // --- NEW --- Add double-click listener to the list
        taskList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Check for double-click
                    editSelectedTask();
                }
            }
        });

        // Clear Button
        clearButton.addActionListener((ActionEvent e) -> {
            int confirm = JOptionPane.showConfirmDialog(
                frame, 
                "Are you sure you want to clear all completed tasks?",
                "Confirm Clear",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                taskManager.clearDoneTasks();
                refreshTaskList();
            }
        });

        // 7. Load initial data and show the window
        refreshTaskList();
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }
    
    // --- NEW --- Helper method for editing (to avoid duplicate code)
    private void editSelectedTask() {
        Task selectedTask = taskList.getSelectedValue();
        if (selectedTask != null) {
            String newDescription = JOptionPane.showInputDialog(
                frame, 
                "Enter new description:", 
                selectedTask.getDescription()
            );
            if (newDescription != null && !newDescription.trim().isEmpty()) {
                taskManager.editTask(selectedTask.getId(), newDescription.trim());
                refreshTaskList();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a task to edit.");
        }
    }

    // Helper method to reload tasks from the manager into the GUI list
    private void refreshTaskList() {
        listModel.clear();
        List<Task> tasks = taskManager.getTasks();
        for (Task task : tasks) {
            listModel.addElement(task);
        }
    }

    // Main method to run the GUI application
    public static void main(String[] args) {
        // --- NEW --- Set the Look and Feel to the system's native one
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
            // If it fails, it will just use the default Java look
        }
        
        // This ensures the GUI is created on the correct thread
        SwingUtilities.invokeLater(() -> new TaskMasterGUI());
    }
}