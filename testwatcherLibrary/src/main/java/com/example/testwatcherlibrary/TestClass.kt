package com.example.testwatcherlibrary

/* Questa corrisponde alla DataClass per oggetti Json!!
    - PUO' COMPRENDERE ANCHE NESTED DATA CLASS!!!
*/

/* Ma ci deve anche essere un controllo sui tipi!!!! quinci va bene EnumClass degli eventi!!!
*/

/* LA STRUTTURA DESIDERATA E' quella di lista di "classi di Test" con una lista di "metodi di test" (che sono oggetti)
    chiave: Stringa classe
    valore: Lista (array) di oggetti (stringa, Risultato)

    Test: {
    [
     {"Classe Uno": [ {"Test Uno": Risultato.successo } ] }
     ]
     }

     List of TestClasses -> List of Methods -> Risultati
     Una DataClass con il metodo toJson viene convertito in un oggetto Json che mantiene come chiavi il nome delle proprieta' prestabilite
     se ad proprietà di una dataClasse viene associata una altra dataclass (si tratta di un Nested Object)  es: "Cliente uno": {nome: ..., cognome: ... }
*/

//oggetto che ha come valore una lista di
data class TestClass (var listaClassi: MutableList<SingleClass>)

data class SingleClass(var listaTests: MutableList<SingleTest>)
//data class SingleClass(var listaTests: SingleTest)

data class SingleTest ( var nameTest: String, var outcome: String, var altriElementi: String? = null )