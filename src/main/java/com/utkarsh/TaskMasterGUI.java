package com.utkarsh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import com.utkarsh.util.StringUtil;

public class TaskMasterGUI{

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
    private JButton deleteButton;
    private JButton searchButton;
    private JButton statsButton;
    private JButton tagsButton;
    private JButton exportButton;

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
        deleteButton = new JButton("Delete Task");
        clearButton = new JButton("Clear Done Tasks");
        searchButton = new JButton("Search Tasks");
        statsButton = new JButton("Show Stats");
        tagsButton = new JButton("View Tags");
        exportButton = new JButton("Export to CSV");

        // Add padding and set consistent size for buttons
        Dimension buttonSize = new Dimension(150, 30);
        doneButton.setPreferredSize(buttonSize);
        editButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        clearButton.setPreferredSize(buttonSize);
        searchButton.setPreferredSize(buttonSize);
        statsButton.setPreferredSize(buttonSize);
        tagsButton.setPreferredSize(buttonSize);
        exportButton.setPreferredSize(buttonSize);
        doneButton.setMaximumSize(buttonSize);
        editButton.setMaximumSize(buttonSize);
        deleteButton.setMaximumSize(buttonSize);
        clearButton.setMaximumSize(buttonSize);
        searchButton.setMaximumSize(buttonSize);
        statsButton.setMaximumSize(buttonSize);
        tagsButton.setMaximumSize(buttonSize);
        exportButton.setMaximumSize(buttonSize);

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(doneButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(editButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(clearButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(searchButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(statsButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(tagsButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(exportButton);
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

        // Delete Button
        deleteButton.addActionListener((ActionEvent e) -> {
            Task selectedTask = taskList.getSelectedValue();
            if (selectedTask != null) {
                int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to delete this task?\n\"" + selectedTask.getDescription() + "\"",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    taskManager.deleteTask(selectedTask.getId());
                    refreshTaskList();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a task to delete.");
            }
        });

        // Search Button
        searchButton.addActionListener((ActionEvent e) -> {
            String keyword = JOptionPane.showInputDialog(frame, "Enter search keyword:", "Search Tasks", JOptionPane.PLAIN_MESSAGE);
            if (keyword != null && !keyword.trim().isEmpty()) {
                searchTasks(keyword.trim());
            }
        });

        // Stats Button
        statsButton.addActionListener((ActionEvent e) -> {
            showStatistics();
        });

        // Tags Button
        tagsButton.addActionListener((ActionEvent e) -> {
            showTags();
        });

        // Export Button
        exportButton.addActionListener((ActionEvent e) -> {
            exportToCSV();
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

    // Helper method for searching tasks
    private void searchTasks(String keyword) {
        List<Task> allTasks = taskManager.getTasks();
        List<Task> matchedTasks = allTasks.stream()
            .filter(t -> StringUtil.contains(t.getDescription(), keyword))
            .toList();
        
        if (matchedTasks.isEmpty()) {
            JOptionPane.showMessageDialog(frame, 
                "No tasks found matching: \"" + keyword + "\"",
                "Search Results",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder results = new StringBuilder();
            results.append("Found ").append(matchedTasks.size()).append(" task(s) matching \"").append(keyword).append("\":\n\n");
            for (Task task : matchedTasks) {
                results.append("ID: ").append(task.getId())
                       .append(" - ").append(task.getDescription())
                       .append(" [").append(task.getStatus()).append("]\n");
            }
            JTextArea textArea = new JTextArea(results.toString());
            textArea.setEditable(false);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));
            JOptionPane.showMessageDialog(frame, scrollPane, "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Helper method for showing statistics
    private void showStatistics() {
        List<Task> tasks = taskManager.getTasks();
        long total = tasks.size();
        long done = tasks.stream().filter(t -> t.getStatus() == Status.DONE).count();
        long todo = total - done;
        double completion = total > 0 ? (done * 100.0 / total) : 0;
        
        StringBuilder stats = new StringBuilder();
        stats.append("Task Statistics\n");
        stats.append("═══════════════════\n\n");
        stats.append("Total Tasks: ").append(total).append("\n");
        stats.append("Completed: ").append(done).append("\n");
        stats.append("Pending: ").append(todo).append("\n");
        stats.append(String.format("Completion Rate: %.1f%%", completion));
        
        JOptionPane.showMessageDialog(frame, stats.toString(), "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

    // Helper method for showing all tags
    private void showTags() {
        Set<String> allTags = taskManager.getAllTags();
        if (allTags.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No tags found in any tasks.", "All Tags", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder tagsList = new StringBuilder();
            tagsList.append("All Tags (").append(allTags.size()).append(" total):\n\n");
            for (String tag : allTags) {
                tagsList.append("• ").append(tag).append("\n");
            }
            JTextArea textArea = new JTextArea(tagsList.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(300, 250));
            JOptionPane.showMessageDialog(frame, scrollPane, "All Tags", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Helper method for exporting tasks to CSV
    private void exportToCSV() {
        List<Task> tasks = taskManager.getTasks();
        if (tasks.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No tasks to export.", "Export", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Tasks to CSV");
        fileChooser.setSelectedFile(new File("tasks.csv"));
        
        int userSelection = fileChooser.showSaveDialog(frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(fileToSave)) {
                // Write CSV header
                writer.write("ID,Description,Status,Priority,Tags,CreatedOn\n");
                
                // Write task data
                for (Task task : tasks) {
                    String tags = task.getTags() != null ? String.join(";", task.getTags()) : "";
                    writer.write(String.format("%d,\"%s\",%s,%s,\"%s\",%s\n",
                        task.getId(),
                        task.getDescription().replace("\"", "\"\""), // Escape quotes
                        task.getStatus(),
                        task.getPriority(),
                        tags,
                        task.getCreationDate()
                    ));
                }
                
                JOptionPane.showMessageDialog(frame, 
                    "Successfully exported " + tasks.size() + " task(s) to:\n" + fileToSave.getAbsolutePath(),
                    "Export Successful",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, 
                    "Error exporting tasks: " + ex.getMessage(),
                    "Export Failed",
                    JOptionPane.ERROR_MESSAGE);
            }
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