export function setStatus(el, text, isError) {
  if (!el) return;
  el.textContent = text || "";
  el.style.color = isError ? "#721c24" : "#666";
}

export function buildOption(value, label) {
  const opt = document.createElement("option");
  opt.value = value == null ? "" : String(value);
  opt.textContent = label == null ? String(value || "") : String(label);
  return opt;
}
