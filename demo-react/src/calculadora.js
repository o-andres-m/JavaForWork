import React, { Component } from 'react'
//import './calculadora.css'

export class CalculadoraReact extends Component {

    constructor(props){
      super(props)
      this.state = { pantalla : '0'}
      this.acumulador = 0
      this.operador2 = 0
      this.opPendiente = '+'
      this.esperaOperacion = true
    }

    ponDigito(numero){
      this.setState(prev => {
        if(this.esperaOperacion){
            if(prev.pantalla === '0') 
              return {pantalla: numero.toString()}
          return {pantalla: prev.pantalla + numero}
        }else{
          this.esperaOperacion = true;
          return {pantalla: numero.toString()}
        }
      })
    }

    opera(op){
      this.esperaOperacion = false
      this.operador2 = +this.state.pantalla
      switch(this.opPendiente){
        case '+':
          this.acumulador += this.operador2
          break;
        case '-':
          this.acumulador -= this.operador2
          break;
        case '*':
          this.acumulador *= this.operador2
          break;
        case '/':
          this.acumulador /= this.operador2
          break;
        case 'C':
          this.acumulador = 0
          this.opPendiente = '+'
          this.operador2 = 0
          break;
        default:
          break;

      }
      if(this.operador2 === 0 && this.esperaOperacion === true){
        this.setState(() => {return { pantalla : 'Infinito' }})
      }else{
        this.setState(() => {return { pantalla : this.acumulador.toString() }})
      }
      if(op==='igual'){
        this.opPendiente = '+'
        this.acumulador = 0
        this.operador2 = 0
      }else{
        this.opPendiente = op;
      }
    }

  render() {
    return (
      <>      
      <br></br>
      <div class="container px-4 text-center">
        <div class="row gx-5">
          <div class="col">
          <div class="p-3 border bg-light">

          <div id="table-responsive">
        <div className="h2">JS Calculator</div>
        <div class="alert alert-warning" role="alert">
          <output ><h3>{this.state.pantalla}</h3></output>
        </div>
        </div>

      <div class="btn-group" role="group" aria-label="Basic example">
        <input type="button" id="n7" defaultValue={7} className="btn btn-primary" onClick={() => this.ponDigito(7)}/>
        <input type="button" id="n8" defaultValue={8} className="btn btn-primary" onClick={() => this.ponDigito(8)}/>
        <input type="button" id="n9" defaultValue={9} className="btn btn-primary" onClick={() => this.ponDigito(9)}/>
        <input type="button" id="op*" defaultValue="*" className="btn btn-primary" onClick={() => this.opera('*')}/>
      </div>
      <br></br>
      <div class="btn-group" role="group" aria-label="Basic example">
        <input type="button" id="n7" defaultValue={4} className="btn btn-primary" onClick={() => this.ponDigito(4)}/>
        <input type="button" id="n8" defaultValue={5} className="btn btn-primary" onClick={() => this.ponDigito(5)}/>
        <input type="button" id="n9" defaultValue={6} className="btn btn-primary" onClick={() => this.ponDigito(6)}/>
        <input type="button" id="op*" defaultValue="-" className="btn btn-primary" onClick={() => this.opera('-')}/>
      </div>
      <br></br>
      <div class="btn-group" role="group" aria-label="Basic example">
        <input type="button" id="n7" defaultValue={1} className="btn btn-primary" onClick={() => this.ponDigito(1)}/>
        <input type="button" id="n8" defaultValue={2} className="btn btn-primary" onClick={() => this.ponDigito(2)}/>
        <input type="button" id="n9" defaultValue={3} className="btn btn-primary" onClick={() => this.ponDigito(3)}/>
        <input type="button" id="op*" defaultValue="+" className="btn btn-primary" onClick={() => this.opera('+')}/>
      </div>
      <br></br>
      <div class="btn-group" role="group" aria-label="Basic example">
        <input type="button" id="n7" defaultValue={'C'} className="btn btn-primary" onClick={() => this.opera('C')}/>
        <input type="button" id="n8" defaultValue={0} className="btn btn-primary" onClick={() => this.ponDigito(0)}/>
        <input type="button" id="n9" defaultValue={','} className="btn btn-primary" />
        <input type="button" id="op*" defaultValue="/" className="btn btn-primary" onClick={() => this.opera('/')}/>
      </div>
      <br></br>
      <div class="btn-group" role="group" aria-label="Basic example">
        <input type="button" id="=" defaultValue={'='} className="btn btn-primary" onClick={() => this.opera('= ')}/>
      </div>

          </div>
          </div>
        </div>
      </div>


      
      </>
    )
  }
}
