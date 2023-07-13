import React from "react";
import axios from "axios";
import UserService from "../services/UserService";
import {Button} from "@mui/material";
import AddCredit from "../cards/AddCredit";
import AddToCartCardInCartCard from "../cards/AddToCartCardInCartCard";
import PayModal from "../cards/PayModal";

const currentUsername = localStorage.getItem("currentUsername");

const LOGOUT_API = "http://localhost:8080/logout";

class UserComponent extends React.Component {

    constructor(props) {
        super(props);

       this.logout = this.logout.bind(this);
        this.loginControl = this.loginControl.bind(this);
        this.showCart = this.showCart.bind(this);
        this.showHistory = this.showHistory.bind(this);

        this.state = {
            buyListItems: [],
            purchasedListItems: [],
            showCart: false,
            showHistory: false,
            currentUserNull: true,
            currentCredit: 0,
            cartPrice: 0,
            price:0,
            priceSum:0,
            isModalOpen: false
        }
    }

    componentDidMount() {
        this.loginControl();
        UserService.getUsers().then((response) => {
            this.setState({users:response.data})
        })
        UserService.getUserBuyList().then((response) => {
            this.setState({buyListItems: response.data})
        })
    }

    logout() {
        axios.get(LOGOUT_API)
            .then(response => {
            window.location.replace("/login")
            })
            .catch((error) => {
                console.log(error)
            });
    }

    loginControl() {
        if (currentUsername === 'null'){
            window.location.replace("/login");
        }
    }

    showCart = () => {
        if (this.state.showCart)
            this.setState({showCart:false})
        else {
            this.setState({showCart:true})
        }
        console.log(this.state.buyListItems);
        let i = 0;
        const loop = () => {
            if (i < this.state.buyListItems.length) {
                const Item = this.state.buyListItems[i];
                console.log(this.state.priceSum);
                this.setState({priceSum : this.state.priceSum + ((Item.commodity.price)*(Item.quantity))});
                i++;
                setTimeout(loop, 75);
            }
        };
        loop();
        console.log(this.state.priceSum);
        // for (let i=0 ; i < this.state.buyListItems.length ; i++) {
        //     console.log(this.state.priceSum);
        //     const Item = this.state.buyListItems[i];
        //     this.setState({priceSum : this.state.priceSum + Item.commodity.price});
        // }
    }

    showHistory() {
        if (this.state.showHistory)
            this.setState({showHistory:false})
        else {
            this.setState({showHistory:true})
            UserService.getUserPurchasedList().then((response) => {
                this.setState({purchasedListItems:response.data})
            })
        }
    }

    render() {
        const currentUsername = localStorage.getItem("currentUsername");
        const currentEmail = localStorage.getItem("currentEmail");
        const currentBirthDate = localStorage.getItem("currentBirthDate");
        const currentAddress = localStorage.getItem("currentAddress");
        const currentCredit = localStorage.getItem("currentCredit");
        const {priceSum} = this.state;
        const {isModalOpen} = this.state;
        return (
            <div id="body-wrapper" className="container-fluid p-0 d-flex flex-column flex-nowrap">
                <header>
                    <div id="header-wrapper"
                         className="bg-white container-fluid px-4 px-xl-5 py-1 d-flex flex-row flex-nowrap justify-content-between align-items-center">
                        <div id="header-logo" className="d-flex flex-row flex-nowrap align-items-center">
                            <a href="/" id="baloot-logo"></a>
                            <a href="/" id="baloot-title"
                               className="color-copper ms-2 fw-semibold fs-4">Baloot</a>
                        </div>
                        <div id="user-cart">
                            <div className="btn-toolbar wd-100" role="toolbar">
                                <a href="#"
                                   className="btn color-brown col-5 d-flex justify-content-center align-items-center fw-semibold">{currentUsername}</a>
                                <a href="#"
                                   className="btn bg-brown color-copper col-5 rounded-3 d-flex justify-content-center align-items-center ms-3">
                                    <span className="color-linen me-auto">Cart</span>
                                    <span className="color-linen">1</span>
                                </a>
                                <button onClick={this.logout}>logout</button>
                            </div>
                        </div>
                    </div>
                </header>
                <main>
                    <div id="main-wrapper"
                         className="bg-white container-fluid px-4 px-xl-5 py-0 mb-4 mb-xl-5 d-flex flex-column flex-nowrap align-items-center">
                        <div id="user" className="main-item col-11 row g-0 px-5 py-4 rounded-3">
                            <div id="user-details" className="col-6 color-brown fs-5 fw-semibold">
                                <div id="user-name">
                                    <span className="user-logo logo-name"></span>
                                    <span>{currentUsername}</span>
                                </div>
                                <div id="user-email">
                                    <span className="user-logo logo-email"></span>
                                    <span>{currentEmail}</span>
                                </div>
                                <div id="user-birthdate">
                                    <span className="user-logo logo-birthdate"></span>
                                    <span>{currentBirthDate}</span>
                                </div>
                                <div id="user-address">
                                    <span className="user-logo logo-address"></span>
                                    <span>{currentAddress}</span>
                                </div>
                            </div>
                            <div id="user-credit" className="col-6 px-2">
                                <div id="current-credit" className="col-12 color-brown mb-2 text-center fw-bold">
                                    <span>{currentCredit}</span>T
                                </div>
                                <div>
                                    <AddCredit/>
                                </div>

                            </div>
                        </div>
                        <div id="cart" className="main-item col-11">
                            <button className="color-brown my-4 fw-semibold" onClick={this.showCart}>
                                <span id="cart-logo"></span>
                                Cart
                            </button>
                            {this.state.showCart ?
                            <div>
                            <div id="cart-header" className="row g-0 mb-3 px-4 py-3 rounded-3 fw-semibold text-center">
                                <div className="col-1">Image</div>
                                <div className="col-2">Name</div>
                                <div className="col-2">Categories</div>
                                <div className="col-2">Price</div>
                                <div className="col-1">Provider ID</div>
                                <div className="col-1">Rating</div>
                                <div className="col-1">In Stock</div>
                                <div className="col-2">In Cart</div>
                            </div>
                                {
                                    this.state.buyListItems.map (
                                        buyListItem =>
                                    <div key={buyListItem.commodity.id}
                                        className="cart-commodity row g-0 px-4 py-3 rounded-3 fw-semibold text-center d-flex flex-row flex-nowrap align-items-center">
                                        <div className="col-1">
                                            <a href="commodity.html" target="_blank">
                                                <img src={buyListItem.commodity.image} width="80" alt="commodity"/>
                                            </a>
                                        </div>
                                        <div className="col-2 color-grey">{buyListItem.commodity.name}</div>
                                        <div className="col-2 color-grey">{buyListItem.commodity.categories}</div>
                                        <div className="col-2 color-grey">
                                            <span>{buyListItem.commodity.price}T</span>
                                        </div>
                                        <div className="col-1 color-grey">{buyListItem.commodity.providerId}</div>
                                        <div className="col-1 color-yellow fs-5">{buyListItem.commodity.rating}</div>
                                        <div className="col-1 color-red fs-5">{buyListItem.commodity.inStock}</div>
                                        <div> <AddToCartCardInCartCard commodity={buyListItem.commodity} quantity={buyListItem.quantity}/> </div>
                                    </div>
                                    )
                                }
                            <div className="col-6 mx-auto mt-5">
                                <button onClick={() => this.setState({isModalOpen : true})}
                                        className="col-12 btn bg-brown color-linen px-4 py-2 rounded-3 fs-5 fw-bold">Pay Now!</button>
                                {isModalOpen && (
                                    <PayModal priceSum = {priceSum} />
                                )}
                            </div>
                            </div>
                            : null}
                        </div>
                        <div id="history" className="main-item col-11">
                            <Button className="color-brown my-4 fw-semibold" onClick={this.showHistory}>
                                <span id="history-logo"></span>
                                History
                            </Button>
                            {this.state.showHistory ?
                            <div>
                            <div id="history-header"
                                 className="row g-0 mb-3 px-4 py-3 rounded-3 fw-semibold text-center">
                                <div className="col-1">Image</div>
                                <div className="col-2">Name</div>
                                <div className="col-2">Categories</div>
                                <div className="col-2">Price</div>
                                <div className="col-1">Provider ID</div>
                                <div className="col-1">Rating</div>
                                <div className="col-1">In Stock</div>
                                <div className="col-2">Quantity</div>
                            </div>
                                {
                                    this.state.purchasedListItems.map(
                                        purchasedListItem =>
                                    <div
                                        className="history-commodity row g-0 px-4 py-3 rounded-3 fw-semibold text-center d-flex flex-row flex-nowrap align-items-center">
                                        <div className="col-1">
                                            <a href="#">
                                                <img src={purchasedListItem.commodity.image} width="80" alt="commodity"/>
                                            </a>
                                        </div>
                                        <div className="col-2 color-grey">{purchasedListItem.commodity.name}</div>
                                        <div className="col-2 color-grey">{purchasedListItem.commodity.categories}</div>
                                        <div className="col-2 color-grey">
                                            <span>{purchasedListItem.commodity.price}</span>T
                                        </div>
                                        <div className="col-1 color-grey">{purchasedListItem.commodity.providerId}</div>
                                        <div className="col-1 color-yellow fs-5">{purchasedListItem.commodity.rating}</div>
                                        <div className="col-1 color-red fs-5">{purchasedListItem.commodity.inStock}</div>
                                        <div className="col-2 color-grey fs-5">{purchasedListItem.quantity}</div>
                                    </div>
                                    )
                                }
                            </div>
                            : null}
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
        )
    }
}

export default UserComponent