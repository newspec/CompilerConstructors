echo "Start building..."

cd FLanguage

echo "Generating Parser with bison..."
bison -L java -o src/main/java/parser/FParser.java src/main/java/parser/F.y

if [ $? -ne 0 ]; then
    echo "Error generating Parser with bison"
    exit 1
fi

echo "Generatin lexer with jflex..."
jflex src/main/java/lexer/F.flex

if [ $? -ne 0 ]; then
    echo "Error generating lexer with jflex"
    exit 1
fi

echo "Compiling Java files..."
javac -d out src/main/java/parser/FParser.java src/main/java/lexer/FScanner.java src/main/java/Main.java src/main/java/parser/TokenValue.java

if [ $? -ne 0 ]; then
    echo "Error compiling Java files"
    exit 1
fi

echo "Building completed successfully!"
echo "To run tests, use: java -cp out Main tests/test1.f"
