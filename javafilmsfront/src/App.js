import './App.css';


import React, { Component } from 'react'
import { ErrorBoundary } from './biblioteca/comunes';
import { ActoresMnt } from './componentes/actores';
import { Peliculas } from './componentes/peliculas';
import { CategoriasMnt } from './componentes/categorias';
import { IdiomasMnt } from './componentes/idiomas';

export default class App extends Component {
  constructor(props) {
    super(props)
    this.state = {
        cont: 0,
        main: 0
    }
    this.menu = [
      { texto: 'Peliculas', url: '/peliculas', componente: <Peliculas />},
      { texto: 'Actores', url: '/actores', componente: <ActoresMnt />},
      { texto: 'Categorias', url: '/categorias', componente: <CategoriasMnt />},
      { texto: 'Idiomas', url: '/idiomas', componente: <IdiomasMnt />},
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
    <div class="p-3 bg-info bg-opacity-10 border border-info border-start-0 rounded-end">
      <h1>javaFilms</h1>
        <nav>
      {menu.map((item, index) => 
      <>
      <div className="btn-group" role="group" aria-label="Basic example">
      <button key={index} className="btn btn-primary" type='button' onClick={() => onSelectMenu && onSelectMenu(index)}>{item.texto}</button>
      </div>
      </>
        )
      }
      </nav>
    </div>
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
            <span className="me-3" >Catalogo Front por <a href='https://github.com/o-andres-m/JavaForWork' target='blank'>Osvaldo Andres Martinez</a></span>
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