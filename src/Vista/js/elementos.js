$(document).ready(function () {

    $('#id-ver-info').click(function () {
        $("#div-info").toggleClass("hidden")
        $("#linea-opcion").addClass("hidden");
    });

    $('#boton-opciones').click(function () {
        $("#linea-opcion").toggleClass("hidden")
    });

    //  funcion para mostrar mensaje informativo button eliminar
    $('#deleteButton').hover(function (event) {
        $('#delete-e').css({
            display: 'block',
            top: event.pageY + 10 + 'px',
            left: event.pageX + 10 + 'px'
        });
    }, function () {
        $('#delete-e').css('display', 'none');
    });

    //  funcion para mostrar mensaje informativo button modificar
    $('#modifyButton').hover(function (event) {
        $('#modify-e').css({
            display: 'block',
            top: event.pageY + 10 + 'px',
            left: event.pageX + 10 + 'px'
        });
    }, function () {
        $('#modify-e').css('display', 'none');
    });

    //  funcion para mostrar mensaje informativo button agregar registro identificador 
    $('#moverButton').hover(function (event) {
        $('#move-e').css({
            display: 'block',
            top: event.pageY + 10 + 'px',
            left: event.pageX + 10 + 'px'
        });

    }, function () {
        $('#move-e').css('display', 'none');
    });

    //  funcion para mostrar mensaje informativo button agregar registro identificador 
    $('#agregarRegistroButton').hover(function (event) {
        $('#add-e').css({
            display: 'block',
            top: event.pageY + 10 + 'px',
            left: event.pageX + 10 + 'px'
        });
    }, function () {
        $('#add-e').css('display', 'none');
    });

    //mostrar mensaje actualizar elemento
    $('#btnActualizar').hover(function (event) {
        $('#update-e').css({
            display: 'block',
            top: event.pageY + 10 + 'px',
            left: event.pageX + 10 + 'px'
        });
    }, function () {
        $('#update-e').css('display', 'none');
    });

    //mostrar informacion boton hacer reporte
    $('#btnReportar').hover(function (event) {
        $('#report-e').css({
            display: 'block',
            top: event.pageY + 10 + 'px',
            left: event.pageX + 10 + 'px'
        });
    }, function () {
        $('#report-e').css('display', 'none');
    });


    // ------------------------------------------------------------------
    // CONFIGURACION BOTON ELIMINAR ELEMENTO

    // Mostrar tarjeta de conformacion eliminacion
    const BotonDelete = document.getElementById("DropElement");
    const card = document.querySelector(".superpuesto")
    const cardOverlay = document.getElementById("card-overlay");


    function MostrarCardEliminacion() {
        cardOverlay.classList.add("overlay");
        card.classList.remove("hidden");
    };

    // Eliminar fila elemento
    const botonConfirmar = document.querySelector("#confirmarBtn");
    function EliminarFilaElemento(e) {
        let elementoPadre = card.closest(".row")
        if (elementoPadre) {
            elementoPadre.remove();
        }
    }
    //  cancelar eliminacion
    const botonCancelar = document.querySelector("#rechazarBtn");

    function Cancelar() {
        card.classList.add("hidden")
        cardOverlay.classList.remove("overlay");
    };

    BotonDelete.addEventListener("click", MostrarCardEliminacion)
    botonConfirmar.addEventListener("click", EliminarFilaElemento)
    botonCancelar.addEventListener("click", Cancelar)






    const cardOverlay1 = document.querySelector("#card-overlay1");

    function MostrarCardestado() {
        cardOverlay1.classList.add("overlay")
        cardEstado.classList.remove("hidden")
    };
    function OcultarCardEstado() {
        cardOverlay1.classList.remove("overlay");
        cardEstado.classList.add("hidden");
    };


    const cancelarMoverE = document.querySelector("#btn-cancelar-mover");
    const formularioMoverElemento = document.querySelector(".superpuesto-mover-elemento");



    // CONFIGURACION BOTON MOVER ELEMENTO

    const botonMoverElemn = document.querySelector("#MoveElement");
    const cardOverlay2 = document.querySelector("#card-overlay2");

    function MostarFormMoverElemento() {
        cardOverlay2.classList.add("overlay")
        formularioMoverElemento.classList.remove("hidden");
        let elems = document.querySelectorAll("select");
        M.FormSelect.init(elems);
    };

    function OcultarFormularioMover() {
        formularioMoverElemento.classList.add("hidden")
        cardOverlay2.classList.remove("overlay")
    };

    botonMoverElemn.addEventListener("click", MostarFormMoverElemento);
    botonMoverElemn.addEventListener("click", MostarFormMoverElemento);
    cancelarMoverE.addEventListener("click", OcultarFormularioMover);

    // CONFIGURACION BOTON AGREGAR IDENTIFICADOR
    const btnCancelarAgg = document.querySelector("#btnCancelId");
    const btnAggId = document.querySelector("#AggId");

    const formAggIdentificador = document.querySelector(".superpuesto-agg-id");
    const cardOverlay3 = document.querySelector("#card-overlay3");

    function AgregarIdentificador() {
        cardOverlay3.classList.add("overlay")
        formAggIdentificador.classList.remove("hidden");
    };

    function OcultarFormID() {
        formAggIdentificador.classList.add("hidden");
        cardOverlay3.classList.remove("overlay");
    };

    btnAggId.addEventListener("click", AgregarIdentificador);
    btnCancelarAgg.addEventListener("click", OcultarFormID);



    // CONFIGURACION ACTUALIZAR ELEMENTO
    const CancelUpdate = document.querySelector("#cancelUpdate");
    const formActualizar = document.querySelector(".superpuesto-actualizar");
    const cardOverlay4 = document.querySelector("#card-overlay4");
    const btnUpdate = document.querySelector("#updateElement")
    function Actualizar() {
        cardOverlay4.classList.add("overlay")
        formActualizar.classList.remove("hidden");
    };
    function OcultarFormUpdate() {
        formActualizar.classList.add("hidden");
        cardOverlay4.classList.remove("overlay")
    };
    btnUpdate.addEventListener("click", Actualizar);
    CancelUpdate.addEventListener("click", OcultarFormUpdate);
    console.log(btnCancelarAgg); // ¿null?
    console.log(btnAggId);
    console.log(formAggIdentificador);
    console.log(cardOverlay3);

    // CONFIGURACION SELECT
    let elems = document.querySelectorAll('select');
    M.FormSelect.init(elems);

    // CONFIGURACION SI ES MOBILIARIO O TECNOLOGICO

    const tipoElemento = document.getElementById("tipoElemento");
    const camposTecnologicos = document.getElementById("camposTecnologicos");
    tipoElemento.addEventListener("change", function () {
        const tipo = this.value;

        if (tipo === "tecnologico") {
            camposTecnologicos.classList.remove("hidden");
        } else {
            camposTecnologicos.classList.add("hidden");
        }

    });








});