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
    let nombre = 'mundo';
    let saluda = <h1>Hola {nombre.toUpperCase() + '!'} - !!</h1>;
    let estilo = 'App-link';
    let dim = {width:100, heigth:50};
    let errorStyle = {color:'white',backgroundColor:'red'};
    return (
      <>
        {saluda}
        <div style={{color:'white',backgroundColor:'red'}}>
          DemosJSX
        </div>
        <h2>Hola {nombre}</h2>
        <h2 className="App-link">Hola {nombre} con class</h2>
        <h2 className={estilo}>Hola {nombre} con class estilo en variable</h2>
        <h2 className={estilo} style={errorStyle}>Hola {nombre} con estilo de variable</h2>
        <img src={logo} className="App-logo" alt="logo" width={100} hidden={false} />
        <p>Ahora Logo con desestructurar componentes:</p>
        <img src={logo} className="App-logo" alt="logo" {...dim} hidden={false} />


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
