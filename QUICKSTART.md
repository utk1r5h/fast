# TaskMaster Quick Reference 🚀

## Installation & Setup

```bash
# One-time setup
./install.sh

# Or just run directly
./tm --help
```

## Essential Commands

| Command | Description | Example |
|---------|-------------|---------|
| `./tm add "task"` | Add a new task | `./tm add "Write docs"` |
| `./tm list` | List pending tasks | `./tm list` |
| `./tm list --all` | List all tasks | `./tm list --all` |
| `./tm done <id>` | Mark task complete | `./tm done 1` |
| `./tm edit <id> "new"` | Edit task | `./tm edit 1 "Updated text"` |
| `./tm clear` | Clear completed | `./tm clear` |
| `./tm stats` | Show statistics | `./tm stats` |
| `./tm search "word"` | Search tasks | `./tm search "bug"` |
| `./tm tags` | Show all tags | `./tm tags` |
| `./tm export file.csv` | Export to CSV | `./tm export tasks.csv` |

## Adding Tasks with Tags & Priority

```bash
# With tags
./tm add "Fix login bug" --tags bug,urgent

# With priority
./tm add "Review PR" --priority high

# With both
./tm add "Deploy to prod" --tags deployment,backend --priority high
```

## Filtering & Sorting

```bash
# Filter by tag
./tm list --tag urgent

# Sort by priority
./tm list --sort priority

# Show all (including completed)
./tm list --all
```

## Priority Levels

- `high` - Critical tasks
- `medium` - Normal tasks (default)
- `low` - Nice-to-have tasks

## GUI Mode

```bash
# Launch graphical interface
./tm-gui
```

**GUI Features:**
- ✏️ Double-click to edit tasks
- ✅ Select and click "Mark as Done"
- ➕ Press Enter in text field to add
- 🗑️ Click "Clear Done Tasks" to clean up

## File Locations

- **Data**: `~/.taskmaster/tasks.json`
- **Config**: Same directory as data
- **Executable**: `target/fast-1.0-SNAPSHOT.jar`

## Workflow Example

```bash
# Morning routine
./tm add "Check emails" --priority high
./tm add "Stand-up meeting" --tags meeting
./tm add "Code review" --tags work
./tm list --sort priority

# During the day
./tm done 1
./tm done 2

# End of day
./tm stats
./tm clear
```

## Tips

1. **Tag Everything**: Use tags for context switching
2. **Prioritize**: Set high priority for urgent items
3. **Clean Regularly**: Use `clear` to remove done tasks
4. **Check Stats**: Track your productivity daily
5. **Use GUI**: Great for visual task management

## Keyboard Shortcuts (CLI)

- `Ctrl+C` - Cancel/Exit current command
- `Tab` - Auto-complete (if shell completion enabled)
- `↑/↓` - Command history

## Troubleshooting

```bash
# Rebuild if needed
mvn clean package

# Check if running
ps aux | grep fast

# View data file
cat ~/.taskmaster/tasks.json

# Backup tasks
cp ~/.taskmaster/tasks.json ~/tasks-backup.json
```

## Getting Help

```bash
# General help
./tm --help

# Command-specific help
./tm add --help
./tm list --help
```

---

**Pro Tip**: Add `alias tm='~/Documents/fast/tm'` to your `.bashrc` for global access!

