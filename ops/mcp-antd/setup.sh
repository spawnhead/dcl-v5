#!/usr/bin/env bash
# Setup MCP Ant Design Components for Cursor
# Run from project root: bash ops/mcp-antd/setup.sh

set -e
cd "$(dirname "$0")"

echo "=== MCP Ant Design Components Setup ==="

# 1. Install
npm install

# 2. Clone ant-design if not present
if [ ! -d "ant-design" ]; then
  echo "Cloning ant-design (shallow)..."
  git clone --depth 1 https://github.com/ant-design/ant-design.git
else
  echo "ant-design already cloned, skip."
fi

# 3. Extract docs
if [ ! -f "node_modules/mcp-antd-components/data/components-index.json" ]; then
  echo "Extracting component documentation..."
  node node_modules/mcp-antd-components/scripts/extract-docs.mjs ./ant-design
  echo "Extraction done."
else
  echo "Data already extracted, skip."
fi

# 4. Optional: remove ant-design to save space
# rm -rf ant-design

echo "=== Done. Add to Cursor mcp.json ==="
echo ""
echo '  "Ant Design Components": {'
echo '    "command": "node",'
echo "    \"args\": [\"$(pwd -W 2>/dev/null || pwd)/node_modules/mcp-antd-components/index.mjs\"]"
echo '  }'
echo ""
