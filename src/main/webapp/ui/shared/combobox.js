export function createCombobox(containerEl, options) {
  const opts = options || {};
  const minChars = typeof opts.minChars === "number" ? opts.minChars : 2;
  const debounceMs = typeof opts.debounceMs === "number" ? opts.debounceMs : 250;
  const limit = typeof opts.limit === "number" ? opts.limit : 20;
  const recentKey = opts.recentKey ? String(opts.recentKey) : null;
  const fetchOptions = opts.fetchOptions;
  const onSelect = typeof opts.onSelect === "function" ? opts.onSelect : null;

  const wrap = document.createElement("div");
  wrap.className = "ui-combobox";

  const input = document.createElement("input");
  input.type = "text";
  input.className = "ui-combobox-input";
  if (opts.placeholder) input.placeholder = opts.placeholder;
  if (opts.inputId) input.id = opts.inputId;

  const loader = document.createElement("span");
  loader.className = "ui-combobox-loader";

  const dropdown = document.createElement("div");
  dropdown.className = "ui-combobox-dropdown";

  const list = document.createElement("div");
  list.className = "ui-combobox-list";

  const empty = document.createElement("div");
  empty.className = "ui-combobox-empty";
  empty.textContent = "Ничего не найдено";

  const error = document.createElement("div");
  error.className = "ui-combobox-error";
  error.textContent = "Ошибка загрузки";

  dropdown.append(list, empty, error);
  wrap.append(input, loader, dropdown);
  containerEl.appendChild(wrap);

  let items = [];
  let activeIndex = -1;
  let selected = null;
  let requestId = 0;
  let debounceTimer = null;
  let lastQuery = "";

  function setLoading(isLoading) {
    if (isLoading) wrap.classList.add("is-loading");
    else wrap.classList.remove("is-loading");
  }

  function openDropdown() {
    dropdown.classList.add("is-open");
  }

  function closeDropdown() {
    dropdown.classList.remove("is-open");
    activeIndex = -1;
    updateActive();
  }

  function updateActive() {
    const nodes = list.querySelectorAll(".ui-combobox-item");
    nodes.forEach((node, idx) => {
      if (idx === activeIndex) node.classList.add("is-active");
      else node.classList.remove("is-active");
    });
  }

  function renderItems(data, showEmpty, showError) {
    list.innerHTML = "";
    items = data || [];
    activeIndex = items.length > 0 ? 0 : -1;
    items.forEach((it, idx) => {
      const row = document.createElement("div");
      row.className = "ui-combobox-item";
      row.textContent = it.label || it.id || "";
      row.dataset.index = String(idx);
      row.addEventListener("mousedown", (e) => {
        e.preventDefault();
        selectItem(items[idx] || null);
      });
      list.appendChild(row);
    });
    empty.style.display = showEmpty ? "block" : "none";
    error.style.display = showError ? "block" : "none";
    if (items.length > 0 || showEmpty || showError) openDropdown();
    else closeDropdown();
    updateActive();
  }

  function loadRecent() {
    if (!recentKey) return [];
    try {
      const raw = localStorage.getItem("recent:" + recentKey);
      const arr = raw ? JSON.parse(raw) : [];
      return Array.isArray(arr) ? arr.filter((it) => it && it.id) : [];
    } catch (e) {
      return [];
    }
  }

  function saveRecent(item) {
    if (!recentKey || !item || !item.id) return;
    const current = loadRecent().filter((it) => it.id !== item.id);
    current.unshift({ id: item.id, label: item.label });
    while (current.length > 5) current.pop();
    try {
      localStorage.setItem("recent:" + recentKey, JSON.stringify(current));
    } catch (e) {
    }
  }

  function selectItem(item) {
    selected = item;
    if (item) {
      input.value = item.label || item.id || "";
      saveRecent(item);
    } else {
      input.value = "";
    }
    closeDropdown();
    if (onSelect) onSelect(item || null);
  }

  function clearSelectionIfEdited(value) {
    if (selected && value !== selected.label) {
      selected = null;
      if (onSelect) onSelect(null);
    }
  }

  function showRecentIfAny() {
    const recent = loadRecent();
    if (recent.length > 0) {
      renderItems(recent, false, false);
    } else {
      closeDropdown();
    }
  }

  async function doSearch(query) {
    if (!fetchOptions) return;
    const currentId = ++requestId;
    setLoading(true);
    try {
      const data = await fetchOptions(query, limit);
      if (currentId !== requestId) return;
      const out = Array.isArray(data) ? data : [];
      renderItems(out, out.length === 0, false);
    } catch (e) {
      if (currentId !== requestId) return;
      renderItems([], false, true);
    } finally {
      if (currentId === requestId) setLoading(false);
    }
  }

  function scheduleSearch(value) {
    if (debounceTimer) clearTimeout(debounceTimer);
    debounceTimer = setTimeout(() => {
      debounceTimer = null;
      doSearch(value);
    }, debounceMs);
  }

  input.addEventListener("input", () => {
    const value = input.value.trim();
    clearSelectionIfEdited(value);
    lastQuery = value;
    if (!value || value.length < minChars) {
      setLoading(false);
      showRecentIfAny();
      return;
    }
    scheduleSearch(value);
  });

  input.addEventListener("focus", () => {
    const value = input.value.trim();
    if (!value || value.length < minChars) {
      showRecentIfAny();
    } else if (items.length > 0) {
      openDropdown();
    }
  });

  input.addEventListener("keydown", (e) => {
    if (!dropdown.classList.contains("is-open")) return;
    if (e.key === "ArrowDown") {
      e.preventDefault();
      if (items.length === 0) return;
      activeIndex = Math.min(items.length - 1, activeIndex + 1);
      updateActive();
      return;
    }
    if (e.key === "ArrowUp") {
      e.preventDefault();
      if (items.length === 0) return;
      activeIndex = Math.max(0, activeIndex - 1);
      updateActive();
      return;
    }
    if (e.key === "Enter") {
      if (activeIndex >= 0 && items[activeIndex]) {
        e.preventDefault();
        selectItem(items[activeIndex]);
      }
      return;
    }
    if (e.key === "Escape") {
      closeDropdown();
    }
  });

  document.addEventListener("mousedown", (e) => {
    if (!wrap.contains(e.target)) closeDropdown();
  });

  if (opts.initialValue) {
    selectItem(opts.initialValue);
  }

  return {
    input,
    getValue: () => (selected ? { id: selected.id, label: selected.label } : null),
    setValue: (val) => selectItem(val || null),
    clear: () => selectItem(null),
    search: (q) => {
      const value = (q || "").trim();
      input.value = value;
      clearSelectionIfEdited(value);
      if (value.length >= minChars) doSearch(value);
    }
  };
}
