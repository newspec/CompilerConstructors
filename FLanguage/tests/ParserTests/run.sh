#!/usr/bin/env bash

set -euo pipefail

# Resolve workspace root and classes path
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/../.." && pwd)"   # points to .../FLanguage
CLASSES_DIR="$ROOT_DIR/target/classes"

if [ ! -d "$CLASSES_DIR" ]; then
  echo "Classes not found at: $CLASSES_DIR" >&2
  echo "Build/compile the project so that target/classes exists." >&2
  exit 1
fi

EXIT_CODE=0

for f in "$SCRIPT_DIR"/*.f; do
  [ -e "$f" ] || continue
  base="${f%.f}"
  expected="$base.ast"
  actual="$base.out"

  if [ ! -f "$expected" ]; then
    echo "Missing expected AST file: $expected" >&2
    EXIT_CODE=1
    continue
  fi

  java -cp "$CLASSES_DIR" Main "$f" > "$actual"

  # Normalize by removing trailing blank lines in both files before diffing
  expected_norm="$base.expected.norm"
  actual_norm="$base.actual.norm"
  awk '{ lines[NR]=$0 } END { n=NR; while(n>0 && lines[n] ~ /^[[:space:]]*$/) n--; for(i=1;i<=n;i++) print lines[i] }' "$expected" > "$expected_norm"
  awk '{ lines[NR]=$0 } END { n=NR; while(n>0 && lines[n] ~ /^[[:space:]]*$/) n--; for(i=1;i<=n;i++) print lines[i] }' "$actual" > "$actual_norm"

  if diff -u "$expected_norm" "$actual_norm" > "$base.diff"; then
    echo "[OK] $(basename "$f")"
    rm -f "$base.diff" "$actual" "$expected_norm" "$actual_norm"
  else
    echo "[FAIL] $(basename "$f") — see $(basename "$base.diff")"
    EXIT_CODE=1
  fi
done

exit $EXIT_CODE


