let nombre = 'Modulo2';

function resta(a,b) {
    return a-b;
}

function calc(a,b) {
    return resta(a,b);
}

function display() {
    console.log(`Soy ${nombre}`)
}

//Exportamos la variable y ambas funciones:
export {nombre, calc, display}