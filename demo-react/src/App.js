import logo from './logo.svg';
import './App.css';


import React, { Component } from 'react'
import { Card, Contador } from './componentes';
import { ErrorBoundary } from './comunes';
import Muro from './ejercicios/muro';
import { CalculadoraReact } from './calculadora';

export default class App extends Component {
  constructor(props) {
    super(props)
    this.state = {
        cont: 0,
        main: 0
    }
    this.menu = [
      { texto: 'inicio', url: '/', componente: <Home />},
      { texto: 'demos', url: '/demos', componente: <DemosJSX /> },
      { texto: 'contador', url: '/contador', componente: <Contador init={69} />},
      { texto: 'ejemplos', url: '/ejemplos', componente: <Ejemplos />},
      { texto: 'calculadora', url: '/calculadora', componente: <CalculadoraReact />},
      { texto: 'muro', url: '/muro', componente: <Muro />},

    ]
  }

  render() {
    return (
      <>
        <Cabecera menu={this.menu} onSelectMenu={indice => this.setState({main: indice})} />
        <main className='container-fluid'>
          <ErrorBoundary>
          {this.menu[this.state.main].componente}
          </ErrorBoundary>
        </main>
        <Pie />
      </>
    )
  }
}

function Cabecera(props) {
  return (
    <header>
      <Menu {...props} />
    </header>
  );
}

function Menu({menu, onSelectMenu}) {
  return (
    <nav>
      {menu.map((item, index) => 
        <button key={index} type='button' onClick={() => onSelectMenu && onSelectMenu(index)}>{item.texto}</button>)
      }
    </nav>
  );
}

function Pie() {
  return (
    <div className=" mt-1">
  <section className="">
    <footer
      className="text-center text-white"
      style={{ backgroundColor: "#0a4275" }}
    >
      <div className="container p-4 pb-0">
        <section className="">
          <p className="d-flex justify-content-center align-items-center">
            <span className="me-3">Register for free</span>
            <button type="button" className="btn btn-outline-light btn-rounded">
              Sign up!
            </button>
          </p>
        </section>
      </div>
      <div
        className="text-center p-3"
        style={{ backgroundColor: "rgba(0, 0, 0, 0.2)" }}
      >
        © 2020 Copyright:
        <a className="text-white" href="https://mdbootstrap.com/">
          MDBootstrap.com
        </a>
      </div>
    </footer>
  </section>
</div>

  )
}

class Ejemplos extends Component {
    constructor(props) {
      super(props)
      this.state = {
          cont: 0,
      }
     }
  
    render() {
      return (
        <>
          <main className='container-fluid'>
            <Card tittle="Ejemplo de componente">
              <Contador init={10} delta={2} 
                onChange={num => this.setState({cont: num})} />
            </Card>
            <p>El contador: {this.state.cont}</p>
            <input className='btn btn-bg-danger' type='button' value='No tocar' onClick={() => { throw new Error('No tocar')} } />
          </main>
        </>
      )
    }
  }

class DemosJSX extends Component {
  render() {
    let nombre = '<b>mundo</b>'
    let estilo = 'App-link'
    let saluda = <h1>Hola {nombre.toUpperCase() + '!'}!!</h1>
    let dim = { width: 100, height: 50 }
    let errorStyle = { color: 'white', backgroundColor: 'red' }
    let limpia = true
    let falsa
    let list = [
      {id: 1,nombre:'Madrid'},
      {id: 2,nombre:'Barcelona'},
      {id: 3,nombre:'Sevilla'},
      {id: 4,nombre:'Valencia'},
    ]
    return (
      <>
        {saluda}
        {limpia ? <b>verdadero</b> : <i>falso {nombre}</i>}
        {limpia && <h2>Limpia</h2>}
        {/* {falsa ?? <b>no existe</b>} */}
        {/* {falsa !== null && falsa.persona !== null && falsa.persona.nombre} */}
        {falsa?.persona?.nombre ?? <b>no existe</b>}
        <div style={{ color: 'white', backgroundColor: 'red' }}>DemosJSX</div>
        <h2 className={estilo}  style={errorStyle} >Hola
          <span dangerouslySetInnerHTML={{ __html: nombre }} /></h2>
          <ul>
            {[1,2,3,4, 3, 2, 1].map((item,index) => <li key={index}>Elemento {item}</li>)}
          </ul>
          <select>
            {list.map(item => <option key={item.id} value={item.id}>{item.nombre}</option>)}
          </select>
          <img src={logo} className="App-logo" alt="logo" {...dim} hidden={false} />
      </>
    )
  }
}

function Home() {
  let url = process.env.REACT_APP_API_URL
  
  return (
    // eslint-disable-next-line jsx-quotes
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>Hola mundo</h1>
        <h2>url: {url}</h2>
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