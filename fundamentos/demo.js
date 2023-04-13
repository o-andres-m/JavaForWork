console.log('HolaJavascript! desde el Fichero demo.js ');
a=5;
b=6;
console.log(`------> ${+a+b}`);

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

////////////7
//Ojo con los FOR

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
