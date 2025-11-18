#!/bin/bash
# TaskMaster Installation Script

set -e

echo "🚀 TaskMaster Installation Script"
echo "=================================="
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed."
    echo "   Please install Java 17 or higher and try again."
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Error: Java 17 or higher is required."
    echo "   Current version: $JAVA_VERSION"
    exit 1
fi

echo "✓ Java version check passed"

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Error: Maven is not installed."
    echo "   Please install Maven and try again."
    exit 1
fi

echo "✓ Maven check passed"
echo ""

# Build the project
echo "📦 Building TaskMaster..."
mvn clean package -q

if [ ! -f "target/fast-1.0-SNAPSHOT.jar" ]; then
    echo "❌ Error: Build failed. JAR file not found."
    exit 1
fi

echo "✓ Build successful"
echo ""

# Make scripts executable
chmod +x tm tm-gui

echo "✓ Made launcher scripts executable"
echo ""

# Optional: Add to PATH
echo "📍 Installation Options:"
echo ""
echo "Option 1: Add to your PATH (recommended)"
echo "  Add this line to your ~/.bashrc or ~/.zshrc:"
echo "  export PATH=\"$(pwd):\$PATH\""
echo ""
echo "  Then run: source ~/.bashrc (or source ~/.zshrc)"
echo ""
echo "Option 2: Create a symbolic link"
echo "  sudo ln -s $(pwd)/tm /usr/local/bin/tm"
echo "  sudo ln -s $(pwd)/tm-gui /usr/local/bin/tm-gui"
echo ""
echo "Option 3: Run from this directory"
echo "  Use: ./tm [command]"
echo "       ./tm-gui"
echo ""
echo "✅ Installation complete!"
echo ""
echo "📚 Quick start:"
echo "  ./tm add \"My first task\""
echo "  ./tm list"
echo "  ./tm --help"
echo "  ./tm-gui (for GUI mode)"
echo ""
echo "📖 For full documentation, see README.md"

