
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

clear_screen() {
    clear
}
get_test_files() {
    find FLanguage/tests -name "*.f" -type f | sort
}
run_test() {
    local test_file="$1"
    local test_name=$(basename "$test_file")
    
    echo -e "${BLUE}Running test: ${test_name}${NC}"
    echo -e "${BLUE}File: ${test_file}${NC}"
    echo "----------------------------------------"
    
    local output
    local exit_code
    
    cd FLanguage

    local relative_path="${test_file#FLanguage/}"
    output=$(java -cp out Main "$relative_path" 2>&1)
    exit_code=$?
    cd ..
    
    if [[ "$output" == Scanner\ error:* ]]; then
        echo -e "${RED}✗ Test failed (Scanner error)${NC}"
        echo -e "${RED}$output${NC}"
    elif [ $exit_code -eq 0 ]; then
        echo -e "${GREEN}✓ Test passed${NC}"
        echo "$output"
    else
        echo -e "${RED}✗ Test failed (exit code: $exit_code)${NC}"
        echo "$output"
    fi
    
    echo "----------------------------------------"
}

show_help() {
    echo -e "${YELLOW}Navigation:${NC}"
    echo -e "  ${GREEN}↓${NC} (Down arrow) - Next test"
    echo -e "  ${GREEN}↑${NC} (Up arrow)   - Previous test"
    echo -e "  ${GREEN}q${NC}              - Quit"
    echo -e "  ${GREEN}h${NC}              - Show this help"
    echo ""
}

main() {
    if [ ! -f "FLanguage/src/main/java/Main.java" ]; then
        echo -e "${RED}Error: Project not built. Run ./build.sh first.${NC}"
        exit 1
    fi
    
    test_files=($(get_test_files))
    total_tests=${#test_files[@]}
    
    if [ $total_tests -eq 0 ]; then
        echo -e "${RED}No test files found!${NC}"
        exit 1
    fi
    
    current_index=0
    
    clear_screen
    echo -e "${BLUE}F Language Test Runner${NC}"
    echo -e "Total tests: ${total_tests}"
    echo ""
    show_help
    
    
    run_test "${test_files[$current_index]}"
    
    echo ""
    echo -e "${YELLOW}Press ↓ for next test, ↑ for previous test, q to quit, h for help${NC}"
    

    while true; do
        read -rsn1 key
        
        case $key in
            $'\x1b')  
                read -rsn2 key
                case $key in
                    '[A')
                        if [ $current_index -gt 0 ]; then
                            ((current_index--))
                            clear_screen
                            run_test "${test_files[$current_index]}"
                            echo ""
                            echo -e "${YELLOW}Press ↓ for next test, ↑ for previous test, q to quit, h for help${NC}"
                        else
                            echo -e "${YELLOW}Already at first test${NC}"
                        fi
                        ;;
                    '[B')
                        if [ $current_index -lt $((total_tests - 1)) ]; then
                            ((current_index++))
                            clear_screen
                            run_test "${test_files[$current_index]}"
                            echo ""
                            echo -e "${YELLOW}Press ↓ for next test, ↑ for previous test, q to quit, h for help${NC}"
                        else
                            echo -e "${YELLOW}Already at last test${NC}"
                        fi
                        ;;
                esac
                ;;
            'q'|'Q')
                clear_screen
                echo -e "${GREEN}Test session ended.${NC}"
                exit 0
                ;;
            'h'|'H')
                clear_screen
                show_help
                echo -e "${YELLOW}Press any key to continue...${NC}"
                read -rsn1
                clear_screen
                run_test "${test_files[$current_index]}"
                echo ""
                echo -e "${YELLOW}Press ↓ for next test, ↑ for previous test, q to quit, h for help${NC}"
                ;;
        esac
    done
}
main
