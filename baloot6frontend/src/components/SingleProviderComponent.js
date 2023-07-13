import React from "react";
import axios from "axios";

import UserService from "../services/UserService";

import AddToCartCard from "../cards/AddToCartCard";
import LoginRegisterCard from "../cards/LoginRegisterCard";

const SINGLE_PROVIDER_API = "http://localhost:8080/provider";
const SINGLE_PROVIDER_COMMODITIES_API = "http://localhost:8080/commodities";
const currentUsername = localStorage.getItem("currentUsername");


class SingleProviderComponent extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            numberOfBuyListItems: 0,
            provider: {},
            commodities : [],
            currentUserNull : false
        }
    }

    currentUserControl() {
        if (currentUsername === 'undefined') {
            this.setState({currentUserNull:true});
        }
    }

    componentDidMount() {
        let url = window.location.pathname;
        let urlTokens = url.split('/');
        let providerId = urlTokens[2];

        UserService.getUserBuyList().then((response) => {
            this.setState({numberOfBuyListItems: response.data.length})
        })

        axios.get(SINGLE_PROVIDER_API + "/" + providerId)
            .then(response => {
                this.setState({provider: response.data});
            })
        axios.post(SINGLE_PROVIDER_COMMODITIES_API, providerId)
            .then(response => {
                this.setState({commodities: response.data});
                console.log(response.data);
            })
        this.currentUserControl();
    }

    loadCommodity(commodityId) {
        let path = "/commodities/" + commodityId;

        setTimeout(() => {
            window.location.replace("http://localhost:3000" + path);
        }, 88);
    }

    render() {
        const currentUsername = localStorage.getItem('currentUsername');
        const registryDate = this.state.provider.registryDate;

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
                        <div>
                            {this.state.currentUserNull
                                ?
                                <LoginRegisterCard/>
                                :
                                <div id="user-cart">
                                    <div className="btn-toolbar wd-100" role="toolbar">
                                        <a href="/user"
                                           className="btn color-brown col-5 d-flex justify-content-center align-items-center">{currentUsername}</a>
                                        {this.state.numberOfBuyListItems === 0 ?
                                            <a href="/user"
                                               className="btn bg-linen color-copper col-5 rounded-3 d-flex justify-content-center align-items-center ms-3">
                                                <span className="color-copper me-auto">Cart</span>
                                                <span className="color-copper">{this.state.numberOfBuyListItems}</span>
                                            </a>
                                            :
                                            <a href="/user"
                                               className="btn bg-brown color-linen col-5 rounded-3 d-flex justify-content-center align-items-center ms-3">
                                                <span className="color-linen me-auto">Cart</span>
                                                <span className="color-linen">{this.state.numberOfBuyListItems}</span>
                                            </a>
                                        }
                                    </div>
                                </div>
                            }
                        </div>
                    </div>
                </header>
                <main>
                    <div id="main-wrapper"
                         className="bg-white container-fluid px-4 px-xl-5 py-0 mb-4 mb-xl-5 d-flex flex-column flex-nowrap align-items-center">
                        <div id="provider" className="main-item col-11 d-flex flex-column flex-nowrap align-items-center px-5 py-4 rounded-3">
                            <div id="provider-image" className="col-5">
                                <img width="100%" src={this.state.provider.image} alt="provider" className="rounded-4"/>
                            </div>
                            <div id="provider-details" className="col-5 color-brown d-flex flex-row flex-nowrap justify-content-between">
                                <div id="provider-name" className="col-9 mt-4 fs-3 fw-semibold">
                                    <span>{this.state.provider.name}</span>
                                </div>
                                <div id="provider-registryDate" className="col-3 mt-2 pe-2 align-self-top text-end">
                                    since {this.state.provider && this.state.provider.registryDate ? this.state.provider.registryDate.substring(0,4) : ''}
                                </div>
                            </div>
                        </div>
                        <div className="main-item col-10 fs-4 fw-semibold color-brown">All Provided Commodities</div>
                        <div id="commodities" className="main-item col-10">
                            <div className="row row-cols-4 g-4">
                                {
                                    this.state.commodities.map(
                                        commodity =>
                                            <div className="col">
                                                <div className="card h-100 px-2 py-3">
                                                    <div className="card-body py-0">
                                                        <div className="card-title color-brown m-0 fw-semibold">
                                                            <div style={{cursor: 'pointer'}} onClick={() => this.loadCommodity(commodity.id)}>
                                                                {commodity.name}
                                                            </div>
                                                        </div>
                                                        <div
                                                            className="card-text color-red fw-semibold">{commodity.inStock} left
                                                            in stock
                                                        </div>
                                                    </div>
                                                    <div style={{cursor: 'pointer'}} onClick={() => this.loadCommodity(commodity.id)}>
                                                        <img src={commodity.image} className="card-img-top my-2" alt="commodity"/>
                                                    </div>
                                                    <div
                                                        className="card-body py-0 d-flex flex-row flex-nowrap align-items-center">
                                                        <span
                                                            className="color-brown fw-semibold me-auto">{commodity.price}T</span>
                                                        <AddToCartCard commodityId={commodity.id} inStock={commodity.inStock}/>
                                                    </div>
                                                </div>
                                            </div>
                                    )
                                }
                            </div>
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

export default SingleProviderComponent