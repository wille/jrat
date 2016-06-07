#!/bin/bash

echo "Total commits:"
echo "   "$(git rev-list HEAD --count)
echo "Commits by member:"
git shortlog -s
echo "Total lines:"
echo "  "$(git ls-files | xargs cat | wc -l)