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
});
