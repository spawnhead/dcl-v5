/**
 * Shared loading UX - button loader and grid overlay.
 * Use in all /ui/ screens (margin, calc-state, etc).
 */

const LOADING_TEXT = "Формирование...";

/**
 * Set button loading state: disable + spinner + text change.
 * @param {HTMLButtonElement} btn - button element
 * @param {boolean} loading - true to show loading state
 * @param {string} originalText - text to restore when loading=false
 */
export function setButtonLoading(btn, loading, originalText = "Сформировать") {
  if (!btn) return;
  btn.disabled = loading;
  if (loading) {
    btn.dataset.originalText = btn.dataset.originalText || btn.textContent;
    btn.innerHTML = '<span class="btn-spinner"></span> ' + LOADING_TEXT;
    btn.classList.add("btn-loading");
  } else {
    btn.textContent = btn.dataset.originalText || originalText;
    btn.classList.remove("btn-loading");
    delete btn.dataset.originalText;
  }
}

/**
 * Show loading overlay in AG Grid.
 * Feature detection: use showLoadingOverlay if available, else manual overlay.
 * @param {Object} gridApi - AG Grid API (from onGridReady)
 */
export function showGridLoading(gridApi) {
  if (!gridApi) return;
  if (typeof gridApi.showLoadingOverlay === "function") {
    gridApi.showLoadingOverlay();
    return;
  }
  const el = gridApi.getGridElement && gridApi.getGridElement();
  if (el) {
    let overlay = el.querySelector(".ui-loading-overlay");
    if (!overlay) {
      overlay = document.createElement("div");
      overlay.className = "ui-loading-overlay";
      overlay.innerHTML = '<div class="ui-loading-spinner"></div><div class="ui-loading-text">' + LOADING_TEXT + "</div>";
      el.style.position = "relative";
      el.appendChild(overlay);
    }
    overlay.classList.add("active");
  }
}

/**
 * Hide loading overlay, show no-rows if empty.
 * @param {Object} gridApi - AG Grid API
 * @param {Array} rows - current rows (for no-rows overlay)
 */
export function hideGridLoading(gridApi, rows) {
  if (!gridApi) return;
  if (typeof gridApi.hideOverlay === "function") {
    gridApi.hideOverlay();
  }
  if (typeof gridApi.showNoRowsOverlay === "function" && (!rows || rows.length === 0)) {
    gridApi.showNoRowsOverlay();
  }
  const el = gridApi.getGridElement && gridApi.getGridElement();
  if (el) {
    const overlay = el.querySelector(".ui-loading-overlay");
    if (overlay) overlay.classList.remove("active");
  }
}
