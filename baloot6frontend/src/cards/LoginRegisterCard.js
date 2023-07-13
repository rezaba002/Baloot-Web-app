import React from "react";

class LoginRegisterCard extends React.Component {
    constructor(props) {
        super(props);

        this.redirectToRegister = this.redirectToRegister.bind(this);
        this.redirectToLogin = this.redirectToLogin.bind(this);
    }

    redirectToRegister() {
        window.location.href = "/register";
    }

    redirectToLogin() {
        window.location.href = "/login";
    }

    componentDidMount() {
    }

    render() {
        return (
            <div id="reg-login">
                <div className="btn-toolbar wd-100" role="toolbar">
                    <a onClick={this.redirectToRegister}
                       className="btn bg-linen color-copper col-5 rounded-3 d-flex justify-content-center align-items-center">Register</a>
                    <a onClick={this.redirectToLogin}
                       className="btn bg-linen color-copper col-5 rounded-3 d-flex justify-content-center align-items-center ms-3">Login</a>
                </div>
            </div>
        )
    }

}

export default LoginRegisterCard