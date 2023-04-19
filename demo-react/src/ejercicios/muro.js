import React, { Component } from 'react'
import { ErrorMessage, Esperando } from '../comunes'

export default class Muro extends Component {
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

            <h1>Muro</h1>
            <input type='button' defaultValue={1} onClick={()=> this.load(1)} />
            <input type='button' defaultValue={2} onClick={()=> this.load(2)} />
            <input type='button' defaultValue={3} onClick={()=> this.load(3)} />
            <input type='button' defaultValue={4} onClick={()=> this.load(4)} />
            <input type='button' defaultValue={5} onClick={()=> this.load(5)} />

            <br></br>
            <br></br>


{/*             {JSON.stringify(this.state.listado)}
 */}
            <div class="container text-center">
                <div class="row">
                    <div class="col">
                    {this.state.listado && this.state.listado.map((item) => (
/*                             <img src={item.download_url} width="100" height="100" alt={item.id} />
 */
                            <input type='button' defaultValue={item.id} onClick={this.view(item.id)} />               
                            ))}
                    </div>
                </div>
            </div>
        </>
    )
  }

  setError(msg) {
    this.setState({error: msg})
  }
  load(num) {
    this.setState({loading: true})
    fetch('https://picsum.photos/v2/list?page='+num+'&limit=10')
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
  view(id){
    return null;
  }
}