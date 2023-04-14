console.log('HolaJavascript! desde el Fichero demo.js ');
a=5;
b=6;
console.log(`------> ${+a+b}`);


//////////////////////
// CONDICIONALES /////
/////////////////////
console.log('----------------------------------');
console.log('-----------CONDICIONALES-----------');
console.log('----------------------------------');
/**
 * Operadores
    • Asignación
        Simple: <Destino> = <Expresión>
    • Aritméticos
        + : Suma, si el algún operando es una cadena se concatena.
        - : Resta, cambio de signo cuando es unario.
        * : Producto
        / : División, el resultado es siempre real.
        % : Resto de la división entera
    • Acumulativos: 
        += , -= , *= , /= , %=
    • Operadores relacionales
        > : mayor
        >= : mayor o igual
        < : menor
        <= : menor o igual
        == : igual
        != : distinto
        === : identidad (coincide el tipo)
        !== : no identidad
 */

if(a=1){  //OJO CON ESTO!!!!! el signo  = es un operador que devuelve el valor asignado
    //y el valor asignado es 1, y 1 en boolean es TRUE! 
     console.log(`Cierto`);
 }
 a=5;
         
 if(a==1){  //Aca si COMPARA y dara FALSE
     console.log(`Cierto`);
 }else{
    console.log(`No Cierto`);
 }

//Creando una funcion:
 function kk(){
    console.log(`------> ${+a+b}`); //Aca estamos usando variables GLOBALES
    var localVar = 6;               //localVar no podemos usarla desde FUERA de la Funcion
    d = a+localVar;                 // d es GLOBAL, al no ponerle VAR, podemos usarla fuera

    if(false){
        x = 99;
    }
 }

 //Invocando la funcion
 kk();

 //Esto daria error:
 //console.log(`------> ${localVar}`);
 // Porque localVar es LOCAL DECLARADA de la funcion kk()

 //En cambio, la variable d, no le pusimos "var", por lo que se declara como GLOBAL
 //En este caso, si podemos acceder a d:
 console.log(`------> ${d}`); //Aca estamos usando variables GLOBALES

 //Todo esto, luego de haber llamado la funcion, si no se llamo la funcion, todavia no fue declarada!
 
 //Invocando la funcion
 kk();
 //console.log(`------> ${x}`); //Aca daria error porque x NO ESTA DELCARADA TODAVIA

 x=88;
 console.log(`------> ${x}`); //Aca Si funcionaria

///////////////////
// BUCLES FOR /////
//////////////////
console.log('----------------------------------');
console.log('-----------BUCLES FOR-------------');
console.log('----------------------------------');

t = [10,20,30];  //una lista de 3 elementos numericos

for(a in t){
    console.log(a); //imprime LOS INDICES!!!
}
for(a in t){
    console.log(t[a]); //imprime LOS VALORES!!!!
} //Conclusion, con el IN nos movemos por los INDICES
for(a of t){
    console.log(a); //Aca obtenemos los VALORES
}


//Ternario:
valor=1;
//     condicion    true  :   false
console.log( valor ? `Cierto` : `Falso`);
//Ojo aca! valor es 1, entonces es TRUE!


///////////////////
// FUNCIONES /////
//////////////////
console.log('----------------------------------');
console.log('------------FUNCIONES--------------');
console.log('----------------------------------');


function nombreDeFuncion(atributos){
    return atributos + atributos;
}

console.log(nombreDeFuncion("hola!"));
console.log(nombreDeFuncion(2));


// Ojo con los argumentos:

function avg() {
    var rslt= 0;
    for(var i=0; i < arguments.length; i++) {
    rslt += arguments[i];
    }
    return arguments.length ? (rslt / arguments.length) : 0;
}
// la variable "arguments" pasa a ser una LISTA de todos los argumentos enviados
console.log(avg(2,2,2,2,10));


// Se pueden enviar argumentos predefinidos, y no definidos:
function foo (x, y, ...a) {
    return (x + y) * a.length
}
console.log(foo(1, 2, "hello", true, 7));
// (1 + 2) * 3  = 9

console.log(foo(1, 2));
// (1 + 2) * 0  = 0

console.log(foo(2, 2, 4,3,2,3,5,6,7));
// (2 + 2) * 7  = 28

/// OJO CON LA DESESTRUCTURACION:
// es cuando se ponen ... delante, ejemplo:
t = [10,20,30];
console.log(avg(...t));

//lo que hace es DESESTRUCTURAR LOS VALORES DE T, y mandarlos como argumentos a la funcion,
//es decir, haria esto mismo:
console.log(avg(t[0],t[1],t[2]));


/////////////////
// Podemos asignar funciones a variables:
 
var funcion = function(a,b) {return a+b};

//////////////////
// Funciones Anonimas:
//Son funciones que no tienen nombre:

(function () {
    return null;
})

//Como ejemplo, se usa cuando necesitan hacer cosas que no tienen que dejar variables globales declaradas

(function () {
    for(let i=0; i<10; i++){
        // ....
    }
})

// Se mete en el bucle, y recien ahi genera la variable I de manera LOCAL, y cuando termina
//la funcion anonima, desaparece esa variable i



///////////////
// LAMBDAS ////
//////////////
console.log('----------------------------------');
console.log('-------------LAMBDAS--------------');
console.log('----------------------------------');

// Funcion VS Funcion Lambda:
function suma(a,b) { return a+b;}
suma = (a,b) => a+b; // LAMBDA
//    atributos =>  retorno

//Podemos hacer logica de retorno:
suma = (a,b,...c) =>{ 
    // Logica aqui....
    return x; //y el retorno final...
    } 


console.log(t.filter(item => item / 2 == 5)); //Funcion de ORDEN SUPERIOR - Investigar...
//Vemos que el FILTER devuelve algo del mismo tipo que T pero filtrando solo
//los valores que cumplan la condicion 2==5 en este caso
console.log(t.filter(item => item > 20)); // devuelve: [ 30 ]
console.log(t.filter(function(item) {return item>10})); // devuelve: [ 30 ]

filtrado = function(item) {return item>10};

console.log(t.filter(filtrado));
//Claro ejemplo de funcion anonima! Ahi al final, la asignamos a "filtrado"

// Atento!! :


filtrado = function(item) {return item>10};
for(let i = 0; i<10; i++){
    t.filter(filtrado);
}

//En este caso, crea 1 vez la funcion "anonima" filtrado, y luego la ejecuta 10 veces dentro del for
//Si dentro del for hubiesemos puesto directamente la funcion anonima, la iba a crear cada vez que
//la neceste. Porque internamentem cada vez que entra en el for "crea" esa funcion anonima


/////////////
//Ver lo del profe de PUNTO
punto = {x:1, y:2};

///////////////
// ARRAYS ////
//////////////
console.log('----------------------------------');
console.log('--------------ARRAYS--------------');
console.log('----------------------------------');

let tab = new Array();
tab = [10,20,30];
for(a in tab){
    console.log(a); //INDICES!
}
for(a of tab){
    console.log(a); //VALORES!
}

//OJO!!!

tab[5] = 100;
for(b in tab){
    console.log(b); //INDICES! serian 0, 1, 2, 5
}
for(b of tab){
    console.log(b); //VALORES! serian 10, 20, 30, undefined, undefined, 100
}

// a los arrays, podemos asignarle CUALQUIER COSA.. como por ejemplo:
tab[3] = function(a,b) {return a+b};
tab[4] = "Esto es un String";

for(b in tab){
    console.log(b); //INDICES
}
for(b of tab){
    console.log(b); //VALORES! Tenemos valores numericos, funciones, y String!
}

//Para agregarle cosas:
tab.push((a,b)=> a-b)  //le estamos agregando una LAMBDA al FINAL (esto lo hace el push)

for(b in tab){
    console.log(b); //INDICES
}
for(b of tab){
    console.log(b); //VALORES
}

//GOOGLEAR COMO ELIMINAR COSAS DE ARRAYS


///////////////
// OBJETOS ////
//////////////
console.log('----------------------------------');
console.log('---------------OBJETOS------------');
console.log('----------------------------------');


let o = new Object();
o = {};

o.nombre = "Nombre";  //Le asignamos a la key 'nombre' el valor "Nombre"
o['apellidos'] = "Apellidos"; //Le asignamos a la key 'apellidos' el valor "Apellido"

console.log(o); //Vemos que es como un "diccionario"

o.nom = () => o.nombre + ' ' + o.apellidos; //Le asignamos una funcion anonima

console.log(o.nom()); //Imprimimos el RETORNO de la funcion nom


//////////////////////////////
// FUNCIONES CONSTRUCTORAS ////
/////////////////////////////
console.log('----------------------------------');
console.log('------FUNCIONES CONSTRUCTORAS----');
console.log('----------------------------------');

function MiClase(elId, elNombre) {
    this.id = elId;
    this.nombre = elNombre;
    this.muestraId = function() {
        alert("El ID del objeto es " + this.id);
    }
    this.ponNombre = function(nom) {
    this.nombre=nom.toUpperCase();
    }
    return 'Retorno comun';
}
//Si usamos el NEW es una funcion constructora:
var elObjeto = new MiClase("99", "Objeto de prueba");

console.log('Imprimimos el objeto de la funcion constructora:');
console.log(elObjeto);

// Sino, es una funcion normal...
var otroObjeto = MiClase("99", "Objeto de prueba");

console.log('Al ser una funcion "normal" nos va a imprimir su retorno:');
console.log(otroObjeto);

////////////////
// PROTOTYPE ////
/////////////////
console.log('----------------------------------');
console.log('-----------PROTOTYPE------------');
console.log('----------------------------------');

//Es para ir añadiendole cosas a los objetos:
//Le vamos a agregar una funcion lambda que se llame cotilla:
MiClase.prototype.cotilla = () => console.log('Estoy en el prototpo de MICLASE')

var otroObjeto2 = new MiClase("99", "Objeto de prueba");
otroObjeto2.cotilla();

//otroObjeto.cotilla();
//Aqui no funcionaria, porque fue creado antes de asignarle el prototipo!

//Esto sirve para "construirle" cosas a los objetos
//Estamos construyendole metodos internos

Array.prototype.kk = () => console.log('Ahora Array tiene un metodo kk()');
t = [];
t.kk();

////////////////
// GLOBAL ////
/////////////////
console.log('----------------------------------');
console.log('-------------GLOBAL--------------');
console.log('----------------------------------');

// Es una variable que tiene a todas las variables usadas en la aplicacion:
// console.log(globalThis);


////////////////
////// THIS ////
/////////////////
console.log('----------------------------------');
console.log('-------------THIS----------------');
console.log('----------------------------------');

//El this hace referencia a un objeto, y eso cambia en funcion al contexto
//si es dentro de una funcion constructora, hace referencia al objeto que se esta inicializando
//si es dentro de una funcion comun, hace referencia a la propia funcion
//si esta dentro del ambito global, hace referencia a la variable thisGlobal

let obj = new Object();
obj = {};

obj.nombre = "Nombre";
obj.apellidos= "Apellidos"; 
obj.nom = () => this.nombre + ' ' + this.apellidos; //Le asignamos una funcion anonima

console.log(obj.nom()); //Imprimimos el RETORNO de la funcion nom
//OJO!!! UNDEFINED!!! Porque??? Porque usa el THIS de GLOBAL

const ponerNombre = function(tratamiento){
    return tratamiento + ' ' + this.nombre + ' ' + this.apellidos
}
// Creamos una funcion CONSTANTE que devuelve el tratamiento, y "nombre" y "apellidos"
//Lo que hacemos ahora, es a esa funcion "ponerNombre" se la  "APLICAMOS" un objeto con apply:
console.log(ponerNombre.apply(obj, ["Sr"]));
// Si la funcion tiene atributos, con el apply hay que mandarselos con array, sino con call:
console.log(ponerNombre.call(obj, "Sr"));
//Lo que hace esto es ejecutar la funcion ponerNombre con los ATRIBUTOS PROPIOS DEL OBJETO
//Es decir, ahora ponerNombre con su "this" buscara las cosas propias del OBJETO que le hemos enviado

//////////////////////

// Al this podemos meterlo en una variable:

function MiClase(elId, elNombre) {
    let nuevoThis = this;
    nuevoThis.id = elId;
    nuevoThis.nombre = elNombre;
    nuevoThis.muestraId = function() {
        console.log("El ID del objeto es " + this.id);
    }
    nuevoThis.ponNombre = function(nom) {
        nuevoThis.nombre=nom.toUpperCase();
    }
    return 'Retorno comun';
}

// Esto se hace para evitar inconvenientes con la palabra "this"
// Aca el THIS hace referencia al THIS DE ESE OBJETO EN SI (ver como usa su "nuevo this")
// Porque podriamos hacer:
var obj1= new MiClase('11', 'Nombre 11');
var obj2= new MiClase('22', 'Nombre 22');

obj1.muestraId();
//hasta ahi nada raro, ahora...
obj1.muestraId.call(obj2);
//Vemos que llamamos la funcion muestraId DESDE EL OBJ1, pero con el OBJ2
//Entonces, al usar el THIS en la funcion muestraId, muestra el ID del OBJ2 que le mandamos a llamar

// Hay que estar atentos a como se usa el THIS y como reacciona segun donde se aplica


////////////////////////////////////////////////
////// NOMBRES VARIABLES EN OBJETOS ////
/////////////////////////////////////////////////
console.log('----------------------------------');
console.log('--NOMBRES DE VARIABLES EN OBJETOS--');
console.log('----------------------------------');

let x1 = 10, y1 = 20;

//Lo correcto seria:
let punto1 = { x1 : x1 , y1 : y1 }

//Pero se puede hacer:
punto1 = { x1 , y1 }
//Es exactamente lo mismo, lo que hace es asignar la "key" x1 con el nombre de la variable x1
//y ademas le asigna el valor de x1. Para esto es importante asignar bien los nombres de variables

//Con funciones se puede hacer:

punto1 = { x1 : x1 , y1 : y1 , suma : function() {return this.x1 + this.x2} } //old

punto1 = { x1 , y1 , suma() {return this.x1 + this.x2} } //new!

// En este caso, la funcion suma dentro del objeto, podremos acceder con la key 'suma'


///////////////////
////// CLASES ////
//////////////////
console.log('----------------------------------');
console.log('--------------CLASES--------------');
console.log('----------------------------------');

class Rectangle {
    constructor(width,height){
        this._width = width;
        this._height = height;
    }
    set width (width) { this._width = width; }
    get width () { return this._width ; }

    set height (height) { this._height = height; }
    get height () { return this._height ; }

    get area() { return this._width * this._height; }
}

var rectangulo = new Rectangle(10,20);
console.log(rectangulo.area);

// Las clases pueden heredar:

class Persona {
    constructor(id, nombre, apellidos){
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }
    //al tener el get, lo llamaremos como si fuera una PROPIEDAD SIN PARENTESIS
    get nombreCompleto(){
        return this.nombre + ', ' + this.apellidos;
    } 

    //metodo de clase: a este lo llamaremos CON PARENTESIS
    pinta(){
        console.log(this.nombreCompleto)
    }
}

let persona1 = new Persona(1,"Juan","Gomez");
let persona2 = new Persona(2,"Martin","Perez");
let persona3 = new Persona(3,"Jose","Martinez");

persona1.pinta()

//ATENCION!!! Que tipo es Persona???
console.log(typeof(Persona));
// Es una FUNCION CONSTRUCTORA!
