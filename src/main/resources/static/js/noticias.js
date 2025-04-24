/* Menú desplegable del perfil */
const subMenu = document.getElementById("SubMenu");
const profileImage = document.querySelector(".nav__img");

if (subMenu && profileImage) {
    profileImage.addEventListener("click", function () {
        subMenu.classList.toggle("open__menu");
    });

    // Opcional: cerrar menú al hacer clic fuera
    document.addEventListener("click", function (e) {
        if (!subMenu.contains(e.target) && !profileImage.contains(e.target)) {
            subMenu.classList.remove("open__menu");
        }
    });

}