# pokemon-battle
<h3>Spring Boot App that simulates a battle between two Pokémons.</h3>

- Front End: Vue.js
- Back End: Spring Boot

**Loading a list of 50 Pokémons using the API https://pokeapi.co/. Pokemon Name, height and weight information are fetched from api and 20 HP (Health Points) is given to each Pokémon.**
![screencapture-localhost-8081-2022-09-06-00_28_05](https://user-images.githubusercontent.com/6628742/188548050-367820bd-cceb-4859-b993-9e9db63e0156.png)

**Two users can select 2 pokemons. When two pokemons are selected app will disable the selection box and it will enable the "Start Battle" button.**
![image](https://user-images.githubusercontent.com/6628742/188548066-cff40cb3-1a28-46cf-825f-bde7f4075718.png)

**Each battle has 3 rounds (1 Normal round/attack and 2 Special round/attack). The 1st 2 round winner will be the battle winner.**
![image](https://user-images.githubusercontent.com/6628742/188548088-a5263a84-4d30-4002-b0df-07feeaa25f69.png)

**If the 1st 2 round winner is not the same then round 3 will decide the winner.**
![image](https://user-images.githubusercontent.com/6628742/188548103-e75dd309-c560-405e-9496-fd212d8b7a46.png)

**Using the "Retry" button user can go back to the main view and choose pokemon again to fight.
![image](https://user-images.githubusercontent.com/6628742/188559329-636dac0e-02f1-4ae1-8ccf-21e948c32ed0.png)
![image](https://user-images.githubusercontent.com/6628742/188559406-0eddfe1d-4395-4372-ace5-2bc4c955e480.png)

To minimize the load time, pokemon list information is stored into the localStorage. If the information is not available into the localStorage, then it will load data using the API.

````
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
````
