import React from 'react';
import axios from "axios";
import ReactDOM from 'react-dom/client';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";

import UserComponent from "./components/UserComponent";
import CommodityComponent from "./components/CommodityComponent";
import SingleCommodityComponent from "./components/SingleCommodityComponent";
import SingleProviderComponent from "./components/SingleProviderComponent";
import LoginComponent from "./components/LoginComponent";
import RegisterComponent from "./components/RegisterComponent";

import 'bootstrap/dist/js/bootstrap.bundle.min';
import 'bootstrap/dist/css/bootstrap.css';
import './index.css';
import './css/commodity.css';
import './css/error.css';
import './css/header.css';
import './css/index.css';
import './css/login-register.css';
import './css/main.css';
import './css/reset.css';
import './css/user.css';


const CURRENT_USER_API = "http://localhost:8080/currentUser";
axios.get(CURRENT_USER_API)
    .then(response => {
        localStorage.setItem("currentUsername", response.data.username);
        localStorage.setItem("currentPassword", response.data.password);
        localStorage.setItem("currentEmail", response.data.email);
        localStorage.setItem("currentBirthDate", response.data.birthDate);
        localStorage.setItem("currentAddress", response.data.address);
        localStorage.setItem("currentCredit", response.data.credit);
    })
    .catch((error) => {
        console.log(error)
    });

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
      <Router>
          <div>
              <Routes>
                  <Route path="/" element={<CommodityComponent/>} />
                  <Route path="/user" element={<UserComponent/>} />
                  <Route path="/commodities/*" element={<SingleCommodityComponent/>}/>
                  <Route path="/providers/*" element={<SingleProviderComponent/>} />
                  <Route path="/login" element={<LoginComponent/>} />
                  <Route path="/register" element={<RegisterComponent/>} />
              </Routes>
          </div>
      </Router>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
