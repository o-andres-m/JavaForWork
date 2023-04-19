import React, { Component } from 'react'
import { ErrorBoundary } from './comunes';
import Peliculas from './ejercicios/peliculas';

export class App extends Component {

    constructor(props){
      super(props)
      this.state = {
        cont: 0,
        main: 0
    }
      this.menu = [
        { texto: 'inicio', url: '/', componente: <Home />},
        { texto: 'Peliculas', url: '/peliculas', componente: <Peliculas /> }
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
    </>
    )
  }
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

  function Cabecera(props) {
    return (
      <header>
        <Menu {...props} />
      </header>
    );
  }

  function Home() {
    
    return (
      <div className="App">
        <header className="App-header">
            CATALOGO INICIO
        </header>
      </div>
    );
  }