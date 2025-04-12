fields = { 
    email: {
        regex: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/,
        errorMessage: "El correo solo puede contener letras, números, puntos, guiones y guion bajo."
    },
    password: {
        regex: /^.{4,15}$/,
        errorMessage: "La contraseña tiene que ser de 4 a 15 dígitos."
    }
};

Object.keys(fields).forEach(fieldId => {
    const input = document.getElementById(fieldId);
    const inputBox = input.closest(".input-box");
    const checkIcon = inputBox.querySelector(".ri-check-line");
    const errorIcon = inputBox.querySelector(".ri-close-line");
    const errorMessage = inputBox.nextElementSibling;

    input.addEventListener("input", () => {
        const value = input.value.trim();

        if (value === "") {
            // Si el input está vacío, restablecer estilos
            inputBox.classList.remove("input-error");
            checkIcon.style.display = "none";
            errorIcon.style.display = "none";
            errorMessage.style.display = "none";
            input.style.border = "";
        } else if (fields[fieldId].regex.test(value)) {
            // Si es válido
            checkIcon.style.display = "inline-block";
            errorIcon.style.display = "none";
            errorMessage.style.display = "none";
            input.style.border = "2px solid #0034de";
            inputBox.classList.remove("input-error");
        } else {
            // Si es inválido
            checkIcon.style.display = "none";
            errorIcon.style.display = "inline-block";
            errorMessage.style.display = "block";
            input.style.border = "2px solid #fd1f1f";
            inputBox.classList.add("input-error");
        }
    });

    const form = document.querySelector("form");
    const errorMessageBox = document.querySelector(".input__advertencia");
    const errorMessages = document.querySelectorAll(".input__error");
    const inputs = document.querySelectorAll("input:not([type='checkbox'])"); // Todos los inputs excepto el checkbox
    const checkbox = document.querySelector(".remember-forgot input"); // Selecciona el checkbox

    form.addEventListener("submit", function (event) {
        let valid = true;

        // Ocultar mensajes de error individuales
        errorMessages.forEach(error => {
            error.style.display = "none";
        });

        // Verificar si los campos de texto están vacíos
        inputs.forEach(input => {
            if (input.value.trim() === "") {
                valid = false;
            }
        });

        // Verificar si el checkbox está marcado
        if (!checkbox.checked) {
            valid = false;
        }

        // Si hay errores, mostrar solo el mensaje general y evitar el envío
        if (!valid) {
            errorMessageBox.style.display = "flex";
            event.preventDefault();
        }
    });

    // Ocultar el mensaje de advertencia cuando el usuario empieza a escribir o marca el checkbox
    inputs.forEach(input => {
        input.addEventListener("input", function () {
            errorMessageBox.style.display = "none";
        });
    });

    checkbox.addEventListener("change", function () {
        errorMessageBox.style.display = "none";
    });

});

document.addEventListener("DOMContentLoaded", function () {
    const errorMessageBox = document.querySelector(".input__advertencia");
    if (errorMessageBox) {
        setTimeout(function () {
            errorMessageBox.style.display = "none";
        }, 20000); // Ocultar después de 20 segundos
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const errorChanged2 = document.querySelector(".input__error error--changed2");
    if (errorChanged2 && errorChanged2.style.display !== "none") {
        setTimeout(function () {
            errorChanged2.style.display = "none";
        }, 20000); // Ocultar después de 20 segundos
    }
});
