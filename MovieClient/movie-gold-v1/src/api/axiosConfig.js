import axios from 'axios';


const api = axios.create({
  

    baseURL: 'http://localhost:8080',

  headers: {
    'ngrok-skip-browser-warning': 'true',
  },
});

export default api;

//to configure and export the Axios object that we will use to make HTTP requests to the relevant remote API through
//this code

//Note the settings that has been included here,
//We have base URL settings, which provide the base address of the API endpoints that our client react application
//will be calling 

//the headers setting is necessary, because during the development phase, the technology that the remote machine
//is using to expose the relevant API endpoint is called ngrok
//The reason why we include this setting is so that our client HTTP requests are not blocked by cors
//cors stands for cross origin resource sharing
//Because our API is runnning in a different domain, or origin corse might block our access to the endpoint 
//The server code has included settings in order to overcome the restrictions imposed by cors