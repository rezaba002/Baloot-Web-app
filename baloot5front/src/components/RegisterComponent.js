import React from "react";
import axios from "axios";
import LoginRegisterCard from "../cards/LoginRegisterCard";

const REGISTER_API = "http://localhost:8080/register";

class RegisterComponent extends React.Component {
    constructor(props) {
        super(props);

        this.setUsername = this.setUsername.bind(this);
        this.setPassword = this.setPassword.bind(this);
        this.setEmail = this.setEmail.bind(this);
        this.setAddress = this.setAddress.bind(this);
        this.setBirthDate = this.setBirthDate.bind(this);
        this.handleRegister = this.handleRegister.bind(this);

        this.state = {
            username: "",
            password: "",
            email: "",
            address: "",
            birthDate: ""
        };
    }

    setUsername(event) {
        this.setState({username: event.target.value});
    }
    setPassword(event) {
        this.setState({password: event.target.value});
    }
    setEmail(event) {
        this.setState({email: event.target.value});
    }
    setAddress(event) {
        this.setState({address: event.target.value});
    }
    setBirthDate(event) {
        this.setState({birthDate: event.target.value});
    }

    redirectToLogin(event) {
        window.location.href = "/login";
    }

    handleRegister(event) {
        event.preventDefault();

        const username = this.state.username;
        const password = this.state.password;
        const email = this.state.email;
        const address = this.state.address;
        const birthDate = this.state.birthDate;

        const REGISTER_DATA = {"username": username, "password": password, "email": email, "address": address, "birthDate": birthDate};

        axios.post(REGISTER_API, REGISTER_DATA)
            .then(response => {
                if (response.data === "username is invalid")
                    alert("username is invalid");
                else if (response.data === "username is already taken")
                    alert("username is already taken")
                else if (response.data === "email is already taken")
                    alert("email is already taken")
                else if (response.data === "registration successful")
                    window.location.replace("/");
                else
                    console.log(response);
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
                            <div className="color-brown mt-5 mb-4 fs-2 fw-bold">User Register</div>
                            <form onSubmit={this.handleRegister}
                                  className="d-flex flex-column flex-nowrap justify-content-center align-items-center col-10">
                                <input type="text" id="username" name="username" placeholder="Enter username" required value={this.state.username} onChange={this.setUsername} ref={this.usernameRef}
                                       className="col-12 p-3 rounded-3 border"/>
                                <input type="password" id="password" name="password" placeholder="Password" required value={this.state.password} onChange={this.setPassword} ref={this.passwordRef}
                                       className="col-12 p-3 rounded-3 border"/>
                                <input type="email" id="email" name="email" placeholder="Enter email" required value={this.state.email} onChange={this.setEmail} ref={this.emailRef}
                                       className="col-12 p-3 rounded-3 border"/>
                                <input type="text" id="address" name="address" placeholder="Enter address" required value={this.state.address} onChange={this.setAddress} ref={this.addressRef}
                                       className="col-12 p-3 rounded-3 border"/>
                                <input type="date" id="birthDate" name="birthDate" placeholder="Enter birthDate" required value={this.state.birthDate} onChange={this.setBirthDate} ref={this.birthDateRef}
                                       className="col-12 p-3 rounded-3 border"/>
                                <div id="terms" className="align-self-start color-brown">
                                    <input type="checkbox" name="agree" id="agree" className="me-1" required/>
                                    <label htmlFor="agree">I agree to</label><a href="#"
                                                                                className="ms-1 color-blue">Terms and Conditions</a>
                                </div>
                                <button type="submit"
                                        className="col-12 btn bg-brown color-linen p-3 rounded-3">Register
                                </button>
                            </form>
                            <a onClick={this.redirectToLogin}
                               className="col-12 bg-dun color-brown mt-4 p-4 rounded-bottom-3 text-center">Already have an
                                account?</a>
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

export default RegisterComponent