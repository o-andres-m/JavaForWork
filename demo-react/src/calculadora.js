import React, { Component } from 'react'
import './calculadora.css'

export class CalculadoraReact extends Component {

    constructor(props){
      super(props)
      this.state = { pantalla : '0'}
      this.acumulador = 0

    }

    ponDigito(numero){
      this.setState(prev => {
        if(prev.pantalla === '0') 
          return {pantalla: numero.toString()}
      return {pantalla: prev.pantalla + numero}
        
      })
    }

    opera(op){
      switch(op){
        case '+':
          this.setState(prev => {
            this.acumulador = +prev.pantalla + this.acumulador
            return {pantalla : this.acumulador}
        })
              break;
        case '-':
            this.setState(prev => {
                this.acumulador = +prev.pantalla - this.acumulador
                return {pantalla : this.acumulador}
            })
            break;
        case '*':
              this.setState(prev => {
                  this.acumulador = +prev.pantalla * this.acumulador
                  return {pantalla : this.acumulador}
              })
              break;

        case '/':
                this.setState(prev => {
                    this.acumulador = +prev.pantalla / this.acumulador
                    return {pantalla : this.acumulador}
                })
                break;
        case 'C':
                this.setState(prev => {
                    this.acumulador = 0
                    return {pantalla : this.acumulador}
                })
                break;
        default:
          return null;
      }

    }

  render() {
    return (
      <>      <div id="calculadora">
        <div className="title">JS Calculator</div>
        <div className="box-result">
          <output className="result">{this.state.pantalla}</output>
        </div>
        <table id="tabla">
          <tbody>
            <tr>
              <td>
                <input type="button" id="n7" defaultValue={7} className="button" onClick={() => this.ponDigito(7)}/>
              </td>
              <td>
                <input type="button" id="n8" defaultValue={8} className="button" onClick={() => this.ponDigito(8)}/>
              </td>
              <td>
                <input type="button" id="n9" defaultValue={9} className="button" onClick={() => this.ponDigito(9)}/>
              </td>
              <td>
                <input type="button" id="op*" defaultValue="*" className="button" onClick={() => this.opera('*')}/>
              </td>
            </tr>
            <tr>
              <td>
                <input type="button" id="n4" defaultValue={4} className="button" onClick={() => this.ponDigito(4)}/>
              </td>
              <td>
                <input type="button" id="n5" defaultValue={5} className="button" onClick={() => this.ponDigito(5)}/>
              </td>
              <td>
                <input type="button" id="n6" defaultValue={6} className="button" onClick={() => this.ponDigito(6)}/>
              </td>
              <td>
                <input type="button" id="op-" defaultValue="-" className="button" onClick={() => this.opera('-')}/>
              </td>
            </tr>
            <tr>
              <td>
                <input type="button" id="n1" defaultValue={1} className="button" onClick={() => this.ponDigito(1)}/>
              </td>
              <td>
                <input type="button" id="n2" defaultValue={2} className="button" onClick={() => this.ponDigito(2)}/>
              </td>
              <td>
                <input type="button" id="n3" defaultValue={3} className="button" onClick={() => this.ponDigito(3)}/>
              </td>
              <td>
                <input type="button" id="op+" defaultValue="+" className="button" onClick={() => this.opera('+')}/>
              </td>
            </tr>
            <tr>
              <td>
                <input type="button" id="c" defaultValue="C" className="button" onClick={() => this.opera('C')}/>
              </td>
              <td>
                <input type="button" id="n0" defaultValue={0} className="button" onClick={() => this.ponDigito(0)}/>
              </td>
              <td>
                <input type="button" id="op," defaultValue="," className="button" />
              </td>
              <td>
                <input type="button" id="op/" defaultValue="/" className="button" onClick={() => this.opera('/')}/>
              </td>
            </tr>
            <tr>
              <td />
              <td>
                <input type="button" id="op=" defaultValue="=" className="button" onClick={() => this.opera('=')}/>
              </td>
              <td />
            </tr>
          </tbody>
        </table>
      </div>
      </>
    )
  }
}
