import logo from './logo.svg';
import './App.css';

//Al poner rcc el vscode nos crea un componente y nos importa lo necesario:
import React, { Component } from 'react'

//Componente "App"
export default class App extends Component {
  render() {
    return (
      <>
        <Home />
{/*         <Calculadora />

 */}
        <DemosJSX/>      
    </>
    )
  }
}
/**
 * Para devolver componentes, deben ser si o si 1 etiqueta, si dentro tiene otras, no pasa nada
 * En este caso tenemos 1 etiqueta DIV que dentro tiene otra
 * Podemos devolver una LISTA de etiquetas sino:
 *     return [
        <Home />
        <DemosJSX />
 ]
 En este caso devuelve una LISTA de etiquetas
 Y por ultimo podemos devolver con una etiqueta diamante:
       <>
        <Home />
        <DemosJSX />
      </>
Es como el DIV pero sin la palabra Div
 */


//Componente DemosJSX
//Ojo que por defecto va como DEFAULT, pero ya tenemos un defailt
class Calculadora extends Component {
  render() {
    return (
      <>
        <main>
          <h1>Calculadora</h1>
          <div className="Calculadora">
            <output className="Resumen" id="txtResumen" />
            <output className="Pantalla" id="txtPantalla" />
            <input
              id="btnIniciar"
              className="btnOperar"
              type="button"
              defaultValue="C"
            />
            <input
              id="btnBorrar"
              className="btnOperar col-2x2"
              type="button"
              defaultValue="↶ BORRAR"
            />
            <input className="btnOperar btnCalc" type="button" defaultValue="+" />
            <input className="btnDigito btnNum" type="button" defaultValue={7} />
            <input className="btnDigito btnNum" type="button" defaultValue={8} />
            <input className="btnDigito btnNum" type="button" defaultValue={9} />
            <input className="btnOperar btnCalc" type="button" defaultValue="-" />
            <input className="btnDigito btnNum" type="button" defaultValue={4} />
            <input className="btnDigito btnNum" type="button" defaultValue={5} />
            <input className="btnDigito btnNum" type="button" defaultValue={6} />
            <input className="btnOperar btnCalc" type="button" defaultValue="*" />
            <input
              className="btnDigito btnNum"
              type="button"
              defaultValue={1}
              data-valor={1}
            />
            <input className="btnDigito btnNum" type="button" defaultValue={2} />
            <input className="btnDigito btnNum" type="button" defaultValue={3} />
            <input className="btnOperar btnCalc" type="button" defaultValue="/" />
            <input className="btnDigito" type="button" defaultValue="±" />
            <input
              className="btnDigito btnNum"
              type="button"
              defaultValue={0}
              data-valor={0}
            />
            <input className="btnDigito" type="button" defaultValue="," />
            <input className="btnOperar btnCalc" type="button" defaultValue="=" />
            {/* 
              <input id="btnPI" class="btnDigito" type="button" value="&#928">
              <input class="btnOperar btnCalc" type="button" value="^">
              */}
          </div>
        </main>
      </>
    )
  }
}


class DemosJSX extends Component {
  render() {
    let nombre = '<b>mundo</b>';
    let saluda = <h1>Hola {nombre.toUpperCase() + '!'} - !!</h1>;
    let estilo = 'App-link';
    let dim = {width:100, heigth:50};
    let errorStyle = {color:'white',backgroundColor:'red'};
    let limpia = true;
    let falsa = { persona : { nombre: "Tiene nombre!" } };
    //let persona = 'Yo!';
    let espacio = <br/>;

    let lista = [
      {id:1 , nombre:'Nombre 1'},
      {id:2 , nombre:'Nombre 2'},
      {id:3 , nombre:'Nombre 3'},
      {id:4 , nombre:'Nombre 4'}
  ];



    return (
      <>
        {/** imprimimos variables: */}
        {saluda}
        {espacio}
        {/** imprimimos booleanos, ojo! Con ternarios: */}
        {limpia ? 'verdadero' : 'falso'}
        {espacio}

        {/** imprimimos con ternarios con HTML: */}
        {limpia ? <b>verdadero</b> : <b>falso</b>}
        {espacio}

        {/** imprimimos con ternarios con HTML y variables: */}
        {limpia ? <b>verdadero - {nombre}</b> : <b>falso - {nombre}</b>}
        {espacio}

        {/** Solo si es TRUE , que devuelva lo de al lado, en este caso LIMPIA */}
        {limpia && <h2>Limpia</h2>}
        {espacio}

        {/** doble ?? indica imprimir el valor, a menos que no exista: */}
        {limpia ?? <b>no existe!</b>}
        {espacio}


        {/** doble ?? indica imprimir el valor, a menos que no exista: */}
        {falsa ? <b>Existe</b>:<b>no existe!</b>}
        {espacio}


        {/** Verificamos si son nulos, cortocircuitando, caso que validen, imprime el ultimo valor de la expresion: */}
        {falsa !== null && falsa.persona !== null && falsa.persona.nombre}
        {espacio}

        {/** Esto mismo que arriba pero mas corto, esto se llama COMPROBACION DE NULOS */}
        {falsa?.persona?.nombre}
        {espacio}

        {/** Igual que arriba, pero con un imprimir NO EXISTE en caso de que haya un nulo*/}
        {falsa?.persona?.nombre ?? <b>no existe</b>}
        {espacio}

        <div style={{color:'white',backgroundColor:'red'}}>
          DemosJSX
        </div>
        <h2>Hola {nombre}</h2>
        <h2 className="App-link">Hola {nombre} con class</h2>
        <h2 className={estilo}>Hola - - <span dangerouslySetInnerHTML={{__html: nombre}}/> con class estilo en variable</h2>
        <h2 className={estilo} style={errorStyle}>Hola {nombre} con estilo de variable</h2>
        <img src={logo} className="App-logo" alt="logo" width={100} hidden={false} />
        <p>Ahora Logo con desestructurar componentes:</p>
        <img src={logo} className="App-logo" alt="logo" {...dim} hidden={false} />

        <p>Probamos con coleccion de numeros:</p>
        <ul>
        {[1,2,3,4,5].map((item,index) => <li key={index}>Elemento {item}</li>)}
        </ul>

        <p>Probamos con una lista de objetos:</p>
        <ul>
          <select>
            {lista.map(item => <option key={item.id} value={item.id}>{item.nombre}</option>)}
          </select>
        </ul>


      </>
    )
  }
}



function Home() {
  return (
    // eslint-disable-next-line jsx-quotes
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>Hello React!</h1>
        <h2>{process.env.API_URL}</h2>
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

//export default App;
