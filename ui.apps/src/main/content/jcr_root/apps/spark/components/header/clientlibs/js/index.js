// === Mobile navigation toggle ===
const btnNavEl = document.querySelector(".btn-mobile-nav");
const headerEl = document.querySelector(".header");

btnNavEl.addEventListener("click", function () {
  headerEl.classList.toggle("nav-open");
});

// === Sticky header on scroll ===
window.addEventListener("scroll", function () {
  if (window.scrollY >= 720) {
    document.body.classList.add("sticky");
  } else {
    document.body.classList.remove("sticky");
  }
});
