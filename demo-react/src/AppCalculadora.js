import './calculadora';


import React, { Component } from 'react'

import { CalculadoraReact } from './calculadora';

export default class App extends Component {

  render() {
    return (
      <>
        <CalculadoraReact />
      </>
    )
  }
}