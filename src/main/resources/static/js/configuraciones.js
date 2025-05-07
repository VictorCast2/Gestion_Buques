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

    const formConfig = document.querySelector(".formulario");
    const advertenciaConfig = document.querySelector(".input__advertencia");
    const selectRespuesta1 = document.getElementById("respuesta1");
    const selectRespuesta2 = document.getElementById("respuesta2");
    const errorRespuesta1 = document.querySelector(".error--respuesta1");
    const errorRespuesta2 = document.querySelector(".error--respuesta2");

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
            advertenciaConfig.style.display = "none";

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
    [selectRespuesta1, selectRespuesta2].forEach(select => {
        select.addEventListener("change", () => {
            advertenciaConfig.style.display = "none";

            if (select === selectRespuesta1 && select.selectedIndex > 0) {
                errorRespuesta1.style.display = "none";
            }
            if (select === selectRespuesta2 && select.selectedIndex > 0) {
                errorRespuesta2.style.display = "none";
            }

        });
    });

    // Validación al enviar el formulario
    formConfig.addEventListener("submit", function (e) {
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

        const respuesta1Seleccionado = selectRespuesta1.selectedIndex > 0;
        const respuesta2Seleccionada = selectRespuesta2.selectedIndex > 0;


        // Caso: Inputs válidos, pero selects incompletos
        if (todosInputsValidos && (!respuesta1Seleccionado || !respuesta2Seleccionada )) {
            if (!respuesta1Seleccionado) errorRespuesta1.style.display = "block";
            if (!respuesta2Seleccionada) errorRespuesta2.style.display = "block";
            advertenciaConfig.style.display = "block";
            return;
        }

        // Caso: Inputs o selects inválidos
        if (!formularioValido) {
            advertenciaConfig.style.display = "block";
            //  no mostramos errores de selects si inputs están mal
            return;
        }

        // Todo correcto: ocultar advertencias y errores
        advertenciaConfig.style.display = "none";
        errorRespuesta1.style.display = "none";
        errorRespuesta2.style.display = "none";


        // Enviar formulario
        formConfig.submit();
    });

});