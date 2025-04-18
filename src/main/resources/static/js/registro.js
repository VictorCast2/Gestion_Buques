document.addEventListener("DOMContentLoaded", function () { 
    // Manejo del menú desplegable
    const selectBox = document.getElementById("selectBox");
    const optionsList = document.getElementById("optionsList");
    const selectedText = selectBox?.querySelector(".selected");
    const options = document.querySelectorAll(".option");
    const errorTipoId = document.querySelector(".error--changed");
    const errorMessageBox = document.querySelector(".input__advertencia");
    const tipoIdentificacionInput = document.getElementById("tipoIdentificacionInput");
    let tipoIdentificacionSeleccionado = false;

    if (selectBox && optionsList && selectedText && options) {
        selectBox.addEventListener("click", () => {
            selectBox.classList.toggle("open");
            optionsList.classList.toggle("open");
        });

        options.forEach(option => {
            option.addEventListener("click", () => {
                selectedText.textContent = option.textContent;
                tipoIdentificacionSeleccionado = true;
                errorTipoId.style.display = "none";
                errorMessageBox.style.display = "none";
                selectBox.classList.remove("open");
                optionsList.classList.remove("open");
                tipoIdentificacionInput.value = option.getAttribute("data-value");
            });
        });

        document.addEventListener("click", (event) => {
            if (!selectBox.contains(event.target) && !optionsList.contains(event.target)) {
                selectBox.classList.remove("open");
                optionsList.classList.remove("open");
            }
        });
    }

    // Validaciones de formulario 
    const fields = {
        nombre: { regex: /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]{3,}$/, errorMessage: "El nombre debe tener al menos 3 letras." },
        apellido: { regex: /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]{3,}$/, errorMessage: "El apellido debe tener al menos 3 letras." },
        identificacion: { regex: /^\d{6,10}$/, errorMessage: "La cédula debe contener entre 6 y 10 dígitos." },
        telefono: { regex: /^\d{1,10}$/, errorMessage: "El teléfono solo puede contener números (máx. 10)." },
        email: { regex: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/, errorMessage: "Formato de correo inválido." },
        password: { regex: /^.{4,12}$/, errorMessage: "La contraseña debe tener entre 4 y 12 caracteres." },
        repeatPassword: { regex: null, errorMessage: "Las contraseñas no coinciden." }
    };

    Object.keys(fields).forEach(fieldId => {
        const input = document.getElementById(fieldId);
        if (!input) return;

        const inputBox = input.closest(".input-box");
        const checkIcon = inputBox.querySelector(".ri-check-line");
        const errorIcon = inputBox.querySelector(".ri-close-line");
        const errorMessage = inputBox.nextElementSibling;
        const label = inputBox.querySelector("label");

        input.addEventListener("input", () => {
            const value = input.value.trim();
            if (value === "") {
                inputBox.classList.remove("input-error");
                checkIcon.style.display = "none";
                errorIcon.style.display = "none";
                errorMessage.style.display = "none";
                input.style.border = "";
                label.style.color = "";
            } else if (fieldId === "repeatPassword") {
                const passwordValue = document.getElementById("password").value.trim();
                if (value === passwordValue && value !== "") {
                    checkIcon.style.display = "inline-block";
                    errorIcon.style.display = "none";
                    errorMessage.style.display = "none";
                    input.style.border = "2px solid #0034de";
                    label.style.color = "";
                    inputBox.classList.remove("input-error");
                } else {
                    checkIcon.style.display = "none";
                    errorIcon.style.display = "inline-block";
                    errorMessage.style.display = "block";
                    input.style.border = "2px solid #fd1f1f";
                    label.style.color = "red";
                    inputBox.classList.add("input-error");
                }
            } else if (fields[fieldId].regex.test(value)) {
                checkIcon.style.display = "inline-block";
                errorIcon.style.display = "none";
                errorMessage.style.display = "none";
                input.style.border = "2px solid #0034de";
                label.style.color = "";
                inputBox.classList.remove("input-error");
            } else {
                checkIcon.style.display = "none";
                errorIcon.style.display = "inline-block";
                errorMessage.style.display = "block";
                input.style.border = "2px solid #fd1f1f";
                label.style.color = "red";
                inputBox.classList.add("input-error");
            }
        });
    });

    // Validación en tiempo real de repeatPassword cuando cambia password
    const passwordInput = document.getElementById("password");
    const repeatPasswordInput = document.getElementById("repeatPassword");
    
    passwordInput.addEventListener("input", () => {
        repeatPasswordInput.dispatchEvent(new Event("input"));
    });

    const form = document.querySelector("form");
    const errorMessages = document.querySelectorAll(".input__error");
    const inputs = document.querySelectorAll("input:not([type='checkbox'])");
    const checkbox = document.querySelector(".remember-forgot input");

    form.addEventListener("submit", function (event) {
        let valid = true;

        errorMessages.forEach(error => error.style.display = "none");
        inputs.forEach(input => { if (input.value.trim() === "") valid = false; });
        if (!checkbox.checked) valid = false;

        const camposVacios = [...inputs].filter(input => input.value.trim() === "").length;
        if (!tipoIdentificacionSeleccionado && camposVacios === 0) {
            errorTipoId.style.display = "block";
            valid = false;
        } else {
            errorTipoId.style.display = "none";
        }

        if (!valid) {
            errorMessageBox.style.display = "flex";
            event.preventDefault();
        }
    });

    inputs.forEach(input => input.addEventListener("input", () => errorMessageBox.style.display = "none"));
    checkbox.addEventListener("change", () => errorMessageBox.style.display = "none");
});
