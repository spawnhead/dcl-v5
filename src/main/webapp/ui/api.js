export async function apiGetJson(url) {
  const resp = await fetch(url, { credentials: "same-origin" });
  const text = await resp.text();
  let json = null;
  try {
    json = text ? JSON.parse(text) : null;
  } catch (e) {
    // ignore
  }
  if (!resp.ok) {
    const msg = (json && json.error) ? json.error : ("HTTP " + resp.status);
    // #region agent log
    fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({runId:'pre-fix',hypothesisId:'H3',location:'ui/api.js:15',message:'apiGetJson non-ok',data:{url,status:resp.status,msg},timestamp:Date.now()})}).catch(()=>{});
    // #endregion
    const legacyUrl = legacyLookupFallback(url, msg);
    if (legacyUrl) {
      // #region agent log
      fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({runId:'pre-fix',hypothesisId:'H1',location:'ui/api.js:19',message:'apiGetJson fallback to legacy',data:{url,legacyUrl,msg},timestamp:Date.now()})}).catch(()=>{});
      // #endregion
      return fetchLegacyList(legacyUrl);
    }
    const err = new Error(msg);
    err.status = resp.status;
    err.data = json;
    throw err;
  }
  return json || {};
}

export async function apiPostForm(url, data) {
  const body = new URLSearchParams(data || {}).toString();
  const resp = await fetch(url, {
    method: "POST",
    credentials: "same-origin",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    body
  });
  if (!resp.ok) {
    throw new Error("HTTP " + resp.status);
  }
  return resp.text();
}

export function buildQuery(params) {
  const usp = new URLSearchParams();
  Object.keys(params || {}).forEach((k) => {
    const v = params[k];
    if (v !== undefined && v !== null && v !== "") {
      usp.append(k, String(v));
    }
  });
  const qs = usp.toString();
  return qs ? "?" + qs : "";
}

function legacyLookupFallback(url, message) {
  if (!message || message.toLowerCase().indexOf("result set is already closed") === -1) {
    return null;
  }
  if (!url || url.indexOf("/api/lookups/") === -1) return null;
  const includeAll = /includeAll=true/i.test(url);
  if (url.indexOf("/api/lookups/users") !== -1) {
    return "/UsersListAction.do?have_all=" + (includeAll ? "true" : "false");
  }
  if (url.indexOf("/api/lookups/departments") !== -1) {
    return "/DepartmentsListAction.do?have_all=" + (includeAll ? "true" : "false");
  }
  if (url.indexOf("/api/lookups/contractors") !== -1) {
    return "/ContractorsListAction.do?have_all=" + (includeAll ? "true" : "false");
  }
  return null;
}

/** Parse legacy *ListAction HTML to [{ id, label }]. Same format as /api/lookups. */
export function parseLegacyListHtml(html) {
  const doc = new DOMParser().parseFromString(html, "text/html");
  const rows = Array.from(doc.querySelectorAll("tr"));
  const out = [];
  let rowsWithOnclick = 0;
  let rowsWithItemSelected = 0;
  let singleQuoteMatchRows = 0;
  let doubleQuoteMatchRows = 0;
  rows.forEach((tr) => {
    const onclick = tr.getAttribute("onclick") || "";
    if (onclick) rowsWithOnclick++;
    if (onclick.indexOf("ItemSelected") === -1) return;
    rowsWithItemSelected++;
    const re = /'((?:\\'|[^'])*)'/g;
    let m;
    const parts = [];
    while ((m = re.exec(onclick)) !== null) parts.push(m[1].replace(/\\'/g, "'"));
    if (parts.length > 0) singleQuoteMatchRows++;
    if (parts.length === 0) {
      const re2 = /"((?:\\"|[^"])*)"/g;
      while ((m = re2.exec(onclick)) !== null) parts.push(m[1].replace(/\\"/g, "\""));
      if (parts.length > 0) doubleQuoteMatchRows++;
    }
    const td = tr.querySelector("td");
    const label = td ? td.textContent.trim() : "";
    const value = parts[0] || label;
    const lbl = parts[1] || label || value;
    if (value || lbl) out.push({ id: String(value), label: String(lbl) });
  });
  if (out.length === 0 && html && html.indexOf("ItemSelected") !== -1) {
    const re = /ItemSelected\(([^)]*)\)/g;
    let m;
    while ((m = re.exec(html)) !== null) {
      const args = m[1] || "";
      const parts = [];
      const re1 = /'((?:\\'|[^'])*)'/g;
      let q;
      while ((q = re1.exec(args)) !== null) parts.push(q[1].replace(/\\'/g, "'"));
      if (parts.length === 0) {
        const re2 = /"((?:\\"|[^"])*)"/g;
        while ((q = re2.exec(args)) !== null) parts.push(q[1].replace(/\\"/g, "\""));
      }
      const value = parts[0];
      const label = parts[1] || value;
      if (value || label) out.push({ id: String(value || ""), label: String(label || "") });
    }
    // #region agent log
    fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({runId:'pre-fix',hypothesisId:'H4',location:'ui/api.js:102',message:'parseLegacyListHtml fallback used',data:{fallbackCount:out.length},timestamp:Date.now()})}).catch(()=>{});
    // #endregion
  }
  // #region agent log
  fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({runId:'pre-fix',hypothesisId:'H4',location:'ui/api.js:67',message:'parseLegacyListHtml stats',data:{rowCount:rows.length,rowsWithOnclick,rowsWithItemSelected,singleQuoteMatchRows,doubleQuoteMatchRows,outCount:out.length},timestamp:Date.now()})}).catch(()=>{});
  // #endregion
  return out;
}

/** Fetch legacy list HTML and return { data: [...] } for compatibility with loadSelect. */
export async function fetchLegacyList(url) {
  // #region agent log
  fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({runId:'pre-fix',hypothesisId:'H1',location:'ui/api.js:71',message:'fetchLegacyList start',data:{url},timestamp:Date.now()})}).catch(()=>{});
  // #endregion
  const resp = await fetch(url, { credentials: "same-origin" });
  const text = await resp.text();
  const contentType = resp.headers.get("content-type") || "";
  const hasItemSelected = text.indexOf("ItemSelected") !== -1;
  const itemSelectedCount = (text.match(/ItemSelected\s*\(/g) || []).length;
  const itemSelectedEscapedCount = (text.match(/ItemSelected\\\s*\\\(/g) || []).length;
  const hasDocumentWrite = text.indexOf("document.write") !== -1 || text.indexOf("document.writeln") !== -1;
  const tableTagCount = (text.match(/<table\b/gi) || []).length;
  const hasLoginForm = (text.indexOf("Логин") !== -1 && text.indexOf("Пароль") !== -1) || text.indexOf("name=\"login\"") !== -1;
  // #region agent log
  fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({runId:'pre-fix',hypothesisId:'H1',location:'ui/api.js:78',message:'fetchLegacyList response',data:{url,status:resp.status,respUrl:resp.url,contentType,textLength:text.length,hasItemSelected,itemSelectedCount,itemSelectedEscapedCount,hasDocumentWrite,tableTagCount,hasLoginForm},timestamp:Date.now()})}).catch(()=>{});
  // #endregion
  const data = parseLegacyListHtml(text);
  // #region agent log
  fetch('http://127.0.0.1:7242/ingest/c94ca453-3d7b-4b81-b417-94d1a1bdbd34',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({runId:'pre-fix',hypothesisId:'H1',location:'ui/api.js:75',message:'fetchLegacyList parsed',data:{url,count:data.length},timestamp:Date.now()})}).catch(()=>{});
  // #endregion
  return { data };
}
