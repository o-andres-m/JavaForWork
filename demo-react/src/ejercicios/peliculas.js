import React, { Component } from 'react'
import { ErrorMessage, Esperando } from '../comunes'

export default class Peliculas extends Component {
    constructor(props) {
        super(props)
        this.state = {
            listado: null,
            loading: true,
            error: null
        }
    }
  render() {
    if(this.state.loading)
        return <Esperando />
    return (
        <>
            {this.state.error && <ErrorMessage msg={this.state.error} />}

            <h1>PELICULAS</h1>

            <br></br>
            <br></br>
            {JSON.stringify(this.state.listado)}


        </>
    )
  }

  setError(msg) {
    this.setState({error: msg})
  }
  load(num) {

    this.setState({loading: true})
    fetch('http://localhost:8010/api/films/v1')
        .then(resp => {
            if(resp.ok) {
                resp.json().then(
                    data => this.setState({listado: data})
                )
            } else {
                this.setError(resp.status)
            }
        })
        .catch(err => this.setError(JSON.stringify(err)))
        .finally(() => this.setState({loading: false}))
  }
  componentDidMount() {
    this.load(1)
  }
}