document.addEventListener("DOMContentLoaded", () => {

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
    
    //Eliminar Cuenta
    const checkbox = document.getElementById('confirm-delete');
    const deleteBtn = document.getElementById('delete-btn');

    checkbox.addEventListener('change', () => {
        if (checkbox.checked) {
            deleteBtn.disabled = false;
            deleteBtn.classList.add('active');
        } else {
            deleteBtn.disabled = true;
            deleteBtn.classList.remove('active');
        }
    });

    // Validaciones
    const fieldConfig = {
        respuesta1: {regex: /^.{1,}$/,errorMessage: " La respuesta de la pregunta es obligatoria"},
        respuesta2: {regex: /^.{1,}$/,errorMessage: " La respuesta de la pregunta es obligatoria"}
    };

   const formulario = document.querySelector(".formulario");
    const advertencia = document.querySelector(".input__advertencia");
    const selectPregunta1 = document.getElementById("pregunta1");
    const errorPregunta1 = document.querySelector(".error--pregunta1");
    const selectPregunta2 = document.getElementById("pregunta2");
    const errorPregunta2 = document.querySelector(".error--pregunta2");

      // Validar en tiempo real los inputs
    Object.keys(fieldConfig).forEach(fieldId => {
        const input = document.getElementById(fieldId);
        if (!input) return;

        const inputBox = input.closest(".input-box");
        const checkIcon = inputBox.querySelector(".ri-check-line");
        const errorIcon = inputBox.querySelector(".ri-close-line");
        const errorMessage = inputBox.nextElementSibling;
        const label = inputBox.querySelector("label");

        input.addEventListener("input", () => {
            advertencia.style.display = "none";

            const value = input.value.trim();
            if (value === "") {
                checkIcon.style.display = "none";
                errorIcon.style.display = "none";
                errorMessage.style.display = "none";
                input.style.border = "";
                label.style.color = "";
                inputBox.classList.remove("input-error");
            } else if (fieldConfig[fieldId].regex.test(value)) {
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


    // Ocultar advertencias y errores de select al interactuar
    [selectPregunta1, selectPregunta2].forEach(select => {
        select.addEventListener("change", () => {
            advertencia.style.display = "none";

            if (select.selectedIndex > 0) {
                select.style.border = "2px solid #0034de";
            } else {
                select.style.border = "";
            }
             if (select === selectPregunta1 && select.selectedIndex > 0) {
                errorPregunta1.style.display = "none";
            }
            if (select === selectPregunta2 && select.selectedIndex > 0) {
                errorPregunta2.style.display = "none";
            }

        });
    });

    // Validación al enviar el formulario
    formulario.addEventListener("submit", function (e) {
        e.preventDefault();

        let formularioValido = true;
        let todosInputsValidos = true;

        // Validar todos los inputs
        Object.keys(fieldConfig).forEach(fieldId => {
            const input = document.getElementById(fieldId);
            const regex = fieldConfig[fieldId].regex;

            if (!regex.test(input.value.trim())) {
                formularioValido = false;
                todosInputsValidos = false;
            }
        });

        const preguntra1Seleccionada  = selectRespuesta1.selectedIndex > 0;
        const preguntra2Seleccionada  = selectRespuesta2.selectedIndex > 0;


        // Caso: Inputs válidos, pero selects incompletos
        if (todosInputsValidos && (!preguntra1Seleccionada || !preguntra2Seleccionada)) {

            if (!preguntra1Seleccionada) {
                errorPregunta1.style.display = "block";
                selectPregunta1.style.border = "2px solid #fd1f1f";
            }
            if (!preguntra2Seleccionada) {
                errorPregunta2.style.display = "block";
                selectPregunta2.style.border = "2px solid #fd1f1f";
            }

            advertencia.style.display = "block";
            return;
        }

        // Caso: Inputs o selects inválidos
        if (!formularioValido) {
            advertencia.style.display = "block";
            //  no mostramos errores de selects si inputs están mal
            return;
        }

        // Todo correcto: ocultar advertencias y errores
        advertencia.style.display = "none";
        errorPregunta1.style.display = "none";
        errorPregunta2.style.display = "none";


        // Enviar formulario
        formulario.submit();
    });

});