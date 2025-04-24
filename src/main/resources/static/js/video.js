document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById("videoModal");
    const videoPlayer = document.getElementById("videoPlayer");
    const playButton = document.querySelector(".content__icon");
    const closeButton = document.querySelector(".close");

    

    if (playButton && modal && videoPlayer) {
        playButton.addEventListener("click", function () {
            modal.style.display = "flex"; // Mostrar el modal con fondo oscuro
            videoPlayer.play(); // Reproducir el video
        });

        closeButton.addEventListener("click", function () {
            modal.style.display = "none"; // Ocultar el modal
            videoPlayer.pause(); // Pausar el video
            videoPlayer.currentTime = 0; // Reiniciar el video
        });

        modal.addEventListener("click", function (event) {
            if (event.target === modal) {
                modal.style.display = "none";
                videoPlayer.pause();
                videoPlayer.currentTime = 0;
            }
        });
    } else {
        console.error("Uno o más elementos no se encontraron en el DOM.");
    }

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

});
