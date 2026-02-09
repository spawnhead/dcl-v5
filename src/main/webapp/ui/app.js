import { renderMargin } from "./screens/margin.js";
import { renderCalcState } from "./screens/calc-state.js";

const viewEl = document.getElementById("view");
const navMargin = document.getElementById("nav-margin");
const navCalc = document.getElementById("nav-calc");

function setActive(path) {
  if (navMargin) navMargin.classList.toggle("active", path === "margin");
  if (navCalc) navCalc.classList.toggle("active", path === "calc-state");
}

function getRoute() {
  const hash = window.location.hash || "#/margin";
  const m = hash.match(/^#\/([^?]+)/);
  return m ? m[1] : "margin";
}

async function render() {
  const route = getRoute();
  setActive(route);
  if (!viewEl) return;

  viewEl.innerHTML = "";
  if (route === "calc-state") {
    renderCalcState(viewEl);
  } else {
    renderMargin(viewEl);
  }
}

window.addEventListener("hashchange", render);
render();
