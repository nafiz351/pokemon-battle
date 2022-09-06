<template>
<div>
    <div class="sticky" v-if="pokemons.length>0 && selectedNames.length >= 2">
        <div class="battle">
            <h2 class="battle-header">Pokemon Battle: <span style="color:#2a75bb">{{selectedNames[0]}}</span> vs <span style="color:#2a75bb">{{selectedNames[1]}}</span></h2>
        </div>
        <div class="battle">
            <b-button class="battle-button" variant="primary" v-on:click="battle" v-b-modal.battle-result> Start Battle</b-button>
        </div>
    </div>

    <b-modal id="battle-result" ref="battle-result" title="Battle Winner & Result" @close="reload">
        <div class="pokedex-modal">
            <div v-for="(list, index) in result" :key="index">
                <div class="pokemon" v-if="list.winner">
                    <section class="pokemon-description-name">
                        <h3 class="pokemon-name" style="text-align: center;">{{ list.pokemon.name }} (#{{ list.pokemon.id }}) </h3>
                    </section>
                    <figure class="pokemon-figure">
                        <img :src="list.pokemon.sprites ? list.pokemon.sprites.front_default : ''" :alt="list.pokemon.name" width="180">
                    </figure>
                </div>
            </div>
        </div>

        <table class="table">
            <thead>
                <tr class="bg-success">
                    <th>Pokemon Name</th>
                    <th>Normal Attack (HP)</th>
                    <th>Special Attack 01 (HP)</th>
                    <th>Special Attack 02 (HP)</th>
                    <th>Winner</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="item in result" v-bind:key="item.pokemon.id">
                    <td class="capitalising">{{item.pokemon.name}}</td>
                    <td>{{item.normalAttackHP}}</td>
                    <td>{{item.specialAttack01HP}}</td>
                    <td>{{item.specialAttack02HP}}</td>
                    <td style="text-align: center;">
                        <b-icon icon="trophy-fill" scale="2" variant="warning" v-if="item.winner"></b-icon>
                    </td>
                </tr>
            </tbody>
        </table>

        <template #modal-footer>
            <b-button class="mt-2" variant="primary" block @click="reload">Retry</b-button>
        </template>

    </b-modal>

    <div class="pokedex">
        <div class="pokemon" v-for="(pokemon, index) in pokemons" :key="index" :data-name="pokemon.name" data-type="fire" :tabindex="index">
            <section class="pokemon-description-name" :class="pokemon.selected ? 'pokemon-description-name-selected' : ''">
                <h3 class="pokemon-name">{{ pokemon.name }} (#{{ pokemon.id }}) </h3>
            </section>
            <figure class="pokemon-figure">
                <img :src="pokemon.sprites.front_default" :alt="pokemon.name" width="180">
            </figure>
            <section class="pokemon-description" :class="pokemon.selected ? 'pokemon-description-selected' : ''">
                <div class="stat-row">
                    <div class="pokemon-hp">HP: 20</div>
                    <div class="pokemon-height">Height: {{ pokemon.height }}</div>
                    <div class="pokemon-weight">Weight: {{ pokemon.weight }}</div>
                    Select for battle: <input type="checkbox" v-model="pokemon.selected" :disabled="selectedNames.length >= 2 && !pokemon.selected">
                </div>
            </section>
        </div>

        <!--<div class="selected" v-if="selectedNames[0]">
            <section class="pokemon-description">
                <h3 class="pokemon-name-bold">Player 01</h3>
            </section>
            <div><img :src="selectedImages[0]" :alt="selectedNames[0]" width="180"></div>
            <section class="pokemon-description">
                <h3 class="pokemon-name">{{ selectedNames[0] }}</h3>
            </section>
        </div>

        <div class="selected" v-if="selectedNames[1]">
            <section class="pokemon-description">
                <h3 class="pokemon-name-bold">Player 02</h3>
            </section>
            <div><img :src="selectedImages[1]" :alt="selectedNames[1]" width="180"></div>
            <section class="pokemon-description">
                <h3 class="pokemon-name">{{ selectedNames[1] }}</h3>
            </section>
        </div>-->
    </div>
</div>
</template>

<script>
import axios from 'axios';

export default {
    name: "PokemonList",
    data() {
        return {
            sortBy: 'name',
            sortDesc: false,
            pokemons: [],
            result: {},
            fields: [{
                key: 'name',
                sortable: true
            }, 'height', 'weight', 'sprites', 'selected']
        }
    },
    computed: {
        selectedNames() {
            return this.pokemons
                .filter(pokemon => pokemon.selected)
                .map(pokemon => pokemon.name);
        },

        selectedImages() {
            return this.pokemons
                .filter(pokemon => pokemon.selected)
                .map(pokemon => pokemon.sprites.front_default);
        }

    },

    created() {
        this.loadAllPokemon();
    },

    methods: {

        loadAllPokemon() {
            if (localStorage.getItem('pokemons')) {
                this.pokemons = JSON.parse(localStorage.getItem("pokemons"));
            } else {
                axios.get('http://localhost:8080/pokemon').then((result) => {
                    this.pokemons = result.data;
                    //console.warn(result.data);
                    localStorage.setItem("pokemons", JSON.stringify(this.pokemons));
                })
            }

        },

        battle() {
            axios.get("http://localhost:8080/pokemon/battle/" + this.selectedNames[0] + "/" + this.selectedNames[1])
                .then((result) => {
                    this.result = result.data;
                    //console.warn(result.data);
                })

        },

        reload() {
            //window.location.reload();
            this.loadAllPokemon();
            this.$refs['battle-result'].hide()
        }
    }
}
</script>

<style>
.sticky {
    position: -webkit-sticky;
    /* Safari & IE */
    position: sticky;
    top: 0;
    z-index: 1;
    background-color: #ffcb05;
    padding: .5rem;
}

.battle {
    display: inline-block;
}

.battle-header {
    font-size: 1.8rem;
    font-weight: bold;
    text-transform: capitalize;
    margin-top: 5px;
}

.battle-button {
    margin-top: -5px;
    margin-left: 22px;
}

.pokedex {
    padding: 1rem;
    display: grid;
    grid-gap: 3rem 2rem;
    grid-template-columns: repeat(auto-fill, 170px);
    justify-content: center;
}

.pokedex-modal {
    padding: 1rem;
    display: grid;
    grid-template-columns: repeat(auto-fill, 250px);
    justify-content: center;
}

.pokemon {
    background-color: #d2e0f1;
    overflow: hidden;
    position: relative;
    border-radius: 5px;
}

.pokemon:hover {
    animation: pokemon-up .3s;
    cursor: pointer;
}

.pokemon:focus {
    outline: none;
}

.pokemon-figure {
    text-align: center;
}

/*.pokemon .pokemon-figure img {
    position: relative;
    z-index: 1;
    transition: transform .3s;
}*/

.pokemon-description {
    background-color: #fff;
    margin: .1rem .1rem .1rem .15rem;
    padding: .5rem;
    transition: transform .3s;
}

.pokemon-description-selected {
    background-color: rgb(228, 55, 42);
    margin: .1rem .1rem .1rem .15rem;
    padding: .5rem;
    transition: transform .3s;
}

.pokemon-description-name {
    background-color: #fff;
    margin: .1rem .1rem .1rem .15rem;
    padding: .1rem;
    transition: transform .3s;
}

.pokemon-description-name-selected {
    background-color: rgb(228, 55, 42);
    margin: .1rem .1rem .1rem .15rem;
    padding: .1rem;
    transition: transform .3s;
}

.pokemon-id {
    color: #919191;
}

.pokemon-name {
    margin: 1rem 0 .6rem;
    font-size: 1rem;
    font-weight: normal;
    text-transform: capitalize;
}

.pokemon-name-bold {
    margin: 1rem 0 .6rem;
    font-size: 1rem;
    font-weight: bold;
    text-transform: capitalize;
}

.pokemon .pokemon-hp {
    background-color: #23ac17;
    color: #fff;
    padding: 1px;
    border-radius: 25px;
    margin-bottom: 1px;
}

.pokemon .pokemon-height {
    background-color: #fa8b31;
    color: #fff;
    padding: 1px;
    border-radius: 25px;
    margin-bottom: 1px;
}

.pokemon .pokemon-weight {
    background-color: #2a75bb;
    color: #fff;
    padding: 1px;
    border-radius: 25px;
}

.capitalising {
    text-transform: capitalize;
}

/*
.selected {
    background-color: #d2e0f1;
    overflow: hidden;
    position: relative;
    border-radius: 5px;
}*/
</style>
