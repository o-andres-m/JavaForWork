import { titleCase } from "./formateadores"

describe('Prueba Formateador' , () => {

    describe('TitleCase', () => {

        [['prueba','Prueba'],
        ['dos palabras', 'Dos Palabras'],
        ['YA MAYUSCULA' , 'Ya Mayuscula'],
        ['maYuscula eN meDio' , 'Mayuscula En Medio'],
        [' espacio primero' , 'Espacio Primero'],
        [null , null],
        [undefined , undefined],
        [111 , 111],
        ].forEach(([origen,resultado]) =>{

            it(`${origen} => ${resultado}`, function() {
                expect(titleCase(origen)).toBe(resultado)
            })
        })
    })
})