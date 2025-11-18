# TaskMaster (tm) 📋

A feature-rich task management application for the command line and GUI, built with Java 17+.

## Features ✨

- **Dual Interface**: CLI and GUI modes
- **Task Management**: Add, edit, delete, and mark tasks as complete
- **Advanced Organization**: Tags, priorities (high/medium/low), and search
- **Statistics & Analytics**: Track completion rates and task metrics
- **User Management**: Support for multiple user types (Admin, Power User, Guest)
- **Background Operations**: Auto-save, reminders, and task processing
- **Data Persistence**: JSON-based storage in `~/.taskmaster/tasks.json`
- **Export Capability**: Export tasks to CSV format

## Prerequisites 🔧

- Java 17 or higher
- Maven 3.6+

## Building the Project 🛠️

```bash
# Clone or navigate to the project directory
cd /home/samman/Documents/fast

# Build the project (creates executable JAR with all dependencies)
mvn clean package

# The executable JAR will be created at: target/fast-1.0-SNAPSHOT.jar
```

## Running TaskMaster 🚀

### Method 1: Using Convenience Scripts

```bash
# Run CLI version
./tm --help
./tm add "My first task"
./tm list

# Run GUI version
./tm-gui
```

### Method 2: Direct JAR Execution

```bash
# CLI version
java -jar target/fast-1.0-SNAPSHOT.jar [command] [options]

# GUI version
java -cp target/fast-1.0-SNAPSHOT.jar com.utkarsh.TaskMasterGUI
```

## CLI Commands 📝

### Basic Commands

```bash
# Add a task
./tm add "Complete project documentation"

# Add a task with tags and priority
./tm add "Review pull requests" --tags work,urgent --priority high

# List all pending tasks
./tm list

# List all tasks (including completed)
./tm list --all

# List tasks sorted by priority
./tm list --sort priority

# Filter tasks by tag
./tm list --tag urgent

# Mark task as done (replace 1 with task ID)
./tm done 1

# Edit a task
./tm edit 1 "Updated task description"

# Clear all completed tasks
./tm clear
```

### Advanced Commands

```bash
# Search tasks by keyword
./tm search "documentation"

# Show task statistics
./tm stats

# Show all unique tags
./tm tags

# Export tasks to CSV
./tm export output.csv

# Set a reminder for a task
./tm notify 1 "2025-12-01 10:00"

# Start background task monitor
./tm watch

# Process tasks concurrently
./tm process
```

### User Management

```bash
# Create a new user
./tm user create <username> <role>

# List all users
./tm user list

# Switch to a different user
./tm user switch <username>
```

## GUI Mode 🖥️

The GUI provides an intuitive interface for managing tasks:

1. **Add Tasks**: Type in the text field and press Enter or click "Add Task"
2. **Mark as Done**: Select a task and click "Mark as Done"
3. **Edit Tasks**: Select a task and click "Edit Selected" or double-click the task
4. **Clear Completed**: Click "Clear Done Tasks" to remove all completed tasks

The GUI automatically saves changes and displays tasks with color-coded status.

## Project Structure 📂

```
fast/
├── src/main/java/com/utkarsh/
│   ├── App.java                    # Main CLI entry point
│   ├── TaskManager.java            # Core task management logic
│   ├── TaskMasterGUI.java          # Swing-based GUI
│   ├── Task.java                   # Task model
│   ├── Status.java                 # Task status enum
│   ├── *Command.java               # CLI command implementations
│   ├── command/                    # Command base classes
│   ├── exception/                  # Custom exceptions
│   ├── manager/                    # User management
│   ├── model/                      # User models (Admin, Power, Guest)
│   ├── thread/                     # Background threads (auto-save, reminders)
│   └── util/                       # Utility classes
├── pom.xml                         # Maven configuration
├── tm                             # CLI launcher script
└── tm-gui                         # GUI launcher script
```

## Technology Stack 💻

- **Java 17+**: Core language with modern features
- **Maven**: Dependency management and build automation
- **Picocli**: Command-line interface framework
- **Jackson**: JSON serialization/deserialization
- **AsciiTable**: Beautiful CLI table rendering
- **Java Swing**: GUI framework
- **JUnit 5**: Testing framework

## Data Storage 💾

Tasks are stored in JSON format at:
```
~/.taskmaster/tasks.json
```

This allows for:
- Persistent storage across sessions
- Easy backup and restoration
- Human-readable format for debugging

## Optional: GraalVM Native Image 🔥

For blazing-fast startup times, you can compile to a native binary:

```bash
# Requires GraalVM installed
mvn clean package -Pnative

# This creates a native executable: target/tm
./target/tm --help
```

## Troubleshooting 🔍

### Issue: "JAR not found" error
**Solution**: Run `mvn clean package` first to build the project.

### Issue: Java version mismatch
**Solution**: Ensure you have Java 17 or higher installed:
```bash
java -version
```

### Issue: GUI not displaying
**Solution**: Ensure you have a display server running (X11 or Wayland on Linux).

### Issue: Tasks not persisting
**Solution**: Check permissions for `~/.taskmaster/` directory.

## Development 👨‍💻

### Running Tests
```bash
mvn test
```

### Cleaning Build Artifacts
```bash
mvn clean
```

### Viewing Dependency Tree
```bash
mvn dependency:tree
```

## Contributing 🤝

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License 📄

See [LICENSE](LICENSE) file for details.

## Examples 📚

### Quick Start Workflow
```bash
# Build the project
mvn clean package

# Add some tasks
./tm add "Write documentation" --tags docs --priority high
./tm add "Fix bug #123" --tags bug,urgent --priority high
./tm add "Review code" --tags review --priority medium

# List tasks
./tm list

# Mark a task as done
./tm done 1

# View statistics
./tm stats

# Export to CSV
./tm export my-tasks.csv
```

### Working with Tags
```bash
# Add tasks with tags
./tm add "Backend API" --tags backend,api
./tm add "Frontend UI" --tags frontend,ui
./tm add "Database migration" --tags backend,database

# Filter by tag
./tm list --tag backend

# View all tags
./tm tags
```

### Priority Management
```bash
# Add tasks with different priorities
./tm add "Critical bug fix" --priority high
./tm add "Regular task" --priority medium
./tm add "Nice to have feature" --priority low

# Sort by priority
./tm list --sort priority
```

## Tips & Best Practices 💡

1. **Use Tags**: Organize tasks by project, category, or context
2. **Set Priorities**: Focus on what matters most
3. **Regular Cleanup**: Use `./tm clear` to remove completed tasks
4. **Export Regularly**: Back up your tasks with `./tm export`
5. **Use GUI for Bulk Operations**: The GUI is great for quick reviews and updates
6. **Check Stats**: Use `./tm stats` to track your productivity

## Future Enhancements 🚧

Potential features for future versions:
- Task due dates and reminders
- Recurring tasks
- Task dependencies
- Collaboration features
- Mobile app integration
- Cloud sync
- Dark mode for GUI
- Custom themes

---

**Made with ☕ and Java**

