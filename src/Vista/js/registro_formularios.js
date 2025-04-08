$(document).ready(function () {
    $('#btn-registrar-elementos').click(function () {
        $('.botones-elementos').toggleClass('hidden');
        $('.parrafo').toggleClass('hidden');
        $('#formulario-registrar-elementos-tec').addClass('hidden');
        $('#formulario-registrar-elementos-mob').addClass('hidden');
        $("#fila2,#fila3,#fila4,#fila5,#fila6,#fila7,#fila8").toggleClass("hide")
    });
    $('#r_elm_tecnologicos').click(function () {
        $("#formulario-registrar-elementos-mob").addClass("hidden")
        $('#formulario-registrar-elementos-tec').toggleClass('hidden');
    });
    $('#btn-re-e-mob').click(function () {
        $("#formulario-registrar-elementos-tec").addClass("hidden")
        $('#formulario-registrar-elementos-mob').toggleClass('hidden');
    });
    // USUARIOS CONFIG
    $('#btn-registrar-usuarios').click(function () {
        $('#formulario-registrar-usuarios').toggleClass('hidden');
        $("#fila1,#fila3,#fila4,#fila5,#fila6,#fila7,#fila8").toggleClass("hide")

    });
    // ROLES CONFIG
    $('#btn-registrar-rol').click(function () {
        $('#formulario-registrar-rol').toggleClass('hidden');
        $("#fila1,#fila2,#fila4,#fila5,#fila6,#fila7,#fila8").toggleClass("hide")
    });
    // COLEGIO CONFIG
    $('#btn-registrar-colegio').click(function () {
        $('#formulario-registrar-colegio').toggleClass('hidden');
        $("#fila1,#fila2,#fila3,#fila5,#fila6,#fila7,#fila8").toggleClass("hide")
    });
    // SEDE CONFIG
    $('#btn-registrar-sede').click(function () {
        $('#formulario-registrar-sede').toggleClass('hidden');
        $("#fila1,#fila2,#fila3,#fila4,#fila6,#fila7,#fila8").toggleClass("hide")
    });
    // BLOQUE CONFIG
    $('#btn-registrar-bloque').click(function () {
        $('#formulario-registrar-bloque').toggleClass('hidden');
        $("#fila1,#fila2,#fila3,#fila4,#fila5,#fila7,#fila8").toggleClass("hide")
    });
    // PISO CONFIG
    $('#btn-registrar-piso').click(function () {
        $('#formulario-registrar-piso').toggleClass('hidden');
        $("#fila1,#fila2,#fila3,#fila4,#fila5,#fila6,#fila8").toggleClass("hide")
    });
    $('#btn-registrar-aula').click(function () {
        $('#formulario-registrar-aula').toggleClass('hidden');
        $("#fila1,#fila2,#fila3,#fila4,#fila5,#fila6,#fila7").toggleClass("hide")
    });
    $('select!').formSelect();

});
