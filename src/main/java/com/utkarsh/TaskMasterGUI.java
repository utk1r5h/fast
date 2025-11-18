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

    public TaskMasterGUI(){
        taskManager = new TaskManager();

        frame = new JFrame("TaskMaster GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setCellRenderer(new TaskCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(taskList);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        taskInputField = new JTextField();
        addButton = new JButton("Add Task");
        inputPanel.add(taskInputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.SOUTH);
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

        Action addTaskAction = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e){
                String description = taskInputField.getText();
                if(description != null && !description.trim().isEmpty()){
                    taskManager.addTask(description.trim());
                    taskInputField.setText("");
                    refreshTaskList();
                }
            }
        };

        addButton.addActionListener(addTaskAction);
        taskInputField.addActionListener(addTaskAction);

        doneButton.addActionListener((ActionEvent e) -> {
            Task selectedTask = taskList.getSelectedValue();
            if(selectedTask != null){
                taskManager.markTaskAsDone(selectedTask.getId());
                refreshTaskList();
            }else{
                JOptionPane.showMessageDialog(frame, "Please select a task to mark as done.");
            }
        });

        editButton.addActionListener((ActionEvent e) -> {
            editSelectedTask();
        });
        
        taskList.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() == 2){
                    editSelectedTask();
                }
            }
        });
        clearButton.addActionListener((ActionEvent e) -> {
            int confirm = JOptionPane.showConfirmDialog(
                frame, 
                "Are you sure you want to clear all completed tasks?",
                "Confirm Clear",
                JOptionPane.YES_NO_OPTION
            );
            if(confirm == JOptionPane.YES_OPTION){
                taskManager.clearDoneTasks();
                refreshTaskList();
            }
        });

        deleteButton.addActionListener((ActionEvent e) -> {
            Task selectedTask = taskList.getSelectedValue();
            if(selectedTask != null){
                int confirm = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to delete this task?\n\"" + selectedTask.getDescription() + "\"",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
                );
                if(confirm == JOptionPane.YES_OPTION){
                    taskManager.deleteTask(selectedTask.getId());
                    refreshTaskList();
                }
            }else{
                JOptionPane.showMessageDialog(frame, "Please select a task to delete.");
            }
        });

        searchButton.addActionListener((ActionEvent e) -> {
            String keyword = JOptionPane.showInputDialog(frame, "Enter search keyword:", "Search Tasks", JOptionPane.PLAIN_MESSAGE);
            if(keyword != null && !keyword.trim().isEmpty()){
                searchTasks(keyword.trim());
            }
        });

        statsButton.addActionListener((ActionEvent e) -> {
            showStatistics();
        });

        tagsButton.addActionListener((ActionEvent e) -> {
            showTags();
        });

        exportButton.addActionListener((ActionEvent e) -> {
            exportToCSV();
        });

        refreshTaskList();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private void editSelectedTask(){
        Task selectedTask = taskList.getSelectedValue();
        if(selectedTask != null){
            String newDescription = JOptionPane.showInputDialog(
                frame, 
                "Enter new description:", 
                selectedTask.getDescription()
            );
            if(newDescription != null && !newDescription.trim().isEmpty()){
                taskManager.editTask(selectedTask.getId(), newDescription.trim());
                refreshTaskList();
            }
        }else{
            JOptionPane.showMessageDialog(frame, "Please select a task to edit.");
        }
    }

    private void refreshTaskList(){
        listModel.clear();
        List<Task> tasks = taskManager.getTasks();
        for(Task task : tasks){
            listModel.addElement(task);
        }
    }

    private void searchTasks(String keyword){
        List<Task> allTasks = taskManager.getTasks();
        List<Task> matchedTasks = allTasks.stream()
            .filter(t -> StringUtil.contains(t.getDescription(), keyword))
            .toList();
        
        if(matchedTasks.isEmpty()){
            JOptionPane.showMessageDialog(frame, 
                "No tasks found matching: \"" + keyword + "\"",
                "Search Results",
                JOptionPane.INFORMATION_MESSAGE);
        }else{
            StringBuilder results = new StringBuilder();
            results.append("Found ").append(matchedTasks.size()).append(" task(s) matching \"").append(keyword).append("\":\n\n");
            for(Task task : matchedTasks){
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

    private void showStatistics(){
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

    private void showTags(){
        Set<String> allTags = taskManager.getAllTags();
        if(allTags.isEmpty()){
            JOptionPane.showMessageDialog(frame, "No tags found in any tasks.", "All Tags", JOptionPane.INFORMATION_MESSAGE);
        }else{
            StringBuilder tagsList = new StringBuilder();
            tagsList.append("All Tags (").append(allTags.size()).append(" total):\n\n");
            for(String tag : allTags){
                tagsList.append("• ").append(tag).append("\n");
            }
            JTextArea textArea = new JTextArea(tagsList.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(300, 250));
            JOptionPane.showMessageDialog(frame, scrollPane, "All Tags", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void exportToCSV(){
        List<Task> tasks = taskManager.getTasks();
        if(tasks.isEmpty()){
            JOptionPane.showMessageDialog(frame, "No tasks to export.", "Export", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export Tasks to CSV");
        fileChooser.setSelectedFile(new File("tasks.csv"));
        
        int userSelection = fileChooser.showSaveDialog(frame);
        if(userSelection == JFileChooser.APPROVE_OPTION){
            File fileToSave = fileChooser.getSelectedFile();
            try(FileWriter writer = new FileWriter(fileToSave)){
                writer.write("ID,Description,Status,Priority,Tags,CreatedOn\n");
                for(Task task : tasks){
                    String tags = task.getTags() != null ? String.join(";", task.getTags()) : "";
                    writer.write(String.format("%d,\"%s\",%s,%s,\"%s\",%s\n",
                        task.getId(),
                        task.getDescription().replace("\"", "\"\""),
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
            }catch(IOException ex){
                JOptionPane.showMessageDialog(frame, 
                    "Error exporting tasks: " + ex.getMessage(),
                    "Export Failed",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e){
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new TaskMasterGUI());
    }
}