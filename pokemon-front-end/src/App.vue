<template>
  <div id="app">
    <img alt="Vue logo" src="./assets/pokemon.png" width="350" height="190">
    <Pokemon msg="Welcome to Pokemon Battle App"/>
  </div>
</template>

<script>
import Pokemon from './components/PokemonList.vue'

export default {
  name: 'App',
  components: {
    Pokemon
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: -30px;
}
</style>
