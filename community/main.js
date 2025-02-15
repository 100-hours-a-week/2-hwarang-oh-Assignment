async function loadComponent(targetId, file) {
  const target = document.getElementById(targetId);
  const response = await fetch(file);
  const content = await response.text();
  target.innerHTML = content;
}

document.addEventListener("DOMContentLoaded", () => {
  loadComponent("header", "components/header.html");
});
