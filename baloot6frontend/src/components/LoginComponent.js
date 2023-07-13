import React from "react";
import axios from "axios";
import LoginRegisterCard from "../cards/LoginRegisterCard";

const LOGIN_API = "http://localhost:8080/login";

class LoginComponent extends React.Component {
    constructor(props) {
        super(props);

        this.setUsername = this.setUsername.bind(this);
        this.setPassword = this.setPassword.bind(this);
        this.handleLogin = this.handleLogin.bind(this);
        this.showInvalid = this.showInvalid.bind(this);

        this.state = {
            username: "",
            password: "",
            invalid: false
        };
    }

    setUsername(event) {
        this.setState({username: event.target.value});
    }
    setPassword(event) {
        this.setState({password: event.target.value});
    }

    showInvalid(event) {
        this.setState({invalid: true});
    }

    handleLogin(event) {
        event.preventDefault();

        const username = this.state.username;
        const password = this.state.password;

        const LOGIN_DATA = {"username": username, "password": password};

        axios.post(LOGIN_API, LOGIN_DATA)
            .then(response => {
                if (response.data)
                    window.location.replace("/");
                else
                    this.showInvalid();
            })
            .catch((error) => {
                console.log(error)
            });
    }

    render() {
        return (
            <div id="body-wrapper" className="container-fluid p-0 d-flex flex-column flex-nowrap justify-content-between" >
                <header>
                    <div id="header-wrapper"
                         className="bg-white container-fluid px-4 px-xl-5 py-1 d-flex flex-row flex-nowrap justify-content-between align-items-center">
                        <div id="header-logo" className="d-flex flex-row flex-nowrap align-items-center">
                            <a href="/" id="baloot-logo"></a>
                            <a href="/" id="baloot-title"
                               className="color-copper ms-2 fw-semibold fs-4">Baloot</a>
                        </div>
                        <LoginRegisterCard />
                    </div>
                </header>
                <main>
                    <div id="main-wrapper"
                         className="bg-white container-fluid px-4 px-xl-5 py-0 mb-4 mb-xl-5 d-flex flex-column flex-nowrap justify-content-center align-items-center">
                        <div
                            className="col-10 col-sm-8 col-md-6 col-lg-4 col-xl-3 bg-linen d-flex flex-column flex-nowrap justify-content-center align-items-center rounded-4">
                            <div className="color-brown mt-5 mb-4 fs-2 fw-bold">User Login</div>
                            <form onSubmit={this.handleLogin}
                                  className="d-flex flex-column flex-nowrap justify-content-center align-items-center col-10">
                                <input type="text" id="username" name="username" value={this.state.username} onChange={this.setUsername} ref={this.usernameRef} placeholder="Enter username" required
                                       className="col-12 p-3 rounded-3 border"/>
                                <input type="password" id="password" name="password" value={this.state.password} onChange={this.setPassword} ref={this.passwordRef} placeholder="Password" required
                                       className="col-12 p-3 rounded-3 border"/>
                                {this.state.invalid ?
                                    <div>Invalid Credentials!</div>
                                    : null
                                }
                                <div id="rememberme" className="align-self-start color-brown">
                                    <input type="checkbox" name="remember" id="remember" className="me-1"/>
                                    <label htmlFor="remember">Remember me</label>
                                </div>
                                <button type="submit"
                                        className="col-12 btn bg-brown color-linen p-3 rounded-3">Login
                                </button>
                            </form>
                            <a href="#" className="col-12 bg-dun color-brown mt-4 p-4 rounded-bottom-3 text-center">Forgot
                                Password?</a>
                        </div>
                    </div>
                </main>
                <footer>
                    <div id="footer-wrapper"
                         className="bg-brown color-white px-5 py-2 fw-semibold container-fluid d-flex flex-row flex-nowrap justify-content-center align-items-center">
                        <div className="color-linen fs-5">2023 © VPT</div>
                    </div>
                </footer>
            </div>
        );
    }
}

export default LoginComponent