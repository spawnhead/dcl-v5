# MCP Ant Design Components — локальная установка

Этот каталог содержит настроенный MCP‑сервер [mcp-antd-components](https://github.com/hannesj/mcp-antd-components) для доступа к документации Ant Design в Cursor.

## Первоначальная установка (один раз)

```bash
cd ops/mcp-antd

# 1. Установить зависимости
npm install

# 2. Клонировать ant-design (временный клон для извлечения)
git clone --depth 1 https://github.com/ant-design/ant-design.git

# 3. Извлечь документацию компонентов
node node_modules/mcp-antd-components/scripts/extract-docs.mjs ./ant-design

# 4. (Опционально) Удалить ant-design после извлечения
rm -rf ant-design
```

## Проверка

```bash
node node_modules/mcp-antd-components/index.mjs --help
# или
cd node_modules/mcp-antd-components && npm test
```

## Конфигурация Cursor

В `~/.cursor/mcp.json` или `.cursor/mcp.json`:

```json
{
  "mcpServers": {
    "Ant Design Components": {
      "command": "node",
      "args": ["<ABSOLUTE_PATH>/ops/mcp-antd/node_modules/mcp-antd-components/index.mjs"]
    }
  }
}
```

Замените `<ABSOLUTE_PATH>` на полный путь к проекту (например `C:/Users/IVANIN/dcl-v5`).

## Инструменты MCP

- `list-components` — список компонентов Ant Design
- `get-component-docs` — документация компонента
- `get-component-props` — API и props
- `list-component-examples` — примеры
- `get-component-example` — код примера
- `search-components` — поиск по имени
